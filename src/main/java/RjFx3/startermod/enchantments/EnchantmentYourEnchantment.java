package RjFx3.startermod.enchantments;

import RjFx3.startermod.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class EnchantmentYourEnchantment extends Enchantment {
	public EnchantmentYourEnchantment (String name, Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
		super(rarityIn, EnchantmentType.WEAPON, slots);
		//this.setName("your_enchantmnet");
		this.setRegistryName("startermod:"+name);
		
		EnchantmentInit.ENCHANTMENTS.add(this);
	}
	
	public int getMinEnchantability(int enchantmentLevel) {
		return 5 + 20 * (enchantmentLevel - 1);
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	   /**
	    * Returns the maximum level that the enchantment can have.
	    */
	public int getMaxLevel() {
		return 1;
	}
	//
    //public boolean canApply(ItemStack stack)
    //{
    //    return stack.getItem() instanceof ArmorItem && ((ArmorItem)stack.getItem()).getEquipmentSlot() == EquipmentSlotType.CHEST;
    //}
}
