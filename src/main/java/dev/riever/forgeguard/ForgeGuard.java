package dev.riever.forgeguard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = ForgeGuard.MODID,
    version = Tags.VERSION,
    name = "ForgeGuard",
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*")
public class ForgeGuard {

    public static final String MODID = "forgeguard";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(serverSide = "dev.riever.forgeguard.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
}
