package com.sinoiov.lhjh;

import com.sinoiov.lhjh.service.HelloWorld;
import com.sinoiov.lhjh.service.HelloWorldService;
import com.sinoiov.lhjh.service.Knight;
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
        // helloWorldService这个字符串作为bean id，已经在beans.xml中声明了，它对应了一个Java Bean，所以能够实例化
        HelloWorldService service = context.getBean("helloWorldService", HelloWorldService.class);

        // 得到被注入的成员变量的引用
        HelloWorld helloWorld = service.getHelloWorld();

        // 调用创建出来的对象的方法
        helloWorld.sayHello();
    }
}
