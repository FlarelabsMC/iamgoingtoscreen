package com.flarelabsmc.iagts.kubejs.event;

import dev.latvian.mods.kubejs.client.ClientEventJS;
import net.minecraft.client.gui.screens.Screen;

@SuppressWarnings("unused")
public class OpenScreenEvent extends ClientEventJS {
    private final Screen screen;
    private Screen newScreen;

    public OpenScreenEvent(Screen screen, Screen newScreen) {
        this.screen = screen;
        this.newScreen = newScreen;
    }

    public Screen getCurrentScreen() {
        return screen;
    }

    public void setNewScreen(Screen newScreen) {
        this.newScreen = newScreen;
    }

    public Screen getNewScreen() {
        return newScreen;
    }
}
