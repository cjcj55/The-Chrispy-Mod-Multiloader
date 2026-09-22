package com.cjcj55.chrispymod.fabric;

import com.cjcj55.chrispymod.common.creativetab.ModCreativeModeTabs;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FabricCreativeModeTabs {
    private FabricCreativeModeTabs() {
    }

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModCreativeModeTabs.ITEM_TAB_ID, FabricCreativeModeTab.builder()
                .title(ModCreativeModeTabs.ITEM_TAB_TITLE)
                .icon(ModCreativeModeTabs::itemTabIcon)
                .displayItems((parameters, output) -> ModCreativeModeTabs.forEachItemTabEntry(output::accept))
                .build());
    }
}
