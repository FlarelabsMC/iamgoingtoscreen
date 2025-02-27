package com.flarelabsmc.iagts.api;

import com.flarelabsmc.iagts.api.button.ImageButton;
import com.flarelabsmc.iagts.api.button.NineSliceImageButton;
import com.flarelabsmc.iagts.api.renderable.ImageRenderable;
import com.flarelabsmc.iagts.api.renderable.LayerManager;
import com.flarelabsmc.iagts.api.renderable.ShaderRenderable;
import com.flarelabsmc.iagts.api.renderable.TextRenderable;
import com.flarelabsmc.iagts.api.renderable.shaders.OverlayShader;
import com.flarelabsmc.iagts.internal.Alignment;
import com.flarelabsmc.iagts.internal.Anchor;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.*;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class WidgetBuilder {
    public final Screen screen;
    protected final LayerManager layerManager = new LayerManager();

    public WidgetBuilder(Screen screen) {
        this.screen = screen;
    }

    private int calculateAnchorX(int x, int width, Anchor anchor) {
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        return switch (anchor) {
            case TOP_LEFT, LEFT, BOTTOM_LEFT -> x;
            case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> screenWidth - x - width;
            case TOP, CENTER, BOTTOM -> (screenWidth - width) / 2 + x;
        };
    }

    private int calculateAnchorY(int y, int height, Anchor anchor) {
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        return switch (anchor) {
            case TOP_LEFT, TOP, TOP_RIGHT -> y;
            case BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT -> screenHeight - y - height;
            case LEFT, CENTER, RIGHT -> (screenHeight - height) / 2 + y;
        };
    }

    public WidgetBuilder addButton(int layer, Vector3dc pos, Vector2dc size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        addButton((int) pos.x(), (int) pos.y(), (int) size.x(), (int) size.y(), text, onPress, anchor);
        return this;
    }

    public WidgetBuilder addButton(int layer, Vector3dc pos, int size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        addButton((int) pos.x(), (int) pos.y(), size, size, text, onPress, anchor);
        return this;
    }

    public WidgetBuilder addButton(int layer, int x, int y, int size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        addButton(x, y, size, size, text, onPress, anchor);
        return this;
    }

    public WidgetBuilder addButton(int layer, int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        Button button = Button.builder(text != null ? text : Component.empty(), onPress::accept)
                .bounds(anchoredX, anchoredY, width, height)
                .build();
        this.screen.addWidget(button);
        this.layerManager.addRenderable(layer, button);
        return this;
    }

    public WidgetBuilder addImageButton(int layer, int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        ImageButton button = new ImageButton(anchoredX, anchoredY, width, height, text != null ? text : Component.empty(), onPress::accept, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight);
        this.screen.addWidget(button);
        this.layerManager.addRenderable(layer, button);
        return this;
    }

    public WidgetBuilder addNineSliceImageButton(int layer, int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, int sliceWidth, int sliceHeight, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        NineSliceImageButton button = new NineSliceImageButton(anchoredX, anchoredY, width, height, text != null ? text : Component.empty(), onPress::accept, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight, sliceWidth, sliceHeight);
        this.screen.addWidget(button);
        this.layerManager.addRenderable(layer, button);
        return this;
    }

    public WidgetBuilder addTexture(int layer, ResourceLocation texture, Vector3dc pos, Vector2dc size, Anchor anchor) {
        return addTexture(layer, texture, (int) pos.x(), (int) pos.y(), (int) size.x(), (int) size.y(), anchor);
    }

    public WidgetBuilder addTexture(int layer, ResourceLocation texture, Vector3dc pos, int size, Anchor anchor) {
        return addTexture(layer, texture, (int) pos.x(), (int) pos.y(), size, size, anchor);
    }

    public WidgetBuilder addTexture(int layer, ResourceLocation texture, int x, int y, int size, Anchor anchor) {
        return addTexture(layer, texture, x, y, size, size, anchor);
    }

    public WidgetBuilder addTexture(int layer, ResourceLocation texture, int x, int y, int width, int height, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        ImageRenderable image = new ImageRenderable(texture, anchoredX, anchoredY, width, height);
        this.layerManager.addRenderable(layer, image);
        return this;
    }

    public WidgetBuilder removeWidget(GuiEventListener widget) {
        this.screen.removeWidget(widget);
        return this;
    }

    public WidgetBuilder removeButton(int index) {
        Button button = this.screen.children().stream().filter(Button.class::isInstance).map(Button.class::cast).toList().get(index);
        this.screen.removeWidget(button);
        return this;
    }

    public WidgetBuilder removeRenderable(int index) {
        Renderable renderable = this.screen.renderables.get(index);
        this.layerManager.getLayers().values().forEach(renderables -> renderables.remove(renderable));
        return this;
    }

    public WidgetBuilder removeRenderable(int layer, int index) {
        List<Renderable> renderables = this.layerManager.getRenderables(layer);
        if (renderables != null && index >= 0 && index < renderables.size()) {
            renderables.remove(index);
        }
        return this;
    }

    public WidgetBuilder addText(int layer, List<Component> text, int x, int y, float lineSpacing, Anchor anchor) {
        return addText(layer, text, x, y, 0, 0, anchor, Alignment.LEFT, false, lineSpacing);
    }

    public WidgetBuilder addText(int layer, List<Component> text, int x, int y, Anchor anchor, Alignment alignment, float lineSpacing) {
        return addText(layer, text, x, y, 0, 0, anchor, alignment, false, lineSpacing);
    }

    public WidgetBuilder addText(int layer, List<Component> text, int x, int y, int w, int h, Anchor anchor, Alignment alignment, boolean wrap, float lineSpacing) {
        int anchoredX = calculateAnchorX(x, w, anchor);
        int anchoredY = calculateAnchorY(y, 0, anchor);
        this.layerManager.addRenderable(layer, new TextRenderable(text, anchoredX, anchoredY, w, alignment, wrap, lineSpacing));
        return this;
    }

    public WidgetBuilder addShader(int layer, ResourceLocation shader, Consumer<ManagedShaderEffect> uniforms, Consumer<OverlayShader.Extra> whileRendering) {
        ShaderRenderable shaderRenderable = new ShaderRenderable(shader, uniforms, whileRendering);
        this.layerManager.addRenderable(layer, shaderRenderable);
        return this;
    }

    public List<? extends GuiEventListener> getWidgets() {
        return this.screen.children();
    }

    public List<Renderable> getRenderables(int layer) {
        return this.layerManager.getRenderables(layer);
    }

    public void pushLayers() {
        this.layerManager.getLayers().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> this.screen.renderables.addAll(entry.getValue()));
    }

    public List<Button> getButtons() {
        return this.screen.children().stream().filter(Button.class::isInstance).map(Button.class::cast).toList();
    }

    public List<ImageRenderable> getImages(int layer) {
        return this.layerManager.getRenderables(layer).stream().filter(ImageRenderable.class::isInstance).map(ImageRenderable.class::cast).toList();
    }

    public int getLayerCount() {
        return this.layerManager.getLayerCount();
    }
}