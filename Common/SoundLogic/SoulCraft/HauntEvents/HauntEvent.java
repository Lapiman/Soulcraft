package SoundLogic.SoulCraft.HauntEvents;

import java.util.Map;

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

public abstract class HauntEvent {
	public HauntEvent(){}
	public static boolean validForBlock(World world, int x,int y,int z){return false;}
	public static boolean validForPlayer(EntityPlayer player){return false;}
	public static boolean validForItem(Item item){return false;}
	public static boolean isAbstractEvent(){return true;}
	public static boolean isDefensive(){return false;}
	public abstract void StartEvent();
	public abstract void ProgressEvent();
	public abstract void EndEvent();
	public abstract void sourceRemoved();
	public abstract int getDurationRemaining();
	public abstract Map<String,String> writeToMap();
	public abstract void loadFromMap(Map<String,String> map);
}
