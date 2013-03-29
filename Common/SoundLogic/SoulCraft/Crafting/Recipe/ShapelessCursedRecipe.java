package SoundLogic.SoulCraft.Crafting.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import SoundLogic.SoulCraft.ItemHaunted;
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
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.*;

public class ShapelessCursedRecipe extends CursedRecipe
{
    /** Is the ItemStack that you get when craft the recipe. */
    private final ItemStack recipeOutput;

    /** Is a List of ItemStack that composes the recipe. */
    public final List recipeItems;
    public final List<Boolean> recipeItemsAcceptHaunted;
    public final List<Integer> recipeItemsIncreaseHaunted;
    public final List<Boolean> recipeItemsAbsorbHaunted;
    public final List<Boolean> recipeItemsLeaveBehind;

    public ShapelessCursedRecipe(ItemStack par1ItemStack, List par2List, List par3, List par4, List par5, List par6)
    {
        this.recipeOutput = par1ItemStack;
        this.recipeItems = par2List;
        recipeItemsAcceptHaunted=par3;
        recipeItemsIncreaseHaunted=par4;
        recipeItemsAbsorbHaunted=par5;
        recipeItemsLeaveBehind=par6;
    }

    public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        ArrayList<ItemStack> var3 = new ArrayList(this.recipeItems);

        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 3; ++var5)
            {
                ItemStack var6 = par1InventoryCrafting.getStackInRowAndColumn(var5, var4);

                if (var6 != null)
                {
                    boolean var7 = false;
                    ArrayList<ItemStack> staticStack=new ArrayList(var3);
                    for(int i=0;i<staticStack.size();i++)
                    {
                    	ItemStack var9=staticStack.get(i);
                    	Item item=var6.getItem();
                        if (var6.itemID == var9.itemID && (var9.getItemDamage() == -1 || var6.getItemDamage() == var9.getItemDamage()))
                        {
                            var7 = true;
                            staticStack.remove(var9);
                            break;
                        }
                        else if(item instanceof ItemHaunted && recipeItemsAcceptHaunted.get(i))
                    	{
                    		var6=((ItemHaunted) item).getBaseItemStack(var6);
                            if (var6.itemID == var9.itemID && (var9.getItemDamage() == -1 || var6.getItemDamage() == var9.getItemDamage()))
                            {
                                var7 = true;
                                staticStack.remove(var9);
                                break;
                            }
                    	}
                    }
                    var3=staticStack;

                    if (!var7)
                    {
                        return false;
                    }
                }
            }
        }

        return var3.isEmpty();
    }
    /**
     * Sorts the itemstacks to match the initial recipe
     */
    public List<ItemStack> sortToMatch(IInventory craftMatrix, World par2World)
    {
        ArrayList<ItemStack> var3 = new ArrayList(this.recipeItems);
        ArrayList<ItemStack> preprocess = new ArrayList(this.recipeItems);
        ArrayList<ItemStack> sorted = new ArrayList(this.recipeItems);

        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 3; ++var5)
            {
                ItemStack var6 = craftMatrix.getStackInSlot(var5*3+var4);

                if (var6 != null)
                {
                    boolean var7 = false;
                    ArrayList<ItemStack> staticStack=new ArrayList(var3);
                    for(int i=0;i<staticStack.size();i++)
                    {
                    	ItemStack var9=staticStack.get(i);
                    	Item item=var6.getItem();
                        if (var6.itemID == var9.itemID && (var9.getItemDamage() == -1 || var6.getItemDamage() == var9.getItemDamage()))
                        {
                            var7 = true;
                            sorted.set(preprocess.indexOf(var9), var6);
                            var3.remove(var9);
                            break;
                        }
                        else if(item instanceof ItemHaunted && recipeItemsAcceptHaunted.get(i))
                    	{
                    		ItemStack base=((ItemHaunted) item).getBaseItemStack(var9);
                            if (base.itemID == var9.itemID && (var9.getItemDamage() == -1 || base.getItemDamage() == var9.getItemDamage()))
                            {
                                var7 = true;
                                sorted.set(preprocess.indexOf(var9), var6);
                                var3.remove(var9);
                                break;
                            }
                    	}
                    }
                }
            }
        }
        return sorted;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
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
        			if(stack.getItem() instanceof ItemHaunted)
        			{
        				levels+=stack.getItemDamage();
        			}
        		}
        	}
    	ItemStack result=this.recipeOutput.copy();
    	int size=result.stackSize;
    	result.stackSize=1;
    	result=ItemHaunted.instance.hauntStack(result,levels/size);
    	result.stackSize=size;
        return result;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeItems.size();
    }

	@Override
	public boolean[][] acceptHauntedArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		boolean[][] result=new boolean[3][3];
		List<ItemStack> sortedStacks;
		if(player!=null)
			sortedStacks=sortToMatch(craftMatrix, player.worldObj);
		else
			sortedStacks=sortToMatch(craftMatrix, null);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				int q=sortedStacks.indexOf(craftMatrix.getStackInRowAndColumn(i, j));
				if(q!=-1)
					result[i][j]=recipeItemsAcceptHaunted.get(q);
			}
		return result;
	}

	@Override
	public boolean[][] absorbHauntedArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		boolean[][] result=new boolean[3][3];
		List<ItemStack> sortedStacks;
		if(player!=null)
			sortedStacks=sortToMatch(craftMatrix, player.worldObj);
		else
			sortedStacks=sortToMatch(craftMatrix, null);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				int q=sortedStacks.indexOf(craftMatrix.getStackInRowAndColumn(i, j));
				if(q!=-1)
					result[i][j]=recipeItemsAbsorbHaunted.get(q);
			}
		return result;
	}

	@Override
	public boolean[][] leaveBehindArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		boolean[][] result=new boolean[3][3];
		List<ItemStack> sortedStacks;
		if(player!=null)
			sortedStacks=sortToMatch(craftMatrix, player.worldObj);
		else
			sortedStacks=sortToMatch(craftMatrix, null);
		if(player!=null)
			sortedStacks=sortToMatch(craftMatrix, player.worldObj);
		else
			sortedStacks=sortToMatch(craftMatrix, null);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				int q=sortedStacks.indexOf(craftMatrix.getStackInRowAndColumn(i, j));
				if(q!=-1)
					result[i][j]=recipeItemsLeaveBehind.get(q);
			}
		return result;
	}

	@Override
	public int[][] increaseHauntedArray(InventoryCrafting craftMatrix,
			EntityPlayer player) {
		int[][] result=new int[3][3];
		List<ItemStack> sortedStacks;
		if(player!=null)
			sortedStacks=sortToMatch(craftMatrix, player.worldObj);
		else
			sortedStacks=sortToMatch(craftMatrix, null);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				int q=sortedStacks.indexOf(craftMatrix.getStackInRowAndColumn(i, j));
				if(q!=-1)
					result[i][j]=recipeItemsIncreaseHaunted.get(q);
			}
		return result;
	}
}
