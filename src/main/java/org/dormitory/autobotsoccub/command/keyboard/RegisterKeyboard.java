package org.dormitory.autobotsoccub.command.keyboard;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;

public class RegisterKeyboard implements Keyboard {

    private List<List<InlineKeyboardButton>> keyboard = of(of(button("Register", "btn_register"),
                                                              button("Unregister", "btn_unregister")));

    @Override
    public InlineKeyboardMarkup build() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardButton button(String text, String query) {
        return new InlineKeyboardButton().setText(text).setCallbackData(query);
    }
}
