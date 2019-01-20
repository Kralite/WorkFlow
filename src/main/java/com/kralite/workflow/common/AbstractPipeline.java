package com.kralite.workflow.common;

import com.kralite.workflow.exception.RunningFlowException;

/**
 * Created by Kralite on 2019/1/20.
 */
public abstract class AbstractPipeline {
    private String id;
    private String startParamName;
    private Class startParamType;
    private String endParamName;
    private Class endParamType;

    public Object executeTransform(Object startParam) throws RunningFlowException{
        validateParamType(startParamType, startParam, "start");
        Object endParam = transform(startParam);
        validateParamType(endParamType, endParam, "end");
        return endParam;
    }

    abstract protected Object transform(Object startParam);

    protected void validateParamType(Class paramType, Object param, String step) throws RunningFlowException {
        if (param == null) {
            throw new RunningFlowException(
                    "Pipeline's "+step+"Param can't be null. Pipeline [" + id + "] "+step+"Param="+param);
        }
        if (!paramType.isInstance(param)) {
            throw new RunningFlowException(
                    step+"Param is not instance of class " + paramType.getCanonicalName()+". "+step+"Param is instance of class"+param.getClass().getCanonicalName());
        }

    }

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
