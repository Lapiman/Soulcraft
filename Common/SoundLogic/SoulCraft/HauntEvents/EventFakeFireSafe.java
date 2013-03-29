package SoundLogic.SoulCraft.HauntEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import SoundLogic.SoulCraft.BlockFakeFire;
import SoundLogic.SoulCraft.SoulMod;
import SoundLogic.SoulCraft.HauntEvents.Fire.FireTracker;
import SoundLogic.SoulCraft.HauntEvents.Fire.TileEntitySpecialFire;

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

public class EventFakeFireSafe extends HauntEvent {

	int x,y,z,duration,id;
	World world;
	public EventFakeFireSafe()
	{
	}
	public EventFakeFireSafe(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		Random rand=new Random();
		this.duration=30*60+rand.nextInt(15*60);
		id=FireTracker.getFreeID();
		FireTracker.ids.add(Integer.valueOf(id));
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		return world.getBlockId(x, y, z)==0 && world.getBlockId(x, y-1, z)!=0;
	}


	@Override
	public void StartEvent() {
		world.setBlock(x, y, z, SoulMod.proxy.fakeFire.blockID);
		TileEntitySpecialFire tile=new TileEntitySpecialFire();
		tile.setID(id);
    	world.setBlockTileEntity(x, y, z, tile);
	}

	@Override
	public void ProgressEvent() {
		duration--;
	}

	@Override
	public void EndEvent() {
		FireTracker.remove(id);
	}

	@Override
	public void sourceRemoved() {
		FireTracker.remove(id);
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
		map.put("fire id", String.valueOf(this.id));
		return map;
	}

	@Override
	public void loadFromMap(Map<String,String> map) {
		this.x=Integer.valueOf(map.get("x"));
		this.y=Integer.valueOf(map.get("y"));
		this.z=Integer.valueOf(map.get("z"));
		this.world=MinecraftServer.getServer().worldServerForDimension(Integer.valueOf(map.get("dim")));
		this.duration=Integer.valueOf(map.get("duration"));
		this.id=Integer.valueOf(map.get("fire id"));
		FireTracker.ids.add(Integer.valueOf(id));
	}
}
