package com.gastronomicon.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    
    public static final FoodProperties DRAGON_BREATH_CONFIT = new FoodProperties.Builder()
            .nutrition(10)
            .saturationMod(1.0f)
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0), 1.0f)
            .build();

    public static final FoodProperties VOID_CORE = new FoodProperties.Builder()
            .nutrition(8)
            .saturationMod(1.125f)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 200, 0), 1.0f)
            .build();

    public static final FoodProperties ANCIENT_HEARTBROTH = new FoodProperties.Builder()
            .nutrition(12)
            .saturationMod(0.9167f)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2400, 0), 1.0f)
            .build();

    public static final FoodProperties SCULK_RESONANT_FUNGUS = new FoodProperties.Builder()
            .nutrition(7)
            .saturationMod(1.143f)
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 2400, 0), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 2400, 0), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 2400, 0), 1.0f)
            .build();

    public static final FoodProperties WARDENS_OCULUS = new FoodProperties.Builder()
            .nutrition(14)
            .saturationMod(0.857f)
            .alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), 1.0f)
            .build();

    public static final FoodProperties PHILOSOPHERS_TOFFEE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(3.125f)
            .build();

    public static final FoodProperties CHRONOS_CAKE = new FoodProperties.Builder()
            .nutrition(8)
            .saturationMod(1.0625f)
            .build();

    public static final FoodProperties PRISMARINE_POWER_CUBE = new FoodProperties.Builder()
            .nutrition(9)
            .saturationMod(1.056f)
            .build();

    public static final FoodProperties BLIGHTED_GOLDEN_APPLE = new FoodProperties.Builder()
            .nutrition(12)
            .saturationMod(0.833f)
            .alwaysEat()
            .build();

    public static final FoodProperties GLUTTONS_FEAST = new FoodProperties.Builder()
            .nutrition(20)
            .saturationMod(0.75f)
            .alwaysEat()
            .build();
}
