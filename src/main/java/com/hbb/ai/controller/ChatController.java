package com.hbb.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
public class ChatController {
//由于使用了 @Qualifier，你需要将 @RequiredArgsConstructor 改为手动构造方法
// 或使用 @Autowired，因为 Lombok 的 @RequiredArgsConstructor 不支持 @Qualifier。
    @Qualifier("ollamaChatClient")
    @Autowired
    private  ChatClient chatClient;

    @Qualifier("openAiChatClient")
    @Autowired
    private  ChatClient openAiChatClient;




     @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
//    @RequestMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    //prompt:提示语
    public Flux<String> ask(String prompt) {
        return openAiChatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }
}

