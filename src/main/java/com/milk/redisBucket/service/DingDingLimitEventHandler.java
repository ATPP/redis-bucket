package com.milk.redisBucket.service;

import com.alibaba.fastjson.JSON;
import com.milk.redisBucket.config.Constants;
import com.milk.redisBucket.event.DingDingLimitEvent;
import com.milk.redisBucket.spring.SpringContextHolder;
import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * event 处理
 */
@Component
public class DingDingLimitEventHandler implements EventHandler<DingDingLimitEvent> {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void onEvent(DingDingLimitEvent dingDingLimitEvent, long l, boolean b) throws Exception {
        // 处理event
        System.out.println("收到消息，开始处理消息" + JSON.toJSONString(dingDingLimitEvent));
        dealDingDingLimit(dingDingLimitEvent);
    }

    private void dealDingDingLimit(DingDingLimitEvent dingDingLimitEvent) {
        PublishService publishService = SpringContextHolder.getBean("publishService");
        RateLimitService rateLimitService = SpringContextHolder.getBean("rateLimitService");
        if (rateLimitService.accquireToken(Constants.DING_DING_RATE_LIMIT_KEY).isSuccess()) {
            System.out.println("----请求发往钉钉" + atomicInteger.incrementAndGet());
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("继续进入队列" + dingDingLimitEvent.getId());
            publishService.pulishDingDing(Integer.valueOf(dingDingLimitEvent.getId()));
        }
    }

}
