package org.dormitory.autobotsoccub.command.txtcommand;

import org.dormitory.autobotsoccub.command.TextCommand;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.user.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Update;

import java.util.regex.Pattern;

public class ShowPoolCommand implements TextCommand {

    private static final Pattern CMD_PATTERN = Pattern.compile("/showpool");

    @Autowired
    private UserPool userPool;

    @Override
    public Pattern getCommandPattern() {
        return CMD_PATTERN;
    }

    @Override
    public CommandResult execute(Update update) {
        return CommandResult.builder()
                .replyMessage(userPool.toString())
                .build();
    }
}