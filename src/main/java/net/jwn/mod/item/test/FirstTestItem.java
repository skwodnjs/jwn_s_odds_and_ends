package net.jwn.mod.item.test;

import net.jwn.mod.util.MyStuff;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FirstTestItem extends Item {
    public FirstTestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            pPlayer.sendSystemMessage(Component.literal("CLIENT SIDE"));
            pPlayer.sendSystemMessage(Component.literal(MyStuff.print(pPlayer)));
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
