package org.dormitory.autobotsoccub.command.querycommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.InlineQueryCommand;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.telegram.telegrambots.api.objects.Update;

import static org.dormitory.autobotsoccub.command.keyboard.Button.UNREGISTER;


@AllArgsConstructor
public class RegisterCommand implements InlineQueryCommand {

    private KeyboardFactory keyboardFactory;

    @Override
    public Button getCommandButton() {
        return Button.REGISTER;
    }

    @Override
    public CommandResult execute(Update update) {
        return CommandResult.builder()
                .replyMessage("You are registered for upcoming game!")
                .keyboardMarkup(keyboardFactory.keyboardOf(UNREGISTER))
                .build();
    }
}
