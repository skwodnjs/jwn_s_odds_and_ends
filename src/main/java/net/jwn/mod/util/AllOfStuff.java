package net.jwn.mod.util;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllOfStuff {
    public static final int MAX_STUFF = 99; // id: 1 ~ MAX_STUFF, index < MAX_STUFF
    public static final int MAX_ACTIVE_STUFF = 3;
    public static final int MAX_PASSIVE_STUFF = 16;
    public static final Map<Integer, Stuff> ALL_OF_STUFF = new HashMap<>();
    public static ResourceLocation getResources(int id) {
        return new ResourceLocation(Main.MOD_ID, "textures/item/" + ALL_OF_STUFF.get(id) + ".png");
    }
    public static Stuff getRandom(StuffRank rank) {
        List<Stuff> list = new ArrayList<>();
        for (int i = 0; i < MAX_STUFF; i++) {
            Stuff stuff = ALL_OF_STUFF.get(i+1);
            if (stuff == null) break;
            if (stuff.rank == rank) list.add(stuff);
        }
        int index = (int) (Math.random() * list.toArray().length);
        return list.get(index);
    }
    public static Stuff getRandom(StuffRank rank, StuffType type) {
        List<Stuff> list = new ArrayList<>();
        for (int i = 0; i < MAX_STUFF; i++) {
            Stuff stuff = ALL_OF_STUFF.get(i+1);
            if (stuff == null) break;
            if (stuff.rank == rank && stuff.type == type) list.add(stuff);
        }
        int index = (int) (Math.random() * list.toArray().length);
        return list.get(index);
    }
    public static Stuff getRandomDowngrade(StuffRank rank) {
        List<Stuff> list = new ArrayList<>();
        for (int i = 0; i < MAX_STUFF; i++) {
            System.out.println(ALL_OF_STUFF.get(i+1));
            Stuff stuff = ALL_OF_STUFF.get(i+1);
            if (stuff == null) break;
            if (stuff.rank.ordinal() <= rank.ordinal()) list.add(stuff);
        }
        int index = (int) (Math.random() * list.toArray().length);
        return list.get(index);
    }
    public static Stuff getRandomDowngrade(StuffRank rank, StuffType type) {
        List<Stuff> list = new ArrayList<>();
        for (int i = 0; i < MAX_STUFF; i++) {
            Stuff stuff = ALL_OF_STUFF.get(i+1);
            if (stuff == null) break;
            if (stuff.rank.ordinal() <= rank.ordinal() && stuff.type == type) list.add(stuff);
        }
        int index = (int) (Math.random() * list.toArray().length);
        return list.get(index);
    }
    public static int getStuffCount(StuffRank rank) {
        int count = 0;
        for (int i = 0; i < MAX_STUFF; i++) {
            Stuff stuff = ALL_OF_STUFF.get(i+1);
            if (stuff == null) break;
            if (stuff.rank == rank) count += 1;
        }
        return count;
    }
    public static int getStuffCount(StuffRank rank, StuffType type) {
        int count = 0;
        for (int id = 1; id <= MAX_STUFF; id++) {
            Stuff stuff = ALL_OF_STUFF.get(id);
            if (stuff == null) break;
            if (stuff.rank == rank && stuff.type == type) count += 1;
        }
        return count;
    }
}
