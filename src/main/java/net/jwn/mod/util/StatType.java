package net.jwn.mod.util;

public enum StatType {
    HEALTH("health"),
    SPEED("speed"),
    MINING_SPEED("mining_speed"),
    ATTACK_DAMAGE("attack_damage"),
    KNOCKBACK_RESISTANCE("knockback_resistance"),
    LUCK("luck");
    public final String name;
    StatType(String name) {
        this.name = name;
    }
}
