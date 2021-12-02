package RjFx3.startermod.entities.projectiles;

import RjFx3.startermod.lists.ItemList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class WindProjectileEntity extends DamagingProjectileEntity {

	public WindProjectileEntity(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
		super(p_i50173_1_, p_i50173_2_);
		// TODO Auto-generated constructor stub
	}
	
	public WindProjectileEntity(EntityType<? extends DamagingProjectileEntity> p_i50175_1_, LivingEntity p_i50175_2_,
			double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_) {
		super(p_i50175_1_, p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
		// TODO Auto-generated constructor stub
	}
	
	//@Override
	protected Item getDefaultItem() {
		return ItemList.bronze_axe.getItem();
	}
	
	
}
