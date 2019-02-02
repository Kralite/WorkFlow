package com.kralite.workflow.common;

import com.kralite.workflow.annotation.*;
import com.kralite.workflow.exception.InitFlowException;
import com.kralite.workflow.exception.RunningFlowException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */
@PipelineTypeName("DefaultPipeline")
@PipelineStartParamType(Object.class)
@PipelineEndParamType(Object.class)
public class Pipeline extends AbstractPipeline{
    protected Logger logger = Logger.getLogger(getClass());

    @Override
    protected Object transform(Object startParam){
        return startParam;
    }

    public void init(Map<String, String> props){
        initParamTypeName();
        initAnnotaion_PropNames();
        initStartParamType();
        initEndParamType();
        if (props == null) {
            props = new HashMap<>();
        }
        validateProps(props);
        this.props = props;
    }

    public Object executeTransform(Object startParam) throws RunningFlowException {
        validateParamType(startParamType, startParam, "start");
        Object endParam = transform(startParam);
        validateParamType(endParamType, endParam, "end");
        return endParam;
    }

    protected void validateParamType(Class paramType, Object param, String step) throws RunningFlowException {
        if (param == null) {
//            throw new RunningFlowException(
//                    "Pipeline's "+step+"Param can't be null. Pipeline [" + id + "] "+step+"Param="+param);
            // 参数要么是null，要么就要符合类型要求
            return;
        }
        if (!paramType.isInstance(param)) {
            throw new RunningFlowException(
                    step+"Param is not instance of class " + paramType.getCanonicalName()+". "+step+"Param is instance of class"+param.getClass().getCanonicalName());
        }

    }

    private final void validateProps(Map<String, String> initProps) {
        for(Map.Entry<String, PropInfo> propTypeEntry: propNames.entrySet()){
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
                            "Pipeline ["+id+"] necessary Property '"+propName+"' doesn't exists." );
                }
            }
            // 参数不是必需的
            else {
                // pass
            }
        }
    }

    private void initParamTypeName(){
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(PipelineTypeName.class);
        if(isExist) {
            PipelineTypeName a = (PipelineTypeName)(clazz.getAnnotation(PipelineTypeName.class));
            pipelineTypeName = a.value();
            logger.info("init [" +pipelineTypeName+"] annotaion PipelineTypeName="+pipelineTypeName);
        }
        else {
            throw new InitFlowException(
                    "Pipeline ["+id+"] init failed, annotation PipelineTypeName can't be null");
        }
    }

    private final void initAnnotaion_PropNames() {
        if (propNames == null) {
            propNames = new HashMap<>();
        }
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(PropNames.class);
        if(isExist) {
            PropNames a = (PropNames)(clazz.getAnnotation(PropNames.class));
            PropName[] types = a.value();
            logger.info("init [Pipeline: " +id+ "] annotaion PropNames:");
            for (PropName type: types) {
                propNames.put(type.value(), new PropInfo(type.required()));
                logger.info("\tparamName="+type.value()+", required="+type.required());
            }
        }
    }

    private void initStartParamType(){
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(PipelineStartParamType.class);
        if(isExist) {
            PipelineStartParamType a = (PipelineStartParamType)(clazz.getAnnotation(PipelineStartParamType.class));
            startParamType = a.value();
            logger.info("init [" +pipelineTypeName+"] annotaion PipelineStartParamType="+startParamType);
        }
        else {
            throw new InitFlowException(
                    "Pipeline ["+id+"] init failed, annotation PipelineStartParamType can't be null");
        }
    }

    private void initEndParamType(){
        Class clazz = this.getClass();
        boolean isExist = clazz.isAnnotationPresent(PipelineEndParamType.class);
        if(isExist) {
            PipelineEndParamType a = (PipelineEndParamType)(clazz.getAnnotation(PipelineEndParamType.class));
            endParamType = a.value();
            logger.info("init [" +pipelineTypeName+"] annotaion PipelineEndParamType="+endParamType);
        }
        else {
            throw new InitFlowException(
                    "Pipeline ["+id+"] init failed, annotation PipelineEndParamType can't be null");
        }
    }

}
