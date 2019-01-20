package com.kralite.workflow.datamanager;

import com.kralite.workflow.common.FlowNode;
import com.kralite.workflow.common.RunningFlowNode;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public interface DataManager {
    RunningFlowNode getRuningFlowNode(FlowNode flowNode);
}
