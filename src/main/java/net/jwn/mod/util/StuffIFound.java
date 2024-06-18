package net.jwn.mod.util;

import net.jwn.mod.item.Stuff;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class StuffIFound {
    private static final int MAX_STUFF = 40;
    public static final HashMap<Integer, Stuff> ALL_OF_STUFF = new HashMap<>();

    public static void register(Player player, Stuff stuff) {
        int[] stuffIFounds = player.getPersistentData().getIntArray("stuff_i_found");
        stuffIFounds[stuff.id - 1] = 1;
        player.getPersistentData().putIntArray("stuff_i_found", stuffIFounds);
    }

    public static void init(Player player) {
        player.getPersistentData().putIntArray("stuff_i_found",
                resize(player.getPersistentData().getIntArray("stuff_i_found"), MAX_STUFF));
    }

    private static int[] resize(int[] arr, int size) {
        int[] newArray = new int[size];
        System.arraycopy(arr, 0, newArray, 0, Math.min(arr.length, size));
        return newArray;
    }

    // --------------------- test ---------------------

    public static void reset(Player player) {
        player.getPersistentData().putIntArray("stuff_i_found", new int[MAX_STUFF]);
    }

    public static String print(Player player) {
        int[] stuffIFounds = player.getPersistentData().getIntArray("stuff_i_found");

        StringBuilder p = new StringBuilder("--- STUFF I FOUND ---\n");
        for (int i = 0; i < stuffIFounds.length; i++) {
            p.append("{id %d : %d} ".formatted(i + 1, stuffIFounds[i]));
        }
        return p.toString();
    }
}
