package SoundLogic.SoulCraft.Haunting;

import java.util.Map;

import net.minecraft.server.MinecraftServer;
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
import SoundLogic.SoulCraft.LocationField;
import SoundLogic.SoulCraft.HauntEvents.HauntEvent;
import SoundLogic.SoulCraft.Haunting.Cause.TileEntitySimpleHaunt;
import SoundLogic.SoulCraft.Haunting.Cause.TileEntityWrapper;
import SoundLogic.SoulCraft.Registry.FileRegistry;

public class HauntingBlockLocation extends HauntingLocation{

	int x,y,z;
	public HauntingBlockLocation(){}
	public HauntingBlockLocation(int x,int y,int z,World world, EntityPlayer player)
	{
		this.location=new LocationField(new float[]{x+0F,y+0F,z+0F},new float[]{(float) player.posX,(float) player.posY,(float) player.posZ},15F,world.provider.dimensionId);
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		this.causes.addCause(new TileEntityWrapper(x,y,z,world), 1);
	}
	@Override
	public boolean targetExists() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public Map<String, String> writeToMap() {
		Map<String,String> map=super.writeToMap();
		map.put("Block.X",String.valueOf(this.x));
		map.put("Block.Y",String.valueOf(this.y));
		map.put("Block.Z",String.valueOf(this.z));
		map.put("Block.Dim",String.valueOf(this.world.provider.dimensionId));
		return map;
	}

	@Override
	public void loadFromMap(Map<String, String> map) {
		super.loadFromMap(map);
		this.x=Integer.valueOf(map.get("Block.X"));
		this.y=Integer.valueOf(map.get("Block.Y"));
		this.z=Integer.valueOf(map.get("Block.Z"));
		this.world=MinecraftServer.getServer().worldServerForDimension(Integer.valueOf(map.get("Block.Dim")));
	}

}
