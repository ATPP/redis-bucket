package com.milk.redisBucket.service.impl;

import com.milk.redisBucket.config.Constants;
import com.milk.redisBucket.enums.Token;
import com.milk.redisBucket.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * redis 令牌桶操作
 * @author hongwang
 */

@Service
@Slf4j
public class RateLimitServiceImpl implements RateLimitService {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Qualifier("getRedisScript")
    @Resource
    RedisScript<Long> ratelimitLua;
    @Qualifier("getInitRedisScript")
    @Resource
    RedisScript<Long> ratelimitInitLua;


/**
     * 初始化令牌桶
     *
     * @param key
     * @return
     *   * redis.pcall("HMSET",KEYS[1],
     *          "last_mill_second",ARGV[1],
     *          "curr_permits",ARGV[2],
     *          "max_burst",ARGV[3],
     *          "rate",ARGV[4],
     *          "app",ARGV[5])
     *
     *          last_mill_second 最后时间毫秒
     *          curr_permits 当前可用的令牌
     *          max_burst 令牌桶最大值
     *          rate 每秒生成几个令牌
     *          app 应用
     */

    @Override
    public Token initToken(String key) {
        Token token = Token.SUCCESS;
        Long currMillSecond = stringRedisTemplate.execute(
                (RedisCallback<Long>) redisConnection -> redisConnection.time()
        );
        Long accquire = stringRedisTemplate.execute(ratelimitInitLua,
                Collections.singletonList(getKey(key)), currMillSecond.toString(), "1", "10", "10", "skynet");
        if (accquire == 1) {
            token = Token.SUCCESS;
        } else if (accquire == 0) {
            token = Token.SUCCESS;
        } else {
            token = Token.FAILED;
        }
        return token;
    }
/**
     * 获得一个令牌
     *
     * @param key
     * @return
     */

    @Override
    public Token accquireToken(String key) {
        return accquireToken(key, 1);
    }

/**
     * 获得多个令牌
     * @param key
     * @param permits
     * @return
     */

    @Override
    public Token accquireToken(String key, Integer permits) {
        Token token = Token.SUCCESS;
        Long currMillSecond = stringRedisTemplate.execute(
                (RedisCallback<Long>) redisConnection -> redisConnection.time()
        );
        Long accquire = stringRedisTemplate.execute(ratelimitLua,
                Collections.singletonList(getKey(key)), permits.toString(), currMillSecond.toString());
        if (accquire == 1) {
            token = Token.SUCCESS;
        } else {
            token = Token.FAILED;
        }
        return token;
    }

    public String getKey(String key) {
        return Constants.RATE_LIMIT_KEY + key;
    }

}

