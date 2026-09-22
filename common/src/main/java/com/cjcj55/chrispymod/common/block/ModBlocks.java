package com.cjcj55.chrispymod.common.block;

import com.cjcj55.chrispymod.common.ChrispyMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ModBlocks {
    private ModBlocks() {
    }

    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    // Storage blocks
    public static final Block RUBY_BLOCK = metalBlock("ruby_block", MapColor.COLOR_RED, 5.1f, 31.0f);
    public static final Block OPAL_BLOCK = metalBlock("opal_block", MapColor.TERRACOTTA_WHITE, 4.5f, 31.0f);
    public static final Block TANGERINE_BLOCK = metalBlock("tangerine_block", MapColor.COLOR_ORANGE, 5.3f, 31.0f);
    public static final Block COBALT_BLOCK = metalBlock("cobalt_block", MapColor.TERRACOTTA_BLUE, 6.0f, 45.0f);
    public static final Block BLUE_EMERALD_BLOCK = metalBlock("blue_emerald_block", MapColor.TERRACOTTA_ORANGE, 9.0f, 50.0f);
    public static final Block PARYTH_BLOCK = metalBlock("paryth_block", MapColor.COLOR_PINK, 5.6f, 35.0f);
    public static final Block LIGHTNING_BLOCK = registerBlock("lightning_block", properties -> new Block(properties
            .mapColor(MapColor.COLOR_LIGHT_BLUE).strength(8.5f, 40.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(state -> 15)));
    public static final Block FLAME_BLOCK = metalBlock("flame_block", MapColor.COLOR_RED, 5.1f, 40.0f);
    public static final Block HARDENED_REDSTONE_BLOCK = metalBlock("hardened_redstone_block", MapColor.COLOR_RED, 3.2f, 18.0f);

    // Ores
    public static final Block RUBY_ORE = ore("ruby_ore", MapColor.COLOR_RED, 5.0f, 15.0f, 1, 3);
    public static final Block DEEPSLATE_RUBY_ORE = ore("deepslate_ruby_ore", MapColor.COLOR_RED, 5.0f, 15.0f, 1, 3);
    public static final Block OPAL_ORE = ore("opal_ore", MapColor.TERRACOTTA_WHITE, 3.8f, 12.0f, 0, 2);
    public static final Block DEEPSLATE_OPAL_ORE = ore("deepslate_opal_ore", MapColor.TERRACOTTA_WHITE, 3.8f, 12.0f, 0, 2);
    public static final Block TANGERINE_ORE = ore("tangerine_ore", MapColor.COLOR_ORANGE, 5.5f, 25.0f, 1, 2);
    public static final Block DEEPSLATE_TANGERINE_ORE = ore("deepslate_tangerine_ore", MapColor.COLOR_ORANGE, 5.5f, 25.0f, 1, 2);
    public static final Block COBALT_ORE = ore("cobalt_ore", MapColor.TERRACOTTA_BLUE, 6.5f, 35.0f, 2, 3);
    public static final Block DEEPSLATE_COBALT_ORE = ore("deepslate_cobalt_ore", MapColor.TERRACOTTA_BLUE, 6.5f, 35.0f, 2, 3);
    public static final Block PARYTH_ORE = ore("paryth_ore", MapColor.COLOR_PINK, 5.5f, 35.0f, 0, 2);
    public static final Block DEEPSLATE_PARYTH_ORE = ore("deepslate_paryth_ore", MapColor.COLOR_PINK, 5.5f, 35.0f, 0, 2);
    public static final Block WHITE_DWARF_STAR_ORE = ore("white_dwarf_star_ore", MapColor.TERRACOTTA_WHITE, 6.0f, 200.0f, 4, 8);
    public static final Block DEEPSLATE_WHITE_DWARF_STAR_ORE = ore("deepslate_white_dwarf_star_ore", MapColor.TERRACOTTA_WHITE, 6.0f, 200.0f, 4, 8);
    public static final Block NATURAL_ESSENCE_ORE = ore("natural_essence_ore", MapColor.TERRACOTTA_GREEN, 2.0f, 8.0f, 1, 3);
    public static final Block DEEPSLATE_NATURAL_ESSENCE_ORE = ore("deepslate_natural_essence_ore", MapColor.TERRACOTTA_GREEN, 2.0f, 8.0f, 1, 3);
    public static final Block EXPERIENCE_ORE = registerBlock("experience_ore", properties -> new Block(properties
            .mapColor(MapColor.COLOR_LIGHT_GREEN).strength(4.0f, 13.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final Block DEEPSLATE_EXPERIENCE_ORE = registerBlock("deepslate_experience_ore", properties -> new Block(properties
            .mapColor(MapColor.COLOR_LIGHT_GREEN).strength(4.0f, 13.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    // Nether ores
    public static final Block RUBY_ORE_NETHER = ore("ruby_ore_nether", MapColor.COLOR_RED, 2.0f, 8.0f, 1, 3);
    public static final Block FLAME_ORE_NETHER = ore("flame_ore_nether", MapColor.COLOR_RED, 2.0f, 8.0f, 3, 5);
    public static final Block HELLFIRE_ORE_NETHER = ore("hellfire_ore_nether", MapColor.COLOR_RED, 2.0f, 8.0f, 3, 6);

    // Misc
    public static final Block SUGAR_BLOCK = registerBlock("sugar_block", properties -> new Block(properties
            .mapColor(MapColor.COLOR_GREEN).strength(1.0f, 8.0f).sound(SoundType.SWEET_BERRY_BUSH)));
    public static final Block SUGAR_CANE_BLOCK = registerBlock("sugar_cane_block", properties -> new RotatedPillarBlock(properties
            .mapColor(MapColor.COLOR_GREEN).strength(1.2f, 10.0f).sound(SoundType.CROP)));
    public static final Block LAVA_SPONGE = registerBlock("lava_sponge", properties -> new LavaSpongeBlock(properties
            .mapColor(MapColor.COLOR_BLACK).strength(0.6f).sound(SoundType.GRASS)));
    public static final Block WET_LAVA_SPONGE = registerBlock("wet_lava_sponge", properties -> new WetLavaSpongeBlock(properties
            .mapColor(MapColor.FIRE).strength(0.6f).sound(SoundType.GRASS)));
    public static final Block ALLOY_FURNACE = registerBlock("alloy_furnace", properties -> new AlloyFurnaceBlock(properties
            .mapColor(MapColor.COLOR_GRAY).strength(4.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    // Bricks, in the order they appear in the creative tab
    public static final List<Block> BRICKS = new ArrayList<>();

    static {
        for (String name : List.of("skinny_slanted_bricks", "slanted_bricks", "skinny_bricks", "circular_bricks", "cracked_bricks",
                "encased_bricks", "mosaic_bricks", "ornate_bricks", "road_bricks", "solid_bricks", "weaver_bricks")) {
            BRICKS.add(registerBlock(name, properties -> new Block(properties.mapColor(Blocks.BRICKS.defaultMapColor())
                    .strength(2.0f, 6.0f).sound(SoundType.STONE).requiresCorrectToolForDrops())));
        }
    }

    // Redstone lamps, one per dye color
    public static final Map<DyeColor, Block> REDSTONE_LAMPS = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            REDSTONE_LAMPS.put(color, registerBlock(color.getName() + "_redstone_lamp", properties -> new RedstoneLampBlock(properties
                    .mapColor(color).strength(0.3f).sound(SoundType.GLASS)
                    .lightLevel(state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0))));
        }
    }

    /**
     * Registers all blocks. Must be called while the block registry is open: Fabric does this during
     * initialization, NeoForge from its block RegisterEvent. The work happens in static initialization.
     */
    public static void register() {
        ChrispyMod.LOGGER.debug("Registered {} blocks", BLOCKS.size());
    }

    /** All blocks of the mod, in registration order. */
    public static Map<Identifier, Block> blocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    private static Block metalBlock(String name, MapColor color, float hardness, float resistance) {
        return registerBlock(name, properties -> new Block(properties
                .mapColor(color).strength(hardness, resistance).sound(SoundType.METAL).requiresCorrectToolForDrops()));
    }

    private static Block ore(String name, MapColor color, float hardness, float resistance, int minXp, int maxXp) {
        return registerBlock(name, properties -> new DropExperienceBlock(UniformInt.of(minXp, maxXp), properties
                .mapColor(color).strength(hardness, resistance).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    }

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory) {
        Identifier id = ChrispyMod.id(name);
        Block block = Registry.register(BuiltInRegistries.BLOCK, id,
                factory.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))));
        BLOCKS.put(id, block);
        return block;
    }
}
