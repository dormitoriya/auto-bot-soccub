package org.dormitory.autobotsoccub.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

import static org.dormitory.autobotsoccub.model.MatchTeam.anotherTeam;
import static org.dormitory.autobotsoccub.model.PlayPosition.GK;
import static org.dormitory.autobotsoccub.model.Scores.SCORED;
import static org.dormitory.autobotsoccub.model.Scores.AUTO_SCORED;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoreTable {
    @Getter private final ConcurrentHashMap<Integer, ScoreTableRecord> statsByUserId;
    private final ConcurrentHashMap<Integer, Scores> lastScore;

    public static ScoreTable fromGame(Game currentGame) {
        ConcurrentHashMap<Integer, ScoreTableRecord> statsBuilder = new ConcurrentHashMap<>();

        currentGame.getPlayersByTeams().entrySet().stream()
                .map(userKeyTeamValue -> ScoreTableRecord.builder()
                        .userId(userKeyTeamValue.getKey())
                        .position(userKeyTeamValue.getValue().getPosition())
                        .team(userKeyTeamValue.getValue().getTeam())
                        .build())
                .forEach(stats -> statsBuilder.put(stats.getUserId(), stats));

        return new ScoreTable(statsBuilder, new ConcurrentHashMap<>());
    }

    public void incrementScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.setScored(userStats.getScored() + 1);
        lastScore.put(userId, SCORED);
    }

    public void incrementAutoScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.setAutoScored(userStats.getAutoScored() + 1);
        lastScore.put(userId, AUTO_SCORED);
    }

    public void swapPositions(int userId) {
        statsByUserId.values().stream()
                .filter(scoreTable -> scoreTable.getTeam().equals(getTeamByUserId(userId)))
                .forEach(record -> statsByUserId.put(record.getUserId(),
                                                 ScoreTableRecord.changePosition(record)));
    }

    public void decrementScore(int userId) {
        if (lastScore.get(userId) == SCORED) {
            decrementScored(userId);
            decrementGoalkeeperMissed(getGoalkeeperIdAnotherTeam(userId));
        } else {
            decrementAutoScored(userId);
            decrementGoalkeeperMissed(userId);
        }
    }

    public int getGoalkeeperIdAnotherTeam(int userId) {
        return statsByUserId.values().stream()
                .filter(scoreTableRecord -> scoreTableRecord.getPosition().equals(GK)
                        && scoreTableRecord.getTeam().equals(anotherTeam(getTeamByUserId(userId))))
                .findFirst()
                .map(ScoreTableRecord::getUserId)
                .orElse(0);
    }

    public void incrementGoalkeeperMissed(int userId) {
        statsByUserId.values().stream()
                .filter(scoreTableRecord -> scoreTableRecord.getPosition().equals(GK)
                                    && scoreTableRecord.getTeam().equals(getTeamByUserId(userId)))
                .findFirst()
                .ifPresent(record -> incrementMissed(record.getUserId()));
    }

    private void decrementGoalkeeperMissed(int userId) {
        statsByUserId.values().stream()
            .filter(scoreTableRecord -> scoreTableRecord.getPosition().equals(GK)
                                    && scoreTableRecord.getTeam().equals(getTeamByUserId(userId)))
            .findFirst()
            .ifPresent(record -> decrementMissed(record.getUserId()));
    }

    private void decrementScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        if (userStats.getScored() > 0) {
            userStats.setScored(userStats.getScored() - 1);
        }
        lastScore.remove(userId);
    }

    private void decrementAutoScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        if (userStats.getAutoScored() > 0) {
            userStats.setAutoScored(userStats.getAutoScored() - 1);
        }
        lastScore.remove(userId);
    }

    private void incrementMissed(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.setMissed(userStats.getMissed() + 1);
    }

    private void decrementMissed(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        if (userStats.getMissed() > 0) {
            userStats.setMissed(userStats.getMissed() - 1);
        }
    }

    private MatchTeam getTeamByUserId(int userId) {
        return statsByUserId.get(userId).getTeam();
    }
}