package org.dormitory.autobotsoccub.bot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dormitory.autobotsoccub.command.Command;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

import static org.dormitory.autobotsoccub.command.result.CommandResultConverter.fromCommandResult;

@Slf4j
@AllArgsConstructor
public class SoccubBot extends TelegramLongPollingBot {

    private String name;
    private String token;
    private List<Command> commands;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got update: {}", update);

        commands.stream()
                .filter(command -> command.accepts(update))
                .findFirst()
                .map(command -> command.execute(update))
                .map(cmdResult -> fromCommandResult(cmdResult, update))
                .ifPresent(this::tryReply);
    }

    private void tryReply(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("An error occurred while sending response", e);
        }
    }
}
