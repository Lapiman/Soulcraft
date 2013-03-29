package SoundLogic.SoulCraft;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.TickRegistry;

import SoundLogic.SoulCraft.HauntEvents.ClientSideEvents;
import SoundLogic.SoulCraft.HauntEvents.ClientSideEvents.ClientSideEvent;
import SoundLogic.SoulCraft.HauntEvents.Generators.EventGeneratorTest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{
	ArrayList<ClientSideEvents.ClientSideEvent> events=new ArrayList();
	public void EventPasser(EntityPlayer thePlayer, int ID,
			int[] data) {
		if(thePlayer instanceof EntityClientPlayerMP)
			ClientSideEvents.Event(thePlayer, thePlayer.worldObj, ID, data);
		else
			ClientSideEvents.Event(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.worldObj, ID, data);
	}
	public void onTickClient()
	{
		for(ClientSideEvent event : events)
		{
			event.tick();
		}
	}
	public void renderClientEvents(float partialTick) {
		Entity entity=Minecraft.getMinecraft().thePlayer;
		float frame=partialTick;
		double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)frame;
		double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)frame;
		double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)frame;
		GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
		for(ClientSideEvent event : events)
		{
			event.render(partialTick);
		}
		GL11.glTranslated(interpPosX, interpPosY, interpPosZ);
	}
	public void Init(SoulMod soulMod)
	{
		super.Init(soulMod);
		ClientSideEvents.init();
		ClientEventHandler handle=new ClientEventHandler();
		MinecraftForge.EVENT_BUS.register(handle);
		TickRegistry.registerTickHandler(handle, Side.CLIENT);
	}
	public void registerClientEvent(ClientSideEvents.ClientSideEvent event)
	{
		this.events.add(event);
	}
}
