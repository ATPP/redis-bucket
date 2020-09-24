package com.milk.redisBucket.service;

import com.milk.redisBucket.enums.Token;

/**  
 * @Description: 令牌桶
 * @author wanghong
 * @date 2020/7/7 - 13:52
*/
public interface RateLimitService {

    /**
     * 初始化
     * @param key
     * @return
     */
    public Token initToken(String key);

    /**
     * 获得一个令牌
     *
     * @param key
     * @return
     */
    public Token accquireToken(String key);

    /**
     * 获得多个令牌
     *
     * @param key
     * @return
     */
    public Token accquireToken(String key, Integer permits);
}
