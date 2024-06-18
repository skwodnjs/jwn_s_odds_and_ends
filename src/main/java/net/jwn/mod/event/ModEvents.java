package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.MyStuffSyncS2CPacket;
import net.jwn.mod.networking.packet.StuffIFoundSyncS2CPacket;
import net.jwn.mod.util.MyStuff;
import net.jwn.mod.util.StuffIFound;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println(event.getEntity().level().isClientSide ? "LOGGED IN : CLIENT" : "LOGGED IN : SERVER"); // only SERVER

        MyStuff.init(event.getEntity());
        StuffIFound.init(event.getEntity());    // for server
        ModMessages.sendToPlayer(new MyStuffSyncS2CPacket(
                event.getEntity().getPersistentData().getIntArray("my_active_stuff_ids"),
                event.getEntity().getPersistentData().getIntArray("my_active_stuff_levels"),
                event.getEntity().getPersistentData().getIntArray("my_passive_stuff_ids"),
                event.getEntity().getPersistentData().getIntArray("my_passive_stuff_levels"),
                event.getEntity().getPersistentData().getInt("main_active_stuff_id")
        ), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new StuffIFoundSyncS2CPacket(
                event.getEntity().getPersistentData().getIntArray("stuff_i_found")
        ), (ServerPlayer) event.getEntity()); // for client

    }

    @SubscribeEvent
    public static void onEntityItemPickupEvent(EntityItemPickupEvent event) {
        System.out.println(event.getEntity().level().isClientSide ? "LOGGED IN : CLIENT" : "LOGGED IN : SERVER"); // only SERVER

        if (event.getItem().getItem().getItem() instanceof Stuff stuff) {
            StuffIFound.register(event.getEntity(), stuff);
            ModMessages.sendToPlayer(new StuffIFoundSyncS2CPacket(
                    event.getEntity().getPersistentData().getIntArray("stuff_i_found")
            ), (ServerPlayer) event.getEntity());
        }
    }
}
