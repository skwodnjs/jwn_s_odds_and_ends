package net.jwn.mod.util;

import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;

public class PassiveOperator {
    // ID: 2
    public static void amulet(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof EnderMan enderMan) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                int level = myStuff.getLevel(2);
                if (level != 0) {
                    double r = Math.random();
                    if (r < 0.4 + 0.1 * level) {
                        enderMan.kill();
                    }
                }
            });
        }
    }
    // ID: 4
    public static void fourLeafClover(BlockEvent.BreakEvent event) {
        event.getPlayer().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            int level = myStuff.getLevel(4);
            if (level != 0) {
                double r = Math.random();
                if (r < 0.2 + 0.1 * level) {
                    boolean success = false;
                    Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
                    if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.COAL.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.RAW_COPPER.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.RAW_IRON.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.RAW_GOLD.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.NETHER_GOLD_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.GOLD_NUGGET.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.REDSTONE_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.REDSTONE.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.LAPIS_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.LAPIS_LAZULI.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.EMERALD.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.DIAMOND.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.NETHER_QUARTZ_ORE) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.QUARTZ.getDefaultInstance()
                        ));
                        success = true;
                    } else if (block == Blocks.AMETHYST_CLUSTER) {
                        event.getPlayer().level().addFreshEntity(new ItemEntity(
                                event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5,
                                Items.AMETHYST_SHARD.getDefaultInstance()
                        ));
                        success = true;
                    }
                    if (success) {
                        // 뭔가 행운이 오는 듯한 소리
                        event.getPlayer().level().playSound(null, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(),
                                SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.PLAYERS, 1, 1);
                    }
                }
            }
        });
    }
    // ID: 6
    public static void lightStep(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!player.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                    if (myStuff.getLevel(6) != 0) {
                        event.setCanceled(true);
                    }
                });
            }
        }
    }
    // ID: 8
    public static void senseOfBalance(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource() == player.level().damageSources().fall()) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                int level = myStuff.getLevel(8);
                if (level != 0) {
                    double r = Math.random();
                    if (r < 0.70 + 0.05 * level) {
                        event.setCanceled(true);
                        player.setHealth(1.0f);
                        player.fallDistance = 0;
                    }
                }
            });
        }
    }
    public static void can(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(MobEffects.HUNGER)) {
            event.player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                int level = myStuff.getLevel(10);
                if (level != 0) {
                    double p = 1 - Math.pow(10, Math.log(0.2) / (60 - level * 20));
                    double r = Math.random();
                    if (r < p) {
                        event.player.removeEffect(MobEffects.HUNGER);
                    }
                }
            });
        }
    }
    public static void phantom_eye(PlayerSleepInBedEvent event) {
        event.getEntity().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            int level = myStuff.getLevel(13);
            if (level > 0) {
                Player player = event.getEntity();
                ServerLevel world = (ServerLevel) player.level();
                BlockPos bedPos = event.getPos();

                world.setBlock(bedPos, Blocks.AIR.defaultBlockState(), 3);
                world.explode(null, bedPos.getX(), bedPos.getY(), bedPos.getZ(), 5.0F, Level.ExplosionInteraction.BLOCK);
                event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
            }
        });
    }
    public static void smart_guy(PlayerEvent.BreakSpeed event) {
        event.getEntity().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            int level = myStuff.getLevel(26);
            if (level == 0) return;

            int i1 = 0;
            if (0 < level && level < 5) {
                i1 = 9;
            } else if (level == 5) {
                i1 = 36;
            }
            Inventory inventory = event.getEntity().getInventory();
            ItemStack heldItem = event.getEntity().getMainHandItem();
            BlockState blockState = event.getState();

            if (heldItem.isCorrectToolForDrops(blockState)) {
                return;
            }
            for (int i = 0; i < i1; i++) {
                ItemStack checkItem = inventory.getItem(i);
                if (checkItem.isCorrectToolForDrops(blockState)) {
                    if (i < 9) {
                        event.getEntity().getInventory().selected = i;
                    }
                    else {
                        event.getEntity().getInventory().setItem(event.getEntity().getInventory().selected, checkItem);
                        event.getEntity().getInventory().setItem(i, heldItem);
                    }
                    return;
                }
            }
        });
    }

    public static void hoglin_tusk(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getSource() == player.level().damageSources().onFire()) {
                player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                    int level = myStuff.getLevel(14);
                    event.setAmount(event.getAmount() * (1 + level / 3f));
                });
            }
        }
    }
    public static void pig_nose(TickEvent.PlayerTickEvent event) {
        event.player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            int level = myStuff.getLevel(15);
            if (level > 0) {
                if (Math.floor(event.player.getFoodData().getExhaustionLevel() % 2) == 1) {
                    event.player.getFoodData().setExhaustion(event.player.getFoodData().getExhaustionLevel() * (1 + level / 3f));
                }
            }
        });
    }
}
