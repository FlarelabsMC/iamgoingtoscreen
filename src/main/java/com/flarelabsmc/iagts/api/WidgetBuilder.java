package com.flarelabsmc.iagts.api;

import com.flarelabsmc.iagts.api.button.ImageButton;
import com.flarelabsmc.iagts.api.button.NineSliceImageButton;
import com.flarelabsmc.iagts.api.renderable.ImageRenderable;
import com.flarelabsmc.iagts.api.renderable.TextRenderable;
import com.flarelabsmc.iagts.internal.Alignment;
import com.flarelabsmc.iagts.internal.Anchor;
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
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class WidgetBuilder {
    protected final Screen screen;

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

    public WidgetBuilder addButton(Vector3dc pos, Vector2dc size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        addButton((int) pos.x(), (int) pos.y(), (int) size.x(), (int) size.y(), text, onPress, anchor);
        return this;
    }

    public WidgetBuilder addButton(Vector3dc pos, int size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        addButton((int) pos.x(), (int) pos.y(), size, size, text, onPress, anchor);
        return this;
    }

    public WidgetBuilder addButton(int x, int y, int size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        addButton(x, y, size, size, text, onPress, anchor);
        return this;
    }

    public WidgetBuilder addButton(int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        this.screen.addRenderableWidget(Button.builder(text != null ? text : Component.empty(), onPress::accept)
                .bounds(anchoredX, anchoredY, width, height)
                .build());
        return this;
    }

    public WidgetBuilder addImageButton(int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        this.screen.addRenderableWidget(new ImageButton(anchoredX, anchoredY, width, height, text != null ? text : Component.empty(), onPress::accept, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight));
        return this;
    }

    public WidgetBuilder addNineSliceImageButton(int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, int sliceWidth, int sliceHeight, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        this.screen.addRenderableWidget(new NineSliceImageButton(anchoredX, anchoredY, width, height, text != null ? text : Component.empty(), onPress::accept, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight, sliceWidth, sliceHeight));
        return this;
    }

    public WidgetBuilder addTexture(ResourceLocation texture, Vector3dc pos, Vector2dc size, Anchor anchor) {
        addTexture(texture, (int) pos.x(), (int) pos.y(), (int) size.x(), (int) size.y(), anchor);
        return this;
    }

    public WidgetBuilder addTexture(ResourceLocation texture, Vector3dc pos, int size, Anchor anchor) {
        addTexture(texture, (int) pos.x(), (int) pos.y(), size, size, anchor);
        return this;
    }

    public WidgetBuilder addTexture(ResourceLocation texture, int x, int y, int size, Anchor anchor) {
        addTexture(texture, x, y, size, size, anchor);
        return this;
    }

    public WidgetBuilder addTexture(ResourceLocation texture, int x, int y, int width, int height, Anchor anchor) {
        int anchoredX = calculateAnchorX(x, width, anchor);
        int anchoredY = calculateAnchorY(y, height, anchor);
        this.screen.addRenderableOnly(new ImageRenderable(texture, anchoredX, anchoredY, width, height));
        return this;
    }

    public WidgetBuilder removeWidget(GuiEventListener widget) {
        this.screen.removeWidget(widget);
        return this;
    }

    public WidgetBuilder removeButton(int index) {
        this.screen.removeWidget(this.screen.children().get(index));
        return this;
    }

    public WidgetBuilder removeRenderable(int index) {
        this.screen.renderables.remove(index);
        return this;
    }

    public WidgetBuilder addText(List<Component> text, int x, int y, float lineSpacing, Anchor anchor) {
        addText(text, x, y, anchor, Alignment.LEFT, lineSpacing);
        return this;
    }

    public WidgetBuilder addText(List<Component> text, int x, int y, Anchor anchor, Alignment alignment, float lineSpacing) {
        addText(text, x, y, 0, 0, anchor, alignment, false, lineSpacing);
        return this;
    }

    public WidgetBuilder addText(List<Component> text, int x, int y, int w, int h, Anchor anchor, Alignment alignment, boolean wrap, float lineSpacing) {
        int anchoredX = calculateAnchorX(x, w, anchor);
        int anchoredY = calculateAnchorY(y, h, anchor);
        this.screen.addRenderableOnly(new TextRenderable(text, anchoredX, anchoredY, w, alignment, wrap, lineSpacing));
        return this;
    }

    public List<? extends GuiEventListener> getWidgets() {
        return this.screen.children();
    }

    public List<Renderable> getRenderables() {
        return this.screen.renderables;
    }

    public List<Button> getButtons() {
        return this.screen.children().stream().filter(Button.class::isInstance).map(Button.class::cast).toList();
    }

    public List<ImageRenderable> getImages() {
        return this.screen.renderables.stream().filter(ImageRenderable.class::isInstance).map(ImageRenderable.class::cast).toList();
    }
}