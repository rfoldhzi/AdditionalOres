package RjFx3.startermod.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.entities.PhoniexEntity;
import RjFx3.startermod.lists.ItemList;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("startermod")
public class EntitiesInit {
	public static final String modid = "startermod";
	private static final Logger logger = LogManager.getLogger(modid); 
	
	
	public static EntityType<?> PHONIEX = EntityType.Builder.create(PhoniexEntity::new, EntityClassification.CREATURE).build("startermod:phoniex").setRegistryName(Location("phoniex"));

	public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				//ItemList.phoniex_spawn_egg = registerEntitySpawnEgg(PHONIEX, 0x2f5882, 0x6f1499, "phoniex_spawn_egg")
		);
		logger.info("Spawn egg registered");
	}
	public static void registerEntityWorldSpawns() {
		//registerEntityWorldSpawn(PHONIEX, ForgeRegistries.BIOMES.getValue(Biomes.PLAINS.getRegistryName()));
	}
	public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name) {
		SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(ItemGroup.MISC));
		item.setRegistryName(Location(name));
		return item;
	}
	
	public static void registerEntityWorldSpawn(EntityType<?> entity, Biome...biomes) {
		for (Biome biome : biomes) {
			if (biome != null) {
				biome.getMobSpawnInfo().getSpawners(entity.getClassification()).add(new Spawners(entity, 10,1,10));
			}
		}
	}
	
	
	private static ResourceLocation Location(String name) {
		return new ResourceLocation(modid, name);
	}
}




















