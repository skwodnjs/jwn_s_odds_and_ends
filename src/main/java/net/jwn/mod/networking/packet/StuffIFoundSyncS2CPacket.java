package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.MyStuffSyncS2CPacketHandler;
import net.jwn.mod.networking.packet.handler.StuffIFoundSyncS2CPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StuffIFoundSyncS2CPacket {
    int[] stuffIFound;

    public StuffIFoundSyncS2CPacket(int[] stuffIFound) {
        this.stuffIFound = stuffIFound;

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(stuffIFound);
    }

    public StuffIFoundSyncS2CPacket(FriendlyByteBuf buf) {
        this.stuffIFound = buf.readVarIntArray();
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                StuffIFoundSyncS2CPacketHandler.handlePacket(supplier, stuffIFound);
            });
        });
        context.setPacketHandled(true);
    }
}
