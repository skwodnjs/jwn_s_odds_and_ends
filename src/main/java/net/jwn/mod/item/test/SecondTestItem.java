package net.jwn.mod.item.test;

import net.jwn.mod.stuff.StuffDataProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SecondTestItem extends Item {
    public SecondTestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            pPlayer.getCapability(StuffDataProvider.STUFF_DATA).ifPresent(stuffData -> {
                System.out.println(stuffData.print());
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
