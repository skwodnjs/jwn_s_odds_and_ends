package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncMyStuffS2CPacketHandler;
import net.jwn.mod.stuff.MyStuff;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMyStuffS2CPacket {
    MyStuff m;

    public SyncMyStuffS2CPacket(MyStuff myStuff) {
        this.m = myStuff;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(m.myActiveStuffIds);
        buf.writeVarIntArray(m.myActiveStuffLevels);
        buf.writeVarIntArray(m.myPassiveStuffIds);
        buf.writeVarIntArray(m.myPassiveStuffLevels);
    }

    public SyncMyStuffS2CPacket(FriendlyByteBuf buf) {
        m = new MyStuff();
        m.set(buf.readVarIntArray(), buf.readVarIntArray(), buf.readVarIntArray(), buf.readVarIntArray());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncMyStuffS2CPacketHandler.handlePacket(m);
            });
        });
        context.setPacketHandled(true);
    }
}
