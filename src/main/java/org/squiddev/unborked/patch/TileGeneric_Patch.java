package org.squiddev.unborked.patch;

import dan200.computercraft.shared.common.TileGeneric;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Ensures the initial TE update packet is sent
 */
public class TileGeneric_Patch extends TileGeneric {
	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
}
