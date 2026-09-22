package com.cjcj55.chrispymod.common.item;

import com.cjcj55.chrispymod.common.ChrispyMod;
import com.cjcj55.chrispymod.common.block.ModBlocks;
import com.cjcj55.chrispymod.common.item.custom.EdibleExperienceItem;
import com.cjcj55.chrispymod.common.item.custom.FlameSwordItem;
import com.cjcj55.chrispymod.common.item.custom.FlameWandItem;
import com.cjcj55.chrispymod.common.item.custom.LightningSwordItem;
import com.cjcj55.chrispymod.common.item.custom.LightningWandItem;
import com.cjcj55.chrispymod.common.item.custom.SetBonusArmorItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ModItems {
    private ModItems() {
    }

    public record ToolSet(String material, Item sword, Item shovel, Item pickaxe, Item axe, Item hoe) {
    }

    public record ArmorSet(String material, ResourceKey<EquipmentAsset> asset, Item helmet, Item chestplate, Item leggings, Item boots) {
    }

    private static final List<ToolSet> TOOL_SETS = new ArrayList<>();
    private static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();

    private static final int SET_BONUS_EFFECT_TICKS = 60;

    // Materials
    public static final Item RUBY = registerItem("ruby", Item::new);
    public static final Item OPAL = registerItem("opal", Item::new);
    public static final Item TANGERINE = registerItem("tangerine", Item::new);
    public static final Item COBALT = registerItem("cobalt", Item::new);
    public static final Item BLUE_EMERALD = registerItem("blue_emerald", Item::new);
    public static final Item PARYTH = registerItem("paryth", Item::new);
    public static final Item LIGHTNING = registerItem("lightning", Item::new);
    public static final Item FLAME = registerItem("flame", Item::new);
    public static final Item REDSTONE_INGOT = registerItem("redstone_ingot", Item::new);
    public static final Item HELLFIRE = registerItem("hellfire",
            properties -> new Item(properties.cookingFuel(ContextIntProviders.COOKING_TIME_BLAZE_ROD)));
    public static final Item WHITE_DWARF_STAR = registerItem("white_dwarf_star",
            properties -> new Item(properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));
    public static final Item NATURAL_ESSENCE = registerItem("natural_essence", Item::new);
    public static final Item EDIBLE_EXPERIENCE = registerItem("edible_experience",
            properties -> new EdibleExperienceItem(properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    // Food
    public static final Item HONEY_STICK = registerItem("honey_stick",
            properties -> new Item(properties.food(ModFoodProperties.HONEY_STICK, ModFoodProperties.FAST_FOOD)));
    public static final Item COOKED_CARROT = registerItem("cooked_carrot",
            properties -> new Item(properties.food(ModFoodProperties.COOKED_CARROT)));
    public static final Map<DyeColor, Item> CANDY_CANES = new EnumMap<>(DyeColor.class);

    static {
        for (DyeColor color : DyeColor.values()) {
            CANDY_CANES.put(color, registerItem(color.getName() + "_candy_cane",
                    properties -> new Item(properties.food(ModFoodProperties.CANDY_CANE, ModFoodProperties.candyCane(color)))));
        }
    }

    // Wands
    public static final Item LIGHTNING_WAND = registerItem("lightning_wand", properties -> new LightningWandItem(properties.durability(64)));
    public static final Item FLAME_WAND = registerItem("flame_wand", properties -> new FlameWandItem(properties.durability(64)));

    // Tools and armor
    public static final ToolSet RUBY_TOOLS = registerToolSet("ruby", ModToolMaterials.RUBY, Item::new);
    public static final ArmorSet RUBY_ARMOR = registerArmorSet("ruby", ModArmorMaterials.RUBY, Item::new);
    public static final ToolSet OPAL_TOOLS = registerToolSet("opal", ModToolMaterials.OPAL, Item::new);
    public static final ArmorSet OPAL_ARMOR = registerArmorSet("opal", ModArmorMaterials.OPAL, Item::new);
    public static final ToolSet TANGERINE_TOOLS = registerToolSet("tangerine", ModToolMaterials.TANGERINE, Item::new);
    public static final ArmorSet TANGERINE_ARMOR = registerArmorSet("tangerine", ModArmorMaterials.TANGERINE, Item::new);
    public static final ToolSet COBALT_TOOLS = registerToolSet("cobalt", ModToolMaterials.COBALT, Item::new);
    public static final ArmorSet COBALT_ARMOR = registerArmorSet("cobalt", ModArmorMaterials.COBALT, Item::new);
    public static final ToolSet BLUE_EMERALD_TOOLS = registerToolSet("blue_emerald", ModToolMaterials.BLUE_EMERALD, Item::new);
    public static final ArmorSet BLUE_EMERALD_ARMOR = registerArmorSet("blue_emerald", ModArmorMaterials.BLUE_EMERALD, Item::new);
    public static final ToolSet PARYTH_TOOLS = registerToolSet("paryth", ModToolMaterials.PARYTH, Item::new);
    public static final ArmorSet PARYTH_ARMOR = registerArmorSet("paryth", ModArmorMaterials.PARYTH, Item::new);
    public static final ToolSet LIGHTNING_TOOLS = registerToolSet("lightning", ModToolMaterials.LIGHTNING, LightningSwordItem::new);
    public static final ArmorSet LIGHTNING_ARMOR = registerArmorSet("lightning", ModArmorMaterials.LIGHTNING, Item::new);
    public static final ToolSet FLAME_TOOLS = registerToolSet("flame", ModToolMaterials.FLAME, FlameSwordItem::new);
    public static final ArmorSet FLAME_ARMOR = registerArmorSet("flame", ModArmorMaterials.FLAME,
            properties -> setBonusHelmet(properties, ModArmorMaterials.FLAME_ASSET, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, SET_BONUS_EFFECT_TICKS, 0, false, false)));
    public static final ToolSet REDSTONE_TOOLS = registerToolSet("redstone", ModToolMaterials.REDSTONE, Item::new);
    public static final ArmorSet REDSTONE_ARMOR = registerArmorSet("redstone", ModArmorMaterials.REDSTONE, Item::new);
    public static final ToolSet EMERALD_TOOLS = registerToolSet("emerald", ModToolMaterials.EMERALD, Item::new);
    public static final ArmorSet EMERALD_ARMOR = registerArmorSet("emerald", ModArmorMaterials.EMERALD, Item::new);
    public static final ToolSet HONEY_TOOLS = registerToolSet("honey", ModToolMaterials.HONEY, Item::new);
    public static final ArmorSet HONEY_ARMOR = registerArmorSet("honey", ModArmorMaterials.HONEY,
            properties -> setBonusHelmet(properties, ModArmorMaterials.HONEY_ASSET, new MobEffectInstance(MobEffects.REGENERATION, SET_BONUS_EFFECT_TICKS, 0, false, false)));
    public static final ArmorSet WHITE_DWARF_STAR_ARMOR = registerArmorSet("white_dwarf_star", ModArmorMaterials.WHITE_DWARF_STAR,
            properties -> setBonusHelmet(properties, ModArmorMaterials.WHITE_DWARF_STAR_ASSET, new MobEffectInstance(MobEffects.SPEED, SET_BONUS_EFFECT_TICKS, 2, false, false)));

    public static List<ToolSet> toolSets() {
        return Collections.unmodifiableList(TOOL_SETS);
    }

    public static List<ArmorSet> armorSets() {
        return Collections.unmodifiableList(ARMOR_SETS);
    }

    /**
     * Registers all items, then a block item for every mod block. Must be called while the item registry is open,
     * and after {@link ModBlocks#register()}. The plain items are registered by static initialization.
     */
    public static void register() {
        ModBlocks.blocks().forEach(ModItems::registerBlockItem);
        ChrispyMod.LOGGER.debug("Registered mod items");
    }

    private static void registerBlockItem(Identifier id, Block block) {
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()
                .useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, id))));
    }

    /** The sword factory receives properties that already carry the sword attributes. */
    private static ToolSet registerToolSet(String material, ToolMaterial toolMaterial, Function<Item.Properties, Item> swordFactory) {
        ToolSet set = new ToolSet(material,
                registerItem(material + "_sword", properties -> swordFactory.apply(properties.sword(toolMaterial, 3.0f, -2.4f))),
                registerItem(material + "_shovel", properties -> new Item(properties.shovel(toolMaterial, 1.5f, -3.0f))),
                registerItem(material + "_pickaxe", properties -> new Item(properties.pickaxe(toolMaterial, 1.0f, -2.8f))),
                registerItem(material + "_axe", properties -> new Item(properties.axe(toolMaterial, 6.0f, -3.2f))),
                registerItem(material + "_hoe", properties -> new Item(properties.hoe(toolMaterial, 0.0f, -3.0f))));
        TOOL_SETS.add(set);
        return set;
    }

    /** The helmet factory receives properties that already carry the helmet's armor components. */
    private static ArmorSet registerArmorSet(String material, ArmorMaterial armorMaterial, Function<Item.Properties, Item> helmetFactory) {
        ArmorSet set = new ArmorSet(material, armorMaterial.assetId(),
                registerItem(material + "_helmet", properties -> helmetFactory.apply(properties.humanoidArmor(armorMaterial, ArmorType.HELMET))),
                registerItem(material + "_chestplate", properties -> new Item(properties.humanoidArmor(armorMaterial, ArmorType.CHESTPLATE))),
                registerItem(material + "_leggings", properties -> new Item(properties.humanoidArmor(armorMaterial, ArmorType.LEGGINGS))),
                registerItem(material + "_boots", properties -> new Item(properties.humanoidArmor(armorMaterial, ArmorType.BOOTS))));
        ARMOR_SETS.add(set);
        return set;
    }

    private static Item setBonusHelmet(Item.Properties properties, ResourceKey<EquipmentAsset> asset, MobEffectInstance effect) {
        return new SetBonusArmorItem(properties, asset, effect);
    }

    private static Item registerItem(String name, Function<Item.Properties, Item> factory) {
        Identifier id = ChrispyMod.id(name);
        return Registry.register(BuiltInRegistries.ITEM, id,
                factory.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
    }
}
