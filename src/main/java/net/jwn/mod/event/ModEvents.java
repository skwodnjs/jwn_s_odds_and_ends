package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        // ONLY SERVER
        event.getEntity().getCapability(MyStuffProvider.MY_STUFF).ifPresent(MyStuff::init);
        event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(StuffIFound::init);
    }
    @SubscribeEvent
    public static void onEntityItemPickupEvent(EntityItemPickupEvent event) {
        // ONLY SERVER
        if (event.getItem().getItem().getItem() instanceof Stuff stuff) {
            event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                stuffIFound.updateStuffIFound(stuff.id);
            });
        }
    }
    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(StuffIFoundProvider.STUFF_I_FOUND).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "stuff_i_found"), new StuffIFoundProvider());
            }
            if (!event.getObject().getCapability(MyStuffProvider.MY_STUFF).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "my_stuff"), new MyStuffProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        // cool time
        int cool_time = event.player.getPersistentData().getInt("cool_time");
        if (!event.player.level().isClientSide && cool_time > 0 && event.phase == TickEvent.Phase.END) {
            event.player.getPersistentData().putInt("cool_time", cool_time - 1);
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(MyStuffProvider.MY_STUFF).ifPresent(oldStore -> {
            event.getEntity().getCapability(MyStuffProvider.MY_STUFF).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
        event.getOriginal().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(oldStore -> {
            event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
        event.getOriginal().invalidateCaps();
    }
}
