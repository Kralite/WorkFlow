package com.kralite.workflow.annotation;

import java.lang.annotation.*;

/**
 * Created by Kralite on 2019/1/20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OutParamTypes {
    ParamType[] value();
}
