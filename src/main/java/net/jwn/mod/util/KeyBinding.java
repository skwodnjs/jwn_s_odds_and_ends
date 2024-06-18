package net.jwn.mod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.jwn.mod.Main;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    /*
    CAN USE
    R Y U I O G H J K Z V B N M
    , .
    CAPS LOCK
    ` 0 - = BACKSPACE \
    ALT 오른쪽 CTRL
    F1 F3 F4 F6 F7 F8 F9 F10 F12
    */
    public static final String KEY_CATEGORY_MOD = "key.category." + Main.MOD_ID + ".mod";
    public static final String KEY_TEST_1 = "key." + Main.MOD_ID + ".test";
    public static final String KEY_TEST_2 = "key." + Main.MOD_ID + ".test_2";
    public static final String KEY_TEST_3 = "key." + Main.MOD_ID + ".test_3";
    public static final String KEY_TEST_4 = "key." + Main.MOD_ID + ".test_4";
    public static final String KEY_ACTIVE_SKILL = "key." + Main.MOD_ID + ".active_skill";
    public static final String KEY_ACTIVE_STUFF_SWITCH = "key." + Main.MOD_ID + ".active_stuff_switch";
    public static final KeyMapping TEST_1_KEY = new KeyMapping(KEY_TEST_1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_MOD);
    public static final KeyMapping TEST_2_KEY = new KeyMapping(KEY_TEST_2, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_MOD);
    public static final KeyMapping TEST_3_KEY = new KeyMapping(KEY_TEST_3, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, KEY_CATEGORY_MOD);
    public static final KeyMapping TEST_4_KEY = new KeyMapping(KEY_TEST_4, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_MOD);
    public static final KeyMapping ACTIVE_SKILL_KEY = new KeyMapping(KEY_ACTIVE_SKILL, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_MOD);
    public static final KeyMapping ACTIVE_STUFF_SWITCH_KEY = new KeyMapping(KEY_ACTIVE_STUFF_SWITCH, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_MOD);

}
