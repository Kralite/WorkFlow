package com.kralite.workflow.engine;

import com.kralite.workflow.common.*;
import com.kralite.workflow.manager.DataManager;
import com.kralite.workflow.manager.FlowManager;
import com.kralite.workflow.parser.FlowParser;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public class RunEngine {

    private DataManager dataManager;
    private FlowManager flowManager;
    private FlowParser flowParser;

    public void loadFlow(){
        flowManager.init(flowParser);
    }

    /**
     * 同步运行
     */
    public void run(){
        BFS();
    }

    public void BFS(){
        Queue<String> nodeQ = new ConcurrentLinkedQueue<String>();

        FlowNode startFN = flowManager.getStartNode();
        dataManager.buildRunningFlowNode(startFN);
        nodeQ.add(startFN.getId());

        while (!nodeQ.isEmpty()) {
            String curNodeId = nodeQ.remove();
            RunningFlowNode curRN = dataManager.getRunningFlowNode(curNodeId);
            if (isNodeReady(curRN)) {
                processNode(curRN);
                List<FlowLine> outlineList = flowManager.getNodeOutLines(curNodeId);
                for (FlowLine outline: outlineList) {
                    RunningFlowLine outRL = dataManager.getRunningFlowLine(outline.getId());
                    if (outRL == null) {
                        outRL = dataManager.buildRunningFlowLine(outline);
                    }
                    if (outRL.getStatus().isFinished()) { continue; }
                    FlowNode nextFN = flowManager.getLineEndNode(outline.getId());
                    RunningFlowNode nextRN = dataManager.buildRunningFlowNode(nextFN);
                    processLine(outline, curRN, nextRN);
                    if (! nodeQ.contains(nextFN.getId())) {
                        nodeQ.add(nextFN.getId());
                    }
                }
            }
        }
    }

    /**
     * 异步运行
     */
    public void runAsyn(){

    }

    private void processNode(RunningFlowNode rn) {
        rn.execute();
        for (Map.Entry<String, Object> outParamEntry: rn.getOutParams().entrySet()) {
            dataManager.setNodeOutParam(rn.getFlowNode().getId(), outParamEntry.getKey(), outParamEntry.getValue());
        }
        dataManager.addNodeRunRecord(rn.getFlowNode().getId());
    }

    private void processLine(FlowLine line, RunningFlowNode prevRN, RunningFlowNode nextRN){
        List<Pipeline> pipelineList = line.getPipelines();
        Map<String, Object> prevOutParams = prevRN.getOutParams();
        for (Pipeline pipeline: pipelineList) {
            String startParamName = pipeline.getStartParamName();
            String endParamName = pipeline.getEndParamName();
            // pipeline的两端都能找到字段名相同的参数才处理，否则pass
            if (prevRN.getFlowNode().getOutParamTypeMap().containsKey(startParamName) &&
                    nextRN.getFlowNode().getInParamTypeMap().containsKey(endParamName)){
                Object lineStartParam = prevOutParams.get(startParamName);
                Object lineEndParam = pipeline.executeTransform(lineStartParam);
                dataManager.setNodeInParam(nextRN.getFlowNode().getId(), endParamName, lineEndParam);
            }
        }
        dataManager.setLineFinished(line.getId(), true);
    }


    public boolean isNodeReady(RunningFlowNode rn){
        List<FlowLine> inFLs = flowManager.getNodeInLines(rn.getFlowNode().getId());
        for (FlowLine inFL: inFLs) {
            RunningFlowLine inRL = dataManager.getRunningFlowLine(inFL.getId());
            if (! inRL.getStatus().isFinished()) {return false;}
        }

        for(Map.Entry<String, ParamTypeInfo> paramTypeEntry: rn.getFlowNode().getInParamTypeMap().entrySet()) {
            String paramName = paramTypeEntry.getKey();
            ParamTypeInfo paramTypeInfo = paramTypeEntry.getValue();
            // 参数是必需的，但是入参中没有该参数或该参数值为null，就不ready
            if (paramTypeInfo.isRequired() && !rn.getInParams().containsKey(paramName)) {
                return false;
            }
        }
        return true;

    }


    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public FlowManager getFlowManager() {
        return flowManager;
    }

    public void setFlowManager(FlowManager flowManager) {
        this.flowManager = flowManager;
    }

    public FlowParser getFlowParser() {
        return flowParser;
    }

    public void setFlowParser(FlowParser flowParser) {
        this.flowParser = flowParser;
    }
}
