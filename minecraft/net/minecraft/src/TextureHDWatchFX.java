package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureHDWatchFX extends TextureFX implements TextureHDFX
{
    private Minecraft mc;
    private int tileWidth;
    private ITexturePack texturePackBase;
    private int[] watchIconImageData;
    private int[] dialImageData;
    private byte[] watchBaseData;
    private byte[] dialBaseData;
    private double showAngle;
    private double angleDiff;

    public TextureHDWatchFX(Minecraft var1)
    {
        super(Item.pocketSundial.getIconFromDamage(0));
        this.mc = var1;
        this.tileWidth = 16;
        this.setup();
    }

    public void setTileWidth(int var1)
    {
        this.tileWidth = var1;
        this.setup();
    }

    public void setTexturePackBase(ITexturePack var1)
    {
        this.texturePackBase = var1;
    }

    private void setup()
    {
        this.imageData = new byte[this.tileWidth * this.tileWidth * 4];
        this.watchIconImageData = new int[this.tileWidth * this.tileWidth];
        this.dialImageData = new int[this.tileWidth * this.tileWidth];
        this.tileImage = 1;

        try
        {
            BufferedImage var1 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));

            if (this.texturePackBase != null)
            {
                var1 = ImageIO.read(this.texturePackBase.getResourceAsStream("/gui/items.png"));
            }

            this.tileWidth = var1.getWidth() / 16;
            this.imageData = new byte[this.tileWidth * this.tileWidth * 4];
            this.watchIconImageData = new int[this.tileWidth * this.tileWidth];
            this.dialImageData = new int[this.tileWidth * this.tileWidth];
            int var2 = this.iconIndex % 16 * this.tileWidth;
            int var3 = this.iconIndex / 16 * this.tileWidth;
            var1.getRGB(var2, var3, this.tileWidth, this.tileWidth, this.watchIconImageData, 0, this.tileWidth);
            var1 = ImageIO.read(Minecraft.class.getResource("/misc/dial.png"));

            if (this.texturePackBase != null)
            {
                var1 = ImageIO.read(this.texturePackBase.getResourceAsStream("/misc/dial.png"));
            }

            if (var1.getWidth() != this.tileWidth)
            {
                var1 = RenderEngine.scaleBufferedImage(var1, this.tileWidth, this.tileWidth);
            }

            var1.getRGB(0, 0, this.tileWidth, this.tileWidth, this.dialImageData, 0, this.tileWidth);
            this.watchBaseData = new byte[this.watchIconImageData.length * 4];
            this.dialBaseData = new byte[this.dialImageData.length * 4];
            int var4 = this.tileWidth * this.tileWidth;
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;

            for (var5 = 0; var5 < var4; ++var5)
            {
                var6 = this.watchIconImageData[var5] >> 24 & 255;
                var7 = this.watchIconImageData[var5] >> 16 & 255;
                var8 = this.watchIconImageData[var5] >> 8 & 255;
                var9 = this.watchIconImageData[var5] >> 0 & 255;

                if (var7 == var9 && var8 == 0 && var9 > 0)
                {
                    boolean var10 = false;
                }

                this.watchBaseData[var5 * 4 + 0] = (byte)var7;
                this.watchBaseData[var5 * 4 + 1] = (byte)var8;
                this.watchBaseData[var5 * 4 + 2] = (byte)var9;
                this.watchBaseData[var5 * 4 + 3] = (byte)var6;
            }

            for (var5 = 0; var5 < var4; ++var5)
            {
                var6 = this.dialImageData[var5] >> 24 & 255;
                var7 = this.dialImageData[var5] >> 16 & 255;
                var8 = this.dialImageData[var5] >> 8 & 255;
                var9 = this.dialImageData[var5] >> 0 & 255;
                this.dialBaseData[var5 * 4 + 0] = (byte)var7;
                this.dialBaseData[var5 * 4 + 1] = (byte)var8;
                this.dialBaseData[var5 * 4 + 2] = (byte)var9;
                this.dialBaseData[var5 * 4 + 3] = (byte)var6;
            }
        }
        catch (IOException var11)
        {
            var11.printStackTrace();
        }
    }

    public void onTick()
    {
        double var1 = 0.0D;

        if (this.mc.theWorld != null && this.mc.thePlayer != null)
        {
            float var3 = this.mc.theWorld.getCelestialAngle(1.0F);
            var1 = (double)(-var3 * (float)Math.PI * 2.0F);

            if (!this.mc.theWorld.provider.isSurfaceWorld())
            {
                var1 = Math.random() * Math.PI * 2.0D;
            }
        }

        double var28;

        for (var28 = var1 - this.showAngle; var28 < -Math.PI; var28 += (Math.PI * 2D))
        {
            ;
        }

        while (var28 >= Math.PI)
        {
            var28 -= (Math.PI * 2D);
        }

        if (var28 < -1.0D)
        {
            var28 = -1.0D;
        }

        if (var28 > 1.0D)
        {
            var28 = 1.0D;
        }

        this.angleDiff += var28 * 0.1D;
        this.angleDiff *= 0.8D;
        this.showAngle += this.angleDiff;
        double var5 = Math.sin(this.showAngle);
        double var7 = Math.cos(this.showAngle);
        int var9 = this.tileWidth * this.tileWidth;
        int var10 = this.tileWidth - 1;
        double var11 = (double)var10;

        for (int var13 = 0; var13 < var9; ++var13)
        {
            int var14 = var13 * 4;
            int var15 = this.watchBaseData[var14 + 0] & 255;
            int var16 = this.watchBaseData[var14 + 1] & 255;
            int var17 = this.watchBaseData[var14 + 2] & 255;
            int var18 = this.watchBaseData[var14 + 3] & 255;

            if (var15 == var17 && var16 == 0 && var17 > 0)
            {
                double var19 = -((double)(var13 % this.tileWidth) / var11 - 0.5D);
                double var21 = (double)(var13 / this.tileWidth) / var11 - 0.5D;
                int var23 = var15;
                int var24 = (int)((var19 * var7 + var21 * var5 + 0.5D) * (double)this.tileWidth);
                int var25 = (int)((var21 * var7 - var19 * var5 + 0.5D) * (double)this.tileWidth);
                int var26 = (var24 & var10) + (var25 & var10) * this.tileWidth;
                int var27 = var26 * 4;
                var15 = (this.dialBaseData[var27 + 0] & 255) * var15 / 255;
                var16 = (this.dialBaseData[var27 + 1] & 255) * var23 / 255;
                var17 = (this.dialBaseData[var27 + 2] & 255) * var23 / 255;
                var18 = this.dialBaseData[var27 + 3] & 255;
            }

            if (this.anaglyphEnabled)
            {
                int var29 = (var15 * 30 + var16 * 59 + var17 * 11) / 100;
                int var20 = (var15 * 30 + var16 * 70) / 100;
                int var30 = (var15 * 30 + var17 * 70) / 100;
                var15 = var29;
                var16 = var20;
                var17 = var30;
            }

            this.imageData[var14 + 0] = (byte)var15;
            this.imageData[var14 + 1] = (byte)var16;
            this.imageData[var14 + 2] = (byte)var17;
            this.imageData[var14 + 3] = (byte)var18;
        }
    }
}
