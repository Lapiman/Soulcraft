package SoundLogic.SoulCraft;

import java.awt.Transparency;
import java.awt.color.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.nio.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureStitched;
import net.minecraft.util.Icon;

@SideOnly(Side.CLIENT)
public class TextureHaunted extends TextureStitched
{
    private double field_94239_h;
    private double field_94240_i;
    private TextureStitched original;

    public TextureHaunted(TextureStitched icon)
    {
        super("");
        this.copyFrom(ItemHaunted.Base);
        original=icon;
    }

    @SideOnly(Side.CLIENT)
    public void updateAnimation()
    {
    	byte[] result=funkyRender();
//        Texture tex=TextureManager.instance().makeTexture("HAUNTED "+original.getIconName(), 0, 0, 0, 0, 0, 0, 0, true,result);
        this.copyFrom(this.originX, this.originY, result, this.rotated);
    }
	
    @SideOnly(Side.CLIENT)
    public void copyFrom(int par1, int par2, byte[] bytes, boolean par4)
    {
        this.textureSheet.getTextureData().position(0);
        int constant=16;
        for (int k = 0; k < constant; ++k)
        {
            int l = par2 + k;
            int i1 = k * constant * 3;
            int j1 = l * this.textureSheet.getWidth() * 4;
             if (par4)
            {
                    l = par2 + (16 - k);
                }

                for (int k1 = 0; k1 < constant; ++k1)
                {
                    int l1 = j1 + (k1 + par1) * 4;
                    int i2 = i1 + k1 * 3;

                    if (par4)
                    {
                        l1 = par1 + k1 * this.textureSheet.getWidth() * 4 + l * 4;
                    }
                    int test=bytes.length;
                    this.textureSheet.getTextureData().put(l1 + 0, bytes[i2 + 0]);
                    this.textureSheet.getTextureData().put(l1 + 1, bytes[i2 + 1]);
                    this.textureSheet.getTextureData().put(l1 + 2, bytes[i2 + 2]);
                    this.textureSheet.getTextureData().put(l1 + 3, (byte) 255);
                }
            }

            this.textureSheet.getTextureData().position(16 * 16 * 4);
    }

	@SideOnly(Side.CLIENT)
    public byte[] funkyRender()
    {
        Tessellator tessellator = Tessellator.instance;
        float f = original.getMinU();
        float f1 = original.getMaxU();
        float f2 = original.getMinV();
        float f3 = original.getMaxV();
        float f4 = 0.0F;
        float f5 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-f4, -f5, 0.0F);
        float f6 = 1.5F;
        GL11.glScalef(f6, f6, f6);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        renderItemIn2D(tessellator, f1, f2, f, f3, original.getSheetWidth(), original.getSheetHeight(), 0.0625F);
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        Minecraft.getMinecraft().renderEngine.bindTexture("%blur%/misc/glint.png");
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        byte[] img=grabScreen();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        return img;
    }
	@SideOnly(Side.CLIENT)
    public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7)
    {
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)par1, (double)par4);
        par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)par1, (double)par2);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - par7), (double)par1, (double)par2);
        par0Tessellator.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - par7), (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - par7), (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - par7), (double)par1, (double)par4);
        par0Tessellator.draw();
        float f5 = (float)par5 * (par1 - par3);
        float f6 = (float)par6 * (par4 - par2);
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        int k;
        float f7;
        float f8;

        for (k = 0; (float)k < f5; ++k)
        {
            f7 = (float)k / f5;
            f8 = par1 + (par3 - par1) * f7 - 0.5F / (float)par5;
            par0Tessellator.addVertexWithUV((double)f7, 0.0D, (double)(0.0F - par7), (double)f8, (double)par4);
            par0Tessellator.addVertexWithUV((double)f7, 0.0D, 0.0D, (double)f8, (double)par4);
            par0Tessellator.addVertexWithUV((double)f7, 1.0D, 0.0D, (double)f8, (double)par2);
            par0Tessellator.addVertexWithUV((double)f7, 1.0D, (double)(0.0F - par7), (double)f8, (double)par2);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);
        float f9;

        for (k = 0; (float)k < f5; ++k)
        {
            f7 = (float)k / f5;
            f8 = par1 + (par3 - par1) * f7 - 0.5F / (float)par5;
            f9 = f7 + 1.0F / f5;
            par0Tessellator.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - par7), (double)f8, (double)par2);
            par0Tessellator.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f8, (double)par2);
            par0Tessellator.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f8, (double)par4);
            par0Tessellator.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - par7), (double)f8, (double)par4);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (k = 0; (float)k < f6; ++k)
        {
            f7 = (float)k / f6;
            f8 = par4 + (par2 - par4) * f7 - 0.5F / (float)par6;
            f9 = f7 + 1.0F / f6;
            par0Tessellator.addVertexWithUV(0.0D, (double)f9, 0.0D, (double)par1, (double)f8);
            par0Tessellator.addVertexWithUV(1.0D, (double)f9, 0.0D, (double)par3, (double)f8);
            par0Tessellator.addVertexWithUV(1.0D, (double)f9, (double)(0.0F - par7), (double)par3, (double)f8);
            par0Tessellator.addVertexWithUV(0.0D, (double)f9, (double)(0.0F - par7), (double)par1, (double)f8);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (k = 0; (float)k < f6; ++k)
        {
            f7 = (float)k / f6;
            f8 = par4 + (par2 - par4) * f7 - 0.5F / (float)par6;
            par0Tessellator.addVertexWithUV(1.0D, (double)f7, 0.0D, (double)par3, (double)f8);
            par0Tessellator.addVertexWithUV(0.0D, (double)f7, 0.0D, (double)par1, (double)f8);
            par0Tessellator.addVertexWithUV(0.0D, (double)f7, (double)(0.0F - par7), (double)par1, (double)f8);
            par0Tessellator.addVertexWithUV(1.0D, (double)f7, (double)(0.0F - par7), (double)par3, (double)f8);
        }

        par0Tessellator.draw();
    }
	@SideOnly(Side.CLIENT)
    private static synchronized byte[] grabScreen()
    {
        int w = 16;
        int h = 16;
        ByteBuffer bufor = BufferUtils.createByteBuffer(w * h * 3);

        GL11.glReadPixels(0, 0, w, h, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, bufor); //Copy the image to the array imageData

        byte[] byteimg = new byte[w * h * 3];
        bufor.get(byteimg, 0, byteimg.length);
        return byteimg;
    }
	@SideOnly(Side.CLIENT)
    BufferedImage toImage(byte[] data, int w, int h)
    {
        if (data.length == 0)
            return null;

        DataBuffer buffer = new DataBufferByte(data, w * h);

        int pixelStride = 3; //assuming r, g, b, skip, r, g, b, skip...
        int scanlineStride = 3 * w; //no extra padding   
        int[] bandOffsets = { 0, 1, 2 }; //r, g, b
        WritableRaster raster = Raster.createInterleavedRaster(buffer, w, h, scanlineStride, pixelStride, bandOffsets,
                null);

        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        boolean hasAlpha = false;
        boolean isAlphaPremultiplied = true;
        int transparency = Transparency.TRANSLUCENT;
        int transferType = DataBuffer.TYPE_BYTE;
        ColorModel colorModel = new ComponentColorModel(colorSpace, hasAlpha, isAlphaPremultiplied, transparency,
                transferType);

        BufferedImage image = new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);

        AffineTransform flip;
        AffineTransformOp op;
        flip = AffineTransform.getScaleInstance(1, -1);
        flip.translate(0, -image.getHeight());
        op = new AffineTransformOp(flip, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);

        return image;
    }
}
