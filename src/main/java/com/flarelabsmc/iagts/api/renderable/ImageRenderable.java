package com.flarelabsmc.iagts.api.renderable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;

public class ImageRenderable implements Renderable {
    private final ResourceLocation texture;
    private final int x, y, w, h;

    public ImageRenderable(ResourceLocation texture, int x, int y, int w, int h) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int i1, float v) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blit(texture, x, y, 0, 0, w, h, w, h);
    }
}
