package org.squiddev.unborked;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = "unborked", name = "Unborked",
	version = "${mod_version}",
	dependencies = "required-after:ComputerCraft@[1.80pr0,);after:CCTurtle"
)
public class Unborked {
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	}
}
