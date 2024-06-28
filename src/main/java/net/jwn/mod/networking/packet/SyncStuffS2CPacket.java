package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncStuffS2CPacketHandler;
import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class SyncStuffS2CPacket {
    MyStuff m;
    StuffIFound s;

    public SyncStuffS2CPacket(MyStuff myStuff, StuffIFound stuffIFound) {
        this.m = myStuff;
        this.s = stuffIFound;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(m.myActiveStuffIds);
        buf.writeVarIntArray(m.myActiveStuffLevels);
        buf.writeVarIntArray(m.myPassiveStuffIds);
        buf.writeVarIntArray(m.myPassiveStuffLevels);
        buf.writeVarIntArray(s.stuffIFound);
    }

    public SyncStuffS2CPacket(FriendlyByteBuf buf) {
        m = new MyStuff();
        s = new StuffIFound();
        m.set(buf.readVarIntArray(), buf.readVarIntArray(), buf.readVarIntArray(), buf.readVarIntArray());
        s.set(buf.readVarIntArray());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncStuffS2CPacketHandler.handlePacket(m, s);
            });
        });
        context.setPacketHandled(true);
    }
}
