package com.kralite.workflow.extend;

import com.kralite.workflow.annotation.*;
import com.kralite.workflow.common.Pipeline;
import com.kralite.workflow.exception.RunningFlowException;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/2/1.
 */
@PipelineTypeName("GroovyPipeline")
@PropNames({@PropName("groovyCode")})
@PipelineStartParamType(Object.class)
@PipelineEndParamType(Object.class)
public class GroovyPipeline extends Pipeline {
    @Override
    protected Object transform(Object startParam) {
        String groovyCode = props.get("groovyCode");

        GroovyClassLoader groovyLoader = new GroovyClassLoader();
        Class groovyClass = groovyLoader.parseClass(groovyCode);
        Object endParam = null;
        try {
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            Object[] groovyInParam = new Object[2];
            groovyInParam[0] = startParam;
            groovyInParam[1] = props;
            endParam = groovyObject.invokeMethod("transform", groovyInParam);
        }catch (InstantiationException | IllegalAccessException e) {
            throw new RunningFlowException("GroovyPipeline[" + id + "] groovy code parse error:" + e.getMessage(), e);
        }
        return endParam;
    }
}
