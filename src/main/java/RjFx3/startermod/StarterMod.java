package RjFx3.startermod;

import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.blocks.CustomChest;
import RjFx3.startermod.blocks.ModLightBlock;
import RjFx3.startermod.client.RenderHandler;
import RjFx3.startermod.client.renders.PhoniexRenderRegistry;
import RjFx3.startermod.config.StartermodConfig;
import RjFx3.startermod.entities.CloudEntity;
import RjFx3.startermod.entities.ElephantEntity;
import RjFx3.startermod.entities.FireChickenEntity;
import RjFx3.startermod.entities.GlowSheepEntity;
import RjFx3.startermod.entities.ModEntityTypes2;
import RjFx3.startermod.entities.PhoniexEntity;
import RjFx3.startermod.fluids.ModFluids;
import RjFx3.startermod.init.EnchantmentInit;
import RjFx3.startermod.init.EntitiesInit;
import RjFx3.startermod.init.ModEntityTypes;
import RjFx3.startermod.init.PotionInit;
import RjFx3.startermod.items.ArrowLauncher;
import RjFx3.startermod.items.CustomShearsItem;
import RjFx3.startermod.items.ModFood;
import RjFx3.startermod.items.StarterKit;
import RjFx3.startermod.lists.ArmorMaterialList;
import RjFx3.startermod.lists.BlockList;
import RjFx3.startermod.lists.ItemList;
import RjFx3.startermod.lists.ToolMaterialList;
import RjFx3.startermod.recipes.CannedFoodRecipe;
import RjFx3.startermod.lists.ModSoundList;
import RjFx3.startermod.world.OreGeneration;
import RjFx3.startermod.world.biomes.ModBiomes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.loot.LootPool;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.config.ModConfig.Type;

@Mod(StarterMod.modid)
public class StarterMod {
	public static StarterMod instance = new StarterMod();
	public static final String modid = "startermod";
	public static final Logger logger = LogManager.getLogger(modid); 
	public IEventBus modEventBus;//MOD_EVENT_BUS;
	
	public static RegistryKey<Dimension> MINING_DIMENSION;
	public static final IRecipeSerializer<CannedFoodRecipe> CANNED_FOOD_RECIPE = null;

	public StarterMod() {
		instance = this;
		ModLoadingContext.get().registerConfig(Type.COMMON, StartermodConfig.spec);
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		
		ModSoundList.SOUNDS.register(modEventBus);
		
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		MinecraftForge.EVENT_BUS.register(this);
		//MinecraftForge.EVENT_BUS.addListener(this::ClientSetup);
		modEventBus.addListener(this::ClientSetup);
		
		ModBiomes.initialise(modEventBus);
		//modEventBus.addListener(ModBiomes::setupBiomes);
		/*
		logger.info("main thing happened");
		instance = this;
		MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON, StartermodConfig.spec);
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);	
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		
		//ModEntityTypes2.ENTITIES.register(MOD_EVENT_BUS);
		
		//MinecraftForge.EVENT_BUS.register(PhoniexRenderRegistry.class);
		
		ModEntityTypes.ENTITY_TYPES.register(modEventBus);
		modEventBus.addListener(this::ClientSetup);
		//modEventBus.addListener(this::CommonSetup);
		//MinecraftForge.EVENT_BUS.register(new PlaceBlockEventHandler());
		MinecraftForge.EVENT_BUS.register(this);
		
		//MOD_EVENT_BUS.addListener(PhoniexRenderRegistry::setupCommon);
		//MOD_EVENT_BUS.addListener(PhoniexRenderRegistry::setupClient);
		MOD_EVENT_BUS.addListener(this::commonSetup);
		logger.info("mod event bus stuff happened");
		
		//MinecraftForge.EVENT_BUS.register(this);
		 */
	}
	public void ClientSetup(FMLClientSetupEvent event) {
		RenderHandler.registerEntityRenders();
	}

