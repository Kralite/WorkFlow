package com.kralite.workflow.test.node;

import com.kralite.workflow.annotation.*;
import com.kralite.workflow.common.FlowNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenDaLin on 2019/1/31.
 */
@NodeTypeName("NumNode")
@InParamTypes({})
@OutParamTypes({
        @ParamType(paramName = "value", paramClass = Double.class)
})
@PropNames({
        @PropName("value")
})
public class NumNode extends FlowNode{
    @Override
    protected Map<String, Object> execute(Map<String, Object> inParams) {
        Map<String, Object> result = new HashMap<>();
        result.put("value", Double.valueOf(props.getOrDefault("value", "0")));
        return result;
    }
}
