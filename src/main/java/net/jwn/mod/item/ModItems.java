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

    public static final RegistryObject<Item> POOP = ITEMS.register("poop_item",
            () -> new ActiveStuff(new Item.Properties(), 1, StuffRank.RARE, 10 * 20, 0));

    public static final RegistryObject<Item> AMULET = ITEMS.register("amulet",
            () -> new PassiveStuff(new Item.Properties(), 2, StuffRank.EPIC));


    public static final RegistryObject<Item> CELL_PHONE = ITEMS.register("cell_phone",
            () -> new ActiveStuff(new Item.Properties(), 32, StuffRank.EPIC, 300 * 20, 60 * 20));

    public static final RegistryObject<Item> FOUR_LEAF_CLOVER = ITEMS.register("four_leaf_clover",
            () -> new PassiveStuff(new Item.Properties(), 33, StuffRank.RARE));

    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite",
            () -> new ActiveStuff(new Item.Properties(), 34, StuffRank.EPIC, 75 * 20, 15 * 20));

    public static final RegistryObject<Item> LIGHT_FEATHER = ITEMS.register("light_feather",
            () -> new PassiveStuff(new Item.Properties(), 35, StuffRank.RARE));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
