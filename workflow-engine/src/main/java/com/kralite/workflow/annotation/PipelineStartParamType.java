package com.kralite.workflow.annotation;

import java.lang.annotation.*;

/**
 * Created by ChenDaLin on 2019/1/30.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface PipelineStartParamType {
    Class value() default Object.class;
}
