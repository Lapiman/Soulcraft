package SoundLogic.SoulCraft.HauntEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import SoundLogic.SoulCraft.SoulMod;

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

public class EventFlickerTorch extends HauntEvent {

	int x,y,z,duration;
	World world;
	public EventFlickerTorch()
	{
	}
	public EventFlickerTorch(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		this.duration=100;
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		return world.getBlockId(x, y, z)==Block.torchWood.blockID;
	}


	@Override
	public void StartEvent() {
	}

	@Override
	public void ProgressEvent() {
		duration--;
		if(duration%20==0)
			world.setBlock(x, y, z, SoulMod.proxy.DeadTorch.blockID);
		if(duration%20==10)
			world.setBlock(x, y, z, Block.torchWood.blockID);
	}

	@Override
	public void EndEvent() {
		world.setBlock(x, y, z, Block.torchWood.blockID);
	}

	@Override
	public void sourceRemoved() {
	}

	@Override
	public int getDurationRemaining() {
		return duration;
	}

	@Override
	public Map writeToMap() {
		Map<String,String> map=new HashMap();
		map.put("x", String.valueOf(this.x));
		map.put("y", String.valueOf(this.y));
		map.put("z", String.valueOf(this.z));
		map.put("dim", String.valueOf(world.provider.dimensionId));
		map.put("duration", String.valueOf(this.duration));
		return map;
	}

	@Override
	public void loadFromMap(Map<String,String> map) {
		this.x=Integer.valueOf(map.get("x"));
		this.y=Integer.valueOf(map.get("y"));
		this.z=Integer.valueOf(map.get("z"));
		this.world=MinecraftServer.getServer().worldServerForDimension(Integer.valueOf(map.get("dim")));
		this.duration=Integer.valueOf(map.get("duration"));
	}
}
