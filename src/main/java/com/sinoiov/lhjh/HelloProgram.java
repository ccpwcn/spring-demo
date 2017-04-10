package com.sinoiov.lhjh;

import com.sinoiov.lhjh.service.Knight;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * HelloWorld 应用程序
 * Created by lidawei on 2017/4/5.
 */
public class HelloProgram {
    public static void main(String[] args) {
        method2();
    }

    private static void method2() {
        // HelloProgram这个类、main这个方法、乃至method2这个方法，都根本不知道执行探险任务的到底是哪个类
        // 他们仅仅是从knights.xml中加载应用程序的上下文，然后调用embarkOnQuest方法即可，他们甚至不知道BraveKnight的存在
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("knights.xml");
        Knight knight = (Knight) context.getBean("knight");
        knight.embarkOnQuest();
        context.close();
    }
}
