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
@NodeTypeName("AddNode")
@InParamTypes({
        @ParamType(paramName = "value1", paramClass = Double.class),
        @ParamType(paramName = "value2", paramClass = Double.class)
})
@OutParamTypes({
        @ParamType(paramName = "result", paramClass = Double.class)
})
public class AddNode extends FlowNode{
    @Override
    protected Map<String, Object> execute(Map<String, Object> inParams) {
        Double v1 = (Double)inParams.get("value1");
        Double v2 = (Double)inParams.get("value2");
        Double result = v1+v2;
        Map<String, Object> resultMap = new ConcurrentHashMap<>();
        resultMap.put("result", result);
        return resultMap;
    }
}
