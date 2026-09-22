package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.item.ModItems;
import com.cjcj55.chrispymod.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ChrispyMod.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        for (Item gem : new Item[]{ModItems.RUBY, ModItems.OPAL, ModItems.COBALT, ModItems.BLUE_EMERALD, ModItems.WHITE_DWARF_STAR}) {
            tag(Tags.Items.GEMS).add(key(gem));
        }

        // What repairs each material's tools and armor
        repairedBy(ModTags.Items.RUBY_REPAIRABLE, ModItems.RUBY);
        repairedBy(ModTags.Items.OPAL_REPAIRABLE, ModItems.OPAL);
        repairedBy(ModTags.Items.TANGERINE_REPAIRABLE, ModItems.TANGERINE);
        repairedBy(ModTags.Items.COBALT_REPAIRABLE, ModItems.COBALT);
        repairedBy(ModTags.Items.BLUE_EMERALD_REPAIRABLE, ModItems.BLUE_EMERALD);
        repairedBy(ModTags.Items.PARYTH_REPAIRABLE, ModItems.PARYTH);
        repairedBy(ModTags.Items.LIGHTNING_REPAIRABLE, ModItems.LIGHTNING);
        repairedBy(ModTags.Items.FLAME_REPAIRABLE, ModItems.FLAME);
        repairedBy(ModTags.Items.REDSTONE_REPAIRABLE, ModItems.REDSTONE_INGOT);
        repairedBy(ModTags.Items.EMERALD_REPAIRABLE, Items.EMERALD);
        repairedBy(ModTags.Items.HONEY_REPAIRABLE, Items.HONEY_BLOCK);
        repairedBy(ModTags.Items.WHITE_DWARF_STAR_REPAIRABLE, ModItems.WHITE_DWARF_STAR);

        // Vanilla tags that make enchanting, sweeping and similar mechanics recognise the tools and armor
        for (ModItems.ToolSet set : ModItems.toolSets()) {
            tag(ItemTags.SWORDS).add(key(set.sword()));
            tag(ItemTags.SHOVELS).add(key(set.shovel()));
            tag(ItemTags.PICKAXES).add(key(set.pickaxe()));
            tag(ItemTags.AXES).add(key(set.axe()));
            tag(ItemTags.HOES).add(key(set.hoe()));
        }
        for (ModItems.ArmorSet set : ModItems.armorSets()) {
            tag(ItemTags.HEAD_ARMOR).add(key(set.helmet()));
            tag(ItemTags.CHEST_ARMOR).add(key(set.chestplate()));
            tag(ItemTags.LEG_ARMOR).add(key(set.leggings()));
            tag(ItemTags.FOOT_ARMOR).add(key(set.boots()));
            for (Item piece : new Item[]{set.helmet(), set.chestplate(), set.leggings(), set.boots()}) {
                tag(ItemTags.TRIMMABLE_ARMOR).add(key(piece));
            }
        }
    }

    private void repairedBy(TagKey<Item> tag, Item repairItem) {
        tag(tag).add(key(repairItem));
    }

    private static ResourceKey<Item> key(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
    }
}
