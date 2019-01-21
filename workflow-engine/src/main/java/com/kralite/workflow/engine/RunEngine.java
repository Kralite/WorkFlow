package com.kralite.workflow.engine;

import com.kralite.workflow.common.*;
import com.kralite.workflow.datamanager.DataManager;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public class RunEngine {

    private DataManager dataManager;
    private FlowParser flowParser;

    private Queue<RunningFlowNode> runningQueue;
    private Map<String, NodeConnections> flowMap;

    public void loadFlow(){
        flowMap = flowParser.parse();
    }

    /**
     * 同步运行
     */
    public void run(){
        String startNodeId = "start";
        FlowNode startFN = flowMap.get(startNodeId).getFlowNode();


    }

    public void BFS(FlowNode flowNode){
        RunningFlowNode rn = dataManager.getRuningFlowNode(flowNode);
        rn.execute();
        List<FlowLine> outFLlist = flowMap.get(rn.getFlowNode().getId()).getOutlines();
        for (FlowLine outFL: outFLlist) {
            FlowNode endFN = outFL.getEndNode();

        }

    }

    /**
     * 异步运行
     */
    public void runAsyn(){

    }


    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
