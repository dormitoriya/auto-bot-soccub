package org.dormitory.autobotsoccub.command.result;

import lombok.SneakyThrows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;

import static java.util.Objects.nonNull;

public class CommandResultConverter {

    public static DeleteMessage buildDeleteMessage(String  chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        return deleteMessage;
    }

    @SneakyThrows
    public static SendPhoto buildSendPhoto(CommandResult commandResult, Long chatId)  {

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setNewPhoto(commandResult.getGameStateImage());

        if (hasKeyboardMarkup(commandResult)) {
            sendPhoto.setReplyMarkup(commandResult.getKeyboardMarkup());
        }

        if (hasReplyKeyboardMarkup(commandResult)) {
            sendPhoto.setReplyMarkup(commandResult.getReplyKeyboardMarkup());
        }

        if (hasReplyKeyboardRemove(commandResult)) {
            sendPhoto.setReplyMarkup(commandResult.getReplyKeyboardRemove());
        }
       return sendPhoto;
    }

    public static SendMessage buildSendMessage(CommandResult commandResult, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(commandResult.getReplyMessage());

        if (hasKeyboardMarkup(commandResult)) {
            sendMessage.setReplyMarkup(commandResult.getKeyboardMarkup());
        }

        if (hasReplyKeyboardMarkup(commandResult)) {
            sendMessage.setReplyMarkup(commandResult.getReplyKeyboardMarkup());
        }

        if (hasReplyKeyboardRemove(commandResult)) {
            sendMessage.setReplyMarkup(commandResult.getReplyKeyboardRemove());
        }
        return sendMessage;
    }

    private static boolean hasKeyboardMarkup(CommandResult commandResult) {
        return nonNull(commandResult.getKeyboardMarkup());
    }

    private static boolean hasReplyKeyboardMarkup(CommandResult commandResult) {
        return nonNull(commandResult.getReplyKeyboardMarkup());
    }

    private static boolean hasReplyKeyboardRemove(CommandResult commandResult) {
        return nonNull(commandResult.getReplyKeyboardRemove());
    }
}