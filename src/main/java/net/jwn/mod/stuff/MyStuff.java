package net.jwn.mod.stuff;

import net.jwn.mod.item.Stuff;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.Functions;
import net.jwn.mod.util.StuffType;
import net.minecraft.nbt.CompoundTag;

public class MyStuff {
    public int[] myActiveStuffIds = new int[AllOfStuff.MAX_ACTIVE_STUFF];
    public int[] myActiveStuffLevels = new int[AllOfStuff.MAX_ACTIVE_STUFF];
    public int[] myPassiveStuffIds = new int[AllOfStuff.MAX_PASSIVE_STUFF];
    public int[] myPassiveStuffLevels = new int[AllOfStuff.MAX_PASSIVE_STUFF];
    public int mainActiveStuffId;

    /**
     * @return 0 for success, -1 for full, 1 for max level
     */
    public int register(Stuff stuff) {
        if (stuff.type == StuffType.ACTIVE) {
            int index = -1;
            for (int i = 0; i < myActiveStuffIds.length; i++) {
                if (myActiveStuffIds[i] == stuff.id || myActiveStuffIds[i] == 0) {
                    myActiveStuffIds[i] = stuff.id;
                    index = i;
                    break;
                }
            }
            if (index == -1) return -1;
            else {
                if (myActiveStuffLevels[index] < stuff.rank.max_level) myActiveStuffLevels[index] += 1;
                else return 1;
            }
            if (index == 0 && myActiveStuffLevels[0] == 1) {
                mainActiveStuffId = stuff.id;
            }
        } else if (stuff.type == StuffType.PASSIVE) {
            int index = -1;
            for (int i = 0; i < myPassiveStuffIds.length; i++) {
                if (myPassiveStuffIds[i] == stuff.id || myPassiveStuffIds[i] == 0) {
                    myPassiveStuffIds[i] = stuff.id;
                    index = i;
                    break;
                }
            }
            if (index == -1) return -1;
            else {
                if (myPassiveStuffLevels[index] < stuff.rank.max_level) myPassiveStuffLevels[index] += 1;
                else return 1;
            }
        }

        return 0;
    }
    public void init() {
        myActiveStuffIds = Functions.resize(myActiveStuffIds, AllOfStuff.MAX_ACTIVE_STUFF);
        myActiveStuffLevels = Functions.resize(myActiveStuffLevels, AllOfStuff.MAX_ACTIVE_STUFF);
        myPassiveStuffIds = Functions.resize(myPassiveStuffIds, AllOfStuff.MAX_PASSIVE_STUFF);
        myPassiveStuffLevels = Functions.resize(myPassiveStuffLevels, AllOfStuff.MAX_PASSIVE_STUFF);
    }
    public void mainActiveSwitch() {
        int wasMainActiveId = myActiveStuffIds[0];
        int wasMainActiveLevel = myActiveStuffLevels[0];
        Functions.remove(myActiveStuffIds, 0);
        Functions.remove(myActiveStuffLevels, 0);
        for (int i = 0; i < myActiveStuffIds.length; i++) {
            if (myActiveStuffIds[i] == 0) {
                myActiveStuffIds[i] = wasMainActiveId;
                myActiveStuffLevels[i] = wasMainActiveLevel;
                break;
            }
        }
        mainActiveStuffId = myActiveStuffIds[0];
    }
    public int getMainActiveStuffId() {
        return mainActiveStuffId;
    }
    public int getMainActiveStuffLevel() {
        for (int i = 0; i < myActiveStuffIds.length; i++) {
            if (myActiveStuffIds[i] == mainActiveStuffId) {
                return myActiveStuffLevels[i];
            }
        }
        return 0;
    }

    /**
     * @return level of stuff, 0 for has not
     */
    public int hasPassiveStuff(int id) {
        for (int i = 0; i < myPassiveStuffIds.length; i++) {
            if (myPassiveStuffIds[i] == id) {
                return myActiveStuffLevels[i];
            }
        }
        return 0;
    }

