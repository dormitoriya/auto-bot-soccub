package org.dormitory.autobotsoccub.engine

import org.dormitory.autobotsoccub.engine.exception.GameDoesNotExistException
import org.dormitory.autobotsoccub.engine.model.Game
import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Unroll

import static org.dormitory.autobotsoccub.engine.model.PlayPosition.anotherPosition

class GameEngineShould extends Specification {

    @Shared
    def GK_A = 0
    @Shared
    def FW_A = 1
    @Shared
    def GK_B = 2
    @Shared
    def FW_B = 3
    @Shared
    def unknown_user = 999

    @Shared
    Game game
    @Shared
    GameEngine sut

    def setup() {
        sut = new GameEngine()
        game = Game.builder()
                .goalKeeperA(GK_A)
                .forwardA(FW_A)
                .goalKeeperB(GK_B)
                .forwardB(FW_B)
                .build()
    }

    def setupSpec() {
        GameEngine.metaClass.getCurrentStats { int userId ->
            activeGames.get(userId).scoreTable.statsByUserId.get(userId)
        }
    }

    @Unroll
    def "successfully start and end game"() {
        when:
        def isStarted = sut.startGame(scoreAuthor, game)

        then:
        isStarted
        sut.stopGame(scoreAuthor)

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    def "fail to start already active game"() {
        when:
        sut.startGame(FW_A, game)

        then:
        !sut.startGame(FW_A, game)
    }

    def "fail to start game by unknown player"() {
        expect:
        !sut.startGame(unknown_user, game)
    }

    @Unroll
    def "correctly count scored goal"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.score(scoreAuthor)

        then:
        with(sut.getCurrentStats(scoreAuthor)) {
            scored.get() == 1
        }

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    def "fail to score"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.score(unknown_user)

        then:
        thrown GameDoesNotExistException
    }

    @Unroll
    def "correctly count auto scored goal"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.autoScore(scoreAuthor)

        then:
        with(sut.getCurrentStats(scoreAuthor)) {
            autoScored.get() == 1
        }

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    def "fail to auto score"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.autoScore(unknown_user)

        then:
        thrown GameDoesNotExistException
    }

    @Unroll
    def "changed position in the team"() {
        given:
        sut.startGame(FW_A, game)

        when:
        def anotherPosition = anotherPosition(sut.getCurrentStats(scoreAuthor).position)
        sut.changePositionsInTeam(scoreAuthor)

        then:
        with(sut.getCurrentStats(scoreAuthor)) {
            position == anotherPosition
        }

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    def "fail to change position in the team"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.changePositionsInTeam(unknown_user)

        then:
        thrown GameDoesNotExistException
    }

    @Unroll
    def "correctly revert scored event"() {
        given:
        sut.startGame(FW_A, game)
        sut.score(scoreAuthor)

        when:
        sut.revert(scoreAuthor)

        then:
        with(sut.getCurrentStats(scoreAuthor)) {
            scored.get() == 0
            autoScored.get() == 0
            missed.get() == 0
        }

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    @Unroll
    def "correctly revert auto scored event"() {
        given:
        sut.startGame(FW_A, game)
        sut.autoScore(scoreAuthor)

        when:
        sut.revert(scoreAuthor)

        then:
        with(sut.getCurrentStats(scoreAuthor)) {
            scored.get() == 0
            autoScored.get() == 0
            missed.get() == 0
        }

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    @Unroll
    def "incorrectly revert event"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.revert(scoreAuthor)

        then:
        with(sut.getCurrentStats(scoreAuthor)) {
            scored.get() == 0
            autoScored.get() == 0
            missed.get() == 0
        }

        where:
        scoreAuthor | _
        FW_A        | _
        FW_B        | _
        GK_A        | _
        GK_B        | _
    }

    def "fail to revert event"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.revert(unknown_user)

        then:
        thrown GameDoesNotExistException
    }

    def "fail to end already ended game"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.stopGame(FW_A)
        sut.stopGame(FW_A)

        then:
        thrown GameDoesNotExistException
    }

    def "fail to end game by unknown player"() {
        given:
        sut.startGame(FW_A, game)

        when:
        sut.stopGame(unknown_user)

        then:
        thrown GameDoesNotExistException
    }
}