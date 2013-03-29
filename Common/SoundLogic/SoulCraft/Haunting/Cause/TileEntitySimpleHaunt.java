package SoundLogic.SoulCraft.Haunting.Cause;

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

public class TileEntitySimpleHaunt extends TileEntity{

	int tickssincehit;


	public boolean valid()
	{
		return true;
	}
	public void updateEntity()
	{
		this.tickssincehit--;
		if(this.tickssincehit<0)
			this.tickssincehit=0;
	}

	public HauntEvent protectEvent() {
		return null;
	}


	public boolean underAttack() {
		// TODO Auto-generated method stub
		return this.tickssincehit>0;
	}
	public void playerPunch(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		this.tickssincehit=40;
	}

}
