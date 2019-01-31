package com.kralite.workflow.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public class NodeConnections {
    private FlowNode flowNode;
    private List<FlowLine> inlines;
    private List<FlowLine> outlines;

    public NodeConnections(FlowNode flowNode, List<FlowLine> inlines, List<FlowLine> outlines) {
        this.flowNode = flowNode;
        if (inlines == null) {
            inlines = new ArrayList<>();
        }
        this.inlines = inlines;
        if (outlines == null) {
            outlines = new ArrayList<>();
        }
        this.outlines = outlines;
    }

    public FlowNode getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(FlowNode flowNode) {
        this.flowNode = flowNode;
    }

    public List<FlowLine> getInlines() {
        return inlines;
    }

    public void setInlines(List<FlowLine> inlines) {
        this.inlines = inlines;
    }

    public List<FlowLine> getOutlines() {
        return outlines;
    }

    public void setOutlines(List<FlowLine> outlines) {
        this.outlines = outlines;
    }
}
