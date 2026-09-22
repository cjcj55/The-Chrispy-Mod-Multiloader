package com.cjcj55.chrispymod.common.worldgen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

/** How many veins per chunk and at what height each ore feature is placed. */
public final class ModPlacedFeatures {
    private ModPlacedFeatures() {
    }

    public static final ResourceKey<PlacedFeature> RUBY_SMALL = key("ruby_small_placed");
    public static final ResourceKey<PlacedFeature> RUBY_LARGE = key("ruby_large_placed");
    public static final ResourceKey<PlacedFeature> RUBY_HIDDEN = key("ruby_hidden_placed");
    public static final ResourceKey<PlacedFeature> OPAL = key("opal_placed");
    public static final ResourceKey<PlacedFeature> OPAL_SMALL = key("opal_small_placed");
    public static final ResourceKey<PlacedFeature> TANGERINE = key("tangerine_placed");
    public static final ResourceKey<PlacedFeature> TANGERINE_SMALL = key("tangerine_small_placed");
    public static final ResourceKey<PlacedFeature> COBALT_SMALL = key("cobalt_small_placed");
    public static final ResourceKey<PlacedFeature> COBALT_LARGE = key("cobalt_large_placed");
    public static final ResourceKey<PlacedFeature> COBALT_HIDDEN = key("cobalt_hidden_placed");
    public static final ResourceKey<PlacedFeature> PARYTH = key("paryth_placed");
    public static final ResourceKey<PlacedFeature> PARYTH_SMALL = key("paryth_small_placed");
    public static final ResourceKey<PlacedFeature> WHITE_DWARF_STAR_SMALL = key("white_dwarf_star_small_placed");
    public static final ResourceKey<PlacedFeature> WHITE_DWARF_STAR_HIDDEN = key("white_dwarf_star_hidden_placed");
    public static final ResourceKey<PlacedFeature> NATURAL_ESSENCE_HIDDEN = key("natural_essence_hidden_placed");
    public static final ResourceKey<PlacedFeature> EXPERIENCE = key("experience_placed");
    public static final ResourceKey<PlacedFeature> EXPERIENCE_HIDDEN = key("experience_hidden_placed");
    public static final ResourceKey<PlacedFeature> NETHER_RUBY = key("nether_ruby_placed");
    public static final ResourceKey<PlacedFeature> NETHER_FLAME = key("nether_flame_placed");
    public static final ResourceKey<PlacedFeature> NETHER_HELLFIRE = key("nether_hellfire_placed");

    /** All overworld placements, for the biome injection step. */
    public static final List<ResourceKey<PlacedFeature>> OVERWORLD = List.of(
            RUBY_SMALL, RUBY_LARGE, RUBY_HIDDEN, OPAL, OPAL_SMALL, TANGERINE, TANGERINE_SMALL,
            COBALT_SMALL, COBALT_LARGE, COBALT_HIDDEN, PARYTH, PARYTH_SMALL,
            WHITE_DWARF_STAR_SMALL, WHITE_DWARF_STAR_HIDDEN, NATURAL_ESSENCE_HIDDEN, EXPERIENCE, EXPERIENCE_HIDDEN);

    /** All nether placements, for the biome injection step. */
    public static final List<ResourceKey<PlacedFeature>> NETHER = List.of(NETHER_RUBY, NETHER_FLAME, NETHER_HELLFIRE);

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> features = context.lookup(Registries.FEATURE);

        register(context, RUBY_SMALL, features, ModFeatures.RUBY_SMALL, common(6, triangle(-80, 80)));
        register(context, RUBY_LARGE, features, ModFeatures.RUBY_LARGE, rare(8, triangle(-80, 80)));
        register(context, RUBY_HIDDEN, features, ModFeatures.RUBY_HIDDEN, common(5, triangle(-80, 80)));

        register(context, OPAL, features, ModFeatures.OPAL, common(30, triangle(80, 384)));
        register(context, OPAL_SMALL, features, ModFeatures.OPAL_SMALL, common(10, uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80))));

        register(context, TANGERINE, features, ModFeatures.TANGERINE, common(20, triangle(80, 384)));
        register(context, TANGERINE_SMALL, features, ModFeatures.TANGERINE_SMALL, common(7, uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(70))));

        register(context, COBALT_SMALL, features, ModFeatures.COBALT_SMALL, common(6, triangle(-80, 80)));
        register(context, COBALT_LARGE, features, ModFeatures.COBALT_LARGE, rare(7, triangle(-80, 80)));
        register(context, COBALT_HIDDEN, features, ModFeatures.COBALT_HIDDEN, common(3, triangle(-80, 80)));

        register(context, PARYTH, features, ModFeatures.PARYTH, common(25, triangle(64, 180)));
        register(context, PARYTH_SMALL, features, ModFeatures.PARYTH_SMALL, common(8, uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(180))));

        register(context, WHITE_DWARF_STAR_SMALL, features, ModFeatures.WHITE_DWARF_STAR_SMALL, common(3, triangle(-80, 80)));
        register(context, WHITE_DWARF_STAR_HIDDEN, features, ModFeatures.WHITE_DWARF_STAR_HIDDEN, common(4, triangle(-80, 80)));

        register(context, NATURAL_ESSENCE_HIDDEN, features, ModFeatures.NATURAL_ESSENCE_HIDDEN, common(2, triangle(-32, 32)));

        register(context, EXPERIENCE, features, ModFeatures.EXPERIENCE, common(7, triangleFullHeight()));
        register(context, EXPERIENCE_HIDDEN, features, ModFeatures.EXPERIENCE_HIDDEN, common(5, uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));

        register(context, NETHER_RUBY, features, ModFeatures.NETHER_RUBY, common(7, PlacementUtils.RANGE_10_10));
        register(context, NETHER_FLAME, features, ModFeatures.NETHER_FLAME, common(8, PlacementUtils.RANGE_10_10));
        register(context, NETHER_HELLFIRE, features, ModFeatures.NETHER_HELLFIRE, common(10, PlacementUtils.RANGE_10_10));
    }

    private static HeightRangePlacement triangle(int min, int max) {
        return HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(min), VerticalAnchor.aboveBottom(max));
    }

    private static HeightRangePlacement triangleFullHeight() {
        return HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.top());
    }

    private static HeightRangePlacement uniform(VerticalAnchor min, VerticalAnchor max) {
        return HeightRangePlacement.uniform(min, max);
    }

    private static List<PlacementModifier> common(int veinsPerChunk, PlacementModifier heightRange) {
        return List.of(CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> rare(int chance, PlacementModifier heightRange) {
        return List.of(RarityFilter.onAverageOnceEvery(chance), InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 HolderGetter<Feature> features, ResourceKey<Feature> feature, List<PlacementModifier> modifiers) {
        Holder<Feature> holder = features.getOrThrow(feature);
        context.register(key, new PlacedFeature(holder, List.copyOf(modifiers)));
    }

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ChrispyMod.id(name));
    }
}
