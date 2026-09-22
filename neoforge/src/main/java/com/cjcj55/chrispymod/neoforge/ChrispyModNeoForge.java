package com.cjcj55.chrispymod.neoforge;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.block.entity.ModBlockEntities;
import com.cjcj55.chrispymod.common.item.ModItems;
import com.cjcj55.chrispymod.common.recipe.ModRecipeTypes;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ChrispyMod.MOD_ID)
public class ChrispyModNeoForge {
    public ChrispyModNeoForge(IEventBus modEventBus) {
        ChrispyMod.initialize();
        modEventBus.addListener(ChrispyModNeoForge::onRegister);
        modEventBus.addListener(NeoForgeClient::registerScreens);
        NeoForgeCreativeModeTabs.register(modEventBus);
        NeoForgeMenuTypes.register(modEventBus);
    }

    // NeoForge only opens each registry inside RegisterEvent, so common's registration runs from here.
    private static void onRegister(RegisterEvent event) {
        event.register(Registries.BLOCK, helper -> ModBlocks.register());
        event.register(Registries.ITEM, helper -> ModItems.register());
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> ModBlockEntities.register());
        event.register(Registries.RECIPE_SERIALIZER, helper -> ModRecipeTypes.register());
    }
}
