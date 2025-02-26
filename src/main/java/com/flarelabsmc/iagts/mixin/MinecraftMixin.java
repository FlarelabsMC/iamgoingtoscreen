package com.flarelabsmc.iagts.mixin;

import com.flarelabsmc.iagts.kubejs.event.IAGTSJSEvents;
import com.flarelabsmc.iagts.kubejs.event.OpenScreenEvent;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Restriction(require = @Condition("kubejs"))
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public Screen screen;

    @Shadow @Final private static Logger LOGGER;

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen setScreen(Screen newScreen) {
        OpenScreenEvent event = new OpenScreenEvent(screen, newScreen);
        IAGTSJSEvents.OPEN_SCREEN.post(event);
        LOGGER.info("New screen: " + event.getNewScreen());
        return event.getNewScreen();
    }
}