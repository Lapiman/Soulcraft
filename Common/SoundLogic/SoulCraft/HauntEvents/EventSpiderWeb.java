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

public class EventSpiderWeb extends HauntEvent {

	int x,y,z;
	World world;
	public EventSpiderWeb()
	{
	}
	public EventSpiderWeb(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		System.out.println(world.getBlockId(x, y, z));
		return world.getBlockId(x, y, z)==0;
	}


	@Override
	public void StartEvent() {
		world.setBlock(x, y, z, Block.web.blockID);
	}

	@Override
	public void ProgressEvent() {
	}

	@Override
	public void EndEvent() {

	}

	@Override
	public void sourceRemoved() {
	}

	@Override
	public int getDurationRemaining() {
		return 0;
	}

	@Override
	public Map writeToMap() {
		return null;
	}

	@Override
	public void loadFromMap(Map<String,String> map) {
	}

}
