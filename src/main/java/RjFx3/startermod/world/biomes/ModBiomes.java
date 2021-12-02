package RjFx3.startermod.world.biomes;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.init.ModEntityTypes;
import RjFx3.startermod.world.OreGeneration;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeGenerationSettings.Builder;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

@Mod.EventBusSubscriber(modid = "startermod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {
	public static final Logger logger = LogManager.getLogger("startermod"); 
	private static boolean isInitialised = false;

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "startermod");

    public static final RegistryObject<Biome> TEST_BIOME = BIOMES.register("test_biome", () ->
        new Biome.Builder()
            .category(Biome.Category.FOREST)
            .withTemperatureModifier(Biome.TemperatureModifier.NONE)
            .withGenerationSettings(new BiomeGenerationSettings.Builder()
                .withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243767_a)
                .withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243768_b)
                .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FANCY_OAK)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_COAL)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GOLD)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_IRON)
                .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIAMOND)
                .withStructure(StructureFeatures.RUINED_PORTAL)
                .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j)
                .build()
            )
            .withMobSpawnSettings(new MobSpawnInfo.Builder()
                .withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 15, 2, 7))
                .withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 25, 3, 8))
                .copy()
            )
            .scale(0.4f)
            .downfall(.4f)
            .precipitation(Biome.RainType.SNOW)
            .temperature(.8f)
            .depth(0.123f)
            .setEffects(new BiomeAmbience.Builder()
                .withGrassColor(0xDA67C1)
                .setFogColor(0xEEEEEE)
                .setWaterColor(0xCF21B8)
                .setWaterFogColor(0xCF78C5)
                .withSkyColor(0xE83452)
                .withFoliageColor(0xCA57C1)
                .build()
            )
            .build()
    );
    
    private static BiomeGenerationSettings getGenerationSettings() {
    	Builder BUILDER = new BiomeGenerationSettings.Builder();
    	BUILDER.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243767_a)
        .withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243768_b)
        .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SWAMP_TREE)
        .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP)
        .withStructure(StructureFeatures.RUINED_PORTAL_SWAMP)
        .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244189_u);
    	DefaultBiomeFeatures.withOverworldOres(BUILDER);
    	 DefaultBiomeFeatures.withNormalMushroomGeneration(BUILDER);
    	logger.info("BUILDING");
    	
    	
    	for(ConfiguredFeature<?, ?> ore : OreGeneration.overworldOres){
    		logger.info("ALL the other ores"+ore);
            if (ore != null) BUILDER.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            //logger.info("ore thing done");
        }
    	for(ConfiguredFeature<?, ?> ore : OreGeneration.radioactiveStuff){
    		logger.info("RADIOACTIVE. radioactive . (woo-oo-aa-hh, woo-aa-h) "+ore);
            if (ore != null) BUILDER.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            //logger.info("ore thing done");
        }
    	for(ConfiguredFeature<?, ?> ore : OreGeneration.flowers){
    		logger.info("RADIOACTIVE. radioactive . (woo-oo-aa-hh, woo-aa-h) "+ore);
            if (ore != null) BUILDER.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ore);
            //logger.info("ore thing done");
        }
		return BUILDER.build();
    	
    }
    private static MobSpawnInfo getMobSettings() {
    	net.minecraft.world.biome.MobSpawnInfo.Builder BUILDER = new MobSpawnInfo.Builder()
    			.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 15, 2, 7))
    			.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.GLOW_SHEEP_ENTITY.get(), 25, 3, 8))
                .withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 4, 1, 3));
    	DefaultBiomeFeatures.withBatsAndHostiles(BUILDER);
		return BUILDER.copy();
    	
    }
    
    
    public static final RegistryObject<Biome> RADIOACTIVE = BIOMES.register("radioactive_swamp", () ->
    new Biome.Builder()
        .category(Biome.Category.SWAMP)
        .withTemperatureModifier(Biome.TemperatureModifier.NONE)
        /*
        .withGenerationSettings(new BiomeGenerationSettings.Builder()
            .withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243767_a)
            .withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243768_b)
            .withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SWAMP_TREE)
            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_COAL)
            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GOLD)
            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_IRON)
            .withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIAMOND)
            .withStructure(StructureFeatures.RUINED_PORTAL)
            .withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244189_u)
            .build()
        )
        */
        .withGenerationSettings(getGenerationSettings())
        .withMobSpawnSettings(getMobSettings())
        .scale(0.2f)
        .depth(-0.3F)
        .downfall(.4f)
        .precipitation(Biome.RainType.RAIN)
        .temperature(.8f)
        //.depth(0.123f)
        
        .setEffects(new BiomeAmbience.Builder()
            .withGrassColor(0x4C5E29)
            .setFogColor(0xEEEEEE)
            .setWaterColor(0x6BFC03)
            .setWaterFogColor(0x2D7500)
            .withSkyColor(0x542d00)
            .withFoliageColor(0x142400)
            .withGrassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP)
            .build()
        )
        .build()
);
   
    public static void initialise(final IEventBus modEventBus) {
		if (isInitialised) {
			throw new IllegalStateException("Already initialised");
		}

		BIOMES.register(modEventBus);

		isInitialised = true;
	}

    
    //@SubscribeEvent
    @SuppressWarnings("deprecation")
	public static void setupBiomes(FMLCommonSetupEvent event) {
    	logger.info("seting up test biome");
        //event.enqueueWork(() ->
    	DeferredWorkQueue.runLater(() -> {
            //setupBiome(TEST_BIOME.get(), BiomeManager.BiomeType.WARM, 1500,
            //	BiomeDictionary.Type.FOREST, BiomeDictionary.Type.COLD, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.WET);
            setupBiome(RADIOACTIVE.get(), BiomeManager.BiomeType.WARM, 300,
                	BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.RARE, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.WET);
            
    	});
    }

    private static void setupBiome(Biome biome, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(
            ForgeRegistries.Keys.BIOMES,
            Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(biome), "Biome registry name was null"));
        logger.info("inside the setupBiome");
        BiomeDictionary.addTypes(key, types);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(key, weight));
    }
}