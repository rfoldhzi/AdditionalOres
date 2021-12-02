package RjFx3.startermod.items;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ArrowLauncher extends Item{
	private static final Logger logger = LogManager.getLogger("startermod"); 
	public ArrowLauncher(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	private void LaunchArrow(World world, PlayerEntity playerIn, Hand handIn,double x, double y, double z) {
		
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerIn, Hand handIn) {
		logger.info("launch.");
		ItemStack item = playerIn.getHeldItem(handIn);
		Vector3d aim = playerIn.getLookVec();
		
		
		ArrowEntity arrow = new ArrowEntity(world, playerIn);
		arrow.setPosition(playerIn.getPosX() + aim.x * 1.5, playerIn.getPosY() + aim.y * 1.5 + 1.5, playerIn.getPosZ() + aim.z * 1.5);//,playerIn.rotationYaw,playerIn.rotationPitch);
		arrow.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 0.0F);
		world.addEntity(arrow);
		
		ArrowEntity arrow2 = new ArrowEntity(world, playerIn);
		arrow2.setPosition(playerIn.getPosX() + aim.x * 1.5, playerIn.getPosY() + aim.y * 1.5 + 1.5, playerIn.getPosZ() + aim.z * 1.5);//,playerIn.rotationYaw,playerIn.rotationPitch);
		arrow2.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw + 3, 0.0F, 2.0F, 0.0F);
		world.addEntity(arrow2);
		
		ArrowEntity arrow3 = new ArrowEntity(world, playerIn);
		arrow3.setPosition(playerIn.getPosX() + aim.x * 1.5, playerIn.getPosY() + aim.y * 1.5 + 1.5, playerIn.getPosZ() + aim.z * 1.5);//,playerIn.rotationYaw,playerIn.rotationPitch);
		arrow3.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw - 3, 0.0F, 2.0F, 0.0F);
		world.addEntity(arrow3);
		
		ArrowEntity arrow4 = new ArrowEntity(world, playerIn);
		arrow4.setPosition(playerIn.getPosX() + aim.x * 1.5, playerIn.getPosY() + aim.y * 1.5 + 1.5, playerIn.getPosZ() + aim.z * 1.5);//,playerIn.rotationYaw,playerIn.rotationPitch);
		arrow4.func_234612_a_(playerIn, playerIn.rotationPitch + 3, playerIn.rotationYaw, 0.0F, 2.0F, 0.0F);
		world.addEntity(arrow4);
		
		ArrowEntity arrow5 = new ArrowEntity(world, playerIn);
		arrow5.setPosition(playerIn.getPosX() + aim.x * 1.5, playerIn.getPosY() + aim.y * 1.5 + 1.5, playerIn.getPosZ() + aim.z * 1.5);//,playerIn.rotationYaw,playerIn.rotationPitch);
		arrow5.func_234612_a_(playerIn, playerIn.rotationPitch - 3, playerIn.rotationYaw, 0.0F, 2.0F, 0.0F);
		world.addEntity(arrow5);
		
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS,item);
	}
	@Override
	public void onCreated(ItemStack stack, World world, PlayerEntity player) {
		stack.addEnchantment(Enchantment.getEnchantmentByID(2) , 2);
	}
}
