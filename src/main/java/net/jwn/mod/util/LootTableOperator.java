package net.jwn.mod.util;

import net.jwn.mod.Main;
import net.jwn.mod.item.ModItems;
import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class LootTableOperator {
    // ALL
    @SubscribeEvent
    public static void allLivingEntity(LivingDeathEvent event) {
        if (event.getEntity() instanceof Mob mob && event.getSource().getEntity() instanceof Player player) {
            float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
            double r = Math.random();
            r /= (1 + 0.5 * luck);
            if (r < 0.0089) {
                dropItem(player.level(), mob.getOnPos(), ModItems.BATTERY.get().getDefaultInstance());
            } else if (r < 0.0121) {
                dropItem(player.level(), mob.getOnPos(), ModItems.DICE_LEVEL_FIVE.get().getDefaultInstance());
            } else if (r < 0.0165) {
                dropItem(player.level(), mob.getOnPos(), ModItems.DICE_LEVEL_FOUR.get().getDefaultInstance());
            } else if (r < 0.0242) {
                dropItem(player.level(), mob.getOnPos(), ModItems.DICE_LEVEL_THREE.get().getDefaultInstance());
            } else if (r < 0.0321) {
                dropItem(player.level(), mob.getOnPos(), ModItems.DICE_LEVEL_TWO.get().getDefaultInstance());
            } else if (r < 0.04) {
                dropItem(player.level(), mob.getOnPos(), ModItems.DICE_LEVEL_ONE.get().getDefaultInstance());
            }
        }
    }

    // 1
    @SubscribeEvent
    public static void dropPoo(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() instanceof FlowerBlock
                || event.getState().getBlock() instanceof LeavesBlock
                || event.getState().getBlock() == Blocks.GRASS_BLOCK
                || event.getState().getBlock() == Blocks.PODZOL
                || event.getState().getBlock() == Blocks.MYCELIUM
                || event.getState().getBlock() == Blocks.DIRT_PATH
                || event.getState().getBlock() == Blocks.DIRT
                || event.getState().getBlock() == Blocks.COARSE_DIRT
                || event.getState().getBlock() == Blocks.ROOTED_DIRT
                || event.getState().getBlock() == Blocks.FARMLAND
                || event.getState().getBlock() == Blocks.GRASS
                || event.getState().getBlock() == Blocks.TALL_GRASS
                || event.getState().getBlock() == Blocks.FERN
                || event.getState().getBlock() == Blocks.LARGE_FERN
                || event.getState().getBlock() == Blocks.DEAD_BUSH
        ) {
            event.getPlayer().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(1)) {
                    float luck = event.getPlayer().getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 1 + 0.4 * luck) {
                        dropItem(event.getPlayer().level(), event.getPos(), ModItems.POO.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 2
    @SubscribeEvent
    public static void dropAmulet(LivingDeathEvent event) {
        if (event.getEntity() instanceof Endermite && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(2)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 50 + 4 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.AMULET.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 3
    @SubscribeEvent
    public static void dropCellPhone(LivingDeathEvent event) {
        if ((event.getEntity() instanceof ZombifiedPiglin
                || event.getEntity() instanceof Piglin
                || event.getEntity() instanceof PiglinBrute)
                && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
               if (!myStuff.isMaxLevel(3)) {
                   float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                   double r = Math.random();
                   if (r * 100 < 5 + 1 * luck) {
                       dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.AMULET.get().getDefaultInstance());
                   }
               }
            });
        }
    }

    // 4
    @SubscribeEvent
    public static void dropFourLeafClover(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() instanceof FlowerBlock
                || event.getState().getBlock() instanceof LeavesBlock
                || event.getState().getBlock() == Blocks.GRASS_BLOCK
                || event.getState().getBlock() == Blocks.PODZOL
                || event.getState().getBlock() == Blocks.MYCELIUM
                || event.getState().getBlock() == Blocks.DIRT_PATH
                || event.getState().getBlock() == Blocks.DIRT
                || event.getState().getBlock() == Blocks.COARSE_DIRT
                || event.getState().getBlock() == Blocks.ROOTED_DIRT
                || event.getState().getBlock() == Blocks.FARMLAND
                || event.getState().getBlock() == Blocks.GRASS
                || event.getState().getBlock() == Blocks.TALL_GRASS
                || event.getState().getBlock() == Blocks.FERN
                || event.getState().getBlock() == Blocks.LARGE_FERN
                || event.getState().getBlock() == Blocks.DEAD_BUSH
        ) {
            event.getPlayer().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(4)) {
                    float luck = event.getPlayer().getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 1 + 0.4 * luck) {
                        dropItem(event.getPlayer().level(), event.getPos(), ModItems.FOUR_LEAF_CLOVER.get().getDefaultInstance());
                    }
                }
            });
        }
    }
    @SubscribeEvent
    public static void dropFourLeafClover(LivingDeathEvent event) {
        if (event.getEntity() instanceof Rabbit && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(4)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 10 + 4 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.AMULET.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 5
    @SubscribeEvent
    public static void dropDynamite(LivingDeathEvent event) {
        if (event.getEntity() instanceof Creeper && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(5)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 5 + 1 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.AWKWARD_GUNPOWDER.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 6
    @SubscribeEvent
    public static void dropLightSteps(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.HAY_BLOCK) {
            event.getPlayer().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(6)) {
                    float luck = event.getPlayer().getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 5 + 1 * luck) {
                        dropItem(event.getPlayer().level(), event.getPos(), ModItems.LIGHT_STEPS.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 7
    @SubscribeEvent
    public static void dropWormhole(LivingDeathEvent event) {
        if (event.getEntity() instanceof EnderMan && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(7)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 5 + 1 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.WORMHOLE.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 8
    @SubscribeEvent
    public static void dropSenseOfBalance(LivingHurtEvent event) {
        if (event.getEntity() instanceof Cat || event.getEntity() instanceof Ocelot) {
            double r = Math.random();
            if (r * 100 < 3) {
                dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.SENSE_OF_BALANCE.get().getDefaultInstance());
            }
        }
    }

    // 9
    @SubscribeEvent
    public static void dropMysteriousYellowLiquid(LivingDeathEvent event) {
        if (event.getEntity() instanceof Blaze && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(9)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 2.5 + 0.5 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.MYSTERIOUS_YELLOW_LIQUID.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 10
    @SubscribeEvent
    public static void dropCan(LivingDeathEvent event) {
        if (event.getEntity() instanceof Zombie && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(10)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 10 + 4 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.CAN.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    // 11
    @SubscribeEvent
    public static void dropThankYouForTheMeal(LivingDeathEvent event) {
        if (event.getEntity() instanceof MushroomCow && event.getSource().getEntity() instanceof Player player) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (!myStuff.isMaxLevel(11)) {
                    float luck = player.getPersistentData().getFloat(StatType.LUCK.name);
                    double r = Math.random();
                    if (r * 100 < 5 + 1 * luck) {
                        dropItem(event.getEntity().level(), event.getEntity().getOnPos(), ModItems.THANK_YOU_FOR_THE_MEAL.get().getDefaultInstance());
                    }
                }
            });
        }
    }

    static void dropItem(Level level, BlockPos pos, ItemStack itemStack) {
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack));
    }
}
