package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.proxy.ComputerCraftProxyCommon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class ComputerCraftProxyCommon_Patch extends ComputerCraftProxyCommon {
	/**
	 * Don't use client only method
	 */
	@SuppressWarnings("deprecation")
	public String getRecordInfo(ItemStack recordStack) {
		Item item = recordStack.getItem();
		if (item instanceof ItemRecord) {
			ItemRecord record = (ItemRecord) item;
			String key = ObfuscationReflectionHelper.getPrivateValue(ItemRecord.class, record, "field_185077_c");
			return I18n.translateToLocal(key);
		} else {
			return null;
		}
	}
}
