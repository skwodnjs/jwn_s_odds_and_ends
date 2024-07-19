package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.stuff.CoolTimeProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCoolTimeRequestC2SPacket {

    public SyncCoolTimeRequestC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public SyncCoolTimeRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;
            player.getCapability(CoolTimeProvider.CoolTime).ifPresent(coolTime -> {
                ModMessages.sendToPlayer(new SyncCoolTimeS2CPacket(coolTime.get()), player);
            });
        });
    }
}
