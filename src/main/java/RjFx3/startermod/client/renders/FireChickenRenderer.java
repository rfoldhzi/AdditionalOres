package RjFx3.startermod.client.renders;

//import com.tristankechlo.livingthings.client.model.entity.ElephantModel;

import RjFx3.startermod.client.models.ElephantModel;
import RjFx3.startermod.client.models.FireChickenModel;
import RjFx3.startermod.entities.ElephantEntity;
import RjFx3.startermod.entities.FireChickenEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireChickenRenderer extends MobRenderer<FireChickenEntity, FireChickenModel<FireChickenEntity>>  {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("startermod", "textures/entity/fire_chicken.png");//,"textures/entity/elephant/elephant.png");
	protected static final ResourceLocation TEXTURE_SADDLED = new ResourceLocation("livingthings","textures/entity/elephant/elephant_saddled.png");

	public FireChickenRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new FireChickenModel(), 0.5F);
	}

	public ResourceLocation getEntityTexture(FireChickenEntity entity) {
		return TEXTURE;//entity.isTame() && !entity.func_70631_g_() ? TEXTURE_SADDLED : TEXTURE;
	}

}
