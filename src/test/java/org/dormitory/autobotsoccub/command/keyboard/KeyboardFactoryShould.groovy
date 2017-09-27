package org.dormitory.autobotsoccub.command.keyboard

import org.dormitory.autobotsoccub.TestDataProvider
import spock.lang.Specification

import static org.dormitory.autobotsoccub.TelegramMatchers.matchesKeyboardRepresentationOf
import static org.dormitory.autobotsoccub.command.keyboard.Button.REGISTER
import static org.dormitory.autobotsoccub.command.keyboard.Button.UNREGISTER
import static spock.util.matcher.HamcrestSupport.that

class KeyboardFactoryShould extends Specification {

    def buttonFactory = Mock(ButtonFactory)

    def sut = new KeyboardFactory(buttonFactory)

    def setup() {
        buttonFactory.create(_) >> {
            TestDataProvider.telegramInlineKeyboardButton(it)
        }
    }

    def "build keyboard with specified buttons in one line"() {
        given:
        def createdKeyboard = sut.keyboardOf(REGISTER, UNREGISTER)
        def expectedKeyboard = [buttons]

        expect:
        that createdKeyboard.keyboard, matchesKeyboardRepresentationOf(expectedKeyboard)

        where:
        buttons = [REGISTER, UNREGISTER]
    }


}
