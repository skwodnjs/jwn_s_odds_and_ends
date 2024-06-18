package net.jwn.mod.item.test;

import net.jwn.mod.util.MyStuff;
import net.jwn.mod.util.StuffIFound;
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
        MyStuff.reset(pPlayer);
        StuffIFound.reset(pPlayer);

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
