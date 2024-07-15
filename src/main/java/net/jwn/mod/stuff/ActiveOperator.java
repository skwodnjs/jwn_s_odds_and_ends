package net.jwn.mod.stuff;

import net.jwn.mod.Main;
import net.jwn.mod.block.ModBlocks;
import net.jwn.mod.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
            BlockPos pos = player.getOnPos().offset(0, 1, 0);
            int[] posArray = {pos.getX(), pos.getY(), pos.getZ()};
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            player.getPersistentData().putIntArray("cell_phone_pos", posArray);
            player.getPersistentData().putFloat("cell_phone_yaw", yaw);
            player.getPersistentData().putFloat("cell_phone_pitch", pitch);

            return false;
        } else {
            int[] pos = player.getPersistentData().getIntArray("cell_phone_pos");
            float yaw = player.getPersistentData().getFloat("cell_phone_yaw");
            float pitch = player.getPersistentData().getFloat("cell_phone_pitch");
            if (pos.length == 3) {
                ServerLevel overWorld = player.getServer().getLevel(Level.OVERWORLD);
                // 띠리링 전화 소리
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                        SoundSource.PLAYERS, 1F, 0.2F);
                player.teleportTo(overWorld, pos[0], pos[1], pos[2], yaw, pitch);
            } else {
                player.sendSystemMessage(Component.translatable("message." + Main.MOD_ID + ".cell_phone.no_save"));
            }
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
//    public static boolean dice_i(ServerPlayer player) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
//            if (player.getInventory().getItem(i).getItem() instanceof Stuff) list.add(i);
//        }
//        int index = (int) (Math.random() * list.size());
//
//        Stuff stuff = (Stuff) player.getInventory().getItem(index).getItem();
//        int count = player.getInventory().getItem(index).getCount();
//        ItemStack newStuff = AllOfStuff.getRandomDowngrade(stuff.rank).getDefaultInstance();
//        newStuff.setCount(count);
//        player.getInventory().setItem(index, newStuff);
//        return true;
//    }
//    public static boolean dice_ii(ServerPlayer player) {
//        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
//            if (player.getInventory().getItem(i).getItem() instanceof Stuff stuff) {
//                int count = player.getInventory().getItem(i).getCount();
//                ItemStack newStuff = AllOfStuff.getRandomDowngrade(stuff.rank).getDefaultInstance();
//                newStuff.setCount(count);
//                player.getInventory().setItem(i, newStuff);
//            }
//        }
//        return true;
//    }
//    public static boolean dice_iii(ServerPlayer player) {
//        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
//            if (player.getInventory().getItem(i).getItem() instanceof Stuff stuff) {
//                int count = player.getInventory().getItem(i).getCount();
//                ItemStack newStuff = AllOfStuff.getRandom(stuff.rank).getDefaultInstance();
//                newStuff.setCount(count);
//                player.getInventory().setItem(i, newStuff);
//            }
//        }
//        return true;
//    }
//    public static boolean dice_100(ServerPlayer player) {
//        AtomicBoolean success = new AtomicBoolean(true);
//        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
//            List<Integer> list = new ArrayList<>();
//            for (int id : myStuff.myActiveStuffIds) {
//                if (id == 0) break;
//                if (AllOfStuff.ALL_OF_STUFF.get(id).rank != StuffRank.LEGENDARY) {
//                    if (myStuff.canUpgrade(id)) list.add(id);
//                }
//            }
//            for (int id : myStuff.myPassiveStuffIds) {
//                if (id == 0) break;
//                if (AllOfStuff.ALL_OF_STUFF.get(id).rank != StuffRank.LEGENDARY) {
//                    if (myStuff.canUpgrade(id)) list.add(id);
//                }
//            }
//            if (list.isEmpty()) {
//                player.sendSystemMessage(Component.literal("아이템을 업그레이드 할 수 없습니다."));
//                success.set(false);
//            } else {
//                int index = (int) (Math.random() * list.size());
//                int oldId = list.get(index);
//                int oldLevel = myStuff.getLevel(oldId);
//
//                Stuff oldStuff = AllOfStuff.ALL_OF_STUFF.get(oldId);
//                List<Integer> pool = myStuff.getRandomIdPool(oldStuff.rank.next(), oldStuff.type);
//
//                int newId = pool.get((int) (Math.random() * pool.size()));
//                Stuff newStuff = AllOfStuff.ALL_OF_STUFF.get(newId);
//
//                System.out.println(oldId + " -> " + newId);
//
//                myStuff.remove(oldId);
//                for (int i = 0; i < oldLevel; i++) {
//                    myStuff.register(newStuff);
//                }
//
//                player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
//                    if (newStuff.type == StuffType.ACTIVE) {
//                        stuffIFound.updateStuffIFound(newStuff.id, 2);
//                    } else if (newStuff.type == StuffType.PASSIVE){
//                        stuffIFound.updateStuffIFound(newStuff.id, 3);
//                    }
//                });
//
//                StatOperator.reCalculate(player);
//                Map<String, Float> map = new HashMap<>();
//                for (StatType type : StatType.values()) {
//                    map.put(type.name, player.getPersistentData().getFloat(type.name));
//                }
//                ModMessages.sendToPlayer(new SyncStatS2CPacket(map), player);
//
//                player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
//                    ModMessages.sendToPlayer(new SyncMyStuffS2CPacket(myStuff, stuffIFound), player);
//                });
//            }
//        });
//        return success.get();
//    }
//    public static boolean dice_999(ServerPlayer player, int level) {
//        AtomicBoolean success = new AtomicBoolean(true);
//        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
//            int activeToEpic = 0, activeToUnique = 0, activeToLegendary = 0;
//            for (int id : myStuff.myActiveStuffIds) {
//                if (id == 0) break;
//                switch (AllOfStuff.ALL_OF_STUFF.get(id).rank) {
//                    case RARE -> activeToEpic += 1;
//                    case EPIC -> activeToUnique += 1;
//                    case UNIQUE -> activeToLegendary += 1;
//                }
//            }
//            int passiveToEpic = 0, passiveToUnique = 0, passiveToLegendary = 0;
//            for (int id : myStuff.myPassiveStuffIds) {
//                if (id == 0) break;
//                switch (AllOfStuff.ALL_OF_STUFF.get(id).rank) {
//                    case RARE -> passiveToEpic += 1;
//                    case EPIC -> passiveToUnique += 1;
//                    case UNIQUE -> passiveToLegendary += 1;
//                }
//            }
//            if (AllOfStuff.getStuffCount(StuffRank.EPIC, StuffType.PASSIVE) < passiveToEpic
//                    || AllOfStuff.getStuffCount(StuffRank.UNIQUE, StuffType.PASSIVE) < passiveToUnique
//                    || AllOfStuff.getStuffCount(StuffRank.LEGENDARY, StuffType.PASSIVE) < passiveToLegendary
//                    || AllOfStuff.getStuffCount(StuffRank.EPIC, StuffType.ACTIVE) < activeToEpic
//                    || AllOfStuff.getStuffCount(StuffRank.UNIQUE, StuffType.ACTIVE) < activeToUnique
//                    || AllOfStuff.getStuffCount(StuffRank.LEGENDARY, StuffType.ACTIVE) < activeToLegendary) {
//                success.set(false);
//            }
//            if (!success.get()) {
//                player.sendSystemMessage(Component.literal("아이템을 업그레이드 할 수 없습니다."));
//            } else {
//                int[] activeIds = myStuff.myActiveStuffIds;
//                int[] passiveIds = myStuff.myPassiveStuffIds;
//                myStuff.myActiveStuffIds = new int[AllOfStuff.MAX_ACTIVE_STUFF];
//                myStuff.myPassiveStuffIds = new int[AllOfStuff.MAX_PASSIVE_STUFF];
//                for (int index = 0; index < activeIds.length; index++) {
//                    int oldId = activeIds[index];
//                    if (oldId == 0) break;
//
//                    int oldLevel = myStuff.myActiveStuffLevels[index];
//                    Stuff oldStuff = AllOfStuff.ALL_OF_STUFF.get(oldId);
//                    if (oldStuff.rank == StuffRank.LEGENDARY) break;
//
//
//                    List<Integer> pool;
//                    double r = Math.random();
//                    System.out.println(r);
//                    if (r < 0.4 + 0.05 * level) {
//                        pool = myStuff.getRandomIdPool(oldStuff.rank.next(), oldStuff.type);
//                    } else {
//                        pool = myStuff.getRandomIdPool(oldStuff.rank, oldStuff.type);
//                    }
//
//                    int newId = pool.get((int) (Math.random() * pool.size()));
//                    Stuff newStuff = AllOfStuff.ALL_OF_STUFF.get(newId);
//
//                    System.out.println(oldId + " -> " + newId);
//
//                    myStuff.myActiveStuffLevels[index] = 0;
//                    for (int i = 0; i < oldLevel; i++) {
//                        myStuff.register(newStuff);
//                    }
//
//                    player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
//                        if (newStuff.type == StuffType.ACTIVE) {
//                            stuffIFound.updateStuffIFound(newStuff.id, 2);
//                        } else if (newStuff.type == StuffType.PASSIVE){
//                            stuffIFound.updateStuffIFound(newStuff.id, 3);
//                        }
//                    });
//                }
//                for (int index = 0; index < passiveIds.length; index++) {
//                    int oldId = passiveIds[index];
//                    if (oldId == 0) break;
//
//                    int oldLevel = myStuff.myPassiveStuffLevels[index];
//                    Stuff oldStuff = AllOfStuff.ALL_OF_STUFF.get(oldId);
//                    if (oldStuff.rank == StuffRank.LEGENDARY) break;
//
//                    List<Integer> pool;
//                    double r = Math.random();
//                    if (r < 0.4 + 0.1 * level) {
//                        pool = myStuff.getRandomIdPool(oldStuff.rank.next(), oldStuff.type);
//                    } else {
//                        pool = myStuff.getRandomIdPool(oldStuff.rank, oldStuff.type);
//                    }
//                    System.out.println(r);
//                    int newId = pool.get((int) (Math.random() * pool.size()));
//                    Stuff newStuff = AllOfStuff.ALL_OF_STUFF.get(newId);
//
//                    System.out.println(oldId + " -> " + newId);
//
//                    myStuff.myPassiveStuffLevels[index] = 0;
//                    for (int i = 0; i < oldLevel; i++) {
//                        myStuff.register(newStuff);
//                    }
//
//                    player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
//                        if (newStuff.type == StuffType.ACTIVE) {
//                            stuffIFound.updateStuffIFound(newStuff.id, 2);
//                        } else if (newStuff.type == StuffType.PASSIVE){
//                            stuffIFound.updateStuffIFound(newStuff.id, 3);
//                        }
//                    });
//                }
//
//                StatOperator.reCalculate(player);
//                Map<String, Float> map = new HashMap<>();
//                for (StatType type : StatType.values()) {
//                    map.put(type.name, player.getPersistentData().getFloat(type.name));
//                }
//                ModMessages.sendToPlayer(new SyncStatS2CPacket(map), player);
//
//                player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
//                    ModMessages.sendToPlayer(new SyncMyStuffS2CPacket(myStuff, stuffIFound), player);
//                });
//            }
//        });
//        return success.get();
//    }
}
