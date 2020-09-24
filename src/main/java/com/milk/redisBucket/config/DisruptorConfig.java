package com.milk.redisBucket.config;

import com.milk.redisBucket.event.DingDingLimitEvent;
import com.milk.redisBucket.event.DingDingLimitEventFactory;
import com.milk.redisBucket.service.DingDingLimitEventHandler;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: Disruptor队列配置
 * @author wanghong
 * @date 2020/7/7 - 14:34
 */

@Configuration
public class DisruptorConfig{

    @Bean("dingDingLimit")
    public RingBuffer<DingDingLimitEvent> dingDingLimitlRingBuffer() {
        //定义用于事件处理的线程池
        final AtomicInteger index = new AtomicInteger(1);
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread((ThreadGroup) null, r, "disruptor-thread-" + index.getAndIncrement());
            }
        };

        //指定事件工厂
        EventFactory<DingDingLimitEvent> eventFactory = new DingDingLimitEventFactory();

        //指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 256;

        //单线程模式，获取额外的性能
        Disruptor<DingDingLimitEvent> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory,
                ProducerType.SINGLE, new YieldingWaitStrategy());

        //设置事件业务处理器---消费者
        disruptor.handleEventsWith(new DingDingLimitEventHandler());

        // 启动disruptor线程
        disruptor.start();

        //获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<DingDingLimitEvent> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }

}

