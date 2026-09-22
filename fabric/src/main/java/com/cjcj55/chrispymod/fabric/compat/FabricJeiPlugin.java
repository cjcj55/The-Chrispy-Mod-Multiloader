package com.cjcj55.chrispymod.fabric.compat;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.recipe.AlloyFurnaceRecipe;
import com.cjcj55.chrispymod.common.recipe.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Dev-only: shows every mod item and, for the alloy furnace, its recipes in JEI when JEI happens to be installed.
 * Discovered through the "jei_mod_plugin" entrypoint in fabric.mod.json; NeoForge discovers its own copy via the
 * {@code @JeiPlugin} annotation instead.
 */
public class FabricJeiPlugin implements IModPlugin {
    private static final Identifier UID = ChrispyMod.id("jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AlloyFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(AlloyFurnaceRecipeCategory.TYPE, ModBlocks.ALLOY_FURNACE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AlloyFurnaceRecipeCategory.TYPE, alloyFurnaceRecipes());
    }

    @SuppressWarnings("unchecked")
    private static List<RecipeHolder<AlloyFurnaceRecipe>> alloyFurnaceRecipes() {
        List<RecipeHolder<AlloyFurnaceRecipe>> recipes = new ArrayList<>();
        // In 26.3 the full recipe registry is never synced to a plain client connection (only recipe-book hints
        // are); JEI itself only sees everything because it syncs recipes over its own dev-only networking. This
        // plugin is dev-only too, so read straight from the integrated singleplayer server's RecipeManager instead,
        // which (unlike registryAccess()'s reloadable-registry layer) is guaranteed populated as soon as the server
        // exists, with no sync-timing race.
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) {
            return recipes;
        }
        for (RecipeHolder<?> holder : server.getRecipeManager().getRecipes()) {
            Recipe<?> recipe = holder.value();
            if (recipe.getType() == ModRecipeTypes.ALLOY_FURNACE) {
                recipes.add(new RecipeHolder<>(holder.id(), (AlloyFurnaceRecipe) recipe));
            }
        }
        return recipes;
    }
}
