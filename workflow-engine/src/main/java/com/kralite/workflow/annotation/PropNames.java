package com.kralite.workflow.annotation;

import java.lang.annotation.*;

/**
 * Created by ChenDaLin on 2019/2/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface PropNames {
    PropName[] value();
}
