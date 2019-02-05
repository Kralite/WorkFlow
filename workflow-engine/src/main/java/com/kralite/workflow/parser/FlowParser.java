package com.kralite.workflow.parser;

import com.kralite.workflow.common.FlowLine;
import com.kralite.workflow.common.NodeConnections;
import com.kralite.workflow.exception.ParseFlowException;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public interface FlowParser {
    ParseStruct parse(String flowDescriptionXml) throws ParseFlowException;
}
