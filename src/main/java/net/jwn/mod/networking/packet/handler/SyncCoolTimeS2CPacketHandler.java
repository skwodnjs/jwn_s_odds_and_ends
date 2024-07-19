package net.jwn.mod.networking.packet.handler;

import net.jwn.mod.stuff.CoolTimeProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SyncCoolTimeS2CPacketHandler {
    public static void handlePacket(int cool_time) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        player.getCapability(CoolTimeProvider.CoolTime).ifPresent(coolTime -> {
            coolTime.set(cool_time);
        });
    };
}
