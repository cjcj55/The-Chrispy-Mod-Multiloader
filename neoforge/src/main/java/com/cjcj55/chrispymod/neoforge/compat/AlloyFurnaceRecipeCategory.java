package com.cjcj55.chrispymod.neoforge.compat;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.recipe.AlloyFurnaceRecipe;
import com.cjcj55.chrispymod.common.recipe.ModRecipeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

/** Shown in JEI as: two input slots and one output slot, matching part of {@code AlloyFurnaceScreen}'s layout. */
public class AlloyFurnaceRecipeCategory implements IRecipeCategory<RecipeHolder<AlloyFurnaceRecipe>> {
    public static final RecipeType<RecipeHolder<AlloyFurnaceRecipe>> TYPE = RecipeType.createFromVanilla(ModRecipeTypes.ALLOY_FURNACE);
    private static final int BACKGROUND_COLOR = 0xFFC6C6C6;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    private final IDrawable icon;

    public AlloyFurnaceRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.ALLOY_FURNACE));
    }

    @Override
    public RecipeType<RecipeHolder<AlloyFurnaceRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container." + ChrispyMod.MOD_ID + ".alloy_furnace");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(RecipeHolder<AlloyFurnaceRecipe> recipe, IRecipeSlotsView slotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        guiGraphics.fill(0, 0, WIDTH, HEIGHT, BACKGROUND_COLOR);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<AlloyFurnaceRecipe> recipeHolder, IFocusGroup focuses) {
        AlloyFurnaceRecipe recipe = recipeHolder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 4).addIngredients(recipe.first());
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 38).addIngredients(recipe.second());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 21).addItemStack(recipe.output().create());
    }
}
