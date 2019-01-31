package com.kralite.workflow.manager;

import com.kralite.workflow.common.*;
import com.kralite.workflow.exception.NoSuchLineIdException;
import com.kralite.workflow.exception.NoSuchNodeIdException;
import com.kralite.workflow.parser.FlowParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/29.
 */
public class DefaultFlowManager implements FlowManager {
    private Map<String, NodeConnections> nodeMap = new ConcurrentHashMap<String, NodeConnections>();
    private Map<String, FlowLine> lineMap = new ConcurrentHashMap<String, FlowLine>();

    private FlowNode startNode;

    public void init(FlowParser parser) {

        // todo
    }

    private void parse(){
        // todo: 校验startNode有且仅有一个
        // todo: 生成pipeline
        // todo: 如果没有in/outLine，返回的应该是空List，不是null
        // todo：检测是否每一个必须参数都有pipeline连接了
    }

    public void setStartNode(FlowNode startNode) {
        this.startNode = startNode;
    }

    public FlowNode getStartNode(){
        return startNode;
    }

    public void addNodeConnections(String nodeId, NodeConnections nodeConnections) {
        nodeMap.put(nodeId, nodeConnections);
    }

    public void addLine(String lineId, FlowLine flowLine) {
        lineMap.put(lineId, flowLine) ;
    }

    /**
     * 获得某节点的outline
     * @param flowNodeId
     * @return
     */
    public List<FlowLine> getNodeOutLines(String flowNodeId) {
        if (flowNodeId == null || !nodeMap.containsKey(flowNodeId)) {
            throw new NoSuchNodeIdException(flowNodeId);
        }
        return nodeMap.get(flowNodeId).getOutlines();
    }

    public List<FlowLine> getNodeInLines(String flowNodeId) {
        if (flowNodeId == null || !nodeMap.containsKey(flowNodeId)) {
            throw new NoSuchNodeIdException(flowNodeId);
        }
        return nodeMap.get(flowNodeId).getInlines();
    }

    public FlowNode getLineStartNode(String flowLineId) {
        return getLineStartEndNode("start", flowLineId);
    }

    public FlowNode getLineEndNode(String flowLineId) {
        return getLineStartEndNode("end", flowLineId);
    }

    public FlowNode getLineStartEndNode(String startOrEnd, String flowLineId) {
        if (flowLineId==null || !lineMap.containsKey(flowLineId)) {
            throw new NoSuchLineIdException(flowLineId);
        }
        FlowLine line = lineMap.get(flowLineId);
        String nodeId;
        if (startOrEnd.equals("start")) {
            nodeId = line.getStartNodeId();
        }
        else {
            nodeId = line.getEndNodeId();
        }

        if (nodeId == null) {
            return null;
        }
        if (!nodeMap.containsKey(nodeId)) {
            throw new NoSuchNodeIdException(nodeId);
        }
        return nodeMap.get(nodeId).getFlowNode();
    }
}
