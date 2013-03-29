package SoundLogic.SoulCraft.Haunting.Cause;

import SoundLogic.SoulCraft.HauntHandler;
import SoundLogic.SoulCraft.HauntEvents.Generators.EventGeneratorTest;
import SoundLogic.SoulCraft.Haunting.Haunting;
import SoundLogic.SoulCraft.Haunting.HauntingBlockLocation;
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

public class BlockHauntCube extends BlockContainer{

	public BlockHauntCube(int par1) {
		super(par1, Material.glass);
        this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntitySimpleHaunt();
	}
	@Override
	public boolean onBlockActivated(World world, int x,int y, int z, EntityPlayer player, int metadata, float smallX,float smallY,float smallZ)
	{
		if(world.isRemote)
			return true;
		Haunting haunt=new HauntingBlockLocation(x,y,z,world,player);
		haunt.generator=new EventGeneratorTest();
		HauntHandler.AddHaunt(haunt);
		return true;
	}
}
