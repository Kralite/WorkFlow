package com.kralite.workflow.common;

import com.kralite.workflow.annotation.InParamTypes;
import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.annotation.OutParamTypes;
import com.kralite.workflow.annotation.ParamType;
import com.kralite.workflow.exception.InitFlowException;
import com.kralite.workflow.exception.RunningFlowException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kralite on 2019/1/20.
 */
@NodeTypeName("DefaultNode")
public class FlowNode extends AbstractFlowNode{

    @Override
    protected Map<String, Object> execute(Map<String, Object> inParams){return null;}

    public final void initNode(){
        initAnnotaion_NodeTypeParam();
        initAnnotaion_InParamTypes();
        initAnnotaion_OutParamTypes();
    }

    public void putProperty(String key, Object value) {
        props.put(key, value);
    }

    public Object getProperty(String key) {
        return props.get(key);
    }

    private final void initAnnotaion_NodeTypeParam() {
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(NodeTypeName.class);
        if(isExist) {
            NodeTypeName a = (NodeTypeName)(clazz.getAnnotation(NodeTypeName.class));
            nodeTypeName = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion NodeTypeParam="+a.value());
        }
        else {
            throw new InitFlowException(
                    "FlowNode ["+id+"] init failed, annotation NodeTypeName can't be null");
        }
    }

    private final void initAnnotaion_InParamTypes() {
        if (inParamTypeMap == null) {
            inParamTypeMap = new ConcurrentHashMap<>();
        }
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(InParamTypes.class);
        if(isExist) {
            InParamTypes a = (InParamTypes)(clazz.getAnnotation(InParamTypes.class));
            ParamType[] inParamTypes = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion InParamTypes:");
            for (ParamType p: inParamTypes) {
                inParamTypeMap.put(p.paramName(), new ParamTypeInfo(p.paramClass(), p.required()));
                logger.info("\tparamName="+p.paramName()+", paramClass="+p.paramClass()+", required="+p.required());
            }
        }
    }

    private final void initAnnotaion_OutParamTypes() {
        if (outParamTypeMap == null) {
            outParamTypeMap = new ConcurrentHashMap<>();
        }
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(OutParamTypes.class);
        if(isExist) {
            OutParamTypes a = (OutParamTypes)(clazz.getAnnotation(OutParamTypes.class));
            ParamType[] outParamTypes = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion OutParamTypes:");
            for (ParamType p: outParamTypes) {
                outParamTypeMap.put(p.paramName(), new ParamTypeInfo(p.paramClass(), p.required()));
                logger.info("\tparamName="+p.paramName()+", paramClass="+p.paramClass()+", required="+p.required());
            }
        }
    }

    public String nodeLogName(){
        return nodeTypeName+" - "+id;
    }
}
