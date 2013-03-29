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

public class EventSnuffTorch extends HauntEvent {

	int x,y,z;
	World world;
	public EventSnuffTorch()
	{
	}
	public EventSnuffTorch(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		return world.getBlockId(x, y, z)==Block.torchWood.blockID;
	}


	@Override
	public void StartEvent() {
		world.setBlock(x, y, z, SoulMod.proxy.DeadTorch.blockID);
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