	@SuppressWarnings("deprecation")
	private void setup(final FMLCommonSetupEvent event) {
		logger.info("Setup method begun");
		//instance = new StarterMod();
		
		OreGeneration.registerOres();
		
		MinecraftForge.EVENT_BUS.register(this);
		ModBiomes.setupBiomes(event);
		
		RenderTypeLookup.setRenderLayer(BlockList.dahlia, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(BlockList.glowshroom, RenderType.getCutout());
		
        MINING_DIMENSION = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, new ResourceLocation(modid, "mining"));
		
		DeferredWorkQueue.runLater(() -> {
			logger.info("Attributes may have works");
			GlobalEntityTypeAttributes.put(ModEntityTypes.ELEPHANT_ENTITY.get(), ElephantEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.FIRE_CHICKEN_ENTITY.get(), FireChickenEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.PHONIEX_ENTITY.get(), PhoniexEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.GLOW_SHEEP_ENTITY.get(), GlowSheepEntity.setCustomAttributes().create());
			GlobalEntityTypeAttributes.put(ModEntityTypes.CLOUD_ENTITY.get(), CloudEntity.setCustomAttributes().create());
			 //GlobalEntityTypeAttributes.put(EntitiesInit.PHONIEX, Phoniex.setCustomAttributes().func_233813_a_());
		});
		logger.info("Setup method registered.");
		
	}
	@SuppressWarnings("deprecation")
	private void commonSetup(final FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(() -> {
			logger.info("Attributes may have works");
			GlobalEntityTypeAttributes.put(ModEntityTypes.ELEPHANT_ENTITY.get(), ElephantEntity.setCustomAttributes().create());
			 //GlobalEntityTypeAttributes.put(EntitiesInit.PHONIEX, Phoniex.setCustomAttributes().func_233813_a_());
		});
	}
	//private void clientRegistries(final FMLClientSetupEvent event) {
	//	PhoniexRenderRegistry.registryEntityRenderes();
	//	logger.info("ClientRegistries method registered.");
	//}
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SuppressWarnings("deprecation")
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			
			event.getRegistry().registerAll(
			
					ItemList.starter_item = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("starter_item")),
					