    public void remove(int id) {
        if (AllOfStuff.ALL_OF_STUFF.get(id).type == StuffType.ACTIVE) {
            for (int i = 0; i < myActiveStuffIds.length; i++) {
                if (myActiveStuffIds[i] == id) {
                    Functions.remove(myActiveStuffIds, i);
                    Functions.remove(myActiveStuffLevels, i);
                    if (id == mainActiveStuffId) {
                        System.out.println(id);
                        System.out.println(myActiveStuffIds[0]);
                        mainActiveStuffId = myActiveStuffIds[0];
                    }
                }
            }
        } else if (AllOfStuff.ALL_OF_STUFF.get(id).type == StuffType.PASSIVE) {
            for (int i = 0; i < myPassiveStuffIds.length; i++) {
                if (myPassiveStuffIds[i] == id) {
                    Functions.remove(myPassiveStuffIds, i);
                    Functions.remove(myPassiveStuffLevels, i);
                }
            }
        }
    }
    public void set(int[] myActiveStuffIds, int[] myActiveStuffLevels,
                    int[] myPassiveStuffIds, int[] myPassiveStuffLevels,
                    int mainActiveStuffId) {
        this.myActiveStuffIds = myActiveStuffIds;
        this.myActiveStuffLevels = myActiveStuffLevels;
        this.myPassiveStuffIds = myPassiveStuffIds;
        this.myPassiveStuffLevels = myPassiveStuffLevels;
        this.mainActiveStuffId = mainActiveStuffId;
    }
    public void copyFrom(MyStuff myStuff) {
        this.myActiveStuffIds = myStuff.myActiveStuffIds;
        this.myActiveStuffLevels = myStuff.myActiveStuffLevels;
        this.myPassiveStuffIds = myStuff.myPassiveStuffIds;
        this.myPassiveStuffLevels = myStuff.myPassiveStuffLevels;
        this.mainActiveStuffId = myStuff.mainActiveStuffId;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putIntArray("my_active_stuff_ids", myActiveStuffIds);
        nbt.putIntArray("my_active_stuff_levels", myActiveStuffLevels);
        nbt.putIntArray("my_passive_stuff_ids", myPassiveStuffIds);
        nbt.putIntArray("my_passive_stuff_levels", myPassiveStuffLevels);
        nbt.putInt("main_active_stuff_id", mainActiveStuffId);
    }

    public void loadNBTData(CompoundTag nbt) {
        myActiveStuffIds = nbt.getIntArray("my_active_stuff_ids");
        myActiveStuffLevels = nbt.getIntArray("my_active_stuff_levels");
        myPassiveStuffIds = nbt.getIntArray("my_passive_stuff_ids");
        myPassiveStuffLevels = nbt.getIntArray("my_passive_stuff_levels");
        mainActiveStuffId = nbt.getInt("main_active_stuff_id");
    }

    // ---------------------- test ----------------------

    public void reset() {
        myActiveStuffIds = new int[AllOfStuff.MAX_ACTIVE_STUFF];
        myActiveStuffLevels = new int[AllOfStuff.MAX_ACTIVE_STUFF];
        myPassiveStuffIds = new int[AllOfStuff.MAX_PASSIVE_STUFF];
        myPassiveStuffLevels = new int[AllOfStuff.MAX_PASSIVE_STUFF];
        mainActiveStuffId = 0;
    }
    public String print() {
        StringBuilder p = new StringBuilder();
        p.append("MY STUFF ACTIVE:\n");
        for (int i = 0; i < myActiveStuffIds.length; i++) {
            p.append("{id: %d, %d} ".formatted(myActiveStuffIds[i], myActiveStuffLevels[i]));
        }
        p.append("\nMY STUFF PASSIVE:\n");
        for (int i = 0; i < myPassiveStuffIds.length; i++) {
            p.append("{id: %d, %d} ".formatted(myPassiveStuffIds[i], myPassiveStuffLevels[i]));
        }
        p.append("\nMAIN ACTIVE STUFF: %d".formatted(mainActiveStuffId));
        return p.toString();
    }
}
