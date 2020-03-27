package com.hydra.service;

import com.hydra.anno.MyAutowired;
import com.hydra.anno.MyService;

@MyService("servicetest")
public class ServiceTest {

//    @MyAutowired("service1")
//    TestService test;

    @MyAutowired("service1")
    ServiceImpl1 test;

    public ServiceImpl1 getTest() {
        return test;
    }

    public void setTest(ServiceImpl1 test) {
        this.test = test;
    }

    public void test(){
        test.run();
        System.out.println("end");
    }




}
