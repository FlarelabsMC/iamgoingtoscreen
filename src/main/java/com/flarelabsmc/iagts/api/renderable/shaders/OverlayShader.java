package com.flarelabsmc.iagts.api.renderable.shaders;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public interface OverlayShader {
    void setOverlayShader(ResourceLocation shader);
    ResourceLocation getOverlayShader();

    record Extra(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, ManagedShaderEffect shader) {}
}