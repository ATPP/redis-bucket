package com.milk.redisBucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@ComponentScan("com.milk")
@ControllerAdvice
@EnableSwagger2
@EnableTransactionManagement
@EnableAsync
public class RedisBucketApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisBucketApplication.class, args);
        System.out.println("RedisBucket is working");
    }
}
