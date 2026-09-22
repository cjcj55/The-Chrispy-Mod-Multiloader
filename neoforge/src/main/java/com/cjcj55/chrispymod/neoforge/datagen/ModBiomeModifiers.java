package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.worldgen.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/** NeoForge adds the shared placed features to biomes with data-driven biome modifiers; Fabric does it in code. */
public final class ModBiomeModifiers {
    private ModBiomeModifiers() {
    }

    private static final ResourceKey<BiomeModifier> ADD_OVERWORLD_ORES = key("add_overworld_ores");
    private static final ResourceKey<BiomeModifier> ADD_NETHER_ORES = key("add_nether_ores");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(ADD_OVERWORLD_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), holders(placedFeatures, ModPlacedFeatures.OVERWORLD), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_NETHER_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER), holders(placedFeatures, ModPlacedFeatures.NETHER), GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static HolderSet<PlacedFeature> holders(HolderGetter<PlacedFeature> features, List<ResourceKey<PlacedFeature>> keys) {
        List<Holder<PlacedFeature>> holders = new ArrayList<>();
        for (ResourceKey<PlacedFeature> key : keys) {
            holders.add(features.getOrThrow(key));
        }
        return HolderSet.direct(holders);
    }

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ChrispyMod.id(name));
    }
}
