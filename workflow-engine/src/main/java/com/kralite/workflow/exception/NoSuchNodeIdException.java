package com.kralite.workflow.exception;

/**
 * Created by ChenDaLin on 2019/1/28.
 */
public class NoSuchNodeIdException extends RunningFlowException {

    public NoSuchNodeIdException(String nodeId) {
        super("nodeId=" + nodeId + " is not defined in the flow map");
    }


}
