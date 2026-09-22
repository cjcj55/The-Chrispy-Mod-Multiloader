package com.cjcj55.chrispymod.common.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FlameSwordItem extends Item {
    private static final int FIRE_TICKS = 60;

    public FlameSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setRemainingFireTicks(FIRE_TICKS);
        super.postHurtEnemy(stack, target, attacker);
    }
}
