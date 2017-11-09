package org.dormitory.autobotsoccub.command.querycommand.gameprocess;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;

import static java.lang.String.format;
import static org.dormitory.autobotsoccub.command.keyboard.Button.*;
import static org.dormitory.autobotsoccub.utils.ImageGenerator.updateImage;

@AllArgsConstructor
public class AutoScoreCommand implements Command {
    private static final String AUTO_SCORED = "%s auto scored";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (AUTO_GOAL.getText().equals(chatData.getCommandName())) {
            chatData.getGameEngineOperations().autoScore(chatData.getUser().getId());

            chatData.setCommandResult(
                    CommandResult.builder()
                            .replyKeyboardMarkup(keyboardFactory.replyKeyboardOf(GOAL, AUTO_GOAL, SUBSTITUTION, REVERT, STOP))
                            .gameStateImage(updateImage(format(AUTO_SCORED, chatData.getUser().getFirstName()), chatData))
                            .build());
            sendStrategy.execute(chatData);
        }
    }
}