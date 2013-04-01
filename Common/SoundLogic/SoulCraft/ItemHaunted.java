package SoundLogic.SoulCraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureStitched;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class ItemHaunted extends Item{
	public static ItemHaunted instance;
	public static TextureStitched Base;
	public static Map<TextureStitched,TextureHaunted> icons=new HashMap<TextureStitched,TextureHaunted>();
	TextureMap textureMap;
	protected ItemHaunted(int par1) {
		super(par1);
		SoulMod.proxy.RegisterAsHaunted(this);
        instance=this;
	}
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(!world.isRemote) return stack;
		for(TextureHaunted icon : icons.values())
		{
			icon.updateAnimation();
		}
		return stack;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack stack, int renderpass)
	{
		TextureStitched original=(TextureStitched) this.getBaseItemStack(stack).getIconIndex();
		if(icons.containsKey(original))
			return icons.get(original);
		else
		{
			TextureHaunted icon=new TextureHaunted(original);
			icons.put(original, icon);
			this.textureMap.setTextureEntry("HAUNTED ICON FOR "+original.getIconName(), icon);
			return icon;
		}
	}
	public ItemStack hauntStack(ItemStack stack,int level)
	{
		if(level==0)
			return stack;
		ItemStack result=stack.copy();
		if(stack.getItem() instanceof ItemHaunted)
			result.setItemDamage(stack.getItemDamage()+level);
		else
			result=hauntStack(getHauntedStack(stack),level);
		return result;
	}
	public ItemStack getBaseItemStack(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemHaunted)
		{
			int id=stack.stackTagCompound.getInteger("OriginalStackID");
			int damage=stack.stackTagCompound.getInteger("OriginalStackDamage");
			ItemStack original=new ItemStack(Item.itemsList[id]);
			original.setItemDamage(damage);
			if(stack.stackTagCompound.hasKey("OriginalStackNBT"))
			{
				NBTTagCompound compound=stack.stackTagCompound.getCompoundTag("OriginalStackNBT");
				original.stackTagCompound=compound;
			}
			return original;
		}
		return stack;
	}
    @Override
	public String getItemDisplayName(ItemStack stack)
	{
		return "Haunted "+this.getBaseItemStack(stack).getDisplayName()+" : Level "+stack.getItemDamage();
	}
	public ItemStack getCursedContainerItemStack(ItemStack itemStack)
	{
		ItemStack stack=this.getBaseItemStack(itemStack).getItem().getContainerItemStack(this.getBaseItemStack(itemStack));
		if(stack==null)
			return stack;
		return this.hauntStack(stack, itemStack.getItemDamage());
	}
	private ItemStack getHauntedStack(ItemStack stack)
	{
		ItemStack haunted=new ItemStack(this);
		haunted.stackTagCompound=new NBTTagCompound();
		haunted.stackTagCompound.setInteger("OriginalStackDamage",stack.getItemDamage());
		haunted.stackTagCompound.setInteger("OriginalStackID",stack.itemID);
		NBTTagCompound oldCompound=stack.getTagCompound();
		if(oldCompound!=null)
			haunted.stackTagCompound.setCompoundTag("OriginalStackNBT",oldCompound);
		return haunted;
	}
    @Override
	@SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
    	this.textureMap=(TextureMap)par1IconRegister;
    	this.Base = (TextureStitched) par1IconRegister.registerIcon("BLANKTEX");
    }
}
