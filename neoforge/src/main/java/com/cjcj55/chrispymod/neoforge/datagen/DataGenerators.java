package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.creativetab.ModCreativeModeTabs;
import com.cjcj55.chrispymod.common.worldgen.ModFeatures;
import com.cjcj55.chrispymod.common.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Data generation only runs from NeoForge (`:neoforge:runClientData`). The output goes to
 * `common/src/main/generated`, so Fabric ships the same assets and data.
 */
@EventBusSubscriber(modid = ChrispyMod.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getWorldLookupProvider();

        // Catch new items that were forgotten in the creative tab (or listed twice) while generating.
        if (!ModCreativeModeTabs.itemsMissingFromTab().isEmpty() || !ModCreativeModeTabs.itemsListedTwice().isEmpty()) {
            throw new IllegalStateException("Creative tab is out of sync. Missing: " + ModCreativeModeTabs.itemsMissingFromTab()
                    + ", listed twice: " + ModCreativeModeTabs.itemsListedTwice());
        }

        generator.addProvider(true, new ModModelProvider(output));
        generator.addProvider(true, new ModEquipmentAssetProvider(output));
        generator.addProvider(true, new ModLanguageProvider(output));
        generator.addProvider(true, new ModBlockTagProvider(output, lookup));
        generator.addProvider(true, new ModItemTagProvider(output, lookup));

        // Ore features are datapack registries that are loaded with the world.
        event.createWorldRegistryObjects(new RegistrySetBuilder()
                .add(Registries.FEATURE, ModFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap), Set.of(ChrispyMod.MOD_ID));

        // Loot tables, recipes and recipe advancements are reloadable datapack registries.
        RegistrySetBuilder reloadable = new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(),
                        List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK))))
                .add(ModRecipeProvider.bootstrap());
        event.createReloadableRegistryObjects(reloadable, Set.of(ChrispyMod.MOD_ID));
    }
}
