package net.jwn.mod.gui;

import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.stuff.StuffIFoundProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.Functions;
import net.jwn.mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class StuffIFoundScreen extends Screen {
    private static final ResourceLocation windowResource = new ResourceLocation(Main.MOD_ID, "textures/gui/stuff_i_found_gui.png");
    private int leftPos, topPos;
    private int page = 0;
    private int detailId;

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

        this.clearWidgets();

        Player player = Minecraft.getInstance().player;
        assert player != null;

        if (detailId == 0) {
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
                        ImageButton stuffButton = new ImageButton(pX, pY, 16, 16, 0, 0, 0,
                                AllOfStuff.getResources(id), 16, 16, button -> {
                            if (s.stuffIFound[id - 1] == 3) {
                                detailId = id;
                            } else {
                                player.sendSystemMessage(Component.translatable("message." + Main.MOD_ID + ".stuff_i_found.cannot_see"));
                                detailId = 0;
                            }
                        });
                        addRenderableWidget(stuffButton);

                        if (pX <= pMouseX && pMouseX < pX + 16 && pY <= pMouseY && pMouseY < pY + 16) {
                            Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                            String name = I18n.get("item." + Main.MOD_ID + "." + stuff);
                            String tooltip = I18n.get("tooltip." + Main.MOD_ID + "." + stuff);

                            Component[] components;

                            if (stuffIFound[id - 1] == 1) {
                                components = new Component[]{Component.literal("??")};
                            } else if (stuffIFound[id - 1] == 2) {
                                components = new Component[]{Component.literal("id: " + id),
                                        Component.literal(name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")").withStyle(stuff.rank.color),
                                        Component.literal("??")};
                            } else {
                                components = new Component[]{Component.literal("id: " + id),
                                        Component.literal(name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")").withStyle(stuff.rank.color),
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
                ImageButton previousButton = new ImageButton(leftPos + 26, topPos + 156, 18, 10,
                        0, 181, 13, windowResource, 256, 256, pButton -> {
                    page -= 1;
                });
                addRenderableWidget(previousButton);
            }
            if ((page + 1) * 65 + 1 <= AllOfStuff.MAX_STUFF) {
                ImageButton nextButton = new ImageButton(leftPos + 212, topPos + 156, 18, 10,
                        23, 181, 13, windowResource, 256, 256, pButton -> {
                    page += 1;
                });
                addRenderableWidget(nextButton);
            }
        } else {
            pGuiGraphics.blit(AllOfStuff.getResources(detailId), leftPos + 59, topPos + 44,
                    0, 0, 16, 16, 16, 16);
            Stuff detailStuff = AllOfStuff.ALL_OF_STUFF.get(detailId);

            int line = 1;
            float scale = 0.5f;
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().scale(scale, scale, scale);

            Font font = Minecraft.getInstance().font;

            // ID
            String detailIdString = "id: %d".formatted(detailId);
            pGuiGraphics.drawString(font, detailIdString,(leftPos + 24) / scale, (topPos + 64) / scale, 0xFFdecc99, false);

            // NAME
            String nameHead = I18n.get("text." + Main.MOD_ID + ".stuff_i_found.name");
            String detailNameString = I18n.get(detailStuff.getDescriptionId());
            pGuiGraphics.drawString(font, nameHead + detailNameString,
                    (leftPos + 24) / scale, (topPos + 64 + 8 * line++) / scale, 0xFFdecc99, false);

            // RANK
            String rankHead = I18n.get("text." + Main.MOD_ID + ".stuff_i_found.rank");
            String detailRankString = detailStuff.rank.toString().toLowerCase();
            pGuiGraphics.drawString(font, rankHead + detailRankString,
                    (leftPos + 24) / scale, (topPos + 64 + 8 * line++) / scale, 0xFFdecc99, false);

            // DESCRIPTION
            String descriptionHead = I18n.get("text." + Main.MOD_ID + ".stuff_i_found.description");
            pGuiGraphics.drawString(font, descriptionHead,
                    (leftPos + 24) / scale, (topPos + 64 + 8 * line++) / scale, 0xFFdecc99, false);
            String description = I18n.get("tooltip.details." + Main.MOD_ID + "." + detailStuff);
            List<String> lines = Functions.splitText(font, description, 90 / scale);
            for (String string : lines) {
                pGuiGraphics.drawString(font, string,
                        (leftPos + 24) / scale, (topPos + 64 + 8 * line++) / scale, 0xFFdecc99, false);
            }

            pGuiGraphics.pose().popPose();
        }
    }
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBinding.STUFF_I_FOUND_KEY.getKey().getValue() && detailId == 0) {
            this.onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    @Override
    public boolean shouldCloseOnEsc() {
        if (detailId != 0) {
            detailId = 0;
            return false;
        } else {
            return super.shouldCloseOnEsc();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}