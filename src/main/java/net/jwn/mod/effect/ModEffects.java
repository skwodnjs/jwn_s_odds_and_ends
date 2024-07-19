package net.jwn.mod.effect;

import net.jwn.mod.Main;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MOD_ID);

    public static final RegistryObject<MobEffect> EXP_BOOST = MOB_EFFECTS.register("exp_boost",
            () -> new ExpBoostEffect(MobEffectCategory.BENEFICIAL, 0x32CD32));
    public static final RegistryObject<MobEffect> STUN = MOB_EFFECTS.register("stun",
            () -> new StunEffect(MobEffectCategory.HARMFUL, 0xFFD700));
    public static final RegistryObject<MobEffect> MILK_BOY = MOB_EFFECTS.register("milk_boy",
            () -> new MilkBoyEffect(MobEffectCategory.BENEFICIAL, 0xD3D3D3));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
