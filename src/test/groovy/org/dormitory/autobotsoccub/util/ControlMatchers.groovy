package org.dormitory.autobotsoccub.util

import org.dormitory.autobotsoccub.command.keyboard.Button
import org.hamcrest.BaseMatcher
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton

import static org.dormitory.autobotsoccub.command.keyboard.Button.of

class ControlMatchers {

    static matchesButtonRepresentationOf(Button expected) {
        [
            matches: { InlineKeyboardButton arg ->
                expected.text == arg.text && expected.callBackQuery == arg.callbackData
            },
            describeTo: { description ->
                description.appendText("be internally qeual to ${expected}")
            },
            describeMismatch: { list, description ->
                description.appendValue(list.toListString()).appendText(" was not internally equal to ${expected}")
            }
        ] as BaseMatcher
    }

    static matchesKeyboardRepresentationOf(List<List<Button>> expected) {
        [
            matches: { List<List<InlineKeyboardMarkup>> arg ->
                expected == arg.collect {
                    row -> row.collect {
                        telegramButton -> of(telegramButton.text, telegramButton.callbackData)
                    }}
            },
            describeTo: { description ->
                description.appendText("be internally qeual to ${expected}")
            },
            describeMismatch: { list, description ->
                description.appendValue(list.toListString()).appendText(" was not internally equal to ${expected}")
            }
        ] as BaseMatcher
    }
}
