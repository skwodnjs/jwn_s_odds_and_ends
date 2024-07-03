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
            () -> new PassiveStuff(new Item.Properties(), 4, StuffRank.RARE, new Stat(StatType.LUCK, 1.1f)));
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite",
            () -> new ActiveStuff(new Item.Properties(), 5, StuffRank.EPIC, 75 * 20, 15 * 20));
    public static final RegistryObject<Item> LIGHT_FEATHER = ITEMS.register("light_feather",
            () -> new PassiveStuff(new Item.Properties(), 6, StuffRank.RARE));
    public static final RegistryObject<Item> WORMHOLE = ITEMS.register("wormhole",
            () -> new ActiveStuff(new Item.Properties(), 7, StuffRank.EPIC, 50 * 20, 10 * 20));
    public static final RegistryObject<Item> BALLOON = ITEMS.register("balloon",
            () -> new PassiveStuff(new Item.Properties(), 8, StuffRank.EPIC));
    public static final RegistryObject<Item> LEATHER_WALLET = ITEMS.register("leather_wallet",
            () -> new PassiveStuff(new Item.Properties(), 9, StuffRank.RARE));
    public static final RegistryObject<Item> CAN = ITEMS.register("can",
            () -> new PassiveStuff(new Item.Properties(), 10, StuffRank.RARE));
    public static final RegistryObject<Item> TURTLE_SHELL = ITEMS.register("turtle_shell",
            () -> new PassiveStuff(new Item.Properties(), 11, StuffRank.RARE, new Stat(StatType.SPEED, -2.0f)));
    public static final RegistryObject<Item> PUFFER_SKIN = ITEMS.register("puffer_skin",
            () -> new PassiveStuff(new Item.Properties(), 12, StuffRank.RARE));
    public static final RegistryObject<Item> PHANTOM_EYE = ITEMS.register("phantom_eye",
            () -> new PassiveStuff(new Item.Properties(), 13, StuffRank.RARE));
    public static final RegistryObject<Item> HOGLIN_TUSK = ITEMS.register("hoglin_tusk",
            () -> new PassiveStuff(new Item.Properties(), 14, StuffRank.RARE));
    public static final RegistryObject<Item> PIG_NOSE = ITEMS.register("pig_nose",
            () -> new PassiveStuff(new Item.Properties(), 15, StuffRank.RARE));
    public static final RegistryObject<Item> DICE_I = ITEMS.register("dice_i",
            () -> new ActiveStuff(new Item.Properties(), 16, StuffRank.RARE, 120 * 20, 20 * 20));
    public static final RegistryObject<Item> DICE_II = ITEMS.register("dice_ii",
            () -> new ActiveStuff(new Item.Properties(), 17, StuffRank.RARE, 120 * 20, 20 * 20));
    public static final RegistryObject<Item> DICE_III = ITEMS.register("dice_iii",
            () -> new ActiveStuff(new Item.Properties(), 18, StuffRank.EPIC, 120 * 20, 20 * 20));
    public static final RegistryObject<Item> DICE_100 = ITEMS.register("dice_100",
            () -> new ActiveStuff(new Item.Properties(), 19, StuffRank.UNIQUE, 120 * 20, 20 * 20));
    public static final RegistryObject<Item> DICE_999 = ITEMS.register("dice_999",
            () -> new ActiveStuff(new Item.Properties(), 20, StuffRank.UNIQUE, 120 * 20, 20 * 20));
    public static final RegistryObject<Item> GUARDIAN_THORN = ITEMS.register("guardian_thorn",
            () -> new PassiveStuff(new Item.Properties(), 21, StuffRank.EPIC, new Stat(StatType.KNOCKBACK_RESISTANCE, 2.66f)));
    public static final RegistryObject<Item> ELDER_GUARDIAN_THORN = ITEMS.register("elder_guardian_thorn",
            () -> new PassiveStuff(new Item.Properties(), 22, StuffRank.UNIQUE, new Stat(StatType.MINING_SPEED, 3.61f)));
    public static final RegistryObject<Item> STAR = ITEMS.register("star",
            () -> new PassiveStuff(new Item.Properties(), 23, StuffRank.UNIQUE,
                    new Stat(StatType.HEALTH, 2.25f),
                    new Stat(StatType.SPEED, 2.25f),
                    new Stat(StatType.MINING_SPEED, 2.25f),
                    new Stat(StatType.ATTACK_DAMAGE, 2.25f),
                    new Stat(StatType.KNOCKBACK_RESISTANCE, 2.25f),
                    new Stat(StatType.LUCK, 2.25f)));
    public static final RegistryObject<Item> WITCH_WAND = ITEMS.register("witch_wand",
            () -> new ActiveStuff(new Item.Properties(), 24, StuffRank.EPIC, 90 * 20, 0));
    public static final RegistryObject<Item> SHINY_SOMETHING = ITEMS.register("shiny_something",
            () -> new PassiveStuff(new Item.Properties(), 25, StuffRank.EPIC, new Stat(StatType.LUCK, 2.55f)));
    public static final RegistryObject<Item> LAZINESS = ITEMS.register("laziness",
            () -> new PassiveStuff(new Item.Properties(), 26, StuffRank.UNIQUE, new Stat(StatType.SPEED, 3.24f)));
    public static final RegistryObject<Item> ALCOHOL = ITEMS.register("alcohol",
            () -> new PassiveStuff(new Item.Properties(), 27, StuffRank.EPIC, new Stat(StatType.ATTACK_DAMAGE, 2.73f)));

    // ------ dummy ------

    public static final RegistryObject<Item> DUMMY_PASSIVE_1 = ITEMS.register("dummy_passive_1",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 40, StuffRank.EPIC));  // 59
    public static final RegistryObject<Item> DUMMY_PASSIVE_2 = ITEMS.register("dummy_passive_2",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 39, StuffRank.EPIC));
    public static final RegistryObject<Item> DUMMY_PASSIVE_3 = ITEMS.register("dummy_passive_3",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 38, StuffRank.EPIC));
    public static final RegistryObject<Item> DUMMY_PASSIVE_4 = ITEMS.register("dummy_passive_4",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 37, StuffRank.EPIC));
    public static final RegistryObject<Item> DUMMY_PASSIVE_5 = ITEMS.register("dummy_passive_5",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 36, StuffRank.EPIC));
    public static final RegistryObject<Item> DUMMY_PASSIVE_6 = ITEMS.register("dummy_passive_6",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 35, StuffRank.EPIC));
    public static final RegistryObject<Item> DUMMY_PASSIVE_7 = ITEMS.register("dummy_passive_7",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 34, StuffRank.EPIC));
    public static final RegistryObject<Item> DUMMY_PASSIVE_8 = ITEMS.register("dummy_passive_8",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 33, StuffRank.UNIQUE));
    public static final RegistryObject<Item> DUMMY_PASSIVE_9 = ITEMS.register("dummy_passive_9",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 32, StuffRank.UNIQUE));
    public static final RegistryObject<Item> DUMMY_PASSIVE_10 = ITEMS.register("dummy_passive_10",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 31, StuffRank.UNIQUE));
    public static final RegistryObject<Item> DUMMY_PASSIVE_11 = ITEMS.register("dummy_passive_11",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 30, StuffRank.UNIQUE));
    public static final RegistryObject<Item> DUMMY_PASSIVE_12 = ITEMS.register("dummy_passive_12",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 29, StuffRank.UNIQUE));
    public static final RegistryObject<Item> DUMMY_PASSIVE_13 = ITEMS.register("dummy_passive_13",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 28, StuffRank.UNIQUE));
    public static final RegistryObject<Item> DUMMY_PASSIVE_14 = ITEMS.register("dummy_passive_14",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 27, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_PASSIVE_15 = ITEMS.register("dummy_passive_15",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 26, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_PASSIVE_16 = ITEMS.register("dummy_passive_16",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 25, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_PASSIVE_17 = ITEMS.register("dummy_passive_17",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 24, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_PASSIVE_18 = ITEMS.register("dummy_passive_18",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 23, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_PASSIVE_19 = ITEMS.register("dummy_passive_19",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 22, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_PASSIVE_20 = ITEMS.register("dummy_passive_20",
            () -> new PassiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 21, StuffRank.LEGENDARY));
    public static final RegistryObject<Item> DUMMY_ACTIVE_1 = ITEMS.register("dummy_active_1",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 20, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_2 = ITEMS.register("dummy_active_2",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 19, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_3 = ITEMS.register("dummy_active_3",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 18, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_4 = ITEMS.register("dummy_active_4",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 17, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_5 = ITEMS.register("dummy_active_5",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 16, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_6 = ITEMS.register("dummy_active_6",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 15, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_7 = ITEMS.register("dummy_active_7",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 14, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_8 = ITEMS.register("dummy_active_8",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 13, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_9 = ITEMS.register("dummy_active_9",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 12, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_10 = ITEMS.register("dummy_active_10",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 11, StuffRank.UNIQUE, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_11 = ITEMS.register("dummy_active_11",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 10, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_12 = ITEMS.register("dummy_active_21",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 9, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_13 = ITEMS.register("dummy_active_13",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 8, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_14 = ITEMS.register("dummy_active_14",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 7, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_15 = ITEMS.register("dummy_active_15",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 6, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_16 = ITEMS.register("dummy_active_16",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 5, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_17 = ITEMS.register("dummy_active_17",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 4, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_18 = ITEMS.register("dummy_active_18",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 3, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_19 = ITEMS.register("dummy_active_19",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 2, StuffRank.LEGENDARY, 20, 0));
    public static final RegistryObject<Item> DUMMY_ACTIVE_20 = ITEMS.register("dummy_active_20",
            () -> new ActiveStuff(new Item.Properties(), AllOfStuff.MAX_STUFF - 1, StuffRank.LEGENDARY, 20, 0));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}