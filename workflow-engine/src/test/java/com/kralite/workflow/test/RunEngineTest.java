package com.kralite.workflow.test;

import com.kralite.workflow.common.*;
import com.kralite.workflow.engine.RunEngine;
import com.kralite.workflow.manager.DataManager;
import com.kralite.workflow.manager.FlowManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ChenDaLin on 2019/1/29.
 */
public class RunEngineTest extends BaseTest {
    @Autowired
    RunEngine runEngine;

    @Autowired
    DataManager dataManager;

    @Autowired
    FlowManager flowManager;

    @Test
    public void testRun(){
        mockFlow_add();
        runEngine.run();
    }

    /**
     * 节点间没有数据传递，单纯的打印。期待的结果是按 start-0-1-2-3-4 的顺序打印节点
     */
    private void mockFlow_Print(){
        FlowNode startNode = FlowNodeBuilder.buildNode("PrintNode", "node-start", null);
        startNode.putProperty("defaultMsg", "Start Node test");
        FlowNode[] nodes = new FlowNode[5];
        for (int i=0; i<nodes.length; ++i) {
            nodes[i] = FlowNodeBuilder.buildNode("PrintNode", "node-"+i, null);
            nodes[i].putProperty("defaultMsg", "Node id " + i);
        }

        FlowLine[] lines = new FlowLine[7];
        lines[0] = new FlowLine("line-0", startNode.getId(), nodes[0].getId(), false, new ArrayList<Pipeline>());
        lines[1] = new FlowLine("line-1", startNode.getId(), nodes[1].getId(), false, new ArrayList<Pipeline>());
        lines[2] = new FlowLine("line-2", startNode.getId(), nodes[2].getId(), false, new ArrayList<Pipeline>());
        lines[3] = new FlowLine("line-3", nodes[0].getId(), nodes[3].getId(), false, new ArrayList<Pipeline>());
        lines[4] = new FlowLine("line-4", nodes[1].getId(), nodes[3].getId(), false, new ArrayList<Pipeline>());
        lines[5] = new FlowLine("line-5", nodes[1].getId(), nodes[4].getId(), false, new ArrayList<Pipeline>());
        lines[6] = new FlowLine("line-6", nodes[2].getId(), nodes[4].getId(), false, new ArrayList<Pipeline>());
        for (int i=0; i<lines.length; ++i) {
            flowManager.addLine(lines[i].getId(), lines[i]);
        }

        flowManager.setStartNode(startNode);
        NodeConnections c = new NodeConnections(startNode, new ArrayList<FlowLine>(), Arrays.asList(lines[0], lines[1], lines[2]));
        flowManager.addNodeConnections(startNode.getId(), c);

        NodeConnections c0 = new NodeConnections(nodes[0], Arrays.asList(lines[0]), Arrays.asList(lines[3]));
        flowManager.addNodeConnections(nodes[0].getId(), c0);

        NodeConnections c1 = new NodeConnections(nodes[1], Arrays.asList(lines[1]), Arrays.asList(lines[4], lines[5]));
        flowManager.addNodeConnections(nodes[1].getId(), c1);

        NodeConnections c2 = new NodeConnections(nodes[2], Arrays.asList(lines[2]), Arrays.asList(lines[6]));
        flowManager.addNodeConnections(nodes[2].getId(), c2);

        NodeConnections c3 = new NodeConnections(nodes[3], Arrays.asList(lines[3], lines[4]), new ArrayList<>());
        flowManager.addNodeConnections(nodes[3].getId(), c3);

        NodeConnections c4 = new NodeConnections(nodes[4], Arrays.asList(lines[5], lines[6]), new ArrayList<>());
        flowManager.addNodeConnections(nodes[4].getId(), c4);
    }

