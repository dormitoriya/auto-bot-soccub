package org.dormitory.autobotsoccub.util

import org.telegram.telegrambots.api.objects.User
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton

class TestDataProvider {

    static InlineKeyboardButton telegramInlineKeyboardButton(def button) {
        new InlineKeyboardButton().setText(button.text).setCallbackData(button.callBackQuery)
    }

    static User buildUser(Map params) {
        def userProperties = [
            id: 1,
            firstName: "Test",
            lastName: "User",
            userName: "testUser"
        ]
        userProperties.putAll(params)

        new User(userProperties)
    }
}
