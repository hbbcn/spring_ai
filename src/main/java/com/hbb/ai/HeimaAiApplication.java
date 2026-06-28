package com.hbb.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication(exclude =
//        RedisVectorStoreAutoConfiguration.class)
@SpringBootApplication
@MapperScan("com.hbb.ai.mapper")
public class HeimaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeimaAiApplication.class, args);
    }

}
