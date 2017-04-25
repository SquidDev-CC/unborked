package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.media.items.ItemPrintout;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.squiddev.unborked.ProxyServer;

public class ItemPrintout_Patch extends ItemPrintout {
	/**
	 * Pass the hand argument to the printout GUI, so we can use the appropriate hand.
	 *
	 * Also always return {@link EnumActionResult#SUCCESS} so we actually get the correct hand.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) ProxyServer.openPrintoutGUI(player, hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
