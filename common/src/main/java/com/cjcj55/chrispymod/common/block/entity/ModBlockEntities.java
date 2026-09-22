package com.cjcj55.chrispymod.common.block.entity;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public final class ModBlockEntities {
    private ModBlockEntities() {
    }

    public static final BlockEntityType<AlloyFurnaceBlockEntity> ALLOY_FURNACE =
            new BlockEntityType<>(AlloyFurnaceBlockEntity::new, Set.of(ModBlocks.ALLOY_FURNACE));

    /** Must be called while the registry is open, same timing as {@code ModBlocks}/{@code ModItems}, and after {@code ModBlocks.register()}. */
    public static void register() {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ChrispyMod.id("alloy_furnace"), ALLOY_FURNACE);
    }
}
