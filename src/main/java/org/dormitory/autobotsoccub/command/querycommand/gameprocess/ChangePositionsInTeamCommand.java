package org.dormitory.autobotsoccub.command.querycommand.gameprocess;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;

import org.dormitory.autobotsoccub.engine.model.MatchTeam;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.dormitory.autobotsoccub.command.keyboard.Button.*;
import static org.dormitory.autobotsoccub.utils.ImageGenerator.updateImage;

@AllArgsConstructor
public class ChangePositionsInTeamCommand implements Command {
    private static final String CHANGE_POSITIONS = "Change of positions in team: %s";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (SUBSTITUTION.getText().equals(chatData.getCommandName())) {
            MatchTeam team = chatData.getGameEngineOperations().changePositionsInTeam(chatData.getUser().getId());

            chatData.setCommandResult(
                    CommandResult.builder()
                            .replyKeyboardMarkup(keyboardFactory.replyKeyboardOf(GOAL, AUTO_GOAL, SUBSTITUTION, STOP))
                            .gameStateImage(updateImage(format(CHANGE_POSITIONS, team), chatData))
                            .build());
            sendStrategy.execute(chatData);
        }
    }
}