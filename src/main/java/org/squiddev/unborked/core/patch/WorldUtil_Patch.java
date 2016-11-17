package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.util.WorldUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WorldUtil_Patch extends WorldUtil {
	/**
	 * Patches WorldUtil to decrease the velocity at which
	 * items are launched. I don't know why everything is so powerful under
	 * 1.10
	 */
	public static void dropItemStack(ItemStack stack, World world, double xPos, double yPos, double zPos, double xDir, double yDir, double zDir) {
		EntityItem entityItem = new EntityItem(world, xPos, yPos, zPos, stack.copy());

		// Note, this doesn't use the same algorithm as BehaviorDefaultDispenseItem#doDispense
		// or InventoryHelper#spawnItemStack. It maybe should.

		entityItem.motionX = xDir * 0.1 + world.rand.nextFloat() * 0.1 - 0.05;
		entityItem.motionY = yDir * 0.1 + world.rand.nextFloat() * 0.1 - 0.05;
		entityItem.motionZ = zDir * 0.1 + world.rand.nextFloat() * 0.1 - 0.05;
		entityItem.setDefaultPickupDelay();
		world.spawnEntityInWorld(entityItem);
	}
}
