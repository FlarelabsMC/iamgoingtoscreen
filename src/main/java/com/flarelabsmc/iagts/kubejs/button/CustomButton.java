package com.flarelabsmc.iagts.kubejs.button;

import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class CustomButton {
    private final Consumer<Button> onPress;
    private final Supplier<MutableComponent> createNarration;
    private Component tooltip;
    private Component tooltipNarration;
    private Consumer<OnRenderWidgetCallback> onRenderWidgetPre;
    private Consumer<OnRenderWidgetCallback> onRenderWidgetPost;
    private Consumer<OnRenderStringCallback> onRenderString;
    private int width;
    private int height;
    private int x;
    private int y;
    private Component message;

    private CustomButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, Consumer<Button> onPress, Supplier<MutableComponent> createNarration) {
        this.onPress = onPress;
        this.createNarration = createNarration;
        this.width = pWidth;
        this.height = pHeight;
        this.setX(pX);
        this.setY(pY);
    }

    @Info("""
        Creates a new CustomButton instance. Used in the WidgetBuilder in the `addCustomButton` method.
        """)
    public static CustomButton create(int pX, int pY, int pWidth, int pHeight, Component pMessage, Consumer<Button> onPress, Supplier<MutableComponent> createNarration) {
        return new CustomButton(pX, pY, pWidth, pHeight, pMessage, onPress, createNarration);
    }

    public CustomButton onRenderWidgetPre(Consumer<OnRenderWidgetCallback> onRenderWidget) {
        this.onRenderWidgetPre = onRenderWidget;
        return this;
    }

    public CustomButton onRenderWidgetPost(Consumer<OnRenderWidgetCallback> onRenderWidget) {
        this.onRenderWidgetPost = onRenderWidget;
        return this;
    }

    public CustomButton onRenderString(Consumer<OnRenderStringCallback> onRenderString) {
        this.onRenderString = onRenderString;
        return this;
    }

    @Info("""
        Sets the tooltip for the button. This will be displayed when the button is hovered over, and has a secondary parameter for the narrator to read out the tooltip.
        `tooltipNarration` can be null.
        """)
    public CustomButton setTooltip(Component tooltip, Component tooltipNarration) {
        this.tooltip = tooltip;
        this.tooltipNarration = tooltipNarration;
        return this;
    }

    public CustomButton setWidth(int width) {
        this.width = width;
        return this;
    }

    public CustomButton setHeight(int height) {
        this.height = height;
        return this;
    }

    public CustomButton setX(int x) {
        this.x = x;
        return this;
    }

    public CustomButton setY(int y) {
        this.y = y;
        return this;
    }

    public CustomButton setMessage(Component message) {
        this.message = message;
        return this;
    }

    public Component getMessage() {
        return message;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Info("""
        Used by the WidgetBuilder to add a custom button to the screen. Not necessary to use this method directly.
    """)
    public Button build() {
        return new Button(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getMessage(), onPress::accept, (s) -> createNarration.get()) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                if (onRenderWidgetPre != null) {
                    onRenderWidgetPre.accept(new OnRenderWidgetCallback(guiGraphics, mouseX, mouseY, partialTicks, this));
                }
                super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
                if (onRenderWidgetPost != null) {
                    onRenderWidgetPost.accept(new OnRenderWidgetCallback(guiGraphics, mouseX, mouseY, partialTicks, this));
                }
            }

            @Override
            public void renderString(GuiGraphics guiGraphics, Font font, int getColor) {
                if (onRenderString != null) {
                    OnRenderStringCallback callback = new OnRenderStringCallback(guiGraphics, font, getColor);
                    onRenderString.accept(callback);
                    if (callback.cancelled) {
                        return;
                    }
                }
                super.renderString(guiGraphics, font, getColor);
            }

            @Override
            public Tooltip getTooltip() {
                if (tooltip != null) {
                    return Tooltip.create(tooltip, tooltipNarration);
                }
                return super.getTooltip();
            }
        };
    };

    public static class OnRenderStringCallback {
        public transient boolean cancelled = false;
        public transient final GuiGraphics guiGraphics;
        public transient final Font font;
        public transient final int color;

        public OnRenderStringCallback(GuiGraphics getGuiGraphics, Font getFont, int getColor) {
            this.guiGraphics = getGuiGraphics;
            this.font = getFont;
            this.color = getColor;
        }

        public void cancelOriginal() {
            this.cancelled = true;
        }
    };

    public record OnRenderWidgetCallback(GuiGraphics getGuiGraphics, int getMouseX, int getMouseY, float getPartialTicks, Button getButton) {}
}
