package com.milk.redisBucket.event;

import com.lmax.disruptor.EventFactory;

/**  
 * @Description: 令牌桶工厂
 * @author wanghong
 * @date 2020/7/7 - 13:57
*/
public class DingDingLimitEventFactory implements EventFactory<DingDingLimitEvent> {

    @Override
    public DingDingLimitEvent newInstance() {
        return new DingDingLimitEvent();
    }
}
