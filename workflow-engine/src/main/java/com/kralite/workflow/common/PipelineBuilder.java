package com.kralite.workflow.common;

import com.kralite.workflow.annotation.PipelineTypeName;
import com.kralite.workflow.exception.PipelineTypeNameExistsException;
import com.kralite.workflow.exception.RunningFlowException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/30.
 */
public class PipelineBuilder {
    // key是注解@PipelineTypeName中的节点类型名，value是该节点类型Class
    static private volatile Map<String, Class> pipelineTypeMap = new ConcurrentHashMap<String, Class>();

    static {
        buildPipelineTypeMap();
    }

    static public Pipeline buildPipeline(String pipeLineType, String id, String startParamName, String endParamName){
        validate(pipeLineType, id, startParamName, endParamName);

        Class pipelineTypeClass = pipelineTypeMap.get(pipeLineType);
        try {
            Constructor defaultConstructor = pipelineTypeClass.getConstructor();
            Pipeline newPipeline = (Pipeline)defaultConstructor.newInstance();
            newPipeline.setId(id);
            newPipeline.setStartParamName(startParamName);
            newPipeline.setEndParamName(endParamName);
            newPipeline.init();
            return newPipeline;
        } catch (NoSuchMethodException nme) {
            throw new RunningFlowException(pipelineTypeClass.getCanonicalName() + " doesn't have default constructor");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RunningFlowException(pipelineTypeClass.getCanonicalName() + " new instance failed");
        }
    }

    /**
     * 扫描工程中所有有PipelineTypeName注解的类，然后放入pipelineTypeMap中，
     */
    static private void buildPipelineTypeMap(){
        Reflections reflections = new Reflections();
        Set<Class<?>> pipelineTypeSet =
                reflections.getTypesAnnotatedWith(com.kralite.workflow.annotation.PipelineTypeName.class);
        for (Class pipelineTypeClass: pipelineTypeSet) {
            PipelineTypeName a = (PipelineTypeName)(pipelineTypeClass.getAnnotation(PipelineTypeName.class));
            String pipeLineTypeStr = a.value();
            pipelineTypeMap.put(pipeLineTypeStr, pipelineTypeClass);
        }
    }

    // todo: 自定义代码的pipeline使用
    static public void addPipelineType(String pipelineTypeName, Class pipelineTypeClass) throws PipelineTypeNameExistsException {
        if (pipelineTypeMap.containsKey(pipelineTypeName)) {
            throw new PipelineTypeNameExistsException(pipelineTypeName);
        }
        pipelineTypeMap.put(pipelineTypeName, pipelineTypeClass);
    }

    private static void validate(String pipeLineType, String id, String startParamName, String endParamName){
        if (!pipelineTypeMap.containsKey(pipeLineType)) {
            throw new RunningFlowException("Pipeline type name '" + pipeLineType + "' doesn't exist");
        }
        if (id == null) {
            throw new RunningFlowException(pipeLineType + "'s pipeline id can't be null");
        }
        if (startParamName == null) {
            throw new RunningFlowException(pipeLineType + "'s pipeline start param name can't be null");
        }
        if (endParamName == null) {
            throw new RunningFlowException(pipeLineType + "'s pipeline end param name can't be null");
        }
    }
}
