package net.jwn.mod.networking.packet.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StuffIFoundSyncS2CPacketHandler {
    public static void handlePacket(Supplier<NetworkEvent.Context> supplier, int[] stuffIFound) {
        Player player = Minecraft.getInstance().player;
        assert player != null : "stuff i found S2C packet, player null";
        player.getPersistentData().putIntArray("stuff_i_found", stuffIFound);
    }
}
