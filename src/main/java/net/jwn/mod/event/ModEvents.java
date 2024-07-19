package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.effect.ModEffects;
import net.jwn.mod.item.PassiveStuff;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.SyncCoolTimeS2CPacket;
import net.jwn.mod.networking.packet.SyncMyStuffS2CPacket;
import net.jwn.mod.networking.packet.SyncStatS2CPacket;
import net.jwn.mod.stuff.*;
import net.jwn.mod.util.PassiveOperator;
import net.jwn.mod.util.StatType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        // ONLY SERVER

        // STUFF INIT ( LENGTH )
        event.getEntity().getCapability(MyStuffProvider.MY_STUFF).ifPresent(MyStuff::init);
        event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(StuffIFound::init);

        // COOL TIME needs to be synchronized with the client because it must be displayed on the client's HUD.
        // STAT needs to be synchronized with the client because of the MINING SPEED
        // MY STUFF needs to be synchronized with the client because some stuff operates on the client side
        ModMessages.sendToPlayer(new SyncStatS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncMyStuffS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());

    }
    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        // ATTACH CAPABILITIES
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(StuffIFoundProvider.STUFF_I_FOUND).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "stuff_i_found"), new StuffIFoundProvider());
            }
            if (!event.getObject().getCapability(MyStuffProvider.MY_STUFF).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "my_stuff"), new MyStuffProvider());
            }
            if (!event.getObject().getCapability(StuffDataProvider.STUFF_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "stuff_data"), new StuffDataProvider());
            }
            if (!event.getObject().getCapability(CoolTimeProvider.CoolTime).isPresent()) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "cool_time"), new CoolTimeProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        // ONLY SERVER

        // COOL TIME needs to be synchronized with the client because it must be displayed on the client's HUD.
        // STAT needs to be synchronized with the client because of the MINING SPEED
        // MY STUFF needs to be synchronized with the client because some stuff operates on the client side
        ModMessages.sendToPlayer(new SyncStatS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncMyStuffS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
    }
    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        // ONLY SERVER

        // Capabilities are removed when player cloned
        // STUFF data have to be preserved.
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
        event.getOriginal().getCapability(StuffDataProvider.STUFF_DATA).ifPresent(oldStore -> {
            event.getEntity().getCapability(StuffDataProvider.STUFF_DATA).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });

        // COOL TIME needs to be synchronized with the client because it must be displayed on the client's HUD.
        event.getOriginal().getCapability(CoolTimeProvider.CoolTime).ifPresent(oldStore -> {
            event.getEntity().getCapability(CoolTimeProvider.CoolTime).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });
        event.getOriginal().invalidateCaps();

        // STAT needs to be synchronized with the client because of the MINING SPEED
        ModMessages.sendToPlayer(new SyncStatS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityItemPickupEvent(EntityItemPickupEvent event) {
        // ONLY SERVER

        // STUFF I FOUND
        if (event.getItem().getItem().getItem() instanceof Stuff stuff) {
            event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                stuffIFound.updateStuffIFound(stuff.id, 1);
            });
        }
    }
    @SubscribeEvent
    public static void onPlayerContainerEvent (PlayerContainerEvent event) {
        // ONLY SERVER

        // STUFF I FOUND
        for (int i = 0; i < event.getEntity().getInventory().getContainerSize(); i++) {
            if (event.getEntity().getInventory().getItem(i).getItem() instanceof Stuff stuff) {
                event.getEntity().getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                    stuffIFound.updateStuffIFound(stuff.id, 1);
                });
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        // BOTH SIDE

        // COOL TIME
        if (event.phase == TickEvent.Phase.END) {
            event.player.getCapability(CoolTimeProvider.CoolTime).ifPresent(CoolTime::sub);
        }

        // 10 : CAN
        PassiveOperator.can(event);
    }
    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        // ONLY SERVER

        // 2 : AMULET
        PassiveOperator.amulet(event);

        // 28 : WOOL COAT
        PassiveOperator.wool_coat(event);

        // 29 : FORTIFIED EGG
        PassiveOperator.fortified_egg(event);
    }
    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        // ONLY SERVER

        // 4 : FOUR LEAF CLOVER
        PassiveOperator.fourLeafClover(event);
    }
    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        // ONLY SERVER

        // 6 : LIGHT FEATHER
        PassiveOperator.lightStep(event);
    }
    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        // BOTH SIDE

        // STAT: MINING SPEED
        Player player = event.getEntity();
        BlockState blockState = event.getState();
        ItemStack mainHandItem = player.getMainHandItem();

        float speedMultiplier = mainHandItem.getDestroySpeed(blockState) + player.getPersistentData().getFloat(StatType.MINING_SPEED.name) * 3 / 20;
        if (player.hasEffect(MobEffects.DIG_SPEED)) {
            int hasteLevel = player.getEffect(MobEffects.DIG_SPEED).getAmplifier() + 1;
            speedMultiplier *= 0.2f * hasteLevel + 1;
        }
        if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
            int fatigueLevel = player.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier();
            speedMultiplier *= (float) Math.pow(0.3f, Math.min(fatigueLevel, 4));
        }
        if (player.isInWater() && mainHandItem.getEnchantmentLevel(Enchantments.AQUA_AFFINITY) == 0) {
            speedMultiplier /= 5.0f;
        }
        if (!player.onGround()) {
            speedMultiplier /= 5.0f;
        }

        event.setNewSpeed(speedMultiplier);

        // 26 : SMART GUY
        PassiveOperator.smart_guy(event);

        System.out.println(event.getNewSpeed());
    }
    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        // ONLY SERVER

        // 8 : LIGHT FEATHER
        PassiveOperator.senseOfBalance(event);
    }
    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event) {
        // ONLY SERVER

        // EXP_BOOST effect
        Player player = event.getEntity();
        if (player.hasEffect(ModEffects.EXP_BOOST.get())) {
            int level = player.getEffect(ModEffects.EXP_BOOST.get()).getAmplifier();
            ExperienceOrb orb = event.getOrb();
            int originalXp = orb.getValue();
            event.getEntity().giveExperiencePoints(originalXp * (level + 9));
            orb.remove(Entity.RemovalReason.DISCARDED);
        }
    }
    @SubscribeEvent
    public static void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event) {
        // ONLY SERVER
//        System.out.println(event.getEntity().level().isClientSide ? "CLIENT" : "SERVER");

        // 25 : PHANTOM EYE
        PassiveOperator.phantom_eye(event);
    }
}
