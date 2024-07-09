package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncStuffIFoundS2CPacketHandler;
import net.jwn.mod.stuff.StuffIFound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncStuffIFoundS2CPacket {
    StuffIFound s;

    public SyncStuffIFoundS2CPacket(StuffIFound stuffIFound) {
        this.s = stuffIFound;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(s.stuffIFound);
    }

    public SyncStuffIFoundS2CPacket(FriendlyByteBuf buf) {
        s = new StuffIFound();
        s.set(buf.readVarIntArray());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncStuffIFoundS2CPacketHandler.handlePacket(s);
            });
        });
        context.setPacketHandled(true);
    }
}
