package net.minecraft.src;

import java.nio.ByteBuffer;
import java.util.Properties;
import org.lwjgl.opengl.GL11;

public class TextureAnimation
{
    private String srcTex = null;
    private String dstTex = null;
    private int dstTextId = -1;
    private int dstX = 0;
    private int dstY = 0;
    private int frameWidth = 0;
    private int frameHeight = 0;
    private CustomAnimationFrame[] frames = null;
    private int activeFrame = 0;
    private ByteBuffer imageData = null;

    public TextureAnimation(String var1, byte[] var2, String var3, int var4, int var5, int var6, int var7, int var8, Properties var9, int var10)
    {
        this.srcTex = var1;
        this.dstTex = var3;
        this.dstTextId = var4;
        this.dstX = var5;
        this.dstY = var6;
        this.frameWidth = var7;
        this.frameHeight = var8;
        int var11 = var7 * var8 * 4;

        if (var2.length % var11 != 0)
        {
            Config.dbg("Invalid animated texture length: " + var2.length + ", frameWidth: " + var8 + ", frameHeight: " + var8);
        }

        this.imageData = GLAllocation.createDirectByteBuffer(var2.length);
        this.imageData.put(var2);
        int var12 = var2.length / var11;

        if (var9.get("tile.0") != null)
        {
            for (int var13 = 0; var9.get("tile." + var13) != null; ++var13)
            {
                var12 = var13 + 1;
            }
        }

        String var21 = (String)var9.get("duration");
        int var14 = Config.parseInt(var21, var10);
        this.frames = new CustomAnimationFrame[var12];

        for (int var15 = 0; var15 < this.frames.length; ++var15)
        {
            String var16 = (String)var9.get("tile." + var15);
            int var17 = Config.parseInt(var16, var15);
            String var18 = (String)var9.get("duration." + var15);
            int var19 = Config.parseInt(var18, var14);
            CustomAnimationFrame var20 = new CustomAnimationFrame(var17, var19);
            this.frames[var15] = var20;
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

    public boolean updateTexture()
    {
        if (!this.nextFrame())
        {
            return false;
        }
        else
        {
            int var1 = this.frameWidth * this.frameHeight * 4;
            int var2 = this.getActiveFrameIndex();
            int var3 = var1 * var2;

            if (var3 + var1 > this.imageData.capacity())
            {
                return false;
            }
            else
            {
                this.imageData.position(var3);
                Config.getRenderEngine().bindTexture(this.dstTextId);
                GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
                return true;
            }
        }
    }
}
