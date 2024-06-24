package net.jwn.mod.gui;

import net.jwn.mod.Main;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MyStuffScreen extends Screen {
    private static final ResourceLocation resourceLocation = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff_gui.png");

    protected MyStuffScreen() {
        super(Component.literal("MY STUFF"));
    }


}
