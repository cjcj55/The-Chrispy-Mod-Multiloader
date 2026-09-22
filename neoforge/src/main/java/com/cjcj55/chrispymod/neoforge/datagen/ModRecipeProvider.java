package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.item.ModItems;
import com.cjcj55.chrispymod.common.recipe.AlloyFurnaceRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModRecipeProvider extends RecipeProvider {
    private static final int SMELTING_TICKS = 200;
    private static final int BLASTING_TICKS = 100;

    protected ModRecipeProvider(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    /** Recipes and their unlock advancements are datapack registries, so they are generated through a bootstrap. */
    public static MultiRegistryBootstrap bootstrap() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(Registries.RECIPE, Registries.ADVANCEMENT);
            }

            @Override
            public void run(BootstrapGetter getter) {
                new ModRecipeProvider(getter.get(Registries.RECIPE), getter.get(Registries.ADVANCEMENT)).buildRecipes();
            }
        };
    }

    @Override
    protected void buildRecipes() {
        gemsAndOres();
        toolsAndArmor();
        food();
        blocks();
        machines();
    }

    private void gemsAndOres() {
        gem(ModItems.RUBY, ModBlocks.RUBY_BLOCK, 1.0f, ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.RUBY_ORE_NETHER);
        gem(ModItems.OPAL, ModBlocks.OPAL_BLOCK, 0.8f, ModBlocks.OPAL_ORE, ModBlocks.DEEPSLATE_OPAL_ORE);
        gem(ModItems.TANGERINE, ModBlocks.TANGERINE_BLOCK, 0.8f, ModBlocks.TANGERINE_ORE, ModBlocks.DEEPSLATE_TANGERINE_ORE);
        gem(ModItems.COBALT, ModBlocks.COBALT_BLOCK, 1.0f, ModBlocks.COBALT_ORE, ModBlocks.DEEPSLATE_COBALT_ORE);
        gem(ModItems.PARYTH, ModBlocks.PARYTH_BLOCK, 0.8f, ModBlocks.PARYTH_ORE, ModBlocks.DEEPSLATE_PARYTH_ORE);
        gem(ModItems.FLAME, ModBlocks.FLAME_BLOCK, 1.0f, ModBlocks.FLAME_ORE_NETHER);
        gem(ModItems.BLUE_EMERALD, ModBlocks.BLUE_EMERALD_BLOCK, 0.0f);
        gem(ModItems.REDSTONE_INGOT, ModBlocks.HARDENED_REDSTONE_BLOCK, 0.0f);
        cookOres(ModItems.WHITE_DWARF_STAR, 1.0f, ModBlocks.WHITE_DWARF_STAR_ORE, ModBlocks.DEEPSLATE_WHITE_DWARF_STAR_ORE);
        cookOres(ModItems.NATURAL_ESSENCE, 1.0f, ModBlocks.NATURAL_ESSENCE_ORE, ModBlocks.DEEPSLATE_NATURAL_ESSENCE_ORE);
        cookOres(ModItems.EDIBLE_EXPERIENCE, 1.0f, ModBlocks.EXPERIENCE_ORE, ModBlocks.DEEPSLATE_EXPERIENCE_ORE);
        cookOres(ModItems.HELLFIRE, 0.2f, ModBlocks.HELLFIRE_ORE_NETHER);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.REDSTONE_BLOCK), RecipeCategory.MISC, CookingBookCategory.MISC,
                        ModItems.REDSTONE_INGOT, 2.0f, 300)
                .unlockedBy(getHasName(Items.REDSTONE_BLOCK), has(Items.REDSTONE_BLOCK))
                .save(output, id("redstone_smelting"));

        // Lightning: a gem made from iron and a diamond, and a block made from eight of them around glowstone.
        shaped(RecipeCategory.MISC, ModItems.LIGHTNING)
                .pattern("III").pattern("IDI").pattern("III")
                .define('I', Items.IRON_INGOT).define('D', Items.DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(output);
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIGHTNING_BLOCK)
                .pattern("LLL").pattern("LGL").pattern("LLL")
                .define('L', ModItems.LIGHTNING).define('G', Items.GLOWSTONE)
                .unlockedBy(getHasName(ModItems.LIGHTNING), has(ModItems.LIGHTNING))
                .save(output);
        shapeless(RecipeCategory.MISC, ModItems.LIGHTNING, 8)
                .requires(ModBlocks.LIGHTNING_BLOCK)
                .unlockedBy(getHasName(ModBlocks.LIGHTNING_BLOCK), has(ModBlocks.LIGHTNING_BLOCK))
                .save(output, id("lightning_from_block"));
    }

    private void toolsAndArmor() {
        tools(ModItems.RUBY_TOOLS, ModItems.RUBY, Items.STICK);
        armor(ModItems.RUBY_ARMOR, ModItems.RUBY);
        tools(ModItems.OPAL_TOOLS, ModItems.OPAL, Items.STICK);
        armor(ModItems.OPAL_ARMOR, ModItems.OPAL);
        tools(ModItems.TANGERINE_TOOLS, ModItems.TANGERINE, Items.STICK);
        armor(ModItems.TANGERINE_ARMOR, ModItems.TANGERINE);
        tools(ModItems.COBALT_TOOLS, ModItems.COBALT, Items.STICK);
        armor(ModItems.COBALT_ARMOR, ModItems.COBALT);
        tools(ModItems.BLUE_EMERALD_TOOLS, ModItems.BLUE_EMERALD, Items.DIAMOND);
        armor(ModItems.BLUE_EMERALD_ARMOR, ModItems.BLUE_EMERALD);
        tools(ModItems.PARYTH_TOOLS, ModItems.PARYTH, Items.STICK);
        armor(ModItems.PARYTH_ARMOR, ModItems.PARYTH);
        tools(ModItems.LIGHTNING_TOOLS, ModItems.LIGHTNING, Items.STICK);
        armor(ModItems.LIGHTNING_ARMOR, ModItems.LIGHTNING);
        tools(ModItems.FLAME_TOOLS, ModItems.FLAME, Items.OBSIDIAN);
        armor(ModItems.FLAME_ARMOR, ModItems.FLAME);
        tools(ModItems.REDSTONE_TOOLS, ModItems.REDSTONE_INGOT, Items.STICK);
        armor(ModItems.REDSTONE_ARMOR, ModItems.REDSTONE_INGOT);
        tools(ModItems.EMERALD_TOOLS, Items.EMERALD, Items.STICK);
        armor(ModItems.EMERALD_ARMOR, Items.EMERALD);
        armor(ModItems.WHITE_DWARF_STAR_ARMOR, ModItems.WHITE_DWARF_STAR);

        // Honey armor is made from honey blocks with honeycomb in the top row (and the middle of the chestplate).
        tools(ModItems.HONEY_TOOLS, Items.HONEY_BLOCK, ModItems.HONEY_STICK);
        ItemLike honey = Items.HONEY_BLOCK;
        ItemLike comb = Items.HONEYCOMB;
        shaped(RecipeCategory.COMBAT, ModItems.HONEY_ARMOR.helmet())
                .pattern("HCH").pattern("H H")
                .define('H', honey).define('C', comb)
                .unlockedBy(getHasName(honey), has(honey)).save(output);
        shaped(RecipeCategory.COMBAT, ModItems.HONEY_ARMOR.chestplate())
                .pattern("H H").pattern("HCH").pattern("HHH")
                .define('H', honey).define('C', comb)
                .unlockedBy(getHasName(honey), has(honey)).save(output);
        shaped(RecipeCategory.COMBAT, ModItems.HONEY_ARMOR.leggings())
                .pattern("HCH").pattern("H H").pattern("H H")
                .define('H', honey).define('C', comb)
                .unlockedBy(getHasName(honey), has(honey)).save(output);
        shaped(RecipeCategory.COMBAT, ModItems.HONEY_ARMOR.boots())
                .pattern("H H").pattern("H H")
                .define('H', honey)
                .unlockedBy(getHasName(honey), has(honey)).save(output);
    }

    private void food() {
        shaped(RecipeCategory.FOOD, ModItems.HONEY_STICK, 4)
                .pattern("B").pattern("B")
                .define('B', Items.HONEY_BOTTLE)
                .unlockedBy(getHasName(Items.HONEY_BOTTLE), has(Items.HONEY_BOTTLE))
                .save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CARROT), RecipeCategory.FOOD, CookingBookCategory.FOOD,
                        ModItems.COOKED_CARROT, 0.0f, SMELTING_TICKS)
                .unlockedBy(getHasName(Items.CARROT), has(Items.CARROT))
                .save(output, id("carrot_smelting"));
        for (DyeColor color : DyeColor.values()) {
            Item dye = dye(color);
            shapeless(RecipeCategory.FOOD, ModItems.CANDY_CANES.get(color))
                    .requires(ModBlocks.SUGAR_BLOCK)
                    .requires(dye)
                    .unlockedBy(getHasName(ModBlocks.SUGAR_BLOCK), has(ModBlocks.SUGAR_BLOCK))
                    .save(output);
        }
        shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE)
                .pattern("GGG").pattern("GAG").pattern("GGG")
                .define('G', Items.GOLD_BLOCK).define('A', Items.APPLE)
                .unlockedBy(getHasName(Items.GOLD_BLOCK), has(Items.GOLD_BLOCK))
                .save(output);
    }

    private void blocks() {
        shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUGAR_BLOCK)
                .pattern("SSS").pattern("SSS").pattern("SSS")
                .define('S', Items.SUGAR)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(output);
        storage(Items.SUGAR_CANE, ModBlocks.SUGAR_CANE_BLOCK, "sugar_cane_from_sugar_cane_block");

        for (Block brick : ModBlocks.BRICKS) {
            SingleItemRecipeBuilder.stonecutting(Ingredient.of(Items.BRICKS), RecipeCategory.BUILDING_BLOCKS, brick, 1)
                    .unlockedBy(getHasName(Items.BRICKS), has(Items.BRICKS))
                    .save(output, id(getItemName(brick) + "_from_bricks_stonecutting"));
        }
        ModBlocks.REDSTONE_LAMPS.forEach((color, lamp) -> shapeless(RecipeCategory.REDSTONE, lamp)
                .requires(Items.REDSTONE_LAMP)
                .requires(dye(color))
                .unlockedBy(getHasName(Items.REDSTONE_LAMP), has(Items.REDSTONE_LAMP))
                .save(output));
    }

    private void machines() {
        shapeless(RecipeCategory.MISC, ModBlocks.LAVA_SPONGE)
                .requires(Items.LAVA_BUCKET).requires(Items.SPONGE)
                .unlockedBy(getHasName(Items.SPONGE), has(Items.SPONGE))
                .save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.WET_LAVA_SPONGE), RecipeCategory.MISC, CookingBookCategory.MISC,
                        ModBlocks.LAVA_SPONGE.asItem(), 0.15f, SMELTING_TICKS)
                .unlockedBy(getHasName(ModBlocks.WET_LAVA_SPONGE), has(ModBlocks.WET_LAVA_SPONGE))
                .save(output, id("lava_sponge_from_smelting"));

        // A furnace reinforced with hardened redstone ingots on each side.
        shaped(RecipeCategory.MISC, ModBlocks.ALLOY_FURNACE)
                .pattern(" R ").pattern("RFR").pattern(" R ")
                .define('R', ModItems.REDSTONE_INGOT).define('F', Items.FURNACE)
                .unlockedBy(getHasName(Items.FURNACE), has(Items.FURNACE))
                .save(output);

        alloyFurnaceRecipe("blue_emerald_alloying", Items.DIAMOND, Items.EMERALD, ModItems.BLUE_EMERALD, Items.EMERALD);
    }

    private void alloyFurnaceRecipe(String path, ItemLike first, ItemLike second, ItemLike result, ItemLike unlockItem) {
        AlloyFurnaceRecipe recipe = new AlloyFurnaceRecipe(Ingredient.of(first), Ingredient.of(second), new ItemStackTemplate(result.asItem()));
        Identifier recipeId = ChrispyMod.id(path);
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, recipeId);
        AdvancementHolder advancement = Advancement.Builder.recipeAdvancement()
                .addCriterion(getHasName(unlockItem), has(unlockItem))
                .build(recipeId.withPrefix("recipes/misc/"));
        output.accept(key, recipe, advancement);
    }

    /** A gem that packs into a block and, when an ore is given, is cooked out of that ore. */
    private void gem(Item gem, Block block, float experience, Block... ores) {
        storage(gem, block, getItemName(gem) + "_from_block");
        if (ores.length > 0) {
            cookOres(gem, experience, ores);
        }
    }

    /** Nine of the unpacked item make the packed block, and the block turns back into nine. */
    private void storage(ItemLike unpacked, ItemLike packed, String unpackId) {
        shaped(RecipeCategory.BUILDING_BLOCKS, packed)
                .pattern("UUU").pattern("UUU").pattern("UUU")
                .define('U', unpacked)
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(output);
        shapeless(RecipeCategory.MISC, unpacked, 9)
                .requires(packed)
                .unlockedBy(getHasName(packed), has(packed))
                .save(output, id(unpackId));
    }

    /**
     * Smelting and blasting recipes with explicit mod-namespaced ids. The vanilla helpers save under the
     * `minecraft` namespace, which the mod's data generation would drop.
     */
    private void cookOres(Item result, float experience, Block... ores) {
        for (Block ore : ores) {
            String path = getItemName(result) + "_from_%s_" + getItemName(ore);
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(ore), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, SMELTING_TICKS)
                    .unlockedBy(getHasName(ore), has(ore))
                    .save(output, id(path.formatted("smelting")));
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(ore), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, BLASTING_TICKS)
                    .unlockedBy(getHasName(ore), has(ore))
                    .save(output, id(path.formatted("blasting")));
        }
    }

    private static String id(String path) {
        return ChrispyMod.MOD_ID + ":" + path;
    }

    private void tools(ModItems.ToolSet set, ItemLike material, ItemLike handle) {
        shaped(RecipeCategory.COMBAT, set.sword())
                .pattern("M").pattern("M").pattern("H")
                .define('M', material).define('H', handle)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.TOOLS, set.shovel())
                .pattern("M").pattern("H").pattern("H")
                .define('M', material).define('H', handle)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.TOOLS, set.pickaxe())
                .pattern("MMM").pattern(" H ").pattern(" H ")
                .define('M', material).define('H', handle)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.TOOLS, set.axe())
                .pattern("MM").pattern("MH").pattern(" H")
                .define('M', material).define('H', handle)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.TOOLS, set.hoe())
                .pattern("MM").pattern("H ").pattern("H ")
                .define('M', material).define('H', handle)
                .unlockedBy(getHasName(material), has(material)).save(output);
    }

    private void armor(ModItems.ArmorSet set, ItemLike material) {
        shaped(RecipeCategory.COMBAT, set.helmet())
                .pattern("MMM").pattern("M M")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.COMBAT, set.chestplate())
                .pattern("M M").pattern("MMM").pattern("MMM")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.COMBAT, set.leggings())
                .pattern("MMM").pattern("M M").pattern("M M")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material)).save(output);
        shaped(RecipeCategory.COMBAT, set.boots())
                .pattern("M M").pattern("M M")
                .define('M', material)
                .unlockedBy(getHasName(material), has(material)).save(output);
    }

    private static Item dye(DyeColor color) {
        return BuiltInRegistries.ITEM.getValue(Identifier.withDefaultNamespace(color.getName() + "_dye"));
    }
}
