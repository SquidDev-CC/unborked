package org.squiddev.unborked;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.media.items.ItemDiskLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Empty class to ensure textures get loaded correctly
 */
@Mod(
	modid = "unborked", name = "Unborked",
	version = "${mod_version}",
	dependencies = "required-after:ComputerCraft@[1.80pr0,);after:CCTurtle"
)
public class Unborked {
	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	public void init(FMLInitializationEvent event) {
		setupColors();
	}

	@SideOnly(Side.CLIENT)
	private void setupColors() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DiskColorHandler(ComputerCraft.Items.disk), ComputerCraft.Items.disk);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new DiskColorHandler(ComputerCraft.Items.diskExpanded), ComputerCraft.Items.diskExpanded);
	}

	@SideOnly(Side.CLIENT)
	private static class DiskColorHandler implements IItemColor {
		private final ItemDiskLegacy disk;

		private DiskColorHandler(ItemDiskLegacy disk) {
			this.disk = disk;
		}

		@Override
		public int getColorFromItemstack(ItemStack stack, int layer) {
			return layer == 0 ? 0xFFFFFF : disk.getColor(stack);
		}
	}
}
