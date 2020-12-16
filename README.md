# Spring学习笔记
这是一个Spring框架应用示例工程，里面写了大量的注释，对于我们理解和掌握Spring的一些关键特性是非常有帮助的。

这也是我学习和理解Spring的一个过程，分享出来给有这方面需求的同学快速入门，希望对您有所帮助，如果你觉得好，请给一个star，谢谢。

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
Spring容器是Spring框架的核心，它使用依赖注入（Dependencies Injection）这种方式管理构成应用的组件，而且还能创建多个组件之间相互协作的关联关系。这么做的好处是：**这些对象更简单、更易于理解、能更好的被重用、更加容易测试。**

最核心的问题：Spring容器究竟是什么？我们用技术的思维这么描述：如果你写了一个类，实现了Spring的`org.springframework.context.ApplicationContext`这个接口，那么你这个类就是一个Spring容器，所以，Spring的容器并不只有一个，恰恰相反，Spring自带了很多的容器实现，但是主要有两个类型的，一种是Bean工厂（由org.springframework.beans.factory.BeanFactory接口定义），这是最简单的一种，提供最基本的DI支持。另外一种是应用上下文（由org.springframework.context.ApplicationContext接口定义），它基于BeanFactory构建，在此基础之上，提供应用框架级别的服务，例如从属性文件（.properties）中解析文本信息，或者应用事件发布给感兴趣的事件监听者。
> 在一个普通的Java工程中，我们可以通过实例化`ClassPathXmlApplicationContext`或者`FileSystemXmlApplicationContext`来初始化一个Spring容器。在Web工程中，我们一般是通过配置web.xml的方式来初始化Spring容器。
> 重点地：对于Web工程，我们需要配置web.xml来实现Spring容器的初始化，如下所示：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml classpath:services.xml</param-value>
    </context-param>
    <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener> 
