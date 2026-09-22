package com.cjcj55.chrispymod.common.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;

import java.util.List;

/** The helmet of an armor set. While the whole set is worn, it keeps the given effect active on the wearer. */
public class SetBonusArmorItem extends Item {
    private static final List<EquipmentSlot> ARMOR_SLOTS = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    private final ResourceKey<EquipmentAsset> setAsset;
    private final MobEffectInstance effect;

    public SetBonusArmorItem(Properties properties, ResourceKey<EquipmentAsset> setAsset, MobEffectInstance effect) {
        super(properties);
        this.setAsset = setAsset;
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        if (slot == EquipmentSlot.HEAD && entity instanceof Player player
                && wearsFullSet(player) && !player.hasEffect(effect.getEffect())) {
            player.addEffect(new MobEffectInstance(effect));
        }
    }

    private boolean wearsFullSet(Player player) {
        for (EquipmentSlot armorSlot : ARMOR_SLOTS) {
            Equippable equippable = player.getItemBySlot(armorSlot).get(DataComponents.EQUIPPABLE);
            if (equippable == null || !equippable.assetId().map(setAsset::equals).orElse(false)) {
                return false;
            }
        }
        return true;
    }
}
