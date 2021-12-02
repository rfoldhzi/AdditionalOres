package RjFx3.startermod.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.enchantments.Dispel;
import RjFx3.startermod.enchantments.EnchantmentYourEnchantment;
import RjFx3.startermod.enchantments.FoodAbsorbtion;
import RjFx3.startermod.enchantments.Healing;
import RjFx3.startermod.enchantments.Ignition;
import RjFx3.startermod.enchantments.LavaWalker;
import RjFx3.startermod.enchantments.PanicSpeed;
import RjFx3.startermod.enchantments.SpectralDefense;
import RjFx3.startermod.enchantments.Syphon;
import RjFx3.startermod.enchantments.Vengence;
import RjFx3.startermod.entities.GlowSheepEntity;
import RjFx3.startermod.lists.ItemList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="startermod")
public class EnchantmentInit {
	static Random rand = new Random(); 
	
	public static final String modid = "startermod";
	private static final Logger logger = LogManager.getLogger(modid); 
	
	public static final List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
	
	public static final Enchantment YOUR_ENCHANTMENT = new EnchantmentYourEnchantment("your_enchantment",Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND);
	public static final Enchantment FoodAbsorbtion = new FoodAbsorbtion("food_absorbtion",Enchantment.Rarity.UNCOMMON, EquipmentSlotType.CHEST);
	public static final Enchantment lavaWalker = new LavaWalker("lava_walker",Enchantment.Rarity.RARE, EquipmentSlotType.FEET);
	public static final Enchantment panicSpeed = new PanicSpeed("panic_speed",Enchantment.Rarity.UNCOMMON, EquipmentSlotType.FEET);
	public static final Enchantment ignition = new Ignition("ignition",Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND);
	public static final Enchantment healing = new Healing("healing",Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND);
	public static final Enchantment syphon = new Syphon("syphon",Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND);
	public static final Enchantment vengence = new Vengence("vengence",Enchantment.Rarity.UNCOMMON, EquipmentSlotType.HEAD);
	public static final Enchantment spectralDefense = new SpectralDefense("spectral_defense",Enchantment.Rarity.UNCOMMON, EquipmentSlotType.CHEST);
	public static final Enchantment dispel = new Dispel("dispel",Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND);
	
