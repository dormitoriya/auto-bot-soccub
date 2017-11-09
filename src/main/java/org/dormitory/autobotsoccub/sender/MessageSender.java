package org.dormitory.autobotsoccub.sender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dormitory.autobotsoccub.bot.SoccubBot;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.Serializable;

@Slf4j
@AllArgsConstructor
public class MessageSender {

    private static final String TELEGRAM_API_EXCEPTION_MESSAGE = "An error occurred while sending response";

    private ConfigurableApplicationContext ctx;

    public void tryReply(BotApiMethod<? extends Serializable> message) {
        try {
            SoccubBot bot = ctx.getBean(SoccubBot.class);
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.debug(TELEGRAM_API_EXCEPTION_MESSAGE, e);
        }
    }

    public Message tryReply(SendPhoto sendPhoto) {
        try {
            SoccubBot bot = ctx.getBean(SoccubBot.class);
            return bot.sendPhoto(sendPhoto);
        } catch (TelegramApiException e) {
            log.debug(TELEGRAM_API_EXCEPTION_MESSAGE, e);
        }
        throw new NullPointerException();
    }
}
