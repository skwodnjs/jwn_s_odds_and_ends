package net.jwn.mod.networking.packet.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExS2CPacketHandler {
    public static void handlePacket(Supplier<NetworkEvent.Context> supplier) {
        Player player = Minecraft.getInstance().player;
        assert player != null : "my stuff sync S2C packet, player null";
        player.sendSystemMessage(Component.literal("hello world!"));
    }
}
