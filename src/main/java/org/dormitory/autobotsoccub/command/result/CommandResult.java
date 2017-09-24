package org.dormitory.autobotsoccub.command.result;

import lombok.Builder;
import lombok.Value;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

@Value
@Builder
public class CommandResult {
    private String replyMessage;
    private InlineKeyboardMarkup keyboardMarkup;
}
