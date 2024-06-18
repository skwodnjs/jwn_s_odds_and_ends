package net.jwn.mod.networking.active;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PoopSkillC2SPacket {

    public PoopSkillC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public PoopSkillC2SPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null : "poop skill C2S packet, player null";
            Level level = player.level();

            level.setBlock(player.getOnPos().offset(0, 1, 0), Blocks.SAND.defaultBlockState(), 3);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                    SoundSource.PLAYERS, 1F, 0.2F);

        });
        return true;
    }
}
