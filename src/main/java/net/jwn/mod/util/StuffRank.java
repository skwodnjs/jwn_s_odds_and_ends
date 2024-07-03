package net.jwn.mod.util;

public enum StuffRank {
    RARE(3, "§7"),
    EPIC(5, "§5"),
    UNIQUE(5, "§e"),
    LEGENDARY(5, "§a");
    public final int max_level;
    public final String color_tag;
    StuffRank(int max_level, String color_tag) {
        this.max_level = max_level;
        this.color_tag = color_tag;
    }
    public StuffRank next() {
        int nextOrdinal = this.ordinal() + 1;

        if (nextOrdinal >= values().length) {
            nextOrdinal = 0;
        }

        return values()[nextOrdinal];
    }
}