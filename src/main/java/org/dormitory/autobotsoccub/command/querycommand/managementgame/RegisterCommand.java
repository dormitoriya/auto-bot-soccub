package org.dormitory.autobotsoccub.command.querycommand.managementgame;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.pool.ExpiringPool;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import org.telegram.telegrambots.api.objects.User;

import static java.util.Objects.nonNull;
import static org.dormitory.autobotsoccub.command.keyboard.Button.*;

@AllArgsConstructor
public class RegisterCommand implements Command {

    private static final String REGISTERED = "%s, you are registered for upcoming game!";
    private static final String ALREADY_REGISTERED = "%s, you are already registered!";
    private static final String REGISTERED_GAME_STARTS =
            "%s, you are registered for upcoming game!\nThe game begins...";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (REGISTER.getCallBackQuery().equals(chatData.getCommandName())) {
            chatData.setCommandResult(formCommandResult(chatData));
            sendStrategy.execute(chatData);
        }
    }

    private CommandResult formCommandResult(ChatData chatData) {
        ExpiringPool<Integer, User> userPool = chatData.getRegisterExpiringUserPool();
        if (nonNull(userPool) && userPool.get().getPool().containsKey(chatData.getUser().getId())) {
            return CommandResult.builder()
                    .replyMessage(String.format(ALREADY_REGISTERED, chatData.getUser().getFirstName()))
                    .build();
        }
        chatData.putUserToRegisterPool(chatData.getUser());

        if (chatData.getRegisterExpiringUserPool().get().isFull()) {
            chatData.setGameProcessUserPool(chatData.getRegisterExpiringUserPool().get().getPool());
            return CommandResult.builder()
                    .replyMessage(String.format(REGISTERED_GAME_STARTS, chatData.getUser().getFirstName()))
                    .keyboardMarkup(keyboardFactory.inlineKeyboardOf(START))
                    .build();
        }
        return CommandResult.builder()
                .replyMessage(String.format(REGISTERED, chatData.getUser().getFirstName()))
                .build();
    }
}