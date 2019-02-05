package com.kralite.workflow.parser;

import com.kralite.workflow.common.*;
import com.kralite.workflow.exception.ParseFlowException;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Attr;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by ChenDaLin on 2019/1/29.
 */
public class DefaultXMLFlowParser implements FlowParser {

    private static final String ELE_NODES = "nodes";
    private static final String ELE_NODE = "node";
    private static final String ELE_START_NODE = "startNode";
    private static final String ELE_LINES = "lines";
    private static final String ELE_LINE = "line";
    private static final String ELE_PROPS = "props";
    private static final String ELE_PROP = "prop";
    private static final String ELE_PARAM = "param";
    private static final String ELE_EXTENDED_IN_PARAMS = "extendedInParams";
    private static final String ELE_EXTENDED_OUT_PARAMS = "extendedOutParams";
    private static final String ELE_PIPELINES = "pipelines";
    private static final String ELE_PIPELINE = "pipeline";

    private static final String ATTR_ID = "id";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_TYPE = "type";
    private static final String ATTR_CLASS = "class";
    private static final String ATTR_REQUIRED = "required";
    private static final String ATTR_START_PARAM = "startParam";
    private static final String ATTR_END_PARAM = "endParam";
    private static final String ATTR_START_NODE = "startNode";
    private static final String ATTR_END_NODE = "endNode";

    private static final String DEFAULT_NODE_NAME = "DefaultNode";
    private static final String DEFAULT_NODE_PIPELINE = "DefaultPipeline";


    @Override
    public ParseStruct parse(String flowDescriptionXml) throws ParseFlowException{
        ParseStruct parseStruct = new ParseStruct();
        try {
            Document document = DocumentHelper.parseText(flowDescriptionXml);
            Element root = document.getRootElement();

            Map<String, FlowNode> flowNodeMap = parseNodes(root.elements(ELE_NODES));
            FlowNode startNode = parseStartNode(root.elements(ELE_START_NODE), flowNodeMap);
            Map<String, FlowLine> lineMap = parseLines(root.elements(ELE_LINES), flowNodeMap);
            Map<String, NodeConnections> nodeConnectionsMap = parseNodeConnections(flowNodeMap, lineMap);

            parseStruct.nodeMap = nodeConnectionsMap;
            parseStruct.lineMap = lineMap;
            parseStruct.startNode = startNode;

        }catch (DocumentException de) {
            throw new ParseFlowException("Parse flow description XML file error. " + de.getMessage(), de);
        }
        return parseStruct;
    }

    private FlowNode parseStartNode(List<Element> startNodeEle, Map<String, FlowNode> flowNodeMap) throws ParseFlowException {
        if (startNodeEle.size() == 0) {throw new ParseFlowException("No '<startNode>' tag is found in the flow description XML file.");}
        else if (startNodeEle.size() > 1) {throw new ParseFlowException("More than one '<startNode>' tag are found in the flow description XML file.");}

        FlowNode startNode = parseSingleNode(startNodeEle.get(0), flowNodeMap);
        flowNodeMap.put(startNode.getId(), startNode);
        return startNode;
    }

    private Map<String, FlowNode> parseNodes(List<Element> nodesEle) throws ParseFlowException{
        Map<String, FlowNode> flowNodeMap = new HashMap<>();
        if (nodesEle.size() == 0) {throw new ParseFlowException("No '<nodes>' tag is found in the flow description XML file.");}
        else if (nodesEle.size() > 1) {throw new ParseFlowException("More than one '<nodes>' tag are found in the flow description XML file.");}

        List<Element> nodeEleList = nodesEle.get(0).elements(ELE_NODE);
        for (Element nodeEle: nodeEleList) {
            FlowNode node = parseSingleNode(nodeEle, flowNodeMap);
            flowNodeMap.put(node.getId(), node);
        }
        return flowNodeMap;
    }

    private FlowNode parseSingleNode(Element nodeEle, Map<String, FlowNode> nodeMap) throws ParseFlowException{
        // id
        Attribute nodeId = nodeEle.attribute(ATTR_ID);
        if (nodeId == null) {throw new ParseFlowException("<node> tag must have attrbute 'id'.");}
        if (nodeMap.containsKey(nodeId.getValue())) {throw new ParseFlowException("Node id duplicated: " + nodeId);}

        // type
        Attribute type = nodeEle.attribute(ATTR_TYPE);
        String typeStr =
                type == null || "".equals(type.getValue().trim()) ? DEFAULT_NODE_NAME : type.getValue();

        // props
        List<Element> propsEleList = nodeEle.elements(ELE_PROPS);
        Map<String, String> propMap = new HashMap<>();
        if (propsEleList.size() == 0){}
        else if(propsEleList.size() > 1) {throw new ParseFlowException("Node ["+nodeId.getValue()+"] has more than 1 '<props>' tag.");}
        else {
            propMap = getPropsMap(propsEleList.get(0), "Node", nodeId.getValue());
        }

        // extendedInParams
        Map<String, ParamTypeInfo> extendedInParamsMap = getExtendedParams(nodeEle.elements(ELE_EXTENDED_IN_PARAMS), nodeId.getValue());

        // extendedOutParams
        Map<String, ParamTypeInfo> extendedOutParamsMap = getExtendedParams(nodeEle.elements(ELE_EXTENDED_OUT_PARAMS), nodeId.getValue());

        FlowNode node = FlowNodeBuilder.buildNode(typeStr, nodeId.getValue(), propMap, extendedInParamsMap, extendedOutParamsMap);

        return node;
    }

