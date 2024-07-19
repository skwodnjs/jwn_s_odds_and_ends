package net.jwn.mod.item.disposable;

import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.SyncMyStuffS2CPacket;
import net.jwn.mod.networking.packet.SyncStatS2CPacket;
import net.jwn.mod.networking.packet.SyncStuffIFoundS2CPacket;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.StuffRank;
import net.jwn.mod.util.StuffType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DiceLevelFive extends Stuff {
    public DiceLevelFive(Properties pProperties, int id, StuffRank rank) {
        super(pProperties, id, StuffType.DISPOSABLE, rank);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        super.use(pLevel, pPlayer, pUsedHand);
        if (!pLevel.isClientSide) {
            pPlayer.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                List<Integer> myActives = new ArrayList<>();
                List<Integer> myPassives = new ArrayList<>();
                for (int id : myStuff.myActiveStuffIds) {
                    if (id == 0) break;
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    Stuff newStuff;
                    if (stuff.rank == StuffRank.LEGENDARY) continue;
                    newStuff = AllOfStuff.getRandom(Math.random() < 0.4 ? stuff.rank.next() : stuff.rank, StuffType.ACTIVE,
                            myActives.stream().mapToInt(Integer::intValue).toArray());

                    myStuff.replace(id, newStuff.id);
                    pPlayer.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                        stuffIFound.updateStuffIFound(newStuff.id, 2);
                    });
                    myActives.add(newStuff.id);
                }
                for (int id : myStuff.myPassiveStuffIds) {
                    if (id == 0) break;
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    Stuff newStuff;
                    if (stuff.rank == StuffRank.LEGENDARY) continue;
                    newStuff = AllOfStuff.getRandom(Math.random() < 0.4 ? stuff.rank.next() : stuff.rank, StuffType.PASSIVE,
                            myPassives.stream().mapToInt(Integer::intValue).toArray());

                    myStuff.replace(id, newStuff.id);
                    pPlayer.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                        stuffIFound.updateStuffIFound(newStuff.id, 3);
                    });
                    myPassives.add(newStuff.id);
                }
                ModMessages.sendToPlayer(new SyncStatS2CPacket(pPlayer), (ServerPlayer) pPlayer);
                ModMessages.sendToPlayer(new SyncMyStuffS2CPacket(pPlayer), (ServerPlayer) pPlayer);
                ModMessages.sendToPlayer(new SyncStuffIFoundS2CPacket(pPlayer), (ServerPlayer) pPlayer);

                ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            });


        }

        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
