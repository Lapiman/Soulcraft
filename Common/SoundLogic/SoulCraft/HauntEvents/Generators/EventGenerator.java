package SoundLogic.SoulCraft.HauntEvents.Generators;

import java.util.HashMap;
import java.util.Map;
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

import SoundLogic.SoulCraft.HauntEvents.HauntEvent;
import SoundLogic.SoulCraft.Haunting.Haunting;

public abstract class EventGenerator {
	public static EventGenerator instance;
	Random random=new Random();
	public abstract void Tick(Haunting haunting);
	public abstract String getName();
	protected void addEvent(HauntEvent event,Haunting haunting)
	{
		event.StartEvent();
		if(event.getDurationRemaining()==0)
		{
			event.ProgressEvent();
			event.EndEvent();
		}
		else
			haunting.curEvents.add(event);
	}
	public Map<String, String> writeToMap() {
		Map map=new HashMap<String,String>();
		return map;
	}

	public void loadFromMap(Map<String, String> map) {
		
	}
}
