package net.jwn.mod.networking.packet;

import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MainActiveSwitchC2SPacket {

    public MainActiveSwitchC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public MainActiveSwitchC2SPacket(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(MyStuff::mainActiveSwitch);
        });
    }
}
