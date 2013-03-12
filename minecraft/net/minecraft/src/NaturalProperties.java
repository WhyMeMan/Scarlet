package net.minecraft.src;

public class NaturalProperties
{
    public int rotation = 1;
    public boolean flip = false;

    public NaturalProperties(String var1)
    {
        if (var1.equals("4"))
        {
            this.rotation = 4;
        }
        else if (var1.equals("2"))
        {
            this.rotation = 2;
        }
        else if (var1.equals("F"))
        {
            this.flip = true;
        }
        else if (var1.equals("4F"))
        {
            this.rotation = 4;
            this.flip = true;
        }
        else if (var1.equals("2F"))
        {
            this.rotation = 2;
            this.flip = true;
        }
        else
        {
            Config.dbg("NaturalTextures: Unknown type: " + var1);
        }
    }

    public boolean isValid()
    {
        return this.rotation != 2 && this.rotation != 4 ? this.flip : true;
    }
}
