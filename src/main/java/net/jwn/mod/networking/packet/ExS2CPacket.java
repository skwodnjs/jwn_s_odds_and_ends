package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.ExS2CPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExS2CPacket {

    public ExS2CPacket() {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public ExS2CPacket(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                ExS2CPacketHandler.handlePacket(supplier);
            });
        });
        context.setPacketHandled(true);
    }
}
