package SoundLogic.SoulCraft.Crafting;

import java.util.List;

import SoundLogic.SoulCraft.ItemHaunted;
import SoundLogic.SoulCraft.Crafting.Recipe.CursedRecipe;

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

public class CursedCraftingHandler implements ICraftingHandler{

	public static CursedRecipe getRecipe(InventoryCrafting craftMatrix,World world)
	{
		List<IRecipe> recipes=CraftingManager.getInstance().getRecipeList();
		CursedRecipe recipe=null;
		for(int i=0;i<recipes.size();i++)
		{
			if(recipes.get(i) instanceof CursedRecipe)
			{
				recipe=(CursedRecipe) recipes.get(i);
				if(recipe.matches(craftMatrix, world))
					i=recipes.size();
			}
		}
		return recipe;
	}
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		CursedRecipe recipe=getRecipe((InventoryCrafting)craftMatrix,player.worldObj);
		if(recipe==null)
			return;
		boolean[][] absorbHaunted=recipe.absorbHauntedArray((InventoryCrafting)craftMatrix, player);
		boolean[][] leaveBehind=recipe.leaveBehindArray((InventoryCrafting)craftMatrix,player);
		int[][] increaseHaunted=recipe.increaseHauntedArray((InventoryCrafting)craftMatrix,player);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				ItemStack stack=craftMatrix.getStackInSlot(i+j*3);
				if(stack!=null)
				{
					ItemStack container=getContainer(stack);
					boolean haunt=increaseHaunted[i][j]>0;
					boolean stay=leaveBehind[i][j];
					boolean absorb=absorbHaunted[i][j];
					ItemStack result=stack;
					if(stay)
					{
						if(absorb && stack.stackSize==1)
						{
							result=ItemHaunted.instance.getBaseItemStack(result);
							result.stackSize++;
						}
						else if(absorb)
						{
							player.inventory.addItemStackToInventory(ItemHaunted.instance.getBaseItemStack(result));
						}
						else
							result.stackSize++;
					}
					else if(stack.stackSize==1 && container==null && haunt)
					{
						result=ItemHaunted.instance.hauntStack(stack, increaseHaunted[i][j]);
						result.stackSize++;
					}
					else if(stack.stackSize==1 && container!=null && !absorb)
					{
						result=ItemHaunted.instance.hauntStack(container, increaseHaunted[i][j]);
						result.stackSize++;
					}
					else if(container==null && haunt)
					{
						player.inventory.addItemStackToInventory(ItemHaunted.instance.hauntStack(stack, increaseHaunted[i][j]));
					}
					else if(container!=null && !absorb)
					{
						player.inventory.addItemStackToInventory(ItemHaunted.instance.hauntStack(container, increaseHaunted[i][j]));
					}
					else if(absorb && container!=null)
					{
						if(absorb && stack.stackSize==1)
						{
							result=container;
							result.stackSize++;
						}
						else
						{
							player.inventory.addItemStackToInventory(container);
						}
					}
					craftMatrix.setInventorySlotContents(i+j*3, result);
				}
			}
	}
	private ItemStack getContainer(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemHaunted)
			return ((ItemHaunted)stack.getItem()).getCursedContainerItemStack(stack);
		else
			return stack.getItem().getContainerItemStack(stack);
	}
	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		
	}

}
