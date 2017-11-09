package org.dormitory.autobotsoccub.sender.sendingstrategy;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.sender.MessageSender;

import static java.util.Objects.isNull;
import static org.dormitory.autobotsoccub.command.result.CommandResultConverter.buildSendMessage;
import static org.dormitory.autobotsoccub.command.result.CommandResultConverter.buildSendPhoto;

@AllArgsConstructor
public class SendToAllChatsProcessor implements SendingStrategy {

    private MessageSender sender;

    @Override
    public void execute(ChatData chatData) {
        chatData.getGameProcessUserPool().keySet().forEach(userId ->
                replyTo(userId.longValue(), chatData.getCommandResult()));
        replyTo(chatData.getChatId(), chatData.getCommandResult());
    }

    private void replyTo(Long chatId, CommandResult commandResult) {
        if(isNull(commandResult.getGameStateImage())) {
            sender.tryReply(buildSendMessage(commandResult, chatId));
            return;
        }
       sender.tryReply(buildSendPhoto(commandResult, chatId));
    }
}