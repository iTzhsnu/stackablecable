package dd.itzhsnu.stackablecable.registrys;

import dd.itzhsnu.stackablecable.blocks.Crusher.CrusherScreen;
import dd.itzhsnu.stackablecable.blocks.conduits.ConduitScreen;
import dd.itzhsnu.stackablecable.blocks.generator.GeneratorScreen;
import dd.itzhsnu.stackablecable.scable;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = scable.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientsInit {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(ContainersInit.CRUSHER_CONTAINER.get(), CrusherScreen::new);
        ScreenManager.register(ContainersInit.GENERATOR_CONTAINER.get(), GeneratorScreen::new);
        ScreenManager.register(ContainersInit.CONDUIT_CONTAINER.get(), ConduitScreen::new);
    }
}
