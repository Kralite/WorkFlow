package com.kralite.workflow.common;

import com.kralite.workflow.annotation.InParamTypes;
import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.annotation.OutParamTypes;
import com.kralite.workflow.annotation.ParamType;
import com.kralite.workflow.exception.InitFlowException;

import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */

public class FlowNode extends AbstractFlowNode{

    protected FlowNode(){}

    public static FlowNode getNode(String id, Map props, Map inParamTypeMap, Map outParamTypeMap){
        FlowNode node = new FlowNode();
        node.setId(id);
        node.setProps(props);
        node.setInParamTypeMap(inParamTypeMap);
        node.setOutParamTypeMap(outParamTypeMap);
        node.initNode();
        return node;
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> inParams){return null;}

    public void initNode(){
        initAnnotaion_NodeTypeParam();
        initAnnotaion_InParamTypes();
        initAnnotaion_OutParamTypes();
    }

    private void initAnnotaion_NodeTypeParam() {
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(NodeTypeName.class);
        if(isExist) {
            NodeTypeName a = (NodeTypeName)(clazz.getAnnotation(NodeTypeName.class));
            nodeTypeName = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion NodeTypeParam="+a.value());
        }
        else {
            throw new InitFlowException(
                    "FlowNode ["+nodeLogName()+"] init failed, annotation NodeTypeName can't be null");
        }
    }

    private void initAnnotaion_InParamTypes() {
        if (inParamTypeMap == null) {
            throw new InitFlowException(
                    "FlowNode ["+nodeLogName()+"] init failed, its inParamTypeMap is null" );
        }
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(InParamTypes.class);
        if(isExist) {
            InParamTypes a = (InParamTypes)(clazz.getAnnotation(InParamTypes.class));
            ParamType[] inParamTypes = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion InParamTypes:");
            for (ParamType p: inParamTypes) {
                inParamTypeMap.put(p.paramName(), new ParamTypeInfo(p.paramClass(), p.required()));
                logger.info("\tparamName="+p.paramName()+", paramClass="+p.paramClass()+", required="+p.required());
            }
        }
    }

    private void initAnnotaion_OutParamTypes() {
        if (outParamTypeMap == null) {
            throw new InitFlowException(
                    "FlowNode ["+nodeLogName()+"] init failed, its outParamTypeMap is null" );
        }
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(OutParamTypes.class);
        if(isExist) {
            OutParamTypes a = (OutParamTypes)(clazz.getAnnotation(OutParamTypes.class));
            ParamType[] outParamTypes = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion OutParamTypes:");
            for (ParamType p: outParamTypes) {
                outParamTypeMap.put(p.paramName(), new ParamTypeInfo(p.paramClass(), p.required()));
                logger.info("\tparamName="+p.paramName()+", paramClass="+p.paramClass()+", required="+p.required());
            }
        }
    }

    public String nodeLogName(){
        return nodeTypeName+" - "+id;
    }
}
