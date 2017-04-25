package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.computer.blocks.TileComputer;
import dan200.computercraft.shared.computer.inventory.ContainerComputer;
import dan200.computercraft.shared.peripheral.diskdrive.ContainerDiskDrive;
import dan200.computercraft.shared.peripheral.diskdrive.TileDiskDrive;
import dan200.computercraft.shared.peripheral.printer.ContainerPrinter;
import dan200.computercraft.shared.peripheral.printer.TilePrinter;
import dan200.computercraft.shared.proxy.ComputerCraftProxyCommon;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import dan200.computercraft.shared.turtle.inventory.ContainerTurtle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.squiddev.unborked.ProxyClient;
import org.squiddev.unborked.ProxyServer;

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

	/**
	 * Stubs {}
	 */
	public class ForgeHandlers implements IGuiHandler {
		public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntity tile;
			switch (id) {
				case 100:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TileDiskDrive) {
						TileDiskDrive drive = (TileDiskDrive) tile;
						return new ContainerDiskDrive(player.inventory, drive);
					}
					break;
				case 101:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TileComputer) {
						TileComputer computer = (TileComputer) tile;
						return new ContainerComputer(computer);
					}
					break;
				case 102:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TilePrinter) {
						TilePrinter printer = (TilePrinter) tile;
						return new ContainerPrinter(player.inventory, printer);
					}
					break;
				case 103:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TileTurtle) {
						TileTurtle turtle = (TileTurtle) tile;
						return new ContainerTurtle(player.inventory, turtle.getAccess());
					}
					break;
				case 104:
				default:
					break;
				case 105:
					return ProxyServer.createContainerHeldItem(player, x == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				case 106:
					return ProxyServer.createContainerHeldItem(player, x == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
			}

			return null;
		}

		public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
			BlockPos pos = new BlockPos(x, y, z);
			TileEntity tile;
			switch (id) {
				case 100:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TileDiskDrive) {
						TileDiskDrive drive = (TileDiskDrive) tile;
						return ComputerCraftProxyCommon_Patch.this.getDiskDriveGUI(player.inventory, drive);
					}
					break;
				case 101:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TileComputer) {
						TileComputer computer = (TileComputer) tile;
						return ComputerCraftProxyCommon_Patch.this.getComputerGUI(computer);
					}
					break;
				case 102:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TilePrinter) {
						TilePrinter printer = (TilePrinter) tile;
						return ComputerCraftProxyCommon_Patch.this.getPrinterGUI(player.inventory, printer);
					}
					break;
				case 103:
					tile = world.getTileEntity(pos);
					if (tile != null && tile instanceof TileTurtle) {
						TileTurtle turtle = (TileTurtle) tile;
						return ComputerCraftProxyCommon_Patch.this.getTurtleGUI(player.inventory, turtle);
					}
					break;
				case 104:
				default:
					break;
				case 105:
					return ProxyClient.getPrintoutGUI(player, x == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				case 106:
					return ProxyClient.getPocketComputerGUI(player, x == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
			}

			return null;
		}
	}
}
