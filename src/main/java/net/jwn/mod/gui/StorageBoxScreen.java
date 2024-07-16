package net.jwn.mod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jwn.mod.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class StorageBoxScreen extends AbstractContainerScreen<StorageBoxMenu> {
    Player player;
    private static final ResourceLocation resourceLocation =
            new ResourceLocation(Main.MOD_ID, "textures/gui/storage_box.png");
    public StorageBoxScreen(StorageBoxMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        player = pPlayerInventory.player;
    }
    @Override
    protected void init() {
        super.init();
        this.leftPos = (width - 176) / 2;
        this.topPos = (height - 166) / 2;
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, resourceLocation);
        guiGraphics.blit(resourceLocation, leftPos, topPos, 0, 0, 176, 166);
    }
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
