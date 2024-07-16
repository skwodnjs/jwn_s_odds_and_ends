package net.jwn.mod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jwn.mod.Main;
import net.jwn.mod.item.Stuff;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.MainActiveSwitchC2SPacket;
import net.jwn.mod.networking.packet.RemoveStuffC2SPacket;
import net.jwn.mod.networking.packet.SyncMyStuffRequestC2SPacket;
import net.jwn.mod.networking.packet.SyncStatRequestC2SPacket;
import net.jwn.mod.stuff.MyStuffProvider;
import net.jwn.mod.util.AllOfStuff;
import net.jwn.mod.util.KeyBinding;
import net.jwn.mod.util.StatType;
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
    private static final ResourceLocation windowResource = new ResourceLocation(Main.MOD_ID, "textures/gui/my_stuff_gui.png");
    private static final ResourceLocation levelResource = new ResourceLocation(Main.MOD_ID, "textures/gui/level.png");
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
    }
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(windowResource, leftPos, topPos, 0, 0, 146, 180, 256, 256);

        RenderSystem.enableBlend();

        // STAT IMAGE
        pGuiGraphics.blit(new ResourceLocation(Main.MOD_ID, "textures/gui/health.png"),
                leftPos + 21, topPos + 41, 0, 0, 6, 6, 6, 6);
        pGuiGraphics.blit(new ResourceLocation(Main.MOD_ID, "textures/gui/speed.png"),
                leftPos + 47, topPos + 41, 0, 0, 6, 6, 6, 6);
        pGuiGraphics.blit(new ResourceLocation(Main.MOD_ID, "textures/gui/mining_speed.png"),
                leftPos + 73, topPos + 41, 0, 0, 6, 6, 6, 6);
        pGuiGraphics.blit(new ResourceLocation(Main.MOD_ID, "textures/gui/attack_damage.png"),
                leftPos + 21, topPos + 47, 0, 0, 6, 6, 6, 6);
        pGuiGraphics.blit(new ResourceLocation(Main.MOD_ID, "textures/gui/knockback_resistance.png"),
                leftPos + 47, topPos + 47, 0, 0, 6, 6, 6, 6);
        pGuiGraphics.blit(new ResourceLocation(Main.MOD_ID, "textures/gui/luck.png"),
                leftPos + 73, topPos + 47, 0, 0, 6, 6, 6, 6);

        // STAT TOOLTIP
        if (topPos + 41 <= pMouseY && pMouseY < topPos + 41 + 6) {
            if (leftPos + 21 <= pMouseX && pMouseX < leftPos + 21 + 20) {
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.literal("체력")), pMouseX, pMouseY);
            } else if (leftPos + 47 <= pMouseX && pMouseX < leftPos + 47 + 20) {
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.literal("속도")), pMouseX, pMouseY);
            } else if (leftPos + 73 <= pMouseX && pMouseX < leftPos + 73 + 20) {
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.literal("채굴 속도")), pMouseX, pMouseY);
            }
        } else if (topPos + 47 <= pMouseY && pMouseY < topPos + 47 + 6) {
            if (leftPos + 21 <= pMouseX && pMouseX < leftPos + 21 + 20) {
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.literal("공격력")), pMouseX, pMouseY);
            } else if (leftPos + 47 <= pMouseX && pMouseX < leftPos + 47 + 20) {
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.literal("넉백 저항")), pMouseX, pMouseY);
            } else if (leftPos + 73 <= pMouseX && pMouseX < leftPos + 73 + 20) {
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.literal("행운")), pMouseX, pMouseY);
            }
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        Player player = Minecraft.getInstance().player;
        assert player != null;

        // STAT VALUE & COOL TIME VALUE
        float scale = 0.5f;
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(scale, scale, scale);

        pGuiGraphics.drawString(Minecraft.getInstance().font,
                (player.getPersistentData().getFloat(StatType.HEALTH.name) >= 0 ? "+" : "")
                        + "%.1f".formatted(player.getPersistentData().getFloat(StatType.HEALTH.name) / 2),
                (leftPos + 21 + 8) / scale, (topPos + 41 + 2) / scale, 0xFFdecc99, false);
        pGuiGraphics.drawString(Minecraft.getInstance().font,
                (player.getPersistentData().getFloat(StatType.SPEED.name) >= 0 ? "+" : "")
                        + "%.1f".formatted(player.getPersistentData().getFloat(StatType.SPEED.name)),
                (leftPos + 47 + 8) / scale, (topPos + 41 + 2) / scale, 0xFFdecc99, false);
        pGuiGraphics.drawString(Minecraft.getInstance().font,
                (player.getPersistentData().getFloat(StatType.MINING_SPEED.name) >= 0 ? "+" : "")
                        + "%.1f".formatted(player.getPersistentData().getFloat(StatType.MINING_SPEED.name)),
                (leftPos + 73 + 8) / scale, (topPos + 41 + 2) / scale, 0xFFdecc99, false);
        pGuiGraphics.drawString(Minecraft.getInstance().font,
                (player.getPersistentData().getFloat(StatType.ATTACK_DAMAGE.name) >= 0 ? "+" : "")
                        + "%.1f".formatted(player.getPersistentData().getFloat(StatType.ATTACK_DAMAGE.name)),
                (leftPos + 21 + 8) / scale, (topPos + 47 + 2) / scale, 0xFFdecc99, false);
        pGuiGraphics.drawString(Minecraft.getInstance().font,
                (player.getPersistentData().getFloat(StatType.KNOCKBACK_RESISTANCE.name) >= 0 ? "+" : "")
                        + "%.1f".formatted(player.getPersistentData().getFloat(StatType.KNOCKBACK_RESISTANCE.name)),
                (leftPos + 47 + 8) / scale, (topPos + 47 + 2) / scale, 0xFFdecc99, false);
        pGuiGraphics.drawString(Minecraft.getInstance().font,
                (player.getPersistentData().getFloat(StatType.LUCK.name) >= 0 ? "+" : "")
                        + "%.1f".formatted(player.getPersistentData().getFloat(StatType.LUCK.name)),
                (leftPos + 73 + 8) / scale, (topPos + 47 + 2) / scale, 0xFFdecc99, false);

        pGuiGraphics.drawString(Minecraft.getInstance().font, String.format("%.1f sec", player.getPersistentData().getInt("cool_time") / 20f), (leftPos + 55) / scale, (topPos + 62) / scale, 0xFFdecc99, false);

        pGuiGraphics.pose().popPose();

        // STUFF IMAGE & TOOLTIPS

        if (trashCanButton != null) {
            removeWidget(trashCanButton);
        }

        player.getCapability(MyStuffProvider.MY_STUFF).ifPresent(myStuff -> {
            // ACTIVE STUFF
            for (int i = 0; i < myStuff.myActiveStuffIds.length; i++) {
                int id = myStuff.myActiveStuffIds[i];
                int level = myStuff.myActiveStuffLevels[i];
                if (id == 0) {
                    break;
                }
                int pX = leftPos + 21 + (i % 7) * 15;
                int pY = topPos + 69;
                // STUFF IMAGE & LEVEL
                pGuiGraphics.blit(AllOfStuff.getResources(id), pX, pY, 0, 0, 12, 12, 12, 12);
                pGuiGraphics.blit(levelResource, pX + 8, pY + 7, (level - 1) * 5, 0, 5, 6, 25, 6);

                // STUFF TOOLTIP
                if (pX < pMouseX && pMouseX < pX + 16 && pY < pMouseY && pMouseY < pY + 16) {
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    String name = I18n.get("item." + Main.MOD_ID + "." + stuff);

                    if (!removeMode) {
                        Component[] components = {Component.literal("id: " + id),
                                Component.literal(name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")").withStyle(stuff.rank.color),
                                Component.literal("level: " + level)};
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(components), pMouseX, pMouseY);
                    } else {
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip." + Main.MOD_ID + ".remove")) , pMouseX, pMouseY);
                    }
                }
            }
            // PASSIVE STUFF
            for (int i = 0; i < myStuff.myPassiveStuffIds.length; i++) {
                int id = myStuff.myPassiveStuffIds[i];
                int level = myStuff.myPassiveStuffLevels[i];
                if (id == 0) {
                    break;
                }
                int pX = leftPos + 21 + (i % 7) * 15;
                int pY = topPos + 97 + (i / 7) * 15;
                // STUFF IMAGE & LEVEL
                pGuiGraphics.blit(AllOfStuff.getResources(id), pX, pY, 0, 0, 12, 12, 12, 12);
                pGuiGraphics.blit(levelResource, pX + 8, pY + 7, (level - 1) * 5, 0, 5, 6, 25, 6);

                // STUFF TOOLTIP
                if (pX < pMouseX && pMouseX < pX + 16 && pY < pMouseY && pMouseY < pY + 16) {
                    Stuff stuff = AllOfStuff.ALL_OF_STUFF.get(id);
                    String name = I18n.get("item." + Main.MOD_ID + "." + stuff);

                    if (!removeMode) {
                        Component[] components = {Component.literal("id: " + id),
                                Component.literal(name + " (" + String.valueOf(stuff.rank).toLowerCase() + ")").withStyle(stuff.rank.color),
                                Component.literal("level: " + level)};
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(components), pMouseX, pMouseY);
                    } else {
                        pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(Component.translatable("tooltip." + Main.MOD_ID + ".remove")) , pMouseX, pMouseY);
                    }
                }
            }
            trashCanButton = new ImageButton(leftPos + 109, topPos + 156, 10, 11,
                    0, removeMode ? 193 : 181, removeMode ? 0 : 12, windowResource, 256, 256, pButton -> {
                removeMode = !removeMode;
            });
            addRenderableWidget(trashCanButton);
        });
        RenderSystem.disableBlend();
    }
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (removeMode) {
            Player player = Minecraft.getInstance().player;
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
                    ModMessages.sendToServer(new SyncMyStuffRequestC2SPacket());

                    ModMessages.sendToServer(new SyncStatRequestC2SPacket());
                    removeMode = !removeMode;
                }
            });
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == KeyBinding.MY_STUFF_KEY.getKey().getValue()) {
            this.onClose();
            return true;
        } else if (pKeyCode == KeyBinding.ACTIVE_STUFF_SWITCH_KEY.getKey().getValue()) {
            ModMessages.sendToServer(new MainActiveSwitchC2SPacket());
            ModMessages.sendToServer(new SyncMyStuffRequestC2SPacket());
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
