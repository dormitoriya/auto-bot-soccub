package org.dormitory.autobotsoccub.bot.chat;

import lombok.Builder;
import lombok.Getter;
import org.telegram.telegrambots.api.objects.Update;

import static org.dormitory.autobotsoccub.bot.SoccubBot.*;

@Builder
public class ChatThread extends Thread {

    @Getter private ChatData chatData;

    public void updateChatData(Update update) {
        chatData.seCommandNameAndUserFromUpdate(update);
    }

    @Override
    public void run() {
        getEventBus().post(chatData);
    }
}
