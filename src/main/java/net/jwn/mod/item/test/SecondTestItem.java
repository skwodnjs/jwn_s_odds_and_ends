package net.jwn.mod.item.test;

import net.jwn.mod.stuff.MyStuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.util.StatOperator;
import net.minecraft.network.chat.Component;
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
        if (!pLevel.isClientSide) {
            pPlayer.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                pPlayer.sendSystemMessage(Component.literal(myStuff.print()));
            });
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
