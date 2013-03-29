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

public class itemHauntCube extends ItemBlock{
	public itemHauntCube(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
	}
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    public String getItemNameIS(ItemStack par1ItemStack)
    {
   	return "tile.soundlogic.hauntBlock."+par1ItemStack.getItemDamage();
    }
}
