package com.kralite.workflow.test.groovy

/**
 * Created by ChenDaLin on 2019/2/1.
 */
def execute(Map<String, Object> inParams, Map<String, Object> props) {
    Map<String, Object> outParams = new HashMap<>()
    Double v1 = (Double)inParams.get("value1")
    Double v2 = (Double)inParams.get("value2")
    Double result
    String operator = (String)props.get("operator")
    if (operator.equals("+")) {result = v1+v2}
    else if (operator.equals("-")) {result = v1-v2}
    else if (operator.equals("*")) {result = v1*v2}
    else if (operator.equals("/")) {result = v1/v2}
    outParams.put("result", result)
    return outParams
}
