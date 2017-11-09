package org.dormitory.autobotsoccub.command.keyboard;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

@AllArgsConstructor
public class ButtonFactory {

    InlineKeyboardButton createInlineButton(Button button) {
        return new InlineKeyboardButton()
                .setText(button.getText())
                .setCallbackData(button.getCallBackQuery());
    }

    KeyboardRow createReplyButton(Button button) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(button.getText());
        return keyboardRow;
    }
}