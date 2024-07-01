package net.jwn.mod.util;

import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class StatOperator {
    public static final float MAX_HEALTH_STAT = 40;
    public static final float MAX_STAT = 20;

    private static final UUID HEALTH_UUID = UUID.fromString("93e9087c-a397-4164-bf37-a2dc2603bb6d");
    private static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("55c9add0-afb0-41e5-b910-8450a3ebb60c");
    private static final UUID SWIM_SPEED_UUID = UUID.fromString("3402e770-99d1-4ab8-91be-8100889a82ed");
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("2b6cf773-6867-41cd-a642-956198e56130");
    private static final UUID KNOCKBACK_RESISTANCE_UUID = UUID.fromString("5bba596d-9109-4a85-baf2-be4d0d333d6a");
    private static final UUID LUCK_UUID = UUID.fromString("bd852fbd-d3c2-4cdb-aca4-ba0e27e3a7b0");

    public static void reCalculate(Player player) {
        player.sendSystemMessage(Component.literal("reCalculate"));
        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            Map<String, Float> map = new HashMap<>();
            for (StatType type : StatType.values()) {
                map.put(type.name, 0.0f);
            }
            for (int i = 0; i < AllOfStuff.MAX_PASSIVE_STUFF; i++) {
                if (myStuff.myPassiveStuffIds[i] == 0) break;
                else {
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(myStuff.myPassiveStuffIds[i]);
                    for (Stat stat : stuff.stats) {
                        float value = map.get(stat.type().name);
                        value += (float) (stat.value() * 0.5 + stat.value() * 0.5 * myStuff.myPassiveStuffLevels[i] / stuff.rank.max_level);
                        map.put(stat.type().name, value);
                    }
                }
            }
            for (int i = 0; i < AllOfStuff.MAX_ACTIVE_STUFF; i++) {
                if (myStuff.myActiveStuffIds[i] == 0) break;
                else {
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(myStuff.myActiveStuffIds[i]);
                    for (Stat stat : stuff.stats) {
                        float value = map.get(stat.type().name);
                        value += (float) (stat.value() * 0.5 + stat.value() * 0.5 * myStuff.myActiveStuffLevels[i] / stuff.rank.max_level);
                        map.put(stat.type().name, value);
                    }
                }
            }
            for (Map.Entry<String, Float> entry : map.entrySet()) {
                if (Objects.equals(entry.getKey(), StatType.HEALTH.name)) {
                    player.getPersistentData().putFloat(entry.getKey(), Math.min(entry.getValue(), MAX_HEALTH_STAT));
                } else {
                    player.getPersistentData().putFloat(entry.getKey(), Math.min(entry.getValue(), MAX_STAT));

                }
            }
            operate(player);
        });
    }

    private static void operate(Player player) {
        // HEALTH
        if (player.getAttribute(Attributes.MAX_HEALTH).getModifier(HEALTH_UUID) != null) {
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH_UUID);
        }

        float health = player.getPersistentData().getFloat(StatType.HEALTH.name);

        AttributeModifier healthModifier = new AttributeModifier(HEALTH_UUID, "Health", health, AttributeModifier.Operation.ADDITION);
        player.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthModifier);

        // SPEED
        if (player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(MOVEMENT_SPEED_UUID) != null) {
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MOVEMENT_SPEED_UUID);
        }
        if (player.getAttribute(ForgeMod.SWIM_SPEED.get()).getModifier(SWIM_SPEED_UUID) != null) {
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).removeModifier(SWIM_SPEED_UUID);
        }

        float speed = player.getPersistentData().getFloat(StatType.SPEED.name);
        AttributeModifier movementSpeedModifier = new AttributeModifier(MOVEMENT_SPEED_UUID, "Movement speed", speed * 0.06 / 20, AttributeModifier.Operation.ADDITION);
        player.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(movementSpeedModifier);
        AttributeModifier swimSpeedModifier = new AttributeModifier(SWIM_SPEED_UUID, "Swim speed", speed * 1.5 / 20, AttributeModifier.Operation.ADDITION);
        player.getAttribute(ForgeMod.SWIM_SPEED.get()).addPermanentModifier(swimSpeedModifier);

        // MINING SPEED
        // NOTHING TO DO HERE
        // ONLY IN PlayerEvent.BreakSpeed EVENT

        // ATTACK DAMAGE
        if (player.getAttribute(Attributes.ATTACK_DAMAGE).getModifier(ATTACK_DAMAGE_UUID) != null) {
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_UUID);
        }

        float damage = player.getPersistentData().getFloat(StatType.ATTACK_DAMAGE.name);

        AttributeModifier damageModifier = new AttributeModifier(ATTACK_DAMAGE_UUID, "Attack damage", damage * 6 / 20, AttributeModifier.Operation.ADDITION);
        player.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(damageModifier);

        // KNOCKBACK RESISTANCE
        if (player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getModifier(KNOCKBACK_RESISTANCE_UUID) != null) {
            player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).removeModifier(KNOCKBACK_RESISTANCE_UUID);
        }

        float knockback_resistance = player.getPersistentData().getFloat(StatType.KNOCKBACK_RESISTANCE.name);

        AttributeModifier knockbackResistanceModifier = new AttributeModifier(KNOCKBACK_RESISTANCE_UUID, "Knockback resistance", knockback_resistance * 0.8 / 20, AttributeModifier.Operation.ADDITION);
        player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(knockbackResistanceModifier);

        // LUCK
        if (player.getAttribute(Attributes.LUCK).getModifier(LUCK_UUID) != null) {
            player.getAttribute(Attributes.LUCK).removeModifier(LUCK_UUID);
        }

        float luck = player.getPersistentData().getFloat(StatType.LUCK.name);

        AttributeModifier luckModifier = new AttributeModifier(LUCK_UUID, "Luck", luck * 5 / 20, AttributeModifier.Operation.ADDITION);
        player.getAttribute(Attributes.LUCK).addPermanentModifier(luckModifier);
    }

    // ------------------------------------ TEST ------------------------------------
    public static void printStat(Player player) {
        for (StatType type : StatType.values()) {
            player.sendSystemMessage(Component.literal(
                    type.name.toUpperCase() + ": " + player.getPersistentData().getFloat(type.name)
            ));
        }
    }
    public static void printAttribute(Player player) {
        player.sendSystemMessage(Component.literal(
                "MAX_HEALTH: " + player.getAttribute(Attributes.MAX_HEALTH).getValue()
        ));
        player.sendSystemMessage(Component.literal(
                "MOVEMENT_SPEED: " + player.getAttribute(Attributes.MOVEMENT_SPEED).getValue()
        ));
        player.sendSystemMessage(Component.literal(
                "SWIM_SPEED: " + player.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue()
        ));
        player.sendSystemMessage(Component.literal(
                "ATTACK_DAMAGE: " + player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()
        ));
        player.sendSystemMessage(Component.literal(
                "KNOCKBACK_RESISTANCE: " + player.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue()
        ));
        player.sendSystemMessage(Component.literal(
                "LUCK: " + player.getAttribute(Attributes.LUCK).getValue()
        ));
    }
    public static void reset(Player player) {
        for (StatType type : StatType.values()) {
            player.getPersistentData().putFloat(type.name, 0.0f);
        }
        reCalculate(player);
    }
}
