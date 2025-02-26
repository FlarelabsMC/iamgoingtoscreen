package com.flarelabsmc.iagts.api.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class NineSliceImageButton extends ImageButton {
    protected final int sliceWidth;
    protected final int sliceHeight;

    public NineSliceImageButton(int x, int y, int width, int height, Component message, OnPress onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, int sliceWidth, int sliceHeight) {
        super(x, y, width, height, message, onPress, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight);
        this.sliceWidth = sliceWidth;
        this.sliceHeight = sliceHeight;
    }


    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        if (this.isHovered()) {
            guiGraphics.blitNineSliced(texture, this.getX(), this.getY(), width, height, sliceWidth, sliceHeight, uWidth, vHeight, hoverTextureX, hoverTextureY);
        } else {
            guiGraphics.blitNineSliced(texture, this.getX(), this.getY(), width, height, sliceWidth, sliceHeight, uWidth, vHeight, textureX, textureY);
        }

        int color = this.getMessage().getStyle().getColor() != null ? this.getMessage().getStyle().getColor().getValue() : 0xFFFFFF;
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color);
    }
}