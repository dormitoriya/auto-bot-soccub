package org.dormitory.autobotsoccub.sender.sendingstrategy;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.sender.MessageSender;
import org.telegram.telegrambots.api.objects.Message;

import static java.lang.String.valueOf;
import static java.util.Objects.nonNull;
import static org.dormitory.autobotsoccub.command.result.CommandResultConverter.buildDeleteMessage;
import static org.dormitory.autobotsoccub.command.result.CommandResultConverter.buildSendPhoto;
import static org.dormitory.autobotsoccub.utils.ChatsMessageId.*;

@AllArgsConstructor
public class SendToPrivateChatsProcessor implements SendingStrategy {

    private MessageSender sender;

    @Override
    public void execute(ChatData chatData) {
        chatData.getGameProcessUserPool().keySet().forEach(userId ->
                replyTo(userId.longValue(), chatData));
    }

    private void replyTo(Long chatId, ChatData chatData) {
        if(!getChatMessageId().isEmpty() && nonNull(getMessageIdByChatId(chatId))) {
            sender.tryReply(buildDeleteMessage(valueOf(chatId), getMessageIdByChatId(chatId)));
            removeFromChatMessageId(chatId);
        }
        Message message = sender.tryReply(buildSendPhoto(chatData.getCommandResult(), chatId));
        if (nonNull(message)) {
            putToChatMessageId(chatId, message.getMessageId());
        }
    }
}
