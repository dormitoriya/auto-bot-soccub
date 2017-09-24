package org.dormitory.autobotsoccub.command.txtcommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.TextCommand;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.dormitory.autobotsoccub.command.keyboard.Button.REGISTER;

@AllArgsConstructor
public class HelloCommand implements TextCommand {

    private static final Pattern CMD_PATTERN = Pattern.compile("/hello");
    private static final String REPLY = "Hello, %s!";
    private static final String UNKNOWN_USER = "anonymous";

    private KeyboardFactory keyboardFactory;

    @Override
    public Pattern getCommandPattern() {
        return CMD_PATTERN;
    }

    @Override
    public CommandResult execute(Update update) {
        return CommandResult.builder()
                .replyMessage(replyFromUpdate(update))
                .keyboardMarkup(keyboardFactory.keyboardOf(REGISTER))
                .build();
    }

    private String replyFromUpdate(Update update) {
        return String.format(REPLY, userNameFromUpdate(update));
    }

    private String userNameFromUpdate(Update update) {
        return Optional.ofNullable(update.getMessage())
                .map(Message::getFrom)
                .map(this::toUserName)
                .orElse(UNKNOWN_USER);
    }

    private String toUserName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}