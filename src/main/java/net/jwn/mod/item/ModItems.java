package net.jwn.mod.item;

import net.jwn.mod.Main;
import net.jwn.mod.item.test.FirstTestItem;
import net.jwn.mod.item.test.SecondTestItem;
import net.jwn.mod.item.test.ThirdTestItem;
import net.jwn.mod.util.StuffRank;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> FIRST_TEST_ITEM = ITEMS.register("first_test",
            () -> new FirstTestItem(new Item.Properties()));
    public static final RegistryObject<Item> SECOND_TEST_ITEM = ITEMS.register("second_test",
            () -> new SecondTestItem(new Item.Properties()));
    public static final RegistryObject<Item> THIRD_TEST_ITEM = ITEMS.register("third_test",
            () -> new ThirdTestItem(new Item.Properties()));

    public static final RegistryObject<Item> POOP = ITEMS.register("poop",
            () -> new ActiveStuff(new Item.Properties(), 1, StuffRank.RARE, 10, 0));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
