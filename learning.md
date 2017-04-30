# 真正理解和掌握Spring框架
## 1. 理解和掌握Spring框架的意义
![https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/spring%20logo.png](https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/spring%20logo.png)

1. 从发展的层面：最流行的企业级框架
2. 从技术的层面：降低开发难度，提高开发效率
3. 从项目的层面：促进项目的快速开发、快速部署、快速见效

## 2. 属性注入
### 2.1 什么是属性注入
其实很简单，就是将指定的值设置给我们的目标类的目标属性，比如有一个User类，我们要针对它的一个实例user，将它的nickName
属性的值设置为“海绵宝宝”，这就是属性注入。

### 2.2 怎么做
#### 2.2.1 最简单的，通过set方法（许多地方将这类方法统统叫setter）
先定义一个类
```java
package com.sinoiov.code.spring;

import com.google.gson.Gson;

/**
 * 用户信息类
 * Created by lidawei on 2017/4/30.
 */
public class User {
    private int gender;
    private String nickName;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
```
现在这么调用：
```java
package com.sinoiov.code.spring;

import org.junit.Test;

/**
 * 用户信息测试类
 * Created by lidawei on 2017/4/30.
 */
public class UserTest {
    @Test
    public void testIt() throws Exception {
        User user = new User();
        user.setNickName("海绵宝宝");
    }
}
```
简单吧！再看下一种，也很简单：
#### 2.2.2 通过构造器
```java
package com.sinoiov.code.spring;

import com.google.gson.Gson;

/**
 * 用户信息类
 * Created by lidawei on 2017/4/30.
 */
public class User {
    private int gender;
    private String nickName;

    public User(int gender, String nickName) {
        this.gender = gender;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
```
然后我们这么调用：
```java
package com.sinoiov.code.spring;

import org.junit.Test;

/**
 * 用户信息测试类
 * Created by lidawei on 2017/4/30.
 */
public class UserTest {
    @Test
    public void testIt() throws Exception {
        User user = new User(1, "海绵宝宝");
        System.out.println(user);
    }
}
```
#### 2.2.3 通过配置进行注入
这种方式是最复杂的，它的核心思想是：定义一个通用的能够创建各种类的工厂（我们称之为Bean工厂），然后通过配置文件的方式
来管理这些类的创建和调用依赖以及销毁。那么问题来了，既然有上面两种如此简单的方式，我们为什么还要弄这么一种复杂的方
式？？？   
答案却很简单：因为上面两种方式，对于企业级大规模的应用，存在难以克服的障碍和困难。比如说一个大型系统，我们创建了用户
类或者订单类或者商品类等等之类的很多很多类的这样的情况，然后又有N个场景都在使用这些类，都在调用这些类，这个时候，如
果需求变了，字段有增减，或者业务业务规则有变化，我们怎么办？我们得找到所有凡是调用了这些类的地方，然后统统改一遍，再
然后，就是反复核对有没有地方漏了，而且如果要上线，凡是改动过的地方，都要重新测试，想想这个成本得有多大。

下面让我们看看如何实现它？

我们继续使用2.2.1节中定义的User类，尝试使用通用的方法创建它，那么我们要怎么做呢？

第一步：创建一个配置文件：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans>
    <bean id="userHMBB" class="com.sinoiov.code.spring.User">
        <property name="gender" type="int" value="1"/>
        <property name="nickName" type="String" value="海绵宝宝"/>
    </bean>
</beans>
```
起名叫applicationContext.xml，放在Maven工程的resources目录下。这个配置文件很简单，就是定义一个Bean，然后指定它是从
哪个类来创建，然后它有两个属性，我们分别要给它们指定值。

第二步：写一个Bean工厂的类：
```java
package com.sinoiov.code.spring;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Bean工厂
 * Created by lidawei on 2017/4/30.
 */
public class BeanFactory<T> {
    private static final BeanFactory instance = new BeanFactory();
    private static List<Element> beans = new ArrayList<>();

