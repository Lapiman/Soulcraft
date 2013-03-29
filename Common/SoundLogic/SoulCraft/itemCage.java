package SoundLogic.SoulCraft;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class itemCage extends ItemBlock{
	public itemCage(int par1)
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
   	return "tile.soundlogic.cage."+par1ItemStack.getItemDamage();
    }
}
