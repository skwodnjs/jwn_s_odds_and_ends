package net.jwn.mod.util;

import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;

public class PassiveOperator {
    /**
     * ModEvents#onLivingHurtEvent
     * @return hurt event canceled or not
     */
    public static void amulet(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof EnderMan) {
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                int level = myStuff.hasPassiveStuff(2);
                if (level != 0) {
                    double r = Math.random();
                    if (r < 0.4 + 0.1 * level) {
                        // 막는 소리
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.PLAYERS, 1, 1);
                        event.setCanceled(true);
                    }
                }
            });
        }
    }

    public static void fourLeafClover(BlockEvent.BreakEvent event) {
        event.getPlayer().getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            int level = myStuff.hasPassiveStuff(33);
            if (level != 0) {
                double r = Math.random();
                if (r < 0.3 + 0.2 * level) {
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
}
