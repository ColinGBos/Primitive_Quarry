package vapourdrive.primitive_quarry.setup;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import vapourdrive.primitive_quarry.PrimitiveQuarry;
import vapourdrive.primitive_quarry.content.quarry.QuarryScreen;

@EventBusSubscriber(modid = PrimitiveQuarry.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(Registration.PRIMITIVE_QUARRY_CONTAINER.get(), QuarryScreen::new);
    }
}
