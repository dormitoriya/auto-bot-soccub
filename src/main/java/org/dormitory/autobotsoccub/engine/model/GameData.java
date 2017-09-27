package org.dormitory.autobotsoccub.engine.model;

import lombok.Builder;
import lombok.Value;
import org.dormitory.autobotsoccub.engine.scores.ScoreTable;

@Value
@Builder
public class GameData {
    private Game game;
    private ScoreTable scoreTable;
}
