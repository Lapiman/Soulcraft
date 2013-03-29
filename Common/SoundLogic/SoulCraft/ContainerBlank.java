package SoundLogic.SoulCraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;


public class ContainerBlank extends Container
{
    public ContainerBlank()
    {
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return false;
    }
}
