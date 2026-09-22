package com.cjcj55.chrispymod.fabric;

import com.cjcj55.chrispymod.common.worldgen.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/** Adds the shared placed features to biomes. NeoForge does the same with data-driven biome modifiers. */
public final class FabricWorldGeneration {
    private FabricWorldGeneration() {
    }

    public static void register() {
        for (ResourceKey<PlacedFeature> feature : ModPlacedFeatures.OVERWORLD) {
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, feature);
        }
        for (ResourceKey<PlacedFeature> feature : ModPlacedFeatures.NETHER) {
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Decoration.UNDERGROUND_ORES, feature);
        }
    }
}
