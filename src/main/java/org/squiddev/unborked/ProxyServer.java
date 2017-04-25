package org.squiddev.unborked;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.shared.media.inventory.ContainerHeldItem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.squiddev.patcher.Logger;

import java.lang.reflect.Constructor;

/**
 * The base proxy class.
 * Also holds a load of methods I need to get obfuscated.
 */
public class ProxyServer {
	public void preInit() {
	}

	public void init() {
	}

	public static void playSound(World world, BlockPos pos, IBlockState state) {
		world.playEvent(2001, pos, Block.getStateId(state));
	}

	public static void playSound(World world, BlockPos pos, Block block) {
		world.playEvent(2001, pos, Block.getStateId(block.getDefaultState()));
	}

	public static void openPrintoutGUI(EntityPlayer player, EnumHand hand) {
		player.openGui(ComputerCraft.instance, 105, player.getEntityWorld(), hand == EnumHand.MAIN_HAND ? 0 : 1, 0, 0);
	}

	public static void openPocketComputerGUI(EntityPlayer player, EnumHand hand) {
		player.openGui(ComputerCraft.instance, 106, player.getEntityWorld(), hand == EnumHand.MAIN_HAND ? 0 : 1, 0, 0);
	}

	public static ContainerHeldItem createContainerHeldItem(EntityPlayer player, EnumHand hand) {
		try {

			if (constructor == null) {
				constructor = ContainerHeldItem.class.getConstructor(EntityPlayer.class, EnumHand.class);
			}

			return constructor.newInstance(player, hand);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			Logger.error("Cannot access ContainerHeldItem constructor", e);
			return new ContainerHeldItem(player.inventory);
		}
	}

	private static Constructor<ContainerHeldItem> constructor;
}
