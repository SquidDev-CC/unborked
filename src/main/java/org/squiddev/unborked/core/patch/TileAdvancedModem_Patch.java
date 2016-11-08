package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.peripheral.modem.BlockAdvancedModem;
import dan200.computercraft.shared.peripheral.modem.TileAdvancedModem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 * Fixes the advanced modem to use the correct property state. Before
 * it was using BlockPeripheral's FACING property instead.
 */
public class TileAdvancedModem_Patch extends TileAdvancedModem {
	public EnumFacing getDirection() {
		IBlockState state = getBlockState();
		return state.getValue(BlockAdvancedModem.Properties.FACING);
	}

	public void setDirection(EnumFacing dir) {
		setBlockState(getBlockState().withProperty(BlockAdvancedModem.Properties.FACING, dir));
	}
}
