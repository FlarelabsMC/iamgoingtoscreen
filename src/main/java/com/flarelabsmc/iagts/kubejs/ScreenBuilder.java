package com.flarelabsmc.iagts.kubejs;

import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@Info("""
        A Screen builder that allows for KubeJS scripts to create new screens.
        Does not need to be placed in a startup script. Rather, place it in client scripts. Instantiate it in a variable and run build() to create a new screen.
        """)
public class ScreenBuilder {
    private final Component title;
    private boolean shouldCloseOnEsc = true;
    private boolean isPauseScreen = true;
    private Music backgroundMusic;
    private Consumer<Screen> initCallback;
    private Consumer<Screen> onCloseCallback;
    private Consumer<KeyPressedCallback> keyPressedCallback;
    private Consumer<Screen> tickCallback;
    private Consumer<Screen> removedCallback;
    private Consumer<Screen> addedCallback;
    private Consumer<RenderBackgroundCallback> renderBackgroundCallback;
    private Consumer<Screen> afterMouseMoveCallback;
    private Consumer<Screen> afterMouseActionCallback;
    private Consumer<Screen> afterKeyboardActionCallback;
    private Consumer<RenderCallback> renderCallback;

    private ScreenBuilder(Component title) {
        this.title = title;
    }

    @Info("""
            Creates a new ScreenBuilder with the given title.
            """)
    public static ScreenBuilder create(Component title) {
        return new ScreenBuilder(title);
    }

    @Info("""
            This is called when the screen is being rendered. It can be used for special rendering, but is not necessary for most screens.
            """)
    public ScreenBuilder onRender(Consumer<RenderCallback> renderCallback) {
        this.renderCallback = renderCallback;
        return this;
    }

    @Info("""
            This is called when the screen's background is being rendered. You can replace the background with a custom image, or keep it empty.
            """)
    public ScreenBuilder onRenderBackground(Consumer<RenderBackgroundCallback> renderBackgroundCallback) {
        this.renderBackgroundCallback = renderBackgroundCallback;
        return this;
    }

    @Info("""
            Sets the background music for the screen.
            """)
    public ScreenBuilder setBackgroundMusic(SoundEvent music, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        this.backgroundMusic = new Music(Holder.direct(music), minDelay, maxDelay, replaceCurrentMusic);
        return this;
    }

    @Info("""
            Called on initialization of the screen. Mostly used for adding initial widgets, images, etc.
            """)
    public ScreenBuilder onInit(Consumer<Screen> init) {
        this.initCallback = init;
        return this;
    }

    @Info("""
            Sets whether the screen can close when the escape key is pressed. Default is true.
            """)
    public ScreenBuilder shouldCloseOnEsc(boolean shouldClose) {
        this.shouldCloseOnEsc = shouldClose;
        return this;
    }

    @Info("""
            Called when the screen is closed. Mostly used for cleaning up resources, or saving data.
            """)
    public ScreenBuilder onClose(Consumer<Screen> onClose) {
        this.onCloseCallback = onClose;
        return this;
    }

    @Info("""
            Called when a key is pressed.
            """)
    public ScreenBuilder onKeyPressed(Consumer<KeyPressedCallback> onKeyPressed) {
        this.keyPressedCallback = onKeyPressed;
        return this;
    }

    @Info("""
            Called on every client tick while the screen is open.
            """)
    public ScreenBuilder onTick(Consumer<Screen> onTick) {
        this.tickCallback = onTick;
        return this;
    }

    @Info("""
            Whether the screen is a pause screen or not. If true, the game will be paused when the screen is open.
            """)
    public ScreenBuilder setIsPauseScreen(boolean isPauseScreen) {
        this.isPauseScreen = isPauseScreen;
        return this;
    }

    public ScreenBuilder onRemoved(Consumer<Screen> onRemoved) {
        this.removedCallback = onRemoved;
        return this;
    }

    public ScreenBuilder onAdded(Consumer<Screen> onAdded) {
        this.addedCallback = onAdded;
        return this;
    }

    @Info("""
            Called when the user's mouse moves while the screen is open.
            """)
    public ScreenBuilder afterMouseMove(Consumer<Screen> afterMouseMove) {
        this.afterMouseMoveCallback = afterMouseMove;
        return this;
    }

    @Info("""
            Called when the user has clicked one of their mouse buttons while the screen is open.
            """)
    public ScreenBuilder afterMouseAction(Consumer<Screen> afterMouseAction) {
        this.afterMouseActionCallback = afterMouseAction;
        return this;
    }

    @Info("""
            Called when the user has pressed one of their keyboard keys while the screen is open.
            """)
    public ScreenBuilder afterKeyboardAction(Consumer<Screen> afterKeyboardAction) {
        this.afterKeyboardActionCallback = afterKeyboardAction;
        return this;
    }

    @Info("""
            Builds the screen. Final step in the process. Returns a new Screen object.
            """)
    public Screen build() {
        return new Screen(title) {
            @Override
            protected void init() {
                super.init();
                if (initCallback != null) {
                    initCallback.accept(this);
                }
            }

            @Override
            public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                if (renderCallback != null) {
                    renderCallback.accept(new RenderCallback(this, pGuiGraphics));
                }
                super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            }

            @Override
            public @Nullable Music getBackgroundMusic() {
                return backgroundMusic != null ? backgroundMusic : super.getBackgroundMusic();
            }

            @Override
            public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
                if (keyPressedCallback != null) {
                    keyPressedCallback.accept(new KeyPressedCallback(this, pKeyCode, pScanCode, pModifiers));
                }
                return super.keyPressed(pKeyCode, pScanCode, pModifiers);
            }

            @Override
            public boolean shouldCloseOnEsc() {
                return shouldCloseOnEsc;
            }

            @Override
            public void onClose() {
                super.onClose();
                if (onCloseCallback != null) {
                    onCloseCallback.accept(this);
                }
            }

            @Override
            public void tick() {
                super.tick();
                if (tickCallback != null) {
                    tickCallback.accept(this);
                }
            }

            @Override
            public void removed() {
                super.removed();
                if (removedCallback != null) {
                    removedCallback.accept(this);
                }
            }

            @Override
            public void added() {
                super.added();
                if (addedCallback != null) {
                    addedCallback.accept(this);
                }
            }

            @Override
            public void renderBackground(GuiGraphics pGuiGraphics) {
                if (renderBackgroundCallback != null) {
                    renderBackgroundCallback.accept(new RenderBackgroundCallback(this, pGuiGraphics));
                    return;
                }
                super.renderBackground(pGuiGraphics);
            }

            @Override
            public boolean isPauseScreen() {
                return isPauseScreen;
            }

            @Override
            public void afterMouseMove() {
                super.afterMouseMove();
                if (afterMouseMoveCallback != null) {
                    afterMouseMoveCallback.accept(this);
                }
            }

            @Override
            public void afterMouseAction() {
                super.afterMouseAction();
                if (afterMouseActionCallback != null) {
                    afterMouseActionCallback.accept(this);
                }
            }

            @Override
            public void afterKeyboardAction() {
                super.afterKeyboardAction();
                if (afterKeyboardActionCallback != null) {
                    afterKeyboardActionCallback.accept(this);
                }
            }

            @Override
            protected boolean shouldNarrateNavigation() {
                return super.shouldNarrateNavigation();
            }
        };
    }

    public record KeyPressedCallback(Screen getScreen, int getKeyCode, int getScanCode, int getModifiers) {}

    public record RenderCallback(Screen getScreen, GuiGraphics getGuiGraphics) {}

    public record RenderBackgroundCallback(Screen getScreen, GuiGraphics getGuiGraphics) {}
}