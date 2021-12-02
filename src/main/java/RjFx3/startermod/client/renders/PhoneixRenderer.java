package RjFx3.startermod.client.renders;

import RjFx3.startermod.client.models.PhoniexModel;
import RjFx3.startermod.entities.PhoniexEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhoneixRenderer extends MobRenderer<PhoniexEntity, PhoniexModel<PhoniexEntity>> {
    private static final ResourceLocation PHONIEX_TEXTURES = new ResourceLocation("startermod", "textures/entity/phoniex.png");
 
    public PhoneixRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PhoniexModel(), 0.7F);
    }

	@Override
	public ResourceLocation getEntityTexture(PhoniexEntity entity) {
		// TODO Auto-generated method stub
		return PHONIEX_TEXTURES;
	}
}

















