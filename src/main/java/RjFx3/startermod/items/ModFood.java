package RjFx3.startermod.items;

import net.minecraft.item.Food;

public class ModFood {
	public static final Food BLANK_FOOD;
	
	static {
		BLANK_FOOD = (new Food.Builder()).hunger(0).saturation(0).setAlwaysEdible().build();
	}
	
}
