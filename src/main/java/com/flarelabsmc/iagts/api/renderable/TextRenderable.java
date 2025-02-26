package com.flarelabsmc.iagts.api.renderable;

import com.flarelabsmc.iagts.internal.Alignment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class TextRenderable implements Renderable {
    private final List<Component> text;
    private final int x, y, w;
    private final Alignment alignment;
    private final boolean wrap;
    private final float lineSpacing;

    public TextRenderable(List<Component> text, int x, int y, int w, Alignment alignment, boolean wrap, float lineSpacing) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.alignment = alignment;
        this.wrap = wrap;
        this.lineSpacing = lineSpacing;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int i1, float v) {
        Minecraft mc = Minecraft.getInstance();

        List<String> lines = wrap ? wrapText(mc, text, w) : convertComponentsToString(text);

        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            Component component = text.get(index);
            int lineWidth = mc.font.width(line);
            int drawX = calculateHorizontalAlignmentX(x, lineWidth);
            int drawY = y + index * (int) (mc.font.lineHeight * lineSpacing);
            int color = component.getStyle().getColor() != null ? component.getStyle().getColor().getValue() : 0xFFFFFF;
            guiGraphics.drawString(mc.font, line, drawX, drawY, color, false);
        }
    }

    private int calculateHorizontalAlignmentX(int startX, int lineWidth) {
        return switch (alignment) {
            case RIGHT -> startX + w - lineWidth;
            case CENTER -> startX + (w - lineWidth) / 2;
            default -> startX;
        };
    }

    private List<String> wrapText(Minecraft mc, List<Component> components, int maxWidth) {
        List<String> wrappedLines = new ArrayList<>();
        for (Component component : components) {
            String text = component.getString();
            int lineWidth = mc.font.width(text);
            if (lineWidth > maxWidth) {
                String[] words = text.split(" ");
                StringBuilder currentLine = new StringBuilder();
                for (String word : words) {
                    if (mc.font.width(currentLine + word) <= maxWidth) {
                        currentLine.append(word).append(" ");
                    } else {
                        wrappedLines.add(currentLine.toString().trim());
                        currentLine = new StringBuilder(word + " ");
                    }
                }
                if (!currentLine.isEmpty()) {
                    wrappedLines.add(currentLine.toString().trim());
                }
            } else {
                wrappedLines.add(text);
            }
        }
        return wrappedLines;
    }

    private List<String> convertComponentsToString(List<Component> components) {
        List<String> lines = new ArrayList<>();
        for (Component component : components) {
            lines.add(component.getString());
        }
        return lines;
    }
}