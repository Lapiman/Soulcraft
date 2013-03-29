package SoundLogic.SoulCraft;

import SoundLogic.SoulCraft.HauntEvents.Generators.EventGeneratorTest;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.BonemealEvent;

public class ItemBlockPrimer extends Item
{

    public ItemBlockPrimer(int par1)
    {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }


    public String getItemNameIS(ItemStack par1ItemStack)
    {
    	return "item.soundlogic.primer."+par1ItemStack.getItemDamage();
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int blockX, int blockY, int blockZ, int par7, float par8, float par9, float par10)
    {
    	if(par3World.isRemote){return true;}
    	System.out.println("PRIMING");
    	EventGeneratorTest.eventtype=par1ItemStack.getItemDamage();
    	return true;
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 10; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
