package net.jwn.mod.stuff;

import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.Functions;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

public class StuffIFound {
    
    public int[] stuffIFound = new int[AllOfStuff.MAX_STUFF];
    public void updateStuffIFound(int id, int max) {
        // PICK UP / CONTAINER: 1
        // USE: 2
        // 3
        stuffIFound[id - 1] = Math.max(stuffIFound[id - 1], max);
    }
    public void init() {
        stuffIFound = Functions.resize(stuffIFound, AllOfStuff.MAX_STUFF);
    }
    public void copyFrom(StuffIFound stuffIFound) {
        this.stuffIFound = stuffIFound.stuffIFound;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putIntArray("stuff_i_found", stuffIFound);
    }

    public void loadNBTData(CompoundTag nbt) {
        stuffIFound = nbt.getIntArray("stuff_i_found");
    }

    // ---------------------- test ----------------------
    public void reset() {
        stuffIFound = new int[AllOfStuff.MAX_STUFF];
    }
    public String print() {
        System.out.println("STUFF I FOUND");
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < AllOfStuff.MAX_STUFF; i++) {
            p.append("{id: %d, %d} ".formatted((i + 1), stuffIFound[i]));
        }
        return p.toString();
    }

    public void set(int[] stuffIFound) {
        this.stuffIFound = stuffIFound;
    }
}
