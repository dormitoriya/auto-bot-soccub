package org.dormitory.autobotsoccub.command.querycommand.txtcommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;
import static org.dormitory.autobotsoccub.command.keyboard.Button.*;

@AllArgsConstructor
public class HelloCommand implements Command {

    private static final Pattern CMD_PATTERN = Pattern.compile("/start");
    private static final String REPLY = "Hello, %s";
    private static final String UNKNOWN_USER = "anonymous";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (CMD_PATTERN.matcher(chatData.getCommandName()).matches()) {
            chatData.setCommandResult(CommandResult.builder()
                    .replyMessage(replyFromUpdate(chatData.getUser().getFirstName()))
                    .keyboardMarkup(keyboardFactory.inlineKeyboardOf(REGISTER, UNREGISTER))
                    .build());
            sendStrategy.execute(chatData);
        }
    }

    private String replyFromUpdate(String userName) {
        return String.format(REPLY, nonNull(userName) ? userName : UNKNOWN_USER);
    }
}