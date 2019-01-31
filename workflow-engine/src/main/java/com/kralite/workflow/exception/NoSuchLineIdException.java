package com.kralite.workflow.exception;

/**
 * Created by ChenDaLin on 2019/1/28.
 */
public class NoSuchLineIdException extends RunningFlowException{
    public NoSuchLineIdException(String lineId) {
        super("lineId=" + lineId + " is not defined in the flow map");
    }
}
