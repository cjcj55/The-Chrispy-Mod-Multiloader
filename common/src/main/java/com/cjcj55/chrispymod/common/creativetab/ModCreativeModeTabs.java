package com.cjcj55.chrispymod.common.creativetab;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Shared description of the mod's creative tab. The tab itself is built and registered by each loader,
 * because vanilla's builder needs a fixed grid position while the loaders place modded tabs automatically.
 * <p>
 * The contents are listed by hand, in groups: materials, gear, food, decor. Every item must be listed exactly once;
 * data generation fails otherwise (see {@link #itemsMissingFromTab()} and {@link #itemsListedTwice()}).
 */
public final class ModCreativeModeTabs {
    private ModCreativeModeTabs() {
    }

    public static final Identifier ITEM_TAB_ID = ChrispyMod.id("chrispy_mod_item_tab");
    public static final String ITEM_TAB_TITLE_KEY = "itemGroup." + ChrispyMod.MOD_ID + ".chrispy_mod_item_tab";
    public static final Component ITEM_TAB_TITLE = Component.translatable(ITEM_TAB_TITLE_KEY);

    public static ItemStack itemTabIcon() {
        return new ItemStack(ModItems.BLUE_EMERALD);
    }

    /** Passes every item of the tab, in display order. A consumer is used because the tab's output type is not accessible here. */
    public static void forEachItemTabEntry(Consumer<Item> out) {
        materials(out);
        gear(out);
        food(out);
        decor(out);
    }

    /** Gems, then storage blocks (each group is one row of nine), then ores. */
    private static void materials(Consumer<Item> out) {
        items(out, ModItems.RUBY, ModItems.OPAL, ModItems.TANGERINE, ModItems.COBALT, ModItems.BLUE_EMERALD,
                ModItems.PARYTH, ModItems.LIGHTNING, ModItems.FLAME, ModItems.REDSTONE_INGOT);
        items(out, ModItems.WHITE_DWARF_STAR, ModItems.NATURAL_ESSENCE, ModItems.HELLFIRE, ModItems.EDIBLE_EXPERIENCE);
        blocks(out, ModBlocks.RUBY_BLOCK, ModBlocks.OPAL_BLOCK, ModBlocks.TANGERINE_BLOCK, ModBlocks.COBALT_BLOCK,
                ModBlocks.BLUE_EMERALD_BLOCK, ModBlocks.PARYTH_BLOCK, ModBlocks.LIGHTNING_BLOCK, ModBlocks.FLAME_BLOCK,
                ModBlocks.HARDENED_REDSTONE_BLOCK);
        blocks(out, ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.RUBY_ORE_NETHER,
                ModBlocks.OPAL_ORE, ModBlocks.DEEPSLATE_OPAL_ORE,
                ModBlocks.TANGERINE_ORE, ModBlocks.DEEPSLATE_TANGERINE_ORE,
                ModBlocks.COBALT_ORE, ModBlocks.DEEPSLATE_COBALT_ORE,
                ModBlocks.PARYTH_ORE, ModBlocks.DEEPSLATE_PARYTH_ORE,
                ModBlocks.WHITE_DWARF_STAR_ORE, ModBlocks.DEEPSLATE_WHITE_DWARF_STAR_ORE,
                ModBlocks.NATURAL_ESSENCE_ORE, ModBlocks.DEEPSLATE_NATURAL_ESSENCE_ORE,
                ModBlocks.EXPERIENCE_ORE, ModBlocks.DEEPSLATE_EXPERIENCE_ORE,
                ModBlocks.FLAME_ORE_NETHER, ModBlocks.HELLFIRE_ORE_NETHER);
    }

    /** One row per material: sword, shovel, pickaxe, axe, hoe, helmet, chestplate, leggings, boots. Wands last. */
    private static void gear(Consumer<Item> out) {
        Set<String> materials = new LinkedHashSet<>();
        ModItems.toolSets().forEach(set -> materials.add(set.material()));
        ModItems.armorSets().forEach(set -> materials.add(set.material()));
        for (String material : materials) {
            ModItems.toolSets().stream().filter(set -> set.material().equals(material)).forEach(set ->
                    items(out, set.sword(), set.shovel(), set.pickaxe(), set.axe(), set.hoe()));
            ModItems.armorSets().stream().filter(set -> set.material().equals(material)).forEach(set ->
                    items(out, set.helmet(), set.chestplate(), set.leggings(), set.boots()));
        }
        items(out, ModItems.LIGHTNING_WAND, ModItems.FLAME_WAND);
    }

    private static void food(Consumer<Item> out) {
        items(out, ModItems.HONEY_STICK, ModItems.COOKED_CARROT);
        ModItems.CANDY_CANES.values().forEach(out);
    }

    private static void decor(Consumer<Item> out) {
        blocks(out, ModBlocks.SUGAR_BLOCK, ModBlocks.SUGAR_CANE_BLOCK, ModBlocks.LAVA_SPONGE, ModBlocks.WET_LAVA_SPONGE, ModBlocks.ALLOY_FURNACE);
        ModBlocks.BRICKS.forEach(brick -> out.accept(brick.asItem()));
        ModBlocks.REDSTONE_LAMPS.values().forEach(lamp -> out.accept(lamp.asItem()));
    }

    /** Mod items that the tab does not list. */
    public static Set<Item> itemsMissingFromTab() {
        Set<Item> listed = new HashSet<>();
        forEachItemTabEntry(listed::add);
        Set<Item> missing = new LinkedHashSet<>();
        for (Identifier id : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.getValue(id);
            if (id.getNamespace().equals(ChrispyMod.MOD_ID) && !listed.contains(item)) {
                missing.add(item);
            }
        }
        return missing;
    }

    /** Items that the tab lists more than once. */
    public static List<Item> itemsListedTwice() {
        Set<Item> seen = new HashSet<>();
        List<Item> duplicates = new ArrayList<>();
        forEachItemTabEntry(item -> {
            if (!seen.add(item)) {
                duplicates.add(item);
            }
        });
        return duplicates;
    }

    private static void items(Consumer<Item> out, Item... items) {
        for (Item item : items) {
            out.accept(item);
        }
    }

    private static void blocks(Consumer<Item> out, Block... blocks) {
        for (Block block : blocks) {
            out.accept(block.asItem());
        }
    }
}
