package org.dormitory.autobotsoccub.command.querycommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.command.InlineQueryCommand;
import org.dormitory.autobotsoccub.command.keyboard.Keyboard;
import org.telegram.telegrambots.api.objects.Update;


@AllArgsConstructor
public class RegisterCommand implements InlineQueryCommand {

    private static final String COMMAND = "btn_register";

    private Keyboard registerKeyboard;

    @Override
    public String getQueryName() {
        return COMMAND;
    }

    @Override
    public CommandResult execute(Update update) {
        return CommandResult.builder()
                .replyMessage("You are registered for upcoming game!")
                .keyboardMarkup(registerKeyboard.build())
                .build();
    }
}
