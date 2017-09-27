package org.dormitory.autobotsoccub.engine.scores;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dormitory.autobotsoccub.engine.model.MatchTeam;
import org.dormitory.autobotsoccub.engine.model.PlayPosition;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Builder
public class ScoreTableRecord {
    private int userId;
    private MatchTeam team;
    private PlayPosition position;
    private AtomicInteger scored;
    private AtomicInteger missed;
    private AtomicInteger autoScored;
}