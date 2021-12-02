package RjFx3.startermod.blocks;

import java.util.function.Supplier;

import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CustomChest extends ChestBlock{

	public CustomChest(Properties builder, Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityTypeIn) {
		super(builder, tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

}
