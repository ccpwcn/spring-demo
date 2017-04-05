package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.service.HelloWorld;

/**
 * Spring 的 HelloWorld 实现类
 * Created by lidawei on 2017/4/5.
 */
public class SpringHelloWorld implements HelloWorld {
    @Override
    public void sayHello() {
        System.out.println("Spring Say Hello!!!");
    }
}
