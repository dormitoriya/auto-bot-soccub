package org.dormitory.autobotsoccub.command;

import com.google.common.eventbus.Subscribe;
import org.dormitory.autobotsoccub.bot.chat.ChatData;

public interface Command {

    @Subscribe
    void execute(ChatData chatData);
}