</web-app>
```
> contextConfigLocation指明Spring去哪里读取配置文件，`org.springframework.web.context.ContextLoaderListener`继承自`org.springframework.web.context.ContextLoader`，而且实现了J2EE API包`javaee-api`中的`javax.servlet.ServletContextListener`，所以，它能够做到Web容器（例如Tomcat）启动的时候，去读取指定位置的配置文件从而完成Spring容器的初始化（包括也是最重要的，就是初始化DispatcherServlet和加载Bean），而最重要的DispatcherServlet又是J2EE API包`javaee-api`中servlet子包下`javax.servlet.http.HttpServlet`的子类（中间还有其他的，不是直接继承下来的），所以就能够完成整个Spring容器的初始化和项目的加载启动。
> J2EE API包`javaee-api`是一个非常重要的包，它实现了J2EE技术规范中的很多内容，包括：
> 1. JDBC（Java Database Connectivity，访问数据库）
> 2. JNDI（Java Name and Directory Interface，操作DNS和LDAP及本地文件系统和服务器中的对象等）
> 3. EJB（Enterprise Java Bean，使用简单Java类简化企业级开发领域的复杂业务逻辑）
> 4. RMI（Remote Method Invoke，远程方法调用，分布式环境下必备，传统技术中应用最多的就是hessian）
> 5. IDL/CORBA（Interface Definition Language？不知道干啥的，没见过）
> 6. JSP（Java Server Page，这个太熟悉了，用来生成和渲染Web页面的）
> 7. Servlet（这个更熟悉吧，建立Web服务器端程序接受客户端请求实现业务逻辑的）
> 8. XML（Extensible Markup Language，单纯的Java代码解决服务器领域的开发在某些情况下有些困难，我们需要XML这种表述能力很强的标记语言辅助一下）
> 9. JMS（Java Message Service，消息通信）
> 10. JTA（Java Transaction Architecture，事务处理、监控等）
> 11. JTS（Java Transaction Service，也是事务监控相关的，而且好像还和IDL/CORBA有关，我也不熟悉）
> 12. JavaMail（存取邮件服务器的，这个还是挺常见的，邮件收发的规范和标准早已有之，J2EE领域实现了这一套规范，就可以自建邮件服务器，也可以访问其他邮件服务器了）
> 13. JAF（Java Bean Activation Framework，好像和JavaMail一起协作工作的，我也不熟悉）

与javaee-api齐名的还有一个名叫`javaee-web-api`的家伙，这两者大体相同，但是还有些区别的，javaee-web-api不支持JMS，而javaee-api中的xml解析库更大（这难道是写基于J2EE行业规范的Spring项目的时候要写很多XML的始作俑者？）。J2EE这一套技术规范之下，包含的东西还是很多的。但是：
  - 这东西是一些所谓的专家拍脑袋想出来的，根本就没在实践场景中大规模调研过，也不是实践中研究出来的，所以极其难用，但是为什么我们在这里还是提到了它呢？那是因为这个东西太难用，所以后来出来了Java Web，Java Web技术开发起来就容易多了，但是Java Web也不是凭空捏造出来的，而是对J2EE的继承和发展，所以Java Web中也用到了很多J2EE中的技术，比如JSP、Servlet、JNDI等。
  - J2EE只是一套技术规范，没有具体实现，所以就有很多它的实现了，经过这几十年的发展，J2EE本身的问题暴露越来越严重，在它的基础上发展起来了Spring已经一家独大，成为了事实上的标准，所以可以这么理解：J2EE是先祖，没有先祖，哪里来的我们？所以我们身上有各种先祖的影子（比如长的基本上差不多一样，都有四肢、眼睛、鼻子、耳朵等），但是我们和先祖差别还是很大的，我们很先进，很发达，很文明，我们和先祖不是一回事。
  - 作为后进小生的Spring，当初也不怎么起眼，后来发展的越来越好，帮助我们屏蔽了很多J2EE和Java Web技术中的难点和细节，让我们只关注于具体的业务逻辑功能开发，效率翻了N倍，所以还是很好用的。
  - 上面又提到了Java Web，相信很多人对J2EE和Java Web又分不清楚了，我们简单地这么理解：J2EE只是一套技术规范并没有实现（这个上面其实已经说过了），但是Java Web却是基于J2EE的技术规范做了很多实现，是以Java语言为基础，能够完整地开发出服务器端应用程序的（最重要的是能基于JSP实现动态页面），开发完之后部署到一个Web服务器容器中（比如Tomcat）就可以响应客户端和浏览器的请求了。

> 据网上一般资讯介绍，虽然可以在Bean工厂和应用上下文中任选一种，但是Bean工厂这种做法对一般应用来说太低级了，因此应用
上下文的方式相对于Bean工厂来说更受程序员欢迎。

### 2.1 应用上下文
Spring自带了很多的应用上下文，我们先看几个常用的：
- AnnotationConfigApplicationContext，从一个或多个Java配置类中加载Spring应用上下文。
- AnnotationConfigWebApplicationContext，从一个或多个Java配置类中加载Spring Web应用上下文。
- ClassPathXmlApplicationContext，从类路径（也就是Java中最常见的CLASSPATH路径）下的一个或多个XML配置文件中加载上下文定义，这个上下文定义文件（也就是XML文件）作为类资源文件存在。
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

使用ClassPathXmlApplicationContext和FileSystemXmlApplicationContext非常相似，区别只在于如何加载应用上下文定义文件：
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
这个做法，是在所有类路径（CLASSPATH路径）和JAR文件中查找knights.xml这个文件。当上下文定义好之后，我们就可以调用上下文的getBean()方法从Spring容器中获取Bean。我们以ClassPathXmlApplicationContext为例：
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
在应用上下文准备就绪之后，我们就可以调用上下文的getBean()方法从Spring容器中获取Bean。在调用getBean()方法时，如果指定了类型，那么返回的类型是确定的，如果没有指定，则需要转型。如果getBean方法中传的参数（Bean的名称，在注入容器时可以指定，也可以使用默认规则自动生成）不正确，会返回null。

### 2.2 Bean的生命周期
在传统的Java应用中，Bean的生命周期是自new实例化以来，它就可以使用了，一旦该Bean不再使用，则由Java自动进行垃圾回收。

相比之下，Spring容器的Bean生命周期就显的相对复杂的多。然而，我们要掌握Spring，就必须正确理解Spring Bean的生命周期，这非常重要。

Spring Bean的生命周期图示：

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
### 4.1 Spring 3.1版本特性
Spring 3.1在简化和改善配置方面有很多改进，还提供了声明式缓存的支持，以及对Spring MVC的功能增强。
1. 环境Profile功能，使得针对开发、测试、生产环境可以应用不同的配置，借助于Profile，可以快速处理在不同环境中应用
不同的Bean。
2. 多个enable注解，使用这个注解可以启用Spring的特定功能
3. 添加对声明式缓存的支持，能够简单的注解声明缓存的边界和规则，这与我们声明事务边界非常类似
4. 新添加的用于构造器注入的c命名空间，它类似于Spring 2.x中提供的面向属性的p命名空间（p命名空间用于属性注入）
5. 支持Servlet 3.0，包括在基于Java的配置中声明Servlet和Filter，这样就可以不用借助于web.xml这个配置文件了
6. 改进Spring对JPA的支持，现在可以在Spring中完整的使用JPA，不再需要使用persistence.xml文件了
Spring 3.1针对SpringMVC的功能增强：
1. 自动绑定路径变量到模型属性
2. 提供了RequestMapping的produces和consumers两个属性，分别用于匹配请求中的Accept和Content-Type头部信息
3. 提供了RequestPart注解，用于将MultiPart请求中的某些部分数据绑定到处理器的方法参数中
4. 支持Flash属性（这是一个能够在redirect之后仍然能够存活的属性）以及用于在请求间存放Flash属性的RedirectAttributes
类型


不能继续使用的功能：  
JpaTemplate类和JpaDaoSupport类被废弃了，后来更是在3.2之后的版本中被删除了。

### 4.2 Spring 3.2版本特性
Spring 3.2主要关注于SpringMVC的功能改进：
1. 控制器可以使用Servlet 3.0的异步请求，允许在一个独立的线程中处理请求，从而将Servlet线程解放出来以处理更多的请求
2. 虽然从Spring 2.5开始，SpringMVC就能够以POJO的形式进行很方便的测试，但是Spring 3.2中引入了SpringMVC测试框架，
用于为控制器编写更丰富的测试，使用断言判定控制器的行为是否正确，而且在使用的过程中并不需要Servlet容器
3. 除了提供控制器的测试功能，Spring 3.2还包含了基于RestTemplate的客户端的测试支持，在测试的过程中，不需要往真正的
REST端点上发送请求
4. ControllerAdvice注解能够将通用的ExceptionHandler、InitBinder、ModeAttributes方法收集到一个类中，并应用到所有
控制器上
5. 在Spring 3.2之前，只能通过ContentNegotiatingViewResolver使用完整的内容协商（Full Content Negotiation）功能，
但是在Spring 3.2中，完整的内容协商功能可以在整个SpringMVC中使用，即使是依赖于消息转换器（Message Converter）使用
和生产内容的控制器也能使用该功能
6. 一个新的MatrixVariable注解，这个注解能够将请求中的矩阵变量绑定到处理器的方法参数中
7. 基础的抽象类AbstractDispatcherServletInitializer能够非常便利的配置DispatcherServlet，而不必再使用web.xml。与
之类似，当你希望通过基于Java的方式来配置Spring时，可以使用AbstractAnnotationConfigDispatcherServletInitializer的
子类
8. 新增ResponseEntityExceptionHandler，可以用于替代DefaultHandlerExceptionResolver，这个新增类会返回
ResponseEntity< Object >而不是ModelAndView
9. RestTemplate和RequestBody参数支持泛型
10. RestTemplate和RequestMapping可以支持HTTP的PATCH方法
11. 在拦截器匹配时，支持使用URL模式将其排除在拦截器的处理功能之外

Spring 3.2的MVC之外的功能更新：   
1. Autowired、Value、Bean注解可以作为元注解，用于创建自定义的注入和Bean声明注解
2. DateTimeFormat注解不再依赖于JodaTime，如果提供了JodaTime，就使用它，如果没有提供，使用JDK的SimpleDateFormat
3. Spring的声明式缓存提供了对JCache 0.5的支持
4. 支持定义全局的格式来解析和渲染日期与时间
5. 在集成测试中，能够配置和加载WebApplicationContext
6. 在集成测试中，能够针对request和session作用域的bean进行测试

### 4.3 Spring 4.0版本特性
1. 提供对WebSocket编程支持，包括支持JSR-356--Java API for WebSocket
2. 由于WebSocket本身仅仅了一种低层次的API，编程人员急需高层次的抽象，因此Spring 4.0在WebSocket基础之上提供了一个
更高层次的面向消息的编程模型，该模型基于SockJS，并且包含了对STOMP协议的支持
3. 新的消息模块（Message），很多的类型源于Spring Integration项目，这个消息模块支持前面所说的Spring自己在4.0中新增
的SockJS/STOMP功能，同时提供了基于模板的方式发布消息
4. 率先支持Java 8特性（据说是第一批支持Java 8的框架），比如lambda表达式，这让代码更加简洁和易读，尤其是在使用特定的
回调接口（比如RowMapper和JdbcTemplate）的时候
5. 支持Java 8的同时，提供了对JSR-310--Date与Time API的支持，在处理日期和时间时，为开发者提供了比java.util.Date和
java.util.Calendar更丰富的API
6. 为Groovy开发提供了更加顺畅的编程体验，尤其是支持非常便利地完全采用Groovy开发Spring应用。随这些一起提供的是来自于
Grails的BeanBuilder，借助它能够通过Groovy配置Spring应用
7. 添加条件化创建Bean功能，也就是说只有预定义和条件满足时才会创建所声明的Bean
8. Spring 4.0包含了Spring RestTemplate的一个新的异步实现，它会立即返回并且允许在操作完成之后执行回调
9. 添加了多项对JEE规范的支持，包括JMS 2.0、JTA 1.2、JPA 2.1和Bean Validationi 1.1

好，让我们从Spring 4.x开始吧！！！

## 5. Spring之Bean的装配（Wiring）
### 5.1 Spring配置的可选方案
作为开发人员，需要告诉Spring要创建哪些Bean，并且如何将他们装配在一起。Spring提供了三种主要的装配机制：
- 在XML中进行显式配置
- 在Java中进行显式配置
- 隐式的Bean发现机制和自动装配

虽然有这样多样化的装配机制，甚至它们可以互相搭配使用，但是权威资料建议的做法还是使用自动配置的机制。显式配置越少越好，
当必须要显式的配置Bean的时候（比如，有些源码不是由你来维护的，而当你需要为这些代码配置Bean的时候），推荐使用类型安全
并且比XML更加强大的JavaConfig。最后，只有当想要使用便利的XML命名空间，并且JavaConfig中没有同样的实现时，才考虑使用
XML。

### 5.2 自动化装配Bean
虽然显式装配技术非常有用，但是在便利性方式，最强大的还是Spring的自动化装配。

Spring从两个角度来实现自动化装配：
- 组件扫描（component scanning）：Spring会自动发现应用上下文中所创建的Bean
- 自动装配（autowiring）：Spring自动满足Bean之间的依赖

首先，我们创建一个CompactDisc类，Spring会发现并将其创建为一个Bean，然后，再创建一个CDPlayer类，让Spring自动的发现
和应用CompactDisc（也就是说，让Spring自动地将CompactDisc这个Bean注入到CDPlayer中来。

#### 5.2.1 创建可以被发现的Bean
首先可以确认的逻辑是：如果不将CompactDisc注入到CDPlayer中，CDPlayer是没有什么功用的，所以说：CDPlayer这个播放器必
须要依赖于CompactDisc才能完成它的使命。

为了在Spring中阐述这个例子，我们首先在Java中建立CompactDisc，创建一个接口，如下所示：
```java
package com.sinoiov.lhjh;