	@SubscribeEvent
	public static void yourEnchantmentsFunction(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		//int level = EnchantmentHelper.getMaxEnchantmentLevel(YOUR_ENCHANTMENT, living);
		//World world = event.getEntity().world;
		if( living instanceof PlayerEntity ) {
			PlayerEntity player = (PlayerEntity) living;
			ItemStack hold = player.getHeldItemMainhand();
			
			Map<Enchantment, Integer> enchants2 = EnchantmentHelper.getEnchantments(hold);
			if (enchants2.get(YOUR_ENCHANTMENT) != null) {
				
				//player.inventory.addItemStackToInventory(new ItemStack(ItemList.starter_item));
			}
			if (player.isPotionActive(PotionInit.magic_armor_effect)) {
				if (!player.getTags().contains("Magiced")) {
					player.addTag("Magiced");
					ItemStack boots = player.inventory.armorItemInSlot(0);
					ItemStack legs = player.inventory.armorItemInSlot(1);
					ItemStack chest = player.inventory.armorItemInSlot(2);
					ItemStack helmet = player.inventory.armorItemInSlot(3);
					if (boots.equals(ItemStack.EMPTY)) {
						player.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(ItemList.spectral_boots));
					}
					if (legs.equals(ItemStack.EMPTY)) {
						player.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(ItemList.spectral_leggings));
					}
					if (chest.equals(ItemStack.EMPTY)) {
						player.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(ItemList.spectral_chestplate));
					}
					if (helmet.equals(ItemStack.EMPTY)) {
						player.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(ItemList.spectral_helmet));
					}
					
				}
			} else {
				//logger.info("No status effects hjere");
				if (player.getTags().contains("Magiced")) {
					player.removeTag("Magiced");
					//logger.info("tHE player Is MAgiC");
					ItemStack boots = player.inventory.armorItemInSlot(0);
					ItemStack legs = player.inventory.armorItemInSlot(1);
					ItemStack chest = player.inventory.armorItemInSlot(2);
					ItemStack helmet = player.inventory.armorItemInSlot(3);
					if (boots.getItem().equals(ItemList.spectral_boots)) {
						player.setItemStackToSlot(EquipmentSlotType.FEET, ItemStack.EMPTY);
						//player.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.AIR));
						//player.setItemStackToSlot(EquipmentSlotType.FEET, ItemStack.EMPTY);
					}
					if (legs.getItem().equals(ItemList.spectral_leggings)) {
						player.setItemStackToSlot(EquipmentSlotType.LEGS, ItemStack.EMPTY);
					}
					if (chest.getItem().equals(ItemList.spectral_chestplate)) {
						player.setItemStackToSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
					}
					if (helmet.getItem().equals(ItemList.spectral_helmet)) {
						player.setItemStackToSlot(EquipmentSlotType.HEAD, ItemStack.EMPTY);
					}
					
					
				}
			
			}
		}
		//player.inventory.addItemStackToInventory(new ItemStack(ItemList.starter_item));// addItemStackToInventory(new ItemStack(mod_whatever.emptyShell,1));
	}
	
	@SubscribeEvent
	public static void LavaWalkerFunction(LivingUpdateEvent event) {
		LivingEntity living = event.getEntityLiving();
		//int level = EnchantmentHelper.getMaxEnchantmentLevel(YOUR_ENCHANTMENT, living);
		if( living instanceof PlayerEntity ) {
			PlayerEntity player = (PlayerEntity) living;
			ItemStack boots = player.inventory.armorItemInSlot(0);
			
			Map<Enchantment, Integer> enchants2 = EnchantmentHelper.getEnchantments(boots);
			if (enchants2.get(lavaWalker) != null) { // Lava Walker enchantment
				((LavaWalker) lavaWalker).stoneNearby(player, event.getEntity().world, player.getPosition(), EnchantmentHelper.getMaxEnchantmentLevel(YOUR_ENCHANTMENT, living));
			}
		}
		//player.inventory.addItemStackToInventory(new ItemStack(ItemList.starter_item));// addItemStackToInventory(new ItemStack(mod_whatever.emptyShell,1));
	}
	
	@SubscribeEvent
	public static void deathEvent(LivingDeathEvent event) {
		LivingEntity living = event.getEntityLiving();
		Entity source = event.getSource().getTrueSource();
		if ( living instanceof EndermanEntity ) {
			if (source instanceof PlayerEntity) {
				PlayerEntity playerAttacker = (PlayerEntity) source;
				ItemStack main = playerAttacker.getHeldItemMainhand();
				if (main.getItem().equals(ItemList.zyron_sword)) {
					playerAttacker.inventory.addItemStackToInventory(new ItemStack(Items.ENDER_PEARL));
					main.damageItem(300, living,null);
					playerAttacker.sendMessage(new TranslationTextComponent("Bonus Ender Pearl gained"), null);
				}
			}
		}
	}
	@SubscribeEvent
	public static void spawnEvent(PlayerRespawnEvent event) {
		LivingEntity living = event.getEntityLiving();
		if( living instanceof PlayerEntity ) {
			PlayerEntity player = (PlayerEntity) living;
			player.inventory.addItemStackToInventory(new ItemStack(Items.COMPASS));
		}
	}
	
	@SubscribeEvent
	public static void yourEnchantmentsFunction2(LivingHurtEvent event) {
		LivingEntity living = event.getEntityLiving();
		Entity source = event.getSource().getTrueSource();
		if( living instanceof PlayerEntity ) {
			PlayerEntity player = (PlayerEntity) living;
			ItemStack chest = player.inventory.armorItemInSlot(2);
			ItemStack boots = player.inventory.armorItemInSlot(0);
			ItemStack helmet = player.inventory.armorItemInSlot(3);
			Map<Enchantment, Integer> enchantschest = EnchantmentHelper.getEnchantments(chest);
			Map<Enchantment, Integer> enchantsboots = EnchantmentHelper.getEnchantments(boots);
			Map<Enchantment, Integer> enchantshelmet = EnchantmentHelper.getEnchantments(helmet);
			if (enchantschest.get(FoodAbsorbtion) != null) { // Food Absorbtion Enchantment
				float dmg = event.getAmount();
				//event.getSource()
				World world = event.getEntity().world;
				player.heal(dmg/2.0F);
				player.addExhaustion(dmg*2.0F);
				world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, SoundCategory.NEUTRAL,1.0F, (float) (Math.random() * 0.4F + 0.8F));// (player, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			}
			if (enchantsboots.get(panicSpeed) != null) { // panic run enchantment
				if (player.getHealth() <= 10 && event.getSource().getTrueSource() instanceof Entity) {
					int level = EnchantmentHelper.getMaxEnchantmentLevel(panicSpeed, living);
					player.addPotionEffect(new EffectInstance(Effects.SPEED,60*level, 0, false, false));
				}
			}
			if (enchantschest.get(spectralDefense) != null) { // spectral Defense enchantment
				if (event.getSource().getTrueSource() instanceof Entity) {
					int level = EnchantmentHelper.getMaxEnchantmentLevel(spectralDefense, living);
					player.addPotionEffect(new EffectInstance(PotionInit.magic_armor_effect,100+100*level, 0, false, false));
				}
			}
			if (enchantshelmet.get(vengence) != null) { // panic run enchantment
				if (source instanceof Entity) {
					int level = EnchantmentHelper.getMaxEnchantmentLevel(vengence, living);
					((LivingEntity) source).addPotionEffect(new EffectInstance(Effects.GLOWING,100*level, 0, true, true));
				}
			}
			if( source instanceof PlayerEntity ) {
				PlayerEntity playerAttacker = (PlayerEntity) source;
				ItemStack main = playerAttacker.getHeldItemMainhand();
				Map<Enchantment, Integer> enchantsMain = EnchantmentHelper.getEnchantments(main);
				if (enchantsMain.get(healing) != null) { // healing enchantment
					int level = EnchantmentHelper.getMaxEnchantmentLevel(healing, playerAttacker);
					player.heal((float)level * event.getAmount()/4.0F);
					event.setCanceled(true);
				}
			}
		} /*else if ( living instanceof EndermanEntity ) {
			if (source instanceof PlayerEntity) {
				PlayerEntity playerAttacker = (PlayerEntity) source;
				ItemStack main = playerAttacker.getHeldItemMainhand();
				if (main.getItem().equals(ItemList.zyron_sword)) {
					//if (living.getHealth() < 1) {
						playerAttacker.inventory.addItemStackToInventory(new ItemStack(Items.ENDER_PEARL));
						main.damageItem(300, living,null);
					//}
				}
			}
		}*/
		
		if (source instanceof PlayerEntity) {
			PlayerEntity playerAttacker = (PlayerEntity) source;
			ItemStack main = playerAttacker.getHeldItemMainhand();
			Map<Enchantment, Integer> enchantsMain = EnchantmentHelper.getEnchantments(main);
			if (enchantsMain.get(syphon) != null) {// Syphon enchantment
				if (rand.nextInt(2) == 0 && playerAttacker.getHealth() < 20) {
					int level = EnchantmentHelper.getMaxEnchantmentLevel(syphon, playerAttacker);
					World world = event.getEntity().world;
					playerAttacker.heal((float)level * event.getAmount()/10.0F);
					playerAttacker.addExhaustion((float)level * event.getAmount()/5.0F);//2*healed = exhaust
					world.playSound(null, playerAttacker.getPosX(), playerAttacker.getPosY(), playerAttacker.getPosZ(),SoundEvents.BLOCK_NOTE_BLOCK_BANJO, SoundCategory.NEUTRAL,1.0F, (float) (Math.random() * 0.4F + 1.0F));
				}
			} else if (enchantsMain.get(dispel) != null) { // dispel enchantment
				living.clearActivePotions();
			}
		}
		if (living instanceof CreeperEntity) {
			CreeperEntity creeper = (CreeperEntity) living;
			if( source instanceof PlayerEntity ) {
				PlayerEntity player = (PlayerEntity) source;
				ItemStack main = player.getHeldItemMainhand();
				Map<Enchantment, Integer> enchantsMain = EnchantmentHelper.getEnchantments(main);
				if (enchantsMain.get(ignition) != null) { // ignition enchantment
					creeper.ignite();
				}
				
			}
		}
	}
	//RIGHT HER CRASH JUST HAPPENED
	@SubscribeEvent 
	public static void onEntityJoin(EntityJoinWorldEvent event) {
		logger.info("ENTITY JOINED WORLD:"+event);
	Entity entity = event.getEntity();
	if (entity instanceof SheepEntity) {
		logger.info("ITS A SHOOP!!");
		SheepEntity sheepEntity = (SheepEntity) entity;
	    sheepEntity.goalSelector.addGoal(3, new AvoidEntityGoal<GlowSheepEntity>(sheepEntity,  GlowSheepEntity.class, 24.0F, 1.1D, 1.6D));
	    }
	}
	
	@SubscribeEvent
	public static void funvtionthign23(LivingEquipmentChangeEvent event) {
		LivingEntity living = (LivingEntity) event.getEntity();
		if (!living.isPotionActive(PotionInit.magic_armor_effect)) {
			EquipmentSlotType slot = event.getSlot();
			Item item = living.getItemStackFromSlot(slot).getItem();
			if (item.equals(ItemList.spectral_boots) || item.equals(ItemList.spectral_leggings) || item.equals(ItemList.spectral_chestplate) || item.equals(ItemList.spectral_helmet)) {
				living.setItemStackToSlot(slot, ItemStack.EMPTY);
			}
		}
		if (living.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.tin_foil_hat)) {
			if (event.getSlot().equals(EquipmentSlotType.HEAD)) {
				ItemStack tinfoil = living.getItemStackFromSlot(EquipmentSlotType.HEAD);
				Map<Enchantment, Integer> enchantsTinFoil = EnchantmentHelper.getEnchantments(tinfoil);
				if (enchantsTinFoil.get(Enchantments.FIRE_PROTECTION) != null) {
					living.addTag("TinFoilFire");
					living.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE,300, 0, true, true));
					living.setFire(15);
				}
				if (enchantsTinFoil.get(Enchantments.BLAST_PROTECTION) != null) {
					living.addTag("TinFoilBlast");
					living.attackEntityFrom(DamageSource.GENERIC, 2);
					living.addPotionEffect(new EffectInstance(Effects.ABSORPTION,300, 1, true, true));
				}
				if (enchantsTinFoil.get(Enchantments.PROJECTILE_PROTECTION) != null) {
					living.addTag("TinFoilEyes");
					living.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION,300, 0, true, true));
					living.addPotionEffect(new EffectInstance(Effects.BLINDNESS,300, 0, true, true));
				}
				if (enchantsTinFoil.get(Enchantments.PROTECTION) != null) {
					living.addTag("TinFoilProtect");
					living.addPotionEffect(new EffectInstance(Effects.RESISTANCE,300, 1, true, true));
					living.addPotionEffect(new EffectInstance(Effects.WEAKNESS,300, 1, true, true));
					living.addPotionEffect(new EffectInstance(Effects.GLOWING,300, 0, false, false));
				}
				if (enchantsTinFoil.get(Enchantments.AQUA_AFFINITY) != null) {
					living.addTag("TinFoilAqua");
					living.addPotionEffect(new EffectInstance(Effects.SLOWNESS,300, 2, true, true));
					living.addPotionEffect(new EffectInstance(Effects.HASTE,300, 2, true, true));
				}
				if (enchantsTinFoil.get(Enchantments.RESPIRATION) != null) {
					if (living.isInWater()) {
						tinfoil.damageItem(1, living, null);
					} else {
						living.attackEntityFrom(DamageSource.GENERIC, 2);
					}
					living.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING,100, 1, true, true));
				}
				if (enchantsTinFoil.get(Enchantments.THORNS) != null) {
					living.addPotionEffect(new EffectInstance(Effects.POISON,50, 0, true, true));
				}
				if (enchantsTinFoil.get(Enchantments.MENDING) != null || enchantsTinFoil.get(Enchantments.UNBREAKING) != null) {
					if (living instanceof PlayerEntity) {
						PlayerEntity playerAttacker = (PlayerEntity) living;
						playerAttacker.addExhaustion(4);
					}
				}
				if (enchantsTinFoil.get(Enchantments.VANISHING_CURSE) != null) {
					living.addTag("TinFoilInvis");
					living.addPotionEffect(new EffectInstance(Effects.INVISIBILITY,300, 0, false, false));
				}
				if (enchantsTinFoil.get(Enchantments.BINDING_CURSE) != null) {
					living.addPotionEffect(new EffectInstance(Effects.LEVITATION,72000, 0, false, false));
				} else {
					living.addPotionEffect(new EffectInstance(Effects.LEVITATION,300, 0, false, false));
				}
				
				if (enchantsTinFoil.get(EnchantmentInit.vengence) != null) {
					tinfoil.damageItem(2, living, null);
					living.addPotionEffect(new EffectInstance(Effects.BAD_OMEN,300, 0, false, false));
					living.addPotionEffect(new EffectInstance(Effects.WITHER,300, 0, false, false));
				}
				
				living.addTag("TinFoil");
				tinfoil.damageItem(1, living, null);
			}
		} else if (living.getTags().contains("TinFoil")) {
			living.removeTag("TinFoil");
			living.removePotionEffect(Effects.LEVITATION);
			if (living.getTags().contains("TinFoilFire")) {
				living.removeTag("TinFoilFire");
				living.removePotionEffect(Effects.FIRE_RESISTANCE);
			}
			if (living.getTags().contains("TinFoilBlast")) {
				living.removeTag("TinFoilBlast");
				living.removePotionEffect(Effects.ABSORPTION);
			}
			if (living.getTags().contains("TinFoilInvis")) {
				living.removeTag("TinFoilInvis");
				living.removePotionEffect(Effects.INVISIBILITY);
			}
			if (living.getTags().contains("TinFoilEyes")) {
				living.removeTag("TinFoilEyes");
				living.removePotionEffect(Effects.NIGHT_VISION);
				living.removePotionEffect(Effects.BLINDNESS);
			}
			if (living.getTags().contains("TinFoilProtect")) {
				living.removeTag("TinFoilProtect");
				living.removePotionEffect(Effects.RESISTANCE);
				living.removePotionEffect(Effects.WEAKNESS);
				living.removePotionEffect(Effects.GLOWING);
			}
			if (living.getTags().contains("TinFoilAqua")) {
				living.removeTag("TinFoilAqua");
				living.removePotionEffect(Effects.HASTE);
				living.removePotionEffect(Effects.SLOWNESS);
			}
			//Collection<EffectInstance> potions = living.effect .getActivePotionEffects();
			//potions.
		}
	}
	
	@SubscribeEvent
	public static void getAppleCore(LivingEntityUseItemEvent.Finish event) {
		if(event.getEntityLiving() instanceof PlayerEntity){
			Entity EntityLiving = event.getEntityLiving();
			PlayerEntity player = (PlayerEntity)EntityLiving;
			ItemStack can = (event.getItem());
			if (can.getItem() == ItemList.canned_fruit) {
				float s = player.getFoodStats().getSaturationLevel();
				int h = player.getFoodStats().getFoodLevel();
				
				
				if (h + s >= can.getMaxDamage() - can.getDamage()) {
					ItemStack newCan = new ItemStack(ItemList.tin_can);
					if (h >= can.getMaxDamage() - can.getDamage()) {
						player.getFoodStats().setFoodLevel(h + can.getMaxDamage() - can.getDamage());
						
						//can.setDamage(can.getMaxDamage());
					} else {
						player.getFoodStats().setFoodLevel(20);
						player.getFoodStats().setFoodSaturationLevel(s + can.getMaxDamage() - can.getDamage());
						//can.setDamage(can.getMaxDamage());
					}
					player.addItemStackToInventory(newCan);
				} else {
					ItemStack newCan = new ItemStack(ItemList.canned_fruit);
					player.getFoodStats().setFoodLevel(20);
					player.getFoodStats().setFoodSaturationLevel(20);
					newCan.setDamage((can.getDamage() + (int) ((20-h)+(20-s))));
					player.addItemStackToInventory(newCan);
				}
			}
		}
	}
}
