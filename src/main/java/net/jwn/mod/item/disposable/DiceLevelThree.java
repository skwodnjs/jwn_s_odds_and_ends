package net.jwn.mod.item.disposable;

import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DiceLevelThree extends Stuff {
    public DiceLevelThree(Properties pProperties, int id, StuffRank rank) {
        super(pProperties, id, StuffType.DISPOSABLE, rank);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        super.use(pLevel, pPlayer, pUsedHand);
        if (!pLevel.isClientSide) {
            for (int i = 0; i < pPlayer.getInventory().getContainerSize(); i++) {
                if (pPlayer.getInventory().getItem(i).getItem() instanceof Stuff stuff) {
                    if (stuff.type != StuffType.DISPOSABLE) {
                        int count = pPlayer.getInventory().getItem(i).getCount();
                        ItemStack newStuff = AllOfStuff.getRandom(stuff.rank, null, stuff.id).getDefaultInstance();
                        newStuff.setCount(count);
                        pPlayer.getInventory().setItem(i, newStuff);
                    }
                }
            }

            ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
