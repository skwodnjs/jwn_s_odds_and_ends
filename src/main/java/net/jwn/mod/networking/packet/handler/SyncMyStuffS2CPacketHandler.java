package net.jwn.mod.networking.packet.handler;

import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SyncMyStuffS2CPacketHandler {
    public static void handlePacket(MyStuff myStuff) {
        Player player = Minecraft.getInstance().player;
        assert player != null;

        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuffCap -> {
            myStuffCap.copyFrom(myStuff);
        });
    };
}
