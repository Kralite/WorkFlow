package com.kralite.workflow.manager;

import com.kralite.workflow.common.FlowLine;
import com.kralite.workflow.common.FlowNode;
import com.kralite.workflow.common.NodeConnections;
import com.kralite.workflow.common.RunningFlowLine;
import com.kralite.workflow.parser.FlowParser;
import com.kralite.workflow.exception.NoSuchLineIdException;
import com.kralite.workflow.exception.NoSuchNodeIdException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/28.
 */
public interface FlowManager {
    void init(FlowParser parser);
    void setStartNode(FlowNode startNode);
    void addNodeConnections(String nodeId, NodeConnections nodeConnections);
    void addLine(String lineId, FlowLine flowLine);
    FlowNode getStartNode();
    List<FlowLine> getNodeOutLines(String flowNodeId);
    List<FlowLine> getNodeInLines(String flowNodeId);
    FlowNode getLineStartNode(String flowLineId);
    FlowNode getLineEndNode(String flowLineId);
    FlowNode getLineStartEndNode(String startOrEnd, String flowLineId);
}
