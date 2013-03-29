package SoundLogic.SoulCraft.HauntEvents.Fire;

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

public class TileEntitySpecialFire extends TileEntity{
	public int TrackID;
    /**
     * Writes a tile entity to NBT.
     */
	public void setID(int id)
	{
		TrackID=id;
		FireTracker.fires.add(this);
	}
	public void invalidate()
	{
		FireTracker.fires.remove(this);
	}
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("hauntingsource", this.TrackID);
    }
    public boolean removeIfMatches(int id)
    {
    	if(TrackID!=id) return false;
    	this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, 0);
    	return true;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.TrackID = par1NBTTagCompound.getInteger("hauntingsource");
        setID(TrackID);
    }
}
