package SoundLogic.SoulCraft.Registry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
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

import SoundLogic.SoulCraft.HauntHandler;
import SoundLogic.SoulCraft.HauntEvents.*;
import SoundLogic.SoulCraft.HauntEvents.Generators.*;
import SoundLogic.SoulCraft.Haunting.*;
import SoundLogic.SoulCraft.Haunting.Cause.*;

public class FileRegistry {

	private static final String end="###";
	public static Map<Class<? extends IHauntCause>, String> causes=new HashMap();
	public static Map<Class<? extends Haunting>, String> hauntings=new HashMap();
	public static Map<Class<? extends HauntEvent>, String> events=new HashMap();
	public static Map<String, EventGenerator> generators=new HashMap();

	static {
		hauntings.put(HauntingBlockLocation.class, "HauntingBlockLocation");
		causes.put(TileEntityWrapper.class, "TileEntityWrapper");
		events.put(EventFootsteps.class, "EventFootsteps");
		events.put(EventFlickerTorch.class, "EventFlickerTorch");
		events.put(EventSpider.class, "EventSpider");
		events.put(EventFakeFireSafe.class, "EventFakeFireSafe");
		EventGeneratorTest.instance=new EventGeneratorTest();
		generators.put(EventGeneratorTest.instance.getName(), EventGeneratorTest.instance);
		}
	private static ArrayList<HauntEvent> getEventInstances()
	{
		ArrayList<HauntEvent> list=new ArrayList();
		for(Haunting haunt : HauntHandler.hauntings)
		{
			for(HauntEvent event : haunt.curEvents)
			{
				if(!list.contains(event))
					list.add(event);
			}
		}
		return list;
	}
	private static ArrayList<IHauntCause> getCauseInstances()
	{
		ArrayList<IHauntCause> list=new ArrayList();
		for(Haunting haunt : HauntHandler.hauntings)
		{
			for(IHauntCause cause : haunt.causes.causes.keySet())
			{
				if(!list.contains(cause))
					list.add(cause);
			}
		}
		return list;
	}
	private static Map<String,String> readMap(BufferedReader reader) throws IOException 
	{
		Map<String,String> map=new HashMap();
		boolean done=false;
		while(!done)
		{
			String str=reader.readLine();
			if(str==null)
			{
				return null;
			}
			if(str.equals(end))
				done=true;
			else
			{
				String[] entry=str.split(":");
				if(entry.length==1)
					map.put(entry[0], "");
				else
					map.put(entry[0], entry[1]);
			}
		}
		return map;
	}
	private static void writeMap(BufferedWriter writer, Map<String,String> map) throws IOException
	{
		for(Entry<String, String> entry : map.entrySet())
		{
			writer.write(entry.getKey()+":"+entry.getValue());
			writer.newLine();
		}
		writer.write(end);
		writer.newLine();
	}
	public static void Save(File file) throws IOException
	{
		ArrayList<IHauntCause> curCauses=getCauseInstances();
		ArrayList<HauntEvent> curEvents=getEventInstances();
		BufferedWriter writer=new BufferedWriter(new FileWriter(file));
		writeMap(writer,getGeneralMap(curCauses,curEvents));
		SaveCauses(writer,curCauses);
		SaveEvents(writer,curEvents);
		SaveHauntings(writer,curCauses,curEvents);
		writer.close();
	}
	private static Map<String,String> getGeneralMap(ArrayList<IHauntCause> causes,ArrayList<HauntEvent> events)
	{
		Map<String,String> map=new HashMap();
		map.put("Cause Number", String.valueOf(causes.size()));
		map.put("Event Number", String.valueOf(events.size()));
		return map;
	}
	private static void SaveCauses(BufferedWriter writer,ArrayList<IHauntCause> curCauses) throws IOException
	{
		for(IHauntCause cause : curCauses)
		{
			if(!(cause instanceof Haunting))
			{
				Map<String,String> map=cause.writeToMap();
				map.put("Type", "Cause");
				map.put("Cause Class ID", causes.get(cause.getClass()));
				map.put("Cause Instance ID", String.valueOf(curCauses.indexOf(cause)));
				writeMap(writer,map);
			}
		}
	}
	private static void SaveEvents(BufferedWriter writer, ArrayList<HauntEvent> curEvents) throws IOException
	{
		for(HauntEvent event : curEvents)
		{
			Map<String,String> map=event.writeToMap();
			if(map==null)
			{
				break;
			}
			map.put("Type", "Event");
			map.put("Event Class ID", events.get(event.getClass()));
			map.put("Event Instance ID", String.valueOf(curEvents.indexOf(event)));
			writeMap(writer,map);
		}
	}
	private static void SaveHauntings(BufferedWriter writer,ArrayList<IHauntCause> curCauses,ArrayList<HauntEvent> curEvents) throws IOException
	{
		ArrayList<Haunting> curHauntings=HauntHandler.hauntings;
		for(Haunting haunting : curHauntings)
		{
			Map<String,String> map=haunting.writeToMap();
			if(curCauses.contains(haunting))
				map.put("Cause Instance ID", String.valueOf(curCauses.indexOf(haunting)));
			map.put("Type", "Haunting");
			map.put("Haunting Class ID", hauntings.get(haunting.getClass()));
			if(haunting.generator!=null)
				map.put("Generator Class ID", haunting.generator.getName());
			String str="";
			for(Entry<IHauntCause,Integer> entry : haunting.causes.causes.entrySet())
			{
				str+=curCauses.indexOf(entry.getKey());
				str+=",";
				str+=entry.getValue().toString();
				str+="|";
			}
			map.put("Causes", str);
			str="";
			for(HauntEvent entry : haunting.curEvents)
			{
				str+=curEvents.indexOf(entry);
				str+="|";
			}
			map.put("Events", str);
			writeMap(writer,map);
		}
	}
	public static void Load(File file)
	{
		HauntHandler.hauntings.clear();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			return;
		}
		ArrayList<Map<String,String>> maps=new ArrayList();
		while(true)
		{
			try
			{
				Map<String,String> map=readMap(reader);
				if(map==null)
				{
					break;
				}
				maps.add(map);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
		}
		LoadFromMaps(maps);
		try {reader.close();} catch (IOException e) {}
	}
	private static Class<? extends IHauntCause> getCauseClass(String str)
	{
		for(Entry<Class<? extends IHauntCause>,String> entry: causes.entrySet())
		{
			if(entry.getValue().equals(str))
			{
				return entry.getKey();
			}
		}
		return null;
	}
	private static Class<? extends HauntEvent> getEventClass(String str)
	{
		for(Entry<Class<? extends HauntEvent>,String> entry: events.entrySet())
		{
			if(entry.getValue().equals(str))
			{
				return entry.getKey();
			}
		}
		return null;
	}
	private static Class<? extends Haunting> getHauntClass(String str)
	{
		for(Entry<Class<? extends Haunting>,String> entry: hauntings.entrySet())
		{
			
			if(entry.getValue().equals(str))
			{
				return entry.getKey();
			}
		}
		return null;
	}
	private static void LoadFromMaps(ArrayList<Map<String, String>> maps) {
		if(maps.size()==0)
			return;
		IHauntCause[] causearray=new IHauntCause[Integer.valueOf(maps.get(0).get("Cause Number"))];
		HauntEvent[] eventarray=new HauntEvent[Integer.valueOf(maps.get(0).get("Event Number"))];
		Map<Map<String,String>,Haunting> haunts=new HashMap();
		for(int i=1;i<maps.size();i++)
		{
			Map<String,String> map=maps.get(i);
			if(map.get("Type").equals("Cause"))
			{
				int id=Integer.valueOf(map.get("Cause Instance ID"));
				try
				{
					causearray[id]=getCauseClass(map.get("Cause Class ID")).newInstance();
					causearray[id].loadFromMap(map);
				}
				catch(Exception e)
				{
					System.out.println("ID:"+id);
					System.out.println("Class ID:"+map.get("Cause Class ID"));
					System.out.println("Class:"+getCauseClass(map.get("Cause Class ID")));
					e.printStackTrace();
				}
			}
			else if(map.get("Type").equals("Event"))
			{
				int id=Integer.valueOf(map.get("Event Instance ID"));
				try
				{
					eventarray[id]=getEventClass(map.get("Event Class ID")).newInstance();
					eventarray[id].loadFromMap(map);
				}
				catch(Exception e)
				{
					System.out.println("ID:"+id);
					System.out.println("Class ID:"+map.get("Event Class ID"));
					System.out.println("Event:"+getEventClass(map.get("Event Class ID")));
					e.printStackTrace();
				}
			}
			else if(map.get("Type").equals("Haunting"))
			{
				Haunting haunt=null;
				try
				{
					haunt=getHauntClass(map.get("Haunting Class ID")).newInstance();
					haunt.generator=generators.get(map.get("Generator Class ID"));
				}
				catch(Exception e)
				{
					System.out.println("Class ID:"+map.get("Haunting Class ID"));
					System.out.println("Class:"+getHauntClass(map.get("Haunting Class ID")));
					e.printStackTrace();
				}
				haunt.loadFromMap(map);
				if(map.containsKey("Cause Instance ID"))
				{
					int id=Integer.valueOf(map.get("Cause Instance ID"));
					causearray[id]=haunt;
				}
				haunts.put(map, haunt);
			}
			else
			{
				System.out.println("UNKNOWN TYPE:"+map.get("Type"));
			}
		}
		for(Entry<Map<String,String>,Haunting> entry : haunts.entrySet())
		{
			entry.getValue().loadCausesEffects(entry.getKey(), eventarray, causearray);
			HauntHandler.AddHaunt(entry.getValue());
		}
	}
}
