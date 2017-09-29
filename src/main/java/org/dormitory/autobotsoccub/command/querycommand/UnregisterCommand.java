package org.dormitory.autobotsoccub.command.querycommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.InlineQueryCommand;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.user.UserPool;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;


import static org.dormitory.autobotsoccub.command.keyboard.Button.REGISTER;


@AllArgsConstructor
public class UnregisterCommand implements InlineQueryCommand {

    private static final String REGISTRATION_CANCELLED =
            "%s, your registration for the game is canceled! But you can change your mind...";
    private static final String REGISTRATION_NOT_CANCELLED =
            "%s, you can not cancel registration for the game, since you are not registered!";

    private KeyboardFactory keyboardFactory;

    private UserPool userPool;

    @Override
    public Button getCommandButton() {
        return Button.UNREGISTER;
    }

    @Override
    public CommandResult execute(Update update) {
        User currentUser = update.getCallbackQuery().getFrom();
        if (userPool.containsUser(currentUser.getId())) {
            userPool.removeUser(currentUser);
            return CommandResult.builder()
                    .replyMessage(String.format(REGISTRATION_CANCELLED, currentUser.getFirstName()))
                    .keyboardMarkup(keyboardFactory.keyboardOf(REGISTER))
                    .build();
        }
        return CommandResult.builder()
            .replyMessage(String.format(REGISTRATION_NOT_CANCELLED, currentUser.getFirstName()))
            .build();
    }
}