package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncMyStuffS2CPacketHandler;
import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncMyStuffS2CPacket {
    MyStuff myStuff;

    public SyncMyStuffS2CPacket(MyStuff myStuff) {
        this.myStuff = myStuff;
    }
    public SyncMyStuffS2CPacket(Player player) {
        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            this.myStuff = myStuff;
        });
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(myStuff.myActiveStuffIds);
        buf.writeVarIntArray(myStuff.myActiveStuffLevels);
        buf.writeVarIntArray(myStuff.myPassiveStuffIds);
        buf.writeVarIntArray(myStuff.myPassiveStuffLevels);
    }

    public SyncMyStuffS2CPacket(FriendlyByteBuf buf) {
        myStuff = new MyStuff();
        myStuff.set(buf.readVarIntArray(), buf.readVarIntArray(), buf.readVarIntArray(), buf.readVarIntArray());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncMyStuffS2CPacketHandler.handlePacket(myStuff);
            });
        });
        context.setPacketHandled(true);
    }
}
