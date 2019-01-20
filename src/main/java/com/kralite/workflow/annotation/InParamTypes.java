package com.kralite.workflow.annotation;

import java.lang.annotation.*;
import java.util.Map;

/**
 * Created by Kralite on 2019/1/20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InParamTypes {
    ParamType[] value();
}
