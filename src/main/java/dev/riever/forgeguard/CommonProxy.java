package dev.riever.forgeguard;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        ForgeGuard.LOG.info("I am ForgeGuard at version " + Tags.VERSION);
    }
}
