package SoundLogic.SoulCraft;

import java.util.ArrayList;
import java.util.Random;

import SoundLogic.SoulCraft.HauntEvents.HauntEvent;
import SoundLogic.SoulCraft.Haunting.Haunting;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class HauntHandler {
	public static ArrayList<Haunting> hauntings=new ArrayList<Haunting>();
	public static void AddHaunt(Haunting haunt)
	{
		hauntings.add(haunt);
	}
	public static void Tick()
	{
		ArrayList<Haunting> copy=new ArrayList<Haunting>(hauntings);
		for(Haunting haunt : copy)
		{
			haunt.Tick();
			haunt.causesCheck();
			if(!haunt.exists())
			{
				hauntings.remove(haunt);
				for(HauntEvent event : haunt.curEvents)
				{
					event.sourceRemoved();
				}
			}
		}
	}
}
