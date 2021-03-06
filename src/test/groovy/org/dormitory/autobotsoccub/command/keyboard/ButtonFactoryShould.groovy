package org.dormitory.autobotsoccub.command.keyboard

import spock.lang.Specification

import static org.dormitory.autobotsoccub.util.ControlMatchers.matchesButtonRepresentationOf
import static spock.util.matcher.HamcrestSupport.that

class ButtonFactoryShould extends Specification {

    def sut = new ButtonFactory()

    def "create inline keyboard button with correct params"() {
        given:
        def buttonTypes = Button.values()

        expect:
        buttonTypes.each { buttonType ->
            assert that(sut.create(buttonType), matchesButtonRepresentationOf(buttonType))
        }
    }
}
