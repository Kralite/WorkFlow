package com.kralite.workflow.common;

import java.util.List;

/**
 * Created by Kralite on 2019/1/20.
 */
public class FlowLine extends AbstractFlowLine {
    public FlowLine(String id, String startNodeId, String endNodeId, List<Pipeline> pipelines) {
        this.id = id;
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
        this.pipelines = pipelines;
    }
}
