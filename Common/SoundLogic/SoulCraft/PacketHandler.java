package SoundLogic.SoulCraft;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// TODO Auto-generated method stub
		if(packet.channel.equals("SoulCraftEvents"))
		{
			try {
				handleEventPacket(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@SideOnly(Side.CLIENT)
	public static void handleEventPacket(Packet250CustomPayload packet) throws IOException
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		int[] data=new int[packet.length-1];
		int ID=-1;
		try
		{
			int i=0;
			ID=inputStream.read();
			while(i<data.length)
			{
				data[i]=inputStream.read();
				i++;
			}
		}
		catch (EOFException e){}
		((ClientProxy)SoulMod.proxy).EventPasser(Minecraft.getMinecraft().thePlayer,ID,data);
	}

	public static void sendEvent(EntityPlayer player,int ID, int[] data) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(4+data.length*4);
		DataOutputStream outputStream = new DataOutputStream(bos);
		outputStream.write(ID);
		for(int i=0;i<data.length;i++)
		{
			outputStream.write(data[i]);
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "SoulCraftEvents";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
	}

}
