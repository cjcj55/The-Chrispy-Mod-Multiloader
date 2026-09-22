package com.cjcj55.chrispymod.common.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LightningSwordItem extends Item {
    public LightningSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel serverLevel) {
            EntityTypes.LIGHTNING_BOLT.spawn(serverLevel, null, attacker, target.blockPosition(), EntitySpawnReason.TRIGGERED, true, true);
        }
        super.postHurtEnemy(stack, target, attacker);
    }
}
