package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.PassiveOperator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
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
                stuffIFound.updateStuffIFoundFirstTime(stuff.id);
            });
        }
    }
    @SubscribeEvent
    public static void onPlayerContainerEvent (PlayerContainerEvent event) {
        // ONLY SERVER
        for (int i = 0; i < event.getEntity().getInventory().getContainerSize(); i++) {
            if (event.getEntity().getInventory().getItem(i).getItem() instanceof Stuff stuff) {
                event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                    stuffIFound.updateStuffIFoundFirstTime(stuff.id);
                });
            }
        }
    }
    @SubscribeEvent
    public static void onItemCraftedEvent (PlayerEvent.ItemCraftedEvent event) {
        // CLIENT: 만들 때 한 번 발생
        // SERVER: 중첩된 횟수만큼 발생

//        crafted 도 container event 발생해서 굳이 안해도 될 듯
//        if (event.getCrafting().getItem() instanceof Stuff stuff) {
//            event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
//                stuffIFound.updateStuffIFoundFirstTime(stuff.id);
//            });
//        }
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
        // BOTH SIDE

        // cool time
        int cool_time = event.player.getPersistentData().getInt("cool_time");
        if (cool_time > 0 && event.phase == TickEvent.Phase.END) {
            event.player.getPersistentData().putInt("cool_time", cool_time - 1);
        }
        if (event.player.level().isClientSide) {
            event.player.sendSystemMessage(Component.literal(String.valueOf(cool_time)));
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

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        // ONLY SERVER

        // 2 amulet
        PassiveOperator.amulet(event);
    }

    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        // ONLY SERVER

        // 33 four leaf clover
        PassiveOperator.fourLeafClover(event);
    }

    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        // ONLY SERVER

        // 35 light feather
        PassiveOperator.lightFeather(event);
    }

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event) {

    }
}
