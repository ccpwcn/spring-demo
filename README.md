# Spring Demo说明文档
这是一个Spring框架应用示例工程，里面写了大量的注释，对于我们理解和掌握Spring的一些关键特性是非常有帮助的。

## 1. 工程主要内容
### 1.1 框架的Maven引用
在Maven中引用Spring组件，以实现在工程中的引用。

### 1.2 依赖注入
通过两个例子，说明了如何引用Spring中的依赖注入这一特性。

### 1.3 面向切面编程的实现
通过一个英勇的骑士的探险行动的示例设计，展示面向切面编程的具体实现。

### 1.4 使用模板消息样板式代码
主要是避免重复的代码，本例中我们使用的是通过JDBC访问数据库这样的示例。

## 2. 关于Spring Bean
在基于Spring的应用中，我们的应用对象生存于Spring容器（Container）中。

**什么是Spring容器？**  
Spring容器是Spring框架的核心，它使用依赖注入（Dependencies Injection）这种方式管理构成应用的组件，而且还能创建
多个组件之间相互协作的关联关系。这么做的好处是：**这些对象更简单、更易于理解、能更好的被重用、更加容易测试。**

另外，Spring的容器并不只有一个，恰恰相反，Spring自带了很多的容器实现，但是主要有两个类型的，一种是Bean工厂（由
org.springframework.beans.factory.BeanFactory接口定义），这是最简单的一种，提供最基本的DI支持。另外一种是应用
上下文（由org.springframework.context.ApplicationContext接口定义），它基于BeanFactory构建，并提供应用框架级别的
服务，例如从属性文件（.properties）中解析文本信息，或者应用事件发布给感兴趣的事件监听者。

> 据网上一般资讯介绍，虽然可以在Bean工厂和应用上下文中任选一种，但是Bean工厂这种做法对一般应用来说太低级了，因此应用
上下文对Bean工厂更受程序员欢迎。

### 2.1 应用上下文
Spring自带了很多的应用上下文，我们先看几个常用的：
- AnnotationConfigApplicationContext，从一个或多个Java配置类中加载Spring应用上下文。
- AnnotationConfigWebApplicationContext，从一个或多个Java配置类中加载Spring Web应用上下文。
- ClassPathXmlApplicationContext，从类路径（也就是Java中最常见的CLASSPATH路径）下的一个或多个XML配置文件中加载
上下文定义，这个上下文定义文件（也就是XML文件）作为类资源文件存在。
- FileSystemXmlApplicationContext，从文件系统下的一个或多个XML配置文件中加载上下文定义。
- XmlWebApplicationContext，从Web应用下的一个或多个XML配置文件中加载上下文定义。

简单地，我们使用FileSystemXmlApplicationContext从文件系统中装载应用上下文定义文件：
```java
package com.company.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class HelloProgram {
    public static void main(String[] args) {
        ApplicatoinContext context = new FileSystemXmlApplicationContext("C:/res/knights.xml");
    }
}
```

使用ClassPathXmlApplicationContext和FileSystemXmlApplicationContext非常相似，区别只在于如何加载应用上下文定义
文件：
```java
package com.company.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloProgram {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("knights.xml");
    }
}
```
这个做法，是在所有类路径（CLASSPATH路径）和JAR文件中查找knights.xml这个文件。当上下文定义好之后，我们就可以调用
上下文的getBean()方法从Spring容器中获取Bean。我们以ClassPathXmlApplicationContext为例：
```java
package com.company.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloProgram {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("knights.xml");
        HelloWorldService service = context.getBean("helloWorldService", HelloWorldService.class);
        Knight knight = (Knight) context.getBean("knight");
    }
}
```
在应用上下文准备就绪之后，我们就可以调用上下文的getBean()方法从Spring容器中获取Bean。  
在调用getBean()方法时，如果指定了类型，那么返回的类型是确定的，如果没有指定，则需要转型。

