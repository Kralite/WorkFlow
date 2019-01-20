package com.kralite.workflow.common;

import com.kralite.workflow.annotation.InParamTypes;
import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.annotation.OutParamTypes;
import com.kralite.workflow.annotation.ParamType;
import com.kralite.workflow.exception.InitFlowException;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */
public abstract class AbstractFlowNode {
    protected Logger logger = Logger.getLogger(this.getClass());

    // 类型属性
    protected String nodeTypeName;
    protected Map<String, ParamTypeInfo> inParamTypeMap;
    protected Map<String, ParamTypeInfo> outParamTypeMap;

    // 静态属性
    protected String id;
    protected Map<String, Object> props;

    // 动态属性
    protected Map<String, Object> inParams;
    protected Map<String, Object> outParams;
    protected NodeStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName(String nodeTypeName) {
        this.nodeTypeName = nodeTypeName;
    }

    public Map<String, ParamTypeInfo> getInParamTypeMap() {
        return inParamTypeMap;
    }

    public void setInParamTypeMap(Map<String, ParamTypeInfo> inParamTypeMap) {
        this.inParamTypeMap = inParamTypeMap;
    }

    public Map<String, ParamTypeInfo> getOutParamTypeMap() {
        return outParamTypeMap;
    }

    public void setOutParamTypeMap(Map<String, ParamTypeInfo> outParamTypeMap) {
        this.outParamTypeMap = outParamTypeMap;
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

    public Map<String, Object> getProps() {
        return props;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    abstract public void execute();
}
