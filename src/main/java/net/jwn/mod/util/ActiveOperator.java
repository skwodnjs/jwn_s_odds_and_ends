package net.jwn.mod.util;

import net.jwn.mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class ActiveOperator {
    public static boolean poop(ServerPlayer player) {
        Level level = player.level();
        level.setBlock(player.getOnPos().offset(0, 1, 0), ModBlocks.POOP_BLOCK.get().defaultBlockState(), 3);
        // 똥 싸는 소리
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                SoundSource.PLAYERS, 1F, 0.2F);
        return true;
    }
    public static boolean cellPhone(ServerPlayer player) {
        if (player.level().dimension() == Level.OVERWORLD) {
            player.sendSystemMessage(Component.literal("OVERWORLD!"));
            BlockPos pos = player.getOnPos().offset(0, 1, 0);
            int[] posArray = {pos.getX(), pos.getY(), pos.getZ()};
            player.getPersistentData().putIntArray("cell_phone", posArray);
            return false;
        } else {
            player.sendSystemMessage(Component.literal("NO OVERWORLD!"));
            int[] pos = player.getPersistentData().getIntArray("cell_phone");
            if (pos.length == 3) {
                ServerLevel overWorld = player.getServer().getLevel(Level.OVERWORLD);
                // 띠리링 전화 소리
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                        SoundSource.PLAYERS, 1F, 0.2F);
                player.teleportTo(overWorld, pos[0], pos[1], pos[2], 0, 0);
            } else {
                player.sendSystemMessage(Component.literal("§c먼저 오버월드에서 장소를 저장해야 합니다!§c"));
            }
            return true;
        }
    }
}
