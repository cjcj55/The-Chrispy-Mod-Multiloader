package com.cjcj55.chrispymod.neoforge;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.menu.AlloyFurnaceMenu;
import com.cjcj55.chrispymod.common.menu.ModMenuTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.RegisterEvent;

/** NeoForge's {@code IMenuTypeExtension} is how a mod builds a {@code MenuType} with vanilla's private constructor. */
public final class NeoForgeMenuTypes {
    private NeoForgeMenuTypes() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener((RegisterEvent event) -> event.register(Registries.MENU, helper -> {
            MenuType<AlloyFurnaceMenu> type = IMenuTypeExtension.create((id, inventory, buf) -> new AlloyFurnaceMenu(id, inventory));
            Registry.register(BuiltInRegistries.MENU, ChrispyMod.id("alloy_furnace"), type);
            ModMenuTypes.ALLOY_FURNACE = type;
        }));
    }
}
