package org.squiddev.unborked.core.patch;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.squiddev.patcher.visitors.MergeVisitor;

public class TileGeneric_Patch extends TileEntity {
	/**
	 * Send/receive object information on first tick. This ensures computer,
	 * turtle and monitor data are synced correctly
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeDescription(tag);
		return tag;
	}

	public void handleUpdateTag(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readDescription(tag);
	}

	/**
	 * Force sending a block update to the client.
	 * This is used to ensure turtle updates are sent correctly.
	 */
	public final void updateBlock() {
		markDirty();
		BlockPos pos = getPos();
		IBlockState state = worldObj.getBlockState(pos);
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
		worldObj.notifyBlockUpdate(getPos(), state, state, 3);
	}

	@MergeVisitor.Stub
	protected void writeDescription(NBTTagCompound tag) {
	}

	@MergeVisitor.Stub
	protected void readDescription(NBTTagCompound tag) {
	}
}
