package com.cucci.annations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动注入bean
 *
 * @author shenyunwen
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoWired {

    /**
     * 以该名称注入bean
     *
     * @return bean的名称
     */
    String name() default "";
}
