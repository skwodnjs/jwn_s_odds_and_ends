package net.jwn.mod.item;

import net.jwn.mod.Main;
import net.jwn.mod.item.test.FirstTestItem;
import net.jwn.mod.item.test.SecondTestItem;
import net.jwn.mod.item.test.ThirdTestItem;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.Stat;
import net.jwn.mod.util.StatType;
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
    public static final RegistryObject<Item> MAX_STAT_ITEM = ITEMS.register("max_stat_item",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF, StuffRank.RARE,
                    new Stat(StatType.HEALTH, 40f),
                    new Stat(StatType.SPEED, 20f),
                    new Stat(StatType.MINING_SPEED, 20f),
                    new Stat(StatType.ATTACK_DAMAGE, 20f),
                    new Stat(StatType.KNOCKBACK_RESISTANCE, 20f),
                    new Stat(StatType.LUCK, 20f)));
    public static final RegistryObject<Item> POOP = ITEMS.register("poop_item",
            () -> new ActiveStuff(new Item.Properties(), 1, StuffRank.RARE, 10 * 20, 0));
    public static final RegistryObject<Item> AMULET = ITEMS.register("amulet",
            () -> new PassiveStuff(new Item.Properties(), 2, StuffRank.EPIC));
    public static final RegistryObject<Item> CELL_PHONE = ITEMS.register("cell_phone",
            () -> new ActiveStuff(new Item.Properties(), 3, StuffRank.EPIC, 300 * 20, 60 * 20));
    public static final RegistryObject<Item> FOUR_LEAF_CLOVER = ITEMS.register("four_leaf_clover",
            () -> new PassiveStuff(new Item.Properties(), 4, StuffRank.RARE, new Stat(StatType.LUCK, 2.2f)));
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite",
            () -> new ActiveStuff(new Item.Properties(), 5, StuffRank.EPIC, 75 * 20, 15 * 20));
    public static final RegistryObject<Item> LIGHT_FEATHER = ITEMS.register("light_feather",
            () -> new PassiveStuff(new Item.Properties(), 6, StuffRank.RARE));
    public static final RegistryObject<Item> WORMHOLE = ITEMS.register("wormhole",
            () -> new ActiveStuff(new Item.Properties(), 7, StuffRank.EPIC, 50 * 20, 10 * 20));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}