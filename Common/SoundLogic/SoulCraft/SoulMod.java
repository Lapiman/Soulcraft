package SoundLogic.SoulCraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.SidedProxy;

@Mod(modid = "SoundLogicSoul", name = "SoundLogic's Soul Crafting", version = "1.0", useMetadata=true)
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"SoulCraftEvents"}, packetHandler = PacketHandler.class)

public class SoulMod {
	public static final String itemsImage="/SoundLogic/SoulCraft/items.png";
	public static final String terrainImage="/SoundLogic/SoulCraft/terrain.png";
	@SidedProxy(clientSide = "SoundLogic.SoulCraft.ClientProxy", serverSide = "SoundLogic.SoulCraft.CommonProxy")
	public static CommonProxy proxy;
	@Init
	public void init(FMLInitializationEvent evt)
	{
		proxy.Init(this);
	}
}
