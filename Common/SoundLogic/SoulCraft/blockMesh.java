package SoundLogic.SoulCraft;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;

public class blockMesh extends Block
{
	//0=blaze
	//1=cooled
	//2=chilled
    public blockMesh(int par1)
    {
        super(par1, Material.fire);
        this.setHardness(2.0F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        float var3 = 0.5F;
        float var4 = 0.125F/2;
        this.setBlockBounds(0.5F - var3, 0.5F, 0.5F - var3, 0.5F + var3, 0.5F+var4, 0.5F + var3);
        this.needsRandomTick=true;
//        this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4)
    }
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	return true;
    }
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving ent)
    {
    	this.onNeighborBlockChange(world, x, y, z, this.blockID);
    }
	public int damageDropped(int par1)
	{
		return par1;
	}
    public void blocksTemp(World world, int x, int y, int z)
    {
    	int metadata=world.getBlockMetadata(x, y, z);
    	int[] dx=new int[]{-1, 0, 0, 1, 0, 0};
    	int[] dy=new int[]{ 0, 1,-1, 0, 0, 0};
    	int[] dz=new int[]{ 0, 0, 0, 0, 1,-1};
    	for(int i=0;i<6;i++)
    	{
    		int nx=x+dx[i];
    		int ny=y+dy[i];
    		int nz=z+dz[i];
    		if(world.getBlockId(nx, ny, nz)==Block.waterStill.blockID)
    		{
    			cool(world,x,y,z);
    			world.setBlock(nx, ny, nz, 0);
    		}
    		if(world.getBlockId(nx, ny, nz)==Block.waterMoving.blockID)
    		{
    			cool(world,x,y,z);
    			world.setBlock(nx, ny, nz, 0);
    		}
    		if(world.getBlockId(nx, ny, nz)==Block.ice.blockID)
    		{
    			if(metadata==0)
    			{
        			cool(world,x,y,z);
        			world.setBlock(nx, ny, nz, Block.waterStill.blockID);
    			}
    			if(metadata==1)
    			{
        			chill(world,x,y,z);
    			}
    		}
    		if(world.getBlockId(nx, ny, nz)==Block.blockSnow.blockID)
    		{
    			if(metadata==0)
    			{
        			cool(world,x,y,z);
        			world.setBlock(nx, ny, nz, 0);
    			}
    			if(metadata==1)
    			{
        			chill(world,x,y,z);
    			}
    		}
    		if(world.getBlockId(nx, ny, nz)==Block.lavaStill.blockID)
    		{
    			if(metadata==1)
    			{
        			heat(world,x,y,z);
    			}
    			if(metadata==2)
    			{
        			heat(world,x,y,z);
        			world.setBlock(nx, ny, nz, Block.obsidian.blockID);
    			}
    		}
    		if(world.getBlockId(nx, ny, nz)==Block.lavaMoving.blockID)
    		{
    			if(metadata==1)
    			{
        			heat(world,x,y,z);
    			}
    			if(metadata==2)
    			{
        			heat(world,x,y,z);
        			world.setBlock(nx, ny, nz, Block.cobblestone.blockID);
    			}
    		}
    		if(world.getBlockId(nx, ny, nz)==Block.fire.blockID)
    		{
        		warm(world,x,y,z);
    		}
    	}
    }
    public void chill(World world, int x, int y, int z)
    {
    	if(world.getBlockId(x, y, z)==blockID)
    	{
    		if(world.getBlockMetadata(x,y,z)==0)
    			cool(world,x,y,z);
    		if(world.getBlockMetadata(x,y,z)==1)
    			world.setBlockMetadataWithNotify(x, y, z, blockID, 2);
    	}
    }
    public void warm(World world, int x, int y, int z)
    {
    	if(world.getBlockId(x, y, z)==blockID)
    	{
    		if(world.getBlockMetadata(x,y,z)==2)
    			world.setBlockMetadataWithNotify(x, y, z, blockID, 1);
    	}
    }
    public void heat(World world, int x, int y, int z)
    {
    	if(world.getBlockId(x, y, z)==blockID)
    	{
    		if(world.getBlockMetadata(x,y,z)==2)
    			warm(world,x,y,z);
    		if(world.getBlockMetadata(x,y,z)==1)
    			world.setBlockMetadataWithNotify(x, y, z, blockID, 0);
    	}
    }
    public void cool(World world, int x, int y, int z)
    {
    	if(world.getBlockId(x, y, z)==blockID)
    	{
    		if(world.getBlockMetadata(x,y,z)==0)
    			world.setBlockMetadataWithNotify(x, y, z, blockID, 1);
    	}
    }
    public void updateTick(World world,int x, int y, int z, Random random)
    {
        if (world.isRemote)
        {
            return;
        }
    	BiomeGenBase biome=world.getBiomeGenForCoords(x, z);
    	if(biome.biomeID==biome.hell.biomeID)
    	{
    		if(random.nextDouble()<1)
    			warm(world,x,y,z);
    		if(random.nextDouble()<.6)
    			heat(world,x,y,z);
    	}
    	else if(biome.getFloatTemperature()<=.06)
    	{
    		if(random.nextDouble()<.5)
    			cool(world,x,y,z);
    		if(random.nextDouble()<.2)
    			chill(world,x,y,z);
    	}
    	else if(biome.getFloatTemperature()<=.6)
    	{
    		if(random.nextDouble()<.2)
    			cool(world,x,y,z);
    	}
    	else if(biome.getFloatTemperature()<=1)
    	{
    		if(random.nextDouble()<.2)
    			cool(world,x,y,z);
    		if(random.nextDouble()<.2)
    			warm(world,x,y,z);
    	}
    	else if(biome.getFloatTemperature()<=1.5)
    	{
    		if(random.nextDouble()<.1)
    			cool(world,x,y,z);
    		if(random.nextDouble()<.3)
    			warm(world,x,y,z);
    	}
    	else if(biome.getFloatTemperature()<=2)
    	{
    		if(random.nextDouble()<.3)
    			warm(world,x,y,z);
    		if(random.nextDouble()<.05)
    			heat(world,x,y,z);
    	}
    }
    public void onFallenUpon(World world, int x, int y, int z,Entity ent, float fall)
    {
    	if(!canBeAt(world,x,y,z,fall))
    	{
    		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    		world.setBlock(x, y, z, 0);
    	}
    }
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
    	blocksTemp(world,x,y,z);
    	if(!canBeAt(world,x,y,z,0))
    	{
    		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    		world.setBlock(x, y, z, 0);
    	}
    }
    public boolean canBeAt(World world,int x, int y, int z, float force)
    {
    	int[] dx=new int[]{-1, 0, 0, 1};
    	int[] dz=new int[]{ 0, 1,-1, 0};
    	int count=0;
    	for(int i=0;i<4;i++)
    	{
    		int nx,ny,nz;
    		nx=x+dx[i];
    		nz=z+dz[i];
    		if(world.isBlockNormalCube(nx,y,nz))
    			count++;
    		if(world.getBlockId(nx, y, nz)==this.blockID)
    			count++;
    	}
    	if(force>1F)
    	{
    		return count>2;
    	}
    	else
    	{
    		return count>1;
    	}
    }
    public boolean canPlaceBlockAt(World world, int x, int y,int z)
    {
    	if(super.canPlaceBlockAt(world,x,y,z))
    		return canBeAt(world,x,y,z,0F);
    	return false;
    }
    public boolean isOpaqueCube()
    {
        return false;
    }
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	if(par5Entity instanceof EntityItem)
    		par5Entity.setPosition(par5Entity.posX, par5Entity.posY - .1D, par5Entity.posZ);
    	if(par5Entity instanceof EntityLiving && par1World.getBlockMetadata(par2,par3,par4)==0)
    		par5Entity.attackEntityFrom(DamageSource.onFire, 1);
    }
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 3; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
