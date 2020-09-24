package com.milk.redisBucket.controller;

import com.milk.redisBucket.enums.Token;
import com.milk.redisBucket.service.PublishService;
import com.milk.redisBucket.service.RateLimitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rateLimit")
@Api(tags = "redis令牌桶相关接口")
public class RateLimitController {

    @Resource
    private RateLimitService rateLimitService;
    @Resource
    private PublishService publishService;

    @PostMapping("/in")
    public void testIn() {
        System.out.println("-------------------in--------------------");
    }

    @GetMapping(value = "/init.do")
    @ApiOperation(value = "初始化数据")
    public Token initToken(@RequestParam String key) {
        return rateLimitService.initToken(key);
    }

    @GetMapping(value = "/accquireToken.do")
    @ApiOperation(value = "获取令牌")
    public Token accquireToken(@RequestParam String key, @RequestParam Integer permits) {
        return rateLimitService.accquireToken(key, permits);
    }

    @PostMapping("/publish")
    @ApiOperation(value = "往消息队列中添加消息")
    public void publish() {
        for (int i = 1; i < 100; i++) {
            publishService.pulishDingDing(i);
        }
    }
}