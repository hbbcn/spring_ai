package com.hbb.ai.controller;

import com.hbb.ai.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName CustomerServiceController
 * @Author huang.qingbin
 * @Date 2026/6/28 1:14
 * @Version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("ai")
public class CustomerServiceController {

    private final ChatClient serviceChatClient;

    private final ChatHistoryRepository chatHistoryRepository;


    @RequestMapping(value = "service",produces = "text/html;charset=utf-8")
    public String service(String prompt, String chatId){
        // 保存会话id
        chatHistoryRepository.save("service", chatId);
//        Flux<ChatResponse> chatResponseFlux = serviceChatClient.prompt()
//                .user(prompt)
//                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
//                .stream()
//                .chatResponse();
        return serviceChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .content();
    }

    private Flux<String> streamWithTokenTracking(Flux<ChatResponse> chatResponseFlux, String userId, String type, String prompt) {
        StringBuilder completionBuilder = new StringBuilder();
        AtomicLong promptTokensRef = new AtomicLong(0);
        AtomicLong completionTokensRef = new AtomicLong(0);

        return chatResponseFlux
                .mapNotNull(chatResponse -> {
                    if (chatResponse.getMetadata() != null && chatResponse.getMetadata().getUsage() != null) {
                        var usage = chatResponse.getMetadata().getUsage();
                        if (usage.getTotalTokens() > 0) {
                            promptTokensRef.set(usage.getPromptTokens());
                            completionTokensRef.set(usage.getCompletionTokens());
                        }
                    }
                    if (chatResponse.getResult() != null && chatResponse.getResult().getOutput() != null) {
                        String text = chatResponse.getResult().getOutput().getText();
                        if (text != null && !text.isEmpty()) {
                            completionBuilder.append(text);
                            return text;
                        }
                    }
                    return null;
                })
                .doFinally(signal -> {
//                    int pTokens = (int) promptTokensRef.get();
//                    int cTokens = (int) completionTokensRef.get();
//                    String completion = completionBuilder.toString();
//                    tokenUsageLogService.recordUsage(userId, type, prompt, completion, pTokens, cTokens);


                });
    }


}

