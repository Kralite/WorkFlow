package com.kralite.workflow.manager;

import com.kralite.workflow.common.*;
import com.kralite.workflow.exception.RunningFlowException;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public class LocalMemoryDataManager implements DataManager{

    private Map<String, RunningFlowNode> rnMap = new ConcurrentHashMap<String, RunningFlowNode>();
    private Map<String, RunningFlowLine> rlMap = new ConcurrentHashMap<String, RunningFlowLine>();

    public synchronized RunningFlowNode buildRunningFlowNode(FlowNode flowNode) {
        if (rnMap.containsKey(flowNode.getId())) {
            return rnMap.get(flowNode.getId());
        }
        RunningFlowNode rn = new RunningFlowNode(
                flowNode,
                new ConcurrentHashMap<String, Object>(),
                new ConcurrentHashMap<String, Object>(),
                new NodeStatus()
        );
        rnMap.put(flowNode.getId(), rn);
        return rn;
    }

    public RunningFlowNode getRunningFlowNode(String nodeId) {
        if (!rnMap.containsKey(nodeId)) { return null; }
        return rnMap.get(nodeId);
    }

    @Override
    public RunningFlowLine buildRunningFlowLine(FlowLine flowLine) {
        if (rlMap.containsKey(flowLine.getId())) {
            return rlMap.get(flowLine.getId());
        }
        RunningFlowLine runningFL = new RunningFlowLine(
                flowLine,
                new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>(),
                new LineStatus(false));
        rlMap.put(flowLine.getId(), runningFL);
        return runningFL;
    }

    @Override
    public RunningFlowLine getRunningFlowLine(String lineId) {
        if (!rlMap.containsKey(lineId)) { return null;}
        return rlMap.get(lineId);
    }

    public void setNodeInParam(String nodeId, String paramName, Object param) {
        nodeExists(nodeId);
        rnMap.get(nodeId).getInParams().put(paramName, param);
    }

    public void setNodeOutParam(String nodeId, String paramName, Object param) {
        nodeExists(nodeId);
        rnMap.get(nodeId).getOutParams().put(paramName, param);
    }

    public void setLineFinished(String lineId, boolean finished) {
        lineExists(lineId);
        rlMap.get(lineId).getStatus().setFinished(finished);
    }

    public void addNodeRunRecord(String nodeId) {
        nodeExists(nodeId);
        rnMap.get(nodeId).getStatus().addRunRecord(new Date());
    }

    private void nodeExists(String nodeId) {
        if (!rnMap.containsKey(nodeId)) {
            throw new RunningFlowException("nodeId=" + nodeId + " doesn't exist in the Data Storage");
        }
    }

    private void lineExists(String lineId) {
        if (!rlMap.containsKey(lineId)) {
            throw new RunningFlowException("lineId=" + lineId + " doesn't exist in the Data Storage");
        }
    }
}
