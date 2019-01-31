package com.kralite.workflow.test.node;

import com.kralite.workflow.annotation.InParamTypes;
import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.annotation.OutParamTypes;
import com.kralite.workflow.annotation.ParamType;
import com.kralite.workflow.common.FlowNode;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/31.
 */
@NodeTypeName("NumNode")
@InParamTypes({})
@OutParamTypes({
        @ParamType(paramName = "value", paramClass = Double.class)
})
public class NumNode extends FlowNode{
    @Override
    protected Map<String, Object> execute(Map<String, Object> inParams) {
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("value", props.getOrDefault("value", Double.valueOf(0)));
        return result;
    }
}
