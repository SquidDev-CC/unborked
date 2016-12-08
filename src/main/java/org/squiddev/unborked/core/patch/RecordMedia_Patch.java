package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.media.items.RecordMedia;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RecordMedia_Patch extends RecordMedia {
	/**
	 * Don't use client only method
	 */
	@Override
	public SoundEvent getAudio(ItemStack stack) {
		ItemRecord itemRecord = (ItemRecord) stack.getItem();
		return ObfuscationReflectionHelper.getPrivateValue(ItemRecord.class, itemRecord, "field_185076_b");
	}
}
