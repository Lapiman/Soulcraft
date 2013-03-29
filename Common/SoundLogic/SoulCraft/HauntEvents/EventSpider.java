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

public class EventSpider extends HauntEvent {

	int x,y,z,duration,ticksSinceSpoke,pauseCount;
	World world;
	Random random;
	public EventSpider()
	{
		this.random=new Random();
	}
	public EventSpider(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		this.random=new Random();
		this.duration=600+random.nextInt(800);
		this.ticksSinceSpoke=0;
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		return world.isBlockNormalCube(x, y, z);
	}


	@Override
	public void StartEvent() {
	}

	@Override
	public void ProgressEvent() {
		this.duration--;
		if(pauseCount>0){pauseCount--;return;}
		ticksSinceSpoke++;
		StepSound sound=Block.blocksList[world.getBlockId(x, y, z)].stepSound;
		if(random.nextInt(200)<=1)
		{
			pauseCount=random.nextInt(40*60)+20*60;
		}
		if(duration%32==0 || random.nextInt(100)<1)
		{
			world.playSoundEffect(x, y, z, "mob.spider.step",0.35F, 1.0F);
		}
		if(random.nextInt(800)<ticksSinceSpoke)
		{
			ticksSinceSpoke=-80;
			world.playSoundEffect(x, y, z, "mob.spider.say",0.35F, 1.0F);
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
		map.put("ticksSinceSpoke", String.valueOf(this.ticksSinceSpoke));
		map.put("pauseCount", String.valueOf(this.pauseCount));
		return map;
	}

	@Override
	public void loadFromMap(Map<String,String> map) {
		this.x=Integer.valueOf(map.get("x"));
		this.y=Integer.valueOf(map.get("y"));
		this.z=Integer.valueOf(map.get("z"));
		this.world=MinecraftServer.getServer().worldServerForDimension(Integer.valueOf(map.get("dim")));
		this.duration=Integer.valueOf(map.get("duration"));
		this.ticksSinceSpoke=Integer.valueOf(map.get("ticksSinceSpoke"));
		this.pauseCount=Integer.valueOf(map.get("pauseCount"));
	}

}
