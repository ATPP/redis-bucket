package com.milk.redisBucket.config;
import com.duobaoyu.middleware.redis.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @Description: redis限流乱lua配置
 * @author wanghong
 * @date 2020/7/7 - 13:54
*/

@Configuration
public class RedisRateLimitConfig extends RedisConfig {

    @Bean("ratelimitLua")
    public DefaultRedisScript getRedisScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource("ratelimit.lua"));
        redisScript.setResultType(java.lang.Long.class);
        return redisScript;
    }
    @Bean("ratelimitInitLua")
    public DefaultRedisScript getInitRedisScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource("ratelimitInit.lua"));
        redisScript.setResultType(java.lang.Long.class);
        return redisScript;
    }

}
