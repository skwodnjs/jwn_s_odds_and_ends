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

public class StuffIFoundProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<StuffIFound> STUFF_I_FOUND = CapabilityManager.get(new CapabilityToken<StuffIFound>() { });

    private StuffIFound stuffIFound = null;
    private final LazyOptional<StuffIFound> optional = LazyOptional.of(this::createStuffIFound);

    private StuffIFound createStuffIFound() {
        if(this.stuffIFound == null) {
            this.stuffIFound = new StuffIFound();
        }

        return this.stuffIFound;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == STUFF_I_FOUND) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createStuffIFound().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createStuffIFound().loadNBTData(nbt);
    }
}
