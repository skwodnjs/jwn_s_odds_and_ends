package net.jwn.mod.gui;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.RemoveStuffC2SPacket;
import net.jwn.mod.networking.packet.SyncForGUIRequestC2SPacket;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.util.AllOfStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class MyStuffScreen extends Screen {
    private static final ResourceLocation resourceLocation = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff_gui.png");
    private static final ResourceLocation levelLocation = new ResourceLocation(Main.MOD_ID, "textures/gui/level.png");
    private static final Player player = Minecraft.getInstance().player;
    private int leftPos, topPos;
    private ImageButton trashCanButton;
    private boolean removeMode = false;

    public MyStuffScreen() {
        super(Component.literal("MY STUFF"));
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (width - 146) / 2;
        this.topPos = (height - 180) / 2;

        ModMessages.sendToServer(new SyncForGUIRequestC2SPacket());
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(resourceLocation, leftPos, topPos, 0, 0, 146, 180);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        assert player != null;
        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            for (int i = 0; i < myStuff.myActiveStuffIds.length; i++) {
                int id = myStuff.myActiveStuffIds[i];
                int level = myStuff.myActiveStuffLevels[i];
                if (id == 0) {
                    break;
                }
                int pX = leftPos + 21 + (i % 7) * 15;
                int pY = topPos + 60;
                pGuiGraphics.blit(AllOfStuff.getResources(id), pX, pY, 0, 0, 12, 12, 12, 12);
                pGuiGraphics.blit(levelLocation, pX + 8, pY + 7, (level - 1) * 5, 0, 5, 6, 25, 6);
                if (pX <= pMouseX && pMouseX < pX + 16 && pY <= pMouseY && pMouseY < pY + 16) {
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    String name = I18n.get("item." + Main.MOD_ID + "." + stuff);

                    Component[] components = {Component.literal("id: " + id),
                            Component.literal(stuff.rank.color_tag + name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")" + "§f"),
                            Component.literal("level: " + level)};

                    if (!removeMode) {
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(components), pMouseX, pMouseY);
                    } else {
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip." + Main.MOD_ID + ".remove")) , pMouseX, pMouseY);
                    }
                }
            }

            for (int i = 0; i < myStuff.myPassiveStuffIds.length; i++) {
                int id = myStuff.myPassiveStuffIds[i];
                int level = myStuff.myPassiveStuffLevels[i];
                if (id == 0) {
                    break;
                }
                int pX = leftPos + 21 + (i % 7) * 15;
                int pY = topPos + 97 + (i / 7) * 15;
                pGuiGraphics.blit(AllOfStuff.getResources(id), pX, pY, 0, 0, 12, 12, 12, 12);
                pGuiGraphics.blit(levelLocation, pX + 8, pY + 7, (level - 1) * 5, 0, 5, 6, 25, 6);
                if (pX <= pMouseX && pMouseX < pX + 16 && pY <= pMouseY && pMouseY < pY + 16) {
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    String name = I18n.get("item." + Main.MOD_ID + "." + stuff);

                    Component[] components = {Component.literal("id: " + id),
                            Component.literal(stuff.rank.color_tag + name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")" + "§f"),
                            Component.literal("level: " + level)};

                    if (!removeMode) {
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(components), pMouseX, pMouseY);
                    } else {
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip." + Main.MOD_ID + ".remove")) , pMouseX, pMouseY);
                    }
                }
            }
            trashCanButton = new TrashCanButton(leftPos + 109, topPos + 156, 10, 11,
                    removeMode ? 56 : 45, 181, 0, resourceLocation, 256, 256, pButton -> {
                removeMode = !removeMode;
            });
            addRenderableWidget(trashCanButton);
        });
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (removeMode) {
            assert player != null;
            player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
                int removeId = 0;
                for (int i = 0; i < myStuff.myActiveStuffIds.length; i++) {
                    int id = myStuff.myActiveStuffIds[i];
                    if (id == 0) {
                        break;
                    }
                    int pX = leftPos + 21 + (i % 7) * 15;
                    int pY = topPos + 60;
                    if (pX <= pMouseX && pMouseX < pX + 16 && pY <= pMouseY && pMouseY < pY + 16) {
                        removeId = id;
                    }
                }

                for (int i = 0; i < myStuff.myPassiveStuffIds.length; i++) {
                    int id = myStuff.myPassiveStuffIds[i];
                    if (id == 0) {
                        break;
                    }
                    int pX = leftPos + 21 + (i % 7) * 15;
                    int pY = topPos + 97 + (i / 7) * 15;
                    if (pX <= pMouseX && pMouseX < pX + 16 && pY <= pMouseY && pMouseY < pY + 16) {
                        removeId = id;
                    }
                }

                if (removeId != 0) {
                    ModMessages.sendToServer(new RemoveStuffC2SPacket(removeId));
                    ModMessages.sendToServer(new SyncForGUIRequestC2SPacket());
                    removeMode = !removeMode;
                }
            });
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
