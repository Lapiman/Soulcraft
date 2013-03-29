package SoundLogic.SoulCraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class blockIceCage extends Block
{
    public blockIceCage(int par1)
    {
        super(par1,Material.ice);
        this.setHardness(2.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
    	ArrayList<ItemStack> items=new ArrayList();
    	items.add(new ItemStack(Block.ice));
    	items.add(new ItemStack(SoulMod.proxy.BlockMesh,8,2));
    	return items;
    }
    public boolean isOpaqueCube()
    {
        return false;
    }
    
}
