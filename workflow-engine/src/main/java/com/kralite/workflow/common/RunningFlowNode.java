package com.kralite.workflow.common;

import com.kralite.workflow.exception.RunningFlowException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/20.
 */
public class RunningFlowNode{
    private Logger logger = Logger.getLogger(this.getClass());

    private FlowNode flowNode;
    // 动态属性
    private Map<String, Object> inParams;
    private Map<String, Object> outParams;
    private NodeStatus status;

    public RunningFlowNode(FlowNode flowNode, Map<String, Object> inParams, Map<String, Object> outParams, NodeStatus status) {
        this.flowNode = flowNode;
        this.inParams = inParams;
        this.outParams = outParams;
        this.status = status;
    }

    public void execute(){
        if (flowNode == null) {
            throw new RunningFlowException("RunningFlowNode's flowNode can't be null");
        }
        validateParams(inParams, flowNode.getInParamTypeMap(), "in");
        outParams = flowNode.execute(inParams);
        if (outParams == null) { outParams = new ConcurrentHashMap<>();}
        validateParams(outParams, flowNode.getOutParamTypeMap(), "out");
    }

    private void validateParams(Map<String, Object> params, Map<String, ParamTypeInfo> paramTypes, String step){
        for(Map.Entry<String, ParamTypeInfo> paramTypeEntry: paramTypes.entrySet()){
            String paramName = paramTypeEntry.getKey();
            ParamTypeInfo paramTypeInfo = paramTypeEntry.getValue();
            // 参数是必需的
            if (paramTypeInfo.isRequired()) {
                // 入参中有该参数（可能是null的）
                if (params.containsKey(paramName) && params.get(paramName) != null){
                    // 该参数不为空
                    if (params.get(paramName) != null) {
                        // 类型检查通过
                        if (paramTypeInfo.getParamClass().isInstance(params.get(paramName))) {
                            // pass
                        }
                        // 类型检查不过
                        else {
                            throw new RunningFlowException(
                                    "RunningFlowNode ["+flowNode.nodeLogName()+"] "+step+"Param "+paramName+"'s class should be " +
                                            paramTypeInfo.getParamClass().getCanonicalName() + ", but it's " + params.get(paramName).getClass().getCanonicalName());
                        }
                    }
                    // 入参中有该参数但为null，不做类型判断
                    else {
                        //pass
                    }
                }
                // 入参中无该参数
                else {
                    throw new RunningFlowException(
                            "RunningFlowNode ["+flowNode.nodeLogName()+"] necessary "+step+"Param "+paramName+"=null" );
                }
            }
            // 参数不是必需的
            else {
                // 如果非必需参数仍然出现了且不为null，那就要校验其类型
                if (params.containsKey(paramName) && params.get(paramName) != null) {
                    if (paramTypeInfo.getParamClass().isInstance(params.get(paramName))) {
                        // pass
                    }
                    else {
                        throw new RunningFlowException(
                                "RunningFlowNode ["+flowNode.nodeLogName()+"] "+step+"Param "+paramName+"'s class should be " +
                                        paramTypeInfo.getParamClass().getCanonicalName() + ", but it's " + params.get(paramName).getClass().getCanonicalName());
                    }
                }
                // 如果参数不是必需的且入参中没有这个参数，那就不处理
                else {
                    // pass
                }
            }
        }
    }

    public FlowNode getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(FlowNode flowNode) {
        this.flowNode = flowNode;
    }

    public Map<String, Object> getInParams() {
        return inParams;
    }

    public void setInParams(Map<String, Object> inParams) {
        this.inParams = inParams;
    }

    public Map<String, Object> getOutParams() {
        return outParams;
    }

    public void setOutParams(Map<String, Object> outParams) {
        this.outParams = outParams;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }
}
