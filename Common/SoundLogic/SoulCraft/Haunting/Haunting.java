package SoundLogic.SoulCraft.Haunting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import SoundLogic.SoulCraft.HauntEvents.Generators.EventGenerator;
import SoundLogic.SoulCraft.HauntEvents.HauntEvent;
import SoundLogic.SoulCraft.Haunting.Cause.IHauntCause;

public abstract class Haunting implements IHauntCause{
	public World world;
	public int stress=0;
	private boolean removed;
	public EventGenerator generator=null;
	public HauntCauseHandler causes=new HauntCauseHandler();
	public ArrayList<HauntEvent> curEvents=new ArrayList();
	public boolean valid(){return exists();}
	public abstract boolean targetExists();
	public int strength=1;
	public void Tick()
	{
		stress++;
		if(generator!=null)
			generator.Tick(this);
		ArrayList<HauntEvent> theEvents=new ArrayList(curEvents);
		for(HauntEvent event : theEvents)
		{
			if(event.getDurationRemaining()==0)
			{
				event.EndEvent();
				curEvents.remove(event);
			}
			else
			{
				event.ProgressEvent();
			}
		}
	}
	public Haunting(){}
	public final boolean exists()
	{
		return !removed && targetExists();
	}
	public final void causesCheck()
	{
		removed=!causes.checkCauses();
	}
	@Override
	public HauntEvent protectEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean underAttack() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Map<String, String> writeToMap() {
		Map map=new HashMap<String,String>();
		return map;
	}

	@Override
	public void loadFromMap(Map<String, String> map) {
		
	}
	public void loadCausesEffects(Map<String,String> map, HauntEvent[] events, IHauntCause[] causes)
	{
		String strCause=map.get("Causes");
		String strEvent=map.get("Events");
		String[] arrCause=strCause.split("\\|");
		String[] arrEvent=strEvent.split("\\|");
		for(int i=0;i<arrEvent.length;i++)
		{
			try{
			HauntEvent event=events[Integer.valueOf(arrEvent[i])];
			curEvents.add(event);
			}
			catch(Exception e){}
		}
		for(int i=0;i<arrCause.length;i++)
		{
			String[] data=arrCause[i].split(",");
			try
			{
			IHauntCause cause=causes[Integer.valueOf(data[0])];
			this.causes.addCause(cause, Integer.valueOf(data[1]));
			}
			catch(Exception e){}
		}
	}
}
