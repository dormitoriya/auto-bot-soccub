package org.dormitory.autobotsoccub.engine.model;


public enum PlayPosition {
    GK, FW;

    public static PlayPosition anotherPosition(PlayPosition position) {
        return position == GK ? FW : GK;
    }
}