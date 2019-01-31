package com.kralite.workflow.common;

import java.util.List;
import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */
public abstract class AbstractFlowLine {
    protected String id;
    protected String startNodeId;
    protected String endNodeId;
    protected boolean isParallel;
    protected List<Pipeline> pipelines;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId;
    }

    public String getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(String endNodeId) {
        this.endNodeId = endNodeId;
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
