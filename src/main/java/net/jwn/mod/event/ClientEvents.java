package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.gui.MyStuffScreen;
import net.jwn.mod.gui.StuffIFoundScreen;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.MainActiveSwitchC2SPacket;
import net.jwn.mod.networking.packet.SyncCoolTimeRequestC2SPacket;
import net.jwn.mod.networking.packet.SyncStatRequestC2SPacket;
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
            assert player != null;
            if (KeyBinding.MY_STUFF_KEY.consumeClick()) {
                ModMessages.sendToServer(new SyncStatRequestC2SPacket());
                Minecraft.getInstance().setScreen(new MyStuffScreen());
            } else if (KeyBinding.STUFF_I_FOUND_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new StuffIFoundScreen());
            } else if (KeyBinding.HINT_KEY.consumeClick()) {

            } else if (KeyBinding.TEST_3_KEY.consumeClick()) {

            } else if (KeyBinding.TEST_4_KEY.consumeClick()) {

            } else if (KeyBinding.ACTIVE_SKILL_KEY.consumeClick()) {
                ModMessages.sendToServer(new UseSkillC2SPacket());
                ModMessages.sendToServer(new SyncCoolTimeRequestC2SPacket());
            } else if (KeyBinding.ACTIVE_STUFF_SWITCH_KEY.consumeClick()) {
                ModMessages.sendToServer(new MainActiveSwitchC2SPacket());
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MY_STUFF_KEY);
            event.register(KeyBinding.HINT_KEY);
            event.register(KeyBinding.TEST_3_KEY);
            event.register(KeyBinding.TEST_4_KEY);
            event.register(KeyBinding.ACTIVE_SKILL_KEY);
            event.register(KeyBinding.ACTIVE_STUFF_SWITCH_KEY);
        }
    }
}