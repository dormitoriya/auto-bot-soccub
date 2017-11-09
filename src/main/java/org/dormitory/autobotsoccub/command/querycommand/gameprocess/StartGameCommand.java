package org.dormitory.autobotsoccub.command.querycommand.gameprocess;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.engine.model.Game;
import org.telegram.telegrambots.api.objects.User;
import java.util.Map;

import static org.dormitory.autobotsoccub.command.keyboard.Button.*;
import static org.dormitory.autobotsoccub.utils.ImageGenerator.updateImage;

@AllArgsConstructor
public class StartGameCommand implements Command {

    private static final String START_MESSAGE = "The battle begins!";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (START.getCallBackQuery().equals(chatData.getCommandName())) {

            // this temp solution. for demo
            Map<Integer, User> pool = chatData.getGameProcessUserPool();
            Object[] keySet = pool.keySet().toArray();
            Game game = Game.builder()
                    .forwardA(Integer.valueOf(keySet[0].toString()))
                    .goalKeeperA(Integer.valueOf(keySet[1].toString()))
                    .forwardB(Integer.valueOf(keySet[0].toString()) + 2)
                    .goalKeeperB(Integer.valueOf(keySet[0].toString()) + 3)
                    .build();

            chatData.getGameEngineOperations().startGame(chatData.getUser().getId(), game);

            chatData.setCommandResult(
                    CommandResult.builder()
                            .replyKeyboardMarkup(keyboardFactory.replyKeyboardOf(GOAL, AUTO_GOAL, SUBSTITUTION, STOP))
                            .gameStateImage(updateImage(START_MESSAGE, chatData))
                            .build());
            sendStrategy.execute(chatData);
        }
    }
}