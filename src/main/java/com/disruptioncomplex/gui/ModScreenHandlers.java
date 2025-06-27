package com.disruptioncomplex.gui;

import com.disruptioncomplex.MurkysManyFish;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<FishTankScreenHandler> FISH_TANK_SCREEN_HANDLER;
    public static ScreenHandlerType<CrabTrapScreenHandler> CRAB_TRAP_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        MurkysManyFish.LOGGER.info("Registering Screen Handlers for " + MurkysManyFish.MOD_ID);
        
        CRAB_TRAP_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
            RegistryKey.of(RegistryKeys.SCREEN_HANDLER, Identifier.of(MurkysManyFish.MOD_ID, "crab_trap")),
            new ScreenHandlerType<>(CrabTrapScreenHandler::new, CrabTrapScreenHandler.REQUIRED_FEATURES)
        );


        FISH_TANK_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                RegistryKey.of(RegistryKeys.SCREEN_HANDLER, Identifier.of(MurkysManyFish.MOD_ID, "fish_tank")),
                new ScreenHandlerType<>(FishTankScreenHandler::new, FishTankScreenHandler.REQUIRED_FEATURES)
        );
    }
}