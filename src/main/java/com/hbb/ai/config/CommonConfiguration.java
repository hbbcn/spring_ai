package com.hbb.ai.config;

import com.hbb.ai.constants.SystemConstants;
import com.hbb.ai.tools.CourseTools;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @ClassName CommonConfig
 * @Author huang.qingbin
 * @Date 2026/6/24 22:01
 * @Version 1.0
 */

@Configuration
public class CommonConfiguration implements WebMvcConfigurer {


    @Bean
    public VectorStore vectorStore(OpenAiEmbeddingModel openAiEmbeddingModel){
        return SimpleVectorStore.builder(openAiEmbeddingModel)
                .build();
    }

    //跨域：允许所有来源的请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // 允许所有来源，开发环境使用
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)  // 默认保留最近20条消息（约10轮对话）
                .build();
    }

    @Bean("ollamaChatClient")
    public ChatClient chatClient(OllamaChatModel ollamaChatModel) {
        // 使用模型工厂创建模型
        return ChatClient.builder(ollamaChatModel)
                .defaultSystem("You are a helpful assistant.")
                // 添加一个日志拦截器
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    // 使用 OpenAI 模型
    //实现会话记忆功能
    @Bean("openAiChatClient")
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
//                        MessageChatMemoryAdvisor.builder(chatMemory)
//                                .order(0)                              // int：Advisor 执行顺序
//                                .scheduler(Schedulers.boundedElastic()) // Scheduler：异步调度器                Update available! Run: winget upgrade Anthropic.ClaudeCode
//                                .build()
                        // 使用默认的会话记忆功能
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultSystem(SystemConstants.GAME_SYSTEM_PROMPT)
                .build();
    }


    @Bean
    public ChatClient serviceChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory, CourseTools courseTools) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultSystem(SystemConstants.SERVICE_SYSTEM_PROMPT)
                .defaultTools(courseTools)
                .build();
    }


}

