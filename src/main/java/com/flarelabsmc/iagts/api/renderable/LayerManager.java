package com.flarelabsmc.iagts.api.renderable;

import net.minecraft.client.gui.components.Renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayerManager {
    private final Map<Integer, List<Renderable>> layers = new HashMap<>();

    public void addRenderable(int layer, Renderable renderable) {
        layers.computeIfAbsent(layer, k -> new ArrayList<>()).add(renderable);
    }

    public void removeRenderable(int layer, Renderable renderable) {
        List<Renderable> renderables = layers.get(layer);
        if (renderables != null) {
            renderables.remove(renderable);
            if (renderables.isEmpty()) {
                layers.remove(layer);
            }
        }
    }

    public List<Renderable> getRenderables(int layer) {
        return layers.getOrDefault(layer, new ArrayList<>());
    }

    public List<Renderable> getAllRenderables() {
        List<Renderable> allRenderables = new ArrayList<>();
        layers.values().forEach(allRenderables::addAll);
        return allRenderables;
    }

    public Map<Integer, List<Renderable>> getLayers() {
        return layers;
    }

    public int getLayerCount() {
        return layers.size();
    }
}