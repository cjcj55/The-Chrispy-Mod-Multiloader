package com.cjcj55.chrispymod.neoforge.compat;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.block.entity.AlloyFurnaceBlockEntity;
import com.cjcj55.chrispymod.common.recipe.AlloyFurnaceRecipe;
import com.cjcj55.chrispymod.common.recipe.ModRecipeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Shown in JEI as a crop of {@code AlloyFurnaceScreen}'s own background texture: the whole upper half (fuel
 * slot included, so it reads as needing fuel like a furnace) plus input1/input2/output and the connector between
 * them, instead of a flat panel. The crop origin (12,12) was measured so the texture's own slot borders line up
 * with the item positions below: subtracting it from the real screen's slot coordinates (66,16) and (66,50)
 * gives the (54,4) and (54,38) used here for input1/input2, and 114-12=102 for the output slot. The fuel slot
 * cycles through every registered fuel item (like vanilla's own smelting category), and the connector and flame
 * animate continuously using JEI's own tick timers rather than any real furnace's progress.
 */
public class AlloyFurnaceRecipeCategory implements IRecipeCategory<RecipeHolder<AlloyFurnaceRecipe>> {
    public static final RecipeType<RecipeHolder<AlloyFurnaceRecipe>> TYPE = RecipeType.createFromVanilla(ModRecipeTypes.ALLOY_FURNACE);
    private static final Identifier BACKGROUND = ChrispyMod.id("textures/gui/alloy_furnace_gui.png");
    private static final Identifier PROGRESS_ARROW_TEXTURE = ChrispyMod.id("textures/gui/alloy_furnace_arrow.png");
    private static final int BACKGROUND_SRC_X = 12;
    private static final int BACKGROUND_SRC_Y = 12;
    private static final int WIDTH = 126;
    private static final int HEIGHT = 64;
    private static final int BACKGROUND_CROP_WIDTH = 122;
    private static final int BACKGROUND_CROP_HEIGHT = 57;
    private static final int FLAME_X = 6;
    private static final int FLAME_Y = 21;
    private static final int FUEL_SLOT_X = 6;
    private static final int FUEL_SLOT_Y = 38;
    private static final int PROGRESS_ARROW_X = 73;
    private static final int PROGRESS_ARROW_Y = 11;
    private static final int PROGRESS_ARROW_WIDTH = 25;
    private static final int PROGRESS_ARROW_HEIGHT = 36;
    private static final int COOK_TIME_X = 73;
    private static final int COOK_TIME_Y = 49;
    private static final int COOK_TIME_COLOR = 0xFF404040;

    private final IDrawable icon;
    private final IDrawableAnimated flame;
    private final IDrawableAnimated progressArrow;
    private final List<ItemStack> fuelItems;
    private final Component cookTime;

    public AlloyFurnaceRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.ALLOY_FURNACE));
        flame = guiHelper.createAnimatedRecipeFlame(AlloyFurnaceBlockEntity.COOK_TIME_TICKS);
        progressArrow = guiHelper.drawableBuilder(PROGRESS_ARROW_TEXTURE, 0, 0, PROGRESS_ARROW_WIDTH, PROGRESS_ARROW_HEIGHT)
                .setTextureSize(PROGRESS_ARROW_WIDTH, PROGRESS_ARROW_HEIGHT)
                .buildAnimated(AlloyFurnaceBlockEntity.COOK_TIME_TICKS, IDrawableAnimated.StartDirection.LEFT, false);
        fuelItems = fuelItems();
        cookTime = Component.literal((AlloyFurnaceBlockEntity.COOK_TIME_TICKS / 20) + "s");
    }

    /** Every item with the cooking-fuel data component, the same check {@code AlloyFurnaceBlockEntity} uses. */
    private static List<ItemStack> fuelItems() {
        List<ItemStack> fuels = new ArrayList<>();
        for (Item item : BuiltInRegistries.ITEM) {
            ItemStack stack = new ItemStack(item);
            if (stack.has(DataComponents.COOKING_FUEL)) {
                fuels.add(stack);
            }
        }
        return fuels;
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
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, 0, 0,
                (float) BACKGROUND_SRC_X, (float) BACKGROUND_SRC_Y,
                BACKGROUND_CROP_WIDTH, BACKGROUND_CROP_HEIGHT, 256, 256);
        flame.draw(guiGraphics, FLAME_X, FLAME_Y);
        progressArrow.draw(guiGraphics, PROGRESS_ARROW_X, PROGRESS_ARROW_Y);
        guiGraphics.text(Minecraft.getInstance().font, cookTime, COOK_TIME_X, COOK_TIME_Y, COOK_TIME_COLOR);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<AlloyFurnaceRecipe> recipeHolder, IFocusGroup focuses) {
        AlloyFurnaceRecipe recipe = recipeHolder.value();
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 4).addIngredients(recipe.first());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 38).addIngredients(recipe.second());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 102, 21).addItemStack(recipe.output().create());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, FUEL_SLOT_X, FUEL_SLOT_Y).addItemStacks(fuelItems);
    }
}
