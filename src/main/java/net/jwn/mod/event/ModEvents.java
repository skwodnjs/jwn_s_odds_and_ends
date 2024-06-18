package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.StuffSyncS2CPacket;
import net.jwn.mod.util.MyStuffController;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println(event.getEntity().level().isClientSide ? "LOGGED IN : CLIENT" : "LOGGED IN : SERVER"); // only SERVER

        MyStuffController.init(event.getEntity());    // for server
        ModMessages.sendToPlayer(new StuffSyncS2CPacket(
                event.getEntity().getPersistentData().getIntArray("my_active_stuff_ids"),
                event.getEntity().getPersistentData().getIntArray("my_active_stuff_levels"),
                event.getEntity().getPersistentData().getIntArray("my_passive_stuff_ids"),
                event.getEntity().getPersistentData().getIntArray("my_passive_stuff_levels"),
                event.getEntity().getPersistentData().getInt("main_active_stuff_id")
        ), (ServerPlayer) event.getEntity()); // for client
    }
}
