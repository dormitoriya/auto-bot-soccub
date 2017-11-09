package org.dormitory.autobotsoccub.sender.sendingstrategy;

import org.dormitory.autobotsoccub.bot.chat.ChatData;

public interface SendingStrategy {

    void execute(ChatData ChatData);
}