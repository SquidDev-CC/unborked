package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.computer.blocks.BlockComputerBase;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import dan200.computercraft.shared.turtle.core.TurtleBrain;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.squiddev.patcher.visitors.MergeVisitor;

public class TurtleBrain_Patch extends TurtleBrain {
	@MergeVisitor.Stub
	private TileTurtle m_owner;

	@MergeVisitor.Stub
	public TurtleBrain_Patch() {
		super(null);
	}

	/**
	 * Fixes phantom peripherals showing up. This was as a result of the block
	 * changed event being fired when moving the turtle.
	 *
	 * To solve this we don't notify neighbours until everything has been copied across.
	 */
	public boolean teleportTo(World world, BlockPos pos) {
		if (!world.isRemote && !this.getWorld().isRemote) {
			World oldWorld = this.getWorld();
			BlockPos oldPos = this.m_owner.getPos();
			BlockComputerBase oldBlock = this.m_owner.getBlock();
			if (oldWorld == world && oldPos.equals(pos)) {
				return true;
			} else {
				// Use 0 as the flag instead, prevents notifying neighbors
				if (world.isBlockLoaded(pos) && world.setBlockState(pos, oldBlock.getDefaultState(), 0)) {
					Block block = world.getBlockState(pos).getBlock();
					if (block == oldBlock) {
						TileEntity newTile = world.getTileEntity(pos);
						if (newTile != null && newTile instanceof TileTurtle) {
							TileTurtle newTurtle = (TileTurtle) newTile;
							newTurtle.setWorldObj(world);
							newTurtle.setPos(pos);
							newTurtle.transferStateFrom(this.m_owner);
							newTurtle.createServerComputer().setWorld(world);
							newTurtle.createServerComputer().setPosition(pos);
							oldWorld.setBlockToAir(oldPos);

							newTurtle.updateBlock(); // And manually update the block here instead.
							newTurtle.updateInput();
							newTurtle.updateOutput();
							return true;
						}
					}

					world.setBlockToAir(pos);
				}

				return false;
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
