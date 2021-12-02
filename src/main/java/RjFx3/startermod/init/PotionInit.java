package RjFx3.startermod.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.lists.ItemList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PotionInit {
	
	public static final String modid = "startermod";
	private static final Logger logger = LogManager.getLogger(modid); 
	
	public static Potion magic_armor_potion = null;
	public static Effect magic_armor_effect = null;
	
	public static class MagicArmorEffect extends Effect {

		public MagicArmorEffect(EffectType typeIn, int liquidColorIn) {
			super(typeIn, liquidColorIn);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@SubscribeEvent
	public static void MagicArmorEvent(LivingUpdateEvent event) {
		if( event.getEntityLiving() instanceof PlayerEntity ) {
		PlayerEntity player = (PlayerEntity) event.getEntity();
		logger.info("Player has had a tick");
		if (player.isPotionActive(magic_armor_effect)) {
			logger.info("The effect is activeT");
			if (true){//!player.getTags().contains("Magiced")) {
				ItemStack boots = player.inventory.armorItemInSlot(0);
				ItemStack legs = player.inventory.armorItemInSlot(1);
				ItemStack chest = player.inventory.armorItemInSlot(2);
				ItemStack helmet = player.inventory.armorItemInSlot(3);
				if (boots.equals(ItemStack.EMPTY)) {
					player.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(ItemList.zyron_boots));
				}
				if (legs.equals(ItemStack.EMPTY)) {
					player.setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(ItemList.zyron_leggings));
				}
				if (chest.equals(ItemStack.EMPTY)) {
					player.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(ItemList.zyron_chestplate));
				}
				if (helmet.equals(ItemStack.EMPTY)) {
					player.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(ItemList.zyron_helmet));
				}
				player.addTag("Magiced");
			}
		} else {
			if (player.getTags().contains("Magiced")) {
				
				
			}
		}
		}
	}
}
