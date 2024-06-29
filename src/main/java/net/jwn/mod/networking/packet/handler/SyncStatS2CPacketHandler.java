package net.jwn.mod.networking.packet.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class SyncStatS2CPacketHandler {
    public static void handlePacket(Map<String, Float> map) {
        Player player = Minecraft.getInstance().player;
        assert player != null;

        for (Map.Entry<String, Float> entry : map.entrySet()) {
            player.getPersistentData().putFloat(entry.getKey(), entry.getValue());
        }
    };
}
