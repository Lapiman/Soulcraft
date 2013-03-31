package SoundLogic.SoulCraft;

import SoundLogic.SoulCraft.HauntEvents.Fire.TileEntitySpecialFire;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.ForgeDirection;
import static net.minecraftforge.common.ForgeDirection.*;

public class BlockFakeFire extends BlockFire implements ITileEntityProvider
{
	boolean damage;
    /** The chance this block will encourage nearby blocks to catch on fire */
	Icon[] iconArray=new Icon[2];
    private int[] chanceToEncourageFire = new int[256];

    /**
     * This is an array indexed by block ID the larger the number in the array the more likely a block type will catch
     * fires
     */
    private int[] abilityToCatchFire = new int[256];
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
    	if(damage==true)
    		this.iconArray = new Icon[] {par1IconRegister.registerIcon(SoulMod.modid+":fire_0"), par1IconRegister.registerIcon(SoulMod.modid+":fire_1")};
    	else
    		this.iconArray = new Icon[] {par1IconRegister.registerIcon("fire_0"), par1IconRegister.registerIcon("fire_1")};
    }
    @SideOnly(Side.CLIENT)
    @Override
    public Icon func_94438_c(int par1)
    {
        return this.iconArray[par1];
    }
    private static void setFireTileData(World world, int x, int y, int z,int sourcex,int sourcey,int sourcez) {
    	world.setBlockTileEntity(x, y, z, new TileEntitySpecialFire());
		TileEntity tile1=world.getBlockTileEntity(x, y, z);
		TileEntity tile2=world.getBlockTileEntity(sourcex, sourcey, sourcez);
		if(tile1==null){return;}
		if(tile2==null){return;}
		if(!(tile1 instanceof TileEntitySpecialFire)){return;}
		if(!(tile2 instanceof TileEntitySpecialFire)){return;}
		((TileEntitySpecialFire) tile1).setID(((TileEntitySpecialFire) tile2).TrackID);
	}
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
    {
        return this.iconArray[0];
    }

    @Override
    public boolean isBlockBurning(World world,int x,int y,int z)
    {
    	return damage;
    }
    public BlockFakeFire(int par1,boolean damage)
    {
        super(par1);
        this.setTickRandomly(true);
        this.damage=damage;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * Sets the burn rate for a block. The larger abilityToCatchFire the more easily it will catch. The larger
     * chanceToEncourageFire the faster it will burn and spread to other blocks. Args: blockID, chanceToEncourageFire,
     * abilityToCatchFire
     */
    private void setBurnRate(int par1, int par2, int par3)
    {
        Block.setBurnProperties(par1, par2, par3);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 3;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    /**
     * How many world ticks before ticking
     */
    @Override
    public int tickRate(World par1World)
    {
        return 30;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            Block base = Block.blocksList[par1World.getBlockId(par2, par3 - 1, par4)];
            boolean flag = (base != null && base.isFireSource(par1World, par2, par3 - 1, par4, par1World.getBlockMetadata(par2, par3 - 1, par4), UP));

            if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
            {
            	if(par1World.getBlockId(par2, par3, par4)==this.blockID)
            		par1World.setBlockToAir(par2, par3, par4);
            }

            if (!flag && par1World.isRaining() && (par1World.canLightningStrikeAt(par2, par3, par4) || par1World.canLightningStrikeAt(par2 - 1, par3, par4) || par1World.canLightningStrikeAt(par2 + 1, par3, par4) || par1World.canLightningStrikeAt(par2, par3, par4 - 1) || par1World.canLightningStrikeAt(par2, par3, par4 + 1)))
            {
            	if(par1World.getBlockId(par2, par3, par4)==this.blockID)
            		par1World.setBlockToAir(par2, par3, par4);
            }
            else
            {
                int l = par1World.getBlockMetadata(par2, par3, par4);

                if (l < 15)
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, l + par5Random.nextInt(3) / 2, 4);
                }

                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World) + par5Random.nextInt(10));

                if (!flag && !this.canNeighborBurn(par1World, par2, par3, par4))
                {
                    if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || l > 3)
                    {
                    	if(par1World.getBlockId(par2, par3, par4)==this.blockID)
                    		par1World.setBlockToAir(par2, par3, par4);
                    }
                }
                else if (!flag && !this.canBlockCatchFire(par1World, par2, par3 - 1, par4, UP) && l == 15 && par5Random.nextInt(4) == 0)
                {
                	if(par1World.getBlockId(par2, par3, par4)==this.blockID)
                		par1World.setBlockToAir(par2, par3, par4);
                }
                else
                {
                    boolean flag1 = par1World.isBlockHighHumidity(par2, par3, par4);
                    byte b0 = 0;

                    if (flag1)
                    {
                        b0 = -50;
                    }

                    this.tryToCatchBlockOnFire(par1World, par2 + 1, par3, par4, 300 + b0, par5Random, l, WEST );
                    this.tryToCatchBlockOnFire(par1World, par2 - 1, par3, par4, 300 + b0, par5Random, l, EAST );
                    this.tryToCatchBlockOnFire(par1World, par2, par3 - 1, par4, 250 + b0, par5Random, l, UP   );
                    this.tryToCatchBlockOnFire(par1World, par2, par3 + 1, par4, 250 + b0, par5Random, l, DOWN );
                    this.tryToCatchBlockOnFire(par1World, par2, par3, par4 - 1, 300 + b0, par5Random, l, SOUTH);
                    this.tryToCatchBlockOnFire(par1World, par2, par3, par4 + 1, 300 + b0, par5Random, l, NORTH);

                    for (int i1 = par2 - 1; i1 <= par2 + 1; ++i1)
                    {
                        for (int j1 = par4 - 1; j1 <= par4 + 1; ++j1)
                        {
                            for (int k1 = par3 - 1; k1 <= par3 + 4; ++k1)
                            {
                                if (i1 != par2 || k1 != par3 || j1 != par4)
                                {
                                    int l1 = 100;

                                    if (k1 > par3 + 1)
                                    {
                                        l1 += (k1 - (par3 + 1)) * 100;
                                    }

                                    int i2 = this.getChanceOfNeighborsEncouragingFire(par1World, i1, k1, j1);

                                    if (i2 > 0)
                                    {
                                        int j2 = (i2 + 40 + par1World.difficultySetting * 7) / (l + 30);

                                        if (flag1)
                                        {
                                            j2 /= 2;
                                        }

                                        if (j2 > 0 && par5Random.nextInt(l1) <= j2 && (!par1World.isRaining() || !par1World.canLightningStrikeAt(i1, k1, j1)) && !par1World.canLightningStrikeAt(i1 - 1, k1, par4) && !par1World.canLightningStrikeAt(i1 + 1, k1, j1) && !par1World.canLightningStrikeAt(i1, k1, j1 - 1) && !par1World.canLightningStrikeAt(i1, k1, j1 + 1))
                                        {
                                            int k2 = l + par5Random.nextInt(5) / 4;

                                            if (k2 > 15)
                                            {
                                                k2 = 15;
                                            }
                                            if(par1World.getBlockId(i1, k1, j1)==0);
                                            {
                                            	par1World.setBlock(i1, k1, j1, this.blockID, k2, 3);
                                            	setFireTileData(par1World,i1,k1,j1,par2,par3,par4);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
	public boolean func_82506_l()
    {
        return false;
    }

    private void tryToCatchBlockOnFire(World par1World, int par2, int par3, int par4, int par5, Random par6Random, int par7, ForgeDirection face)
    {
        int var8 = 0;
        Block block = Block.blocksList[par1World.getBlockId(par2, par3, par4)];
        if (block != null)
        {
            var8 = block.getFlammability(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), face);
        }

        if (par6Random.nextInt(par5) < var8)
        {
            boolean var9 = par1World.getBlockId(par2, par3, par4) == Block.tnt.blockID;

            if (par6Random.nextInt(par7 + 10) < 5 && !par1World.canLightningStrikeAt(par2, par3, par4))
            {
                int var10 = par7 + par6Random.nextInt(5) / 4;

                if (var10 > 15)
                {
                    var10 = 15;
                }
//                par1World.setBlockMetadataWithNotify(par2, par3, par4, this.blockID, var10);
            }
            else
            {
//                par1World.setBlock(par2, par3, par4, 0);
            }

            if (var9)
            {
                Block.tnt.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
            }
        }
    }

    /**
     * Returns true if at least one block next to this one can burn.
     */
    private boolean canNeighborBurn(World par1World, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1World, par2 + 1, par3, par4, WEST ) ||
               canBlockCatchFire(par1World, par2 - 1, par3, par4, EAST ) ||
               canBlockCatchFire(par1World, par2, par3 - 1, par4, UP   ) ||
               canBlockCatchFire(par1World, par2, par3 + 1, par4, DOWN ) ||
               canBlockCatchFire(par1World, par2, par3, par4 - 1, SOUTH) ||
               canBlockCatchFire(par1World, par2, par3, par4 + 1, NORTH);
    }

    /**
     * Gets the highest chance of a neighbor block encouraging this block to catch fire
     */
    private int getChanceOfNeighborsEncouragingFire(World par1World, int par2, int par3, int par4)
    {
        byte var5 = 0;

        if (!par1World.isAirBlock(par2, par3, par4))
        {
            return 0;
        }
        else
        {
            int var6 = this.getChanceToEncourageFire(par1World, par2 + 1, par3, par4, var5, WEST);
            var6 = this.getChanceToEncourageFire(par1World, par2 - 1, par3, par4, var6, EAST);
            var6 = this.getChanceToEncourageFire(par1World, par2, par3 - 1, par4, var6, UP);
            var6 = this.getChanceToEncourageFire(par1World, par2, par3 + 1, par4, var6, DOWN);
            var6 = this.getChanceToEncourageFire(par1World, par2, par3, par4 - 1, var6, SOUTH);
            var6 = this.getChanceToEncourageFire(par1World, par2, par3, par4 + 1, var6, NORTH);
            return var6;
        }
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable()
    {
        return false;
    }

    /**
     * Checks the specified block coordinate to see if it can catch fire.  Args: blockAccess, x, y, z
     * Deprecated for a side-sensitive version
     */
    @Deprecated
    public boolean canBlockCatchFire(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return canBlockCatchFire(par1IBlockAccess, par2, par3, par4, UP);
    }

    /**
     * Retrieves a specified block's chance to encourage their neighbors to burn and if the number is greater than the
     * current number passed in it will return its number instead of the passed in one.  Args: world, x, y, z,
     * curChanceToEncourageFire
     * Deprecated for a side-sensitive version
     */
    @Deprecated
    @Override
    public int getChanceToEncourageFire(World par1World, int par2, int par3, int par4, int par5)
    {
        return getChanceToEncourageFire(par1World, par2, par3, par4, par5, UP);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || this.canNeighborBurn(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !this.canNeighborBurn(par1World, par2, par3, par4))
        {
            par1World.setBlock(par2, par3, par4, 0);
        }
    }
    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(24) == 0)
        {
            par1World.playSound((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), "fire.fire", 1.0F + par5Random.nextFloat(), par5Random.nextFloat() * 0.7F + 0.3F,false);
        }

        int var6;
        float var7;
        float var8;
        float var9;

        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(par1World, par2, par3 - 1, par4, UP))
        {
            if (Block.fire.canBlockCatchFire(par1World, par2 - 1, par3, par4, EAST))
            {
                for (var6 = 0; var6 < 2; ++var6)
                {
                    var7 = (float)par2 + par5Random.nextFloat() * 0.1F;
                    var8 = (float)par3 + par5Random.nextFloat();
                    var9 = (float)par4 + par5Random.nextFloat();
                    par1World.spawnParticle("largesmoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2 + 1, par3, par4, WEST))
            {
                for (var6 = 0; var6 < 2; ++var6)
                {
                    var7 = (float)(par2 + 1) - par5Random.nextFloat() * 0.1F;
                    var8 = (float)par3 + par5Random.nextFloat();
                    var9 = (float)par4 + par5Random.nextFloat();
                    par1World.spawnParticle("largesmoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2, par3, par4 - 1, SOUTH))
            {
                for (var6 = 0; var6 < 2; ++var6)
                {
                    var7 = (float)par2 + par5Random.nextFloat();
                    var8 = (float)par3 + par5Random.nextFloat();
                    var9 = (float)par4 + par5Random.nextFloat() * 0.1F;
                    par1World.spawnParticle("largesmoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2, par3, par4 + 1, NORTH))
            {
                for (var6 = 0; var6 < 2; ++var6)
                {
                    var7 = (float)par2 + par5Random.nextFloat();
                    var8 = (float)par3 + par5Random.nextFloat();
                    var9 = (float)(par4 + 1) - par5Random.nextFloat() * 0.1F;
                    par1World.spawnParticle("largesmoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
                }
            }

            if (Block.fire.canBlockCatchFire(par1World, par2, par3 + 1, par4, DOWN))
            {
                for (var6 = 0; var6 < 2; ++var6)
                {
                    var7 = (float)par2 + par5Random.nextFloat();
                    var8 = (float)(par3 + 1) - par5Random.nextFloat() * 0.1F;
                    var9 = (float)par4 + par5Random.nextFloat();
                    par1World.spawnParticle("largesmoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        else
        {
            for (var6 = 0; var6 < 3; ++var6)
            {
                var7 = (float)par2 + par5Random.nextFloat();
                var8 = (float)par3 + par5Random.nextFloat() * 0.5F + 0.5F;
                var9 = (float)par4 + par5Random.nextFloat();
                par1World.spawnParticle("largesmoke", (double)var7, (double)var8, (double)var9, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    /**
     * Side sensitive version that calls the block function.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param face The side the fire is coming from
     * @return True if the face can catch fire.
     */
    @Override
    public boolean canBlockCatchFire(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            return block.isFlammable(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }
        return false;
    }

    /**
     * Side sensitive version that calls the block function.
     * 
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @param oldChance The previous maximum chance.
     * @param face The side the fire is coming from
     * @return The chance of the block catching fire, or oldChance if it is higher
     */
    @Override
    public int getChanceToEncourageFire(World world, int x, int y, int z, int oldChance, ForgeDirection face)
    {
        int newChance = 0;
        Block block = Block.blocksList[world.getBlockId(x, y, z)];
        if (block != null)
        {
            newChance = block.getFireSpreadSpeed(world, x, y, z, world.getBlockMetadata(x, y, z), face);
        }
        return (newChance > oldChance ? newChance : oldChance);
    }
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntitySpecialFire();
	}
	@Override
	public boolean hasTileEntity(int i)
	{
		return true;
	}
}
