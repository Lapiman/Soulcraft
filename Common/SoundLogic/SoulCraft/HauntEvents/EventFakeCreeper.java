package SoundLogic.SoulCraft.HauntEvents;

import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.*;

import SoundLogic.SoulCraft.SoulMod;

public class EventFakeCreeper extends HauntEvent{

	private EntityPlayer player;
	private int duration;
	private double x;
	private double y;
	private double z;
	public static boolean validForPlayer(EntityPlayer player)
	{
		System.out.println(player.worldObj.isRemote);
		double x=player.posX-player.getLookVec().xCoord;
		double y=player.posY+1;
		double z=player.posZ-player.getLookVec().zCoord;
		if(player.isAirBorne)
			return false;
		if(player.worldObj.provider.dimensionId==1)
			return false;
		if(player.worldObj.provider.dimensionId==-1)
			return false;
		if(player.worldObj.isBlockNormalCube((int)x, (int)y, (int)z))
			return false;
		if(player.worldObj.isBlockNormalCube((int)x, (int)y-1, (int)z))
			return false;
		return true;
	}
	public EventFakeCreeper(EntityPlayer player)
	{
		this.player=player;
		duration=80;
	}
	public EventFakeCreeper(){}
	@Override
	public void StartEvent() {
		SoulMod.proxy.EventPasserServer(player, PacketIDs.Creeper, new int[]{0});
	}

	@Override
	public void ProgressEvent() {
		duration--;
		SoulMod.proxy.EventPasserServer(player, PacketIDs.Creeper, new int[]{1});
	}

	@Override
	public void EndEvent() {
		SoulMod.proxy.EventPasserServer(player, PacketIDs.Creeper, new int[]{2});
	}

	@Override
	public void sourceRemoved() {
		SoulMod.proxy.EventPasserServer(player, PacketIDs.Creeper, new int[]{2});
	}

	@Override
	public int getDurationRemaining() {
		// TODO Auto-generated method stub
		return duration;
	}

	@Override
	public Map<String, String> writeToMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadFromMap(Map<String, String> map) {
		// TODO Auto-generated method stub
		
	}

}
