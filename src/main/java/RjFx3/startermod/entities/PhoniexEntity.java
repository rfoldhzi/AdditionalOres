package RjFx3.startermod.entities;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import RjFx3.startermod.config.StartermodConfig;
import RjFx3.startermod.init.EntitiesInit;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

public class PhoniexEntity extends FlyingEntity implements IMob {

	//protected PhoniexEntity(EntityType<? extends FlyingEntity> type, World worldIn) {
	//	super(type, worldIn);
		// TODO Auto-generated constructor stub
	//}

	private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(PhoniexEntity.class, DataSerializers.VARINT);
	private Vector3d orbitOffset = Vector3d.ZERO;
	private BlockPos orbitPosition = BlockPos.ZERO;
	private PhoniexEntity.AttackPhase attackPhase = PhoniexEntity.AttackPhase.CIRCLE;

	public PhoniexEntity(EntityType<? extends PhoniexEntity> type, World worldIn) {
	      super(type, worldIn);
	      this.experienceValue = 5;
	      this.moveController = new PhoniexEntity.MoveHelperController(this);
	      this.lookController = new PhoniexEntity.LookHelperController(this);
	 }
	
	protected BodyController createBodyController() {
	      return new PhoniexEntity.BodyHelperController(this);
	   }

	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PhoniexEntity.PickAttackGoal());
	    this.goalSelector.addGoal(2, new PhoniexEntity.SweepAttackGoal());
	    this.goalSelector.addGoal(3, new PhoniexEntity.OrbitPointGoal());
	    this.targetSelector.addGoal(1, new PhoniexEntity.AttackPlayerGoal());
	}


	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return PhoniexEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5F)
        		.createMutableAttribute(Attributes.MAX_HEALTH, 10.0F)
        		.createMutableAttribute(Attributes.ATTACK_DAMAGE , 2.0F);
    }
	
	@Override
	public double getAttributeValue(Attribute attribute) {
		if (attribute.equals(Attributes.MAX_HEALTH)) {
			return 20.0;
		} else if (attribute.equals(Attributes.MOVEMENT_SPEED)) {
			return 1.0;
		} else if (attribute.equals(Attributes.FOLLOW_RANGE)) {
			return 1.0;
		}
	     return this.getAttributeManager().getAttributeValue(attribute);
	}
	
	public static AttributeModifierMap getAttributes() {
        AttributeModifierMap.MutableAttribute attributes = CowEntity.func_233666_p_();

        //attributes.createMutableAttribute(Attributes.MAX_HEALTH);

        return attributes.create();
    }
	
	static enum AttackPhase {
	      CIRCLE,
	      SWOOP;
	}
	
	class AttackPlayerGoal extends Goal {
	      private final EntityPredicate Pred = (new EntityPredicate()).setDistance(64.0D);
	      private int tickDelay = 20;

	      private AttackPlayerGoal() {
	      }

	      /**
	       * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	       * method as well.
	       */
	      public boolean shouldExecute() {
	         if (this.tickDelay > 0) {
	            --this.tickDelay;
	            return false;
	         } else {
	            this.tickDelay = 60;
	            List<PlayerEntity> list = PhoniexEntity.this.world.getTargettablePlayersWithinAABB(this.Pred, PhoniexEntity.this, PhoniexEntity.this.getBoundingBox().grow(16.0D, 64.0D, 16.0D));
	            if (!list.isEmpty()) {
	               list.sort(Comparator.<Entity, Double>comparing(Entity::getPosY).reversed());

	               for(PlayerEntity playerentity : list) {
	                  if (PhoniexEntity.this.canAttack(playerentity, EntityPredicate.DEFAULT)) {
	                     PhoniexEntity.this.setAttackTarget(playerentity);
	                     return true;
	                  }
	               }
	            }

	            return false;
	         }
	      }
	}
	
	class BodyHelperController extends BodyController {
	      public BodyHelperController(MobEntity mob) {
	         super(mob);
	      }

	      /**
	       * Update the Head and Body rendenring angles
	       */
	      public void updateRenderAngles() {
	    	  PhoniexEntity.this.rotationYawHead = PhoniexEntity.this.renderYawOffset;
	    	  PhoniexEntity.this.renderYawOffset = PhoniexEntity.this.rotationYaw;
	      }
	   }

	   class LookHelperController extends LookController {
	      public LookHelperController(MobEntity entityIn) {
	         super(entityIn);
	      }

	      /**
	       * Updates look
	       */
	      public void tick() {
	      }
	   }
	
	
	abstract class MoveGoal extends Goal {
	      public MoveGoal() {
	         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	      }

	      protected boolean func_203146_f() {
	         return PhoniexEntity.this.orbitOffset.squareDistanceTo(PhoniexEntity.this.getPosX(), PhoniexEntity.this.getPosY(), PhoniexEntity.this.getPosZ()) < 4.0D;
	      }
	   }

	class MoveHelperController extends MovementController {
	      private float speedFactor = 0.1F;

	      public MoveHelperController(MobEntity entityIn) {
	         super(entityIn);
	      }

	      public void tick() {
	         if (PhoniexEntity.this.collidedHorizontally) {
	        	 PhoniexEntity.this.rotationYaw += 180.0F;
	            this.speedFactor = 0.1F;
	         }

	         float f = (float)(PhoniexEntity.this.orbitOffset.x - PhoniexEntity.this.getPosX());
	         float f1 = (float)(PhoniexEntity.this.orbitOffset.y - PhoniexEntity.this.getPosY());
	         float f2 = (float)(PhoniexEntity.this.orbitOffset.z - PhoniexEntity.this.getPosZ());
	         double d0 = (double)MathHelper.sqrt(f * f + f2 * f2);
	         double d1 = 1.0D - (double)MathHelper.abs(f1 * 0.7F) / d0;
	         f = (float)((double)f * d1);
	         f2 = (float)((double)f2 * d1);
	         d0 = (double)MathHelper.sqrt(f * f + f2 * f2);
	         double d2 = (double)MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
	         float f3 = PhoniexEntity.this.rotationYaw;
	         float f4 = (float)MathHelper.atan2((double)f2, (double)f);
	         float f5 = MathHelper.wrapDegrees(PhoniexEntity.this.rotationYaw + 90.0F);
	         float f6 = MathHelper.wrapDegrees(f4 * (180F / (float)Math.PI));
	         PhoniexEntity.this.rotationYaw = MathHelper.approachDegrees(f5, f6, 4.0F) - 90.0F;
	         PhoniexEntity.this.renderYawOffset = PhoniexEntity.this.rotationYaw;
	         if (MathHelper.degreesDifferenceAbs(f3, PhoniexEntity.this.rotationYaw) < 3.0F) {
	            this.speedFactor = MathHelper.approach(this.speedFactor, 1.8F, 0.005F * (1.8F / this.speedFactor));
	         } else {
	            this.speedFactor = MathHelper.approach(this.speedFactor, 0.2F, 0.025F);
	         }

	         float f7 = (float)(-(MathHelper.atan2((double)(-f1), d0) * (double)(180F / (float)Math.PI)));
	         PhoniexEntity.this.rotationPitch = f7;
	         float f8 = PhoniexEntity.this.rotationYaw + 90.0F;
	         double d3 = (double)(this.speedFactor * MathHelper.cos(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f / d2);
	         double d4 = (double)(this.speedFactor * MathHelper.sin(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f2 / d2);
	         double d5 = (double)(this.speedFactor * MathHelper.sin(f7 * ((float)Math.PI / 180F))) * Math.abs((double)f1 / d2);
	         Vector3d vector3d = PhoniexEntity.this.getMotion();
	         PhoniexEntity.this.setMotion(vector3d.add((new Vector3d(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
	      }
	   }

	class OrbitPointGoal extends PhoniexEntity.MoveGoal {
	      private float field_203150_c;
	      private float field_203151_d;
	      private float field_203152_e;
	      private float field_203153_f;

	      private OrbitPointGoal() {
	      }

	      /**
	       * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	       * method as well.
	       */
	      public boolean shouldExecute() {
	         return PhoniexEntity.this.getAttackTarget() == null || PhoniexEntity.this.attackPhase == PhoniexEntity.AttackPhase.CIRCLE;
	      }

	      /**
	       * Execute a one shot task or start executing a continuous task
	       */
	      public void startExecuting() {
	         this.field_203151_d = 5.0F + PhoniexEntity.this.rand.nextFloat() * 10.0F;
	         this.field_203152_e = -4.0F + PhoniexEntity.this.rand.nextFloat() * 9.0F;
	         this.field_203153_f = PhoniexEntity.this.rand.nextBoolean() ? 1.0F : -1.0F;
	         this.func_203148_i();
	      }

	      /**
	       * Keep ticking a continuous task that has already been started
	       */
	      public void tick() {
	         if (PhoniexEntity.this.rand.nextInt(350) == 0) {
	            this.field_203152_e = -4.0F + PhoniexEntity.this.rand.nextFloat() * 9.0F;
	         }

	         if (PhoniexEntity.this.rand.nextInt(250) == 0) {
	            ++this.field_203151_d;
	            if (this.field_203151_d > 15.0F) {
	               this.field_203151_d = 5.0F;
	               this.field_203153_f = -this.field_203153_f;
	            }
	         }

	         if (PhoniexEntity.this.rand.nextInt(450) == 0) {
	            this.field_203150_c = PhoniexEntity.this.rand.nextFloat() * 2.0F * (float)Math.PI;
	            this.func_203148_i();
	         }

	         if (this.func_203146_f()) {
	            this.func_203148_i();
	         }

	         if (PhoniexEntity.this.orbitOffset.y < PhoniexEntity.this.getPosY() && !PhoniexEntity.this.world.isAirBlock(PhoniexEntity.this.getPosition().down(1))) {
	            this.field_203152_e = Math.max(1.0F, this.field_203152_e);
	            this.func_203148_i();
	         }

	         if (PhoniexEntity.this.orbitOffset.y > PhoniexEntity.this.getPosY() && !PhoniexEntity.this.world.isAirBlock(PhoniexEntity.this.getPosition().up(1))) {
	            this.field_203152_e = Math.min(-1.0F, this.field_203152_e);
	            this.func_203148_i();
	         }

	      }

	      private void func_203148_i() {
	         if (BlockPos.ZERO.equals(PhoniexEntity.this.orbitPosition)) {
	            PhoniexEntity.this.orbitPosition = PhoniexEntity.this.getPosition();
	         }

	         this.field_203150_c += this.field_203153_f * 15.0F * ((float)Math.PI / 180F);
	         PhoniexEntity.this.orbitOffset = Vector3d.copy(PhoniexEntity.this.orbitPosition).add((double)(this.field_203151_d * MathHelper.cos(this.field_203150_c)), (double)(-4.0F + this.field_203152_e), (double)(this.field_203151_d * MathHelper.sin(this.field_203150_c)));
	      }
	   }

	class PickAttackGoal extends Goal {
	      private int tickDelay;

	      private PickAttackGoal() {
	      }

	      /**
	       * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	       * method as well.
	       */
	      public boolean shouldExecute() {
	         LivingEntity livingentity = PhoniexEntity.this.getAttackTarget();
	         return livingentity != null ? PhoniexEntity.this.canAttack(PhoniexEntity.this.getAttackTarget(), EntityPredicate.DEFAULT) : false;
	      }

	      /**
	       * Execute a one shot task or start executing a continuous task
	       */
	      public void startExecuting() {
	         this.tickDelay = 10;
	         PhoniexEntity.this.attackPhase = PhoniexEntity.AttackPhase.CIRCLE;
	         this.func_203143_f();
	      }

	      /**
	       * Reset the task's internal state. Called when this task is interrupted by another one
	       */
	      public void resetTask() {
	         PhoniexEntity.this.orbitPosition = PhoniexEntity.this.world.getHeight(Heightmap.Type.MOTION_BLOCKING, PhoniexEntity.this.orbitPosition).up(10 + PhoniexEntity.this.rand.nextInt(20));
	      }

	      /**
	       * Keep ticking a continuous task that has already been started
	       */
	      public void tick() {
	         if (PhoniexEntity.this.attackPhase == PhoniexEntity.AttackPhase.CIRCLE) {
	            --this.tickDelay;
	            if (this.tickDelay <= 0) {
	               PhoniexEntity.this.attackPhase = PhoniexEntity.AttackPhase.SWOOP;
	               this.func_203143_f();
	               this.tickDelay = (8 + PhoniexEntity.this.rand.nextInt(4)) * 20;
	               //PhoniexEntity.this.playSound(SoundEvents.ENTITY_PHANTOM_SWOOP, 10.0F, 0.95F + PhoniexEntity.this.rand.nextFloat() * 0.1F);
	            }
	         }

	      }

	      private void func_203143_f() {
	         PhoniexEntity.this.orbitPosition = PhoniexEntity.this.getAttackTarget().getPosition().up(20 + PhoniexEntity.this.rand.nextInt(20));
	         if (PhoniexEntity.this.orbitPosition.getY() < PhoniexEntity.this.world.getSeaLevel()) {
	            PhoniexEntity.this.orbitPosition = new BlockPos(PhoniexEntity.this.orbitPosition.getX(), PhoniexEntity.this.world.getSeaLevel() + 1, PhoniexEntity.this.orbitPosition.getZ());
	         }

	      }
	   }
	class SweepAttackGoal extends PhoniexEntity.MoveGoal {
	      private SweepAttackGoal() {
	      }

	      /**
	       * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	       * method as well.
	       */
	      public boolean shouldExecute() {
	         return PhoniexEntity.this.getAttackTarget() != null && PhoniexEntity.this.attackPhase == PhoniexEntity.AttackPhase.SWOOP;
	      }

	      /**
	       * Returns whether an in-progress EntityAIBase should continue executing
	       */
	      public boolean shouldContinueExecuting() {
	         LivingEntity livingentity = PhoniexEntity.this.getAttackTarget();
	         if (livingentity == null) {
	            return false;
	         } else if (!livingentity.isAlive()) {
	            return false;
	         } else if (!(livingentity instanceof PlayerEntity) || !((PlayerEntity)livingentity).isSpectator() && !((PlayerEntity)livingentity).isCreative()) {
	            if (!this.shouldExecute()) {
	               return false;
	            } else {
	            	/*
	               if (PhoniexEntity.this.ticksExisted % 20 == 0) {
	                  List<CatEntity> list = PhoniexEntity.this.world.getEntitiesWithinAABB(CatEntity.class, PhoniexEntity.this.getBoundingBox().grow(16.0D), EntityPredicates.IS_ALIVE);
	                  if (!list.isEmpty()) {
	                     for(CatEntity catentity : list) {
	                        catentity.func_213420_ej();
	                     }

	                     return false;
	                  }
	               }
	               */

	               return true;
	            }
	         } else {
	            return false;
	         }
	      }

	      /**
	       * Execute a one shot task or start executing a continuous task
	       */
	      public void startExecuting() {
	      }

	      /**
	       * Reset the task's internal state. Called when this task is interrupted by another one
	       */
	      public void resetTask() {
	         PhoniexEntity.this.setAttackTarget((LivingEntity)null);
	         PhoniexEntity.this.attackPhase = PhoniexEntity.AttackPhase.CIRCLE;
	      }

	      /**
	       * Keep ticking a continuous task that has already been started
	       */
	      public void tick() {
	         LivingEntity livingentity = PhoniexEntity.this.getAttackTarget();
	         PhoniexEntity.this.orbitOffset = new Vector3d(livingentity.getPosX(), livingentity.getPosYHeight(0.5D), livingentity.getPosZ());
	         if (PhoniexEntity.this.getBoundingBox().grow((double)0.2F).intersects(livingentity.getBoundingBox())) {
	            PhoniexEntity.this.attackEntityAsMob(livingentity);
	            livingentity.setFire(3);
	            PhoniexEntity.this.attackPhase = PhoniexEntity.AttackPhase.CIRCLE;
	            if (!PhoniexEntity.this.isSilent()) {
	               PhoniexEntity.this.world.playEvent(1039, PhoniexEntity.this.getPosition(), 0);
	            }
	         } else if (PhoniexEntity.this.collidedHorizontally || PhoniexEntity.this.hurtTime > 0) {
	            PhoniexEntity.this.attackPhase = PhoniexEntity.AttackPhase.CIRCLE;
	         }

	      }
	   }
}