    private void mockFlow_add(){
        FlowNode startNode = FlowNodeBuilder.buildNode("PrintNode", "node-start", null);
        startNode.putProperty("defaultMsg", "Start Node test");
        FlowNode[] nodes = new FlowNode[6];
        nodes[0] = FlowNodeBuilder.buildNode("NumNode", "node-0", null);
        nodes[0].putProperty("value", Double.valueOf(2));
        nodes[1] = FlowNodeBuilder.buildNode("NumNode", "node-1", null);
        nodes[1].putProperty("value", Double.valueOf(3));
        nodes[2] = FlowNodeBuilder.buildNode("AddNode", "node-2", null);
        nodes[3] = FlowNodeBuilder.buildNode("NumNode", "node-3", null);
        nodes[3].putProperty("value", Double.valueOf(4.2));
        nodes[4] = FlowNodeBuilder.buildNode("AddNode", "node-4", null);
        nodes[5] = FlowNodeBuilder.buildNode("PrintNode", "node-5", null);

        FlowLine[] lines = new FlowLine[8];
        lines[0] = new FlowLine("line-0", startNode.getId(), nodes[0].getId(), false, new ArrayList<Pipeline>());
        lines[1] = new FlowLine("line-1", startNode.getId(), nodes[1].getId(), false, new ArrayList<Pipeline>());
        lines[2] = new FlowLine("line-2", startNode.getId(), nodes[3].getId(), false, new ArrayList<Pipeline>());
        Pipeline pipeline31 = PipelineBuilder.buildPipeline("DefaultPipeline", "line-3-pipe-1", "value", "value1");
        lines[3] = new FlowLine("line-3", nodes[0].getId(), nodes[2].getId(), false, Arrays.asList(pipeline31));
        Pipeline pipeline41 = PipelineBuilder.buildPipeline("DefaultPipeline", "line-4-pipe-1", "value", "value2");
        lines[4] = new FlowLine("line-4", nodes[1].getId(), nodes[2].getId(), false, Arrays.asList(pipeline41));
        Pipeline pipeline51 = PipelineBuilder.buildPipeline("DefaultPipeline", "line-5-pipe-1", "value", "value1");
        lines[5] = new FlowLine("line-5", nodes[3].getId(), nodes[4].getId(), false, Arrays.asList(pipeline51));
        Pipeline pipeline61 = PipelineBuilder.buildPipeline("DefaultPipeline", "line-6-pipe-1", "result", "value2");
        lines[6] = new FlowLine("line-6", nodes[2].getId(), nodes[4].getId(), false, Arrays.asList(pipeline61));
        Pipeline pipeline71 = PipelineBuilder.buildPipeline("DefaultPipeline", "line-7-pipe-1", "result", "printMsg");
        lines[7] = new FlowLine("line-7", nodes[4].getId(), nodes[5].getId(), false, Arrays.asList(pipeline71));
        for (int i=0; i<lines.length; ++i) {
            flowManager.addLine(lines[i].getId(), lines[i]);
        }

        flowManager.setStartNode(startNode);
        flowManager.addNodeConnections(startNode.getId(),
                new NodeConnections(startNode, new ArrayList<>(), Arrays.asList(lines[0], lines[1], lines[2])));
        flowManager.addNodeConnections(nodes[0].getId(),
                new NodeConnections(nodes[0], Arrays.asList(lines[0]), Arrays.asList(lines[3])));
        flowManager.addNodeConnections(nodes[1].getId(),
                new NodeConnections(nodes[1], Arrays.asList(lines[1]), Arrays.asList(lines[4])));
        flowManager.addNodeConnections(nodes[2].getId(),
                new NodeConnections(nodes[2], Arrays.asList(lines[3], lines[4]), Arrays.asList(lines[6])));
        flowManager.addNodeConnections(nodes[3].getId(),
                new NodeConnections(nodes[3], Arrays.asList(lines[2]), Arrays.asList(lines[5])));
        flowManager.addNodeConnections(nodes[4].getId(),
                new NodeConnections(nodes[4], Arrays.asList(lines[5], lines[6]), Arrays.asList(lines[7])));
        flowManager.addNodeConnections(nodes[5].getId(),
                new NodeConnections(nodes[5], Arrays.asList(lines[7]), Arrays.asList()));

    }
}