package SoundLogic.SoulCraft;

import java.util.ArrayList;
import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class ClientEventHandler implements ITickHandler{
	@ForgeSubscribe
	public void renderClientEvents(RenderWorldLastEvent event)
	{
		if(!event.context.theWorld.isRemote)
			return;
		SoulMod.proxy.renderClientEvents(event.partialTicks);
	}
/*	@ForgeSubscribe
	public void renderFields(RenderWorldLastEvent event)
	{
		if(!event.context.theWorld.isRemote)
			return;
		Entity entity=Minecraft.getMinecraft().thePlayer;
		float frame=event.partialTicks;
		double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)frame;
		double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)frame;
		double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)frame;
		GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
		GL11.glPointSize(6);
		GL11.glColor4f(1, 0, 0, 1);
		GL11.glBegin(GL11.GL_POINTS);
		ArrayList<Haunting> haunts=new ArrayList(HauntHandler.hauntings);
		for(Haunting haunt : haunts)
		{
			if(haunt instanceof HauntingLocation)
			{
				LocationField loc=((HauntingLocation) haunt).location;
				for(float n=-1;n<1;n+=1.0F/24)
				{
					for(float m=-1;m<1;m+=1.0F/24)
					{
						float[] point=loc.getPointOnSurface(n, m);
						GL11.glVertex3f(point[0], point[1], point[2]);
					}
				}
			}
		}
		GL11.glEnd();
		GL11.glTranslated(interpPosX, interpPosY, interpPosZ);
	}*/
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		SoulMod.proxy.onTickClient();
		
	}
	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.CLIENT);
	}
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Soulcraft Client Ticker";
	}
}
