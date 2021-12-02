package RjFx3.startermod.items;

import RjFx3.startermod.lists.ItemList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class StarterKit extends Item {
	public StarterKit(Item.Properties builder) {
	    super(builder);
	}
	
	public void onCreated(ItemStack stack, World world, PlayerEntity player) {
		player.inventory.addItemStackToInventory(new ItemStack(Items.IRON_SWORD));
		player.inventory.addItemStackToInventory(new ItemStack(Items.IRON_AXE));
		player.inventory.addItemStackToInventory(new ItemStack(Items.BOW));
		player.inventory.addItemStackToInventory(new ItemStack(Items.ARROW, 32));
		player.inventory.addItemStackToInventory(new ItemStack(Items.IRON_HELMET));
		player.inventory.addItemStackToInventory(new ItemStack(Items.IRON_CHESTPLATE));
		player.inventory.addItemStackToInventory(new ItemStack(Items.IRON_LEGGINGS));
		player.inventory.addItemStackToInventory(new ItemStack(Items.IRON_BOOTS));
		player.inventory.addItemStackToInventory(new ItemStack(Items.SHIELD));
	}
}
