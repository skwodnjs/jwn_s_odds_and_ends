package net.jwn.mod.networking.packet.handler;

import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SyncStuffIFoundS2CPacketHandler {
    public static void handlePacket(StuffIFound stuffIFound) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFoundCap -> {
            stuffIFoundCap.copyFrom(stuffIFound);
        });
    };
}
