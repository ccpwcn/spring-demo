package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.service.Knight;
import com.sinoiov.lhjh.service.Quest;

/**
 * 勇敢的骑士
 * Created by lidawei on 2017/4/5.
 */
public class BraveKnight implements Knight {
    private Quest quest;

    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }
}
