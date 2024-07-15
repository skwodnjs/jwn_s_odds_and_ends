package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.effect.ModEffects;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.SyncCoolTimeS2CPacket;
import net.jwn.mod.networking.packet.SyncStatS2CPacket;
import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.stuff.PassiveOperator;
import net.jwn.mod.util.StatType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
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
        Map<String, Float> map = new HashMap<>();
        for (StatType type : StatType.values()) {
            map.put(type.name, event.getEntity().getPersistentData().getFloat(type.name));
        }
        ModMessages.sendToPlayer(new SyncStatS2CPacket(map), (ServerPlayer) event.getEntity());
//        ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(event.getEntity().getPersistentData().getInt("cool_time")), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(event.getEntity()), (ServerPlayer) event.getEntity());
    }
    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        // ATTACH CAPABILITIES : STUFF I FOUNT / MY STUFF
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
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        // ONLY SERVER

        // COOL TIME needs to be synchronized with the client because it must be displayed on the client's HUD.
        // STAT needs to be synchronized with the client because of the MINING SPEED
        Map<String, Float> map = new HashMap<>();
        for (StatType type : StatType.values()) {
            map.put(type.name, event.getEntity().getPersistentData().getFloat(type.name));
        }
        ModMessages.sendToPlayer(new SyncStatS2CPacket(map), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(event.getEntity().getPersistentData().getInt("cool_time")), (ServerPlayer) event.getEntity());
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
        event.getOriginal().invalidateCaps();

        // COOL TIME needs to be synchronized with the client because it must be displayed on the client's HUD.
        // STAT needs to be synchronized with the client because of the MINING SPEED
        Map<String, Float> map = new HashMap<>();
        for (StatType type : StatType.values()) {
            map.put(type.name, event.getEntity().getPersistentData().getFloat(type.name));
        }
        ModMessages.sendToPlayer(new SyncStatS2CPacket(map), (ServerPlayer) event.getEntity());
        ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(event.getEntity().getPersistentData().getInt("cool_time")), (ServerPlayer) event.getEntity());
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
        int cool_time = event.player.getPersistentData().getInt("cool_time");
        if (cool_time > 0 && event.phase == TickEvent.Phase.END) {
            event.player.getPersistentData().putInt("cool_time", cool_time - 1);
        }

        if (!event.player.level().isClientSide && event.phase == TickEvent.Phase.START) {
            // 10 : CAN
            PassiveOperator.can(event);
        }
    }
    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event) {
        // ONLY SERVER

        // 2 : AMULET
        PassiveOperator.amulet(event);
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
        Map<String, Float> materialSpeedMap = new HashMap<>();
        materialSpeedMap.put("WOOD", 2.0f);
        materialSpeedMap.put("STONE", 4.0f);
        materialSpeedMap.put("IRON", 6.0f);
        materialSpeedMap.put("DIAMOND", 8.0f);
        materialSpeedMap.put("GOLD", 12.0f);
        materialSpeedMap.put("NETHERITE", 9.0f);

        Player player = event.getEntity();
        BlockState blockState = event.getState();
        ItemStack mainHandItem = player.getMainHandItem();

        float speedMultiplier = 1.0f + player.getPersistentData().getFloat(StatType.MINING_SPEED.name) * 3 / 20;

        boolean canHarvest = blockState.requiresCorrectToolForDrops() && mainHandItem.isCorrectToolForDrops(blockState);

        if (canHarvest) {
            if (mainHandItem.getItem() instanceof TieredItem tieredItem) {
                String materialName = tieredItem.getTier().toString();
                if (materialSpeedMap.containsKey(materialName)) {
                    speedMultiplier = materialSpeedMap.get(materialName);
                }
            }
            if (mainHandItem.isEnchanted()) {
                int efficiencyLevel = mainHandItem.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY);
                if (efficiencyLevel > 0) {
                    speedMultiplier += (efficiencyLevel * efficiencyLevel) + 1;
                }
            }
        }

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

    }
}
