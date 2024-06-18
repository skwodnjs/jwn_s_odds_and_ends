package net.jwn.mod.item;

import net.jwn.mod.util.StuffIFound;
import net.jwn.mod.util.MyStuff;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class Stuff extends Item {
    public Stuff(Properties pProperties, int id, StuffType type, StuffRank rank) {
        super(pProperties);
        this.id = id;
        this.type = type;
        this.rank = rank;
        StuffIFound.ALL_OF_STUFF.put(id, this);
    }
    public int id;
    public StuffType type;
    public StuffRank rank;
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (MyStuff.register(pPlayer, this)) {
            if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
