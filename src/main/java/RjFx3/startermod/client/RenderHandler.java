package RjFx3.startermod.client;



import RjFx3.startermod.client.renders.CloudRenderer;
import RjFx3.startermod.client.renders.ElephantRenderer;
import RjFx3.startermod.client.renders.FireChickenRenderer;
import RjFx3.startermod.client.renders.GlowSheepRenderer;
import RjFx3.startermod.client.renders.PhoneixRenderer;
import RjFx3.startermod.init.ModEntityTypes;
import RjFx3.startermod.lists.BlockList;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RenderHandler {
	public static void registerEntityRenders() {
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ELEPHANT_ENTITY.get(),ElephantRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FIRE_CHICKEN_ENTITY.get(),FireChickenRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.PHONIEX_ENTITY.get(),PhoneixRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GLOW_SHEEP_ENTITY .get(),GlowSheepRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CLOUD_ENTITY.get(),CloudRenderer::new);
	}
}