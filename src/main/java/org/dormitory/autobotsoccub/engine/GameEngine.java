package org.dormitory.autobotsoccub.engine;

import org.dormitory.autobotsoccub.engine.exception.GameDoesNotExistException;
import org.dormitory.autobotsoccub.engine.model.Game;
import org.dormitory.autobotsoccub.engine.model.GameData;
import org.dormitory.autobotsoccub.engine.model.MatchTeam;
import org.dormitory.autobotsoccub.engine.scores.ScoreTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameEngine implements GameEngineOperations {

    private final Map<Integer, GameData> activeGames = new HashMap<>();

    @Override
    public boolean startGame(Integer userId, Game game) {
        if (activeGames.containsKey(userId) || !game.getPlayers().contains(userId)) {
            return false;
        }

        GameData gameData = GameData.builder()
                .game(game)
                .scoreTable(ScoreTable.fromGame(game))
                .build();

        game.getPlayers().forEach(playerId -> activeGames.put(playerId, gameData));
        return true;
    }

    @Override
    public GameData stopGame(Integer userId) {
        GameData gameData = findGameDataOrFail(userId);
        Game currentGame = gameData.getGame();
        currentGame.getPlayers()
                .forEach(activeGames::remove);
        return gameData;
    }

    @Override
    public void score(Integer userId) {
        GameData activeGame = findGameDataOrFail(userId);
        ScoreTable scoreTable = activeGame.getScoreTable();
        scoreTable.incrementScored(userId);
        scoreTable.incrementGoalkeeperMissed(scoreTable.getGoalkeeperIdAnotherTeam(userId));
    }

    @Override
    public void autoScore(Integer userId) {
        GameData activeGame = findGameDataOrFail(userId);
        ScoreTable scoreTable = activeGame.getScoreTable();
        scoreTable.incrementAutoScored(userId);
        scoreTable.incrementGoalkeeperMissed(userId);
    }

    @Override
    public MatchTeam changePositionsInTeam(Integer userId) {
        GameData activeGame = findGameDataOrFail(userId);
        ScoreTable scoreTable = activeGame.getScoreTable();
        scoreTable.swapPositions(userId);
        return activeGame.getGame().getUserGameInfo(userId).getTeam();
    }

    @Override
    public void revert(Integer userId) {
        GameData activeGame = findGameDataOrFail(userId);
        ScoreTable scoreTable = activeGame.getScoreTable();
        scoreTable.decrementScore(userId);
    }

    private GameData findGameDataOrFail(Integer userId) {
        return Optional.ofNullable(activeGames.get(userId))
                .orElseThrow(() -> new GameDoesNotExistException(userId));
    }
}