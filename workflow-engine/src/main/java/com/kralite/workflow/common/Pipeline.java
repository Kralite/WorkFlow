package com.kralite.workflow.common;

import com.kralite.workflow.exception.RunningFlowException;

/**
 * Created by Kralite on 2019/1/20.
 */
public class Pipeline extends AbstractPipeline{
    @Override
    protected Object transform(Object startParam){
        return startParam;
    }

}
