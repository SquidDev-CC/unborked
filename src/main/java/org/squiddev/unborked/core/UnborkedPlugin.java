package org.squiddev.unborked.core;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Collections;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({
	// Unborked
	"org.squiddev.unborked.core.",
	// Shared
	"org.squiddev.patcher",
})
@IFMLLoadingPlugin.MCVersion("${mc_version}")
@IFMLLoadingPlugin.SortingIndex(1001) // After runtime deobsfucation
public class UnborkedPlugin implements IFMLLoadingPlugin {
	public static final Logger logger = LogManager.getLogger("unborked");

	public static File minecraftDir;
	public static File dump;

	public UnborkedPlugin() {
		if (minecraftDir == null) {
			minecraftDir = (File) FMLInjectionData.data()[6];

			dump = new File(new File(minecraftDir, "asm"), "unborked");
			if (!dump.exists() && !dump.mkdirs()) {
				logger.error("Cannot create ASM dump folder");
			}
		}
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"org.squiddev.unborked.core.UnborkedTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "org.squiddev.unborked.core.UnborkedPlugin$ModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	public static class ModContainer extends DummyModContainer {
		public ModContainer() {
			super(new ModMetadata());
			ModMetadata md = getMetadata();
			md.modId = "unborked_core";
			md.name = "Unborked Core";
			md.authorList = Collections.singletonList("SquidDev");
			md.description = "Makes ComputerCraft ${cc_version} less buggy for ${mc_version}";
			md.version = "${mod_version}";
		}

		@Override
		public boolean registerBus(EventBus bus, LoadController controller) {
			bus.register(this);
			return true;
		}
	}
}
