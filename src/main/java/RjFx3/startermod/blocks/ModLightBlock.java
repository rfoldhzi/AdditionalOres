package RjFx3.startermod.blocks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class ModLightBlock extends Block{
	public static final String modid = "startermod";
	private static final Logger logger = LogManager.getLogger(modid); 

	public ModLightBlock(AbstractBlock.Properties properties) {
	      super(properties);
	   }

	//@OnlyIn(Dist.CLIENT)
	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
    //public float getAmbientOcclusionLightValfue(IBlockReader reader,  BlockPos pos) {
		//logger.info("SPAM 1");
		return 1;
       //return this.getBlock().getAmbientOcclusionLightValue(this.getDefaultState(), reader, pos);
    }
	/*
	@OnlyIn(Dist.CLIENT)
	public float getAmbientOcclusionLightValue(IBlockReader reader, BlockPos pos) {
		logger.info("SPAM 2");
		return 1F;
	}
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(IBlockReader reader, BlockPos pos) {
		logger.info("SPAM 3");
		return 1F;
       //return this.getBlock().getAmbientOcclusionLightValue(this.getDefaultState(), reader, pos);
    }
	@OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
		logger.info("SPAM 4");
        return 1.0F;
    }
*/
}
