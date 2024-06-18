package net.jwn.mod.networking;

import net.jwn.mod.Main;
import net.jwn.mod.networking.active.PoopSkillC2SPacket;
import net.jwn.mod.networking.packet.ExS2CPacket;
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

        net.messageBuilder(ExS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExS2CPacket::new)
                .encoder(ExS2CPacket::toBytes)
                .consumerMainThread(ExS2CPacket::handle)
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
