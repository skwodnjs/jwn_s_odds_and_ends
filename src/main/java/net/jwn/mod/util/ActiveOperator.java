package net.jwn.mod.util;

import net.jwn.mod.Main;
import net.jwn.mod.block.ModBlocks;
import net.jwn.mod.effect.MilkBoyEffect;
import net.jwn.mod.effect.ModEffects;
import net.jwn.mod.gui.StorageBoxMenu;
import net.jwn.mod.stuff.StuffDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ActiveOperator {
    // ID: 1
    public static boolean poo(ServerPlayer player, int level) {
        Level pLevel = player.level();
        if (level < 3) {
            pLevel.setBlock(player.getOnPos().offset(0, 1, 0), ModBlocks.POO_BLOCK.get().defaultBlockState(), 3);
        } else {
            double r = Math.random();
            if (r < 0.3) {
                pLevel.setBlock(player.getOnPos().offset(0, 1, 0), ModBlocks.GOLD_POO_BLOCK.get().defaultBlockState(), 3);
            } else {
                pLevel.setBlock(player.getOnPos().offset(0, 1, 0), ModBlocks.POO_BLOCK.get().defaultBlockState(), 3);
            }
        }
        // 똥 싸는 소리
        pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                SoundSource.PLAYERS, 1F, 0.2F);
        return true;
    }
    // ID: 3
    public static boolean cellPhone(ServerPlayer player) {
        if (player.level().dimension() == Level.OVERWORLD) {
            player.sendSystemMessage(Component.translatable("message." + Main.MOD_ID + ".cell_phone.overworld"));
            player.getCapability(StuffDataProvider.STUFF_DATA).ifPresent(stuffData -> {
                BlockPos pos = player.getOnPos().offset(0, 1, 0);
                int[] posArray = {pos.getX(), pos.getY(), pos.getZ()};
                float yaw = player.getYRot();
                float pitch = player.getXRot();
                stuffData.putCellPhoneData(posArray, yaw, pitch);
            });

            return false;
        } else {
            player.getCapability(StuffDataProvider.STUFF_DATA).ifPresent(stuffData -> {
                int[] pos = stuffData.getCellPhonePos();
                float yaw = stuffData.getCellPhoneYaw();
                float pitch = stuffData.getCellPhonePitch();
                if (pos[1] > Integer.MIN_VALUE) {
                    ServerLevel overWorld = player.getServer().getLevel(Level.OVERWORLD);
                    // 띠리링 전화 소리
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                            SoundSource.PLAYERS, 1F, 0.2F);
                    player.teleportTo(overWorld, pos[0], pos[1], pos[2], yaw, pitch);
                } else {
                    player.sendSystemMessage(Component.translatable("message." + Main.MOD_ID + ".cell_phone.no_save"));
                }
            });
            return true;
        }
    }
    // ID: 5
    public static boolean dynamite(ServerPlayer player) {
        PrimedTnt primedtnt = new PrimedTnt(player.level(), player.position().x + 0.5D, player.position().y, player.position().z + 0.5D, player);
        player.level().addFreshEntity(primedtnt);
        player.level().playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        player.level().gameEvent(player, GameEvent.PRIME_FUSE, player.position());
        return true;
    }
    // ID: 7
    public static boolean wormhole(ServerPlayer player, int level) {
        Vec3 lookVec = new Vec3(player.getLookAngle().x, 0, player.getLookAngle().z).normalize();

        Vec3 footPosition = player.position();
        Vec3 headPosition = player.position().add(0, player.getBbHeight(), 0);

        Vec3 footTargetPosition = footPosition.add(lookVec.scale(8 + level * 3));
        Vec3 headTargetPosition = headPosition.add(lookVec.scale(8 + level * 3));


        HitResult footHitResult = player.level().clip(new ClipContext(
                footPosition,
                footTargetPosition,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        HitResult headHitResult = player.level().clip(new ClipContext(
                headPosition,
                headTargetPosition,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 targetPosition = footTargetPosition;
        if (footHitResult.getType() == HitResult.Type.BLOCK) {
            targetPosition = footHitResult.getLocation();
        }
        if (headHitResult.getType() == HitResult.Type.BLOCK) {
            targetPosition = headHitResult.getLocation().subtract(0, player.getBbHeight(), 0);
        }

        player.teleportTo(targetPosition.x, targetPosition.y, targetPosition.z);
        return true;
    }
    // ID: 9
    public static boolean mysterious_yellow_liquid(ServerPlayer player, int level) {
        player.addEffect(new MobEffectInstance(ModEffects.EXP_BOOST.get(), 60 * 20, level));
        return true;
    }
    // ID: 11
    public static boolean thank_you_for_the_meal(ServerPlayer player) {
        NonNullList<ItemStack> inventory = player.getInventory().items;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itemStack = inventory.get(i);
            if (itemStack.getItem() == Items.BOWL) {
                inventory.set(i, new ItemStack(Items.MUSHROOM_STEW, itemStack.getCount()));
            }
        }
        return true;
    }
    // ID: 13
    public static boolean witch_wand(ServerPlayer player, int level) {
        List<MobEffect> BENEFICIAL_EFFECTS = Arrays.asList(
                MobEffects.ABSORPTION,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.DAMAGE_BOOST,          // "strength"
                MobEffects.HEALTH_BOOST,
                MobEffects.DAMAGE_RESISTANCE,     // "resistance"
                MobEffects.MOVEMENT_SPEED,        // "speed"
                MobEffects.REGENERATION,
                MobEffects.INVISIBILITY,
                MobEffects.JUMP,                  // "jump_boost"
                MobEffects.WATER_BREATHING,
                MobEffects.NIGHT_VISION,
                MobEffects.SATURATION,
                MobEffects.LUCK,
                MobEffects.HEAL,                  // "instant_health"
                MobEffects.DIG_SPEED,             // "haste"
                MobEffects.SLOW_FALLING,
                MobEffects.CONDUIT_POWER,
                MobEffects.DOLPHINS_GRACE,
                MobEffects.HERO_OF_THE_VILLAGE
        );
        MobEffect mobEffect = BENEFICIAL_EFFECTS.get((int) (Math.random() * BENEFICIAL_EFFECTS.size()));
        player.addEffect(new MobEffectInstance(mobEffect, (10 + 2 * level) * 20, (int) (Math.random() * 2 + 1)));
        return true;
    }
    // ID: 19
    public static boolean piggy_bank(ServerPlayer player, int level) {
        Random random = new Random();
        double r = random.nextDouble() * 100;
        int count = (level * 4 - 3) + random.nextInt(6);
        if (r < 20) {
            player.addItem(new ItemStack(Items.COAL, count));
        } else if (r < 40) {
            player.addItem(new ItemStack(Items.COPPER_INGOT, count));
        } else if (r < 56) {
            player.addItem(new ItemStack(Items.IRON_INGOT, count));
        } else if (r < 66) {
            player.addItem(new ItemStack(Items.REDSTONE, count));
        } else if (r < 76) {
            player.addItem(new ItemStack(Items.LAPIS_LAZULI, count));
        } else if (r < 86) {
            player.addItem(new ItemStack(Items.EMERALD, count));
        } else if (r < 91) {
            player.addItem(new ItemStack(Items.QUARTZ, count));
        } else if (r < 96) {
            player.addItem(new ItemStack(Items.AMETHYST_SHARD, count));
        } else if (r < 99) {
            player.addItem(new ItemStack(Items.DIAMOND, count));
        } else {
            player.addItem(new ItemStack(Items.NETHERITE_INGOT, count));
        }
        return true;
    }
    // ID: 20
    public static boolean storage_box(ServerPlayer player) {
        player.openMenu(new SimpleMenuProvider((id, playerInventory, playerEntity) ->
                    new StorageBoxMenu(id, playerInventory), Component.literal("menu")));
        return true;
    }

    // ID: 30
    public static boolean milk_boy(ServerPlayer player, int level) {
        player.addEffect(new MobEffectInstance(ModEffects.MILK_BOY.get(), 10 * level * 20));
        return true;
    }
}
