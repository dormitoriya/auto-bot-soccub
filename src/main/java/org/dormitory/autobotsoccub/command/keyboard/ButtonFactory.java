package org.dormitory.autobotsoccub.command.keyboard;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@AllArgsConstructor
public class ButtonFactory {

    public InlineKeyboardButton create(Button button) {
        return new InlineKeyboardButton()
                .setText(button.getText())
                .setCallbackData(button.getCallBackQuery());
    }
}
