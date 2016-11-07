package org.squiddev.unborked.patch;

import dan200.computercraft.shared.computer.blocks.TileComputerBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Forces the update tag to be sent the first tick. This hopefully ensures
 * computers are receive their instance ID and so are capable of booting.
 */
public abstract class TileComputerBase_Patch extends TileComputerBase {
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeDescription(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		readDescription(tag);
	}
}
