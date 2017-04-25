package org.squiddev.unborked.core.patch;

import dan200.computercraft.shared.computer.core.ServerComputer;
import org.squiddev.patcher.visitors.MergeVisitor;

public class ServerComputer_Patch extends ServerComputer {
	@MergeVisitor.Stub
	public ServerComputer_Patch() {
		super(null, -1, null, -1, null, -1, -1);
	}

	@Override
	public String getHostString() {
		return "ComputerCraft ${cc_version} (Minecraft ${mc_version})";
	}
}

