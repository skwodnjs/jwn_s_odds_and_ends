package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.MyStuffSyncS2CPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MyStuffSyncS2CPacket {
    int[] myActiveStuffIds;
    int[] myActiveStuffLevels;
    int[] myPassiveStuffIds;
    int[] myPassiveStuffLevels;
    int mainActiveStuffId;

    public MyStuffSyncS2CPacket(int[] myActiveStuffIds, int[] myActiveStuffLevels,
                                int[] myPassiveStuffIds, int[] myPassiveStuffLevels, int mainActiveStuffId) {
        this.myActiveStuffIds = myActiveStuffIds;
        this.myActiveStuffLevels = myActiveStuffLevels;
        this.myPassiveStuffIds = myPassiveStuffIds;
        this.myPassiveStuffLevels = myPassiveStuffLevels;
        this.mainActiveStuffId = mainActiveStuffId;

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(myActiveStuffIds);
        buf.writeVarIntArray(myActiveStuffLevels);
        buf.writeVarIntArray(myPassiveStuffIds);
        buf.writeVarIntArray(myPassiveStuffLevels);
        buf.writeInt(mainActiveStuffId);
    }

    public MyStuffSyncS2CPacket(FriendlyByteBuf buf) {
        this.myActiveStuffIds = buf.readVarIntArray();
        this.myActiveStuffLevels = buf.readVarIntArray();
        this.myPassiveStuffIds = buf.readVarIntArray();
        this.myPassiveStuffLevels = buf.readVarIntArray();
        this.mainActiveStuffId = buf.readInt();
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                MyStuffSyncS2CPacketHandler.handlePacket(supplier,  myActiveStuffIds,  myActiveStuffLevels,
                 myPassiveStuffIds,  myPassiveStuffLevels, mainActiveStuffId);
            });
        });
        context.setPacketHandled(true);
    }
}
