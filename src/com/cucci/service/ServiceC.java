package com.cucci.service;

import com.cucci.annations.AutoWired;
import com.cucci.annations.Bean;

/**
 * C服务
 *
 * @author shenyunwen
 **/
@Bean
public class ServiceC {

    @AutoWired
    private ServiceA serviceA;

    public void say() {
        serviceA.say();
        System.out.println("3333333333");
        System.out.println("i'm in ServiceC");
    }
}
