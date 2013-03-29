package SoundLogic.SoulCraft.HauntEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

public class EventFootsteps extends HauntEvent {

	int x,y,z,duration;
	World world;
	Random random;
	public EventFootsteps()
	{
		this.random=new Random();
	}
	public EventFootsteps(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		this.random=new Random();
		this.duration=60+random.nextInt(80);
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		return world.doesBlockHaveSolidTopSurface(x, y, z) && !world.isBlockNormalCube(x, y+1, z);
	}


	@Override
	public void StartEvent() {
		StepSound sound=Block.blocksList[world.getBlockId(x, y, z)].stepSound;
		world.playSoundEffect(x, y, z, sound.getStepSound(), sound.getVolume()* 0.35F, sound.getPitch());
	}

	@Override
	public void ProgressEvent() {
		this.duration--;
		StepSound sound=Block.blocksList[world.getBlockId(x, y, z)].stepSound;
		if(duration%32==0 || duration%30==8 || random.nextDouble()<.05)
		{
			world.playSoundEffect(x, y, z, sound.getStepSound(), sound.getVolume()* 0.35F, sound.getPitch());
		}
	}

	@Override
	public void EndEvent() {

	}

	@Override
	public void sourceRemoved() {
		this.duration=10;
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
