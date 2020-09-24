package com.milk.redisBucket.service;

/**  
 * @Description: 发布消息到队列中
 * @author wanghong
 * @date 2020/7/7 - 13:51
*/
public interface PublishService {

    /**
     * 发布消息到队列中
     */
    public void pulishDingDing(Integer integer);

}
