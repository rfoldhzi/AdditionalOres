package RjFx3.startermod.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS;

	static {
		ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "livingthings");
	}
}