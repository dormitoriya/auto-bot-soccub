package org.dormitory.autobotsoccub

import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton

class TestDataProvider {

    static InlineKeyboardButton telegramInlineKeyboardButton(def button) {
        new InlineKeyboardButton().setText(button.text).setCallbackData(button.callBackQuery)
    }
}
