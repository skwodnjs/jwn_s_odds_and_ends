package net.jwn.mod.item;

import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;
import net.minecraft.network.chat.Component;
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
        AllOfStuff.ALL_OF_STUFF.put(id, this);
    }
    public int id;
    public StuffType type;
    public StuffRank rank;
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        System.out.println(this);

        if (!pLevel.isClientSide) {
            pPlayer.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (myStuff.register(this) == 0) {
                    pPlayer.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                        stuffIFound.updateStuffIFoundSecondTime(this.id);
                    });
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                } else if (myStuff.register(this) == 1) {
                    pPlayer.sendSystemMessage(Component.literal("§c이미 최대로 강화하였습니다."));
                } else if (myStuff.register(this) == -1) {
                    pPlayer.sendSystemMessage(Component.literal("§c더 이상 아이템을 가질 수 없습니다."));
                }
            });
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
