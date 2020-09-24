package com.milk.redisBucket.service.impl;

import com.milk.redisBucket.event.DingDingLimitEvent;
import com.milk.redisBucket.service.PublishService;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 发布消息到队列中
 */

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {

    @Resource
    private RingBuffer<DingDingLimitEvent> dingDingLimit;

    @Override
    public void pulishDingDing(Integer integer) {
        //获取下一个Event槽的下标
        long sequence = dingDingLimit.next();
        try {
            //给Event填充数据
            DingDingLimitEvent event = dingDingLimit.get(sequence);
            event.setId(integer + "");
            event.setValue("value" + integer);
            log.info("往消息队列中添加消息：{}", event);
        } catch (Exception e) {
            log.error("failed to add event to dingDingLimitRingBuffer for : e = {},{}", e, e.getMessage());
        } finally {
            //发布Event，激活观察者去消费，将sequence传递给改消费者
            //注意最后的publish方法必须放在finally中以确保必须得到调用；如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            dingDingLimit.publish(sequence);
        }
    }

}

