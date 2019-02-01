package com.kralite.workflow.common;

import com.kralite.workflow.annotation.*;
import com.kralite.workflow.exception.InitFlowException;
import com.kralite.workflow.exception.RunningFlowException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */
@NodeTypeName("DefaultNode")
public class FlowNode extends AbstractFlowNode{

    @Override
    protected Map<String, Object> execute(Map<String, Object> inParams){return null;}

    public final void initNode(Map<String, String> props){
        initAnnotaion_NodeTypeParam();
        initAnnotaion_InParamTypes();
        initAnnotaion_OutParamTypes();
        initAnnotaion_PropTypes();
        if (props == null) {props = new HashMap<>();}
        validatePropTypes(props);
        this.props = props;
    }

//    public void putProperty(String key, Object value) {
//        props.put(key, value);
//    }

    public Object getProperty(String key) {
        return props.get(key);
    }

    public void putInParamType(String key, ParamTypeInfo paramTypeInfo) {inParamTypeMap.put(key, paramTypeInfo);}

    public void putOutParamType(String key, ParamTypeInfo paramTypeInfo) {outParamTypeMap.put(key, paramTypeInfo);}

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

    private final void initAnnotaion_PropTypes() {
        if (propTypes == null) {
            propTypes = new HashMap<>();
        }
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(PropNames.class);
        if(isExist) {
            PropNames a = (PropNames)(clazz.getAnnotation(PropNames.class));
            PropName[] types = a.value();
            logger.info("init [" +nodeLogName()+"] annotaion PropNames:");
            for (PropName type: types) {
                propTypes.put(type.value(), new PropInfo(type.required()));
                logger.info("\tparamName="+type.value()+", required="+type.required());
            }
        }
    }

    private final void initAnnotaion_InParamTypes() {
        if (inParamTypeMap == null) {
            inParamTypeMap = new HashMap<>();
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
            outParamTypeMap = new HashMap<>();
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

    private final void validatePropTypes(Map<String, String> initProps) {

        for(Map.Entry<String, PropInfo> propTypeEntry: propTypes.entrySet()){
            String propName = propTypeEntry.getKey();
            PropInfo propTypeInfo = propTypeEntry.getValue();
            // 参数是必需的
            if (propTypeInfo.isRequired()) {
                // 入参中有该参数（可能是null的）
                if (initProps.containsKey(propName)){
                    // pass
                }
                // 入参中无该参数
                else {
                    throw new RunningFlowException(
                            "RunningFlowNode ["+nodeLogName()+"] necessary Property '"+propName+"' doesn't exists." );
                }
            }
            // 参数不是必需的
            else {
                // pass
            }
        }
    }

    public String nodeLogName(){
        return nodeTypeName+" : "+id;
    }
}