public interface CompactDisc {
    void play();
}
```
CompactDisc的具体内容我们不关注，所以我们将它定义为一个接口，作为接口，它明确请求方能对这个CD做的事情，就是播放（也
就是play()方法）。我们现在再写一个实现：
```java
package com.sinoiov.lhjh;
import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {
    private String title = "Sgt, Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing..." + title + " by " + artist);
    }
}
```
和CompactDisc接口一样，这个类的具体内容其实我们不关心，它唯一稍显特殊的地方是使用一个注解Component，这个简单的注解就
表明这个类是组件类，并且通知Spring要为这个类创建Bean。现在，我们没有必要再去显式的配置SgtPeppers这个Bean了，因为这个
类使用了Component注解，所以Spring会自动把它处理妥当。

然而组件扫描默认不是启用状态，我们需要在配置文件中通知Spring这么做，让Spring去帮助我们找到有Component注解的那些类并
为其创建Bean。我们这么做：
```java
package com.sinoiov.lhjh;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan
public class CDPlayerConfig {
}
```
类CDPlayerConfig通过Java代码定义了一个Spring的装配规则，细节问题我们以后可以再关注，就目前看到的，是这个类并没有显式
地声明任何Bean，只不过它使用了ComponentScan注解，这个注解就能够通知Spring启用组件自动扫描。

如果没有其他配置的话，ComponentScan默认会扫描与配置类相同的包（也就是这个配置类所在的包），现在，CDPlayerConfig这个类
位于com.sinoiov.lhjh这个包中，那么这个包及其所有子包下的带有Component注解的类，都会被Spring自动扫描到，如此以来，Spring
就能发现CompactDisc并且创建它的Bean。


如果你更倾向于使用XML来启用组件扫描，那么配置文件是这样的：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.sinoiov.lhjh"/>
</beans>
```
上面的配置做完之后，我们就已经正确的使用了Spring的组件自动扫描机制，现在我们还需要创建一个JUnit的测试类，来判断我们
的CompactDisc是不是真正的创建出来了。下面是程序清单：
```java
package com.sinoiov.lhjh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {
    @Autowired
    private CompactDisc cd;

    @Test
    public void cdShouldNotBeNull() {
        org.junit.Assert.assertNotNull(cd);
    }
}
```
使用这些功能，需要在工程依赖中增加JUnit和Spring Test模块这两项，如果你是Java类型的工程，请将它们的JAR包复制到
CLASSPATH中，如果你是Maven类型的工程，请将它们的依赖项增加到pom.xml中，如果你是Gradle类型的工程，请将它们的依
赖项增加到build.gradle文件中。

