package SoundLogic.Core;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class ItemSoundLogic extends Item
{
	private boolean enchant;
	public String modid;
    public ItemSoundLogic(int par1,String modid)
    {
        super(par1);
    	this.modid=modid;
        enchant=false;
    }
    public ItemSoundLogic setEnchant(boolean enchant)
    {
    	this.enchant=enchant;
    	return this;
    }
    @Override
    public boolean hasEffect(ItemStack bla)
    {
    	return enchant;
    }
    @Override
    public void updateIcons(IconRegister par1IconRegister)
    {
    	String reg=modid + ":" + this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1);
        this.iconIndex = par1IconRegister.registerIcon(modid + ":" + reg.substring(reg.indexOf(".") + 1));
    }
}
