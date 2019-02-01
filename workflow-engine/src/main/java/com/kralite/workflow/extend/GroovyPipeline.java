package com.kralite.workflow.extend;

import com.kralite.workflow.annotation.PipelineEndParamType;
import com.kralite.workflow.annotation.PipelineStartParamType;
import com.kralite.workflow.annotation.PipelineTypeName;
import com.kralite.workflow.common.Pipeline;
import com.kralite.workflow.exception.RunningFlowException;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/2/1.
 */
@PipelineTypeName("GroovyPipeline")
@PipelineStartParamType(Object.class)
@PipelineEndParamType(Object.class)
public class GroovyPipeline extends Pipeline {
    @Override
    protected Object transform(Object startParam) {
        String groovyCode = (String)props.get("groovyCode");

        GroovyClassLoader groovyLoader = new GroovyClassLoader();
        Class groovyClass = groovyLoader.parseClass(groovyCode);
        Map outParams = null;
//        try {
//            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
//            Object[] groovyInParam = new Object[2];
//            groovyInParam[0] = inParams;
//            groovyInParam[1] = props;
//            Object groovyResult = groovyObject.invokeMethod("execute", groovyInParam);
//
//            if (groovyResult == null) {
//                outParams = null;
//            }
//            else if (groovyResult instanceof Map) {
//                outParams = (Map) groovyResult;
//            }
//            else {
//                throw new RunningFlowException("GroovyNode[" + id + "] groovy code should return Map<String, Object>.");
//            }
//        }catch (InstantiationException | IllegalAccessException e) {
//            throw new RunningFlowException("GroovyNode[" + id + "] groovy code parse error:" + e.getMessage(), e);
//        }
        return outParams;
    }
}
