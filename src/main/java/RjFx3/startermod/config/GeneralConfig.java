package RjFx3.startermod.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class GeneralConfig {
	public final BooleanValue ambientMode;

	public GeneralConfig(Builder builder) {
		builder.comment("General Configuration").push("General");
		this.ambientMode = builder
				.comment("if set to true no modspecific mobs will attack, overrides the canAttack of every mob")
				.define("AmbientMode", false);
		builder.pop();
	}
}