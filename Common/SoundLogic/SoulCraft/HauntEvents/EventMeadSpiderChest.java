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
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.*;

public class EventMeadSpiderChest extends HauntEvent {

	int x,y,z;
	World world;
	public EventMeadSpiderChest()
	{
	}
	public EventMeadSpiderChest(EntityPlayer player)
	{
		this.x=(int) player.posX;
		this.y=(int) player.posY;
		this.z=(int) player.posZ;
		this.world=player.worldObj;
	}
	public static boolean validForPlayer(EntityPlayer player) {
		return player.username.equals("SOTMead");
	}


	@Override
	public void StartEvent() {
		for(int i=-2;i<=2;i++)
			for(int j=-2;j<=2;j++)
				for(int k=-2;k<=2;k++)
				{
					if(!(Math.abs(i)<2 && Math.abs(j)<2 && Math.abs(k)<2))
					{
						if((i+j+k)%2==0)
							world.setBlock(x+i, y+k+1, z+j, Block.glass.blockID);
						else
							world.setBlock(x+i, y+k+1, z+j, Block.chest.blockID);
					}
					else
					{
						world.setBlock(x+i, y+k+1, z+j, 0);
						Entity ent=new EntityCaveSpider(world);
						ent.setLocationAndAngles(x+i, y+k, z+j, 0, 0);
						world.spawnEntityInWorld(ent);
					}
				}
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
