package com.cjcj55.chrispymod.fabric;

import com.cjcj55.chrispymod.common.client.AlloyFurnaceScreen;
import com.cjcj55.chrispymod.common.menu.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

/** Client-only entrypoint (declared under "client" in fabric.mod.json), so this never loads on a dedicated server. */
public class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.ALLOY_FURNACE, AlloyFurnaceScreen::new);
    }
}
