package com.sinoiov.lhjh.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息发送公共组件，用于发Push
 * Created by lidawei on 2017/5/21.
 */
@Component
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private List<String> queue = new ArrayList<String>();

    public void add(String msg) {
        queue.add(msg);
    }

    public void printAllQueueMessage() {
        int index = 0;
        for (String msg : queue) {
            logger.info("index:{}, message:{}", index++, msg);
        }
    }
}
