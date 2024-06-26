package net.jwn.mod.util;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AllOfStuff {
    public static final int MAX_STUFF = 70; // id: 1 ~ MAX_STUFF, index < MAX_STUFF
    public static final int MAX_ACTIVE_STUFF = 3;
    public static final int MAX_PASSIVE_STUFF = 16;
    public static final Map<Integer, Stuff> ALL_OF_STUFF = new HashMap<>();
    public static ResourceLocation getResources(int id) {
        return new ResourceLocation(Main.MOD_ID, "textures/item/" + ALL_OF_STUFF.get(id) + ".png");
    }

}
