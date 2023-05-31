package vapourdrive.primitive_quarry.setup;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.content.quarry.QuarryScreen;

@Mod.EventBusSubscriber(modid = PrimitiveQuarry.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void setup(final @NotNull FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(Registration.PRIMITIVE_QUARRY_CONTAINER.get(), QuarryScreen::new));
    }
}
