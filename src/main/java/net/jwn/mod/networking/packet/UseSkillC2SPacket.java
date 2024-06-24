package net.jwn.mod.networking.packet;

import net.jwn.mod.block.ModBlocks;
import net.jwn.mod.item.ActiveStuff;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.util.ActiveOperator;
import net.jwn.mod.util.AllOfStuff;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Function;
import java.util.function.Supplier;

public class UseSkillC2SPacket {

    public UseSkillC2SPacket() {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public UseSkillC2SPacket(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            assert player != null : "use skill C2S packet, player null";

            int coolTime = player.getPersistentData().getInt("cool_time");
            if (coolTime != 0) {
                player.sendSystemMessage(Component.literal("§c아직 스킬을 사용할 수 없습니다.§c"));
            } else {
                player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                    int id = myStuff.getMainActiveStuffId();
                    int level = myStuff.getMainActiveStuffLevel();

                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    if (stuff instanceof ActiveStuff activeStuff) {
                        boolean success = false;
                        // ---------- ACTIVE OPERATOR  ----------

                        if (id == 1) success = ActiveOperator.poop(player);
                        else if (id == 32) success = ActiveOperator.cellPhone(player);
                        else if (id == 34) success = ActiveOperator.dynamite(player);

                        // --------------------------------------

                        if (success) player.getPersistentData().putInt("cool_time",
                                activeStuff.t0 - activeStuff.weight * (level - 1));
                    }
                });
            }
        });
        return true;
    }
}
