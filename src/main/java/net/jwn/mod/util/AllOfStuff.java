package net.jwn.mod.util;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.stream.IntStream;

public class AllOfStuff {
    public static final int MAX_STUFF = 99; // id: 1 ~ MAX_STUFF, index < MAX_STUFF
    public static final int MAX_ACTIVE_STUFF = 3;
    public static final int MAX_PASSIVE_STUFF = 16;
    public static final Map<Integer, Stuff> ALL_OF_STUFF = new HashMap<>();
    public static ResourceLocation getResources(int id) {
        return new ResourceLocation(Main.MOD_ID, "textures/item/" + ALL_OF_STUFF.get(id) + ".png");
    }
    public static Stuff getRandom(StuffRank rank, StuffType type, int[] ids) {
        // id : 제외할 id
        List<Stuff> list = new ArrayList<>();
        for (int id = 1; id <= MAX_STUFF; id++) {
            Stuff stuff = ALL_OF_STUFF.get(id);
            if (stuff == null) continue;
            if (stuff.rank != rank) continue;
            if (stuff.type != type) continue;
            if (stuff.type == StuffType.DISPOSABLE) continue;
            int ID = id;
            if (Arrays.stream(ids).anyMatch(num -> num == ID)) continue;
            list.add(stuff);
        }
        int index = (int) (Math.random() * list.toArray().length);
        return list.get(index);
    }
    public static Stuff getRandom(StuffRank rank, StuffType type, int id) {
        int[] ids = {id};
        return getRandom(rank, type, ids);
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
