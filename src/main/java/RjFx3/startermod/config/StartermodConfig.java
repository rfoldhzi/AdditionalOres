package RjFx3.startermod.config;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig.Loading;
import net.minecraftforge.fml.config.ModConfig.Reloading;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;


import RjFx3.startermod.StarterMod;
import RjFx3.startermod.config.GeneralConfig;
import RjFx3.startermod.config.entity.ElephantConfig;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class StartermodConfig {
	public static final String REQUIRES_RESTART = "requires Client and Server restart";
	public static final String DISABLE_SPAWNING = "to disable spawning, leave the array SpawnBoimes empty";
	public static final String SPAWNING_VANILLA = "can spawn on grass-blocks where lightlevel is higher than 8";
	public static final String SPAWNING_WATER = "can spawn in water";
	public static final String WEIGHTED_RANDOM = "the values are considered as weighted-random";
	public static final String HIGH_IMPACT = "be careful, even small changes can have high a impact";
	public static final double MIN_HEALTH = 1.0D;
	public static final double MAX_HEALTH = 32767.0D;
	public static final double MIN_SPEED = 0.05D;
	public static final double MAX_SPEED = 10.0D;
	public static final double MIN_DAMAGE = 1.0D;
	public static final double MAX_DAMAGE = 32767.0D;
	private static final Builder BUILDER = new Builder();
	public static final GeneralConfig GENERAL;
	public static final ElephantConfig ELEPHANT;
	public static final ForgeConfigSpec spec;

	@SubscribeEvent
	public static void onLoad(Loading configEvent) {
		if (configEvent.getConfig().getModId() == "livingthings") {
			StarterMod.logger.debug("Loaded config file {}", configEvent.getConfig().getFileName());
		}

	}

	@SubscribeEvent
	public static void onFileChange(Reloading configEvent) {
		if (configEvent.getConfig().getModId() == "livingthings") {
			StarterMod.logger.debug("Config just got changed on the file system!");
		}

	}

	public static boolean checkBiome(String name, Object test) {
		if (ForgeRegistries.BIOMES.containsKey(new ResourceLocation(String.valueOf(test)))) {
			return true;
		} else {
			StarterMod.logger.log(Level.INFO,
					"Removing unknown Biome[" + String.valueOf(test) + "] from " + name + "-SpawnBiomes");
			return false;
		}
	}

	static {
		GENERAL = new GeneralConfig(BUILDER);
		ELEPHANT = new ElephantConfig(BUILDER);
		spec = BUILDER.build();
	}
}