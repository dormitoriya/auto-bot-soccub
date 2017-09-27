package org.dormitory.autobotsoccub.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Map;

import static org.dormitory.autobotsoccub.model.MatchTeam.A;
import static org.dormitory.autobotsoccub.model.MatchTeam.B;
import static org.dormitory.autobotsoccub.model.PlayPosition.FW;
import static org.dormitory.autobotsoccub.model.PlayPosition.GK;

@Value
public class Game {
    @Getter private final Integer goalKeeperA;
    @Getter private final Integer forwardA;
    @Getter private final Integer goalKeeperB;
    @Getter private final Integer forwardB;

    private final Map<Integer, TeamWithPosition> playersByTeams;

    @Builder
    private Game(Integer goalKeeperA, Integer forwardA, Integer goalKeeperB, Integer forwardB) {
        this.goalKeeperA = goalKeeperA;
        this.forwardA = forwardA;
        this.goalKeeperB = goalKeeperB;
        this.forwardB = forwardB;
        ImmutableMap.Builder<Integer, TeamWithPosition> teamBuilder = new ImmutableMap.Builder<>();

        teamBuilder.put(goalKeeperA, TeamWithPosition.builder()
                .team(A)
                .position(GK)
                .build());
        teamBuilder.put(forwardA, TeamWithPosition.builder()
                .team(A)
                .position(FW)
                .build());
        teamBuilder.put(goalKeeperB, TeamWithPosition.builder()
                .team(B)
                .position(GK)
                .build());
        teamBuilder.put(forwardB, TeamWithPosition.builder()
                .team(B)
                .position(FW)
                .build());

        this.playersByTeams = teamBuilder.build();
    }

    public TeamWithPosition getUserGameInfo(Integer userId) {
        return playersByTeams.get(userId);
    }

    public List<Integer> getPlayers() {
        return ImmutableList.copyOf(playersByTeams.keySet());
    }

    @Value
    @Builder
    public static class TeamWithPosition {
        private MatchTeam team;
        private PlayPosition position;
    }
}