package SoundLogic.SoulCraft.Crafting;

import java.util.ArrayList;
import java.util.List;

import SoundLogic.SoulCraft.Crafting.Recipe.CursedRecipe;
import SoundLogic.SoulCraft.Crafting.Recipe.ShapelessCursedRecipe;

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

public class CursedCrafting {
	public static IRecipe addShapelessRecipe(ItemStack output,Object[] normalItems,Object[] hauntedItems,Object[] staticItems,Object[] hauntItems)
	{
		ShapelessCursedRecipe recipe;
		List<Boolean> acceptHauntedItems=new ArrayList();
		List<Integer> increaseHauntLevel=new ArrayList();
		List<Boolean> absorbHaunt=new ArrayList();
		List<Boolean> leaveBehind=new ArrayList();
		List<ItemStack> itemStacks=new ArrayList();
		for(int i=0;i<normalItems.length;i++)
		{
			ItemStack stack=null;
			Object curObj=normalItems[i];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(false);
			increaseHauntLevel.add(0);
			absorbHaunt.add(false);
			leaveBehind.add(false);
			itemStacks.add(stack);
		}
		for(int i=0;i<hauntedItems.length;i++)
		{
			ItemStack stack=null;
			Object curObj=hauntedItems[i];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(true);
			increaseHauntLevel.add(0);
			absorbHaunt.add(true);
			leaveBehind.add(false);
			itemStacks.add(stack);
		}
		for(int i=0;i<staticItems.length;i++)
		{
			ItemStack stack=null;
			Object curObj=staticItems[i];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(false);
			increaseHauntLevel.add(0);
			absorbHaunt.add(false);
			leaveBehind.add(true);
			itemStacks.add(stack);
		}
		for(int i=0;i<hauntItems.length;i+=2)
		{
			ItemStack stack=null;
			Object curObj=hauntItems[i];
			int level=(Integer)hauntItems[i+1];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(true);
			increaseHauntLevel.add(level);
			absorbHaunt.add(false);
			leaveBehind.add(false);
			itemStacks.add(stack);
		}
//		List<Boolean> acceptHauntedItems=new ArrayList();
//		List<Integer> increaseHauntLevel=new ArrayList();
//		List<Boolean> absorbHaunt=new ArrayList();
//		List<Boolean> leaveBehind=new ArrayList();
//		List<ItemStack> itemStacks=new ArrayList();
		recipe=new ShapelessCursedRecipe(output, itemStacks, acceptHauntedItems, increaseHauntLevel, absorbHaunt, leaveBehind);
		CraftingManager.getInstance().getRecipeList().add(recipe);
		return recipe;
	}
    public static IRecipe buildSimpleShapeless(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).copy());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        return new ShapelessRecipes(par1ItemStack, var3);
    }
	public static IRecipe addShapelessRecipePureOutput(ItemStack output,Object[] normalItems,Object[] hauntedItems,Object[] staticItems,Object[] hauntItems)
	{
		ShapelessCursedRecipe recipe;
		List<Boolean> acceptHauntedItems=new ArrayList();
		List<Integer> increaseHauntLevel=new ArrayList();
		List<Boolean> absorbHaunt=new ArrayList();
		List<Boolean> leaveBehind=new ArrayList();
		List<ItemStack> itemStacks=new ArrayList();
		for(int i=0;i<normalItems.length;i++)
		{
			ItemStack stack=null;
			Object curObj=normalItems[i];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(false);
			increaseHauntLevel.add(0);
			absorbHaunt.add(false);
			leaveBehind.add(false);
			itemStacks.add(stack);
		}
		for(int i=0;i<hauntedItems.length;i++)
		{
			ItemStack stack=null;
			Object curObj=hauntedItems[i];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(true);
			increaseHauntLevel.add(0);
			absorbHaunt.add(false);
			leaveBehind.add(false);
			itemStacks.add(stack);
		}
		for(int i=0;i<staticItems.length;i++)
		{
			ItemStack stack=null;
			Object curObj=staticItems[i];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(false);
			increaseHauntLevel.add(0);
			absorbHaunt.add(false);
			leaveBehind.add(true);
			itemStacks.add(stack);
		}
		for(int i=0;i<hauntItems.length;i+=2)
		{
			ItemStack stack=null;
			Object curObj=hauntItems[i];
			int level=(Integer)hauntItems[i+1];
			if(curObj instanceof Item)
				stack=new ItemStack((Item)curObj);
			if(curObj instanceof Block)
				stack=new ItemStack((Block)curObj);
			if(curObj instanceof ItemStack)
				stack=(ItemStack)curObj;
			acceptHauntedItems.add(true);
			increaseHauntLevel.add(level);
			absorbHaunt.add(false);
			leaveBehind.add(false);
			itemStacks.add(stack);
		}
//		List<Boolean> acceptHauntedItems=new ArrayList();
//		List<Integer> increaseHauntLevel=new ArrayList();
//		List<Boolean> absorbHaunt=new ArrayList();
//		List<Boolean> leaveBehind=new ArrayList();
//		List<ItemStack> itemStacks=new ArrayList();
		recipe=new ShapelessCursedRecipe(output, itemStacks, acceptHauntedItems, increaseHauntLevel, absorbHaunt, leaveBehind);
		CraftingManager.getInstance().getRecipeList().add(recipe);
		return recipe;
	}
}
