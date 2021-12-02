package RjFx3.startermod.client.renders;

//import com.tristankechlo.livingthings.client.model.entity.ElephantModel;

import RjFx3.startermod.client.models.ElephantModel;
import RjFx3.startermod.entities.ElephantEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElephantRenderer extends MobRenderer<ElephantEntity, ElephantModel<ElephantEntity>>  {
	protected static final ResourceLocation TEXTURE = new ResourceLocation("startermod", "textures/entity/fire_moo.png");//,"textures/entity/elephant/elephant.png");
	protected static final ResourceLocation TEXTURE_ANGRY = new ResourceLocation("startermod","textures/entity/angry_fire_moo.png");
	protected static final ResourceLocation TEXTURE_MILKED = new ResourceLocation("startermod","textures/entity/depleted_moo.png");
	protected static final ResourceLocation TEXTURE_SADDLED = new ResourceLocation("livingthings","textures/entity/elephant/elephant_saddled.png");

	public ElephantRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ElephantModel(), 1F);
	}

	public ResourceLocation getEntityTexture(ElephantEntity entity) {
		return entity.revengerTimerClient > 0   ? TEXTURE_ANGRY : (entity.getMilkTimer() > 0 ? TEXTURE_MILKED : TEXTURE);//entity.isTame() && !entity.func_70631_g_() ? TEXTURE_SADDLED : TEXTURE;
	}
}
