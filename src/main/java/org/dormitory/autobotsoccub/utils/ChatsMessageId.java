package org.dormitory.autobotsoccub.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.Getter;

public class ChatsMessageId {

    @Getter private static BiMap<Long, Integer> chatMessageId = HashBiMap.create();

    public static Integer getMessageIdByChatId(Long chatId) {
        return chatMessageId.get(chatId);
    }

    public static void putToChatMessageId(Long chatId, Integer messageId) {
        chatMessageId.put(chatId, messageId);
    }

    public static void removeFromChatMessageId(Long chatId) {
        chatMessageId.remove(chatId);
    }

}
