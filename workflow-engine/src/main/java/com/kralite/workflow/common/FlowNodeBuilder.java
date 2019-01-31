package com.kralite.workflow.common;

import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.exception.RunningFlowException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenDaLin on 2019/1/29.
 */


public class FlowNodeBuilder {

    // key是注解@NodeTypeName中的节点类型名，value是该节点类型Class
    static private volatile Map<String, Class> nodeTypeMap = new ConcurrentHashMap<String, Class>();

    static {
        buildNodeTypeMap();
    }

    static public FlowNode buildNode(String nodeType, String id, Map<String, Object> props){
        if (!nodeTypeMap.containsKey(nodeType)) {
            throw new RunningFlowException("Node type name '" + nodeType + "' doesn't exist");
        }
        if (id == null) {
            throw new RunningFlowException(nodeType + "'s Node id can't be null");
        }
        if (props == null) {
            props = new ConcurrentHashMap<String, Object>();
        }

        Class nodeTypeClass = nodeTypeMap.get(nodeType);
        try {
            Constructor defaultConstructor = nodeTypeClass.getConstructor();
            FlowNode newNode = (FlowNode)defaultConstructor.newInstance();
            newNode.setId(id);
            newNode.setProps(props);
            newNode.initNode();
            return newNode;
        } catch (NoSuchMethodException nme) {
            throw new RunningFlowException(nodeTypeClass.getCanonicalName() + " doesn't have default constructor");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RunningFlowException(nodeTypeClass.getCanonicalName() + " new instance failed");
        }
    }

    /**
     * 扫描工程中所有有NodeTypeName注解的类，然后放入nodeTypeMap中，
     */
    static private void buildNodeTypeMap(){
        Reflections reflections = new Reflections();
        Set<Class<?>> nodeTypeSet =
                reflections.getTypesAnnotatedWith(com.kralite.workflow.annotation.NodeTypeName.class);
        for (Class nodeTypeClass: nodeTypeSet) {
            NodeTypeName a = (NodeTypeName)(nodeTypeClass.getAnnotation(NodeTypeName.class));
            String nodeTypeStr = a.value();
            nodeTypeMap.put(nodeTypeStr, nodeTypeClass);
        }
    }
}