    private Map<String, FlowLine> parseLines(List<Element> linesEle, Map<String, FlowNode> flowNodeMap) throws ParseFlowException {
        if (linesEle.size() == 0) {throw new ParseFlowException("No '<lines>' tag is found in the flow description XML file.");}
        else if (linesEle.size() > 1) {throw new ParseFlowException("More than one '<lines>' tag are found in the flow description XML file.");}

        Map<String, FlowLine> linesMap = new HashMap<>();
        Set<String> pipelineIdSet = new HashSet<>();
        List<Element> lineEleList = linesEle.get(0).elements(ELE_LINE);
        for (Element lineEle: lineEleList) {
            // id
            Attribute lineIdAttr = lineEle.attribute(ATTR_ID);
            if (lineIdAttr == null) {throw new ParseFlowException("<line> tag must have attrbute 'id'.");}
            String lineId = lineIdAttr.getValue();
            if (linesMap.containsKey(lineId)) {throw new ParseFlowException("Line id duplicated: " + lineId);}
            if (flowNodeMap.containsKey(lineId)) {throw new ParseFlowException("The line id is same as the node: " + lineId);}

            Attribute startNode = lineEle.attribute(ATTR_START_NODE);
            if (startNode == null) {throw new ParseFlowException("<line> tag must have attrbute 'startNode'.");}
            String startNodeId = startNode.getValue();
            if (! flowNodeMap.containsKey(startNodeId)) {
                throw new ParseFlowException("Line [" + lineId + "]'s startNode=" + startNodeId + " doesn't exist.");
            }

            Attribute endNode = lineEle.attribute(ATTR_END_NODE);
            if (endNode == null) {throw new ParseFlowException("<line> tag must have attrbute 'endNode'.");}
            String endNodeId = endNode.getValue();
            if (! flowNodeMap.containsKey(endNodeId)) {
                throw new ParseFlowException("Line [" + lineId + "]'s endNode=" + endNodeId + " doesn't exist.");
            }


            List<Pipeline> pipelines = parsePipeline(lineEle, flowNodeMap, linesMap, pipelineIdSet);

            FlowLine newLine = new FlowLine(lineId, startNodeId, endNodeId, pipelines);
            linesMap.put(lineId, newLine);
        }

        return linesMap;
    }

    private List<Pipeline> parsePipeline(Element lineEle, Map<String, FlowNode> flowNodeMap, Map<String, FlowLine> linesMap, Set<String> pipelineIdSet) throws ParseFlowException{
        List<Pipeline> pipelines = new ArrayList<>();
        String lineId = lineEle.attribute(ATTR_ID).getValue();
        List<Element> piplinesEle = lineEle.elements(ELE_PIPELINES);
        if (piplinesEle.size() == 0) {
            // pass
        }
        else if (piplinesEle.size() > 1) {throw new ParseFlowException("Line ["+lineId+"] has more than 1 <pipelines> tag.");}
        else {
            List<Element> pipelineEleList = piplinesEle.get(0).elements(ELE_PIPELINE);
            for(Element pipelineEle: pipelineEleList) {
                // id
                Attribute idAttr = pipelineEle.attribute(ATTR_ID);
                if (idAttr == null) {throw new ParseFlowException("Line ["+lineId+"] has pipeline without attribute 'id'");}
                String id = idAttr.getValue();
                if (flowNodeMap.containsKey(id) && linesMap.containsKey(id) && pipelineIdSet.contains(id)) {
                    throw new ParseFlowException("Duplicated id: " + id);
                }

                // type
                Attribute type = pipelineEle.attribute(ATTR_TYPE);
                String typeStr =
                        type == null || "".equals(type.getValue().trim()) ? DEFAULT_NODE_PIPELINE : type.getValue();

                // startParam
                Attribute startParam = pipelineEle.attribute(ATTR_START_PARAM);
                if (startParam == null) {throw new ParseFlowException("Line ["+lineId+"] has pipeline without attribute 'startParam'");}

                // endParam
                Attribute endParam = pipelineEle.attribute(ATTR_END_PARAM);
                if (endParam == null) {throw new ParseFlowException("Line ["+lineId+"] has pipeline without attribute 'endParam'");}

                // props
                List<Element> propsEleList = pipelineEle.elements(ELE_PROPS);
                Map<String, String> propMap = new HashMap<>();
                if (propsEleList.size() == 0){}
                else if(propsEleList.size() > 1) {throw new ParseFlowException("Pipeline ["+id+"] has more than 1 '<props>' tag.");}
                else {
                    propMap = getPropsMap(propsEleList.get(0), "Pipeline", id);
                }

                pipelineIdSet.add(id);

                Pipeline newPipeline = PipelineBuilder.buildPipeline(typeStr, id, startParam.getValue(), endParam.getValue(), propMap);
                pipelines.add(newPipeline);
            }
        }
        return pipelines;
    }

