package com.gastronomicon;

import com.gastronomicon.event.ModEvents;
import com.gastronomicon.registry.ModEffects;
import com.gastronomicon.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Gastronomicon.MOD_ID)
public class Gastronomicon {
    public static final String MOD_ID = "gastronomicon";
    public static final Logger LOGGER = LogManager.getLogger();

    public Gastronomicon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        ModItems.ITEMS.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModItems.TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new ModEvents());
        
        LOGGER.info("Gastronomicon: The Feast Begins");
    }
}
