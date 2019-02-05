package com.kralite.workflow.exception;

/**
 * Created by Kralite on 2019/1/20.
 */
public class InitFlowException extends RuntimeException{
    public InitFlowException(String msg) {
        super(msg);
    }

    public InitFlowException(String msg, Throwable th) {
        super(msg, th);
    }
}