    private Map<String, NodeConnections> parseNodeConnections(Map<String, FlowNode> nodeMap, Map<String, FlowLine> lineMap) throws ParseFlowException{
        Map<String, NodeConnections> nodeConnectionsMap = new HashMap<>();
        for (Map.Entry<String, FlowLine> lineEntry: lineMap.entrySet()) {
            FlowLine line = lineEntry.getValue();
            // startNode
            FlowNode startNode = nodeMap.get(line.getStartNodeId());
            if (!nodeConnectionsMap.containsKey(startNode.getId())) {
                List<FlowLine> outLines = new ArrayList<>();
                outLines.add(line);
                NodeConnections c = new NodeConnections(startNode, new ArrayList<>(), outLines);
                nodeConnectionsMap.put(startNode.getId(), c);
            }
            else {
                nodeConnectionsMap.get(startNode.getId()).getOutlines().add(line);
            }
            // endNode
            FlowNode endNode = nodeMap.get(line.getEndNodeId());
            if (!nodeConnectionsMap.containsKey(endNode.getId())) {
                List<FlowLine> inLines = new ArrayList<>();
                inLines.add(line);
                NodeConnections c = new NodeConnections(endNode, inLines, new ArrayList<>());
                nodeConnectionsMap.put(endNode.getId(), c);
            }
            else {
                nodeConnectionsMap.get(endNode.getId()).getInlines().add(line);
            }
        }
        return nodeConnectionsMap;
    }

    private Map<String, String> getPropsMap(Element propsEle, String parentName, String parentId) throws ParseFlowException{
        Map<String, String> propMap = new HashMap<>();

        List<Element> propList = propsEle.elements(ELE_PROP);
        for (Element prop: propList) {
            Attribute propName = prop.attribute(ATTR_NAME);
            String propValue = prop.getTextTrim();
            propValue = propValue.replace("<br/>", "\n");
            if (propName == null || propValue == null) {
                throw new ParseFlowException(parentName + " [" + parentId + "] <prop> tag must have attribute 'name' and 'value'.");
            }
            if (propMap.containsKey(propName.getValue())) {
                throw new ParseFlowException(parentName + " [" + parentId + "] have more than 1 <prop> tag with the same attribute name='" + propName.getValue() +"'.");
            }
            propMap.put(propName.getValue(), propValue);
        }
        return propMap;
    }

    private Map<String, ParamTypeInfo> getExtendedParams(List<Element> extendedEleList, String nodeId) throws ParseFlowException{
        Map<String, ParamTypeInfo> extendedMap = new HashMap<>();
        if (extendedEleList.size() == 0){
            // pass
        }
        else if(extendedEleList.size() > 1) {throw new ParseFlowException("Node ["+nodeId+"] has more than 1 '<extendedInParams>' tag.");}
        else {
            List<Element> paramList = extendedEleList.get(0).elements(ELE_PARAM);
            for (Element param: paramList) {
                Attribute paramName = param.attribute(ATTR_NAME);
                Attribute paramClass = param.attribute(ATTR_CLASS);
                Attribute paramRequired = param.attribute(ATTR_REQUIRED);
                boolean paramRequiredBool =
                        paramRequired == null ? true : Boolean.valueOf(paramRequired.getValue());
                if (paramName == null || paramClass == null) {
                    throw new ParseFlowException("Node [" + nodeId + "] <param> tag must have attribute 'name' and 'class'.");
                }
                if (extendedMap.containsKey(paramName.getValue())) {
                    throw new ParseFlowException("Node [" + nodeId + "] have more than 1 <param> tags with the same attribute name='" + paramName.getValue() +"'.");
                }
                Class clazz;
                try {
                    clazz = Class.forName(paramClass.getValue());
                }catch (ClassNotFoundException cnfe) {
                    throw new ParseFlowException("Node [" + nodeId + "] <param> tag's attribute class=" + paramClass.getValue() + " can't be converted to Java Class.");
                }
                extendedMap.put(paramName.getValue(), new ParamTypeInfo(clazz, paramRequiredBool));
            }
        }
        return extendedMap;
    }
}
