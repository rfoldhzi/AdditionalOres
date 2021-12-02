package RjFx3.startermod.client.models;

import RjFx3.startermod.entities.ElephantEntity;

//import com.tristankechlo.livingthings.entities.ElephantEntity;

import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElephantModel<T extends ElephantEntity> extends CowModel<T> {
	
}
