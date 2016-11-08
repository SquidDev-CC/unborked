package org.squiddev.unborked;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.media.items.ItemDiskLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Tiny proxy class
 */
@SideOnly(Side.CLIENT)
public class ProxyClient extends ProxyServer {
	public void init() {
		super.init();
		DiskColorHandler.setupColors();
	}

	public static class DiskColorHandler implements IItemColor {
		private final ItemDiskLegacy disk;

		DiskColorHandler(ItemDiskLegacy disk) {
			this.disk = disk;
		}

		@Override
		public int getColorFromItemstack(ItemStack stack, int layer) {
			return layer == 0 ? 0xFFFFFF : disk.getColor(stack);
		}

		@SideOnly(Side.CLIENT)
		public static void setupColors() {
			ItemColors colors = Minecraft.getMinecraft().getItemColors();
			colors.registerItemColorHandler(new DiskColorHandler(ComputerCraft.Items.disk), ComputerCraft.Items.disk);
			colors.registerItemColorHandler(new DiskColorHandler(ComputerCraft.Items.diskExpanded), ComputerCraft.Items.diskExpanded);
		}
	}
}
