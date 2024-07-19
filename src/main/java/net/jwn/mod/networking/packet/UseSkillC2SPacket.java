package net.jwn.mod.networking.packet;

import net.jwn.mod.Main;
import net.jwn.mod.item.ActiveStuff;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.CoolTimeProvider;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.ActiveOperator;
import net.jwn.mod.util.AllOfStuff;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UseSkillC2SPacket {

    public UseSkillC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public UseSkillC2SPacket(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null;

            player.getCapability(CoolTimeProvider.CoolTime).ifPresent(coolTime -> {
                int cool_time = coolTime.get();
                if (cool_time != 0) {
                    player.sendSystemMessage(Component.translatable("message." + Main.MOD_ID + ".cool_time.cannot_use"));
                } else {
                    player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                        int id = myStuff.myActiveStuffIds[0];
                        int level = myStuff.myActiveStuffLevels[0];

                        Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                        if (stuff instanceof ActiveStuff activeStuff) {
                            boolean success = false;

                            // ---------- ACTIVE OPERATOR  ----------

                            if (id == 1) success = ActiveOperator.poo(player, level);
                            else if (id == 3) success = ActiveOperator.cellPhone(player);
                            else if (id == 5) success = ActiveOperator.dynamite(player);
                            else if (id == 7) success = ActiveOperator.wormhole(player, level);
                            else if (id == 9) success = ActiveOperator.mysterious_yellow_liquid(player, level);
                            else if (id == 11) success = ActiveOperator.thank_you_for_the_meal(player);
                            else if (id == 13) success = ActiveOperator.witch_wand(player, level);
                            else if (id == 19) success = ActiveOperator.piggy_bank(player, level);
                            else if (id == 20) success = ActiveOperator.storage_box(player);
                            else if (id == 30) success = ActiveOperator.milk_boy(player, level);

                            // --------------------------------------

                            if (success) {
                                coolTime.set(activeStuff.t0 - activeStuff.weight * (level - 1));
                            }

                            player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(stuffIFound -> {
                                stuffIFound.updateStuffIFound(id, 3);
                            });
                        }
                    });
                }
            });
        });
    }
}
