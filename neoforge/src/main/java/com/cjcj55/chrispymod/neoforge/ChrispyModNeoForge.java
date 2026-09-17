package com.cjcj55.chrispymod.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(com.cjcj55.chrispymod.common.ChrispyMod.MOD_ID)
public class ChrispyModNeoForge {
    public ChrispyModNeoForge(IEventBus modEventBus) {
        com.cjcj55.chrispymod.common.ChrispyMod.initialize();
    }
}