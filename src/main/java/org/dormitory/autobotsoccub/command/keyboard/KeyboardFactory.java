package org.dormitory.autobotsoccub.command.keyboard;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;

@AllArgsConstructor
public class KeyboardFactory {

    private ButtonFactory buttonFactory;

    public InlineKeyboardMarkup keyboardOf(Button... buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtons = Arrays.stream(buttons)
                .map(buttonFactory::create)
                .collect(Collectors.toList());

        inlineKeyboardMarkup.setKeyboard(of(inlineKeyboardButtons));
        return inlineKeyboardMarkup;
    }
}
