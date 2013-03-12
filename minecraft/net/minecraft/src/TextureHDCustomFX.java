package net.minecraft.src;

public class TextureHDCustomFX extends TextureFX implements TextureHDFX
{
    private ITexturePack texturePackBase;
    private int tileWidth = 0;

    public TextureHDCustomFX(int var1, int var2)
    {
        super(var1);
        this.tileImage = var2;
        this.tileWidth = 16;
        this.imageData = null;
    }

    public void setTileWidth(int var1)
    {
        this.tileWidth = var1;
    }

    public void setTexturePackBase(ITexturePack var1)
    {
        this.texturePackBase = var1;
    }

    public void onTick() {}
}
