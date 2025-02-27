package com.flarelabsmc.iagts.kubejs;

import com.flarelabsmc.iagts.api.WidgetBuilder;
import com.flarelabsmc.iagts.api.renderable.ImageRenderable;
import com.flarelabsmc.iagts.api.renderable.shaders.OverlayShader;
import com.flarelabsmc.iagts.internal.Alignment;
import com.flarelabsmc.iagts.internal.Anchor;
import com.flarelabsmc.iagts.kubejs.button.CustomButton;
import dev.latvian.mods.kubejs.typings.Info;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@Info("""
        A wrapper for the WidgetBuilder class, which allows for easier use of the IAGTS API in KubeJS scripts.
        It can be used to modify screens live, or to create new screens.
        """)
public class WidgetBuilderWrapper {
    private final WidgetBuilder instance;

    private WidgetBuilderWrapper(WidgetBuilder instance) {
        this.instance = instance;
    }

    public static WidgetBuilderWrapper of(Screen screen) {
        return new WidgetBuilderWrapper(new WidgetBuilder(screen));
    }

    @Info("""
            Adds a button to the screen. Uses default widget texture.
            """)
    public WidgetBuilderWrapper addButton(int layer, int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        instance.addButton(layer, x, y, width, height, text, onPress, anchor);
        return this;
    }

    @Info("""
            Adds a button to the screen, with a custom texture.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilderWrapper addImageButton(int layer, int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, Anchor anchor) {
        instance.addImageButton(layer, x, y, width, height, text, onPress, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight, anchor);
        return this;
    }

    @Info("""
            Adds a button to the screen, with a custom nine-slice texture.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilderWrapper addNineSliceImageButton(int layer, int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, int sliceWidth, int sliceHeight, Anchor anchor) {
        instance.addNineSliceImageButton(layer, x, y, width, height, text, onPress, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight, sliceWidth, sliceHeight, anchor);
        return this;
    }

    public WidgetBuilderWrapper addCustomButton(CustomButton button) {
        instance.screen.addRenderableWidget(button.build());
        return this;
    }

    @Info("""
            Adds an image to the screen.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilderWrapper addTexture(int layer, ResourceLocation texture, int x, int y, int width, int height, Anchor anchor) {
        instance.addTexture(layer, texture, x, y, width, height, anchor);
        return this;
    }

    @Info("""
            Adds text to the screen.
            """)
    public WidgetBuilderWrapper addText(int layer, List<Component> text, int x, int y, int w, int h, Anchor anchor, Alignment alignment, boolean wrap, float lineSpacing) {
        instance.addText(layer, text, x, y, w, h, anchor, alignment, wrap, lineSpacing);
        return this;
    }

    @Info("""
            Adds a post-processing shader to the screen.
            """)
    public WidgetBuilderWrapper addShader(int layer, ResourceLocation shader, Consumer<ManagedShaderEffect> uniforms, Consumer<OverlayShader.Extra> whileRendering) {
        instance.addShader(layer, shader, uniforms, whileRendering);
        return this;
    }

    @Info("""
            Removes a widget from the screen.
            """)
    public WidgetBuilderWrapper removeWidget(GuiEventListener widget) {
        instance.removeWidget(widget);
        return this;
    }

    @Info("""
            Removes a button from the screen.
            """)
    public WidgetBuilderWrapper removeButton(int index) {
        instance.removeButton(index);
        return this;
    }

    @Info("""
            Removes a renderable from the screen.
            """)
    public WidgetBuilderWrapper removeRenderable(int index) {
        instance.removeRenderable(index);
        return this;
    }

    @Info("""
            Removes a renderable from a specific layer.
            """)
    public WidgetBuilderWrapper removeRenderable(int layer, int index) {
        instance.removeRenderable(layer, index);
        return this;
    }

    @Info("""
            Returns a list of ALL widgets on the screen.
            """)
    public List<? extends GuiEventListener> getWidgets() {
        return instance.getWidgets();
    }

    @Info("""
            Returns a list of ALL renderables on the screen.
            """)
    public List<Renderable> getRenderables(int layer) {
        return instance.getRenderables(layer);
    }

    @Info("""
            Returns a list of ALL buttons (anything instanceof Button) on the screen.
            """)
    public List<Button> getButtons() {
        return instance.getButtons();
    }

    @Info("""
            Returns a list of all images on the screen.
            """)
    public List<ImageRenderable> getImages(int layer) {
        return instance.getImages(layer);
    }

    @Info("""
            Pushes all layers to the screen. Use this after adding all renderables to the screen.
            """)
    public void pushLayers() {
        instance.pushLayers();
    }

    @Info("""
            Returns the number of layers.
            """)
    public int getLayerCount() {
        return instance.getLayerCount();
    }
}