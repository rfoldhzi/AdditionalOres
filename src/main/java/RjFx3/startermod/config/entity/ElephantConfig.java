package RjFx3.startermod.config.entity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import RjFx3.startermod.config.StartermodConfig;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ElephantConfig {
	public final BooleanValue canAttack;
	public final DoubleValue health;
	public final DoubleValue speed;
	public final DoubleValue damage;
	public final ConfigValue<List<? extends String>> spawnBiomes;
	public final IntValue weight;
	public final IntValue minSpawns;
	public final IntValue maxSpawns;

	public ElephantConfig(Builder builder) {
		builder.comment("Mob-Config for Elephant").push("Elephant");
		this.canAttack = builder.define("CanAttack", true);
		this.health = builder.comment("requires Client and Server restart").worldRestart().defineInRange("Health",
				60.0D, 1.0D, 32767.0D);
		this.speed = builder
				.comment("requires Client and Server restart | be careful, even small changes can have high a impact")
				.worldRestart().defineInRange("MovementSpeed", 0.25D, 0.05D, 10.0D);
		this.damage = builder.comment("requires Client and Server restart").worldRestart().defineInRange("AttackDamage",
				7.0D, 1.0D, 32767.0D);
		builder.comment(
				"requires Client and Server restart | to disable spawning, leave the array SpawnBoimes empty | can spawn on grass-blocks where lightlevel is higher than 8")
				.push("Spawns");
		this.spawnBiomes = builder.worldRestart().defineList("SpawnBoimes",
				Arrays.asList(Biomes.PLAINS.getRegistryName().toString(),
						Biomes.JUNGLE_EDGE.getRegistryName().toString(),
						Biomes.SAVANNA.getRegistryName().toString(),
						Biomes.SAVANNA_PLATEAU.getRegistryName().toString()),
				(biome) -> {
					return StartermodConfig.checkBiome("Elephant", biome);
				});
		this.weight = builder.worldRestart().defineInRange("SpawnWeight", 15, 1, 32767);
		this.minSpawns = builder.worldRestart().defineInRange("MinSpawns", 3, 1, 32767);
		this.maxSpawns = builder.worldRestart().defineInRange("MaxSpawns", 5, 1, 32767);
		builder.pop();
		builder.pop();
	}
}