package SoundLogic.SoulCraft.HauntEvents.Generators;

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
import SoundLogic.SoulCraft.HauntEvents.*;
import SoundLogic.SoulCraft.Haunting.Haunting;
import SoundLogic.SoulCraft.Haunting.HauntingLocation;

public class EventGeneratorTest extends EventGenerator{
	public static int eventtype=-1;
	@Override
	protected void addEvent(HauntEvent event,Haunting haunting)
	{
		super.addEvent(event, haunting);
		eventtype=-1;
	}
	public void Tick(Haunting haunting) {
		if(eventtype==-1)
			return;
		System.out.println("TICKING:"+eventtype);
		if(haunting instanceof HauntingLocation)
		{
			int t=6*2*1-haunting.stress;
			int v;
			if(t>1)
				v=this.random.nextInt(t);
			else
				v=0;
			if(v==0)
			{
				System.out.println("EVENT TRIGGERED");
				if(eventtype==0)
				{
					if(EventFakeCreeper.validForPlayer(haunting.world.getClosestPlayer(((HauntingLocation) haunting).location.a[0], ((HauntingLocation) haunting).location.a[1], ((HauntingLocation) haunting).location.a[2], -1)))
					{
						HauntEvent event2=new EventFakeCreeper(haunting.world.getClosestPlayer(((HauntingLocation) haunting).location.a[0], ((HauntingLocation) haunting).location.a[1], ((HauntingLocation) haunting).location.a[2], -1));
						this.addEvent(event2, haunting);
						haunting.stress=0;
					}
				}
				if(eventtype==1)
				{
					if(EventMeadSpiderChest.validForPlayer(haunting.world.getClosestPlayer(((HauntingLocation) haunting).location.a[0], ((HauntingLocation) haunting).location.a[1], ((HauntingLocation) haunting).location.a[2], -1)))
					{
						HauntEvent event2=new EventMeadSpiderChest(haunting.world.getClosestPlayer(((HauntingLocation) haunting).location.a[0], ((HauntingLocation) haunting).location.a[1], ((HauntingLocation) haunting).location.a[2], -1));
						this.addEvent(event2, haunting);
						haunting.stress=0;
					}
					eventtype=-1;
				}
				if(eventtype==2)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventFakeFireSafe.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventFakeFireSafe(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==3)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventFakeFireUnSafe.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventFakeFireUnSafe(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==4)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventFlickerTorch.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventFlickerTorch(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==5)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventFootsteps.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventFootsteps(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==6)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventSnuffTorch.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventSnuffTorch(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==7)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventSpider.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventSpider(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==8)
				{
					for(int i=5;i>0;i--)
					{
						int x,y,z;
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						x=(int) point[0];
						y=(int) point[1];
						z=(int) point[2];
						if(EventSpiderWeb.validForBlock(haunting.world, x, y, z))
						{
							haunting.stress=0;
							HauntEvent event=new EventSpiderWeb(haunting.world,x,y,z);
							this.addEvent(event, haunting);
							i=0;
						}
					}
				}
				if(eventtype==9)
				{
					float point1[]=new float[3];
					float point2[]=new float[3];
					boolean found1=false,found2=false;
					for(int i=5;i>0;i--)
					{
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						if(EventShuffleInventory.validForBlock(haunting.world,(int)point[0],(int)point[1],(int)point[2]))
						{
							point1=point;
							found1=true;
						}
					}
					for(int i=5;i>0;i--)
					{
						float point[]=((HauntingLocation) haunting).location.getPoint(random.nextFloat(), random.nextFloat(), random.nextFloat());
						if(EventShuffleInventory.validForBlock(haunting.world,(int)point[0],(int)point[1],(int)point[2]))
						{
							point2=point;
							found2=true;
						}
					}
					if(found1&&found2)
					{
						EventShuffleInventory event=new EventShuffleInventory(haunting.world,(int)point1[0],(int)point1[1],(int)point1[2]);
						event.addInventory((int)point2[0],(int)point2[1],(int)point2[2]);
						this.addEvent(event, haunting);
						haunting.stress=0;
					}
				}
			}
		}
	}
	@Override
	public String getName() {
		return "EventGeneratorWalker";
	}

}
