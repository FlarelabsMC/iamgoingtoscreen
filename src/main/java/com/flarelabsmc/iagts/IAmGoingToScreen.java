package com.flarelabsmc.iagts;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(IAmGoingToScreen.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IAmGoingToScreen {
    public static final String MODID = "iagts";
    public static final Logger LOGGER = LogUtils.getLogger();

    public IAmGoingToScreen() {}
}