package com.kralite.workflow.test.groovy

/**
 * Created by ChenDaLin on 2019/2/2.
 */
def transform(Object startParam, Map<String, String> props) {
    Double v1 = (Double)startParam
    Double endParam = v1 * 2L
    return endParam
}