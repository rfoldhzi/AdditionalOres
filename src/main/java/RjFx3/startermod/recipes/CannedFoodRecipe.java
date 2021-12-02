package RjFx3.startermod.recipes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.StarterMod;
import RjFx3.startermod.lists.ItemList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CannedFoodRecipe extends SpecialRecipe{
	
	public static final String modid = "startermod";
	public static final Logger logger = LogManager.getLogger(modid); 

	public CannedFoodRecipe(ResourceLocation idIn) {
		super(idIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		logger.info("Trigger1");
		ResourceLocation Tag = new ResourceLocation("startermod", "food_can");
		boolean flag = false;
	    boolean flag1 = false;
		// TODO Auto-generated method stub
	    for(int i = 0; i < inv.getSizeInventory(); ++i) {
	         ItemStack itemstack = inv.getStackInSlot(i);
	         if (!itemstack.isEmpty()) {
	        	if ((itemstack.getItem() == ItemList.tin_can || itemstack.getItem() == ItemList.canned_fruit) && !flag) {
	        		if (itemstack.getItem() == ItemList.canned_fruit && itemstack.getDamage() == 0) {
	        			return false;
	        		}
	        		flag = true;
	        	} else {
	        		logger.info("Trigger2");
	        		if (itemstack.getItem().getTags().contains(Tag)) {
	        			logger.info("Trigger3");
	                    flag1 = true;
	                 } else {
	                	 logger.info("Trigger4");
	                	 return false;
	                 }
	        	}
	         }
	      }
	    
	    
		return flag && flag1;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		logger.info("getCrafting result");
		ResourceLocation Tag = new ResourceLocation("startermod", "food_can");
		ItemStack can = new ItemStack(ItemList.canned_fruit);
		int total = 0 ;
		
		for(int i = 0; i < inv.getSizeInventory(); ++i) {
	         ItemStack itemstack = inv.getStackInSlot(i);
	         if (!itemstack.isEmpty() && itemstack.getItem().getTags().contains(Tag)) {
	            total += itemstack.getItem().getFood().getSaturation();
	            total += itemstack.getItem().getFood().getHealing();
	         } else if (!itemstack.isEmpty() && itemstack.getItem() == ItemList.canned_fruit){
	        	 total += can.getMaxDamage() - itemstack.getDamage();
	         }
	      }
		
		can.setDamage(can.getMaxDamage()-total);
		
		return can;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		// TODO Auto-generated method stub
		return StarterMod.CANNED_FOOD_RECIPE;
	}

}
