package net.minecraft.src;

import java.nio.ByteBuffer;
import java.util.Properties;

public class CustomAnimation
{
    private String imagePath = null;
    public byte[] imageBytes = null;
    public int frameWidth = 0;
    public int frameHeight = 0;
    public CustomAnimationFrame[] frames = null;
    public int activeFrame = 0;
    public String destTexture = null;
    public int destX = 0;
    public int destY = 0;

    public CustomAnimation(String var1, byte[] var2, int var3, int var4, Properties var5, int var6)
    {
        this.imagePath = var1;
        this.imageBytes = var2;
        this.frameWidth = var3;
        this.frameHeight = var4;
        int var7 = var3 * var4 * 4;

        if (var2.length % var7 != 0)
        {
            Config.dbg("Invalid animated texture length: " + var2.length + ", frameWidth: " + var4 + ", frameHeight: " + var4);
        }

        int var8 = var2.length / var7;

        if (var5.get("tile.0") != null)
        {
            for (int var9 = 0; var5.get("tile." + var9) != null; ++var9)
            {
                var8 = var9 + 1;
            }
        }

        String var17 = (String)var5.get("duration");
        int var10 = Config.parseInt(var17, var6);
        this.frames = new CustomAnimationFrame[var8];

        for (int var11 = 0; var11 < this.frames.length; ++var11)
        {
            String var12 = (String)var5.get("tile." + var11);
            int var13 = Config.parseInt(var12, var11);
            String var14 = (String)var5.get("duration." + var11);
            int var15 = Config.parseInt(var14, var10);
            CustomAnimationFrame var16 = new CustomAnimationFrame(var13, var15);
            this.frames[var11] = var16;
        }
    }

    public boolean nextFrame()
    {
        if (this.frames.length <= 0)
        {
            return false;
        }
        else
        {
            if (this.activeFrame >= this.frames.length)
            {
                this.activeFrame = 0;
            }

            CustomAnimationFrame var1 = this.frames[this.activeFrame];
            ++var1.counter;

            if (var1.counter < var1.duration)
            {
                return false;
            }
            else
            {
                var1.counter = 0;
                ++this.activeFrame;

                if (this.activeFrame >= this.frames.length)
                {
                    this.activeFrame = 0;
                }

                return true;
            }
        }
    }

    public int getActiveFrameIndex()
    {
        if (this.frames.length <= 0)
        {
            return 0;
        }
        else
        {
            if (this.activeFrame >= this.frames.length)
            {
                this.activeFrame = 0;
            }

            CustomAnimationFrame var1 = this.frames[this.activeFrame];
            return var1.index;
        }
    }

    public int getFrameCount()
    {
        return this.frames.length;
    }

    public boolean updateCustomTexture(ByteBuffer var1, boolean var2, boolean var3, StringBuffer var4)
    {
        if (this.imageBytes == null)
        {
            return false;
        }
        else if (!var2 && var3)
        {
            return true;
        }
        else if (!this.nextFrame())
        {
            return true;
        }
        else
        {
            int var5 = this.frameWidth * this.frameHeight * 4;

            if (this.imageBytes.length < var5)
            {
                return false;
            }
            else
            {
                int var6 = this.getFrameCount();
                int var7 = this.getActiveFrameIndex();
                int var8 = 0;

                if (var2)
                {
                    var8 = var5 * var7;
                }

                if (var8 + var5 > this.imageBytes.length)
                {
                    return true;
                }
                else
                {
                    var1.clear();
                    var1.put(this.imageBytes, var8, var5);
                    var1.position(0).limit(var5);
                    var4.append(this.imagePath);
                    var4.append(":");
                    var4.append(var7);
                    return true;
                }
            }
        }
    }
}
