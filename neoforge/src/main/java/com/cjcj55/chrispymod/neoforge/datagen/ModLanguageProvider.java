package com.cjcj55.chrispymod.neoforge.datagen;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.creativetab.ModCreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Locale;
import java.util.Map;

/** English names, derived from the registry ids ("blue_emerald_sword" becomes "Blue Emerald Sword") with a few overrides. */
public class ModLanguageProvider extends LanguageProvider {
    private static final Map<String, String> NAME_OVERRIDES = Map.of(
            "ruby_ore_nether", "Nether Ruby Ore",
            "flame_ore_nether", "Nether Flame Ore",
            "hellfire_ore_nether", "Hellfire Ore",
            "redstone_ingot", "Hardened Redstone Ingot");

    // Legacy colour codes, so the coloured names of the original mod carry over.
    private static final Map<String, String> COLOR_PREFIXES = Map.of(
            "lightning", "§e",
            "flame", "§4",
            "honey", "§6",
            "edible_experience", "§2");

    public ModLanguageProvider(PackOutput output) {
        super(output, ChrispyMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModCreativeModeTabs.ITEM_TAB_TITLE_KEY, "Chrispy Mod");
        add("container." + ChrispyMod.MOD_ID + ".alloy_furnace", "Alloy Furnace");
        for (Identifier id : BuiltInRegistries.ITEM.keySet()) {
            if (id.getNamespace().equals(ChrispyMod.MOD_ID)) {
                Item item = BuiltInRegistries.ITEM.getValue(id);
                add(item.getDescriptionId(), displayName(id.getPath()));
            }
        }
    }

    private static String displayName(String path) {
        String name = NAME_OVERRIDES.getOrDefault(path, titleCase(path));
        for (Map.Entry<String, String> entry : COLOR_PREFIXES.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue() + name;
            }
        }
        return name;
    }

    private static String titleCase(String path) {
        StringBuilder builder = new StringBuilder();
        for (String word : path.split("_")) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(word.substring(0, 1).toUpperCase(Locale.ROOT)).append(word.substring(1));
        }
        return builder.toString();
    }
}
