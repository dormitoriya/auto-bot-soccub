package org.dormitory.autobotsoccub.command.querycommand.txtcommand;

import lombok.AllArgsConstructor;
import org.dormitory.autobotsoccub.pool.ExpiringPool;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.dormitory.autobotsoccub.command.keyboard.Button.REGISTER;
import static org.dormitory.autobotsoccub.command.keyboard.Button.START;

@AllArgsConstructor
public class ShowPoolCommand implements Command {
    private static final Pattern CMD_PATTERN = Pattern.compile("/showpool");
    private static final String POOL_IS_EMPTY_MESSAGE = "No registered users. You can be first!";
    private static final String TEMPLATE_MESSAGE = "Registered users:\n%s\nThis pool expires at %s";

    private KeyboardFactory keyboardFactory;
    private SendingStrategy sendStrategy;

    @Override
    public void execute(ChatData chatData) {
        if (CMD_PATTERN.matcher(chatData.getCommandName()).matches()) {
            chatData.setCommandResult(CommandResult.builder()
                    .replyMessage(getMessage(chatData.getRegisterExpiringUserPool()))
                    .keyboardMarkup(getButton(chatData.getRegisterExpiringUserPool()))
                    .build());
            sendStrategy.execute(chatData);
        }
    }

    private String getMessage(ExpiringPool<Integer, User> userPool) {
        return userPool.get().isEmpty()
                ? POOL_IS_EMPTY_MESSAGE
                : format(TEMPLATE_MESSAGE, poolToString(userPool), userPool.get().getExpiresAt().toString());
    }

    private InlineKeyboardMarkup getButton(ExpiringPool<Integer, User> userPool) {
        return userPool.get().isFull()
                ? keyboardFactory.inlineKeyboardOf(START)
                : keyboardFactory.inlineKeyboardOf(REGISTER);
    }

    private String poolToString(ExpiringPool<Integer, User> userPool) {
        return userPool.get().getPool().values().stream()
                .map(User::getFirstName)
                .collect(Collectors.joining("\n"));
    }
}