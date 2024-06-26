package net.jwn.mod.networking.packet;

import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveStuffC2SPacket {
    int id;

    public RemoveStuffC2SPacket(int id) {
        this.id = id;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
    }

    public RemoveStuffC2SPacket(FriendlyByteBuf buf) {
        id = buf.readInt();
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;

            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                myStuff.remove(id);
            });
        });
        return true;
    }
}
