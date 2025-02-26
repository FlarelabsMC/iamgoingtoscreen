package com.flarelabsmc.iagts.kubejs;

import com.flarelabsmc.iagts.api.WidgetBuilder;
import com.flarelabsmc.iagts.api.renderable.ImageRenderable;
import com.flarelabsmc.iagts.internal.Alignment;
import com.flarelabsmc.iagts.internal.Anchor;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2dc;
import org.joml.Vector3dc;

import java.util.List;
import java.util.function.Consumer;

@Info("""
        A wrapper for the WidgetBuilder class, which allows for easier use of the IAGTS API in KubeJS scripts.
        It can be used to modify screens live, or to create new screens.
        """)
public class WidgetBuilderWrapper extends WidgetBuilder {
    private WidgetBuilderWrapper(Screen screen) {
        super(screen);
    }

    public static WidgetBuilder of(Screen screen) {
        return new WidgetBuilderWrapper(screen);
    }

    @Info("""
            Adds a button to the screen. Uses default widget texture.
            """)
    public WidgetBuilder addButton(Vector3dc pos, Vector2dc size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        return super.addButton(pos, size, text, onPress, anchor);
    }

    @Info("""
            Adds a button to the screen. Uses default widget texture.
            """)
    public WidgetBuilder addButton(Vector3dc pos, int size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        return super.addButton(pos, size, text, onPress, anchor);
    }

    @Info("""
            Adds a button to the screen. Uses default widget texture.
            """)
    public WidgetBuilder addButton(int x, int y, int size, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        return super.addButton(x, y, size, text, onPress, anchor);
    }

    @Info("""
            Adds a button to the screen. Uses default widget texture.
            """)
    public WidgetBuilder addButton(int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, Anchor anchor) {
        return super.addButton(x, y, width, height, text, onPress, anchor);
    }

    @Info("""
            Adds a button to the screen, with a custom texture.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilder addImageButton(int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, Anchor anchor) {
        return super.addImageButton(x, y, width, height, text, onPress, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight, anchor);
    }

    @Info("""
            Adds a button to the screen, with a custom nine-slice texture.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilder addNineSliceImageButton(int x, int y, int width, int height, @Nullable Component text, Consumer<Button> onPress, ResourceLocation texture, int textureWidth, int textureHeight, int textureX, int textureY, int hoverTextureX, int hoverTextureY, int uWidth, int vHeight, int sliceWidth, int sliceHeight, Anchor anchor) {
        return super.addNineSliceImageButton(x, y, width, height, text, onPress, texture, textureWidth, textureHeight, textureX, textureY, hoverTextureX, hoverTextureY, uWidth, vHeight, sliceWidth, sliceHeight, anchor);
    }

    @Info("""
            Adds an image to the screen.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilder addTexture(ResourceLocation texture, Vector3dc pos, Vector2dc size, Anchor anchor) {
        return super.addTexture(texture, pos, size, anchor);
    }

    @Info("""
            Adds an image to the screen.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilder addTexture(ResourceLocation texture, Vector3dc pos, int size, Anchor anchor) {
        return super.addTexture(texture, pos, size, anchor);
    }

    @Info("""
            Adds an image to the screen.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilder addTexture(ResourceLocation texture, int x, int y, int size, Anchor anchor) {
        return super.addTexture(texture, x, y, size, anchor);
    }

    @Info("""
            Adds an image to the screen.
            Register a texture using ClientEvents.atlasSpriteRegistry() to use this with a custom texture.
            """)
    public WidgetBuilder addTexture(ResourceLocation texture, int x, int y, int width, int height, Anchor anchor) {
        return super.addTexture(texture, x, y, width, height, anchor);
    }

    @Info("""
            Removes a widget from the screen.
            """)
    public WidgetBuilder removeWidget(GuiEventListener widget) {
        return super.removeWidget(widget);
    }

    @Info("""
            Removes a button from the screen.
            """)
    public WidgetBuilder removeButton(int index) {
        return super.removeButton(index);
    }

    @Info("""
            Removes a renderable from the screen.
            """)
    public WidgetBuilder removeRenderable(int index) {
        return super.removeRenderable(index);
    }

    @Info("""
            Adds text to the screen.
            """)
    public WidgetBuilder addText(List<Component> text, int x, int y, float lineSpacing, Anchor anchor) {
        return super.addText(text, x, y, lineSpacing, anchor);
    }

    @Info("""
            Adds text to the screen.
            """)
    public WidgetBuilder addText(List<Component> text, int x, int y, Anchor anchor, Alignment alignment, float lineSpacing) {
        return super.addText(text, x, y, anchor, alignment, lineSpacing);
    }

    @Info("""
            Adds text to the screen.
            """)
    public WidgetBuilder addText(List<Component> text, int x, int y, int w, int h, Anchor anchor, Alignment alignment, boolean wrap, float lineSpacing) {
        return super.addText(text, x, y, w, h, anchor, alignment, wrap, lineSpacing);
    }

    @Info("""
            Returns a list of ALL widgets on the screen.
            """)
    public List<? extends GuiEventListener> getWidgets() {
        return super.getWidgets();
    }

    @Info("""
            Returns a list of ALL renderables on the screen.
            """)
    public List<Renderable> getRenderables() {
        return super.getRenderables();
    }

    @Info("""
            Returns a list of ALL buttons (anything instanceof Button) on the screen.
            """)
    public List<Button> getButtons() {
        return super.getButtons();
    }

    @Info("""
            Returns a list of all images on the screen.
            """)
    public List<ImageRenderable> getImages() {
        return super.getImages();
    }
}