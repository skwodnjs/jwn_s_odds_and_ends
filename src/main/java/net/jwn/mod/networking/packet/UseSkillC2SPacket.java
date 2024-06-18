package net.jwn.mod.networking.packet;

import net.jwn.mod.item.ActiveStuff;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.MyStuffProvider;
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

                    // test
                    System.out.println("id: \t\t" + id);
                    System.out.println("level: \t\t" + level);

                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    if (stuff instanceof ActiveStuff activeStuff) {
                        boolean success = false;
                        // ---------- SKILL HANDLER  ----------

                        if (id == 1) success = poop(player);
                        else if (id == 32) success = cellPhone(player);

                        // ------------------------------------

                        if (success) player.getPersistentData().putInt("cool_time",
                                activeStuff.t0 - activeStuff.weight * (level - 1));
                    }
                });
            }
            // test
        });
        return true;
    }
    // -------------------------- SKILLS --------------------------
    private boolean poop(ServerPlayer player) {
        Level level = player.level();
        level.setBlock(player.getOnPos().offset(0, 1, 0), Blocks.SAND.defaultBlockState(), 3);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ROOTED_DIRT_BREAK,
                SoundSource.PLAYERS, 1F, 0.2F);
        return true;
    }
    private boolean cellPhone(ServerPlayer player) {
        if (player.level().dimension() == Level.OVERWORLD) {
            player.sendSystemMessage(Component.literal("OVERWORLD!"));
            BlockPos pos = player.getOnPos().offset(0, 1, 0);
            int[] posArray = {pos.getX(), pos.getY(), pos.getZ()};
            player.getPersistentData().putIntArray("cell_phone", posArray);
            return false;
        } else {
            player.sendSystemMessage(Component.literal("NO OVERWORLD!"));
            int[] pos = player.getPersistentData().getIntArray("cell_phone");
            if (pos.length == 3) {
                ServerLevel overWorld = player.getServer().getLevel(Level.OVERWORLD);
                player.teleportTo(overWorld, pos[0], pos[1], pos[2], 0, 0);
            } else {
                player.sendSystemMessage(Component.literal("§c먼저 오버월드에서 장소를 저장해야 합니다!§c"));
            }
            return true;
        }
    }
}
