package net.jwn.mod.stuff;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StuffData {
    // ID: 3
    private int[] cell_phone_pos = {0, Integer.MIN_VALUE, 0};
    private float cell_phone_yaw, cell_phone_pitch;
    public void putCellPhoneData(int[] pos, float yaw, float pitch) {
        cell_phone_pos = pos;
        cell_phone_yaw = yaw;
        cell_phone_pitch = pitch;
    }
    public int[] getCellPhonePos() {
        return cell_phone_pos;
    }
    public float getCellPhoneYaw() {
        return cell_phone_yaw;
    }
    public float getCellPhonePitch() {
        return cell_phone_pitch;
    }
    // ID: 20
    private NonNullList<ItemStack> storageBox = NonNullList.withSize(9, ItemStack.EMPTY);
    public void putStorageBox(int slot, ItemStack itemStack) {
        storageBox.set(slot, itemStack);
    }
    public ItemStack getStorageBox(int slot) {
        return storageBox.get(slot);
    }
    public String print() {
        StringBuilder s = new StringBuilder();
        for (ItemStack itemStack : storageBox) {
            s.append(itemStack.getDescriptionId());
            s.append("\n");
        }
        return s.toString();
    }

    public void copyFrom(StuffData data) {
        this.cell_phone_pos = data.cell_phone_pos;
        this.cell_phone_yaw = data.cell_phone_yaw;
        this.cell_phone_pitch = data.cell_phone_pitch;
        this.storageBox = data.storageBox;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putIntArray("cell_phone_pos", cell_phone_pos);
        nbt.putFloat("cell_phone_yaw", cell_phone_yaw);
        nbt.putFloat("cell_phone_pitch", cell_phone_pitch);
        int index = 0;
        for (ItemStack itemStack : storageBox) {
            nbt.put("storage_box_" + index++, itemStack.serializeNBT());
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        cell_phone_pos = nbt.getIntArray("cell_phone_pos");
        cell_phone_yaw = nbt.getFloat("cell_phone_yaw");
        cell_phone_pitch = nbt.getFloat("cell_phone_pitch");
        for (int index = 0; index < storageBox.size(); index++) {
            storageBox.set(index, ItemStack.of((CompoundTag) nbt.get("storage_box_" + index)));
        }
    }
}
