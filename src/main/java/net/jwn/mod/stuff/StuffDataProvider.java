package net.jwn.mod.stuff;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StuffDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<StuffData> STUFF_DATA = CapabilityManager.get(new CapabilityToken<StuffData>() { });

    private StuffData stuffData = null;
    private final LazyOptional<StuffData> optional = LazyOptional.of(this::createStuffData);

    private StuffData createStuffData() {
        if(this.stuffData == null) {
            this.stuffData = new StuffData();
        }

        return this.stuffData;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == STUFF_DATA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createStuffData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createStuffData().loadNBTData(nbt);
    }
}
