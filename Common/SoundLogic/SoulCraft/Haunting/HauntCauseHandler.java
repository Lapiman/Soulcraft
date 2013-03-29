package SoundLogic.SoulCraft.Haunting;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

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

import SoundLogic.SoulCraft.Haunting.Cause.*;
public class HauntCauseHandler {
	private boolean valid;
	private int coolDown;
	private int chain;
	public Map<IHauntCause,Integer> causes=new HashMap(); //Fancy list of causes and weights.
	public boolean valid(){return valid;} //Is the haunt valid despite any causal removal
	private int totalCause()
	{
		int tot=0;
		for(Entry<IHauntCause,Integer> a :causes.entrySet())
		{
			tot+=a.getValue().intValue();
		}
		return tot;
	}
	//This is the magic function that makes stuff happen.
	private double probVanish(int weight, int chain)
	{
		return Math.pow(((double)weight)/totalCause(), 1+1.0D/Math.sqrt(chain));
	}
	//Well, actually that doesn't make things happen, it just gives me probabilities.
	//This makes things happen
	public boolean checkCauses()
	{
		boolean removed=false;
		Random rand=new Random();
		coolDown-=1;
		if(coolDown<0)
		{
			coolDown=0;
			chain=0;
		}
		for(Entry<IHauntCause,Integer> a :causes.entrySet())
		{
			if(a.getValue()!=0)
				if(!a.getKey().valid())
				{
					chain++;
					double prob=probVanish(a.getValue().intValue(),chain);
					a.setValue(0);
					if(rand.nextDouble()<prob)
					{
						removed=true;
					}
					//If you remove multiple causes of a haunting within a minute of each other
					//Usually by working with other people, you can get a bonus from chaining a combo
					//Like in fighting games
					coolDown=20*60;
				}
		}
		if(removed)
			cleanCauses();
		return !removed;
	}
	public void cleanCauses()
	{
		Map<IHauntCause,Integer> copy=new HashMap(causes);
		for(Entry<IHauntCause,Integer> a :causes.entrySet())
		{
			if(a.getValue()!=0)
			{
				causes.remove(a.getKey());
			}
		}
	}
	public void addCause(IHauntCause cause,int weight)
	{
		addCause(cause,weight,false);
	}
	public void addCause(IHauntCause cause,int weight, boolean stack)
	{
		if(causes.containsKey(cause) && stack)
			causes.put(cause, causes.get(cause)+weight);
		else
			causes.put(cause, weight);
	}
}
