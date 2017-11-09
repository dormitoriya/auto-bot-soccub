package org.dormitory.autobotsoccub.command.keyboard;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;

@AllArgsConstructor
public class KeyboardFactory {

    private ButtonFactory buttonFactory;

    public InlineKeyboardMarkup inlineKeyboardOf(Button... buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtons = Arrays.stream(buttons)
                .map(buttonFactory::createInlineButton)
                .collect(Collectors.toList());

        inlineKeyboardMarkup.setKeyboard(of(inlineKeyboardButtons));
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup replyKeyboardOf(Button... buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardButtons = Arrays.stream(buttons)
                .map(buttonFactory::createReplyButton)
                .collect(Collectors.toList());

        replyKeyboardMarkup.setKeyboard(keyboardButtons).setSelective(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardRemove replyKeyboardRemove() {
        return new ReplyKeyboardRemove();
    }
}
