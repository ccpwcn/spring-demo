package com.sinoiov.lhjh;

import com.sinoiov.lhjh.service.HelloWorld;
import com.sinoiov.lhjh.service.HelloWorldService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * HelloWorld 应用程序
 * Created by lidawei on 2017/4/5.
 */
public class HelloProgram {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        HelloWorldService service = context.getBean("helloWorldService", HelloWorldService.class);
        HelloWorld helloWorld = service.getHelloWorld();
        helloWorld.sayHello();
    }
}
