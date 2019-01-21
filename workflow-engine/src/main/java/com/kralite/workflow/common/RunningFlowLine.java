package com.kralite.workflow.common;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public class RunningFlowLine {
    private FlowLine flowLine;
    private Map<String, Object> startParams;
    private Map<String, Object> endParams;
    private LineStatus status;

    public RunningFlowLine(FlowLine flowLine, Map<String, Object> startParams, Map<String, Object> endParams, LineStatus status) {
        this.flowLine = flowLine;
        this.startParams = startParams;
        this.endParams = endParams;
        this.status = status;
    }

    public FlowLine getFlowLine() {
        return flowLine;
    }

    public void setFlowLine(FlowLine flowLine) {
        this.flowLine = flowLine;
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
