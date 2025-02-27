package com.flarelabsmc.iagts.api.renderable;

import com.flarelabsmc.iagts.api.renderable.shaders.OverlayShader;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ShaderRenderable implements Renderable, OverlayShader {
    private ResourceLocation shaderLocation;
    protected final ManagedShaderEffect shader;
    private final Consumer<Extra> whileRendering;

    public ShaderRenderable(ResourceLocation shader, @Nullable Consumer<ManagedShaderEffect> uniforms, @Nullable Consumer<Extra> whileRendering) {
        this.shaderLocation = shader;
        this.shader = ShaderEffectManager.getInstance().manage(shader, uniforms != null ? uniforms : (s) -> {});
        this.whileRendering = whileRendering;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int i1, float v) {
        if (whileRendering != null) whileRendering.accept(new Extra(guiGraphics, i, i1, v, shader));
        shader.render(v);
    }

    @Override
    public void setOverlayShader(ResourceLocation shader) {
        this.shaderLocation = shader;
    }

    @Override
    public ResourceLocation getOverlayShader() {
        return shaderLocation;
    }
}
