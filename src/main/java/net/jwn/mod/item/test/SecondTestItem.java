package net.jwn.mod.item.test;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.StuffDataProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.Functions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SecondTestItem extends Item {
    public SecondTestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            Stuff detailStuff = AllOfStuff.ALL_OF_STUFF.get(6);
            float scale = 0.5f;
            String description = I18n.get("tooltip.details." + Main.MOD_ID + "." + detailStuff);
            List<String> lines = Functions.splitText(Minecraft.getInstance().font, description, 90 / scale);
            for (String line : lines) {
                System.out.println(line);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
