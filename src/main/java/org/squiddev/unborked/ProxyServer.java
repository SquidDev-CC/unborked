package org.squiddev.unborked;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The base proxy class.
 * Also holds a load of methods I need to get obfuscated.
 */
public class ProxyServer {
	public void preInit() {
	}

	public void init() {
	}

	public static void playSound(World world, BlockPos pos, IBlockState state) {
		world.playEvent(2001, pos, Block.getStateId(state));
	}

	public static void playSound(World world, BlockPos pos, Block block) {
		world.playEvent(2001, pos, Block.getStateId(block.getDefaultState()));
	}
}
