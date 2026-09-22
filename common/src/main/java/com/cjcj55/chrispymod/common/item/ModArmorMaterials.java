package com.cjcj55.chrispymod.common.item;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.tag.ModTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public final class ModArmorMaterials {
    private ModArmorMaterials() {
    }

    public static final ResourceKey<EquipmentAsset> RUBY_ASSET = assetKey("ruby");
    public static final ResourceKey<EquipmentAsset> OPAL_ASSET = assetKey("opal");
    public static final ResourceKey<EquipmentAsset> TANGERINE_ASSET = assetKey("tangerine");
    public static final ResourceKey<EquipmentAsset> COBALT_ASSET = assetKey("cobalt");
    public static final ResourceKey<EquipmentAsset> BLUE_EMERALD_ASSET = assetKey("blue_emerald");
    public static final ResourceKey<EquipmentAsset> PARYTH_ASSET = assetKey("paryth");
    public static final ResourceKey<EquipmentAsset> LIGHTNING_ASSET = assetKey("lightning");
    public static final ResourceKey<EquipmentAsset> FLAME_ASSET = assetKey("flame");
    public static final ResourceKey<EquipmentAsset> REDSTONE_ASSET = assetKey("redstone");
    public static final ResourceKey<EquipmentAsset> EMERALD_ASSET = assetKey("emerald");
    public static final ResourceKey<EquipmentAsset> HONEY_ASSET = assetKey("honey");
    public static final ResourceKey<EquipmentAsset> WHITE_DWARF_STAR_ASSET = assetKey("white_dwarf_star");

    // durability multiplier, defense (boots, leggings, chestplate, helmet, body), enchantability, equip sound, toughness, repair tag, asset
    public static final ArmorMaterial RUBY = new ArmorMaterial(15, defense(2, 5, 6, 2, 3), 14, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, ModTags.Items.RUBY_REPAIRABLE, RUBY_ASSET);
    public static final ArmorMaterial OPAL = new ArmorMaterial(12, defense(1, 4, 5, 1, 2), 17, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, ModTags.Items.OPAL_REPAIRABLE, OPAL_ASSET);
    public static final ArmorMaterial TANGERINE = new ArmorMaterial(8, defense(2, 4, 5, 1, 2), 8, SoundEvents.ARMOR_EQUIP_IRON, 0f, 0f, ModTags.Items.TANGERINE_REPAIRABLE, TANGERINE_ASSET);
    public static final ArmorMaterial COBALT = new ArmorMaterial(20, defense(2, 5, 6, 3, 3), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, ModTags.Items.COBALT_REPAIRABLE, COBALT_ASSET);
    public static final ArmorMaterial BLUE_EMERALD = new ArmorMaterial(30, defense(3, 6, 9, 4, 4), 11, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, ModTags.Items.BLUE_EMERALD_REPAIRABLE, BLUE_EMERALD_ASSET);
    public static final ArmorMaterial PARYTH = new ArmorMaterial(10, defense(1, 5, 6, 2, 3), 6, SoundEvents.ARMOR_EQUIP_GOLD, 0f, 0f, ModTags.Items.PARYTH_REPAIRABLE, PARYTH_ASSET);
    public static final ArmorMaterial LIGHTNING = new ArmorMaterial(23, defense(2, 5, 6, 3, 3), 9, SoundEvents.ARMOR_EQUIP_DIAMOND, 1f, 0f, ModTags.Items.LIGHTNING_REPAIRABLE, LIGHTNING_ASSET);
    public static final ArmorMaterial FLAME = new ArmorMaterial(13, defense(2, 4, 5, 2, 2), 12, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, ModTags.Items.FLAME_REPAIRABLE, FLAME_ASSET);
    public static final ArmorMaterial REDSTONE = new ArmorMaterial(15, defense(2, 4, 5, 2, 2), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, ModTags.Items.REDSTONE_REPAIRABLE, REDSTONE_ASSET);
    public static final ArmorMaterial EMERALD = new ArmorMaterial(28, defense(3, 6, 7, 3, 3), 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, ModTags.Items.EMERALD_REPAIRABLE, EMERALD_ASSET);
    public static final ArmorMaterial HONEY = new ArmorMaterial(20, defense(1, 2, 3, 1, 1), 15, SoundEvents.ARMOR_EQUIP_TURTLE, 0f, 0f, ModTags.Items.HONEY_REPAIRABLE, HONEY_ASSET);
    public static final ArmorMaterial WHITE_DWARF_STAR = new ArmorMaterial(12, defense(2, 5, 6, 2, 2), 16, SoundEvents.ARMOR_EQUIP_NETHERITE, 0f, 0f, ModTags.Items.WHITE_DWARF_STAR_REPAIRABLE, WHITE_DWARF_STAR_ASSET);

    private static ResourceKey<EquipmentAsset> assetKey(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ChrispyMod.id(name));
    }

    private static Map<ArmorType, Integer> defense(int boots, int leggings, int chestplate, int helmet, int body) {
        Map<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.BOOTS, boots);
        map.put(ArmorType.LEGGINGS, leggings);
        map.put(ArmorType.CHESTPLATE, chestplate);
        map.put(ArmorType.HELMET, helmet);
        map.put(ArmorType.BODY, body);
        return map;
    }
}
