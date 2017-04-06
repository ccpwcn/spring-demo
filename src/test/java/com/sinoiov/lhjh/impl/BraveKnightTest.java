package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.service.Quest;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 勇士测试类
 * Created by lidawei on 2017/4/5.
 */
public class BraveKnightTest {
    @Test
    public void knightShouldEmbarkOnQuest() {
        // 创建Mock对象
        Quest mockQuest = mock(Quest.class);

        // 创建一个英勇骑士，使用这个Mock对象
        BraveKnight knight = new BraveKnight(mockQuest);

        // 勇士行动
        knight.embarkOnQuest();

        // 使用Mockito框架验证Quest的Mock实现被调用了，并且它的embark方法仅被调用了一次
        verify(mockQuest, times(1)).embark();
    }
}
