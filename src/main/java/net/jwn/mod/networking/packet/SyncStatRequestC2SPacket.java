package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.util.StatOperator;
import net.jwn.mod.util.StatType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SyncStatRequestC2SPacket {

    public SyncStatRequestC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public SyncStatRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;

            Map<String, Float> map = new HashMap<>();

            for (StatType type : StatType.values()) {
                map.put(type.name, player.getPersistentData().getFloat(type.name));
            }

            ModMessages.sendToPlayer(new SyncStatS2CPacket(map), player);
        });
    }
}
