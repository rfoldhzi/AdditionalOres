package RjFx3.startermod.entities;

import java.util.function.Predicate;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import RjFx3.startermod.config.StartermodConfig;
import RjFx3.startermod.entities.ai.goal.NewEatGrassGoal;
import RjFx3.startermod.init.ModEntityTypes;
import RjFx3.startermod.lists.BlockList;
import RjFx3.startermod.lists.ItemList;
import RjFx3.startermod.lists.ModSoundList;



import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
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
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlowSheepEntity extends AnimalEntity implements net.minecraftforge.common.IForgeShearable{
	private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(GlowSheepEntity.class, DataSerializers.BOOLEAN);
	Random rnd = new Random();
	private static final Ingredient BREEDING_ITEMS;
	private static final ResourceLocation LEXICON_ENTRY;
	private static final RangedInteger rangedInteger;
	public static final Logger logger = LogManager.getLogger("startermod"); 
	
	private boolean sheared = false;
	private int sheepTimer;
	
	private NewEatGrassGoal eatGrassGoal;

	public GlowSheepEntity(EntityType<? extends GlowSheepEntity> entityType, World worldIn) {
		super(entityType, worldIn);
		this.stepHeight = 1.0F;
	}

	public AgeableEntity func_241840_a(ServerWorld world, AgeableEntity parent) {
		GlowSheepEntity child = (GlowSheepEntity) ((EntityType) ModEntityTypes.GLOW_SHEEP_ENTITY.get()).create(this.world);
				//.func_200721_a(this.field_70170_p);
		if (this.isTame() || ((GlowSheepEntity) parent).isTame()) {
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
				.createMutableAttribute(Attributes.MAX_HEALTH, 8)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
	}
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.3F).createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D);
    }

	protected void registerGoals() {
		this.eatGrassGoal = new NewEatGrassGoal(this);
	    this.goalSelector.addGoal(0, new SwimGoal(this));
		//this.goalSelector.addGoal(0, new FindZyronGoal(this, 1.25F, 8));
	    this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
	    this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
	    this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(Items.WHEAT, ItemList.dahlia), false));
	    this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
	    this.goalSelector.addGoal(5, this.eatGrassGoal);
	    this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.25D, false));
	    this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
	    this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
	    this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
	    this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<SheepEntity>(this, SheepEntity.class, false));
	}
	
	protected void updateAITasks() {
	    this.sheepTimer = this.eatGrassGoal.getEatingGrassTimer();
	    super.updateAITasks();
	}
	
	public void livingTick() {
	      if (this.world.isRemote) {
	         this.sheepTimer = Math.max(0, this.sheepTimer - 1);
	      }

	      super.livingTick();
	   }

	


	
	public boolean isBreedingItem(ItemStack stack) {
		return BREEDING_ITEMS.test(stack);
	}



	
	public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
	      ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
	      return super.func_230254_b_(p_230254_1_, p_230254_2_);
	   }
	
	public boolean getSheared() {
		//logger.info("And the sheep is "+this.dataManager.get(SHEARED));
		return this.dataManager.get(SHEARED);
		
	    //return this.sheared;
	}
	public void setSheared(boolean B) {
	      //this.sheared = B;
		this.dataManager.set(SHEARED, B);
		logger.info("Set sheared to "+B);
	}
	public boolean isShearable() {
		logger.info("Is this sheep shearable? "+this.isAlive()+!this.getSheared()+!this.isChild());
	      return this.isAlive() && !this.getSheared() && !this.isChild();
	}
	@Override
	   public boolean isShearable(@javax.annotation.Nonnull ItemStack item, World world, BlockPos pos) {
	      return isShearable();
	   }
	
	public void eatGrassBonus() {
	      this.setSheared(false);
	      if (this.isChild()) {
	         this.addGrowth(60);
	      }

	   }
	protected void registerData() {
	      super.registerData();
	      this.dataManager.register(SHEARED, false);
	   }
	
	 public boolean attackEntityAsMob(Entity entityIn) {
		if (entityIn instanceof SheepEntity) {
			if (!this.world.isRemote()) {
		         ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, entityIn.getPosX(), entityIn.getPosYHeight(0.5D), entityIn.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
		         entityIn.remove();
		         GlowSheepEntity glowsheepentity = ModEntityTypes.GLOW_SHEEP_ENTITY.get().create(entityIn.world);
		         glowsheepentity.setLocationAndAngles(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), entityIn.rotationYaw, entityIn.rotationPitch);
		         glowsheepentity.setHealth(((SheepEntity) entityIn).getHealth());
		         glowsheepentity.renderYawOffset = ((SheepEntity) entityIn).renderYawOffset;
		         if (this.hasCustomName()) {
		        	 glowsheepentity.setCustomName(this.getCustomName());
		        	 glowsheepentity.setCustomNameVisible(this.isCustomNameVisible());
		         }

		         if (this.isNoDespawnRequired()) {
		        	 glowsheepentity.enablePersistence();
		         }

		         //cowentity.setInvulnerable(this.isInvulnerable());
		         this.world.addEntity(glowsheepentity);

		      }
		}
		return false;
		 
	 }
	
	public void Convert(SoundCategory category) {
	      this.world.playMovingSound((PlayerEntity)null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, category, 1.0F, 1.0F);
	      if (!this.world.isRemote()) {
	         ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
	         this.remove();
	         CowEntity cowentity = EntityType.COW.create(this.world);
	         cowentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
	         cowentity.setHealth(this.getHealth());
	         cowentity.renderYawOffset = this.renderYawOffset;
	         if (this.hasCustomName()) {
	            cowentity.setCustomName(this.getCustomName());
	            cowentity.setCustomNameVisible(this.isCustomNameVisible());
	         }

	         if (this.isNoDespawnRequired()) {
	            cowentity.enablePersistence();
	         }

	         cowentity.setInvulnerable(this.isInvulnerable());
	         this.world.addEntity(cowentity);

	      }

	   }

	@Override
	public int getMaxSpawnedInChunk() {
		return (Integer) StartermodConfig.ELEPHANT.maxSpawns.get();
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isChild() ? 0.5F : 1.5F;
	}

	@Override
	public double getMountedYOffset() {
		return 2.45D;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return (SoundEvent) ModSoundList.GLOW_SHEEP_BAA.get();
		//return (SoundEvent) SoundEvents.ENTITY_SHEEP_AMBIENT; //ModSounds.ELEPHANT_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return (SoundEvent) SoundEvents.ENTITY_SHEEP_HURT;//ModSounds.ELEPHANT_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return (SoundEvent) SoundEvents.ENTITY_SHEEP_DEATH;//ModSounds.ELEPHANT_DEATH.get();
	}

	@OnlyIn(Dist.CLIENT)
	   public void handleStatusUpdate(byte id) {
	      if (id == 10) {
	         this.sheepTimer = 40;
	      } else {
	         super.handleStatusUpdate(id);
	      }

	   }
	
	@OnlyIn(Dist.CLIENT)
	   public float getHeadRotationPointY(float p_70894_1_) {
	      if (this.sheepTimer <= 0) {
	         return 0.0F;
	      } else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
	         return 1.0F;
	      } else {
	         return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F;
	      }
	   }
	@OnlyIn(Dist.CLIENT)
	   public float getHeadRotationAngleX(float p_70890_1_) {
	      if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
	         float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
	         return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
	      } else {
	         return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * ((float)Math.PI / 180F);
	      }
	   }
	
	public void writeAdditional(CompoundNBT compound) {
	      super.writeAdditional(compound);
	      compound.putBoolean("Sheared", this.getSheared());
	   }
	public void readAdditional(CompoundNBT compound) {
	      super.readAdditional(compound);
	      this.setSheared(compound.getBoolean("Sheared"));
	   }
	
	public ResourceLocation getLexiconEntry() {
		super.getLootTable();
		return LEXICON_ENTRY;
	}
	
	@javax.annotation.Nonnull
	   @Override
	   public java.util.List<ItemStack> onSheared(@Nullable PlayerEntity player, @javax.annotation.Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
	      world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
	      if (!world.isRemote) {
	         this.setSheared(true);
	         int i = 1 + this.rand.nextInt(3);

	         java.util.List<ItemStack> items = new java.util.ArrayList<>();
	         for (int j = 0; j < i; ++j) {
	            items.add(new ItemStack(Items.LIME_WOOL));
	         }
	         return items;
	      }
	      return java.util.Collections.emptyList();
	   }
	
	 public class FindZyronGoal extends MoveToBlockGoal {


		public FindZyronGoal(CreatureEntity creature, double speedIn, int length) {
			super(creature, speedIn, length);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
			BlockState blockstate = worldIn.getBlockState(pos);
	         return blockstate.isIn(BlockList.zyron_block);// && pos.getY()-1 > this.creature.getPosY();
		}
		
		public void tick() {
	         if (this.getIsAboveDestination()) {
	            this.creature.world.setBlockState(this.destinationBlock, Blocks.AIR.getDefaultState());
	            
	            //((ServerWorld)this.creature.world).spawnParticle(ParticleTypes.BLOCK.get, this.destinationBlock.getX(), this.destinationBlock.getY(), this.destinationBlock.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
		         
	         }

	         super.tick();
	         
	         BlockPos blockpos = this.func_241846_j();
	         if (!blockpos.withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
	             if (this.shouldMove()) {
	                this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()) - 0.5D, (double)blockpos.getY(), (double)((float)blockpos.getZ()) + 0.5D, this.movementSpeed);
	                this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()) + 0.5D, (double)blockpos.getY(), (double)((float)blockpos.getZ()) - 0.5D, this.movementSpeed);
	                this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()) + 1.5D, (double)blockpos.getY(), (double)((float)blockpos.getZ()) + 0.5D, this.movementSpeed);
	                this.creature.getNavigator().tryMoveToXYZ((double)((float)blockpos.getX()) + 0.5D, (double)blockpos.getY(), (double)((float)blockpos.getZ()) + 1.5D, this.movementSpeed);
		             
	             }
	          }
	      }
		
		public double getTargetDistanceSq() {
	         return 2.0D;
	      }
		 
	 }
	/*
	static class NewEatGrassGoal extends EatGrassGoal {
		private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
		   private final MobEntity grassEaterEntity;
		   private final World entityWorld;
		   private int eatingGrassTimer;
		   
		public NewEatGrassGoal(MobEntity grassEaterEntityIn) {
			super(grassEaterEntityIn);
			this.grassEaterEntity = grassEaterEntityIn;
			this.entityWorld = grassEaterEntityIn.world;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void tick() {
		      this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
		      if (this.eatingGrassTimer == 4) {
		         BlockPos blockpos = this.grassEaterEntity.getPosition();
		         if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
		            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
		               this.entityWorld.destroyBlock(blockpos, false);
		            }
		            this.grassEaterEntity.eatGrassBonus();
		         } else {
		            BlockPos blockpos1 = blockpos.down();
		            if (this.entityWorld.getBlockState(blockpos1).isIn(Blocks.GRASS_BLOCK)) {
		               if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
		                  this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
		                  this.entityWorld.setBlockState(blockpos1, BlockList.contaminated_dirt.getDefaultState(), 2);
		               }

		               this.grassEaterEntity.eatGrassBonus();
		            }
		         }

		      }
		   }
	}
*/
	
	static {
		BREEDING_ITEMS = Ingredient.fromItems(new IItemProvider[]{Items.WHEAT});
		LEXICON_ENTRY = new ResourceLocation("livingthings", "neutral_mobs/glow_sheep");
		rangedInteger = TickRangeConverter.convertRange(120, 160);
	}

	
}