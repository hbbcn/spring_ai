package com.hbb.ai.controller;

import com.hbb.ai.repository.ChatHistoryRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @ClassName ChatController
 * @Author huang.qingbin
 * @Date 2026/6/24 22:05
 * @Version 1.0
 */
//@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
@CrossOrigin
@Tag(name = "演示controller", description = "演示controller")
public class ChatController {
    //由于使用了 @Qualifier，你需要将 @RequiredArgsConstructor 改为手动构造方法
// 或使用 @Autowired，因为 Lombok 的 @RequiredArgsConstructor 不支持 @Qualifier。
//    @Qualifier("ollamaChatClient")
//    @Autowired
//    private  ChatClient chatClient;
//
    @Qualifier("openAiChatClient")
    @Autowired
    private  ChatClient openAiChatClient;

    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    //构造方法注入
//    public ChatController(@Qualifier("openAiChatClient") ChatClient openAiChatClient){
//        this.openAiChatClient = openAiChatClient;
//    }
    @Autowired
    private ChatMemory chatMemory;


    //     @RequestMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    //prompt:提示语
    public Flux<String> ask(String prompt, String chatId) {
        // 1.保存会话id
        chatHistoryRepository.save("chat", chatId);
        return openAiChatClient.prompt()
                .user(prompt)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}

