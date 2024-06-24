package net.jwn.mod.util;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AllOfStuff {
    public static final Map<Integer, Stuff> ALL_OF_STUFF = new HashMap<>();

    public static final ResourceLocation poop = new ResourceLocation(Main.MOD_ID, "textures/item/poop.png");
    public static final ResourceLocation cell_phone = new ResourceLocation(Main.MOD_ID, "textures/item/cell_phone.png");
    public static final ResourceLocation amulet = new ResourceLocation(Main.MOD_ID, "textures/item/amulet.png");
    public static final ResourceLocation four_leaf_clover = new ResourceLocation(Main.MOD_ID, "textures/item/four_leaf_clover.png");

}
