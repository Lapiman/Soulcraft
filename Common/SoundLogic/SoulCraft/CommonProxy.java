package SoundLogic.SoulCraft;

import java.io.File;
import java.util.ArrayList;

import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import SoundLogic.Core.ItemSoundLogic;
import SoundLogic.SoulCraft.Crafting.CraftingHandlerSpecialCases;
import SoundLogic.SoulCraft.Crafting.CursedCrafting;
import SoundLogic.SoulCraft.Crafting.CursedCraftingHandler;
import SoundLogic.SoulCraft.Crafting.Recipe.BaseRecipeWrapper;
import SoundLogic.SoulCraft.HauntEvents.ClientSideEvents;
import SoundLogic.SoulCraft.HauntEvents.Fire.FireTracker;
import SoundLogic.SoulCraft.HauntEvents.Fire.TileEntitySpecialFire;
import SoundLogic.SoulCraft.HauntEvents.Generators.EventGeneratorTest;
import SoundLogic.SoulCraft.Haunting.Cause.BlockHauntCube;
import SoundLogic.SoulCraft.Haunting.Cause.TileEntitySimpleHaunt;
import SoundLogic.SoulCraft.Haunting.Cause.itemHauntCube;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public IRecipe soulMuckRecipe;
	public IRecipe BlazeMeshRecipe;
	private Configuration config;
    public Block CompressedSoul;
    public Block MuckFlowing;
    public Block MuckStill;
    public Block BlockMesh;
    public Block BlockCage;
    public Block BlockIceCage;
    public Item SoulWhisp;
    public Item muckBucket;
    public Item liquidSoul;
    public Item gelledSoul;
    public Item bottledGellSoul;
    public Item paperFunnel;
    public Item blazeFilter;
    public Item hauntedItem;
    public Item bottledTear;
    public Item soulVapor;
    public Block DeadTorch;
    public Block fakeFire;

    private int CompressedSoulID;
    private int MuckFlowingID;
    private int MuckStillID;
    private int SoulWhispID;
    private int muckBucketID;
    private int liquidSoulID;
    private int gelledSoulID;
    private int bottledGellSoulID;
    private int paperFunnelID;
    private int blazeFilterID;
    private int hauntedItemID;
    private int bottledTearID;
    private int soulVaporID;
    private int BlockMeshID;
    private int BlockCageID;
    private int BlockIceCageID;
	private int HauntCubeID;
	private int DeadTorchID;
	private int fakeFireID;
	public BlockHauntCube BlockHaunt;
	private int fakeFireDmgID;
	public Block fakeFireDmg;
	private int DebugItemID;
	public Item DebugItem;
	public void RegisterIcon(ItemHaunted item)
	{
		//client only
	}

    public void RegisterItems()
	{
	    DebugItem=new ItemBlockPrimer(DebugItemID);
	    DebugItem.setUnlocalizedName("soundlogic.debugitem");
	    DebugItem.setCreativeTab(CreativeTabs.tabMisc);
	    SoulWhisp=new ItemSoundLogic(SoulWhispID,SoulMod.modid);
	    SoulWhisp.setUnlocalizedName("soundlogic.soulWhisp");
	    SoulWhisp.setCreativeTab(CreativeTabs.tabMisc);
	    muckBucket=new ItemSoundLogic(muckBucketID,SoulMod.modid);
	    muckBucket.setUnlocalizedName("soundlogic.muckBucket");
	    muckBucket.setCreativeTab(CreativeTabs.tabMisc);
	    liquidSoul=new ItemSoundLogic(liquidSoulID,SoulMod.modid);
	    liquidSoul.setUnlocalizedName("soundlogic.liquidSoul");
	    liquidSoul.setCreativeTab(CreativeTabs.tabMisc);
	    gelledSoul=new ItemSoundLogic(gelledSoulID,SoulMod.modid);
	    gelledSoul.setUnlocalizedName("soundlogic.gelledSoul");
	    gelledSoul.setCreativeTab(CreativeTabs.tabMisc);
	    bottledGellSoul=new ItemSoundLogic(bottledGellSoulID,SoulMod.modid);
	    bottledGellSoul.setUnlocalizedName("soundlogic.bottledGellSoul");
	    bottledGellSoul.setCreativeTab(CreativeTabs.tabMisc);
	    paperFunnel=new ItemSoundLogic(paperFunnelID,SoulMod.modid);
	    paperFunnel.setUnlocalizedName("soundlogic.paperFunnel");
	    paperFunnel.setCreativeTab(CreativeTabs.tabMisc);
	    blazeFilter=new ItemSoundLogic(blazeFilterID,SoulMod.modid);
	    blazeFilter.setUnlocalizedName("soundlogic.blazeFilter");
	    blazeFilter.setCreativeTab(CreativeTabs.tabMisc);
	    hauntedItem=new ItemHaunted(hauntedItemID);
	    hauntedItem.setUnlocalizedName("soundlogic.haunteditembase");
	    bottledTear=new ItemSoundLogic(bottledTearID,SoulMod.modid);
	    bottledTear.setUnlocalizedName("soundlogic.bottledTear");
	    bottledTear.setCreativeTab(CreativeTabs.tabMisc);
	    soulVapor=new ItemSoundLogic(soulVaporID,SoulMod.modid);
	    soulVapor.setUnlocalizedName("soundlogic.soulVapor");
	    soulVapor.setCreativeTab(CreativeTabs.tabMisc);
        bottledGellSoul.setContainerItem(Item.glassBottle);
        liquidSoul.setContainerItem(Item.glassBottle);
        soulVapor.setContainerItem(Item.glassBottle);
        muckBucket.setContainerItem(Item.bucketEmpty);
        

	}
	public void AddRenderer()
	{
		//client only
	}
	public void PreloadTex()
	{
		//Client only;
	}
	public void RegisterBlocks()
	{
		fakeFire=new BlockFakeFire(fakeFireID,false).setHardness(0.0F).setLightValue(1.0F);
        fakeFire.setUnlocalizedName("soundlogic.fakeFire");
        GameRegistry.registerBlock(fakeFire);
		fakeFireDmg=new BlockFakeFire(fakeFireDmgID,true).setHardness(0.0F).setLightValue(1.0F);
        fakeFireDmg.setUnlocalizedName("soundlogic.fakeFireDmg");
        GameRegistry.registerBlock(fakeFireDmg);
        DeadTorch=new BlockDeadTorch(DeadTorchID);
        DeadTorch.setUnlocalizedName("soundlogic.deadTorch");
        GameRegistry.registerBlock(DeadTorch);
        CompressedSoul=new BlockCompSoulSand(CompressedSoulID).setHardness(1.5F);
        CompressedSoul.setUnlocalizedName("soundlogic.compressedSoul");
        GameRegistry.registerBlock(CompressedSoul);
/*        MuckFlowing=new BlockSoulMuckFlowing(MuckStillID,Material.water);
        MuckFlowing.setBlockName("soundlogic.MuckFlowing");
        MuckFlowing.blockIndexInTexture=16;
        MuckFlowing.setTextureFile(SoulMod.terrainImage);
        GameRegistry.registerBlock(MuckFlowing);
        MuckStill=new BlockSoulMuckStationary(MuckFlowingID,Material.water);
        MuckStill.setBlockName("soundlogic.MuckStill");
        MuckStill.blockIndexInTexture=16;
        MuckStill.setTextureFile(SoulMod.terrainImage);
        GameRegistry.registerBlock(MuckStill);*/
        BlockCage = new blockCage(BlockCageID);
        BlockCage.setUnlocalizedName("soundlogic.cage");
        GameRegistry.registerBlock(BlockCage,itemCage.class);
        BlockIceCage = new blockIceCage(BlockIceCageID);
        BlockIceCage.setUnlocalizedName("soundlogic.cage.ice");
        GameRegistry.registerBlock(BlockIceCage);
        BlockMesh = new blockMesh(BlockMeshID);
        BlockMesh.setUnlocalizedName("soundlogic.mesh");
        GameRegistry.registerBlock(BlockMesh,itemMesh.class);
        BlockHaunt = new BlockHauntCube(HauntCubeID);
        BlockHaunt.setUnlocalizedName("soundlogic.testblock");
        GameRegistry.registerBlock(BlockHaunt,itemHauntCube.class);
        GameRegistry.registerTileEntity(TileEntitySimpleHaunt.class, "SimpleHaunt");
        GameRegistry.registerTileEntity(TileEntitySpecialFire.class, "SpecialFire");
	}
	public void InitConfig()
	{
        File file = new File("config/soundlogicSoulCraft.cfg");
        config = new Configuration(file);
        config.load();
        config.save();
	}
	public void RegisterAsHaunted(Item item)
	{
		//Client only
	}
	public void EventPasserServer(EntityPlayer thePlayer, int ID,
			int[] data) {
		try
		{
			PacketHandler.sendEvent(thePlayer,ID,data);
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void RequestIDs()
	{
	    CompressedSoulID=config.getBlock("Compressed Soul Sand ID", 1).getInt();
	    BlockMeshID=config.getBlock("Mesh ID", 1).getInt();
	    BlockCageID=config.getBlock("Cage ID", 1).getInt();
	    BlockIceCageID=config.getBlock("Ice Cage ID", 1).getInt();
	    MuckFlowingID=config.getBlock("Muck Flowing ID", 1).getInt();
	    MuckStillID=config.getBlock("Muck Still ID", 1).getInt();
	    HauntCubeID=config.getBlock("Haunt Block ID", 1).getInt();
	    DeadTorchID=config.getBlock("Dead Torch ID", 1).getInt();
	    fakeFireID=config.getBlock("Fake Fire ID", 1).getInt();
	    fakeFireDmgID=config.getBlock("Fake Fire Dmg ID", 1).getInt();
	    SoulWhispID=config.getItem("Soul Whisp ID", 0).getInt();
	    muckBucketID=config.getItem("Soul Muck Bucket ID", 0).getInt();
	    liquidSoulID=config.getItem("Liquid Soul ID", 0).getInt();
	    gelledSoulID=config.getItem("Gelled Soul ID", 0).getInt();
	    bottledGellSoulID=config.getItem("Bottled Gelled Soul ID", 0).getInt();
	    paperFunnelID=config.getItem("Paper Funnel ID", 0).getInt();
	    blazeFilterID=config.getItem("Blaze Filter ID", 0).getInt();
	    hauntedItemID=config.getItem("Haunted Item ID", 0).getInt();
	    bottledTearID=config.getItem("Bottled Ghast Tear ID", 0).getInt();
	    soulVaporID=config.getItem("Soul Vapor ID", 0).getInt();
	    DebugItemID=config.getItem("Debug Item ID", 0).getInt();
	    config.save();
	}
	public void Init(SoulMod soulMod)
	{
		InitConfig();
		RequestIDs();
		AddRenderer();
		PreloadTex();
		RegisterBlocks();
		RegisterItems();
		InitRecipes();
		AddRenderer();
		InitLang();
		EventHandler handle=new EventHandler();
		MinecraftForge.EVENT_BUS.register(handle);
		TickRegistry.registerTickHandler(handle, Side.SERVER);
		ICraftingHandler craft=new CursedCraftingHandler();
		GameRegistry.registerCraftingHandler(craft);
		ICraftingHandler craft2=new CraftingHandlerSpecialCases();
		GameRegistry.registerCraftingHandler(craft2);
		FireTracker.init();
//		EntityRegistry.registerModEntity(EntityItemHaunted.class, "Haunted Item", 1, soulMod, 250, 5, true);
	}
	public void InitRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(blazeFilter), new Object[] {new ItemStack(BlockMesh,1,0),paperFunnel});
        GameRegistry.addRecipe(new ItemStack(this.BlockMesh,1,0), new Object[] {"BBB","BBB","BBB",'B',Item.blazeRod});
        GameRegistry.addRecipe(new ItemStack(this.BlockCage,1,0), new Object[] {"BBB","B B","BBB",'B',new ItemStack(BlockMesh,1,0)});
        GameRegistry.addRecipe(new ItemStack(this.BlockCage,1,1), new Object[] {"BBB","B B","BBB",'B',new ItemStack(BlockMesh,1,1)});
        GameRegistry.addRecipe(new ItemStack(this.BlockCage,1,2), new Object[] {"BBB","B B","BBB",'B',new ItemStack(BlockMesh,1,2)});
        GameRegistry.addRecipe(new ItemStack(this.CompressedSoul), new Object[] {"SSS","SSS","SSS",'S',Block.slowSand});
        GameRegistry.addShapelessRecipe(new ItemStack(BlockMesh,8,0), new Object[] {new ItemStack(BlockCage,1,0)});
        GameRegistry.addShapelessRecipe(new ItemStack(BlockMesh,8,1), new Object[] {new ItemStack(BlockCage,1,1)});
        GameRegistry.addShapelessRecipe(new ItemStack(BlockMesh,8,2), new Object[] {new ItemStack(BlockCage,1,2)});
        GameRegistry.addShapelessRecipe(new ItemStack(Item.blazeRod,9), new Object[] {new ItemStack(BlockMesh,1,0)});
        GameRegistry.addShapelessRecipe(new ItemStack(Block.slowSand,9), new Object[] {CompressedSoul});

        GameRegistry.addRecipe(new ItemStack(paperFunnel), new Object[] {"P P"," P ",'P',Item.paper});
        GameRegistry.addRecipe(new BaseRecipeWrapper());
        
        CursedCrafting.addShapelessRecipe(new ItemStack(this.liquidSoul), new Object[]{}, new Object[]{Item.glassBottle}, new Object[]{}, new Object[]{this.muckBucket,1,this.blazeFilter,3});
        CursedCrafting.addShapelessRecipe(new ItemStack(this.bottledGellSoul), new Object[]{}, new Object[]{Item.glassBottle,gelledSoul}, new Object[]{}, new Object[]{});
        CursedCrafting.addShapelessRecipe(new ItemStack(Item.ghastTear), new Object[]{}, new Object[]{}, new Object[]{}, new Object[]{bottledTear,1});
        CursedCrafting.addShapelessRecipe(new ItemStack(gelledSoul), new Object[]{Item.slimeBall}, new Object[]{}, new Object[]{}, new Object[]{liquidSoul,1});
        CursedCrafting.addShapelessRecipe(new ItemStack(gelledSoul), new Object[]{}, new Object[]{}, new Object[]{}, new Object[]{bottledGellSoul,1});
        CursedCrafting.addShapelessRecipe(new ItemStack(bottledGellSoul), new Object[]{gelledSoul}, new Object[]{Item.glassBottle}, new Object[]{}, new Object[]{});
        GameRegistry.addShapelessRecipe(new ItemStack(Item.paper,3),new Object[] {paperFunnel});

        BlazeMeshRecipe=CursedCrafting.addShapelessRecipePureOutput(new ItemStack(BlockMesh,1,0),new Object[]{}, new Object[] {blazeFilter},new Object[]{},new Object[]{});
        soulMuckRecipe=CursedCrafting.buildSimpleShapeless(new ItemStack(muckBucket), new Object[] {Item.bucketWater,CompressedSoul});
        GameRegistry.addRecipe(soulMuckRecipe);

		GameRegistry.addSmelting(liquidSoul.itemID, new ItemStack(soulVapor),0);
		GameRegistry.addSmelting(bottledGellSoul.itemID, new ItemStack(bottledTear),0);
	}
	public void InitLang()
	{
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.0.name","Fake Creeper Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.1.name","Mead Spider Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.2.name","Fake Fire Safe Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.3.name","Fake Fire Unsafe Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.4.name","Torch Flicker Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.5.name","Footsteps Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.6.name","Snuff Torch Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.7.name","Spider Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.8.name","Spider Web Primer");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.primer.9.name","Shuffle Primer");

		LanguageRegistry.instance().addStringLocalization("item.soundlogic.soulWhisp.name","Soul Whisp");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.muckBucket.name","Bucket of Soul Muck");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.liquidSoul.name","Bottle of Liquid Soul");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.gelledSoul.name","Gelled Soul");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.bottledGellSoul.name","Gelled Soul in a Bottle");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.paperFunnel.name","Paper Funnel");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.blazeFilter.name","Blaze Filter");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.hauntedBlazeFilter.name","Haunted Blaze Filter");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.hauntedPaperFunnel.name","Haunted Paper Funnel");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.hauntedPaper.name","Haunted Paper");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.hauntedBottle.name","Haunted Bottle");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.hauntedBucket.name","Haunted Bucket");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.bottledTear.name","Bottled Tear");
		LanguageRegistry.instance().addStringLocalization("item.soundlogic.soulVapor.name","Bottle of Soul Vapor");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.compressedSoul.name","Compressed Soul Sand");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.mesh.0.name", "Blaze Mesh");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.mesh.1.name", "Cooled Blaze Mesh");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.mesh.2.name", "Frozen Blaze Mesh");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.cage.0.name", "Blaze Cage");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.cage.1.name", "Cooled Blaze Cage");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.cage.2.name", "Frozen Blaze Cage");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.cage.ice.name", "Ice Cage");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.muckStill.name", "Soul Muck Still");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.muckFlowing.name", "Soul Muck Flowing");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.fakeFire.name","Fake Fire");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.fakeFireDmg.name","Mostly Fake Fire");
		LanguageRegistry.instance().addStringLocalization("tile.soundlogic.hauntBlock.0.name","DEBUG Haunt Cube");
	}
	public void onTick()
	{
		
	}
	public void registerClientEvent(ClientSideEvents.ClientSideEvent event)
	{
		//client side only;
	}

	public void renderClientEvents(float partialTicks) {
		//Client side only
	}

	public void onTickClient() {
		//Client side only
	}
}
