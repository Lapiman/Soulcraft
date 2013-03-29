package SoundLogic.SoulCraft.Crafting;

import java.util.List;

import SoundLogic.SoulCraft.ItemHaunted;
import SoundLogic.SoulCraft.SoulMod;
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

public class CraftingHandlerSpecialCases implements ICraftingHandler{

	public static IRecipe getRecipe(InventoryCrafting craftMatrix,World world)
	{
		List<IRecipe> recipes=CraftingManager.getInstance().getRecipeList();
		IRecipe recipe=null;
		for(int i=0;i<recipes.size();i++)
		{
			recipe=recipes.get(i);
			if(recipe.matches(craftMatrix, world))
				i=recipes.size();
		}
		return recipe;
	}
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		IRecipe recipe=getRecipe((InventoryCrafting)craftMatrix,player.worldObj);
		if(recipe==SoulMod.proxy.BlazeMeshRecipe)
		{
			for(int i=0;i<craftMatrix.getSizeInventory();i++)
			{
				ItemStack stack=craftMatrix.getStackInSlot(i);
				ItemStack targetStack;
				if(stack!=null)
				{
					if(stack.getItem() instanceof ItemHaunted)
						targetStack=ItemHaunted.instance.hauntStack(new ItemStack(SoulMod.proxy.paperFunnel),stack.getItemDamage());
					else
						targetStack=new ItemStack(SoulMod.proxy.paperFunnel);
					targetStack.stackSize=2;
					craftMatrix.setInventorySlotContents(i, targetStack);
				}
			}
		}
		else if(recipe==SoulMod.proxy.soulMuckRecipe)
		{
			for(int i=0;i<craftMatrix.getSizeInventory();i++)
			{
				ItemStack stack=craftMatrix.getStackInSlot(i);
				ItemStack original=stack;
				if(stack!=null)
				{
					if(stack.getItem() instanceof ItemHaunted)
					{
						original=((ItemHaunted)stack.getItem()).getBaseItemStack(stack);
					}
					if(original.getItem()==Item.bucketWater)
					{
						craftMatrix.setInventorySlotContents(i, null);
					}
				}
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
