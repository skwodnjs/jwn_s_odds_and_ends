package net.jwn.mod.networking.packet;

import net.jwn.mod.networking.packet.handler.SyncStatS2CPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SyncStatS2CPacket {
    Map<String, Float> map = new HashMap<>();

    public SyncStatS2CPacket(Map<String, Float> map) {
        this.map = map;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(map.size());
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeFloat(entry.getValue());
        }
    }

    public SyncStatS2CPacket(FriendlyByteBuf buf) {
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            String key = buf.readUtf();
            float value = buf.readFloat();
            map.put(key, value);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // HERE WE ARE ON THE CLIENT!
                SyncStatS2CPacketHandler.handlePacket(map);
            });
        });
        context.setPacketHandled(true);
    }
}
