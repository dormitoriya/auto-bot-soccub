package org.dormitory.autobotsoccub.command.querycommand.gameprocess;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.engine.model.GameData;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;

import static org.dormitory.autobotsoccub.utils.ImageGenerator.updateImage;

@AllArgsConstructor
public class StopGameCommand implements Command {

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (Button.STOP.getText().equals(chatData.getCommandName())) {
            GameData gameData = chatData.getGameEngineOperations().stopGame(chatData.getUser().getId());

            chatData.setCommandResult(
                    CommandResult.builder()
                            .gameStateImage(updateImage(gameData, chatData))
                            .replyKeyboardRemove(keyboardFactory.replyKeyboardRemove())
                            .build());
            sendStrategy.execute(chatData);
        }
    }
}