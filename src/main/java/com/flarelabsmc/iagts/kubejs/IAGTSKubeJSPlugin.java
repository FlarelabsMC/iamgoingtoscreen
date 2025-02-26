package com.flarelabsmc.iagts.kubejs;

import com.flarelabsmc.iagts.internal.Alignment;
import com.flarelabsmc.iagts.internal.Anchor;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraft.client.renderer.CubeMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class IAGTSKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent event) {
        if (event.getType().equals(ScriptType.CLIENT) && FMLEnvironment.dist == Dist.CLIENT) {
            addClientBindings(event);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void addClientBindings(BindingsEvent event) {
        event.add("ScreenBuilder", ScreenBuilder.class);
        event.add("Alignment", Alignment.class);
        event.add("Anchor", Anchor.class);
        event.add("WidgetBuilder", WidgetBuilderWrapper.class);
        event.add("CubeMap", CubeMap.class);
        event.add("RenderSystem", RenderSystem.class);
    }
}