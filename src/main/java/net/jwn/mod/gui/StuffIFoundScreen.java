package net.jwn.mod.gui;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class StuffIFoundScreen extends Screen {
    private static final ResourceLocation windowResource = new ResourceLocation(Main.MOD_ID, "textures/gui/stuff_i_found_gui.png");
    private int leftPos, topPos;
    private int page = 0;
    private ImageButton previousButton;
    private ImageButton nextButton;

    public StuffIFoundScreen() {
        super(Component.literal("STUFF I FOUND"));
    }
    @Override
    protected void init() {
        super.init();

        this.leftPos = (width - 256) / 2;
        this.topPos = (height - 180) / 2;
    }
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(windowResource, leftPos, topPos, 0, 0, 256, 180,256, 256);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (previousButton != null) {
            this.removeWidget(previousButton);
        }

        if (nextButton != null) {
            this.removeWidget(nextButton);
        }

        Player player = Minecraft.getInstance().player;
        assert player != null;

        player.getCapability(StuffIFoundProvider.STUFF_I_FOUND).ifPresent(s -> {
            int[] stuffIFound = s.stuffIFound;
            for (int index = 0; index < 65; index++) {
                int id = page * 65 + 1 + index;
                if (id > AllOfStuff.MAX_STUFF) {
                    break;
                }

                int pX;
                int pY;
                if (index < 30) {
                    pX = leftPos + 21 + (index % 5) * 19;
                    pY = topPos + 43 + (index / 5) * 19;
                } else {
                    pX = leftPos + 143 + (index % 5) * 19;
                    pY = topPos + 24 + (index / 5 - 6) * 19;
                }
                if (stuffIFound[id - 1] != 0) {
                    pGuiGraphics.blit(AllOfStuff.getResources(id), pX, pY, 0, 0, 16, 16, 16, 16);

                    if (pX <= pMouseX && pMouseX < pX + 16 && pY <= pMouseY && pMouseY < pY + 16) {
                        Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                        String name = I18n.get("item." + Main.MOD_ID + "." + stuff);
                        String tooltip = I18n.get("tooltip." + Main.MOD_ID + "." + stuff);

                        Component[] components;

                        if (stuffIFound[id - 1] == 1) {
                            components = new Component[] {Component.literal("??")};
                        } else if (stuffIFound[id - 1] == 2) {
                            components = new Component[] {Component.literal("id: " + id),
                                    Component.literal(stuff.rank.color_tag + name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")" + "§f"),
                                    Component.literal("??")};
                        } else {
                            components = new Component[] {Component.literal("id: " + id),
                                    Component.literal(stuff.rank.color_tag + name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")" + "§f"),
                                    Component.literal(tooltip)};
                        }

                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(components), pMouseX, pMouseY);
                    }
                } else {
                    pGuiGraphics.blit(windowResource, pX, pY, 42, 181, 16, 16, 256, 256);
                }
            }
        });

        if (page != 0) {
            previousButton = new ImageButton(leftPos + 26, topPos + 156, 18, 10,
                    0, 181, 13, windowResource, 256, 256, pButton -> {
                page -= 1;
            });
            addRenderableWidget(previousButton);
        }
        if ((page + 1) * 65 + 1 <= AllOfStuff.MAX_STUFF) {
            nextButton = new ImageButton(leftPos + 212, topPos + 156, 18, 10,
                    23, 181, 13, windowResource, 256, 256, pButton -> {
                page += 1;
            });
            addRenderableWidget(nextButton);
        }
    }
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBinding.STUFF_I_FOUND_KEY.getKey().getValue()) {
            this.onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
}