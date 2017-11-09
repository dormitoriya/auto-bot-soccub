package org.dormitory.autobotsoccub.command.result;

import lombok.Builder;
import lombok.Value;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.io.File;

@Value
@Builder
public class CommandResult {
    private String replyMessage;
    private InlineKeyboardMarkup keyboardMarkup;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardRemove replyKeyboardRemove;
    private File gameStateImage;
}
