package net.jwn.mod.item.disposable;

import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.CoolTime;
import net.jwn.mod.stuff.CoolTimeProvider;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;
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
        pPlayer.getCapability(CoolTimeProvider.CoolTime).ifPresent(CoolTime::reset);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
