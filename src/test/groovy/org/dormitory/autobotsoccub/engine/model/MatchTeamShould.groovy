package org.dormitory.autobotsoccub.engine.model

import spock.lang.Specification

class MatchTeamShould extends Specification {

    def "correctly return another team"() {
        expect:
        MatchTeam.A == MatchTeam.anotherTeam(MatchTeam.B)
        MatchTeam.B == MatchTeam.anotherTeam(MatchTeam.A)
    }
}
