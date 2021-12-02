package RjFx3.startermod.enchantments;

import RjFx3.startermod.init.EnchantmentInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

public class PanicSpeed extends Enchantment {
	public PanicSpeed(String name, Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
	    super(rarityIn, EnchantmentType.ARMOR_FEET, slots);
		//this.setName("your_enchantmnet");
	   	this.setRegistryName("startermod:"+name);
		
	   	EnchantmentInit.ENCHANTMENTS.add(this);
	}

   /**
    * Returns the minimal value of enchantability needed on the enchantment level passed.
    */
   public int getMinEnchantability(int enchantmentLevel) {
      return enchantmentLevel * 10;
   }

   public int getMaxEnchantability(int enchantmentLevel) {
      return this.getMinEnchantability(enchantmentLevel) + 15;
   }

   public boolean isTreasureEnchantment() {
      return true;
   }

   /**
    * Returns the maximum level that the enchantment can have.
    */
   public int getMaxLevel() {
      return 3;
   }

   public boolean canApplyTogether(Enchantment ench) {
      return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER;
   }
}
