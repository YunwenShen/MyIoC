package com.cucci.service;

import com.cucci.annations.AutoWired;
import com.cucci.annations.Bean;

/**
 * B服务
 *
 * @author shenyunwen
 **/
@Bean
public class ServiceB {

    @AutoWired
    private ServiceA serviceA;

    public void say() {
        serviceA.say();
        System.out.println("2222222222222222222222222222222222");
        System.out.println("i'm in ServiceB");
    }
}
