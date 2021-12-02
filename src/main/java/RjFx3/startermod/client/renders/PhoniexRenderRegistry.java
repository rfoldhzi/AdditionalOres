package RjFx3.startermod.client.renders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.entities.ModEntityTypes2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

//@OnlyIn(Dist.CLIENT)
public class PhoniexRenderRegistry {
	public static final String modid = "startermod";
	private static final Logger logger = LogManager.getLogger(modid); 

	@SubscribeEvent
	public static void setupCommon(final FMLCommonSetupEvent event) {}

	@SubscribeEvent
	public static void setupClient(final FMLClientSetupEvent event) {
		logger.info("THIS IS THE RENDER");
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes2.PHONIEX.get(), PhoneixRenderer::new);
	}
}
