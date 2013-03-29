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
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.*;

public abstract class CursedRecipe implements IRecipe
{
	public abstract boolean[][] acceptHauntedArray(InventoryCrafting craftMatrix, EntityPlayer player);
	public abstract boolean[][] absorbHauntedArray(InventoryCrafting craftMatrix, EntityPlayer player);
	public abstract boolean[][] leaveBehindArray(InventoryCrafting craftMatrix, EntityPlayer player);
	public abstract int[][] increaseHauntedArray(InventoryCrafting craftMatrix, EntityPlayer player);
}
