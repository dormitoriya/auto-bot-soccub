package org.dormitory.autobotsoccub.engine;

import org.dormitory.autobotsoccub.engine.model.Game;
import org.dormitory.autobotsoccub.engine.model.GameData;
import org.dormitory.autobotsoccub.engine.model.MatchTeam;

public interface GameEngineOperations {

    boolean startGame(Integer userId, Game game);

    GameData stopGame(Integer userId);

    void score(Integer userId);

    void autoScore(Integer userId);

    MatchTeam changePositionsInTeam(Integer userId);

    void revert(Integer userId);
}