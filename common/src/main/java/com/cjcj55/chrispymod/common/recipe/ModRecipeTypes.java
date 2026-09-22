package com.cjcj55.chrispymod.common.recipe;

import com.cjcj55.chrispymod.common.ChrispyMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeTypes {
    private ModRecipeTypes() {
    }

    public static final RecipeType<AlloyFurnaceRecipe> ALLOY_FURNACE = new RecipeType<>() {
        @Override
        public String toString() {
            return ChrispyMod.id("alloy_furnace").toString();
        }
    };
    public static final RecipeSerializer<AlloyFurnaceRecipe> ALLOY_FURNACE_SERIALIZER =
            new RecipeSerializer<>(AlloyFurnaceRecipe.CODEC, AlloyFurnaceRecipe.STREAM_CODEC);

    /** Registers the recipe type and serializer. Must be called while the registries are open, same timing as {@code ModBlocks}/{@code ModItems}. */
    public static void register() {
        Registry.register(BuiltInRegistries.RECIPE_TYPE, ChrispyMod.id("alloy_furnace"), ALLOY_FURNACE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ChrispyMod.id("alloy_furnace"), ALLOY_FURNACE_SERIALIZER);
    }
}
