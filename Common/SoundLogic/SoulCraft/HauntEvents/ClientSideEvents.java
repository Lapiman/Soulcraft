package SoundLogic.SoulCraft.HauntEvents;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

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
import SoundLogic.SoulCraft.SoulMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraftforge.client.MinecraftForgeClient;

@SideOnly(Side.CLIENT)
public class ClientSideEvents {
	private static FakeCreeper fakeCreeper;
	public static void init()
	{
		fakeCreeper=new FakeCreeper();
		SoulMod.proxy.registerClientEvent(fakeCreeper);
	}
	public static void Event(EntityPlayer player, World world, int ID, int[] data)
	{
		switch(ID)
		{
		case PacketIDs.Creeper: fakeCreeper.HandlePacket(player, world, data);
		}
	}
	public abstract static class ClientSideEvent
	{
		public abstract void tick();

		public abstract void render(float partialTick);
	}
	private static class FakeCreeper extends ClientSideEvent{
		
		private double x;
		private double y;
		private double z;
		private int pulse;
		private boolean active=false;
		private EntityPlayer player;
		private int fuseTime=50;
		public FakeCreeper() {
		}
		public void HandlePacket(EntityPlayer player,World world, int[] data)
		{
			if(data[0]==0)
			{
				this.player=Minecraft.getMinecraft().thePlayer;
				x=player.posX-this.player.getLookVec().xCoord;
				y=player.posY-1;
				z=player.posZ-this.player.getLookVec().zCoord;
				active=true;
				pulse=0;
			}
			if(data[0]==2)
				active=false;
		}
	    public float getCreeperFlashIntensity(float par1)
	    {
	        return (pulse+par1) / (float)(this.fuseTime - 2);
	    }
	    protected void updateCreeperScale(float par2)
	    {
	        float var4 = this.getCreeperFlashIntensity(par2);
	        float var5 = 1.0F + MathHelper.sin(var4 * 100.0F) * var4 * 0.01F;

	        if (var4 < 0.0F)
	        {
	            var4 = 0.0F;
	        }

	        if (var4 > 1.0F)
	        {
	            var4 = 1.0F;
	        }

	        var4 *= var4;
	        var4 *= var4;
	        float var6 = (1.0F + var4 * 0.4F) * var5;
	        float var7 = (1.0F + var4 * 0.1F) / var5;
	        GL11.glScalef(var6, var7, var6);
	    }
		@Override
		public void tick() {
			if(!active){return;}
			if(pulse==0)
				player.worldObj.playSound(x,y,z, "random.fuse", 1.0F, 0.5F,false);
			pulse++;
			if(fuseTime-pulse>15 && checkForLooking())
			{
				pulse=fuseTime-15;
			}
			if(pulse>fuseTime)
			{
//				player.worldObj.createExplosion(null, x,y,z, 3.0F, false);
				player.worldObj.spawnParticle("hugeexplosion", x,y,z, 1.0D, 0.0D, 0.0D);
				player.worldObj.spawnParticle("hugeexplosion", x,y,z, .5D, 0.0D, 0.0D);
				player.worldObj.playSound(x,y,z, "random.explode", 6.0F, (1.0F + (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.2F) * 0.7F,false);
				this.active=false;
			}
		}
	    protected int updateCreeperColorMultiplier(float par2, float par3)
	    {
	        float var5 = this.getCreeperFlashIntensity(par3);

	        if ((int)(var5 * 10.0F) % 2 == 0)
	        {
	            return 0;
	        }
	        else
	        {
	            int var6 = (int)(var5 * 0.2F * 255.0F);

	            if (var6 < 0)
	            {
	                var6 = 0;
	            }

	            if (var6 > 255)
	            {
	                var6 = 255;
	            }

	            short var7 = 255;
	            short var8 = 255;
	            short var9 = 255;
	            return var6 << 24 | var7 << 16 | var8 << 8 | var9;
	        }
	    }
		private void flashCreeperColor(float partialTick)
		{
			int var18=updateCreeperColorMultiplier(player.worldObj.getLightBrightness((int)x,(int)y,(int)z),partialTick);
            float var19 = (float)(var18 >> 16 & 255) / 255.0F;
            float var20 = (float)(var18 >> 8 & 255) / 255.0F;
            float var29 = (float)(var18 & 255) / 255.0F;
            float var22 = (float)(var18 >> 24 & 255) / 255.0F;
            GL11.glColor4f(var19, var20, var29, var22);
//            if(!((var18 >> 24 & 255) > 0))
//            	GL11.glColor4f(1, 1, 1, 1);
		}
		private boolean checkForLooking()
		{
			EntityPlayer player=Minecraft.getMinecraft().thePlayer;
			double dx=player.posX-x;
			double dz=player.posZ-z;
			double ang=Math.toDegrees(Math.atan2(dz, dx));
			dx=player.getLookVec().xCoord;
			dz=player.getLookVec().zCoord;
			double angle=Math.abs((Math.abs(ang-Math.toDegrees(Math.atan2(dz, dx))))%360-180);
			return angle<15;
		}
		@Override
		public void render(float partialTick) {
			if(!active){return;}
			ModelCreeper creep=new ModelCreeper();
			ArrayList rendererList = (ArrayList) creep.boxList;
			int k = Minecraft.getMinecraft().renderEngine.getTexture("/mob/creeper.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, k);
			GL11.glTranslated(x, y+1.25, z);
			GL11.glRotatef(-180, 1, 0, 0);
			GL11.glDisable(GL11.GL_LIGHTING);
			EntityPlayer player=Minecraft.getMinecraft().thePlayer;
			double dx=player.posX-x;
			double dz=player.posZ-z;
			double ang=Math.toDegrees(Math.atan2(dz, dx));
			GL11.glRotatef((float)ang-90, 0, 1, 0);
	        this.updateCreeperScale(partialTick);
			for(int m1 = 0; m1 < rendererList.size(); m1++)
			{
			ModelRenderer modelrenderer = (ModelRenderer)rendererList.get(m1);
			modelrenderer.render(0.0625F);
			}
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDepthFunc(GL11.GL_EQUAL);
	        this.flashCreeperColor(partialTick);
			for(int m1 = 0; m1 < rendererList.size(); m1++)
			{
			ModelRenderer modelrenderer = (ModelRenderer)rendererList.get(m1);
			modelrenderer.render(0.0625F);
			}
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}
}
