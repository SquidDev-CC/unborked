package org.squiddev.unborked.patch;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Ensures the initial TE update packet is sent
 */
public class TileGeneric_Patch extends TileEntity {
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public final void updateBlock() {
		markDirty();
		BlockPos pos = getPos();
		IBlockState state = worldObj.getBlockState(pos);
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
		worldObj.notifyBlockUpdate(getPos(), state, state, 3);
	}
}
