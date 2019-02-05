package com.kralite.workflow.parser;

import com.kralite.workflow.common.FlowLine;
import com.kralite.workflow.common.FlowNode;
import com.kralite.workflow.common.NodeConnections;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/2/3.
 */
public class ParseStruct {
    public Map<String, NodeConnections> nodeMap;
    public Map<String, FlowLine> lineMap;
    public FlowNode startNode;

    public ParseStruct() {}

    public ParseStruct(Map<String, NodeConnections> nodeMap, Map<String, FlowLine> lineMap, FlowNode startNode) {
        this.nodeMap = nodeMap;
        this.lineMap = lineMap;
        this.startNode = startNode;
    }
}
