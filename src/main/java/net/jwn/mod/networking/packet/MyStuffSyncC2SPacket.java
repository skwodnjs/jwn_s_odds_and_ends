package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.MyStuffSyncS2CPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MyStuffSyncC2SPacket {
    int[] myActiveStuffIds;
    int[] myActiveStuffLevels;
    int[] myPassiveStuffIds;
    int[] myPassiveStuffLevels;
    int mainActiveStuffId;

    public MyStuffSyncC2SPacket(int[] myActiveStuffIds, int[] myActiveStuffLevels,
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

    public MyStuffSyncC2SPacket(FriendlyByteBuf buf) {
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
                Player player = context.getSender();
                assert player != null : "my stuff sync C2S packet, player null";
                player.getPersistentData().putIntArray("my_active_stuff_ids", myActiveStuffIds);
                player.getPersistentData().putIntArray("my_active_stuff_levels", myActiveStuffLevels);
                player.getPersistentData().putIntArray("my_passive_stuff_ids", myPassiveStuffIds);
                player.getPersistentData().putIntArray("my_passive_stuff_levels", myPassiveStuffLevels);
                player.getPersistentData().putInt("main_active_stuff_id", mainActiveStuffId);
            });
        });
        context.setPacketHandled(true);
    }
}
