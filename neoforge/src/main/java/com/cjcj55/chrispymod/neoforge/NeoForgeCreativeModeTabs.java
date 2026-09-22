package com.cjcj55.chrispymod.neoforge;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.creativetab.ModCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NeoForgeCreativeModeTabs {
    private NeoForgeCreativeModeTabs() {
    }

    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChrispyMod.MOD_ID);

    public static final Supplier<CreativeModeTab> ITEM_TAB = CREATIVE_MODE_TABS.register(ModCreativeModeTabs.ITEM_TAB_ID.getPath(),
            () -> CreativeModeTab.builder()
                    .title(ModCreativeModeTabs.ITEM_TAB_TITLE)
                    .icon(ModCreativeModeTabs::itemTabIcon)
                    .displayItems((parameters, output) -> ModCreativeModeTabs.forEachItemTabEntry(output::accept))
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
