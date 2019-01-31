package com.kralite.workflow.manager;

import com.kralite.workflow.common.*;

/**
 * Created by ChenDaLin on 2019/1/20.
 * 实时运行数据的管理，类似DAO层
 */
public interface DataManager {
    RunningFlowNode buildRunningFlowNode(FlowNode flowNode);

    RunningFlowNode getRunningFlowNode(String nodeId);

    RunningFlowLine buildRunningFlowLine(FlowLine flowLine);

    RunningFlowLine getRunningFlowLine(String lineId);

    void setNodeInParam(String nodeId, String paramName, Object param);

    void setNodeOutParam(String nodeId, String paramName, Object param);

    void setLineFinished(String lineId, boolean finished);

    void addNodeRunRecord (String nodeId);

}
