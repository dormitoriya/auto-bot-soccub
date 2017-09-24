package org.dormitory.autobotsoccub.command.txtcommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.command.TextCommand;
import org.dormitory.autobotsoccub.command.keyboard.Keyboard;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.Optional;
import java.util.regex.Pattern;

@AllArgsConstructor
public class HelloCommand implements TextCommand {

    private static final Pattern CMD_PATTERN = Pattern.compile("/hello");
    private static final String REPLY = "Hello, %s!";
    private static final String UNKNOWN_USER = "anonymous";

    private Keyboard registerKeyboard;

    @Override
    public Pattern getCommandPattern() {
        return CMD_PATTERN;
    }

    @Override
    public CommandResult execute(Update update) {
        return CommandResult.builder()
                .replyMessage(replyFromUpdate(update))
                .keyboardMarkup(registerKeyboard.build())
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
