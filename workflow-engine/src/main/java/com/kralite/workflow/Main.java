package com.kralite.workflow;

import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.common.ParamTypeInfo;
import com.kralite.workflow.engine.RunEngine;
import com.kralite.workflow.extend.PrintNode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.jvm.hotspot.HelloWorld;

import java.util.HashMap;


/**
 * Created by Kralite on 2019/1/20.
 */
public class Main {
    public static void main(String[] args) throws Throwable{
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");
        RunEngine runEngine = (RunEngine) context.getBean("runEngine");
        runEngine.loadFlow();
        runEngine.run();

//        PrintNode printNode = new PrintNode();
//        printNode.setId("lksadfgjkl2324");
//        printNode.setInParamTypeMap(new HashMap<String, ParamTypeInfo>());
//        printNode.setOutParamTypeMap(new HashMap<String, ParamTypeInfo>());
//        printNode.initNode();

    }
}
