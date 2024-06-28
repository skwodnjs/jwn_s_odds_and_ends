package net.jwn.mod.networking.packet.handler;

import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SyncCoolTimeS2CPacketHandler {
    public static void handlePacket(int cooltime) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        player.getPersistentData().putInt("cool_time", cooltime);
    };
}
