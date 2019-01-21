package com.kralite.workflow;

import com.kralite.workflow.annotation.NodeTypeName;
import com.kralite.workflow.common.ParamTypeInfo;
import com.kralite.workflow.extend.PrintNode;

import java.util.HashMap;


/**
 * Created by Kralite on 2019/1/20.
 */
public class Main {
    public static void main(String[] args) throws Throwable{
        PrintNode printNode = new PrintNode();
        printNode.setId("lksadfgjkl2324");
        printNode.setInParamTypeMap(new HashMap<String, ParamTypeInfo>());
        printNode.setOutParamTypeMap(new HashMap<String, ParamTypeInfo>());
        printNode.initNode();

    }
}
