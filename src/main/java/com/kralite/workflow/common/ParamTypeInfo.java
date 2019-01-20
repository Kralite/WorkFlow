package com.kralite.workflow.common;

/**
 * Created by Kralite on 2019/1/20.
 */
public class ParamTypeInfo {
    private Class paramClass;
    private boolean required;

    public ParamTypeInfo(Class paramClass, boolean required){
        this.paramClass = paramClass;
        this.required = required;
    }

    public Class getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class paramClass) {
        this.paramClass = paramClass;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
