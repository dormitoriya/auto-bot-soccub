package org.dormitory.autobotsoccub

import nl.jqno.equalsverifier.EqualsVerifier
import org.dormitory.autobotsoccub.engine.model.Game
import org.dormitory.autobotsoccub.engine.model.GameData
import spock.lang.Specification

class EqualsAndHashcodeShould extends Specification {

    def "equals and hashcode test"() {
        given:
        def classes = [Game, GameData, Game.TeamWithPosition]

        expect:
        classes.each { EqualsVerifier.forClass(it).verify() }
    }
}
