package RjFx3.startermod.entities;


import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes2 {
	public static final String modid = "startermod";
	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "startermod");
	
	public static final RegistryObject<EntityType<PhoniexEntity>> PHONIEX = ENTITIES.register("phoniex", () -> EntityType.Builder.<PhoniexEntity>create(PhoniexEntity::new,EntityClassification.CREATURE).size(0.5F, 0.9F).build(new ResourceLocation(modid, "phoniex").toString()));
	
	public static ResourceLocation Location(String name) {
		return new ResourceLocation(modid, name);
	}
}
