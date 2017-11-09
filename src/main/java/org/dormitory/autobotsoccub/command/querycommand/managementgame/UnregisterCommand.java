package org.dormitory.autobotsoccub.command.querycommand.managementgame;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;

import static org.dormitory.autobotsoccub.command.keyboard.Button.REGISTER;

@AllArgsConstructor
public class UnregisterCommand implements Command {

    private static final String REGISTRATION_CANCELLED =
            "%s, your registration for the game is canceled! But you can change your mind...";
    private static final String REGISTRATION_NOT_CANCELLED =
            "%s, you can not cancel registration for the game, since you are not registered!";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (Button.UNREGISTER.getCallBackQuery().equals(chatData.getCommandName())) {
            chatData.setCommandResult(formCommandResult(chatData));
            sendStrategy.execute(chatData);
        }
    }

    private CommandResult formCommandResult(ChatData chatData) {
        if (!chatData.getRegisterExpiringUserPool().get().getPool().containsKey(chatData.getUser().getId())) {
            return CommandResult.builder()
                    .replyMessage(String.format(REGISTRATION_NOT_CANCELLED, chatData.getUser().getFirstName()))
                    .build();
        }
        chatData.removeUserFromRegisterPool(chatData.getUser().getId());

        return CommandResult.builder()
                .replyMessage(String.format(REGISTRATION_CANCELLED, chatData.getUser().getFirstName()))
                .keyboardMarkup(keyboardFactory.inlineKeyboardOf(REGISTER))
                .build();
    }
}