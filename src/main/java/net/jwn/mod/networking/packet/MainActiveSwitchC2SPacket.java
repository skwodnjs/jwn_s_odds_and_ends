package net.jwn.mod.networking.packet;

import net.jwn.mod.stuff.MyStuffProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null : "main active switch C2S packet, player null";
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                myStuff.mainActiveSwitch();
                player.sendSystemMessage(Component.literal(String.valueOf(myStuff.getMainActiveStuffId())));
            });
            // test

        });
        return true;
    }
}
