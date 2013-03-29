/*package SoundLogic.SoulCraft;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.*;

@SideOnly(Side.CLIENT)
public class HauntedItemRenderer implements IItemRenderer {
	private static int dir=1;
	private static float speed=.01F;
	private static float alpha=0;
	static Random rand=new Random();
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type==type.INVENTORY || type==type.ENTITY || type==type.EQUIPPED;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		
		ItemHaunted haunted=(ItemHaunted)item.getItem();
        ItemStack originalStack=haunted.getBaseItemStack(item);
        if(originalStack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.blocksList[originalStack.itemID].getRenderType()))
        	return helper.ENTITY_ROTATION==helper || helper.INVENTORY_BLOCK==helper || helper.ENTITY_BOBBING==helper || helper.EQUIPPED_BLOCK==helper;
        else
        	return helper.ENTITY_BOBBING==helper;
	}
	public static void tick()
	{
		alpha+=speed*dir;
		if(alpha>.5)
		{
			alpha=.5F;
			dir=-1;
		}
		if(alpha<0)
		{
			alpha=0;
			dir=1;
			speed=rand.nextFloat()*.02F;
			while(speed==0)
			{
			speed=rand.nextFloat()*.02F;
			}
		}
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
		{
		Tessellator tes = Tessellator.instance;
		ItemHaunted haunted=(ItemHaunted)item.getItem();
        ItemStack originalStack=haunted.getBaseItemStack(item);
        ForgeHooksClient.bindTexture(originalStack.getItem().getTextureFile(), 0);
        int var9=originalStack.getIconIndex();
        RenderBlocks render=(RenderBlocks)data[0];
        int texX=var9 % 16 * 16;
        int texY=var9 / 16 * 16;
        int texX2=texX+16;
        int texY2=texY+16;
        float x1=texX/256F;
        float x2=texX2/256F;
        float y1=texY/256F;
        float y2=texY2/256F;
        if(originalStack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.blocksList[originalStack.itemID].getRenderType()))
        {
        	if(type==type.ENTITY)
        		GL11.glDisable(GL11.GL_LIGHTING);
        	if(type==type.ENTITY)
        		GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        	if(type==type.ENTITY)
                GL11.glScalef(.50F, .50F, 0.50F);
        	if(type==type.EQUIPPED)
        	{
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        		
        	}
        	render.renderBlockAsItem(Block.blocksList[((ItemBlock)originalStack.getItem()).getBlockID()], originalStack.getItemDamage(), 1.0F);
        	if(type==type.INVENTORY)
        	{
                GL11.glDepthFunc(GL11.GL_GREATER);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(false);
                ForgeHooksClient.bindTexture("%blur%/misc/glint.png",0);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                GL11.glColor4f(0.75F, 0F, 0F, 1.0F);

                GL11.glRotatef(-45F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-210F, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(1.0F, 1.0F, -1F);
                GL11.glScalef(.1F, .1F, 1F);
                
                
                GL11.glTranslatef(-9F, -9F, 0);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);

        	}
        	else if(type==type.ENTITY)
        	{
                GL11.glDepthFunc(GL11.GL_GREATER);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(false);
                ForgeHooksClient.bindTexture("%blur%/misc/glint.png",0);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                GL11.glColor4f(0.75F, 0F, 0F, 1.0F);

                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
        	}
        	else if(type==type.EQUIPPED)
        	{
                GL11.glDepthFunc(GL11.GL_GREATER);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(false);
                ForgeHooksClient.bindTexture("%blur%/misc/glint.png",0);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                GL11.glColor4f(0.75F, 0F, 0F, 1.0F);

                GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
                GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
                func_77018_b(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);

                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);

        	}
        	GL11.glDisable(GL11.GL_LIGHTING);
        }
        else
        {
        	if(type==type.ENTITY)
        		GL11.glDisable(GL11.GL_LIGHTING);
        	if(type==type.ENTITY)
        		GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        	draw(tes,x1,y1,x2,y2,type);
        	GL11.glDisable(GL11.GL_LIGHTING);
        }
		}
    private void func_77018_b(int par1, float par2, float par3, float par4, float par5,ItemRenderType type)
    {
        for (int var6 = 0; var6 < 2; ++var6)
        {
            if (var6 == 0)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            if (var6 == 1)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            float var7 = 0.00390625F;
            float var8 = 0.00390625F;
            float var9 = (float)(Minecraft.getSystemTime() % (long)(3000 + var6 * 1873)) / (3000.0F + (float)(var6 * 1873)) * 256.0F;
            float var10 = 0.0F;
            Tessellator var11 = Tessellator.instance;
            float var12 = 4.0F;

            if (var6 == 1)
            {
                var12 = -1.0F;
            }

            if(type==type.INVENTORY)
            {
            var11.startDrawingQuads();
            var11.addVertexWithUV((double)(par2 + 0), (double)(par3 + par5), -1, (double)((var9 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
            var11.addVertexWithUV((double)(par2 + par4), (double)(par3 + par5), -1, (double)((var9 + (float)par4 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
            var11.addVertexWithUV((double)(par2 + par4), (double)(par3 + 0), -1, (double)((var9 + (float)par4) * var7), (double)((var10 + 0.0F) * var8));
            var11.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), -1, (double)((var9 + 0.0F) * var7), (double)((var10 + 0.0F) * var8));
            var11.draw();
            }
            else if(type==type.ENTITY)
            {
                var11.startDrawingQuads();
                var11.addVertexWithUV((double)(par2 + 0-8)/16, (double)(par3 + 0-4)/16, 1.0f/2-1.0f/16, (double)((var9 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
                var11.addVertexWithUV((double)(par2 + par4-8)/16, (double)(par3 + 0-4)/16, 1.0f/2-1.0f/16, (double)((var9 + (float)par4 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
                var11.addVertexWithUV((double)(par2 + par4-8)/16, (double)(par3 + par5-4)/16, 1.0f/2-1.0f/16, (double)((var9 + (float)par4) * var7), (double)((var10 + 0.0F) * var8));
                var11.addVertexWithUV((double)(par2 + 0-8)/16, (double)(par3 + par5-4)/16, 1.0f/2-1.0f/16, (double)((var9 + 0.0F) * var7), (double)((var10 + 0.0F) * var8));
                var11.draw();
            }
            else if(type==type.EQUIPPED)
            {
                var11.startDrawingQuads();
                var11.addVertexWithUV((double)(par2 + 0-8)/16, (double)(par3 + par5-8)/16, -.5f+1/32f, (double)((var9 + (float)par4 * var12) * var7), (double)((var10 + (float)0) * var8));
                var11.addVertexWithUV((double)(par2 + par4-8)/16, (double)(par3 + par5-8)/16, -.5f+1/32f, (double)((var9 + (float)par5 + (float)par5 * var12) * var7), (double)((var10 + (float)0) * var8));
                var11.addVertexWithUV((double)(par2 + par4-8)/16, (double)(par3 + 0-8)/16, -.5f+1/32f, (double)((var9 + (float)0) * var7), (double)((var10 + (float)par5) * var8));
                var11.addVertexWithUV((double)(par2 + 0-8)/16, (double)(par3 + 0-8)/16, -.5f+1/32f, (double)((var9 + (float)par5) * var7), (double)((var10 + (float)par5) * var8));
                var11.draw();
            }
        }
    }
    private void func_77018_a(int par1, float par2, float par3, float par4, float par5,ItemRenderType type)
    {
        for (int var6 = 0; var6 < 2; ++var6)
        {
            if (var6 == 0)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            if (var6 == 1)
            {
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            }

            float var7 = 0.00390625F;
            float var8 = 0.00390625F;
            float var9 = (float)(Minecraft.getSystemTime() % (long)(3000 + var6 * 1873)) / (3000.0F + (float)(var6 * 1873)) * 256.0F;
            float var10 = 0.0F;
            Tessellator var11 = Tessellator.instance;
            float var12 = 4.0F;

            if (var6 == 1)
            {
                var12 = -1.0F;
            }

            if(type==type.INVENTORY)
            {
            var11.startDrawingQuads();
            var11.addVertexWithUV((double)(par2 + 0), (double)(par3 + par5), -50, (double)((var9 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
            var11.addVertexWithUV((double)(par2 + par4), (double)(par3 + par5), -50, (double)((var9 + (float)par4 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
            var11.addVertexWithUV((double)(par2 + par4), (double)(par3 + 0), -50, (double)((var9 + (float)par4) * var7), (double)((var10 + 0.0F) * var8));
            var11.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), -50, (double)((var9 + 0.0F) * var7), (double)((var10 + 0.0F) * var8));
            var11.draw();
            }
            else if(type==type.ENTITY)
            {
                var11.startDrawingQuads();
                var11.addVertexWithUV((double)(par2 + 0-8)/16, (double)(par3 + 0-4)/16, -1.0f/16, (double)((var9 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
                var11.addVertexWithUV((double)(par2 + par4-8)/16, (double)(par3 + 0-4)/16, -1.0f/16, (double)((var9 + (float)par4 + (float)par5 * var12) * var7), (double)((var10 + (float)par5) * var8));
                var11.addVertexWithUV((double)(par2 + par4-8)/16, (double)(par3 + par5-4)/16, -1.0f/16, (double)((var9 + (float)par4) * var7), (double)((var10 + 0.0F) * var8));
                var11.addVertexWithUV((double)(par2 + 0-8)/16, (double)(par3 + par5-4)/16, -1.0f/16, (double)((var9 + 0.0F) * var7), (double)((var10 + 0.0F) * var8));
                var11.draw();
            }
            else if(type==type.EQUIPPED)
            {
                var11.startDrawingQuads();
                var11.addVertexWithUV((double)(par2 + 0)/16, (double)(par3 + par5)/16, 1.0f/16, (double)((var9 + (float)par4 * var12) * var7), (double)((var10 + (float)0) * var8));
                var11.addVertexWithUV((double)(par2 + par4)/16, (double)(par3 + par5)/16, 1.0f/16, (double)((var9 + (float)par5 + (float)par5 * var12) * var7), (double)((var10 + (float)0) * var8));
                var11.addVertexWithUV((double)(par2 + par4)/16, (double)(par3 + 0)/16, 1.0f/16, (double)((var9 + (float)0) * var7), (double)((var10 + (float)par5) * var8));
                var11.addVertexWithUV((double)(par2 + 0)/16, (double)(par3 + 0)/16, 1.0f/16, (double)((var9 + (float)par5) * var7), (double)((var10 + (float)par5) * var8));
                var11.draw();
            }
        }
    }
	
	private void draw(Tessellator tes, float x1, float y1, float x2, float y2, ItemRenderType type) {
		float x1s=0;
       	float y1s=0;
       	float x2s=16;
       	float y2s=16;
       	if(type==type.ENTITY)
       	{
           	x1s=-.5F;
           	y1s=.75F;
           	x2s=.5F;
           	y2s=-.25F;
       	}
       	if(type==type.EQUIPPED)
       	{
           	x1s=0F;
           	y1s=0F;
           	x2s=1F;
           	y2s=1F;
       	}
		tes.startDrawingQuads();
		if(type!=type.EQUIPPED)
		{
			tes.addVertexWithUV(x1s, y2s, 0, x1, y2);
			tes.addVertexWithUV(x2s,y2s,0,x2,y2);
			tes.addVertexWithUV(x2s,y1s,0,x2,y1);
			tes.addVertexWithUV(x1s, y1s, 0, x1,y1);
		}	
		else
		{
        	tes.setNormal(0.0F, 0.0F, 1.0F);
			tes.addVertexWithUV(x1s, y2s,-1.0f/16, x2, y1);
			tes.addVertexWithUV(x2s,y2s,-1.0f/16,x1,y1);
			tes.addVertexWithUV(x2s,y1s,-1.0f/16,x1,y2);
			tes.addVertexWithUV(x1s, y1s, -1.0f/16, x2,y2);
			tes.draw();
			tes.startDrawingQuads();
        	tes.setNormal(0.0F, 0.0F, -1.0F);
			tes.addVertexWithUV(x1s, y2s,0f/16, x2, y1);
			tes.addVertexWithUV(x2s,y2s,0f/16,x1,y1);
			tes.addVertexWithUV(x2s,y1s,0f/16,x1,y2);
			tes.addVertexWithUV(x1s, y1s, 0f/16, x2,y2);
			tes.draw();
			tileEquippedItem(tes,x1,y1,x2,y2);
			tes.startDrawingQuads();
		}
		tes.draw();
       
        GL11.glDepthFunc(GL11.GL_GREATER);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        ForgeHooksClient.bindTexture("%blur%/misc/glint.png",0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        GL11.glColor4f(0.75F, 0F, 0F, 1.0F);
       	func_77018_a(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
       	func_77018_a(0 * 431278612 + 0 * 32178161, 0 - 2, 0 - 2, 20, 20,type);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

	}
	public void tileEquippedItem(Tessellator par1Tessellator, float par2, float par3, float par4, float par5)
	{
        float var6 = 1.0F;
        float var7 = -0.0625F;
        float c=var7;
        var7=0;
        int var8;
        float var9;
        float var10;
        float var11;
        int tileSize = TextureFXManager.instance().getTextureDimensions(GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)).width / 16;
        
        float tx = 1.0f / (32 * tileSize);
        float tz = -1.0f /  tileSize;

        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(1.0F, 0.0F, 0.0F);
        for (var8 = 0; var8 < tileSize; ++var8)
        {
            var9 = (float)var8 / tileSize;
            var10 = par2 + (par4 - par2) * var9 - tx;
            var11 = var6 * var9;
            par1Tessellator.addVertexWithUV(-(double)var11, 0.0D, (double)(0.0F - var7), (double)var10, (double)par5);
            par1Tessellator.addVertexWithUV(-(double)var11, 0.0D, (double)c, (double)var10, (double)par5);
            par1Tessellator.addVertexWithUV(-(double)var11, 1.0D, (double)c, (double)var10, (double)par3);
            par1Tessellator.addVertexWithUV(-(double)var11, 1.0D, (double)(0.0F - var7), (double)var10,(double)par3);
        }

        par1Tessellator.draw();
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(-1.0F, 0.0F, 0.0F);

        for (var8 = 0; var8 < tileSize; ++var8)
        {
            var9 = (float)var8 / tileSize;
            var10 = par2 + (par4 - par2) * var9 - tx;
            var11 = var6 * var9 + tz;
            par1Tessellator.addVertexWithUV((double)var11, 1.0D, (double)(0.0F - var7), (double)var10, (double)par3);
            par1Tessellator.addVertexWithUV((double)var11, 1.0D, (double)c, (double)var10, (double)par3);
            par1Tessellator.addVertexWithUV((double)var11, 0.0D, (double)c, (double)var10, (double)par5);
            par1Tessellator.addVertexWithUV((double)var11, 0.0D, (double)(0.0F - var7), (double)var10, (double)par5);
        }

        par1Tessellator.draw();
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (var8 = 0; var8 < tileSize; ++var8)
        {
            var9 = (float)var8 / tileSize;
            var10 = par5 + (par3 - par5) * var9 - tx;
            var11 = var6 * var9 + tz;
            par1Tessellator.addVertexWithUV(0.0D, (double)var11, (double)c, (double)par2, (double)var10);
            par1Tessellator.addVertexWithUV((double)var6, (double)var11, (double)c, (double)par4, (double)var10);
            par1Tessellator.addVertexWithUV((double)var6, (double)var11, (double)(0.0F - var7), (double)par4, (double)var10);
            par1Tessellator.addVertexWithUV(0.0D, (double)var11, (double)(0.0F - var7), (double)par2, (double)var10);
        }

        par1Tessellator.draw();
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (var8 = 0; var8 < tileSize; ++var8)
        {
            var9 = (float)var8 / tileSize;
            var10 = par5 + (par3 - par5) * var9 - tx;
            var11 = var6 * var9;
            par1Tessellator.addVertexWithUV((double)var6, (double)var11, (double)c, (double)par4, (double)var10);
            par1Tessellator.addVertexWithUV(0.0D, (double)var11, (double)c, (double)par2, (double)var10);
            par1Tessellator.addVertexWithUV(0.0D, (double)var11, (double)(0.0F - var7), (double)par2, (double)var10);
            par1Tessellator.addVertexWithUV((double)var6, (double)var11, (double)(0.0F - var7), (double)par4, (double)var10);
        }

        par1Tessellator.draw();

	}
}*/