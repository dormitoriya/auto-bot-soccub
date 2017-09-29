package org.dormitory.autobotsoccub.command.querycommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.InlineQueryCommand;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.user.UserPool;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;


import static org.dormitory.autobotsoccub.command.keyboard.Button.START;


@AllArgsConstructor
public class RegisterCommand implements InlineQueryCommand {

    private static final String REGISTERED = "%s, you are registered for upcoming game!";
    private static final String ALREADY_REGISTERED = "%s, you are already registered!";
    private static final String REGISTERED_GAME_STARTS =
            "%s, you are registered for upcoming game!\nThe game begins...";

    private KeyboardFactory keyboardFactory;

    private UserPool userPool;

    @Override
    public Button getCommandButton() {
        return Button.REGISTER;
    }

    @Override
    public CommandResult execute(Update update) {
        User currentUser = update.getCallbackQuery().getFrom();
        if(userPool.containsUser(currentUser.getId())) {
            return CommandResult.builder()
                    .replyMessage(String.format(ALREADY_REGISTERED, currentUser.getFirstName()))
                    .build();
        }
        userPool.addUser(currentUser);

        if (userPool.isFull()) {
            return CommandResult.builder()
                    .replyMessage(String.format(REGISTERED_GAME_STARTS, currentUser.getFirstName()))
                    .keyboardMarkup(keyboardFactory.keyboardOf(START))
                    .build();
        }
        return CommandResult.builder()
                .replyMessage(String.format(REGISTERED, currentUser.getFirstName()))
                .build();
    }
}