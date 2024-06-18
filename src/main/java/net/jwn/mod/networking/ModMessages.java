package net.jwn.mod.networking;

import net.jwn.mod.Main;
import net.jwn.mod.networking.packet.MyStuffSyncS2CPacket;
import net.jwn.mod.networking.packet.StuffIFoundSyncS2CPacket;
import net.jwn.mod.networking.skill.PoopSkillC2SPacket;
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

        net.messageBuilder(MyStuffSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MyStuffSyncS2CPacket::new)
                .encoder(MyStuffSyncS2CPacket::toBytes)
                .consumerMainThread(MyStuffSyncS2CPacket::handle)
                .add();
        net.messageBuilder(StuffIFoundSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StuffIFoundSyncS2CPacket::new)
                .encoder(StuffIFoundSyncS2CPacket::toBytes)
                .consumerMainThread(StuffIFoundSyncS2CPacket::handle)
                .add();

        // ------------------- skills -------------------
        net.messageBuilder(PoopSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PoopSkillC2SPacket::new)
                .encoder(PoopSkillC2SPacket::toBytes)
                .consumerMainThread(PoopSkillC2SPacket::handle)
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
