package net.jwn.mod.networking;

import net.jwn.mod.Main;
import net.jwn.mod.networking.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }
    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Main.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(UseSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseSkillC2SPacket::new)
                .encoder(UseSkillC2SPacket::toBytes)
                .consumerMainThread(UseSkillC2SPacket::handle)
                .add();
        net.messageBuilder(MainActiveSwitchC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MainActiveSwitchC2SPacket::new)
                .encoder(MainActiveSwitchC2SPacket::toBytes)
                .consumerMainThread(MainActiveSwitchC2SPacket::handle)
                .add();
        net.messageBuilder(RemoveStuffC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RemoveStuffC2SPacket::new)
                .encoder(RemoveStuffC2SPacket::toBytes)
                .consumerMainThread(RemoveStuffC2SPacket::handle)
                .add();
        // ------------------------------- SYNC -------------------------------
        net.messageBuilder(SyncMyStuffS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncMyStuffS2CPacket::new)
                .encoder(SyncMyStuffS2CPacket::toBytes)
                .consumerMainThread(SyncMyStuffS2CPacket::handle)
                .add();
        net.messageBuilder(SyncMyStuffRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncMyStuffRequestC2SPacket::new)
                .encoder(SyncMyStuffRequestC2SPacket::toBytes)
                .consumerMainThread(SyncMyStuffRequestC2SPacket::handle)
                .add();
        net.messageBuilder(SyncStuffIFoundS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncStuffIFoundS2CPacket::new)
                .encoder(SyncStuffIFoundS2CPacket::toBytes)
                .consumerMainThread(SyncStuffIFoundS2CPacket::handle)
                .add();
        net.messageBuilder(SyncStuffIFoundRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncStuffIFoundRequestC2SPacket::new)
                .encoder(SyncStuffIFoundRequestC2SPacket::toBytes)
                .consumerMainThread(SyncStuffIFoundRequestC2SPacket::handle)
                .add();
        net.messageBuilder(SyncCoolTimeS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncCoolTimeS2CPacket::new)
                .encoder(SyncCoolTimeS2CPacket::toBytes)
                .consumerMainThread(SyncCoolTimeS2CPacket::handle)
                .add();
        net.messageBuilder(SyncCoolTimeRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncCoolTimeRequestC2SPacket::new)
                .encoder(SyncCoolTimeRequestC2SPacket::toBytes)
                .consumerMainThread(SyncCoolTimeRequestC2SPacket::handle)
                .add();
        net.messageBuilder(SyncStatS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncStatS2CPacket::new)
                .encoder(SyncStatS2CPacket::toBytes)
                .consumerMainThread(SyncStatS2CPacket::handle)
                .add();
        net.messageBuilder(SyncStatRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncStatRequestC2SPacket::new)
                .encoder(SyncStatRequestC2SPacket::toBytes)
                .consumerMainThread(SyncStatRequestC2SPacket::handle)
                .add();
    }
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);

    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
