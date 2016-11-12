package org.squiddev.unborked.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.squiddev.patcher.Logger;
import org.squiddev.patcher.transformer.ClassMerger;
import org.squiddev.patcher.transformer.TransformationChain;

import java.io.*;

public class UnborkedTransformer implements IClassTransformer {
	private final TransformationChain patches = new TransformationChain();

	public UnborkedTransformer() {
		patches.add(new ClassMerger(
			"dan200.computercraft.shared.common.BlockGeneric",
			"org.squiddev.unborked.core.patch.BlockGeneric_Patch"
		));
		patches.add(new ClassMerger(
			"dan200.computercraft.shared.common.TileGeneric",
			"org.squiddev.unborked.core.patch.TileGeneric_Patch"
		));
		patches.add(new ClassMerger(
			"dan200.computercraft.shared.peripheral.modem.TileAdvancedModem",
			"org.squiddev.unborked.core.patch.TileAdvancedModem_Patch"
		));
		patches.add(new ClassMerger(
			"dan200.computercraft.shared.turtle.core.TurtleBrain",
			"org.squiddev.unborked.core.patch.TurtleBrain_Patch"
		));
		patches.add(new ClassMerger(
			"dan200.computercraft.shared.turtle.upgrades.TurtleTool",
			"org.squiddev.unborked.core.patch.TurtleTool_Patch"
		));
		patches.add(new ClassMerger(
			"dan200.computercraft.shared.util.RedstoneUtil",
			"org.squiddev.unborked.core.patch.RedstoneUtil_Patch"
		));

		patches.add(new ItemCableSounds());

		Logger.instance = new Logger() {
			@Override
			public void doDebug(String message) {
				UnborkedPlugin.logger.info(message);
			}

			@Override
			public void doWarn(String message) {
				UnborkedPlugin.logger.warn(message);
			}

			@Override
			public void doError(String message, Throwable e) {
				UnborkedPlugin.logger.error(message, e);
			}
		};

		patches.finalise();
	}

	@Override
	public byte[] transform(String className, String transformedName, byte[] bytes) {
		try {
			byte[] rewritten = patches.transform(className, bytes);
			if (rewritten != bytes) writeDump(className, rewritten);
			return rewritten;
		} catch (Exception e) {
			String contents = "Cannot patch " + className + ", falling back to default";
			UnborkedPlugin.logger.error(contents, e);

			return bytes;
		}
	}

	private void writeDump(String className, byte[] bytes) {
		if (!UnborkedPlugin.DO_DUMP) return;

		File file = new File(UnborkedPlugin.dump, className.replace('.', '/') + ".class");
		File directory = file.getParentFile();
		if (directory.exists() || directory.mkdirs()) {
			try {
				OutputStream stream = new FileOutputStream(file);
				try {
					stream.write(bytes);
				} catch (IOException e) {
					UnborkedPlugin.logger.error("Cannot write " + file, e);
				} finally {
					stream.close();
				}
			} catch (FileNotFoundException e) {
				UnborkedPlugin.logger.error("Cannot write " + file, e);
			} catch (IOException e) {
				UnborkedPlugin.logger.error("Cannot write " + file, e);
			}
		} else {
			UnborkedPlugin.logger.error("Cannot create folder for " + file);
		}
	}
}
