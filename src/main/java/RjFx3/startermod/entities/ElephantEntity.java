package RjFx3.startermod.entities;


import java.util.Random;
import java.util.UUID;

import RjFx3.startermod.config.StartermodConfig;
import RjFx3.startermod.init.ModEntityTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElephantEntity extends AnimalEntity implements IAngerable,IRangedAttackMob {
	Random rnd = new Random();
	private static final Ingredient BREEDING_ITEMS;
	private static final ResourceLocation LEXICON_ENTRY;
	private static final RangedInteger rangedInteger;
	private int angerTime;
	private int attackTimer;
	private int milkTimer;
	public int revengerTimerClient;
	private UUID angerTarget;

	public ElephantEntity(EntityType<? extends ElephantEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.stepHeight = 1.0F;
		this.milkTimer = 0;
		this.revengerTimerClient = 0;
	}

	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity parent) {
		ElephantEntity child = (ElephantEntity) ((EntityType) ModEntityTypes.ELEPHANT_ENTITY.get()).create(this.world);
				//.func_200721_a(this.field_70170_p);
		if (this.isTame() || ((ElephantEntity) parent).isTame()) {
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
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE , (Double) StartermodConfig.ELEPHANT.damage.get());
	}
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F).createMutableAttribute(Attributes.MAX_HEALTH, 20.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
    }

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		//this.goalSelector.addGoal(1, new RangedAttackGoal((IRangedAttackMob) this, 1.0D, 20, 10.0F));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.95D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.9D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		
		this.targetSelector.addGoal(0, new ElephantEntity.NewHurtByTargetGoal(this));//.setCallsForHelp(ModEntityTypes.ELEPHANT_ENTITY.getClass()));
		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (this.world instanceof ServerWorld) {
			this.readAngerNBT((ServerWorld) this.world, compound);
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		this.writeAngerNBT(compound);
	}
	
	public boolean isBreedingItem(ItemStack stack) {
		return BREEDING_ITEMS.test(stack);
	}

	@Override
	public boolean attackEntityAsMob(Entity target) {
		this.attackTimer = 40;
		this.world.setEntityState(this, (byte) 4);
		//this.launchFireBall((LivingEntity) target);
		//boolean flag = true;
		boolean flag = target.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
		if (flag) {
			// throw target in the air
			//target.setMotion(target.getMotion().add(0.0D, 0.7D, 0.0D));
			this.applyEnchantments(this, target);
		}
		return flag;
	}
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		this.attackTimer = 40;
		this.launchFireBall((LivingEntity) target);
	}
	
	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
	      ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
	      if (itemstack.getItem() == Items.BUCKET && !this.isChild() && milkTimer <= 0) {
	    	 this.milkTimer = 24000;
	         p_230254_1_.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
	         ItemStack itemstack1 = DrinkHelper.fill(itemstack, p_230254_1_, Items.LAVA_BUCKET.getDefaultInstance());
	         p_230254_1_.setHeldItem(p_230254_2_, itemstack1);
	         return ActionResultType.func_233537_a_(this.world.isRemote);
	      } else if(itemstack.getItem() == Items.COAL){
	    	  ItemStack itemstack2 = new ItemStack(Items.AIR);
	    	  if (itemstack.getCount() > 0) {
	    		  itemstack2 = new ItemStack(Items.COAL, itemstack.getCount() - 1);
	    	  }
	    	  p_230254_1_.setHeldItem(p_230254_2_, itemstack2);
	    	  this.milkTimer = this.milkTimer - 2400;
	    	  return ActionResultType.func_233537_a_(this.world.isRemote);
	      } else {
	         return super.func_230254_b_(p_230254_1_, p_230254_2_);
	      }
	   }
	
	private void launchFireBall(LivingEntity target) {
		//this.lookController.setLookPosition(target.getPositionVec());
		double d1 = 4.0D;
        Vector3d vector3d = this.getLook(1.0F);
        double d2 = target.getPosX() - (this.getPosX() + vector3d.x * 1D);
        double d3 = target.getPosYHeight(0.5D) - (0.5D + this.getPosYHeight(0.5D));
        double d4 = target.getPosZ() - (this.getPosZ() + vector3d.z * 1D);
        /*
        if (!this.isSilent()) {
           world.playEvent((PlayerEntity)null, 1016, this.parentEntity.getPosition(), 0);
        }
		*/
        SmallFireballEntity fireballentity = new SmallFireballEntity(world, this, d2, d3, d4);
        //fireballentity.explosionPower = 1;//this.getFireballStrength();
        fireballentity.setPosition(this.getPosX() + vector3d.x * 0.5D, this.getPosYHeight(0.5D) + 0.0D, fireballentity.getPosZ() + vector3d.z * 0.5D);
        world.addEntity(fireballentity);
	}
	
	@Override
	public void livingTick() {
		super.livingTick();
		if (this.attackTimer > 0) {
			--this.attackTimer;
		}
		if (this.milkTimer > 0) {
			--this.milkTimer;
		}
		if (this.revengerTimerClient > 0) {
			--this.revengerTimerClient;
		}
		if (this.milkTimer == 0 && rand.nextInt(1000) == 1 && !this.isChild()) {
			BlockPos pos = this.getPosition();
			World world = this.world;
			if(world.isAirBlock(pos) && !world.isAirBlock(pos.down())) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState(),11);	
				//This line /\ set the fire with the properly flags
				//this.move (this.getLookVec().add(this.getLook(1).scale(5)));
			}	
		}
		if (this.getRevengeTarget() != null && rand.nextInt(100) == 1 && this.isAlive()) {
			//this.world.setEntityState(this, (byte) 4);
			this.attackTimer = 40;
			this.launchFireBall(this.getRevengeTarget());
			this.setRevengeTarget(attackingPlayer);
		}
		if (this.ticksExisted - this.getRevengeTimer() < 3) {
			this.world.setEntityState(this, (byte) 5);
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return (Integer) StartermodConfig.ELEPHANT.maxSpawns.get();
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isChild() ? 1.3F : 2.25F;
	}

	@Override
	public double getMountedYOffset() {
		return 2.45D;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return (SoundEvent) SoundEvents.ENTITY_COW_AMBIENT; //ModSounds.ELEPHANT_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return (SoundEvent) SoundEvents.ENTITY_COW_HURT;//ModSounds.ELEPHANT_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return (SoundEvent) SoundEvents.ENTITY_COW_DEATH;//ModSounds.ELEPHANT_DEATH.get();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 4) {
			this.attackTimer = 40;
		}else if (id == 5) {
			this.revengerTimerClient = 100;//this.ticksExisted;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public int getAttackTimer() {
		return this.attackTimer;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getAngerTime2() {
		return this.angerTime;
	}
	
	
	@Override
	public int getAngerTime() {
		return this.angerTime;
	}
	
	@Override
	public void setAngerTime(int time) {
		if (time > 0) {
			this.world.setEntityState(this, (byte) 5);
		}
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
	
	public int getMilkTimer() {
		return this.milkTimer;
	}
	
	@Override
	public void func_230258_H__() {
		this.setAngerTime(rangedInteger.getRandomWithinRange(this.rand));
	}
	public boolean isBurning() { 
		return false;
	}
	
	@Override
	public boolean isImmuneToFire() {
		return true;
	}

	public ResourceLocation getLexiconEntry() {
		super.getLootTable();
		return LEXICON_ENTRY;
	}

	static {
		BREEDING_ITEMS = Ingredient.fromItems(new IItemProvider[]{Items.WHEAT});
		LEXICON_ENTRY = new ResourceLocation("livingthings", "neutral_mobs/elephant");
		rangedInteger = TickRangeConverter.convertRange(120, 160);
	}

	static class NewHurtByTargetGoal extends HurtByTargetGoal {

		public NewHurtByTargetGoal(CreatureEntity creatureIn) {
			super(creatureIn);
		}

		@Override
		public boolean shouldExecute() {
			LivingEntity livingentity = this.goalOwner.getRevengeTarget();
			if (livingentity instanceof PlayerEntity) {
				UUID ownerID = this.goalOwner.getUniqueID();//((AbstractTameableChestedEntity) this.goalOwner).getOwnerUniqueId();
				if (ownerID != null) {
					if (ownerID == ((PlayerEntity) livingentity).getUniqueID()) {
						return false;
					}
				}
			}
			return super.shouldExecute();
		}

	}

	
}