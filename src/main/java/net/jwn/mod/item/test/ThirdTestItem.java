package net.jwn.mod.item.test;

import net.jwn.mod.stuff.*;
import net.jwn.mod.util.StatOperator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThirdTestItem extends Item {
    public ThirdTestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.sendSystemMessage(Component.literal("reset"));
        pPlayer.getCapability(MyStuffProvider.MY_STUFF).ifPresent(MyStuff::reset);
        pPlayer.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(StuffIFound::reset);
        pPlayer.getCapability(CoolTimeProvider.CoolTime).ifPresent(CoolTime::reset);
        StatOperator.reset(pPlayer);

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
