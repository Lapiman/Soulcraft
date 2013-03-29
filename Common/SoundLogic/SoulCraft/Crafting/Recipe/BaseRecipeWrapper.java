package SoundLogic.SoulCraft.Crafting.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import SoundLogic.SoulCraft.ItemHaunted;
import SoundLogic.SoulCraft.ContainerBlank;
import SoundLogic.SoulCraft.Crafting.CursedCraftingHandler;

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
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.*;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BaseRecipeWrapper extends CursedRecipe{

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		if(var1==null)
			return false;
		for(int i=0;i<var1.getSizeInventory();i++)
		{
			if(var1.getStackInSlot(i)!=null)
				if(var1.getStackInSlot(i).getItem() instanceof ItemHaunted)
				{
					return getRecipe(var1,var2)!=null;
				}
		}
		return false;
	}
	private InventoryCrafting stripHaunts(InventoryCrafting var1)
	{
		InventoryCrafting result=new InventoryCrafting(new ContainerBlank(),3,3);
		for(int i=0;i<var1.getSizeInventory();i++)
		{
			ItemStack stack=var1.getStackInSlot(i);
			if(stack!=null)
			{
				if(stack.getItem() instanceof ItemHaunted)
					result.setInventorySlotContents(i,((ItemHaunted) stack.getItem()).getBaseItemStack(stack));
				else
					result.setInventorySlotContents(i,stack);
			}
		}
		return result;
	}
	private IRecipe getRecipe(InventoryCrafting var1, World var2)
	{
		List<IRecipe> recipes=CraftingManager.getInstance().getRecipeList();
		IRecipe recipe=null;
		InventoryCrafting temp=stripHaunts(var1);
		for(int j=0;j<temp.getSizeInventory();j++)
		{
			System.out.println(temp.getStackInSlot(j));
		}
		for(int i=0;i<recipes.size();i++)
		{
			if(
					recipes.get(i) instanceof ShapelessRecipes || 
					recipes.get(i) instanceof ShapedRecipes || 
					recipes.get(i) instanceof ShapelessOreRecipe || 
					recipes.get(i) instanceof ShapedOreRecipe)
			{
				recipe=recipes.get(i);
				if(recipe.matches(temp, var2))
				{
					return recipe;
				}
			}
		}
		return null;
	}
	@Override
    public ItemStack getCraftingResult(InventoryCrafting craftMatrix)
    {
    	boolean[][] absorb=this.absorbHauntedArray(craftMatrix,null);
    	int levels=0;
    	for(int i=0;i<3;i++)
        	for(int j=0;j<3;j++)
        	{
        		if(absorb[i][j])
        		{
        			ItemStack stack=craftMatrix.getStackInRowAndColumn(i, j);
        			if(stack!=null)
        				if(stack.getItem() instanceof ItemHaunted)
        				{
        					levels+=stack.getItemDamage();
        				}
        		}
        	}
    	System.out.println("RECIPE");
    	ItemStack result=getRecipe(craftMatrix,null).getCraftingResult(craftMatrix);
    	int size=result.stackSize;
    	result.stackSize=1;
    	result=ItemHaunted.instance.hauntStack(result,levels/size);
    	result.stackSize=size;
    	System.out.println(ItemHaunted.instance.getBaseItemStack(result));
        return result;
    }

	@Override
	public int getRecipeSize() {
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public boolean[][] acceptHauntedArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		return new boolean[][]{{true,true,true},{true,true,true},{true,true,true}};
	}

	@Override
	public boolean[][] absorbHauntedArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		return new boolean[][]{{true,true,true},{true,true,true},{true,true,true}};
	}

	@Override
	public boolean[][] leaveBehindArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		return new boolean[][]{{false,false,false},{false,false,false},{false,false,false}};
	}

	@Override
	public int[][] increaseHauntedArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		return new int[][]{{0,0,0},{0,0,0},{0,0,0}};
	}

}
