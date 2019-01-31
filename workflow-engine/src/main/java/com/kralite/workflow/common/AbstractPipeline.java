package com.kralite.workflow.common;

import com.kralite.workflow.exception.RunningFlowException;

/**
 * Created by Kralite on 2019/1/20.
 */
public abstract class AbstractPipeline {
    protected String id;
    protected String pipelineTypeName;
    protected String startParamName;
    protected Class startParamType;
    protected String endParamName;
    protected Class endParamType;

    abstract protected Object transform(Object startParam);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartParamName() {
        return startParamName;
    }

    public void setStartParamName(String startParamName) {
        this.startParamName = startParamName;
    }

    public Class getStartParamType() {
        return startParamType;
    }

    public void setStartParamType(Class startParamType) {
        this.startParamType = startParamType;
    }

    public String getEndParamName() {
        return endParamName;
    }

    public void setEndParamName(String endParamName) {
        this.endParamName = endParamName;
    }

    public Class getEndParamType() {
        return endParamType;
    }

    public void setEndParamType(Class endParamType) {
        this.endParamType = endParamType;
    }
}
