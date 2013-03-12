package net.minecraft.src;

public class TextureHDWaterFX extends TextureFX implements TextureHDFX
{
    private ITexturePack texturePackBase;
    private int tileWidth = 0;
    protected float[] buf1;
    protected float[] buf2;
    protected float[] buf3;
    protected float[] buf4;
    private int tickCounter;

    public TextureHDWaterFX()
    {
        super(Block.waterMoving.blockIndexInTexture);
        this.tileWidth = 16;
        this.imageData = new byte[this.tileWidth * this.tileWidth * 4];
        this.buf1 = new float[this.tileWidth * this.tileWidth];
        this.buf2 = new float[this.tileWidth * this.tileWidth];
        this.buf3 = new float[this.tileWidth * this.tileWidth];
        this.buf4 = new float[this.tileWidth * this.tileWidth];
        this.tickCounter = 0;
    }

    public void setTileWidth(int var1)
    {
        if (var1 > Config.getMaxDynamicTileWidth())
        {
            var1 = Config.getMaxDynamicTileWidth();
        }

        this.tileWidth = var1;
        this.imageData = new byte[var1 * var1 * 4];
        this.buf1 = new float[var1 * var1];
        this.buf2 = new float[var1 * var1];
        this.buf3 = new float[var1 * var1];
        this.buf4 = new float[var1 * var1];
        this.tickCounter = 0;
    }

    public void setTexturePackBase(ITexturePack var1)
    {
        this.texturePackBase = var1;
    }

    public void onTick()
    {
        if (!Config.isAnimatedWater())
        {
            this.imageData = null;
        }

        if (this.imageData != null)
        {
            ++this.tickCounter;
            int var1 = this.tileWidth - 1;
            int var2;
            int var3;
            float var4;
            int var6;
            int var7;

            for (var2 = 0; var2 < this.tileWidth; ++var2)
            {
                for (var3 = 0; var3 < this.tileWidth; ++var3)
                {
                    var4 = 0.0F;

                    for (int var5 = var2 - 1; var5 <= var2 + 1; ++var5)
                    {
                        var6 = var5 & var1;
                        var7 = var3 & var1;
                        var4 += this.buf1[var6 + var7 * this.tileWidth];
                    }

                    this.buf2[var2 + var3 * this.tileWidth] = var4 / 3.3F + this.buf3[var2 + var3 * this.tileWidth] * 0.8F;
                }
            }

            for (var2 = 0; var2 < this.tileWidth; ++var2)
            {
                for (var3 = 0; var3 < this.tileWidth; ++var3)
                {
                    this.buf3[var2 + var3 * this.tileWidth] += this.buf4[var2 + var3 * this.tileWidth] * 0.05F;

                    if (this.buf3[var2 + var3 * this.tileWidth] < 0.0F)
                    {
                        this.buf3[var2 + var3 * this.tileWidth] = 0.0F;
                    }

                    this.buf4[var2 + var3 * this.tileWidth] -= 0.1F;

                    if (Math.random() < 0.05D)
                    {
                        this.buf4[var2 + var3 * this.tileWidth] = 0.5F;
                    }
                }
            }

            float[] var13 = this.buf2;
            this.buf2 = this.buf1;
            this.buf1 = var13;

            for (var3 = 0; var3 < this.tileWidth * this.tileWidth; ++var3)
            {
                var4 = this.buf1[var3];

                if (var4 > 1.0F)
                {
                    var4 = 1.0F;
                }

                if (var4 < 0.0F)
                {
                    var4 = 0.0F;
                }

                float var14 = var4 * var4;
                var6 = (int)(32.0F + var14 * 32.0F);
                var7 = (int)(50.0F + var14 * 64.0F);
                int var8 = 255;
                int var9 = (int)(146.0F + var14 * 50.0F);

                if (this.anaglyphEnabled)
                {
                    int var10 = (var6 * 30 + var7 * 59 + var8 * 11) / 100;
                    int var11 = (var6 * 30 + var7 * 70) / 100;
                    int var12 = (var6 * 30 + var8 * 70) / 100;
                    var6 = var10;
                    var7 = var11;
                    var8 = var12;
                }

                this.imageData[var3 * 4 + 0] = (byte)var6;
                this.imageData[var3 * 4 + 1] = (byte)var7;
                this.imageData[var3 * 4 + 2] = (byte)var8;
                this.imageData[var3 * 4 + 3] = (byte)var9;
            }
        }
    }
}
