package org.squiddev.unborked.core.patch;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.shared.turtle.core.TurtleBrain;
import dan200.computercraft.shared.turtle.core.TurtlePlaceCommand;
import dan200.computercraft.shared.turtle.core.TurtlePlayer;
import dan200.computercraft.shared.turtle.upgrades.TurtleTool;
import dan200.computercraft.shared.util.InventoryUtil;
import dan200.computercraft.shared.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.squiddev.patcher.visitors.MergeVisitor;

import java.util.Collections;
import java.util.List;

public class TurtleTool_Patch extends TurtleTool {
	public TurtleTool_Patch(ResourceLocation id, int legacyID, String adjective, Item item) {
		super(id, legacyID, adjective, item);
	}

	/**
	 * Patches dig to fire an event instead of playing a sound.
	 * Also displays particles but we can't do anything about that.
	 */
	private TurtleCommandResult dig(ITurtleAccess turtle, EnumFacing direction) {
		World world = turtle.getWorld();
		BlockPos position = turtle.getPosition();
		BlockPos newPosition = WorldUtil.moveCoords(position, direction);
		if (WorldUtil.isBlockInWorld(world, newPosition) && !world.isAirBlock(newPosition) && !WorldUtil.isLiquidBlock(world, newPosition)) {
			if (ComputerCraft.turtlesObeyBlockProtection) {
				TurtlePlayer turtlePlayer = TurtlePlaceCommand.createPlayer(turtle, position, direction);
				if (!ComputerCraft.isBlockEditable(world, newPosition, turtlePlayer)) {
					return TurtleCommandResult.failure("Cannot break protected block");
				}
			}

			if (!canBreakBlock(world, newPosition)) {
				return TurtleCommandResult.failure("Unbreakable block detected");
			} else {
				if (canHarvestBlock(world, newPosition)) {
					List<ItemStack> items = getBlockDropped(world, newPosition);
					if (items != null && items.size() > 0) {
						for (ItemStack stack : items) {
							ItemStack remainder = InventoryUtil.storeItems(stack, turtle.getInventory(), 0, turtle.getInventory().getSizeInventory(), turtle.getSelectedSlot());
							if (remainder != null) {
								WorldUtil.dropItemStack(remainder, world, position, direction);
							}
						}
					}
				}

				// We fire an event instead as this doesn't depend on client-only methods
				IBlockState previousState = world.getBlockState(newPosition);
				world.playEvent(2001, newPosition, Block.getStateId(previousState));

				world.setBlockToAir(newPosition);

				if (turtle instanceof TurtleBrain) {
					TurtleBrain brain = (TurtleBrain) turtle;
					brain.saveBlockChange(newPosition, previousState);
				}

				return TurtleCommandResult.success();
			}
		} else {
			return TurtleCommandResult.failure("Nothing to dig here");
		}
	}

	@MergeVisitor.Stub
	private List<ItemStack> getBlockDropped(World world, BlockPos pos) {
		return Math.random() % 2 == 0 ? null : Collections.<ItemStack>emptyList();
	}
}
