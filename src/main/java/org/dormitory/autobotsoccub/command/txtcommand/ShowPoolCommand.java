package org.dormitory.autobotsoccub.command.txtcommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.TextCommand;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.user.UserPool;
import org.dormitory.autobotsoccub.user.UserUtils;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.regex.Pattern;


import static org.dormitory.autobotsoccub.command.keyboard.Button.REGISTER;
import static org.dormitory.autobotsoccub.command.keyboard.Button.START;


@AllArgsConstructor
public class ShowPoolCommand implements TextCommand {
    private static final Pattern CMD_PATTERN = Pattern.compile("/showpool");

    private KeyboardFactory keyboardFactory;
    private UserPool userPool;

    @Override
    public Pattern getCommandPattern() {
        return CMD_PATTERN;
    }

    @Override
    public CommandResult execute(Update update) {
        return CommandResult.builder()
                .replyMessage(getMessage())
                .keyboardMarkup(getButton())
                .build();
    }

    private String getMessage() {
        return userPool.isEmpty() ? "No registered users. You can be first!"
                : UserUtils.getPoolList(userPool.getUserPool());
    }

    private InlineKeyboardMarkup getButton() {
        return userPool.isEmpty() ? keyboardFactory.keyboardOf(REGISTER)
                : keyboardFactory.keyboardOf(START);
    }
}