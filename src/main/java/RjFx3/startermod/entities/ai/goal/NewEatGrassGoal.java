package RjFx3.startermod.entities.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;

import RjFx3.startermod.lists.BlockList;
import RjFx3.startermod.lists.ItemList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class NewEatGrassGoal extends Goal {
   private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
   private static final Predicate<BlockState> IS_DAHLIA = BlockStateMatcher.forBlock(BlockList.dahlia);
   /** The entity owner of this AITask */
   private final MobEntity grassEaterEntity;
   /** The world the grass eater entity is eating from */
   private final World entityWorld;
   /** Number of ticks since the entity started to eat grass */
   private int eatingGrassTimer;

   public NewEatGrassGoal(MobEntity grassEaterEntityIn) {
      this.grassEaterEntity = grassEaterEntityIn;
      this.entityWorld = grassEaterEntityIn.world;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean shouldExecute() {
      if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000) != 0) {
         return false;
      } else {
         BlockPos blockpos = this.grassEaterEntity.getPosition();
         if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_DAHLIA.test(this.entityWorld.getBlockState(blockpos))) {
            return true;
         } else {
            return this.entityWorld.getBlockState(blockpos.down()).isIn(Blocks.GRASS_BLOCK) || this.entityWorld.getBlockState(blockpos.down()).isIn(BlockList.dahlia);
         }
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.eatingGrassTimer = 40;
      this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
      this.grassEaterEntity.getNavigator().clearPath();
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.eatingGrassTimer = 0;
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.eatingGrassTimer > 0;
   }

   /**
    * Number of ticks since the entity started to eat grass
    */
   public int getEatingGrassTimer() {
      return this.eatingGrassTimer;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
      if (this.eatingGrassTimer == 4) {
         BlockPos blockpos = this.grassEaterEntity.getPosition();
         if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
               this.entityWorld.destroyBlock(blockpos, false);
            }

            this.grassEaterEntity.eatGrassBonus();
         } else  if (IS_DAHLIA.test(this.entityWorld.getBlockState(blockpos))) {
        	 if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                 this.entityWorld.destroyBlock(blockpos, false);
              }
        	  this.grassEaterEntity.setFire(60);
        	  this.grassEaterEntity.entityDropItem(new ItemStack(ItemList.contaminated_dirt));
        	  this.grassEaterEntity.setVelocity(0, 6, 0);
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

