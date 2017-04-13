# Spring学习笔记
这是一个Spring框架应用示例工程，里面写了大量的注释，对于我们理解和掌握Spring的一些关键特性是非常有帮助的。

这也是我学习和理解Spring的一个过程，分享出来给有这方面需求的同学快速入门，希望对您有所帮助，如果你觉得好，请给
一个star，谢谢。

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