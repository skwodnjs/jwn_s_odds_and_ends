package net.jwn.mod.networking.packet.handler;

import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SyncStuffS2CPacketHandler {
    public static void handlePacket(MyStuff myStuff, StuffIFound stuffIFound) {
        Player player = Minecraft.getInstance().player;
        assert player != null;

        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuffCap -> {
            myStuffCap.copyFrom(myStuff);
        });
        player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFoundCap -> {
            stuffIFoundCap.copyFrom(stuffIFound);
        });
    };
}
