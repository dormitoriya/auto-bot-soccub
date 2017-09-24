package org.dormitory.autobotsoccub.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Slf4j
@AllArgsConstructor
public class SoccubBot extends TelegramLongPollingBot {

    private String name;
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got update: {}", update);
        reply("HELLO", update);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    private void reply(String reply, Update toUpdate) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(reply).setChatId(String.valueOf(toUpdate.getMessage().getChatId())).enableMarkdown(true);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("An error occurred while sending response", e);
        }
    }
}
