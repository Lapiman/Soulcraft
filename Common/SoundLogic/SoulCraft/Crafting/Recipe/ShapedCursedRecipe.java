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

public class ShapedCursedRecipe extends CursedRecipe
{
    /** How many horizontal slots this recipe is wide. */
    public final int recipeWidth;

    /** How many vertical slots this recipe uses. */
    public final int recipeHeight;

    /** Is a List of ItemStack that composes the recipe. */
    public final List<ItemStack> recipeItems;
    public final List<Boolean> recipeItemsAcceptHaunted;
    public final List<Integer> recipeItemsIncreaseHaunted;
    public final List<Boolean> recipeItemsAbsorbHaunted;
    public final List<Boolean> recipeItemsLeaveBehind;

    /** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;

    public ShapedCursedRecipe(ItemStack output, int width, int height, List recipe, List acceptHaunted,List increaseHaunted,List absorbHaunted,List leaveBehind)
    {
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.recipeItems = recipe;
        this.recipeOutput = output;
        this.recipeItemsAbsorbHaunted=absorbHaunted;
        this.recipeItemsAcceptHaunted=acceptHaunted;
        this.recipeItemsIncreaseHaunted=increaseHaunted;
        this.recipeItemsLeaveBehind=leaveBehind;
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
        for (int var3 = 0; var3 <= 3 - this.recipeWidth; ++var3)
        {
            for (int var4 = 0; var4 <= 3 - this.recipeHeight; ++var4)
            {
                if (this.checkMatch(par1InventoryCrafting, var3, var4, true))
                {
                    return true;
                }

                if (this.checkMatch(par1InventoryCrafting, var3, var4, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting par1InventoryCrafting, int par2, int par3, boolean par4)
    {
        for (int var5 = 0; var5 < 3; ++var5)
        {
            for (int var6 = 0; var6 < 3; ++var6)
            {
                int var7 = var5 - par2;
                int var8 = var6 - par3;
                ItemStack var9 = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight)
                {
                    if (par4)
                    {
                        var9 = this.recipeItems.get(this.recipeWidth - var7 - 1 + var8 * this.recipeWidth);
                    }
                    else
                    {
                        var9 = this.recipeItems.get(var7 + var8 * this.recipeWidth);
                    }
                }

                ItemStack var10 = par1InventoryCrafting.getStackInRowAndColumn(var5, var6);

                if (var10 != null || var9 != null)
                {
                    if (var10 == null && var9 != null || var10 != null && var9 == null)
                    {
                        return false;
                    }

                    if (var9.itemID != var10.itemID)
                    {
                        return false;
                    }

                    if (var9.getItemDamage() != -1 && var9.getItemDamage() != var10.getItemDamage())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
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
        return ItemHaunted.instance.hauntStack(this.recipeOutput.copy(),levels/recipeOutput.stackSize);
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
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeWidth * this.recipeHeight;
    }
}
