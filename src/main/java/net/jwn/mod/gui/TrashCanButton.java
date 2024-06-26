package net.jwn.mod.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;

public class TrashCanButton extends ImageButton {
    protected int myXTexStart;

    public TrashCanButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffTex, ResourceLocation pResourceLocation, int pTextureWidth, int pTextureHeight, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffTex, pResourceLocation, pTextureWidth, pTextureHeight, pOnPress, CommonComponents.EMPTY);
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (isHoveredOrFocused()) {
            myXTexStart = 56;
        } else {
            myXTexStart = this.xTexStart;
        }
        this.renderTexture(pGuiGraphics, this.resourceLocation, this.getX(), this.getY(), this.myXTexStart, this.yTexStart, this.yDiffTex, this.width, this.height, this.textureWidth, this.textureHeight);
    }
}
