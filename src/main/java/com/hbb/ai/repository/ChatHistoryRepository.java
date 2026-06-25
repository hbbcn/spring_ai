package com.hbb.ai.repository;

import java.util.List;

/**
 * @ClassName ChatHistoryRepository
 * @Author huang.qingbin
 * @Date 2026/6/25 22:46
 * @Version 1.0
 */
public interface ChatHistoryRepository {
    /**
     * 保存会话记录
     * @param type 业务类型，如：chat、service、pdf
     * @param chatId 会话ID
     */
    void save(String type, String chatId);

    /**
     * 获取会话ID列表
     * @param type 业务类型，如：chat、service、pdf
     * @return 会话ID列表
     */
    List<String> getChatIds(String type);

}
