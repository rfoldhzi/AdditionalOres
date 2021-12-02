package RjFx3.startermod.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Testing1 extends Item{

	public Testing1(Properties properties) {
		
		super(properties.maxDamage(256));
		// TODO Auto-generated constructor stub
	}
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerIn, Hand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);
		
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS,item);
		
	}
}
