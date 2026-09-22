package com.cjcj55.chrispymod.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public final class ModFoodProperties {
    private ModFoodProperties() {
    }

    private static final float FAST_EATING_SECONDS = 0.8f;
    private static final int CANDY_CANE_DURATION_TICKS = 100;

    public static final FoodProperties CANDY_CANE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible().build();
    public static final FoodProperties COOKED_CARROT = new FoodProperties.Builder().nutrition(4).saturationModifier(4.0f).build();
    public static final FoodProperties HONEY_STICK = new FoodProperties.Builder().nutrition(3).saturationModifier(0.6f).build();

    public static final Consumable FAST_FOOD = Consumables.defaultFood().consumeSeconds(FAST_EATING_SECONDS).build();

    /** Eating a candy cane applies a colour-specific effect. */
    public static Consumable candyCane(DyeColor color) {
        MobEffectInstance effect = switch (color) {
            case BLACK -> effect(MobEffects.BLINDNESS, CANDY_CANE_DURATION_TICKS, 5);
            case BLUE -> effect(MobEffects.WATER_BREATHING, CANDY_CANE_DURATION_TICKS, 5);
            case BROWN -> effect(MobEffects.HUNGER, CANDY_CANE_DURATION_TICKS, 5);
            case CYAN -> effect(MobEffects.JUMP_BOOST, CANDY_CANE_DURATION_TICKS, 5);
            case GRAY -> effect(MobEffects.MINING_FATIGUE, 200, 5);
            case GREEN -> effect(MobEffects.POISON, CANDY_CANE_DURATION_TICKS, 5);
            case LIGHT_BLUE -> effect(MobEffects.DOLPHINS_GRACE, CANDY_CANE_DURATION_TICKS, 5);
            case LIGHT_GRAY -> effect(MobEffects.INVISIBILITY, CANDY_CANE_DURATION_TICKS, 5);
            case LIME -> effect(MobEffects.NAUSEA, CANDY_CANE_DURATION_TICKS, 5);
            case MAGENTA -> effect(MobEffects.INSTANT_DAMAGE, CANDY_CANE_DURATION_TICKS, 4);
            case ORANGE -> effect(MobEffects.GLOWING, CANDY_CANE_DURATION_TICKS, 5);
            case PINK -> effect(MobEffects.STRENGTH, CANDY_CANE_DURATION_TICKS, 2);
            case PURPLE -> effect(MobEffects.LEVITATION, CANDY_CANE_DURATION_TICKS, 5);
            case RED -> effect(MobEffects.INSTANT_HEALTH, CANDY_CANE_DURATION_TICKS, 1);
            case WHITE -> effect(MobEffects.SLOW_FALLING, CANDY_CANE_DURATION_TICKS, 5);
            case YELLOW -> effect(MobEffects.ABSORPTION, CANDY_CANE_DURATION_TICKS, 1);
        };
        return Consumables.defaultFood()
                .consumeSeconds(FAST_EATING_SECONDS)
                .onConsume(new ApplyStatusEffectsConsumeEffect(effect, 1.0f))
                .build();
    }

    private static MobEffectInstance effect(Holder<MobEffect> effect, int durationTicks, int amplifier) {
        return new MobEffectInstance(effect, durationTicks, amplifier);
    }
}
