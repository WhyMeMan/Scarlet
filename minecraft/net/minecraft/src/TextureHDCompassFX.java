package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureHDCompassFX extends TextureFX implements TextureHDFX
{
    private Minecraft mc;
    private int tileWidth = 0;
    private ITexturePack texturePackBase = null;
    private byte[] baseImageData;
    private int[] compassIconImageData;
    private double showAngle;
    private double angleDiff;
    public static TextureHDCompassFX instance = null;

    public TextureHDCompassFX(Minecraft var1)
    {
        super(Item.compass.getIconFromDamage(0));
        this.mc = var1;
        this.tileWidth = 16;
        this.setup();
        instance = this;
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
        this.compassIconImageData = new int[this.tileWidth * this.tileWidth];
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
            this.compassIconImageData = new int[this.tileWidth * this.tileWidth];
            int var2 = this.iconIndex % 16 * this.tileWidth;
            int var3 = this.iconIndex / 16 * this.tileWidth;
            var1.getRGB(var2, var3, this.tileWidth, this.tileWidth, this.compassIconImageData, 0, this.tileWidth);
            this.baseImageData = new byte[this.imageData.length];
            int var4 = this.tileWidth * this.tileWidth;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                int var6 = this.compassIconImageData[var5] >> 24 & 255;
                int var7 = this.compassIconImageData[var5] >> 16 & 255;
                int var8 = this.compassIconImageData[var5] >> 8 & 255;
                int var9 = this.compassIconImageData[var5] >> 0 & 255;

                if (this.anaglyphEnabled)
                {
                    int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                    int var11 = (var7 * 30 + var8 * 70) / 100;
                    int var12 = (var7 * 30 + var9 * 70) / 100;
                    var7 = var10;
                    var8 = var11;
                    var9 = var12;
                }

                this.baseImageData[var5 * 4 + 0] = (byte)var7;
                this.baseImageData[var5 * 4 + 1] = (byte)var8;
                this.baseImageData[var5 * 4 + 2] = (byte)var9;
                this.baseImageData[var5 * 4 + 3] = (byte)var6;
            }
        }
        catch (IOException var13)
        {
            var13.printStackTrace();
        }
    }

    public void onTick()
    {
        int var10000 = this.tileWidth * this.tileWidth;
        double var2 = (double)(this.tileWidth / 2) + 0.5D;
        double var4 = (double)(this.tileWidth / 2) - 0.5D;
        double var6 = 0.3D * (double)(this.tileWidth / 16);
        System.arraycopy(this.baseImageData, 0, this.imageData, 0, this.imageData.length);
        double var8 = 0.0D;

        if (this.mc.theWorld != null && this.mc.thePlayer != null)
        {
            ChunkCoordinates var10 = this.mc.theWorld.getSpawnPoint();
            double var11 = (double)var10.posX - this.mc.thePlayer.posX;
            double var13 = (double)var10.posZ - this.mc.thePlayer.posZ;
            var8 = (double)(this.mc.thePlayer.rotationYaw - 90.0F) * Math.PI / 180.0D - Math.atan2(var13, var11);

            if (!this.mc.theWorld.provider.isSurfaceWorld())
            {
                var8 = Math.random() * Math.PI * 2.0D;
            }
        }

        double var27;

        for (var27 = var8 - this.showAngle; var27 < -Math.PI; var27 += (Math.PI * 2D))
        {
            ;
        }

        while (var27 >= Math.PI)
        {
            var27 -= (Math.PI * 2D);
        }

        if (var27 < -1.0D)
        {
            var27 = -1.0D;
        }

        if (var27 > 1.0D)
        {
            var27 = 1.0D;
        }

        this.angleDiff += var27 * 0.1D;
        this.angleDiff *= 0.8D;
        this.showAngle += this.angleDiff;
        double var12 = Math.sin(this.showAngle);
        double var14 = Math.cos(this.showAngle);
        int var17;
        int var16;
        int var19;
        int var18;
        int var21;
        int var20;
        short var23;
        int var22;
        int var25;
        int var24;
        int var26;

        for (var16 = -4; var16 <= 4; ++var16)
        {
            var17 = (int)(var2 + var14 * (double)var16 * var6);
            var18 = (int)(var4 - var12 * (double)var16 * var6 * 0.5D);
            var19 = var18 * this.tileWidth + var17;
            var20 = 100;
            var21 = 100;
            var22 = 100;
            var23 = 255;

            if (this.anaglyphEnabled)
            {
                var24 = (var20 * 30 + var21 * 59 + var22 * 11) / 100;
                var25 = (var20 * 30 + var21 * 70) / 100;
                var26 = (var20 * 30 + var22 * 70) / 100;
                var20 = var24;
                var21 = var25;
                var22 = var26;
            }

            var24 = var19 * 4;
            this.imageData[var24 + 0] = (byte)var20;
            this.imageData[var24 + 1] = (byte)var21;
            this.imageData[var24 + 2] = (byte)var22;
            this.imageData[var24 + 3] = (byte)var23;
        }

        for (var16 = -8; var16 <= 16; ++var16)
        {
            var17 = (int)(var2 + var12 * (double)var16 * var6);
            var18 = (int)(var4 + var14 * (double)var16 * var6 * 0.5D);
            var19 = var18 * this.tileWidth + var17;
            var20 = var16 < 0 ? 100 : 255;
            var21 = var16 < 0 ? 100 : 20;
            var22 = var16 < 0 ? 100 : 20;
            var23 = 255;

            if (this.anaglyphEnabled)
            {
                var24 = (var20 * 30 + var21 * 59 + var22 * 11) / 100;
                var25 = (var20 * 30 + var21 * 70) / 100;
                var26 = (var20 * 30 + var22 * 70) / 100;
                var20 = var24;
                var21 = var25;
                var22 = var26;
            }

            var24 = var19 * 4;
            this.imageData[var24 + 0] = (byte)var20;
            this.imageData[var24 + 1] = (byte)var21;
            this.imageData[var24 + 2] = (byte)var22;
            this.imageData[var24 + 3] = (byte)var23;
        }
    }
}
