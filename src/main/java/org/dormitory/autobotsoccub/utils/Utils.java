package org.dormitory.autobotsoccub.utils;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

public class Utils {

    public static Long getChatId(Update update) {
        return getMessageFromUpdate(update).getChat().getId();
    }

    public static boolean isGroupChat(Update update) {
        return !isUserChat(update);
    }

    public static boolean isUserChat(Update update) {
        return getMessageFromUpdate(update).getChat().isUserChat();
    }

    public static Message getMessageFromUpdate(Update update) {
        return update.getCallbackQuery() != null
                ?  update.getCallbackQuery().getMessage()
                :  update.getMessage();
    }
}
