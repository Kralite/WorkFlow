package com.kralite.workflow.common;

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
    protected Map<String, PropInfo> propTypes;
    protected Map<String, String> props;

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

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    abstract protected Map<String, Object> execute(Map<String, Object> inParams);
}
