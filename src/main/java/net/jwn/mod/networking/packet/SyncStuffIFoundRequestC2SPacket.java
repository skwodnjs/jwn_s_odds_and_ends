package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncStuffIFoundRequestC2SPacket {

    public SyncStuffIFoundRequestC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public SyncStuffIFoundRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;
            player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(s -> {
                ModMessages.sendToPlayer(new SyncStuffIFoundS2CPacket(s), player);
            });
        });
    }
}
