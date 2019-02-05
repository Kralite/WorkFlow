package com.kralite.workflow.exception;

/**
 * Created by ChenDaLin on 2019/2/3.
 */
public class ParseFlowException extends Exception{
    public ParseFlowException(String msg) {
        super(msg);
    }

    public ParseFlowException(String msg, Throwable th) {
        super(msg, th);
    }
}
