package com.sinoiov.lhjh.config;

import com.sinoiov.lhjh.impl.BraveKnight;
import com.sinoiov.lhjh.impl.SlayDragonQuest;
import com.sinoiov.lhjh.service.Knight;
import com.sinoiov.lhjh.service.Quest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>勇士行动配置</p>
 * <p>
 *     不管是使用Java配置还是XML配置，依赖注入所带来的收益是相同的，BraveKnight依赖于Quest，但是它不必关心到底是哪个
 *     具体的Quest实现（或者说是什么类型的Quest），也不需要知道这个Quest来自哪里，与之类似的，SlayDragonQuest依赖于
 *     PrintStream，但是它不必关心这个PrintStream到底是什么样的，或者是什么类型，只有Spring通过配置，能够了解这些
 *     组成部分是如何装配起来的。
 * </p>
 * <p>
 *     在这种情况下，就可以不改变所依赖的类的情况下（也就是不改变已经开发好的类），修改依赖关系。因为我们只要变更了配置
 *     （这个配置的变更可能通过一个KnightConfig这样一个Java类的方式，也可能是通过beans.xml这样的方式，就已经修改了它的
 *     依赖关系。从而达到“昨天这个业务类BusinessClass还依赖于类A，今天我们没有改代码，仅仅是变更了配置，它就依赖于类B了”
 * </p>
 * Created by lidawei on 2017/4/5.
 */
@Configuration
public class KnightConfig {
    @Bean
    public Knight knight() {
        return new BraveKnight(quest());
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }
}
