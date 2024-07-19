package net.jwn.mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class MilkBoyEffect extends MobEffect {
    protected MilkBoyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        // ONLY SERVER
        for (MobEffectInstance mobEffectInstance : pLivingEntity.getActiveEffects()) {
            if (!(mobEffectInstance.getEffect() instanceof MilkBoyEffect)) {
                pLivingEntity.removeEffect(mobEffectInstance.getEffect());
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration > 0;
    }
}
