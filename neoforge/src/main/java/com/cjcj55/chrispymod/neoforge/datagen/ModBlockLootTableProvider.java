package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    /** Ores drop this item instead of themselves (unless mined with Silk Touch). Every other block drops itself. */
    private static final Map<Block, Item> ORE_DROPS = Map.ofEntries(
            Map.entry(ModBlocks.RUBY_ORE, ModItems.RUBY),
            Map.entry(ModBlocks.DEEPSLATE_RUBY_ORE, ModItems.RUBY),
            Map.entry(ModBlocks.RUBY_ORE_NETHER, ModItems.RUBY),
            Map.entry(ModBlocks.OPAL_ORE, ModItems.OPAL),
            Map.entry(ModBlocks.DEEPSLATE_OPAL_ORE, ModItems.OPAL),
            Map.entry(ModBlocks.TANGERINE_ORE, ModItems.TANGERINE),
            Map.entry(ModBlocks.DEEPSLATE_TANGERINE_ORE, ModItems.TANGERINE),
            Map.entry(ModBlocks.COBALT_ORE, ModItems.COBALT),
            Map.entry(ModBlocks.DEEPSLATE_COBALT_ORE, ModItems.COBALT),
            Map.entry(ModBlocks.PARYTH_ORE, ModItems.PARYTH),
            Map.entry(ModBlocks.DEEPSLATE_PARYTH_ORE, ModItems.PARYTH),
            Map.entry(ModBlocks.WHITE_DWARF_STAR_ORE, ModItems.WHITE_DWARF_STAR),
            Map.entry(ModBlocks.DEEPSLATE_WHITE_DWARF_STAR_ORE, ModItems.WHITE_DWARF_STAR),
            Map.entry(ModBlocks.NATURAL_ESSENCE_ORE, ModItems.NATURAL_ESSENCE),
            Map.entry(ModBlocks.DEEPSLATE_NATURAL_ESSENCE_ORE, ModItems.NATURAL_ESSENCE),
            Map.entry(ModBlocks.EXPERIENCE_ORE, ModItems.EDIBLE_EXPERIENCE),
            Map.entry(ModBlocks.DEEPSLATE_EXPERIENCE_ORE, ModItems.EDIBLE_EXPERIENCE),
            Map.entry(ModBlocks.FLAME_ORE_NETHER, ModItems.FLAME),
            Map.entry(ModBlocks.HELLFIRE_ORE_NETHER, ModItems.HELLFIRE));

    public ModBlockLootTableProvider(LootTableSubProvider.Context context) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    protected void generate() {
        for (Block block : ModBlocks.blocks().values()) {
            Item drop = ORE_DROPS.get(block);
            if (drop != null) {
                add(block, createOreDrop(block, drop));
            } else {
                dropSelf(block);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.blocks().values();
    }
}
