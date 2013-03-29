package SoundLogic.SoulCraft.HauntEvents;

import java.util.ArrayList;
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
import net.minecraftforge.common.ISidedInventory;

public class EventShuffleInventory extends HauntEvent {

	int x,y,z,duration,ticksSinceSpoke,pauseCount;
	ArrayList<IInventory> inventories;
	World world;
	Random random;
	public EventShuffleInventory()
	{
		this.random=new Random();
	}
	public EventShuffleInventory(World world, int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.world=world;
		this.inventories=new ArrayList();
		this.random=new Random();
	}
	public static boolean validForBlock(World world, int x, int y, int z) {
		TileEntity tile=world.getBlockTileEntity(x, y, z);
		if(tile==null)
		{
			return false;
		}
		if(tile instanceof IInventory)
		{
			return true;
		}
		return false;
	}


	@Override
	public void StartEvent() {
		TileEntity tile=world.getBlockTileEntity(x, y, z);
		if(tile==null)
		{
			return;
		}
		if(!(tile instanceof IInventory))
		{
			return;
		}
		IInventory inv=(IInventory) tile;
		this.inventories.add(inv);
		System.out.println("TEST");
	}
	public void addInventory(int x,int y,int z)
	{
		TileEntity tile=world.getBlockTileEntity(x, y, z);
		if(tile==null)
		{
			return;
		}
		if(!(tile instanceof IInventory))
		{
			return;
		}
		IInventory inv=(IInventory) tile;
		this.inventories.add(inv);
	}

	@Override
	public void ProgressEvent() {
	}

	@Override
	public void EndEvent() {
		ItemStack[] stacks=new ItemStack[inventories.size()*4];
		int i=0;
		int j=0;
		while(i+j/10<stacks.length)
		{
			for(IInventory inv : inventories)
			{
				if(i>stacks.length)
					break;
				System.out.println(inv);
				System.out.println(random);
				int slot=random.nextInt(inv.getSizeInventory());
				ItemStack stack=inv.getStackInSlot(slot);
				inv.setInventorySlotContents(slot,null);
				if(stack==null)
					break;
				if(stack.stackSize==0)
					break;
				stacks[i]=stack;
				i++;
			}
			j++;
		}
		for(i=0;i<stacks.length;i++)
		{
			if(stacks[i]==null)
				break;
			IInventory inv=inventories.get(random.nextInt(inventories.size()));
			boolean handled=false;
			for(j=0;j<inv.getSizeInventory();j++)
			{
				if(!handled)
					if(inv.getStackInSlot(j)==null || inv.getStackInSlot(j).stackSize==0)
					{
						inv.setInventorySlotContents(j, stacks[i]);
						handled=true;
					}
			}
			if(!handled)i--;
		}
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
