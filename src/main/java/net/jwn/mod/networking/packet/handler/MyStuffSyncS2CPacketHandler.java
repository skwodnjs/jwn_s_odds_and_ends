package net.jwn.mod.networking.packet.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MyStuffSyncS2CPacketHandler {
    public static void handlePacket(Supplier<NetworkEvent.Context> supplier, int[] myActiveStuffIds, int[] myActiveStuffLevels,
                                    int[] myPassiveStuffIds, int[] myPassiveStuffLevels, int mainActiveStuffId) {
        Player player = Minecraft.getInstance().player;
        assert player != null : "my stuff sync S2C packet, player null";
        player.getPersistentData().putIntArray("my_active_stuff_ids", myActiveStuffIds);
        player.getPersistentData().putIntArray("my_active_stuff_levels", myActiveStuffLevels);
        player.getPersistentData().putIntArray("my_passive_stuff_ids", myPassiveStuffIds);
        player.getPersistentData().putIntArray("my_passive_stuff_levels", myPassiveStuffLevels);
        player.getPersistentData().putInt("main_active_stuff_id", mainActiveStuffId);
    }
}
