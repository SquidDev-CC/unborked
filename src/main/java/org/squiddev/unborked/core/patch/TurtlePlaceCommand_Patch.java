package org.squiddev.unborked.core.patch;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.shared.turtle.core.TurtlePlaceCommand;
import dan200.computercraft.shared.turtle.core.TurtlePlayer;
import dan200.computercraft.shared.util.IEntityDropConsumer;
import dan200.computercraft.shared.util.InventoryUtil;
import dan200.computercraft.shared.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.squiddev.patcher.visitors.MergeVisitor;

public class TurtlePlaceCommand_Patch extends TurtlePlaceCommand {
	@MergeVisitor.Stub
	public TurtlePlaceCommand_Patch() {
		super(null, null);
	}

	/**
	 * Patches to ensure both {@link Entity#applyPlayerInteraction(EntityPlayer, Vec3d, ItemStack, EnumHand)} and
	 * {@link Entity#processInitialInteract(EntityPlayer, ItemStack, EnumHand)} are called.
	 */
	private static ItemStack deployOnEntity(ItemStack stack, final ITurtleAccess turtle, TurtlePlayer turtlePlayer, EnumFacing direction, Object[] extraArguments, String[] o_errorMessage) {
		final World world = turtle.getWorld();
		final BlockPos position = turtle.getPosition();
		Vec3d turtlePos = new Vec3d(turtlePlayer.posX, turtlePlayer.posY, turtlePlayer.posZ);
		Vec3d rayDir = turtlePlayer.getLook(1.0F);
		Pair hit = WorldUtil.rayTraceEntities(world, turtlePos, rayDir, 1.5D);
		if (hit == null) {
			return stack;
		} else {
			ItemStack stackCopy = stack.copy();
			turtlePlayer.loadInventory(stackCopy);
			Entity hitEntity = (Entity) hit.getKey();
			Vec3d hitPos = (Vec3d) hit.getValue();
			ComputerCraft.setEntityDropConsumer(hitEntity, new IEntityDropConsumer() {
				public void consumeDrop(Entity entity, ItemStack drop) {
					ItemStack remainder = InventoryUtil.storeItems(drop, turtle.getInventory(), 0, turtle.getInventory().getSizeInventory(), turtle.getSelectedSlot());
					if (remainder != null) {
						WorldUtil.dropItemStack(remainder, world, position, turtle.getDirection().getOpposite());
					}
				}
			});
			boolean placed = false;
			if (hitEntity.applyPlayerInteraction(turtlePlayer, hitPos, stackCopy, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
				placed = true;
			} else if (hitEntity.processInitialInteract(turtlePlayer, stackCopy, EnumHand.MAIN_HAND)) {
				placed = true;
			} else if (hitEntity instanceof EntityLivingBase) {
				placed = stackCopy.interactWithEntity(turtlePlayer, (EntityLivingBase) hitEntity, EnumHand.MAIN_HAND);
				if (placed) {
					turtlePlayer.loadInventory(stackCopy);
				}
			}

			ComputerCraft.clearEntityDropConsumer(hitEntity);
			ItemStack remainder = turtlePlayer.unloadInventory(turtle);
			return !placed && remainder != null && ItemStack.areItemStacksEqual(stack, remainder) ? stack : (remainder != null && remainder.stackSize > 0 ? remainder : null);
		}
	}
}
