package com.dormitory.autobotsoccub;

import com.dormitory.autobotsoccub.commands.HelpCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * @author nikolaev
 * @brief Handler for working with commands
 * @date 23.09.2017
 */
@Component
public class Bot extends TelegramLongPollingCommandBot {

    private static final String LOGTAG = "BOT";

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.name}")
    private String botName;

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    /**
     * Constructor.
     */
    public Bot() {
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("Команда '" + message.getText() + "' мне не известна. Давай посмотрим help");
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage echoMessage = new SendMessage();
                echoMessage.setChatId(message.getChatId());
                echoMessage.setText("Привет! Ваше сообщение:\n" + message.getText());

                try {
                    sendMessage(echoMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        }
    }
}
