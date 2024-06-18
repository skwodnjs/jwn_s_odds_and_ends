package net.jwn.mod.util;

public enum StuffRank {
    RARE(3),
    EPIC(5),
    UNIQUE(5),
    LEGENDARY(5);
    public final int max_level;
    StuffRank(int max_level) {
        this.max_level = max_level;
    }
}