package com.sinoiov.lhjh.impl;

import java.io.PrintStream;

/**
 * 游吟诗人类
 * Created by lidawei on 2017/4/6.
 */
public class Minstrel {
    private PrintStream stream;

    public Minstrel(PrintStream stream) {
        this.stream = stream;
    }

    /**
     * 探险之前
     */
    public void singBeforeQuest() {
        stream.println("Fa la la, the knight is so brave");
    }

    /**
     * 探险之后
     */
    public void singAfterQuest() {
        stream.println("Tee hee hee, the brave knight did embark on a quest");
    }
}
