package net.jwn.mod.util;

import net.minecraft.ChatFormatting;

public enum StuffRank {
    RARE(3, ChatFormatting.WHITE),
    EPIC(5, ChatFormatting.LIGHT_PURPLE),
    UNIQUE(5, ChatFormatting.YELLOW),
    LEGENDARY(5, ChatFormatting.GREEN);
    public final int max_level;
    public final ChatFormatting color;
    StuffRank(int max_level, ChatFormatting color) {
        this.max_level = max_level;
        this.color = color;
    }
    public StuffRank next() {
        int nextOrdinal = this.ordinal() + 1;

        if (nextOrdinal >= values().length) {
            nextOrdinal = 0;
        }

        return values()[nextOrdinal];
    }
    public StuffRank prev() {
        int prevOrdinal;
        if (this.ordinal() == 0) {
            prevOrdinal = values().length;
        } else {
            prevOrdinal = this.ordinal() - 1;
        }
        return values()[prevOrdinal];
    }
}