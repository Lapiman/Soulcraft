package SoundLogic.SoulCraft.Haunting.Cause;

import java.util.HashMap;
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
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import SoundLogic.SoulCraft.HauntEvents.HauntEvent;
import SoundLogic.SoulCraft.Registry.FileRegistry;

public class TileEntityWrapper implements IHauntCause{
	int x,y,z,dim;
	World world;
	public TileEntityWrapper(){}
	public TileEntityWrapper(int x,int y,int z, World world)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		this.dim=world.provider.dimensionId;
	}
	@Override
	public boolean valid() {
		TileEntity ent=world.getBlockTileEntity(x, y, z);
		if(ent==null)
			return false;
		if(ent instanceof TileEntitySimpleHaunt)
		{
			return ((TileEntitySimpleHaunt) ent).valid();
		}
		return false;
	}

	@Override
	public HauntEvent protectEvent() {
		TileEntity ent=world.getBlockTileEntity(x, y, z);
		if(ent==null)
			return null;
		if(ent instanceof TileEntitySimpleHaunt)
		{
			return ((TileEntitySimpleHaunt) ent).protectEvent();
		}
		return null;
	}

	@Override
	public boolean underAttack() {
		TileEntity ent=world.getBlockTileEntity(x, y, z);
		if(ent==null)
			return false;
		if(ent instanceof TileEntitySimpleHaunt)
		{
			return ((TileEntitySimpleHaunt) ent).underAttack();
		}
		return false;
	}

	@Override
	public Map<String,String> writeToMap() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("x",String.valueOf(x));
		map.put("y",String.valueOf(y));
		map.put("z",String.valueOf(z));
		map.put("dim",String.valueOf(dim));
		return map;
	}

	@Override
	public void loadFromMap(Map<String,String> map) {
		this.x=Integer.valueOf(map.get("x"));
		this.y=Integer.valueOf(map.get("y"));
		this.z=Integer.valueOf(map.get("z"));
		this.dim=Integer.valueOf(map.get("dim"));
		this.world=MinecraftServer.getServer().worldServerForDimension(dim);
	}

}
