package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.common.TileGeneric;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.squiddev.patcher.visitors.MergeVisitor;

import java.util.ArrayList;
import java.util.List;

public class BlockGeneric_Patch extends Block {
	@MergeVisitor.Stub
	public BlockGeneric_Patch(Material materialIn) {
		super(materialIn);
	}

	/**
	 * Fixes collisions with entities not occurring. This was because the
	 * bounding box wasn't being offset by the block position, and so would
	 * only collide if the block was at (0, 0, 0).
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, Entity entity) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof TileGeneric) {
			TileGeneric generic = (TileGeneric) tile;
			ArrayList<AxisAlignedBB> collision = new ArrayList<AxisAlignedBB>(1);
			generic.getCollisionBounds(collision);
			if (collision.size() > 0) {
				for (AxisAlignedBB aCollision : collision) {
					addCollisionBoxToList(pos, entityBox, list, aCollision);
				}
			}
		}
	}

	/**
	 * Fixes the mouse-over selection box being the full AABB rather than the TE's bounds.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		TileEntity tile = source.getTileEntity(pos);
		if (tile != null && tile instanceof TileGeneric && tile.hasWorldObj()) {
			TileGeneric generic = (TileGeneric) tile;
			return generic.getBounds();
		} else {
			return FULL_BLOCK_AABB;
		}
	}

	/**
	 * Uses the correct neighbor changed method, ensuring redstone is correctly updated.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof TileGeneric) {
			TileGeneric generic = (TileGeneric) tile;
			generic.onNeighbourChange();
		}
	}

	/**
	 * Uses the correct neighbor changed method, ensuring that turtle's don't
	 * detect peripherals when moving.
	 */
	@Override
	public final void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighborPos) {
	}
}
