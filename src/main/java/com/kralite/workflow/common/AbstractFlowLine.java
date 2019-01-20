package com.kralite.workflow.common;

import java.util.List;
import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */
public abstract class AbstractFlowLine {
    protected String id;
    protected FlowNode startNode;
    protected FlowNode endNode;
    protected boolean isParallel;
    protected List<Pipeline> pipelines;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FlowNode getStartNode() {
        return startNode;
    }

    public void setStartNode(FlowNode startNode) {
        this.startNode = startNode;
    }

    public FlowNode getEndNode() {
        return endNode;
    }

    public void setEndNode(FlowNode endNode) {
        this.endNode = endNode;
    }

    public boolean isParallel() {
        return isParallel;
    }

    public void setParallel(boolean parallel) {
        isParallel = parallel;
    }

    public List<Pipeline> getPipelines() {
        return pipelines;
    }

    public void setPipelines(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
    }

}
