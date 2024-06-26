package net.jwn.mod.util;

public enum StuffRank {
    RARE(3, "§7"),
    EPIC(5, "§d"),
    UNIQUE(5, "§7"),
    LEGENDARY(5, "§7");
    public final int max_level;
    public final String color_tag;
    StuffRank(int max_level, String color_tag) {
        this.max_level = max_level;
        this.color_tag = color_tag;
    }
}