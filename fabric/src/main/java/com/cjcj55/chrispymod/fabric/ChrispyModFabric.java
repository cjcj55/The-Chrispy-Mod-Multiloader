package com.cjcj55.chrispymod.fabric;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.block.entity.ModBlockEntities;
import com.cjcj55.chrispymod.common.item.ModItems;
import com.cjcj55.chrispymod.common.recipe.ModRecipeTypes;
import net.fabricmc.api.ModInitializer;

public class ChrispyModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ChrispyMod.initialize();
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        ModRecipeTypes.register();
        FabricCreativeModeTabs.register();
        FabricWorldGeneration.register();
        FabricMenuTypes.register();
    }
}
