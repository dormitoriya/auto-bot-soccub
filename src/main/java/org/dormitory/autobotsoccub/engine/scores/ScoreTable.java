package org.dormitory.autobotsoccub.engine.scores;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dormitory.autobotsoccub.engine.model.Game;
import org.dormitory.autobotsoccub.engine.model.MatchTeam;
import org.dormitory.autobotsoccub.engine.model.PlayPosition;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.dormitory.autobotsoccub.engine.model.MatchTeam.anotherTeam;
import static org.dormitory.autobotsoccub.engine.model.PlayPosition.anotherPosition;
import static org.dormitory.autobotsoccub.engine.scores.Scores.SCORED;
import static org.dormitory.autobotsoccub.engine.scores.Scores.AUTO_SCORED;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ScoreTable {

    @Getter private final ConcurrentHashMap<Integer, ScoreTableRecord> statsByUserId;
    @Getter private final ConcurrentHashMap<MatchTeam, Integer> gameScore;
    private final ConcurrentHashMap<Integer, Scores> lastScore;

    public static ScoreTable fromGame(Game currentGame) {
        ConcurrentHashMap<Integer, ScoreTableRecord> statsBuilder = new ConcurrentHashMap<>();
        ConcurrentHashMap<MatchTeam, Integer> gameScore = new ConcurrentHashMap<>();

        gameScore.put(MatchTeam.A, 0);
        gameScore.put(MatchTeam.B, 0);

        currentGame.getPlayersByTeams().entrySet().stream()
                .map(userKeyTeamValue -> ScoreTableRecord.builder()
                        .userId(userKeyTeamValue.getKey())
                        .position(userKeyTeamValue.getValue().getPosition())
                        .team(userKeyTeamValue.getValue().getTeam())
                        .scored(new AtomicInteger(0))
                        .autoScored(new AtomicInteger(0))
                        .missed(new AtomicInteger(0))
                        .build())
                .forEach(stats -> statsBuilder.put(stats.getUserId(), stats));

        return new ScoreTable(statsBuilder, gameScore, new ConcurrentHashMap<>());
    }

    public void incrementScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.getScored().incrementAndGet();
        MatchTeam team = userStats.getTeam();
        gameScore.put(team, gameScore.get(team) + 1);
        lastScore.put(userId, SCORED);
    }

    public void incrementAutoScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.getAutoScored().incrementAndGet();
        MatchTeam team = MatchTeam.anotherTeam(userStats.getTeam());
        gameScore.put(team, gameScore.get(team) + 1);
        lastScore.put(userId, AUTO_SCORED);
    }

    public void swapPositions(int userId) {
        statsByUserId.values().stream()
                .filter(scoreTable -> scoreTable.getTeam().equals(getTeamByUserId(userId)))
                .forEach(record -> {
                    record.setPosition(anotherPosition(record.getPosition()));
                    statsByUserId.put(record.getUserId(), record);
                });
    }

    public void decrementScore(int userId) {
        if (lastScore.get(userId) == SCORED) {
            decrementScored(userId);
            decrementGoalkeeperMissed(getGoalkeeperIdAnotherTeam(userId));
            return;
        }
        if (lastScore.get(userId) == AUTO_SCORED) {
            decrementAutoScored(userId);
            decrementGoalkeeperMissed(userId);
        }
    }

    public int getGoalkeeperIdAnotherTeam(int userId) {
        return statsByUserId.values().stream()
                .filter(record -> isGoalkeeper(record) && isAnotherTeam(userId, record))
                .findFirst()
                .map(ScoreTableRecord::getUserId)
                .orElse(0);
    }

    public void incrementGoalkeeperMissed(int userId) {
        statsByUserId.values().stream()
                .filter(record -> isGoalkeeper(record) && isThisTeam(userId, record))
                .findFirst()
                .ifPresent(record -> incrementMissed(record.getUserId()));
    }

    private void decrementGoalkeeperMissed(int userId) {
        statsByUserId.values().stream()
                .filter(record ->  isGoalkeeper(record) && isThisTeam(userId, record))
                .findFirst()
                .ifPresent(record -> decrementMissed(record.getUserId()));
    }

    private void decrementScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.getScored().decrementAndGet();
        MatchTeam team = userStats.getTeam();
        gameScore.put(team, gameScore.get(team) - 1);
        lastScore.remove(userId);
    }

    private void decrementAutoScored(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.getAutoScored().decrementAndGet();
        MatchTeam team = MatchTeam.anotherTeam(userStats.getTeam());
        gameScore.put(team, gameScore.get(team) + 1);
        lastScore.remove(userId);
    }

    private void incrementMissed(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.getMissed().incrementAndGet();
    }

    private void decrementMissed(int userId) {
        ScoreTableRecord userStats = statsByUserId.get(userId);
        userStats.getMissed().decrementAndGet();
    }

    private boolean isGoalkeeper(ScoreTableRecord scoreTableRecord) {
        return scoreTableRecord.getPosition().equals(PlayPosition.GK);
    }

    private boolean isThisTeam(int userId, ScoreTableRecord scoreTableRecord) {
        return scoreTableRecord.getTeam().equals(getTeamByUserId(userId));
    }

    private boolean isAnotherTeam(int userId, ScoreTableRecord scoreTableRecord) {
        return scoreTableRecord.getTeam().equals(anotherTeam(getTeamByUserId(userId)));
    }

    private MatchTeam getTeamByUserId(int userId) {
        return statsByUserId.get(userId).getTeam();
    }
}