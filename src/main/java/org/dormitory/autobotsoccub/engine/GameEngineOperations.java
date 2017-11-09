package org.dormitory.autobotsoccub.engine;

import org.dormitory.autobotsoccub.engine.model.Game;
import org.dormitory.autobotsoccub.engine.model.GameData;
import org.dormitory.autobotsoccub.engine.model.MatchTeam;
import org.dormitory.autobotsoccub.engine.scores.ScoreTableRecord;
import org.telegram.telegrambots.api.objects.User;

import java.util.Map;

public interface GameEngineOperations {

    boolean startGame(Integer userId, Game game);

    GameData stopGame(Integer userId);

    void score(Integer userId);

    void autoScore(Integer userId);

    MatchTeam changePositionsInTeam(Integer userId);

    void revert(Integer userId);

    Map<Integer, ScoreTableRecord> getCurrentGameState(Integer userId);

    GameData getCurrentGameData(Integer userId);
}