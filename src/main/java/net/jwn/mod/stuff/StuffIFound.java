package net.jwn.mod.stuff;

import net.jwn.mod.util.Functions;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class StuffIFound {
    private static final int MAX_STUFF = 52;
    private int[] stuffIFound = new int[MAX_STUFF];

    public void updateStuffIFound(int id) {
        stuffIFound[id - 1] = 1;
    }
    public void init() {
        stuffIFound = Functions.resize(stuffIFound, MAX_STUFF);
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
        stuffIFound = new int[MAX_STUFF];
    }
    public void length() {
        System.out.println(stuffIFound.length);
    }
    public String print() {
        System.out.println("STUFF I FOUND");
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < MAX_STUFF; i++) {
            p.append("{id: %d, %d} ".formatted((i + 1), stuffIFound[i]));
        }
        return p.toString();
    }
}
