package com.gastronomicon.registry;

import com.gastronomicon.Gastronomicon;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 效果注册表
 */
public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = 
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Gastronomicon.MOD_ID);

    public static final RegistryObject<MobEffect> DRAGON_FEVER = MOB_EFFECTS.register("dragon_fever",
            () -> new CustomMobEffect(MobEffectCategory.BENEFICIAL, 0xFF4400));
    
    public static final RegistryObject<MobEffect> VOID_PHASE = MOB_EFFECTS.register("void_phase",
            () -> new CustomMobEffect(MobEffectCategory.BENEFICIAL, 0x9900CC));
    
    public static class CustomMobEffect extends MobEffect {
        public CustomMobEffect(MobEffectCategory category, int color) {
            super(category, color);
        }
    }
}
