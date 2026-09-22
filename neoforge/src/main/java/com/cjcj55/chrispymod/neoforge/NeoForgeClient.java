package com.cjcj55.chrispymod.neoforge;

import com.cjcj55.chrispymod.common.client.AlloyFurnaceScreen;
import com.cjcj55.chrispymod.common.menu.ModMenuTypes;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/** Client-only wiring. This class is only touched from an event that fires on the client, so it's safe on a dedicated server. */
public final class NeoForgeClient {
    private NeoForgeClient() {
    }

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ALLOY_FURNACE, AlloyFurnaceScreen::new);
    }
}
