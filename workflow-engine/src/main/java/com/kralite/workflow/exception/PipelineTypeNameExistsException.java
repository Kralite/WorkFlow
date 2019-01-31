package com.kralite.workflow.exception;

/**
 * Created by ChenDaLin on 2019/1/30.
 */
public class PipelineTypeNameExistsException extends Exception {
    public PipelineTypeNameExistsException(String pipelineTypeName) {
        super("Pipeline type '" + pipelineTypeName + "' already exists.");
    }
}
