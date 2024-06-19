package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.MainActiveSwitchC2SPacket;
import net.jwn.mod.networking.packet.UseSkillC2SPacket;
import net.jwn.mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            assert player != null: "Key input, (client) player is null";
            if (KeyBinding.TEST_1_KEY.consumeClick()) {

            } else if (KeyBinding.TEST_2_KEY.consumeClick()) {

            } else if (KeyBinding.TEST_3_KEY.consumeClick()) {

            } else if (KeyBinding.TEST_4_KEY.consumeClick()) {

            } else if (KeyBinding.ACTIVE_SKILL_KEY.consumeClick()) {
                ModMessages.sendToServer(new UseSkillC2SPacket());
            } else if (KeyBinding.ACTIVE_STUFF_SWITCH_KEY.consumeClick()) {
                ModMessages.sendToServer(new MainActiveSwitchC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.TEST_1_KEY);
            event.register(KeyBinding.TEST_2_KEY);
            event.register(KeyBinding.TEST_3_KEY);
            event.register(KeyBinding.TEST_4_KEY);
            event.register(KeyBinding.ACTIVE_SKILL_KEY);
            event.register(KeyBinding.ACTIVE_STUFF_SWITCH_KEY);
        }
    }
}