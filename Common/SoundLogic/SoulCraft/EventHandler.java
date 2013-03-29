package SoundLogic.SoulCraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import SoundLogic.SoulCraft.Haunting.Haunting;
import SoundLogic.SoulCraft.Haunting.HauntingLocation;
import SoundLogic.SoulCraft.Haunting.Cause.*;
import SoundLogic.SoulCraft.Registry.FileRegistry;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

public class EventHandler implements ITickHandler{
	@ForgeSubscribe
	public void Command(CommandEvent event)
	{
	}
	@ForgeSubscribe
	public void Save(WorldEvent.Save save)
	{
		if(save.world.provider.dimensionId!=0 ||save.world.isRemote)
			return;
		String loc1="saves/"+save.world.getSaveHandler().getSaveDirectoryName()+"/Soundlogic/Soulcraft";
		String loc2=loc1+"/hauntings.dat";
		File file=new File(loc2);
		File dir=new File(loc1);
		try {
			dir.mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		try {
			FileRegistry.Save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ForgeSubscribe
	public void Load(WorldEvent.Load load)
	{
		if(load.world.provider.dimensionId!=0||load.world.isRemote)
			return;
		String loc="saves/"+load.world.getSaveHandler().getSaveDirectoryName()+"/Soundlogic/Soulcraft/hauntings.dat";
		File file=new File(loc);
		FileRegistry.Load(file);
	}
	@ForgeSubscribe
	public void checkForPunch(PlayerInteractEvent event)
	{
		if(event.action==event.action.LEFT_CLICK_BLOCK)
		{
			TileEntity tile=event.entityPlayer.worldObj.getBlockTileEntity(event.x, event.y, event.z);
			if(tile instanceof TileEntitySimpleHaunt)
			{
				((TileEntitySimpleHaunt) tile).playerPunch(event.entityPlayer);
			}
		}
	}
	@ForgeSubscribe
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.world.isRemote)
		if(event.entity instanceof EntityItem)
		{
			if(!(event.entity instanceof EntityItemHaunted))
			{
				if(((EntityItem)event.entity).getEntityItem().getItem() instanceof ItemHaunted)
				{
					EntityItemHaunted ent=new EntityItemHaunted(event.world,event.entity.posX,event.entity.posY,event.entity.posZ,((EntityItem)event.entity).getEntityItem());
					event.setCanceled(true);
					event.entity.setDead();
					ent.motionX=event.entity.motionX;
					ent.motionY=event.entity.motionY;
					ent.motionZ=event.entity.motionZ;
					ent.delayBeforeCanPickup=((EntityItem)event.entity).delayBeforeCanPickup;
					event.world.spawnEntityInWorld(ent);
				}
			}
		}
	}
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		HauntHandler.Tick();
		SoulMod.proxy.onTick();
		
	}
	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.SERVER);
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Soulcraft Ticker";
	}
}
