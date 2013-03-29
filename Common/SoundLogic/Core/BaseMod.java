package SoundLogic.Core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.SidedProxy;

@Mod(modid = "SoundLogicBase", name = "SoundLogic's Mod Base", version = "1.0", useMetadata=true)
public class BaseMod {
	@SidedProxy(clientSide = "SoundLogic.Core.ClientProxy", serverSide = "SoundLogic.Core.CommonProxy")
	public static CommonProxy proxy;
	@Init
	public void init(FMLInitializationEvent evt)
	{
		proxy.Init();
	}
}
