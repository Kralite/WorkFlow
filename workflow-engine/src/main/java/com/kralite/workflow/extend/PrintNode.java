package com.kralite.workflow.extend;

import com.kralite.workflow.annotation.*;
import com.kralite.workflow.common.FlowNode;

import java.util.Date;
import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */

@NodeTypeName("PrintNode")
@PropNames({
        @PropName(value = "defaultMsg", required = false)
})
@InParamTypes({
        @ParamType(paramName = "printMsg", paramClass = Object.class, required = false),
        @ParamType(paramName = "time", paramClass = Date.class, required = false)
})
@OutParamTypes({
        @ParamType(paramName = "printMsg", paramClass = Object.class, required = false)
})
public class PrintNode extends FlowNode{
    @Override
    public Map<String, Object> execute(Map<String, Object> inParams){
//        System.out.println(id + " " + nodeTypeName + inParamTypeMap.get("printMsg").getParamClass());
        if (props.containsKey("defaultMsg")){
            System.out.println("[PrintNode - " + id + "] property message: " + props.get("defaultMsg"));
        }
        if (inParams.containsKey("printMsg")){
            System.out.println("[PrintNode - " + id + "] param message: " + inParams.get("printMsg"));
        }
        return null;
    }

}
