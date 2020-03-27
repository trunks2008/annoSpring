package com.hydra.service;

import com.hydra.anno.MyService;

@MyService("service0")
public class ServiceImpl0 implements TestService{

    @Override
    public void run(){
        System.out.println("run0000.....");
    }
}
