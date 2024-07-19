package net.jwn.mod.stuff;

import net.minecraft.nbt.CompoundTag;

public class CoolTime {
    private int cool_time;
    public void set(int i) {
        cool_time = i;
    }
    public int get() {
        return cool_time;
    }
    public void sub() {
        if (cool_time > 0) cool_time -= 1;
    }
    public void reset() {
        cool_time = 0;
    }
    public void copyFrom(CoolTime coolTime) {
        this.cool_time = coolTime.cool_time;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("cool_time", cool_time);
    }
    public void loadNBTData(CompoundTag nbt) {
        cool_time = nbt.getInt("cool_time");
    }
}
