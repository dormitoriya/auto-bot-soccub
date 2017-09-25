package org.dormitory.autobotsoccub.command.querycommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.InlineQueryCommand;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.user.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import static org.dormitory.autobotsoccub.command.keyboard.Button.*;

@AllArgsConstructor
public class RegisterCommand implements InlineQueryCommand {

    private KeyboardFactory keyboardFactory;

    @Autowired
    private UserPool userPool;

    @Override
    public Button getCommandButton() {
        return Button.REGISTER;
    }

    @Override
    public CommandResult execute(Update update) {
        synchronized (userPool) {
            User currentUser = update.getCallbackQuery().getFrom();
            if(userPool.containsUserFromPool(currentUser.getId())) {
                return CommandResult.builder()
                        .replyMessage(currentUser.getFirstName() + " you are already registered!")
                        .build();
            }
            userPool.addUserToPool(update.getCallbackQuery().getFrom());

            if (userPool.isFullPool()) {
                return CommandResult.builder()
                        .replyMessage(currentUser.getFirstName() + " are registered for upcoming game!\nThe game begins...")
                        .keyboardMarkup(keyboardFactory.keyboardOf(START))
                        .build();
            }
            return CommandResult.builder()
                    .replyMessage(currentUser.getFirstName() + " are registered for upcoming game!")
                    .build();
        }

    }
}