    static {
        // 解析配置文件
        InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml");
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                beans.add((Element) i.next());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static BeanFactory getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public T create(String beanId, Class<T> clazz) throws Exception {
        T inst = null;
        for (Element element : beans) {
            String beanIdName = ((Attribute)element.selectObject("@id")).getValue();
            String className = ((Attribute)element.selectObject("@class")).getValue();
            if (beanIdName.equals(beanId)) {
                Class<T> c = (Class<T>) Class.forName(className);
                inst = c.newInstance();
                List<Element> properties = (List<Element>)element.selectNodes("property");
                for (Element property : properties) {
                    String propertyName = ((Attribute)property.selectObject("@name")).getValue();
                    String propertyType = ((Attribute)property.selectObject("@type")).getValue();
                    String propertyValue = ((Attribute)property.selectObject("@value")).getValue();
                    String methodName = "set" + String.valueOf(propertyName.charAt(0)).toUpperCase() + propertyName.substring(1);
                    Class<?> methodParameterType = null;
                    Object val = null;
                    // 在这里应该对所有类型都做一次处理，本代码仅是示例，所以只处理Integer、Long和String
                    if (propertyType.equals("int")) {
                        methodParameterType = int.class;
                        val = Integer.parseInt(propertyValue);
                    } else if (propertyType.equals("long")) {
                        methodParameterType = long.class;
                        val = Long.valueOf(propertyValue);
                    } else if (propertyType.equals("String")) {
                        methodParameterType = String.class;
                        val = String.valueOf(propertyValue);
                    }
                    Method setter = clazz.getMethod(methodName, methodParameterType);
                    setter.invoke(inst, val);
                }
            }

            if (inst != null)
                return inst;
        }


        return null;
    }
}
```
这个类的主要思想是：通过指定类型的传入，反射它的所有属性，和配置文件XML中的定义进行对照，匹配的情况下，实例化这个类
对象，然后得到并调用它的setter方法进行属性值的设置（当然还有另外一种做法，就是直接调用相对应的属性字段，设置它的值
而不必通过setter方法，但是结果是一样的），完成之后返回这个实例对象。

现在我们可以看到，只要我们定义了这个一个配置文件和Bean工厂，它们作为通用组件存在，然后我们在外面要使用任何类，只需要
完成这个类的定义和相应的配置文件的定义就好了，它们的对应关系正确了，就能通过这个工厂和配置文件把它创建出来。

来，我们写个测试类，测一下它：
```java
package com.sinoiov.code.spring;

import org.junit.Test;

/**
 * 用户信息测试类
 * Created by lidawei on 2017/4/30.
 */
public class UserTest {
    @Test
    public void testIt() throws Exception {
        User user = (User) BeanFactory.getInstance().create("userHMBB", User.class);
        System.out.println(user);
    }
}
```
运行结果：
![https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/user.png](https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/user.png)

现在我们不改Bean工厂，我们去写一个新的Order订单类，然后再更新一下配置文件，看能不能自动创建出来：
```java
package com.sinoiov.code.spring;

import com.google.gson.Gson;

/**
 * 订单类
 * Created by lidawei on 2017/4/30.
 */
public class Order {
    private String id;
    private long createTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
```
配置文件：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans>
    <bean id="userHMBB" class="com.sinoiov.code.spring.User">
        <property name="gender" type="int" value="1"/>
        <property name="nickName" type="String" value="海绵宝宝"/>
    </bean>
    <bean id="myOrder" class="com.sinoiov.code.spring.Order">
        <property name="id" type="String" value="508147A9-CBD4-4e67-850B-9EB050A91B41"/>
        <property name="createTimestamp" type="long" value="123456789"/>
    </bean>
</beans>
```
更新测试用例：
```java
package com.sinoiov.code.spring;

import org.junit.Test;

/**
 * 用户信息测试类
 * Created by lidawei on 2017/4/30.
 */
public class UserTest {
    @Test
    public void testIt() throws Exception {
        User user = (User) BeanFactory.getInstance().create("userHMBB", User.class);
        System.out.println(user);
    }

    @Test
    public void testOrder() throws Exception {
        Order order = (Order)BeanFactory.getInstance().create("myOrder", Order.class);
        System.out.println(order);
    }
}
```
看结果：
![https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/order.png](https://raw.githubusercontent.com/ccpwcn/GitRepository/master/resource/spring/order.png)
