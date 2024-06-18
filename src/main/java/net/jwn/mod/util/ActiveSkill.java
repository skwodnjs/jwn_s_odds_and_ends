package net.jwn.mod.util;

import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.UseSkillC2SPacket;
import net.minecraft.world.entity.player.Player;

public class ActiveSkill {
    // when active skill key pressed (in client side), 1) check what is main active item 2) use skill
    private static int id;

    public static void useSkill(Player player) {
        ModMessages.sendToServer(new UseSkillC2SPacket());
    }
}