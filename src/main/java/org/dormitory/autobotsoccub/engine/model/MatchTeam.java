package org.dormitory.autobotsoccub.engine.model;

public enum MatchTeam {
    A, B;

    public static MatchTeam anotherTeam(MatchTeam team) {
        return team == A ? B : A;
    }
}