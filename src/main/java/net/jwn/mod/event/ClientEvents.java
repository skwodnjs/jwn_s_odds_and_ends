package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.gui.MyStuffScreen;
import net.jwn.mod.gui.StuffIFoundScreen;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.*;
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
                // MY STUFF SCREEN : I KEY
                // SYNC MY_STUFF -> OPEN SCREEN
                ModMessages.sendToServer(new SyncMyStuffRequestC2SPacket());
                Minecraft.getInstance().setScreen(new MyStuffScreen());
            } else if (KeyBinding.STUFF_I_FOUND_KEY.consumeClick()) {
                // STUFF I FOUND SCREEN : O KEY
                // SYNC STUFF_I_FOUND -> OPEN SCREEN
                ModMessages.sendToServer(new SyncStuffIFoundRequestC2SPacket());
                Minecraft.getInstance().setScreen(new StuffIFoundScreen());
            } else if (KeyBinding.HINT_KEY.consumeClick()) {
                // HINT SCREEN : H KEY
            } else if (KeyBinding.ACTIVE_SKILL_KEY.consumeClick()) {
                // ACTIVE SKILL : R KEY
                // USE SKILL(COOL TIME IS MODIFIED IN SERVER) -> SYNC COOL_TIME TO CLIENT
                ModMessages.sendToServer(new UseSkillC2SPacket());
                ModMessages.sendToServer(new SyncCoolTimeRequestC2SPacket());
            } else if (KeyBinding.ACTIVE_STUFF_SWITCH_KEY.consumeClick()) {
                // ACTIVE STUFF SWITCH : Z KEY
                // SWITCH (IN SERVER) -> SYNC MY_STUFF
                ModMessages.sendToServer(new MainActiveSwitchC2SPacket());
                ModMessages.sendToServer(new SyncMyStuffRequestC2SPacket());
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.MY_STUFF_KEY);
            event.register(KeyBinding.HINT_KEY);
            event.register(KeyBinding.ACTIVE_SKILL_KEY);
            event.register(KeyBinding.ACTIVE_STUFF_SWITCH_KEY);
        }
    }
}