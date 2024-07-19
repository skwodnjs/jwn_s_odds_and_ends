package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncCoolTimeS2CPacketHandler;
import net.jwn.mod.stuff.CoolTimeProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCoolTimeS2CPacket {
    int cool_time;

    public SyncCoolTimeS2CPacket(int coolTime) {
        this.cool_time = coolTime;
    }
    public SyncCoolTimeS2CPacket(Player player) {
        player.getCapability(CoolTimeProvider.CoolTime).ifPresent(coolTime -> {
            cool_time = coolTime.get();
        });
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(cool_time);
    }

    public SyncCoolTimeS2CPacket(FriendlyByteBuf buf) {
        cool_time = buf.readInt();
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncCoolTimeS2CPacketHandler.handlePacket(cool_time);
            });
        });
        context.setPacketHandled(true);
    }
}
