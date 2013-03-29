package SoundLogic.Core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class ItemSoundLogic extends Item
{
	private boolean enchant;
    public ItemSoundLogic(int par1)
    {
        super(par1);
        enchant=false;
    }
    public ItemSoundLogic setEnchant(boolean enchant)
    {
    	this.enchant=enchant;
    	return this;
    }
    public boolean hasEffect(ItemStack bla)
    {
    	return enchant;
    }
}
