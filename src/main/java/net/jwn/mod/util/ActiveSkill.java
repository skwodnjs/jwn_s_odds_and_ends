package net.jwn.mod.util;

import net.jwn.mod.item.ActiveStuff;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.skill.PoopSkillC2SPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ActiveSkill {
    // when active skill key pressed (in client side), 1) check what is main active item 2) use skill
    private static int id;

    public static void useSkill(Player player) {
        int COOL_TIME = player.getPersistentData().getInt("cool_time");

        if (player.getPersistentData().getInt("cool_time") != 0) {
            player.sendSystemMessage(Component.literal("§c아직 스킬을 사용할 수 없습니다.§c"));
        } else {
            id = player.getPersistentData().getInt("main_active_stuff_id");
            int[] my_active_stuff_ids = player.getPersistentData().getIntArray("my_active_stuff_ids");
            int[] my_active_stuff_levels = player.getPersistentData().getIntArray("my_active_stuff_levels");

            int index = -1;
            for (int i = 0; i < my_active_stuff_ids.length; i++) {
                if (my_active_stuff_ids[i] == id) {
                    index = i;
                    break;
                }
            }

            int level = 0;
            if (index != -1) {
                level = my_active_stuff_levels[index];
            }

            Stuff stuff = StuffIFound.ALL_OF_STUFF.get(id);
            if (stuff instanceof ActiveStuff activeStuff) {
                COOL_TIME = activeStuff.t0 - activeStuff.weight * level;
                System.out.println(COOL_TIME);
            }

            player.getPersistentData().putInt("cool_time", COOL_TIME);
            if (id == 1) skillPoop(player);
        }
    }
    // ------------- SKILL -------------
    private static void skillPoop(Player player) {
        ModMessages.sendToServer(new PoopSkillC2SPacket());
    }
}