					ItemList.raw_iron = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("raw_iron")),
					ItemList.raw_gold = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("raw_gold")),
					ItemList.raw_copper = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("raw_copper")),
					ItemList.raw_zyron = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("raw_zyron")),
					ItemList.raw_tin = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("raw_tin")),
					
					ItemList.steel_ingot = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_ingot")),
					
					ItemList.steel_axe = new AxeItem(ToolMaterialList.steel, 6.0f, -3.1f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_axe")),
					ItemList.steel_hoe = new HoeItem(ToolMaterialList.steel, -2, -1.0f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_hoe")),
					ItemList.steel_pickaxe = new PickaxeItem(ToolMaterialList.steel, 1, -2.8f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_pickaxe")),
					ItemList.steel_shovel = new ShovelItem(ToolMaterialList.steel, 1.5f, -3.0f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_shovel")),
					ItemList.steel_sword = new SwordItem(ToolMaterialList.steel, 3, -2.4f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_sword")),	
					
					ItemList.steel_helmet = new ArmorItem(ArmorMaterialList.steel, EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_helmet")),
					ItemList.steel_chestplate = new ArmorItem(ArmorMaterialList.steel, EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_chestplate")),
					ItemList.steel_leggings = new ArmorItem(ArmorMaterialList.steel, EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_leggings")),
					ItemList.steel_boots = new ArmorItem(ArmorMaterialList.steel, EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_boots")),
							
					ItemList.copper_ingot = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("copper_ingot")),
							
					ItemList.tin_ingot = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("tin_ingot")),
					ItemList.tin_foil = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("tin_foil")),
					ItemList.tin_foil_hat = new ArmorItem(ArmorMaterialList.tin, EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("tin_foil_hat")),
					ItemList.tin_can = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("tin_can")),
					ItemList.canned_fruit = new Item(new Item.Properties().maxDamage(64).group(ItemGroup.MISC).food(ModFood.BLANK_FOOD)).setRegistryName(Location("canned_fruit")),
					
					ItemList.bronze_ingot = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_ingot")),
					
					ItemList.bronze_axe = new AxeItem(ToolMaterialList.bronze, 6.0f, -3.1f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_axe")),
					ItemList.bronze_hoe = new HoeItem(ToolMaterialList.bronze, -2, -1.0f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_hoe")),
					ItemList.bronze_pickaxe = new PickaxeItem(ToolMaterialList.bronze, 1, -2.8f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_pickaxe")),
					ItemList.bronze_shovel = new ShovelItem(ToolMaterialList.bronze, 1.5f, -3.0f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_shovel")),
					ItemList.bronze_sword = new SwordItem(ToolMaterialList.bronze, 3, -2.4f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_sword")),	
							
					ItemList.bronze_helmet = new ArmorItem(ArmorMaterialList.bronze, EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_helmet")),
					ItemList.bronze_chestplate = new ArmorItem(ArmorMaterialList.bronze, EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_chestplate")),
					ItemList.bronze_leggings = new ArmorItem(ArmorMaterialList.bronze, EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_leggings")),
					ItemList.bronze_boots = new ArmorItem(ArmorMaterialList.bronze, EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("bronze_boots")),
					
					ItemList.zyron_ingot = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_ingot")),
					
					ItemList.zyron_axe = new AxeItem(ToolMaterialList.zyron, 4.0f, -3.2f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_axe")),
					ItemList.zyron_hoe = new HoeItem(ToolMaterialList.zyron, -4, -2.0f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_hoe")),
					ItemList.zyron_pickaxe = new PickaxeItem(ToolMaterialList.zyron, -2, -2.4f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_pickaxe")),
					ItemList.zyron_shovel = new ShovelItem(ToolMaterialList.zyron, -1.5f, -3.0f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_shovel")),
					ItemList.zyron_sword = new SwordItem(ToolMaterialList.zyron, 0, -2.4f, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_sword")),
					
					ItemList.zyron_helmet = new ArmorItem(ArmorMaterialList.zyron, EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_helmet")),
					ItemList.zyron_chestplate = new ArmorItem(ArmorMaterialList.zyron, EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_chestplate")),
					ItemList.zyron_leggings = new ArmorItem(ArmorMaterialList.zyron, EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_leggings")),
					ItemList.zyron_boots = new ArmorItem(ArmorMaterialList.zyron, EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("zyron_boots")),
					
					ItemList.spectral_helmet = new ArmorItem(ArmorMaterialList.spectral, EquipmentSlotType.HEAD,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("spectral_helmet")),
					ItemList.spectral_chestplate = new ArmorItem(ArmorMaterialList.spectral, EquipmentSlotType.CHEST,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("spectral_chestplate")),
					ItemList.spectral_leggings = new ArmorItem(ArmorMaterialList.spectral, EquipmentSlotType.LEGS,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("spectral_leggings")),
					ItemList.spectral_boots = new ArmorItem(ArmorMaterialList.spectral, EquipmentSlotType.FEET,new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("spectral_boots")),
					
					ItemList.steel_shears = new CustomShearsItem(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("steel_shears")),
					
					ItemList.bronze_horse_armor = new HorseArmorItem(6, new ResourceLocation("startermod","textures/entity/horse/armor/horse_armor_bronze.png"), new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)).setRegistryName(Location("bronze_horse_armor")),
					
					ItemList.burning_leather = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("burning_leather")),
					
					//ItemList.oil_bucket = new BucketItem(ModFluids.OIL, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1)).setRegistryName(Location("oil_bucket")),
					
					ItemList.starter_block = new BlockItem(BlockList.starter_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("starter_block")),
					ItemList.raw_iron_block = new BlockItem(BlockList.raw_iron_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.raw_iron_block.getRegistryName()),
					ItemList.raw_gold_block = new BlockItem(BlockList.raw_gold_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.raw_gold_block.getRegistryName()),
					ItemList.raw_copper_block = new BlockItem(BlockList.raw_copper_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.raw_copper_block.getRegistryName()),
					ItemList.raw_zyron_block = new BlockItem(BlockList.raw_zyron_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.raw_zyron_block.getRegistryName()),
					ItemList.raw_tin_block = new BlockItem(BlockList.raw_tin_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.raw_tin_block.getRegistryName()),
					ItemList.zyron_ore = new BlockItem(BlockList.zyron_ore, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.zyron_ore.getRegistryName()),
					ItemList.copper_ore = new BlockItem(BlockList.copper_ore, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.copper_ore.getRegistryName()),
					ItemList.tin_ore = new BlockItem(BlockList.tin_ore, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.tin_ore.getRegistryName()),
					
					ItemList.zyron_block = new BlockItem(BlockList.zyron_block, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.zyron_block.getRegistryName()),
					
					ItemList.stone_bone = new BlockItem(BlockList.stone_bone, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.stone_bone.getRegistryName()),
					ItemList.stone_fossil = new BlockItem(BlockList.stone_fossil, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.stone_fossil.getRegistryName()),
					
					ItemList.contaminated_dirt = new BlockItem(BlockList.contaminated_dirt, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.contaminated_dirt.getRegistryName()),
					ItemList.glow_wool = new BlockItem(BlockList.glow_wool, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.glow_wool.getRegistryName()),
					ItemList.dahlia = new BlockItem(BlockList.dahlia, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.dahlia.getRegistryName()),
					ItemList.glowshroom = new BlockItem(BlockList.glowshroom, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(BlockList.glowshroom.getRegistryName()),
							
					
					
					//ItemList.custom_chest = new BlockItem(BlockList.custom_chest, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("starter_block")),
					
					ItemList.starter_kit_basic = new StarterKit(new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Location("starter_kit_basic"))
			);
			//EntitiesInit.registerEntitySpawnEggs(event);
			logger.info("Items registered.");
		}
		
		@SuppressWarnings("deprecation")
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			
			event.getRegistry().registerAll(
					BlockList.starter_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 3.0f).sound(SoundType.METAL)).setRegistryName(Location("starter_block")),
					BlockList.raw_iron_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("raw_iron_block")),
					BlockList.raw_gold_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("raw_gold_block")),
					BlockList.raw_copper_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("raw_copper_block")),
					BlockList.raw_zyron_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("raw_zyron_block")),
					BlockList.raw_tin_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("raw_tin_block")),
					
					
					BlockList.zyron_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("zyron_ore")),
					BlockList.copper_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("copper_ore")),
					BlockList.tin_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(1.2f, 2.5f).sound(SoundType.METAL).harvestLevel(0).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("tin_ore")),
					
					BlockList.zyron_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 10.0f).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("zyron_block")),
					
					BlockList.stone_bone = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE)).setRegistryName(Location("stone_bone")),
					BlockList.stone_fossil = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 6.0f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool()).setRegistryName(Location("stone_fossil")),
					
					BlockList.contaminated_dirt = new ModLightBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).setLightLevel((state) -> {
					      return 4;
					   })).setRegistryName(Location("contaminated_dirt")),
					BlockList.glow_wool = new ModLightBlock(Block.Properties.create(Material.WOOL, MaterialColor.LIME).hardnessAndResistance(3.0f, 6.0f).hardnessAndResistance(0.8F).sound(SoundType.CLOTH).setLightLevel((state) -> {
					      return 6;
					   })).setRegistryName(Location("glow_wool")),
					
					BlockList.dahlia = new FlowerBlock(Effects.HASTE, 8, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)).setRegistryName(Location("dahlia")),
					BlockList.glowshroom = new MushroomBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.RED).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)).setRegistryName(Location("glowshroom"))
					
					//BlockList.oil = new FlowingFluidBlock(ModFluids.OIL, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()).setRegistryName(Location("oil_block"))
					//BlockList.custom_chest = new CustomChest(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.2f, 5.0f).sound(SoundType.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).setRequiresTool(),(Supplier<TileEntityType<? extends ChestTileEntity>>) TileEntityType.CHEST).setRegistryName(Location("custom_chest"))
			);
			
			logger.info("Blocks registered.");
		}
		
		@SubscribeEvent
		public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
			IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
			//ModEntityTypes.ENTITY_TYPES.register(modEventBus);
			ModEntityTypes.ENTITY_TYPES.register(modEventBus);
			//ModEntityTypes.ENTITY_TYPES.register(modEventBus);
			//event.getRegistry().registerAll(EntitiesInit.PHONIEX);
			
			//GlobalEntityTypeAttributes.put((EntityType<? extends LivingEntity>) ModEntityTypes.PHONIEX.get(),Phoniex.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5F).createMutableAttribute(Attributes.MAX_HEALTH, 20.0F).create());
			
			//MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
			//ModEntityTypes.ENTITIES.register(MOD_EVENT_BUS);
			
			//logger.info("The Phoniex has been registered");
			
			
			//EntitiesInit.registerEntityWorldSpawns();
			//logger.info("World Spawns");
		}
		
		@SubscribeEvent
    	public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {	
    		entityRegistryEvent.getRegistry().registerAll(		    	
    		    EntityType.Builder.create(ElephantEntity::new, EntityClassification.CREATURE).size(1F,1F).build("startermod:elephant").setRegistryName("startermod", "elephant"),
    		    EntityType.Builder.create(FireChickenEntity::new, EntityClassification.CREATURE).size(0.5F,0.5F).build("startermod:fire_chicken").setRegistryName("startermod", "fire_chicken"),
    		    EntityType.Builder.create(PhoniexEntity::new, EntityClassification.MONSTER).size(0.8F,0.5F).build("startermod:phoniex").setRegistryName("startermod", "phoniex"),
    		    EntityType.Builder.create(GlowSheepEntity::new, EntityClassification.CREATURE).size(0.9F,1.3F).build("startermod:glow_sheep").setRegistryName("startermod", "glow_sheep"),
    		    EntityType.Builder.create(CloudEntity::new, EntityClassification.MONSTER).size(0.6F,1.8F).build("startermod:cloud").setRegistryName("startermod", "cloud")
        		
    		);	
    		//Biome h = ForgeRegistries.BIOMES.getValue(Biomes.PLAINS.getLocation());
    		//h.getMobSpawnInfo().getSpawners(EntityClassification.CREATURE).add(new SpawnListEntry(ModEntityTypes.elephant, 10, 1, 10));
    	}
		/*
		@SubscribeEvent
	    public static void onFluidRegistry(RegistryEvent.Register<Fluid> event) {
	        // register a new fluid here
	        event.getRegistry().register(ModFluids.OIL.setRegistryName("oil_flowing"));
	        event.getRegistry().register(ModFluids.FLOWING_OIL.setRegistryName("oil"));
	    }
		*/
		@SubscribeEvent
		public static void onEnchantmentRegister(final RegistryEvent.Register<Enchantment> event) {
			event.getRegistry().registerAll(EnchantmentInit.ENCHANTMENTS.toArray(new Enchantment[0]));
			
		}
		@SubscribeEvent
		public static void onPotionRegister(final RegistryEvent.Register<Potion> event) {
			event.getRegistry().registerAll
			(
					PotionInit.magic_armor_potion = new Potion(new EffectInstance(PotionInit.magic_armor_effect, 3600)).setRegistryName(Location("magic_armor"))
			);
			
		}
		
		@SubscribeEvent
		public static void onEffectRegister(final RegistryEvent.Register<Effect> event) {
			event.getRegistry().registerAll
			(
					PotionInit.magic_armor_effect = new PotionInit.MagicArmorEffect(EffectType.BENEFICIAL, 0x992bff).setRegistryName(Location("magic_armor"))
			);
			
		}
		@SubscribeEvent
	    public static void registerRecipes(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
	        event.getRegistry().register(new SpecialRecipeSerializer<>(CannedFoodRecipe::new).setRegistryName("startermod", "canned_food_recipe"));
	    }
		
		private static ResourceLocation Location(String name) {
			return new ResourceLocation(modid, name);
		}
	}
	@SubscribeEvent(priority = EventPriority.HIGH)
    public void onBiomeLoadingEvent(BiomeLoadingEvent event) {
		logger.info("Biome Loaded event:"+event.getName());
        List<MobSpawnInfo.Spawners> spawns = 
            event.getSpawns().getSpawner(EntityClassification.CREATURE);
        // Remove existing Enderman spawn information
        //spawns.removeIf(e -> e.type == EntityType.FOX);
        String biomeName = event.getName().toString();
        // Make Enderman spawns more frequent and add Blaze spawns in all biomes
        //if (biomeName.equals("minecraft:badlands") || biomeName.equals("minecraft:desert")) {
      if (event.getCategory().equals(Biome.Category.DESERT)) {
        	logger.info("This biome is badlands!");
        	spawns.add(new MobSpawnInfo.Spawners(ModEntityTypes.ELEPHANT_ENTITY.get(), 2, 1, 2));
        	//spawns.add(new MobSpawnInfo.Spawners(EntityType.FOX, 250, 1, 4));
        } else if (event.getCategory().equals(Biome.Category.MESA)) {
        	spawns.add(new MobSpawnInfo.Spawners(ModEntityTypes.ELEPHANT_ENTITY.get(), 3, 2, 4));
        }
        //spawns.add(new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 200, 1, 4));
    }
	/*
	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event) {
		String prefix = "minecraft:chests/";
		String name = event.getName().toString();

		if (name.startsWith(prefix)) {
			String file = name.substring(name.indexOf(prefix) + prefix.length());
			if (file.equals("simple_dungeon")) {
				event.getTable().addPool(getInjectPool(file));
			}
		}
	}
	private LootPool getInjectPool(String entryName) {
		return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "botania_inject_pool");
	}
	private LootEntryTable getInjectEntry(String name, int weight) {
		return new LootEntryTable(new ResourceLocation(modid, "inject/" + name), weight, 0, new LootCondition[0], "botania_inject_entry");
	}
	*/
	public static ResourceLocation Location(String name) {
		return new ResourceLocation(modid, name);
	}
	
}











