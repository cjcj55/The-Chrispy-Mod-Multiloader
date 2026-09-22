package com.cjcj55.chrispymod.common.tag;

import com.cjcj55.chrispymod.common.ChrispyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModTags {
    private ModTags() {
    }

    public static final class Items {
        private Items() {
        }

        public static final TagKey<Item> RUBY_REPAIRABLE = create("ruby_repairable");
        public static final TagKey<Item> OPAL_REPAIRABLE = create("opal_repairable");
        public static final TagKey<Item> TANGERINE_REPAIRABLE = create("tangerine_repairable");
        public static final TagKey<Item> COBALT_REPAIRABLE = create("cobalt_repairable");
        public static final TagKey<Item> BLUE_EMERALD_REPAIRABLE = create("blue_emerald_repairable");
        public static final TagKey<Item> PARYTH_REPAIRABLE = create("paryth_repairable");
        public static final TagKey<Item> LIGHTNING_REPAIRABLE = create("lightning_repairable");
        public static final TagKey<Item> FLAME_REPAIRABLE = create("flame_repairable");
        public static final TagKey<Item> REDSTONE_REPAIRABLE = create("redstone_repairable");
        public static final TagKey<Item> EMERALD_REPAIRABLE = create("emerald_repairable");
        public static final TagKey<Item> HONEY_REPAIRABLE = create("honey_repairable");
        public static final TagKey<Item> WHITE_DWARF_STAR_REPAIRABLE = create("white_dwarf_star_repairable");

        private static TagKey<Item> create(String name) {
            return TagKey.create(Registries.ITEM, ChrispyMod.id(name));
        }
    }
}
