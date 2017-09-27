package org.dormitory.autobotsoccub.model;

import lombok.Builder;
import lombok.Data;

import static org.dormitory.autobotsoccub.model.PlayPosition.GK;
import static org.dormitory.autobotsoccub.model.PlayPosition.FW;

@Data
@Builder
public class ScoreTableRecord {
    private int userId;
    private MatchTeam team;
    private PlayPosition position;
    private int scored;
    private int missed;
    private int autoScored;

    public static ScoreTableRecord changePosition(ScoreTableRecord record) {
        record.setPosition(record.getPosition() == GK ? FW : GK);
        return record;
    }
}