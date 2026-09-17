package com.cjcj55.chrispymod.fabric;

import net.fabricmc.api.ModInitializer;

public class ChrispyModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        com.cjcj55.chrispymod.common.ChrispyMod.initialize();
    }
}
