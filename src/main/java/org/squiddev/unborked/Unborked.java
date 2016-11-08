package org.squiddev.unborked;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Doesn't do much. Partially here to do some client side editions,
 * partially to ensure our resource pack is loaded.
 */
@Mod(
	modid = "unborked", name = "Unborked",
	version = "${mod_version}",
	dependencies = "required-after:ComputerCraft@[${cc_version},);after:CCTurtle"
)
public class Unborked {
	@SidedProxy(
		serverSide = "org.squiddev.unborked.ProxyServer",
		clientSide = "org.squiddev.unborked.ProxyClient"
	)
	public static ProxyServer proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
}
