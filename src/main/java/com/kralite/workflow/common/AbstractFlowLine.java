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

    protected Map<String, Object> startParams;
    protected Map<String, Object> endParams;
    protected LineStatus status;

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

    public Map<String, Object> getStartParams() {
        return startParams;
    }

    public void setStartParams(Map<String, Object> startParams) {
        this.startParams = startParams;
    }

    public Map<String, Object> getEndParams() {
        return endParams;
    }

    public void setEndParams(Map<String, Object> endParams) {
        this.endParams = endParams;
    }

    public LineStatus getStatus() {
        return status;
    }

    public void setStatus(LineStatus status) {
        this.status = status;
    }
}
