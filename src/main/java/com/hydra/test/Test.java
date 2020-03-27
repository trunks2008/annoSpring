package com.hydra.test;

import com.hydra.context.AnnotationConfigApplicationContext;
import com.hydra.service.ServiceTest;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
        context.scan("com.hydra.service");

        ServiceTest serviceTest = (ServiceTest) context.getBean("servicetest");
        serviceTest.test();

//        HashMap<String, Object> map = context.getContext();
//        for (String key : map.keySet()){
//            System.out.println(key+"  :  "+map.get(key).getClass());
//        }

    }
}
