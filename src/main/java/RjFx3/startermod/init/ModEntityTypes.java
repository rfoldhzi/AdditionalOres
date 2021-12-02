package RjFx3.startermod.init;



import RjFx3.startermod.StarterMod;
import RjFx3.startermod.entities.CloudEntity;
import RjFx3.startermod.entities.ElephantEntity;
import RjFx3.startermod.entities.FireChickenEntity;
import RjFx3.startermod.entities.GlowSheepEntity;
import RjFx3.startermod.entities.PhoniexEntity;
import RjFx3.startermod.entities.projectiles.WindProjectileEntity;
import RjFx3.startermod.init.ModItems;

//import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES;
	private static final Properties spawn_egg_props;
	public static final EntityType<ElephantEntity> elephant;
	public static final EntityType<FireChickenEntity> firechicken;
	public static final EntityType<PhoniexEntity> phoniex;
	public static final EntityType<GlowSheepEntity> glowsheep;
	public static final EntityType<CloudEntity> cloud;
	public static final EntityType<WindProjectileEntity> wind;
	public static final RegistryObject<EntityType<ElephantEntity>> ELEPHANT_ENTITY;
	public static final RegistryObject<EntityType<FireChickenEntity>> FIRE_CHICKEN_ENTITY;
	public static final RegistryObject<EntityType<PhoniexEntity>> PHONIEX_ENTITY;
	public static final RegistryObject<EntityType<GlowSheepEntity>> GLOW_SHEEP_ENTITY;
	public static final RegistryObject<EntityType<CloudEntity>> CLOUD_ENTITY;
	public static final RegistryObject<EntityType<WindProjectileEntity>> WIND_ENTITY;
	public static final RegistryObject<Item> ELEPHANT_SPAWN_EGG;

	private static <T extends Entity> EntityType<T> createStandardEntityType(String entity_name, IFactory<T> factory,
			EntityClassification classification, float width, float height) {
		StarterMod.logger.debug("I THINK IT WORKED"+entity_name);
		return Builder.create(factory, classification).size(width, height).build("startermod:" + entity_name);
	}

	static {
		ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "startermod");
		spawn_egg_props = (new Properties()).group(ItemGroup.MISC);
		elephant = createStandardEntityType("elephant", ElephantEntity::new, EntityClassification.CREATURE, 1.85F,
				2.7F);
		firechicken = createStandardEntityType("fire_chicken", FireChickenEntity::new, EntityClassification.CREATURE, .5F,
				.5F);
		phoniex = createStandardEntityType("phoniex", PhoniexEntity::new, EntityClassification.MONSTER, 1.85F,
				0.45F);
		glowsheep = createStandardEntityType("glow_sheep", GlowSheepEntity::new, EntityClassification.CREATURE, .8F,
				.8F);
		cloud = createStandardEntityType("cloud", CloudEntity::new, EntityClassification.MONSTER, 1F,
				1F);
		//wind = EntityType.Builder.<WindProjectileEntity>create(WindProjectileEntity::new, EntityClassification.MONSTER).size(1F, 1F).build("startermod:wind");//
		wind = createStandardEntityType("wind", WindProjectileEntity::new, EntityClassification.MISC, 1F,1F);
		ELEPHANT_ENTITY = ENTITY_TYPES.register("elephant", () -> {
			return elephant;
		});
		FIRE_CHICKEN_ENTITY = ENTITY_TYPES.register("fire_chicken", () -> {
			return firechicken;
		});
		PHONIEX_ENTITY = ENTITY_TYPES.register("phoniex", () -> {
			return phoniex;
		});
		GLOW_SHEEP_ENTITY = ENTITY_TYPES.register("glow_sheep", () -> {
			return glowsheep;
		});
		CLOUD_ENTITY = ENTITY_TYPES.register("cloud", () -> {
			return cloud;
		});
		WIND_ENTITY = ENTITY_TYPES.register("wind", () -> {
			return wind;
		});
		
		ELEPHANT_SPAWN_EGG = ModItems.ITEMS.register("elephant_spawn_egg", () -> {
			return new SpawnEggItem(elephant, 0, 5131854, spawn_egg_props);
		});
	}

	private static ResourceLocation Location(String name) {
		return new ResourceLocation("startermod", name);
	}
}