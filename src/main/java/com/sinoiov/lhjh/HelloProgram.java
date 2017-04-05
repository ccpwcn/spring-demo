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
        // beans.xml, 这是一个配置文件，在这里声明Java bean
        // 在这里，我们通过读取beans.xml文件来创建一个应用程序上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        // IoC容器中，其作用是作为第三种角色，它的任务是创建 beans.xml文件中声明的 Java Bean 对象。
        HelloWorldService service = context.getBean("helloWorldService", HelloWorldService.class);
        HelloWorld helloWorld = service.getHelloWorld();
        helloWorld.sayHello();
    }
}
