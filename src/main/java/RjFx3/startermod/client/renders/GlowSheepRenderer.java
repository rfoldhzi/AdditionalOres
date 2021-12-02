package RjFx3.startermod.client.renders;

import RjFx3.startermod.client.layers.GlowSheepWoolLayer;
import RjFx3.startermod.client.models.GlowSheepModel;
import RjFx3.startermod.client.models.PhoniexModel;
import RjFx3.startermod.entities.GlowSheepEntity;
import RjFx3.startermod.entities.PhoniexEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.entity.model.PhantomModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlowSheepRenderer extends MobRenderer<GlowSheepEntity, GlowSheepModel<GlowSheepEntity>> {
    private static final ResourceLocation SHEEP_TEXTURE = new ResourceLocation("startermod", "textures/entity/sheep/glow_sheep.png");
 
    public GlowSheepRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GlowSheepModel(), 0.7F);
        this.addLayer(new GlowSheepWoolLayer(this));
    }

	@Override
	public ResourceLocation getEntityTexture(GlowSheepEntity entity) {
		// TODO Auto-generated method stub
		return SHEEP_TEXTURE;
	}
}

















