package com.kralite.workflow.common;

/**
 * Created by ChenDaLin on 2019/2/1.
 */
public class PropInfo {
    private boolean required;

    public PropInfo(boolean required){
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
