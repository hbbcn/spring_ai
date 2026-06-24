package com.hbb.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CommonConfig
 * @Author huang.qingbin
 * @Date 2026/6/24 22:01
 * @Version 1.0
 */

@Configuration
public class CommonConfiguration {


    @Bean("ollamaChatClient")
    public ChatClient chatClient(OllamaChatModel ollamaChatModel) {
        // 使用模型工厂创建模型
        return ChatClient.builder(ollamaChatModel)
                .defaultSystem("You are a helpful assistant.")
                // 添加一个日志拦截器
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Bean("openAiChatClient")
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem("你是一个小团团")
                .build();
    }

}