### 2.2 Bean的生命周期
在传统的Java应用中，Bean的生命周期是自new实例化以来，它就可以使用了，一旦该Bean不再使用，则由Java自动进行垃圾回收。

相比之下，Spring容器的Bean生命周期就显的相对复杂的多。然而，我们要掌握Spring，就必须正确理解Spring Bean的生命周期，
这非常重要。这是Spring Bean的生命周期图示：

![https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/Spring%20Bean%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.png](https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/Spring%20Bean%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.png)

Spring容器负责创建它们、装配它们，配置并管理它们的整个生命周期，从生存到死亡（也就是可以认为这从new到finalize()）。
这个过程是这样的：
1. Spring对Bean进行实例化；
2. Spring将值和Bean的引用注入到对应的属性中；
3. 如果Bean实现了BeanNameAware接口，Spring将Bean的ID传递给setBeanName()方法；
4. 如果Bean实现了BeanFactoryAware接口，Spring将调用setBeanFactory()方法，将BeanFactory容器实例传入；
5. 如果Bean实现了ApplicationContextAware接口，Spring将调用setApplicationContext()方法，将Bean所在的应用上下文传入；
6. 如果Bean实现了BeanPostProcessor接口，Spring将调用它们的postProcessBeforeInitialization()方法；
7. 如果Bean实现了InitializationBean接口，Spring将调用它们的afterPropertiesSet()方法。类似的，如果Bean使用了
init-method声明了初始化方法，该方法也会被调用；
8. 如果Bean实现了BeanPostProcessor接口，Spring将调用它们的postProcessAfterInitialization()方法；
9. 此时，Bean已经准备就绪，可以被应用程序使用了，它们将一直驻留在应用上下文中，直到该应用上下文被销毁；
10. 如果Bean实现了DisposableBean接口，Spring将调用它们的destroy()方法。同样的，如果Bean使用了destroy-method声明了
销毁方法，该方法也会被调用。

## 3. Spring构成
这是Spring的构成示意图：

![https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/Spring%E6%9E%84%E6%88%90.png](https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/Spring%E6%9E%84%E6%88%90.png)

### 3.1 Spring核心容器
容器是Spring最核心的部分，它管理着Spring中Bean的创建、配置和管理，这个模块中包含了Spring Bean工厂，它为Spring
提供了DI的功能。基于Bean工厂，我们还会发现有多种Spring应用上下文的实现，每一种都提供了不同的配置Spring的实现方式。
最主要的，是通过配置类的方式、通过配置文件XML的方式、通过文件系统指定文件的方式。

除了Bean工厂和应用上下文，这个模块还提供了E-mail、JNDI访问、EJB集成与调度等企业级的应用功能。

Spring核心容器是所有Spring模块的基础，也就是说，其他的所有Spring模块都是构建于核心容器模块之上的，当我们配置应用
时，其实我们隐式的使用了核心模块中的这些类。

## 3.2 AOP模块
在Spring的AOP模块中，提供了面向切面编程的丰富支持，这个模块是在Spring中开发切面的基础，与DI一样，AOP有助于应用
对象解藕。它可以将遍布于系统中各个部位的关注点（例如安全、日志、、统计、事务等）从应用对象中解藕出来。

解藕之后的效果是：应用对象中需要关注自己的业务即可，不需要关注自己什么时间要提交统计数据、什么时间要记录日志、什么
时间要处理安全问题、什么时间要管理事务等等等等。

## 3.3 数据访问与集成
使用JDBC会产生大量的样板式代码，例如获得数据库连接、创建语句、执行语句、处理结果集、关闭数据库连接等。Spring拥有
JDBC和DAO（Data Access Object）模块，它可以使数据库代码变的非常简单明了。

## 4. Spring版本与历史
### 4.1 Spring 3.1的新功能特性
Spring 3.1在简化和改善配置方面有很多改进，还提供了声明式缓存的支持，以及对Spring MVC的功能增强。