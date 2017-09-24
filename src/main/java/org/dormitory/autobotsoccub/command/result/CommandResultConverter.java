package org.dormitory.autobotsoccub.command.result;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CommandResultConverter {

    public static SendMessage fromCommandResult(CommandResult commandResult, Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getChatId(update));
        sendMessage.setText(commandResult.getReplyMessage());

        if (hasKeyboardMarkup(commandResult)) {
            sendMessage.setReplyMarkup(commandResult.getKeyboardMarkup());
        }

        return sendMessage;
    }

    private static String getChatId(Update update) {
        Long chatId = update.getMessage() != null
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();

        return valueOf(chatId);
    }

    private static boolean hasKeyboardMarkup(CommandResult commandResult) {
        return commandResult.getKeyboardMarkup() != null;
    }

    private static boolean hasReplyText(CommandResult commandResult) {
        return isNotBlank(commandResult.getReplyMessage());
    }
}