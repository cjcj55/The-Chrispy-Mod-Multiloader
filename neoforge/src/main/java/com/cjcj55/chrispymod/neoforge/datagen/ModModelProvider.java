package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.AlloyFurnaceBlock;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ChrispyMod.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (Block block : ModBlocks.blocks().values()) {
            if (block instanceof RedstoneLampBlock) {
                litLamp(blockModels, block);
            } else if (block instanceof AlloyFurnaceBlock) {
                alloyFurnace(blockModels, block);
            } else if (block instanceof RotatedPillarBlock) {
                blockModels.createAxisAlignedPillarBlock(block, TexturedModel.COLUMN);
            } else {
                blockModels.createTrivialCube(block);
            }
        }

        Set<Item> specialModels = new HashSet<>();
        for (ModItems.ToolSet set : ModItems.toolSets()) {
            for (Item tool : new Item[]{set.sword(), set.shovel(), set.pickaxe(), set.axe(), set.hoe()}) {
                itemModels.generateFlatItem(tool, ModelTemplates.FLAT_HANDHELD_ITEM);
                specialModels.add(tool);
            }
        }
        for (Item wand : new Item[]{ModItems.LIGHTNING_WAND, ModItems.FLAME_WAND}) {
            itemModels.generateFlatItem(wand, ModelTemplates.FLAT_HANDHELD_ITEM);
            specialModels.add(wand);
        }
        for (ModItems.ArmorSet set : ModItems.armorSets()) {
            for (Item piece : List.of(set.helmet(), set.chestplate(), set.leggings(), set.boots())) {
                itemModels.generateFlatItem(piece, ModelTemplates.FLAT_ITEM);
                specialModels.add(piece);
            }
        }

        // Everything else is a plain flat item; block items get their model from the block generators above.
        for (Identifier id : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.getValue(id);
            if (id.getNamespace().equals(ChrispyMod.MOD_ID) && !(item instanceof BlockItem) && !specialModels.contains(item)) {
                itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
        }
    }

    /** A redstone lamp: `<color>_redstone_lamp` when off and `<color>_redstone_lamp_on` when lit. */
    private static void litLamp(BlockModelGenerators blockModels, Block block) {
        Identifier off = TexturedModel.CUBE.create(block, blockModels.modelOutput);
        Identifier on = blockModels.createSuffixedVariant(block, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(BlockModelGenerators.createBooleanModelDispatch(RedstoneLampBlock.LIT,
                        BlockModelGenerators.plainVariant(on), BlockModelGenerators.plainVariant(off))));
    }

    /**
     * A furnace-style block: front/side/top textures, the front swapped for {@code <id>_front_on} while lit,
     * and the model rotated to face the direction it was placed in.
     */
    private static void alloyFurnace(BlockModelGenerators blockModels, Block block) {
        TextureMapping textures = TextureMapping.orientableCubeSameEnds(block);
        Identifier off = ModelTemplates.CUBE_ORIENTABLE.create(block, textures, blockModels.modelOutput);
        TextureMapping litTextures = textures.copyAndUpdate(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front_on"));
        Identifier on = ModelTemplates.CUBE_ORIENTABLE.create(ModelLocationUtils.getModelLocation(block, "_on"), litTextures, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(BlockModelGenerators.createBooleanModelDispatch(AlloyFurnaceBlock.LIT,
                        BlockModelGenerators.plainVariant(on), BlockModelGenerators.plainVariant(off)))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }
}
