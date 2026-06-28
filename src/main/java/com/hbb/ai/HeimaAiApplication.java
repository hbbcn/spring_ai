package com.hbb.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.vectorstore.redis.autoconfigure.RedisVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude =
        RedisVectorStoreAutoConfiguration.class)
@MapperScan("com.hbb.ai.mapper")
public class HeimaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeimaAiApplication.class, args);
    }

}
