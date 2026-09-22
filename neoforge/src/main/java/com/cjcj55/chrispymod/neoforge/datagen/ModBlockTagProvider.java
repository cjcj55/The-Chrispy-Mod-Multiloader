package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ChrispyMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // Everything except the lamps, the sugar blocks and the sponges is mined with a pickaxe.
        for (Block block : ModBlocks.blocks().values()) {
            if (!(block instanceof RedstoneLampBlock) && block != ModBlocks.SUGAR_BLOCK && block != ModBlocks.SUGAR_CANE_BLOCK
                    && block != ModBlocks.LAVA_SPONGE && block != ModBlocks.WET_LAVA_SPONGE) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key(block));
            }
        }

        add(BlockTags.NEEDS_STONE_TOOL, List.of(
                ModBlocks.EXPERIENCE_ORE, ModBlocks.DEEPSLATE_EXPERIENCE_ORE,
                ModBlocks.OPAL_BLOCK, ModBlocks.OPAL_ORE, ModBlocks.DEEPSLATE_OPAL_ORE));

        add(BlockTags.NEEDS_IRON_TOOL, List.of(
                ModBlocks.FLAME_BLOCK, ModBlocks.FLAME_ORE_NETHER, ModBlocks.HELLFIRE_ORE_NETHER,
                ModBlocks.HARDENED_REDSTONE_BLOCK, ModBlocks.ALLOY_FURNACE,
                ModBlocks.NATURAL_ESSENCE_ORE, ModBlocks.DEEPSLATE_NATURAL_ESSENCE_ORE,
                ModBlocks.PARYTH_BLOCK, ModBlocks.PARYTH_ORE, ModBlocks.DEEPSLATE_PARYTH_ORE,
                ModBlocks.RUBY_BLOCK, ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.RUBY_ORE_NETHER,
                ModBlocks.TANGERINE_BLOCK, ModBlocks.TANGERINE_ORE, ModBlocks.DEEPSLATE_TANGERINE_ORE));

        add(BlockTags.NEEDS_DIAMOND_TOOL, List.of(
                ModBlocks.BLUE_EMERALD_BLOCK, ModBlocks.LIGHTNING_BLOCK,
                ModBlocks.COBALT_BLOCK, ModBlocks.COBALT_ORE, ModBlocks.DEEPSLATE_COBALT_ORE,
                ModBlocks.WHITE_DWARF_STAR_ORE, ModBlocks.DEEPSLATE_WHITE_DWARF_STAR_ORE));
    }

    private void add(net.minecraft.tags.TagKey<Block> tag, List<Block> blocks) {
        for (Block block : blocks) {
            tag(tag).add(key(block));
        }
    }

    private static ResourceKey<Block> key(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
    }
}
