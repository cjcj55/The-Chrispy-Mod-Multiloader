package com.cjcj55.chrispymod.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/** Combines two ingredients (in either input slot) into one output, using any vanilla furnace fuel. */
public record AlloyFurnaceRecipe(Ingredient first, Ingredient second, ItemStackTemplate output) implements Recipe<AlloyFurnaceRecipeInput> {
    public static final MapCodec<AlloyFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("first").forGetter(AlloyFurnaceRecipe::first),
            Ingredient.CODEC.fieldOf("second").forGetter(AlloyFurnaceRecipe::second),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(AlloyFurnaceRecipe::output)
    ).apply(instance, AlloyFurnaceRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyFurnaceRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, AlloyFurnaceRecipe::first,
            Ingredient.CONTENTS_STREAM_CODEC, AlloyFurnaceRecipe::second,
            ItemStackTemplate.STREAM_CODEC, AlloyFurnaceRecipe::output,
            AlloyFurnaceRecipe::new);

    @Override
    public boolean matches(AlloyFurnaceRecipeInput input, Level level) {
        return (first.test(input.first()) && second.test(input.second()))
                || (first.test(input.second()) && second.test(input.first()));
    }

    @Override
    public ItemStack assemble(AlloyFurnaceRecipeInput input) {
        return output.create();
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "alloy_furnace";
    }

    @Override
    public RecipeSerializer<? extends Recipe<AlloyFurnaceRecipeInput>> getSerializer() {
        return ModRecipeTypes.ALLOY_FURNACE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<AlloyFurnaceRecipeInput>> getType() {
        return ModRecipeTypes.ALLOY_FURNACE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }
}
