package org.dormitory.autobotsoccub.sender.sendingstrategy;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.sender.MessageSender;

import static org.dormitory.autobotsoccub.command.result.CommandResultConverter.buildSendMessage;

@AllArgsConstructor
public class SendToGeneralChatProcessor implements SendingStrategy {

    private MessageSender sender;

    @Override
    public void execute(ChatData chatData) {
        sender.tryReply(buildSendMessage(chatData.getCommandResult(), chatData.getChatId()));
    }
}

