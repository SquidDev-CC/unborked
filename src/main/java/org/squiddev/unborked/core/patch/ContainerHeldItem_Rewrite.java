package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Rewrite of {@link dan200.computercraft.shared.media.inventory.ContainerHeldItem} which holds the current hand
 * instead of the hold slot.
 */
public class ContainerHeldItem_Rewrite extends Container {
	private final ItemStack m_stack;
	private final EnumHand m_hand;

	public ContainerHeldItem_Rewrite(InventoryPlayer player) {
		m_stack = player.getCurrentItem();
		m_hand = EnumHand.MAIN_HAND;

		RuntimeException e = new RuntimeException();
		e.fillInStackTrace();
		e.printStackTrace();
	}

	public ContainerHeldItem_Rewrite(EntityPlayer player, EnumHand hand) {
		m_hand = hand;
		m_stack = InventoryUtil.copyItem(player.getHeldItem(hand));
	}

	public ItemStack getStack() {
		return m_stack;
	}

	public boolean canInteractWith(EntityPlayer player) {
		if (player != null && player.isEntityAlive()) {
			ItemStack stack = player.getHeldItem(m_hand);
			if (stack == m_stack || stack != null && m_stack != null && stack.getItem() == m_stack.getItem()) {
				return true;
			}
		}

		return false;
	}

}
