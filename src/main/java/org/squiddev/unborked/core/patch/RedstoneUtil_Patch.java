package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.util.RedstoneUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RedstoneUtil_Patch extends RedstoneUtil {
	private static Block getBlock(IBlockAccess world, BlockPos pos) {
		return pos.getY() >= 0 ? world.getBlockState(pos).getBlock() : null;
	}

	public static int getRedstoneOutput(World world, BlockPos pos, EnumFacing side) {
		// TODO: We could probably use world.getRedstonePower(pos, side)

		int power = 0;
		Block block = getBlock(world, pos);
		if (block != null && block != Blocks.AIR) {
			IBlockState state = world.getBlockState(pos);
			if (block == Blocks.REDSTONE_WIRE) {
				if (side != EnumFacing.UP) {
					power = state.getValue(BlockRedstoneWire.POWER);
				} else {
					power = 0;
				}
			} else if (state.canProvidePower()) {
				power = state.getWeakPower(world, pos, side.getOpposite());
			}

			if (block.isNormalCube(state, world, pos)) {
				for (EnumFacing testSide : EnumFacing.VALUES) {
					if (testSide != side) {
						BlockPos testPos = pos.offset(testSide);
						Block neighbour = getBlock(world, testPos);
						if (neighbour != null && state.canProvidePower()) {
							power = Math.max(power, state.getStrongPower(world, testPos, testSide.getOpposite()));
						}
					}
				}
			}
		}

		return power;
	}
}
