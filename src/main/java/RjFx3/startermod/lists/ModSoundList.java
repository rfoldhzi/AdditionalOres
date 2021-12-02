package RjFx3.startermod.lists;

import RjFx3.startermod.StarterMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModSoundList {
	public static final DeferredRegister<SoundEvent> SOUNDS;
	public static final RegistryObject<SoundEvent> GLOW_SHEEP_BAA;
	//public static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, StarterMod.modid);
	
	//public static final RegistryObject<SoundEvent> GLOW_SHEEP_BAA = SOUNDS.register("entity.glow_sheep_baa", () -> new SoundEvent(new ResourceLocation(StarterMod.modid, "entity.glow_sheep_baa")));
	private static ResourceLocation getSound(String name) {
		return new ResourceLocation("startermod", name);
	}
	
	static {
		SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "startermod");
		GLOW_SHEEP_BAA = SOUNDS.register("glow_sheep_baa", () -> {
			return new SoundEvent(getSound("glow_sheep.ambient"));
		});
	}
}
