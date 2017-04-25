package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.pocket.items.ItemPocketComputer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.squiddev.patcher.visitors.MergeVisitor;
import org.squiddev.unborked.ProxyServer;

public class ItemPocketComputer_Patch extends ItemPocketComputer {
	/**
	 * Pass the hand argument to the computer GUI, so we can use the appropriate hand.
	 *
	 * Also always return {@link EnumActionResult#SUCCESS} so we actually get the correct hand.
	 */
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			ServerComputer computer = createServerComputer(world, player.inventory, stack);
			if (computer != null) computer.turnOn();

			ProxyServer.openPocketComputerGUI(player, hand);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@MergeVisitor.Stub
	private ServerComputer createServerComputer(World world, IInventory inventory, ItemStack stack) {
		return null;
	}
}
