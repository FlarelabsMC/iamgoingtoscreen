package com.flarelabsmc.iagts.kubejs.event;

import dev.latvian.mods.kubejs.bindings.event.ClientEvents;
import dev.latvian.mods.kubejs.event.EventHandler;

public class IAGTSJSEvents {
    public static final EventHandler OPEN_SCREEN = ClientEvents.GROUP.client("screenOpened", () -> OpenScreenEvent.class);

    public static void init() {}
}
