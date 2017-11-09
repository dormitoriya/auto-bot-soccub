package org.dormitory.autobotsoccub.command.querycommand.gameprocess;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;

import static java.lang.String.format;
import static org.dormitory.autobotsoccub.command.keyboard.Button.*;
import static org.dormitory.autobotsoccub.utils.ImageGenerator.updateImage;


@AllArgsConstructor
public class RevertCommand implements Command {
    private static final String REVERT = "%s canceled the last action";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (Button.REVERT.getText().equals(chatData.getCommandName())) {
            chatData.getGameEngineOperations().revert(chatData.getUser().getId());

            chatData.setCommandResult(
                    CommandResult.builder()
                            .replyKeyboardMarkup(keyboardFactory.replyKeyboardOf(GOAL, AUTO_GOAL, SUBSTITUTION, STOP))
                            .gameStateImage(updateImage(format(REVERT, chatData.getUser().getFirstName()), chatData))
                            .build());
            sendStrategy.execute(chatData);
        }
    }
}
