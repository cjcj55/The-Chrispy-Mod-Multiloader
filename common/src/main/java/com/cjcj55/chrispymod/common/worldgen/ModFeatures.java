package com.cjcj55.chrispymod.common.worldgen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * What ore veins look like: size and how likely a block at the edge of the vein is to be skipped.
 * Placement (how many veins per chunk and at what height) is in {@link ModPlacedFeatures}.
 */
public final class ModFeatures {
    private ModFeatures() {
    }

    private static final RuleTest STONE = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    private static final RuleTest DEEPSLATE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    private static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);

    public static final ResourceKey<Feature> RUBY_SMALL = key("ruby_small");
    public static final ResourceKey<Feature> RUBY_LARGE = key("ruby_large");
    public static final ResourceKey<Feature> RUBY_HIDDEN = key("ruby_hidden");
    public static final ResourceKey<Feature> OPAL = key("opal");
    public static final ResourceKey<Feature> OPAL_SMALL = key("opal_small");
    public static final ResourceKey<Feature> TANGERINE = key("tangerine");
    public static final ResourceKey<Feature> TANGERINE_SMALL = key("tangerine_small");
    public static final ResourceKey<Feature> COBALT_SMALL = key("cobalt_small");
    public static final ResourceKey<Feature> COBALT_LARGE = key("cobalt_large");
    public static final ResourceKey<Feature> COBALT_HIDDEN = key("cobalt_hidden");
    public static final ResourceKey<Feature> PARYTH = key("paryth");
    public static final ResourceKey<Feature> PARYTH_SMALL = key("paryth_small");
    public static final ResourceKey<Feature> WHITE_DWARF_STAR_SMALL = key("white_dwarf_star_small");
    public static final ResourceKey<Feature> WHITE_DWARF_STAR_HIDDEN = key("white_dwarf_star_hidden");
    public static final ResourceKey<Feature> NATURAL_ESSENCE_HIDDEN = key("natural_essence_hidden");
    public static final ResourceKey<Feature> EXPERIENCE = key("experience");
    public static final ResourceKey<Feature> EXPERIENCE_HIDDEN = key("experience_hidden");
    public static final ResourceKey<Feature> NETHER_RUBY = key("nether_ruby");
    public static final ResourceKey<Feature> NETHER_FLAME = key("nether_flame");
    public static final ResourceKey<Feature> NETHER_HELLFIRE = key("nether_hellfire");

    public static void bootstrap(BootstrapContext<Feature> context) {
        List<BlockReplacement> ruby = targets(ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE);
        register(context, RUBY_SMALL, ruby, 5, 0.4f);
        register(context, RUBY_LARGE, ruby, 12, 0.6f);
        register(context, RUBY_HIDDEN, ruby, 10, 1.0f);

        List<BlockReplacement> opal = targets(ModBlocks.OPAL_ORE, ModBlocks.DEEPSLATE_OPAL_ORE);
        register(context, OPAL, opal, 9);
        register(context, OPAL_SMALL, opal, 4);

        List<BlockReplacement> tangerine = targets(ModBlocks.TANGERINE_ORE, ModBlocks.DEEPSLATE_TANGERINE_ORE);
        register(context, TANGERINE, tangerine, 8);
        register(context, TANGERINE_SMALL, tangerine, 4);

        List<BlockReplacement> cobalt = targets(ModBlocks.COBALT_ORE, ModBlocks.DEEPSLATE_COBALT_ORE);
        register(context, COBALT_SMALL, cobalt, 3, 0.5f);
        register(context, COBALT_LARGE, cobalt, 10, 0.7f);
        register(context, COBALT_HIDDEN, cobalt, 7, 1.0f);

        List<BlockReplacement> paryth = targets(ModBlocks.PARYTH_ORE, ModBlocks.DEEPSLATE_PARYTH_ORE);
        register(context, PARYTH, paryth, 8);
        register(context, PARYTH_SMALL, paryth, 4);

        List<BlockReplacement> whiteDwarfStar = targets(ModBlocks.WHITE_DWARF_STAR_ORE, ModBlocks.DEEPSLATE_WHITE_DWARF_STAR_ORE);
        register(context, WHITE_DWARF_STAR_SMALL, whiteDwarfStar, 4, 0.5f);
        register(context, WHITE_DWARF_STAR_HIDDEN, whiteDwarfStar, 6, 1.0f);

        List<BlockReplacement> naturalEssence = targets(ModBlocks.NATURAL_ESSENCE_ORE, ModBlocks.DEEPSLATE_NATURAL_ESSENCE_ORE);
        register(context, NATURAL_ESSENCE_HIDDEN, naturalEssence, 5, 1.0f);

        List<BlockReplacement> experience = targets(ModBlocks.EXPERIENCE_ORE, ModBlocks.DEEPSLATE_EXPERIENCE_ORE);
        register(context, EXPERIENCE, experience, 4, 0.1f);
        register(context, EXPERIENCE_HIDDEN, experience, 5, 1.0f);

        register(context, NETHER_RUBY, List.of(BlockReplacement.replace(NETHERRACK, ModBlocks.RUBY_ORE_NETHER.defaultBlockState())), 9);
        register(context, NETHER_FLAME, List.of(BlockReplacement.replace(NETHERRACK, ModBlocks.FLAME_ORE_NETHER.defaultBlockState())), 9);
        register(context, NETHER_HELLFIRE, List.of(BlockReplacement.replace(NETHERRACK, ModBlocks.HELLFIRE_ORE_NETHER.defaultBlockState())), 9);
    }

    private static List<BlockReplacement> targets(Block stoneOre, Block deepslateOre) {
        return List.of(BlockReplacement.replace(STONE, stoneOre.defaultBlockState()), BlockReplacement.replace(DEEPSLATE, deepslateOre.defaultBlockState()));
    }

    private static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, List<BlockReplacement> targets, int size) {
        context.register(key, new OreFeature(targets, size));
    }

    private static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, List<BlockReplacement> targets, int size, float discardChanceOnAirExposure) {
        context.register(key, new OreFeature(targets, size, discardChanceOnAirExposure));
    }

    private static ResourceKey<Feature> key(String name) {
        return ResourceKey.create(Registries.FEATURE, ChrispyMod.id(name));
    }
}
