package net.jwn.mod.networking.packet.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SyncCoolTimeS2CPacketHandler {
    public static void handlePacket(int cool_time) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        player.getPersistentData().putInt("cool_time", cool_time);
    };
}
