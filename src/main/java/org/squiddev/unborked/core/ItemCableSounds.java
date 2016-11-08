package org.squiddev.unborked.core;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.squiddev.patcher.transformer.IPatcher;
import org.squiddev.patcher.visitors.FindingVisitor;

import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

/**
 * Uses {@code getPlaceSound} instead of {@code getBreakSound} in
 * {@link dan200.computercraft.shared.peripheral.common.ItemCable}.
 */
public class ItemCableSounds implements IPatcher {
	@Override
	public boolean matches(String className) {
		return className.equals("dan200.computercraft.shared.peripheral.common.ItemCable");
	}

	@Override
	public ClassVisitor patch(String className, ClassVisitor delegate) throws Exception {
		return new FindingVisitor(
			delegate,
			new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/block/SoundType", null, "()Lnet/minecraft/util/SoundEvent;", false)
		) {
			@Override
			public void handle(InsnList nodes, MethodVisitor visitor) {
				String internalClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap("net/minecraft/block/SoundType");
				String remapped = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(internalClassName, "func_185841_e", "()Lnet/minecraft/util/SoundEvent;");

				visitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/SoundType", remapped, "()Lnet/minecraft/util/SoundEvent;", false);
			}
		}.onMethod("onItemUse").onMethod("func_180614_a").mustFind();
	}
}
