package net.jwn.mod.util;

import net.jwn.mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

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
    public static boolean dynamite(ServerPlayer player) {
        PrimedTnt primedtnt = new PrimedTnt(player.level(), player.position().x + 0.5D, player.position().y, player.position().z + 0.5D, player);
        player.level().addFreshEntity(primedtnt);
        player.level().playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        player.level().gameEvent(player, GameEvent.PRIME_FUSE, player.position());
        return true;
    }
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
}
