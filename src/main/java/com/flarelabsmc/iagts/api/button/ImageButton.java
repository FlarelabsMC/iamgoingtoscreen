package com.flarelabsmc.iagts.api.button;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImageButton extends Button {
    protected final ResourceLocation texture;
    protected final int textureWidth;
    protected final int textureHeight;
    protected final int textureX;
    protected final int textureY;
    protected final int hoverTextureX;
    protected final int hoverTextureY;
    protected final int uWidth;
    protected final int vHeight;

    public ImageButton(int x, int y, int width, int height, Component message, OnPress onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.textureX = textureX;
        this.textureY = textureY;
        this.hoverTextureX = hoverTextureX;
        this.hoverTextureY = hoverTextureY;
        this.uWidth = uWidth;
        this.vHeight = vHeight;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        if (this.isHovered()) {
            guiGraphics.blit(texture, this.getX(), this.getY(), width, height, hoverTextureX, hoverTextureY, uWidth, vHeight, textureWidth, textureHeight);
        } else {
            guiGraphics.blit(texture, this.getX(), this.getY(), width, height, textureX, textureY, uWidth, vHeight, textureWidth, textureHeight);
        }
        int color = this.getMessage().getStyle().getColor() != null ? this.getMessage().getStyle().getColor().getValue() : 0xFFFFFF;
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color);
    }
}