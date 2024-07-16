package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncStuffIFoundS2CPacketHandler;
import net.jwn.mod.stuff.StuffIFound;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncStuffIFoundS2CPacket {
    StuffIFound stuffIFound;

    public SyncStuffIFoundS2CPacket(StuffIFound stuffIFound) {
        this.stuffIFound = stuffIFound;
    }
    public SyncStuffIFoundS2CPacket(Player player) {
        player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
            this.stuffIFound = stuffIFound;
        });
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(stuffIFound.stuffIFound);
    }

    public SyncStuffIFoundS2CPacket(FriendlyByteBuf buf) {
        stuffIFound = new StuffIFound();
        stuffIFound.set(buf.readVarIntArray());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncStuffIFoundS2CPacketHandler.handlePacket(stuffIFound);
            });
        });
        context.setPacketHandled(true);
    }
}
