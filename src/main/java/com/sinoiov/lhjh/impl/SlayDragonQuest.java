package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.service.Quest;

import java.io.PrintStream;

/**
 * 杀死暴龙行动
 * Created by lidawei on 2017/4/5.
 */
public class SlayDragonQuest implements Quest {
    private PrintStream printStream;

    public SlayDragonQuest(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void embark() {
        printStream.println("Embarking on quest to slay a dragon!");
    }
}
