package org.squiddev.unborked.patch;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileGeneric_Patch extends TileEntity {
	/**
	 * Contain the NBT data in the initial update tag
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	/**
	 * Force sending a block update to the client
	 */
	public final void updateBlock() {
		markDirty();
		BlockPos pos = getPos();
		IBlockState state = worldObj.getBlockState(pos);
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
		worldObj.notifyBlockUpdate(getPos(), state, state, 3);
	}
}
