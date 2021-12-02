package RjFx3.startermod.client.renders;

import RjFx3.startermod.client.models.CloudModel;
import RjFx3.startermod.entities.CloudEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloudRenderer extends MobRenderer<CloudEntity, CloudModel<CloudEntity>> {
    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("startermod", "textures/entity/cloud.png");
 
    public CloudRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new CloudModel(), 0.5F);
	}

	@Override
	public ResourceLocation getEntityTexture(CloudEntity entity) {
		// TODO Auto-generated method stub
		return CLOUD_TEXTURE;
	}
}

















