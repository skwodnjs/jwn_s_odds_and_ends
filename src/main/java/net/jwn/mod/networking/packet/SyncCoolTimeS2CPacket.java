package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncCoolTimeS2CPacketHandler;
import net.jwn.mod.networking.packet.handler.SyncStuffS2CPacketHandler;
import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.StuffIFound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCoolTimeS2CPacket {
    int coolTime;

    public SyncCoolTimeS2CPacket(int coolTime) {
        this.coolTime = coolTime;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(coolTime);
    }

    public SyncCoolTimeS2CPacket(FriendlyByteBuf buf) {
        coolTime = buf.readInt();
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncCoolTimeS2CPacketHandler.handlePacket(coolTime);
            });
        });
        context.setPacketHandled(true);
    }
}
