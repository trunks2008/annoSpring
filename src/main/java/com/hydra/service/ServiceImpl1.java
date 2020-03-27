package com.hydra.service;

import com.hydra.anno.MyService;

@MyService("service1")
public class ServiceImpl1 implements TestService{

    @Override
    public void run(){
        System.out.println("run1111.....");
    }
}
