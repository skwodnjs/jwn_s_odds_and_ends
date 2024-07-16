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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceLevelOne extends Stuff {
    public DiceLevelOne(Properties pProperties, int id, StuffRank rank) {
        super(pProperties, id, StuffType.DISPOSABLE, rank);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        super.use(pLevel, pPlayer, pUsedHand);
        if (!pLevel.isClientSide) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < pPlayer.getInventory().getContainerSize(); i++) {
                if (pPlayer.getInventory().getItem(i).getItem() instanceof Stuff stuff) {
                    if (stuff.type != StuffType.DISPOSABLE) {
                        list.add(i);
                    }
                }
            }
            Random random = new Random();
            int index = list.get(random.nextInt(list.size()));
            Stuff stuff = (Stuff) pPlayer.getInventory().getItem(index).getItem();
            int count = pPlayer.getInventory().getItem(index).getCount();

            StuffRank rank = stuff.rank;
            ItemStack newStuff;
            while (true) {
                if (Math.random() < 0.7 || rank == StuffRank.RARE) {
                    newStuff = AllOfStuff.getRandom(rank, null, stuff.id).getDefaultInstance();
                    break;
                } else {
                    rank = rank.prev();
                }
            }
            newStuff.setCount(count);
            pPlayer.getInventory().setItem(index, newStuff);

            ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
