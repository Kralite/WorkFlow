package com.kralite.workflow.extend;

import com.kralite.workflow.annotation.*;
import com.kralite.workflow.common.FlowNode;
import com.kralite.workflow.exception.RunningFlowException;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.util.Map;

/**
 * Created by ChenDaLin on 2019/2/1.
 */
@NodeTypeName("GroovyNode")
@PropNames({
        @PropName("groovyCode")
})
public class GroovyNode extends FlowNode{
    @Override
    protected Map<String, Object> execute(Map<String, Object> inParams) {
        if (! props.containsKey("groovyCode")) {
            throw new RunningFlowException("GroovyNode[" + id +"] doesn't contain property 'groovyCode'");
        }
        String groovyCode = (String)props.get("groovyCode");

        GroovyClassLoader groovyLoader = new GroovyClassLoader();
        Class groovyClass = groovyLoader.parseClass(groovyCode);
        Map outParams = null;
        try {
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            Object[] groovyInParam = new Object[2];
            groovyInParam[0] = inParams;
            groovyInParam[1] = props;
            Object groovyResult = groovyObject.invokeMethod("execute", groovyInParam);

            if (groovyResult == null) {
                outParams = null;
            }
            else if (groovyResult instanceof Map) {
                outParams = (Map) groovyResult;
            }
            else {
                throw new RunningFlowException("GroovyNode[" + id + "] groovy code should return Map<String, Object>.");
            }
        }catch (InstantiationException | IllegalAccessException e) {
            throw new RunningFlowException("GroovyNode[" + id + "] groovy code parse error:" + e.getMessage(), e);
        }
        return outParams;

    }
}
