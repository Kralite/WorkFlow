package com.kralite.workflow.extend;

import com.kralite.workflow.annotation.InParamTypes;
import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.annotation.OutParamTypes;
import com.kralite.workflow.annotation.ParamType;
import com.kralite.workflow.common.FlowNode;

import java.util.Date;

/**
 * Created by Kralite on 2019/1/20.
 */

@NodeTypeName("PrintNode")
@InParamTypes({
        @ParamType(paramName = "printMsg", paramClass = String.class),
        @ParamType(paramName = "time", paramClass = Date.class)
})
@OutParamTypes({
        @ParamType(paramName = "printMsg", paramClass = String.class)
})
public class PrintNode extends FlowNode{
    @Override
    public void execute(){
        System.out.println(id + " " + nodeTypeName + inParamTypeMap.get("printMsg").getParamClass());
    }

}
