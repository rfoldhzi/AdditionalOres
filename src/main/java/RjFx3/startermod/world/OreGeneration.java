package RjFx3.startermod.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;

//import  RjFx3.startermod.StarterMod;

//import net.minecraft.block.Blocks;
//import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
//import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
//import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableSet;

import RjFx3.startermod.lists.BlockList;
 
/**
 * Ore generation
 * @author TechOFreak
 *
 */
 
@Mod.EventBusSubscriber
public class OreGeneration {
	
	private static final Logger logger = LogManager.getLogger("startermod"); 
	
    public static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<ConfiguredFeature<?, ?>>();
    public static final ArrayList<ConfiguredFeature<?, ?>> radioactiveStuff = new ArrayList<ConfiguredFeature<?, ?>>();
    public static final ArrayList<ConfiguredFeature<?, ?>> flowers = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();
    //private static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<ConfiguredFeature<?, ?>>();
    
    
   // private static final BlockState DAHLIA = BlockList.dahlia.getDefaultState();
    //public static final BlockClusterFeatureConfig DAHLIA_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DAHLIA), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock())).func_227317_b_().build();
 
    public static void registerOres(){
        //BASE_STONE_OVERWORLD is for generating in stone, granite, diorite, and andesite
        //NETHERRACK is for generating in netherrack
        //BASE_STONE_NETHER is for generating in netherrack, basalt and blackstone
    	
    	BlockState DAHLIA = BlockList.dahlia.getDefaultState();
    	BlockClusterFeatureConfig DAHLIA_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DAHLIA), SimpleBlockPlacer.PLACER)).tries(64).func_227317_b_().build();//.whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock()))
    	BlockClusterFeatureConfig NORMAL_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(DAHLIA, 2), SimpleBlockPlacer.PLACER)).tries(64).build();
    	BlockClusterFeatureConfig GLOWSHROOM_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(BlockList.glowshroom.getDefaultState()), SimpleBlockPlacer.PLACER)).tries(32).func_227317_b_().build();//.whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK.getBlock()))
    	BlockClusterFeatureConfig GLOWSHROOM_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(BlockList.glowshroom.getDefaultState(), 2), SimpleBlockPlacer.PLACER)).tries(32).build();
    	
    	
    	
        //Overworld Ore Register
    	logger.info("Ores have began register");
        overworldOres.add(register("zyron_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockList.zyron_ore.getDefaultState(), 5)) //Vein Size
                .range(23).square() //Spawn height start
                .func_242731_b(24))); //Chunk spawn frequency
        overworldOres.add(register("copper_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockList.copper_ore.getDefaultState(), 6)) //Vein Size
                .range(50).square() //Spawn height start
                .func_242731_b(28))); //Chunk spawn frequency
        overworldOres.add(register("tin_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(
                OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockList.tin_ore.getDefaultState(), 15)) //Vein Size
                .range(100).square() //Spawn height start
                .func_242731_b(9))); //Chunk spawn frequency
        overworldOres.add(register("raw_tin_block", Feature.ORE.withConfiguration(new OreFeatureConfig(
        		new BlockMatchRuleTest(BlockList.tin_ore), BlockList.raw_tin_block.getDefaultState(), 1)) //Vein Size
                .range(64).square() //Spawn height start
                .func_242731_b(1)));
        radioactiveStuff.add(register("contaminated_dirt", Feature.ORE.withConfiguration(new OreFeatureConfig(
        		new BlockMatchRuleTest(Blocks.DIRT), BlockList.contaminated_dirt.getDefaultState(), 25)) //Vein Size
                .range(100).square() //Spawn height start
                .func_242731_b(30))); //Chunk spawn frequency new BlockMatchRuleTest(Blocks.DIRT)
        //flowers.add(register("dahlia", Feature.RANDOM_PATCH.withConfiguration(DAHLIA_CONFIG).withPlacement(Placement.CHANCE.configure(new ChanceConfig(24))))); 
        //flowers.add(register("dahlia", Feature.RANDOM_PATCH.withConfiguration(DAHLIA_CONFIG).withPlacement((Features.Placements.PATCH_PLACEMENT).chance(16))));
        flowers.add(register("dahlia", Feature.FLOWER.withConfiguration(NORMAL_FLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4)));
        flowers.add(register("glowshroom", Feature.FLOWER.withConfiguration(GLOWSHROOM_FLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4)));
        
        logger.info("Ores have been registered");//Features.Placements.PATCH_PLACEMENT).chance(4)
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gen(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        /*
        if(event.getCategory().equals(Biome.Category.NETHER)){
            for(ConfiguredFeature<?, ?> ore : netherOres){
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }
        }
        
        if(event.getCategory().equals(Biome.Category.THEEND)){
            for(ConfiguredFeature<?, ?> ore : endOres){
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            }
        }
        */
        for(ConfiguredFeature<?, ?> ore : overworldOres){
            if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
            //logger.info("ore thing done");
        }
        if (event.getName().toString().equals("startermod:radioactive_swamp")) {
        	for(ConfiguredFeature<?, ?> ore : OreGeneration.radioactiveStuff){
        		logger.info("RADIOACTIVE2. radioactive . (woo-oo-aa-hh, woo-aa-h) "+ore);
                if (ore != null) generation.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
                //logger.info("ore thing done");
            }
        	for(ConfiguredFeature<?, ?> flower : OreGeneration.flowers){
        		logger.info("RADIOACTIVE3"+flower);
                if (flower != null) generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, flower);
                //logger.info("ore thing done");
            }
        }
        
    }
 
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
    	logger.info("did a register of "+name);
    	return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, "startermod:" + name, configuredFeature);
    }
 
}
