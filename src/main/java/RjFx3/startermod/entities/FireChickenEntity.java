package RjFx3.startermod.entities;


import java.util.UUID;

import RjFx3.startermod.config.StartermodConfig;
import RjFx3.startermod.init.ModEntityTypes;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FireChickenEntity extends AnimalEntity implements IAngerable {
	private static final Ingredient BREEDING_ITEMS;
	private static final ResourceLocation LEXICON_ENTRY;
	private static final RangedInteger rangedInteger;
	private int angerTime;
	private int attackTimer;
	private UUID angerTarget;

	public FireChickenEntity(EntityType<? extends FireChickenEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		//this.field_70138_W = 1.0F;
	}

	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity parent) {
		FireChickenEntity child = (FireChickenEntity) ((EntityType) ModEntityTypes.FIRE_CHICKEN_ENTITY.get()).create(this.world);
				//.func_200721_a(this.field_70170_p);
		if (this.isTame() || ((FireChickenEntity) parent).isTame()) {
			child.setTame(true);
		}

		return child;
	}

	private void setTame(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private boolean isTame() {
		// TODO Auto-generated method stub
		return false;
	}

	public static MutableAttribute getAttributes() {
		return MobEntity.registerAttributes()
				.createMutableAttribute(Attributes.MAX_HEALTH, (Double) StartermodConfig.ELEPHANT.health.get())
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, (Double) StartermodConfig.ELEPHANT.speed.get())
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE , (Double) StartermodConfig.ELEPHANT.damage.get());
	}
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F).createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 5.0D);
    }

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		//this.goalSelector.addGoal(1, new BetterMeleeAttackGoal(this, 1.2D, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.9D));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.95D));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
	}

	public void func_70037_a(CompoundNBT compound) {
		//super.func_70037_a(compound);
		//if (this.field_70170_p instanceof ServerWorld) {
		//	this.func_241358_a_((ServerWorld) this.field_70170_p, compound);
		//}

	}

	public void func_213281_b(CompoundNBT compound) {
		//super.func_213281_b(compound);
		//this.func_233682_c_(compound);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return BREEDING_ITEMS.test(stack);
	}

	public boolean func_70652_k(Entity target) {
		this.attackTimer = 10;
		//this.field_70170_p.func_72960_a(this, (byte) 4);
		//boolean flag = target.func_70097_a(DamageSource.func_76358_a(this),
		//		(float) this.func_233637_b_(Attributes.field_233823_f_));
		//if (flag) {
		//	target.func_213317_d(target.func_213322_ci().func_72441_c(0.0D, 0.7D, 0.0D));
		//	this.func_174815_a(this, target);
		//}

		return false;//flag;
	}

	public void tick() {
		super.tick();
		if (this.attackTimer > 0) {
			--this.attackTimer;
		}

	}

	public int func_70641_bl() {
		return (Integer) StartermodConfig.ELEPHANT.maxSpawns.get();
	}

	protected float func_213348_b(Pose poseIn, EntitySize sizeIn) {
		return 1.3F;//this.func_70631_g_() ? 1.3F : 2.25F;
	}

	public double func_70042_X() {
		return 2.45D;
	}

	protected SoundEvent getAmbientSound() {
		return (SoundEvent) SoundEvents.ENTITY_COW_AMBIENT; //ModSounds.ELEPHANT_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return (SoundEvent) SoundEvents.ENTITY_COW_HURT;//ModSounds.ELEPHANT_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return (SoundEvent) SoundEvents.ENTITY_COW_DEATH;//ModSounds.ELEPHANT_DEATH.get();
	}

	@OnlyIn(Dist.CLIENT)
	public void func_70103_a(byte id) {
		if (id == 4) {
			this.attackTimer = 10;
		} else {
			//super.func_70103_a(id);
		}

	}

	@OnlyIn(Dist.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}
	
	@Override
	public int getAngerTime() {
		return this.angerTime;
	}
	
	@Override
	public void setAngerTime(int time) {
		this.angerTime = time;
	}
	
	@Override
	public UUID getAngerTarget() {
		return this.angerTarget;
	}
	
	@Override
	public void setAngerTarget(UUID target) {
		this.angerTarget = target;
	}

	public void func_230258_H__() {
		//this.func_230260_a__(rangedInteger.func_233018_a_(this.field_70146_Z));
	}

	public ResourceLocation getLexiconEntry() {
		super.getLootTable();
		return LEXICON_ENTRY;
	}

	static {
		BREEDING_ITEMS = Ingredient.fromItems(new IItemProvider[]{Items.PUMPKIN_SEEDS});
		LEXICON_ENTRY = new ResourceLocation("livingthings", "neutral_mobs/elephant");
		rangedInteger = TickRangeConverter.convertRange(20, 39);
	}


}