CDPlayerTest使用了Spring的SpringJUnit4ClassRunner，以便在测试开始的时候自动创建Spring应用上下文。注解
ContextConfiguration会告诉Spring去找到CDPlayerConfig这个类加载配置，因为CDPlayerConfig中包含了ComponentScan，
进一步的，因此，通过CDPlayerConfig就能扫描到配置了注解Component的那些类（也就是组件），所以最终的应用上下文中，
应该会包含CompactDisc的Bean（而且它就是我们前面配置的SgtPeppers的一个实例）。而且在这个测试用例中，我们会有测试
方法中的断言去验证注入到测试代码中的CompactDisc的实例cd不为空，如果它不为空，就意味着Spring能够自动发现CompactDisc
类，自动在Spring应用上下文中为其创建Bean，并将创建的Bean注入到测试类CDPlayerTest中。

在上面这样的配置中，com.sinoiov.lhjh及其子包中，所有带有@Component注解的类都会自动创建为Bean，也就是说，只要我们在
Java配置文件CDPlayerConfig（或者XML配置文件）中加一行@ComponentScan注解（或者context:component-scan节点），就能够
自动创建无数个Bean，真是既方便又划算。

#### 5.2.2 为组件扫描的Bean命名
在前面的例子中，我们没有明确的为Bean设置ID（比如前面的SgtPeppers），但是在实际中，Spring应用上下文中的所有Bean都会
有一个ID，默认的，它是将类名的第一个字母改成小写，本例中，就会是sgtPeppers。当然，我们也可以为这个Bean自己定义一个
ID，这很容易做到：
```java
package com.sinoiov.lhjh;
import org.springframework.stereotype.Component;

@Component(value = "lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {
    private String title = "Sgt, Pepper's Lonely Hearts Club Band";
    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing..." + title + " by " + artist);
    }
}
```
这里的lonelyHeartsClub就是我们为这个Bean指定的新的标识ID，而且，也可以进一步简写：
```java
@Component("lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {
    // TODO ...
}
```
这里还有另外一种规范，它不使用@Component注解，而是使用Java依赖注入规范中所提供的@Named注解来为Bean设
置ID，这也没有什么难的：
```java
import javax.inject.Named;

@Named("lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {
    // TODO ...
}
```
需要说明的是，@Component注解是Spring自带的，而@Named注解是JSR-330的，当前Spring对它是完全兼容的。但是有专家并不建议
使用@Named注解，理由是当我们在大量的代码中看到一个类的时候，它标记为@Named，事实上我们许多时候仍然不能很好的表明它是
做什么的（好像意思就是说不能见名知义）。
> 到底什么是JSR-330呢？它其实是Java EE 6中的一种规范，是对Dependency Injection for Java的一种规范，也就是在Java
EE中的依赖注入的行为规范的称谓。Java语言本身和它所带的JDK，只提供了一些语言的基本实现和功能支持，如果我们需要
开发企业级应用，比如高性能、高可用性的大型分布式系统，那么我们的开发和测试的工作量将是巨大的难以想像的，因此
SUN公司就在Java语言的基础上，做了很多扩展和增强，推出了Java EE，也就是Java语言企业级版本，它能够帮助我们快速
的开发和部署可移植的、健壮的、可伸缩的而且安全的服务器应用，它在Java的基础上，提供了一整套的Web服务、组件模
型、管理和通用API，其中主要有JDBC（提供统一的连接关系型数据库的接口）、RMI（远程方法调用，主要用于开发分布式
Java应用）、JMS（Java消息服务，主要提供企业消息服务）、JavaBeans（开放的标准的组件体系）、JTS（Java事务服务，
主要提供存取事务处理资源的开放标准）等等。在这种应用场景下，众多技术细节中的一个问题：Java类的创建和引用问题，就
已经变的非常复杂，比如我们有一个类叫User，在很多地方调用了它，而现在，这个类的创建方式发生了变化（也就是说它
的构造器可能需要改写或者重载）以适应新的需求，那么所有引用了它的地方都需要进行修改，这样的情况是类与类之间处
于高耦合状态，这些对象和资源的组织都是硬编码，灵活性不高，维护成本也居高不下，所以需要一种更好的方式解决这个
问题，具体做法是：凡是引用了User这个类的其他类，都在自己定义的时候，通过某种机制完成User的自动创建。而这个“
某种机制”的实现思想，就是在组件外部给它们配一个容器，这个容器控制对象的创建、并将对象所依赖的其他对象和资源
注入到这个对象中去，这种做法是对传统的依赖方自己创建自己所依赖的组件的一种反转，所以就叫做控制反转（Inverse
of Controller，IoC），所以，IoC本质上是一种解决问题的思想而不是具体的方法，这种承载了IoC思想的容器，就叫做
IoC容器。在这样一种机制之下，解决问题的过程是这样的：ClassA依赖于ClassB，ClassB又依赖于ClassC，IoC容器在创
建了ClassA之后，分析得知它依赖于ClassB，那么再创建ClassB，并将创建出来的ClassB注入到ClassA，进一步分析发现
ClassB又依赖于ClassC，那么重复这个创建的过程，并将创建出来的ClassC对象注入到刚创建的ClassB对象中，逐层处理
所有依赖，直到所有依赖者都被创建，此时ClassA这个对象就已经可以正常工作了（因为它的所有依赖问题都解决了嘛），
这就是依赖注入（Dependency Inject， DI），所以依赖注入本质上是对组件依赖和引用关系解决方案的一种总结和描述。
现在回归正题，JSR-330被发明出来的过程是这样的：在2004年3月，Spring 1.0就已经使用外部的配置文件来实现上面所
讲的控制反转及其容器以及依赖注入的功能，到了2004年10月JDK 1.5发布时，已经支持注解这种语法，所以2007年3月
Google Guice 1.0发布，就使用注解来实现这些功能，紧接着2007年11月，Spring 2.5也支持注解方式实现这些功能。然
而，新的问题出来了，随着时间的推移，各种IoC容器都出来了，它们对组件依赖方式的描述五花八门，所以，为了规范和
统一，JCP（Java Community Process，一个开放的国际组织， [http://baike.baidu.com/item/jcp](http://baike.baidu.com/item/jcp)）
于2009年10月份发布了JSR-330。JSR-330在javax.inject中对可注入、限定器、标记作用域、基于Spring的限定器、是否
单例等等，都做出了明确规定。

事实上，如果我们在一些正式的Spring项目中，Web应用从容器中启动的时候，通常我们会看到这样的日志输出：
```text
09:57:58.952 [RMI TCP Connection(5)-127.0.0.1] INFO  o.s.c.a.ClassPathBeanDefinitionScanner - JSR-330 'javax.inject.Named' annotation found and supported for component scanning
```
或者
```text
15:52:16.908 [RMI TCP Connection(3)-127.0.0.1] INFO  o.s.b.f.a.AutowiredAnnotationBeanPostProcessor - JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
```
这就是关于Spring的组件扫描与JSR-330的一些注解之间关系的最好证明。

#### 5.2.3 设置组件扫描的基础包
我们在前面使用的@ComponentScan注解没有设置任何属性，所以它是按照默认行为方式进行的组件扫描，实际中我们可能需要进行
更多的自定义，或者要扫描多个包。

至少有一种情况：我们想将配置类放在单独的包中，将它与组件包分开存放，如果这样的话，使用默认选项就不能满足需求了。
然而这很简单，我们很容易就能解决这个问题：
```java
package com.sinoiov.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = {"com.sinoiov.lhjh"})
public class CDPlayerConfig {
    // TODO: ...
}
```
这种方式，我们明确了配置类CDPlayerConfig本身在com.sinoiov.config这个包中，而我们指定要扫描的包在com.sinoiov.lhjh
这个包中。同时我们还看到了，basePackages这是一个数组，所以我们还可以为它指定多个值：
```java
@ComponentScan(basePackages = {"com.sinoiov.lhjh1", "com.sinoiov.lhjh2"})
public class CDPlayerConfig {
    // TODO: ...
}
```
是不是很酷？

当然了，更简单的：我们可以使用使用这种方式：
```java
@Configuration
@ComponentScan("com.sinoiov.lhjh")
public class CDPlayerConfig {
    // TODO: ...
}
```
然而，上面这种方式它有一个缺陷，这里所设置的基础包是String类型的，但它不是类型安全的，如果代码被重构的话，那么所指定
的包就有可能出现错误了。

怎么解决这个问题呢？我们可以将其指定为包中所包含的类或者接口：
```java
@Configuration
@ComponentScan(basePackageClasses = {CDPlayer.class, DVDPlayer.class})
public class CDPlayerConfig {
    // TODO: ...
}
```
这种做法的结果是：basePackageClasses的属性设置的数组中包含了若干个类，这些类所在的包，都会作为组件扫描的基础包。而
且，在这个样例中，我们指定的是具体的组件类CDPlayer和DVDPlayer，更进一步，在实际工作中，我们还可以考虑在要被扫描的
基础包中创建一个空的标记接口（Marker Interface），通过这种标记接口的方式，可以保持对代码重构的友好的接口引用，而且
同时能够避免任何实际的应用程序代码（想想吧，某一天需求说既不需要CDPlayer也不需要DVDPlayer了，它们被删除了，那么我
们的配置难道还要再修改、检查、测试一遍么？而当我们使用了这个空的标记接口，这一切都会变得非常优雅）。

现在，还有一个问题：在实际中，我们的每个组件对象都是独立的（就像SgtPeppers这个Bean一样）的可能是很小的，它们通常
都是存在依赖关系的，如果它们是独立的，那么我们配置了组件自动扫描就可以了，但是面对这种有复杂依赖关系的，我们就需要
使用Spring自动化配置中的自动装配来解决这个问题。

#### 5.2.4 通过为Bean添加注解实现自动装配
简单的讲，自动装配就是让Spring自动满足Bean的依赖的一种方法，在满足依赖的过程中，会在Spring上下文中寻找匹配某个Bena
所需求的其他Bean。声明自动装配，我们需要使用Spring的@Autowired注解。

比方说，我们在前面的CDPlayer类，在它的构造器上添加@Autowired注解，这表明当Spring创建CDPlayer这个Bean时，会通过
这个构造器来进行实例化并且会传入一个可以设置给CompactDisc类型的Bean：
```java
package com.sinoiov.player;

import org.springframework.bean.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;

    @Autowired
    public CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public void play() {
        cd.play();
    }
}
```
@Autowired注解不仅能够用在构造器上，还能够用在属性的setter方法上，比如说，如果CDPlayer有一个setCompactDisc()的方法，那么就可以这样写：
```java
package com.sinoiov.player;

import org.springframework.bean.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;
    
    @Autowired
    public void setCompactDisc(CompactDisc cd) {
        this.cd = cd;
    }
}

```
在Spring初始化Bean完成之后，它会尽可能的去满足Bean的依赖，在本例中，依赖是通过带有@Autowired注解的方式进行声明的，也就是CDPlayer依赖于CompactDisc。实际上，setter方法我们用过很多次了，它并没有什么特殊的，@Autowired注解可以用在类的任何方法上，假设CDPlayer有一个insertDisc()的方法，那么我们可以和上面一样完成它的装配，让它发挥完全相同的作用：
```java
package com.sinoiov.player;

import org.springframework.bean.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;
    
    @Autowired
    public void insertDisc(CompactDisc cd) {
        this.cd = cd;
    }
}

```
不管是构造器、setter方法，还是其他的方法，我们都可以让Spring尝试满足这样的要求：通过方法参数上所声明的依赖，进行自动装配。假如有且仅有一个Bean匹配依赖的话，那么这个Bean将会被装配进来。

如果没有匹配的Bean，那么Spring的应用上下文将会抛出一个异常。为了避免这种异常，我们需要对它的依赖进行检查。在一些条件下，我们可以指定这个装配是否是必须的：
```java
package com.sinoiov.player;

import org.springframework.bean.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;

    @Autowired(required = false)
    public CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }
}

```
将required属性值设置为false的话，Spring将会尝试自动装配，如果没有匹配的Bean，Spring会让这个Bean处于未装配状态，所以我们需要在代码中进行谨慎的处理，比如做非空判断，如果没有这样的检查判断机制，就有可能在程序运行期间出现NullPointerException。
如果有多个Bean能够满足依赖的情况下，Spring将会抛出一个异常，表明没有明确指定要选择那一个Bean进行自动装配。



# 10. 几个要点
## 10.1 过滤器（Filter）、拦截器（Interceptor）、监听器（Listener）的异同
相同之处：它们都是为了让开发人员专心开发业务逻辑，忽略一些会在很多地方都涉及的共通的功能（比如登录身份验证、接口调用安全限制、系统接口调用监控等特定业务需求）。

不同之处：
- 过滤器依赖于servlet，基于函数回调实现，因此可以拦截所有请求，在servlet容器初始化时进行初始化。最常见到的过滤器就是请求和响应内容转码过滤器，也就是在web.xml中配置`org.springframework.web.filter.CharacterEncodingFilter`。
- 拦截器依赖于SpringMVC框架而不依赖于servlet，是使用反射的机制实现的，因此从理论上讲性能和效率会比过滤器要差一点，特别要注意的是：拦截器只能拦截对Controller的请求，对直接访问静态资源是不能拦截的，不过正因为如此，拦截器可以获取到spring的IoC容器注入进去的Bean对象而过滤器是不可以的。要注意的是：假如你要写一个用户登录身份验证的机制，而恰恰你又使用拦截器实现的，此时，如果静态资源没有权限要求，那么是可以满足需求的，如果静态资源也有权限要求（必须登录才能访问，甚至是必须登录并且具备某个角色才能访问），那么使用拦截器就不行了。
- 监听器，是`javax.servlet.ServletContextListener`接口的实现类，跟随web应用启动而启动。典型的应用是web应用启动时加载外部的一些不想放入war包或者写入代码的配置，我也见到过一些人在监听器中获取网卡的地址或者机器硬件识别码，然后作为雪花算法的分布式ID生成的依据，因为这样可以自动地动态处理分布式环境下分布式ID生成时的workerId和centerId的问题，不需要人为的去每台分布式节点上配置（我之前在一些上市公司见到的大型系统有很多个分布式节点，人工管理配置也是十分困难的，此时这种机制就很有用）。

简单地：
filter距离tomcat最近，interceptor距离controller最近。请求进来之后，会先走filter（此时获取不到SpringWeb应用容器中的内容），然后再走interceptor（此时可以获取到SpringWeb容器中的内容），最后进入Controller的action。