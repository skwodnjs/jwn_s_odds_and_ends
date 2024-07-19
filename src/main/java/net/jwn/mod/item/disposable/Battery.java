package net.jwn.mod.item.disposable;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.CoolTime;
import net.jwn.mod.stuff.CoolTimeProvider;
import net.jwn.mod.stuff.StuffDataProvider;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Battery extends Stuff {
    public Battery(Properties pProperties, int id, StuffRank rank) {
        super(pProperties, id, StuffType.DISPOSABLE, rank);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getCapability(StuffDataProvider.STUFF_DATA).ifPresent(stuffData -> {
            if (stuffData.getBatteryTimer() == 0) {
                pPlayer.getCapability(CoolTimeProvider.CoolTime).ifPresent(CoolTime::reset);
                stuffData.putBatteryTimer(10 * 20);
            } else {
                pLevel.explode(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 2.0f, Level.ExplosionInteraction.NONE);
                pPlayer.sendSystemMessage(Component.translatable("message." + Main.MOD_ID + ".battery.cannot_use"));
            }
        });
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
