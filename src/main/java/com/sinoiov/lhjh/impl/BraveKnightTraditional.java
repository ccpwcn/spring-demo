package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.service.Knight;
import com.sinoiov.lhjh.service.Quest;

/**
 * <p>勇敢的骑士</p>
 * <p>
 *     这是一个传统的编程方法，这种方法的最大的问题在于：作为骑士，它不光要关注自己探险的任务，还要关注游吟诗人对自己的传唱
 *     这个行为，甚至要关注游吟诗人具体是谁？由谁指派给自己（也就是由谁通过构造器如何传递给自己），在这种情况下，我们需要更
 *     进一步的处理更多的细节：没有游吟诗人怎么办，游吟诗人指派为空了怎么办，现在的需求变更为游吟诗人这个作为可选项而不是必
 *     选项了怎么办，或者需求变更为对于骑士的探险行动我们不需要使用游吟诗人进行传唱而是改用日志文件进行记录，而且对于这些业
 *     务，我们还有服务器性能监控、安全验证等等的大量需求需要达成。。。
 *     这样一些复杂的需求被引入的时候，可能我们需要重载构造器，我们要判断是否为空，我们要分析什么时间应该抛出异常什么时间需
 *     要忽略它，甚至要引入继承类以应对不同的业务场景的需求，最后写出一个非常复杂的模块以完成这一个功能，然后我们再把它上传
 *     到Nexus私服以供需求方调用，这种情况又要约定调用接口协议。。。
 *     <strong>结果就是：</strong>一个简单的BraveKnight类，在这种情况下，将会变的极其复杂，后期应对不同需求的变化时，它的
 *     维护和变更工作将会变的极其困难，甚至最终变成一个不可维护的模块而导致我们必须花更多的时间和人力去推倒重写。最理想的情
 *     况也只能是新写一个模块完成这些功能，然后调用方再去判断什么情况下要调用A模块而什么情况下要调用B模块，而这，已经是最好
 *     的情况了，即使如此，整体上这仍然是一个糟糕的设计，它让整个工程和项目变的非常麻烦和复杂，成本居高不下。
 * </p>
 * Created by lidawei on 2017/4/5.
 */
public class BraveKnightTraditional implements Knight {
    private Quest quest;
    private Minstrel minstrel;

    public BraveKnightTraditional(Quest quest, Minstrel minstrel) {
        this.quest = quest;
        this.minstrel = minstrel;
    }

    @Override
    public void embarkOnQuest() {
        minstrel.singBeforeQuest();
        quest.embark();
        minstrel.singAfterQuest();
    }
}
