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
                        if (this.type == StuffType.ACTIVE) {
                            stuffIFound.updateStuffIFound(this.id, 2);
                        } else if (this.type == StuffType.PASSIVE){
                            stuffIFound.updateStuffIFound(this.id, 3);
                        }
                    });

                    StatOperator.reCalculate(pPlayer);
                    Map<String, Float> map = new HashMap<>();
                    for (StatType type : StatType.values()) {
                        map.put(type.name, pPlayer.getPersistentData().getFloat(type.name));
                    }
                    ModMessages.sendToPlayer(new SyncStatS2CPacket(map), (ServerPlayer) pPlayer);

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
                    if (type == StuffType.ACTIVE) {
                        pPlayer.sendSystemMessage(Component.literal("§c액티브 아이템은 최대 3개까지 가질 수 있습니다."));
                    } else if (type == StuffType.PASSIVE) {
                        pPlayer.sendSystemMessage(Component.literal("§c패시브 아이템은 최대 16개까지 가질 수 있습니다."));
                    }

                }
            });
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
