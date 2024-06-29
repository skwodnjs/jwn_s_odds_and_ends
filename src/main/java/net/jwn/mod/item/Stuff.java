package net.jwn.mod.item;

import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.SyncStatS2CPacket;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Stuff extends Item {
    public int id;
    public StuffType type;
    public StuffRank rank;
    public List<Stat> stats = new ArrayList<>(); // 최대 레벨일 때 스텟 상승치
    public Stuff(Properties pProperties, int id, StuffType type, StuffRank rank, Stat... stats) {
        super(pProperties);
        this.id = id;
        this.type = type;
        this.rank = rank;
        this.stats.addAll(List.of(stats));
        AllOfStuff.ALL_OF_STUFF.put(id, this);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            pPlayer.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                if (myStuff.register(this) == 0) {
                    pPlayer.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                        stuffIFound.updateStuffIFoundSecondTime(this.id);
                    });

                    StatOperator.reCalculate(pPlayer);

                    for (Stat stat : stats) {
                        if (stat.type().equals(StatType.HEALTH)) {
                            pPlayer.heal(stat.value());
                        }
                    }

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
