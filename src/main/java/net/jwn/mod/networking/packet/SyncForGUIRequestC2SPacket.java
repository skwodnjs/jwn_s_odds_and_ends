package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.SyncStuffS2CPacket;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncForGUIRequestC2SPacket {

    public SyncForGUIRequestC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public SyncForGUIRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(m -> {
                player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(s -> {
                    ModMessages.sendToPlayer(new SyncStuffS2CPacket(m, s), player);
                });
            });
        });
        return true;
    }
}
