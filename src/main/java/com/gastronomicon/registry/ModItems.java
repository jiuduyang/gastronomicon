package com.gastronomicon.registry;

import com.gastronomicon.Gastronomicon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * 物品注册表
 */
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
            DeferredRegister.create(ForgeRegistries.ITEMS, Gastronomicon.MOD_ID);
    
    public static final DeferredRegister<CreativeModeTab> TABS = 
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, Gastronomicon.MOD_ID);

    public static final RegistryObject<Item> DRAGON_BREATH_CONFIT = ITEMS.register("dragon_breath_confit",
            () -> new Item(new Item.Properties().food(ModFoods.DRAGON_BREATH_CONFIT).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.dragon_breath_confit.desc").withStyle(ChatFormatting.GREEN));
                }
            });
    
    public static final RegistryObject<Item> VOID_CORE = ITEMS.register("void_core",
            () -> new Item(new Item.Properties().food(ModFoods.VOID_CORE).rarity(Rarity.RARE)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.void_core.desc").withStyle(ChatFormatting.DARK_PURPLE));
                }
            });
    
    public static final RegistryObject<Item> ANCIENT_HEARTBROTH = ITEMS.register("ancient_heartbroth",
            () -> new BowlFoodItem(new Item.Properties().food(ModFoods.ANCIENT_HEARTBROTH).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.ancient_heartbroth.desc").withStyle(ChatFormatting.RED));
                }
            });
    
    public static final RegistryObject<Item> SCULK_RESONANT_FUNGUS = ITEMS.register("sculk_resonant_fungus",
            () -> new Item(new Item.Properties().food(ModFoods.SCULK_RESONANT_FUNGUS).rarity(Rarity.RARE)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.sculk_resonant_fungus.desc").withStyle(ChatFormatting.DARK_AQUA));
                }
            });
    
    public static final RegistryObject<Item> WARDENS_OCULUS = ITEMS.register("wardens_oculus",
            () -> new Item(new Item.Properties().food(ModFoods.WARDENS_OCULUS).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.wardens_oculus.desc").withStyle(ChatFormatting.DARK_GRAY));
                }
            });
    
    public static final RegistryObject<Item> PHILOSOPHERS_TOFFEE = ITEMS.register("philosophers_toffee",
            () -> new Item(new Item.Properties().food(ModFoods.PHILOSOPHERS_TOFFEE).rarity(Rarity.RARE)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.philosophers_toffee.desc").withStyle(ChatFormatting.GOLD));
                }
            });
    
    public static final RegistryObject<Item> CHRONOS_CAKE = ITEMS.register("chronos_cake",
            () -> new Item(new Item.Properties().food(ModFoods.CHRONOS_CAKE).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.chronos_cake.desc").withStyle(ChatFormatting.LIGHT_PURPLE));
                }
            });
    
    public static final RegistryObject<Item> PRISMARINE_POWER_CUBE = ITEMS.register("prismarine_power_cube",
            () -> new Item(new Item.Properties().food(ModFoods.PRISMARINE_POWER_CUBE).rarity(Rarity.RARE)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.prismarine_power_cube.desc").withStyle(ChatFormatting.AQUA));
                }
            });
    
    public static final RegistryObject<Item> BLIGHTED_GOLDEN_APPLE = ITEMS.register("blighted_golden_apple",
            () -> new Item(new Item.Properties().food(ModFoods.BLIGHTED_GOLDEN_APPLE).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.blighted_golden_apple.desc").withStyle(ChatFormatting.DARK_RED));
                }
            });
    
    public static final RegistryObject<Item> GLUTTONS_FEAST = ITEMS.register("gluttons_feast",
            () -> new Item(new Item.Properties().food(ModFoods.GLUTTONS_FEAST).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.gastronomicon.gluttons_feast.desc").withStyle(ChatFormatting.BOLD));
                }
            });

    public static final RegistryObject<CreativeModeTab> GASTRONOMICON_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.gastronomicon.main"))
                    .icon(() -> new ItemStack(Items.ENCHANTED_GOLDEN_APPLE))
                    .displayItems((parameters, output) -> {
                        ITEMS.getEntries().forEach(registryObject -> {
                            output.accept(registryObject.get());
                        });
                    })
                    .build());
}
