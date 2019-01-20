package com.kralite.workflow.engine;

import com.kralite.workflow.common.NodeConnections;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public interface FlowParser {
    public Map<String, NodeConnections> parse();
}
