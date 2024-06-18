package net.jwn.mod.util;

import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.MyStuffSyncC2SPacket;
import net.jwn.mod.networking.packet.MyStuffSyncS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class MyStuff {
    private static final int MAX_ACTIVE_STUFF = 4;
    private static final int MAX_PASSIVE_STUFF = 16;

    /**
     * player에게 stuff를 register한다. 이미 최대 강화이거나, 이미 아이템을 최대로 보유한 경우 register에 실패한다.
     * register은 player가 사용한 stuff를 player.getPersistentData()에 저장하는 과정이다.
     * @return register 성공 여부
     */
    public static boolean register(Player player, Stuff stuff) {
        // ------------------- test -------------------
        System.out.println("ITEM INFO");
        System.out.println("ID:\t\t" + stuff.id);
        System.out.println("TYPE:\t" + stuff.type);
        System.out.println("RANK:\t" + stuff.rank);
        // --------------------------------------------
        boolean success = true;
        if (stuff.type == StuffType.ACTIVE) {
            int[] myActiveStuffIds = player.getPersistentData().getIntArray("my_active_stuff_ids");
            int[] myActiveStuffLevels = player.getPersistentData().getIntArray("my_active_stuff_levels");

            int index = -1;
            for (int i = 0; i < myActiveStuffIds.length; i++) {
                if (myActiveStuffIds[i] == stuff.id || myActiveStuffIds[i] == 0) {
                    myActiveStuffIds[i] = stuff.id;
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                player.sendSystemMessage(Component.literal("§c아이템을 더 이상 장착할 수 없습니다.§c"));
                success = false;
            } else {
                // test
                System.out.println("CURRENT LEVEL:\t" + myActiveStuffLevels[index]);
                System.out.println("MAX LEVEL:\t" + stuff.rank.max_level);

                if (myActiveStuffLevels[index] < stuff.rank.max_level) {
                    myActiveStuffLevels[index] += 1;
                }
                else {
                    player.sendSystemMessage(Component.literal("§c아이템을 더 이상 강화할 수 없습니다.§c"));
                    success = false;
                }
            }

            if (success) {
                player.getPersistentData().putIntArray("my_active_stuff_ids", myActiveStuffIds);
                player.getPersistentData().putIntArray("my_active_stuff_levels", myActiveStuffLevels);
                if (index == 0 && myActiveStuffLevels[0] == 1) {
                    player.getPersistentData().putInt("main_active_stuff_id", stuff.id);
                }
            }
        } else if (stuff.type == StuffType.PASSIVE) {
            int[] myPassiveStuffIds = player.getPersistentData().getIntArray("my_passive_stuff_ids");
            int[] myPassiveStuffLevels = player.getPersistentData().getIntArray("my_passive_stuff_levels");

            int index = -1;
            for (int i = 0; i < myPassiveStuffIds.length; i++) {
                if (myPassiveStuffIds[i] == stuff.id || myPassiveStuffIds[i] == 0) {
                    myPassiveStuffIds[i] = stuff.id;
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                player.sendSystemMessage(Component.literal("§c아이템을 더 이상 장착할 수 없습니다.§c"));
                success = false;
            } else {
                if (myPassiveStuffLevels[index] < stuff.rank.max_level) myPassiveStuffLevels[index] += 1;
                else {
                    player.sendSystemMessage(Component.literal("§c아이템을 더 이상 강화할 수 없습니다.§c"));
                    success = false;
                }
            }

            if (success) {
                player.getPersistentData().putIntArray("my_passive_stuff_ids", myPassiveStuffIds);
                player.getPersistentData().putIntArray("my_passive_stuff_levels", myPassiveStuffLevels);
            }
        }
        return success;
    }
    public static void init(Player player) {
        // ------------------- test -------------------
        System.out.println("MY STUFF INIT");
        System.out.println(player.level().isClientSide ? "CLIENT" : "SERVER");
        // --------------------------------------------
        player.getPersistentData().putIntArray("my_active_stuff_ids",
                resize(player.getPersistentData().getIntArray("my_active_stuff_ids"), MAX_ACTIVE_STUFF));
        player.getPersistentData().putIntArray("my_active_stuff_levels",
                resize(player.getPersistentData().getIntArray("my_active_stuff_levels"), MAX_ACTIVE_STUFF));
        player.getPersistentData().putIntArray("my_passive_stuff_ids",
                resize(player.getPersistentData().getIntArray("my_passive_stuff_ids"), MAX_PASSIVE_STUFF));
        player.getPersistentData().putIntArray("my_passive_stuff_levels",
                resize(player.getPersistentData().getIntArray("my_passive_stuff_levels"), MAX_PASSIVE_STUFF));
    }

    private static int[] resize(int[] arr, int size) {
        int[] newArray = new int[size];
        System.arraycopy(arr, 0, newArray, 0, Math.min(arr.length, size));
        return newArray;
    }

    public static void switchMainActiveStuff(Player player) {
        int[] myActiveStuffIds = player.getPersistentData().getIntArray("my_active_stuff_ids");
        int[] myActiveStuffLevels = player.getPersistentData().getIntArray("my_active_stuff_levels");
        push(myActiveStuffIds);
        push(myActiveStuffLevels);

        player.getPersistentData().putIntArray("my_active_stuff_ids", myActiveStuffIds);
        player.getPersistentData().putIntArray("my_active_stuff_levels", myActiveStuffLevels);
        player.getPersistentData().putInt("main_active_stuff_id", myActiveStuffIds[0]);

        ModMessages.sendToServer(new MyStuffSyncC2SPacket(
                player.getPersistentData().getIntArray("my_active_stuff_ids"),
                player.getPersistentData().getIntArray("my_active_stuff_levels"),
                player.getPersistentData().getIntArray("my_passive_stuff_ids"),
                player.getPersistentData().getIntArray("my_passive_stuff_levels"),
                player.getPersistentData().getInt("main_active_stuff_id")
        ));
    }

    private static void push(int[] arr) {
        int firstZero = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                firstZero = i;
                break;
            }
        }
        arr[firstZero] = arr[0];
        remove(arr, 0);
    }

    private static void remove(int[] arr, int index) {
        arr[index] = 0;
        for (int i = index + 1; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[i - 1] = arr[i];
                arr[i] = 0;
            }
        }
    }

    // --------------------- test ---------------------

    public static void reset(Player player) {
        player.getPersistentData().putIntArray("my_active_stuff_ids", new int[MAX_ACTIVE_STUFF]);
        player.getPersistentData().putIntArray("my_active_stuff_levels", new int[MAX_ACTIVE_STUFF]);
        player.getPersistentData().putIntArray("my_passive_stuff_ids", new int[MAX_PASSIVE_STUFF]);
        player.getPersistentData().putIntArray("my_passive_stuff_levels", new int[MAX_PASSIVE_STUFF]);
        player.getPersistentData().putInt("main_active_stuff_id", 0);
    }

    public static String print(Player player) {
        int[] myActiveStuffIds = player.getPersistentData().getIntArray("my_active_stuff_ids");
        int[] myActiveStuffLevels = player.getPersistentData().getIntArray("my_active_stuff_levels");
        int[] myPassiveStuffIds = player.getPersistentData().getIntArray("my_passive_stuff_ids");
        int[] myPassiveStuffLevels = player.getPersistentData().getIntArray("my_passive_stuff_levels");

        StringBuilder p = new StringBuilder("MAIN ACTIVE STUFF: ");
        p.append(player.getPersistentData().getInt("main_active_stuff_id"));
        p.append("\nACTIVE: ");
        for (int i = 0; i < myActiveStuffIds.length; i++) {
            p.append("{id: %d / lv: %d} ".formatted(myActiveStuffIds[i], myActiveStuffLevels[i]));
        }
        p.append("\nPASSIVE: ");
        for (int i = 0; i < myPassiveStuffIds.length; i++) {
            p.append("{id: %d / lv: %d} ".formatted(myPassiveStuffIds[i], myPassiveStuffLevels[i]));
        }

        return p.toString();
    }
}