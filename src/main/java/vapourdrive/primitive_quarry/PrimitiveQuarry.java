package vapourdrive.primitive_quarry;


import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vapourdrive.primitive_quarry.config.ConfigSettings;
import vapourdrive.primitive_quarry.setup.ClientSetup;
import vapourdrive.primitive_quarry.setup.Registration;

@Mod(PrimitiveQuarry.MODID)
public class PrimitiveQuarry {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "primitivequarry";
    public static final boolean debugMode = true;

    public PrimitiveQuarry() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigSettings.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigSettings.SERVER_CONFIG);

        Registration.init(eventBus);

        // Register the setup method for modloading
        eventBus.addListener(ClientSetup::setup);
        eventBus.addListener(Registration::buildContents);
    }

    public static void debugLog(String toLog) {
        if (isDebugMode()) {
            LOGGER.log(Level.DEBUG, toLog);
        }
    }

    public static boolean isDebugMode() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp") && debugMode;
    }

}
