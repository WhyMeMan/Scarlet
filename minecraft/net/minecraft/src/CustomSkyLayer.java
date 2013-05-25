package net.minecraft.src;

import java.util.Properties;
import org.lwjgl.opengl.GL11;

public class CustomSkyLayer
{
    public String source = null;
    private int startFadeIn = -1;
    private int endFadeIn = -1;
    private int startFadeOut = -1;
    private int endFadeOut = -1;
    private int blend = 0;
    private boolean rotate = false;
    private float speed = 1.0F;
    private float[] axis;
    public int textureId;
    public static final int BLEND_ADD = 0;
    public static final int BLEND_SUBSTRACT = 1;
    public static final int BLEND_MULTIPLY = 2;
    public static final int BLEND_DODGE = 3;
    public static final int BLEND_BURN = 4;
    public static final int BLEND_SCREEN = 5;
    public static final int BLEND_REPLACE = 6;
    public static final float[] DEFAULT_AXIS = new float[] {1.0F, 0.0F, 0.0F};

    public CustomSkyLayer(Properties var1, String var2)
    {
        this.axis = DEFAULT_AXIS;
        this.textureId = -1;
        this.source = var1.getProperty("source", var2);
        this.startFadeIn = this.parseTime(var1.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(var1.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(var1.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(var1.getProperty("endFadeOut"));
        this.blend = this.parseBlend(var1.getProperty("blend"));
        this.rotate = this.parseBoolean(var1.getProperty("rotate"), true);
        this.speed = this.parseFloat(var1.getProperty("speed"), 1.0F);
        this.axis = this.parseAxis(var1.getProperty("axis"), DEFAULT_AXIS);
    }

    private int parseTime(String var1)
    {
        if (var1 == null)
        {
            return -1;
        }
        else
        {
            String[] var2 = Config.tokenize(var1, ":");

            if (var2.length != 2)
            {
                Config.dbg("Invalid time: " + var1);
                return -1;
            }
            else
            {
                String var3 = var2[0];
                String var4 = var2[1];
                int var5 = Config.parseInt(var3, -1);
                int var6 = Config.parseInt(var4, -1);

                if (var5 >= 0 && var5 <= 23 && var6 >= 0 && var6 <= 59)
                {
                    var5 -= 6;

                    if (var5 < 0)
                    {
                        var5 += 24;
                    }

                    int var7 = var5 * 1000 + (int)((double)var6 / 60.0D * 1000.0D);
                    return var7;
                }
                else
                {
                    Config.dbg("Invalid time: " + var1);
                    return -1;
                }
            }
        }
    }

    private int parseBlend(String var1)
    {
        if (var1 == null)
        {
            return 0;
        }
        else if (var1.equals("add"))
        {
            return 0;
        }
        else if (var1.equals("subtract"))
        {
            return 1;
        }
        else if (var1.equals("multiply"))
        {
            return 2;
        }
        else if (var1.equals("dodge"))
        {
            return 3;
        }
        else if (var1.equals("burn"))
        {
            return 4;
        }
        else if (var1.equals("screen"))
        {
            return 5;
        }
        else if (var1.equals("replace"))
        {
            return 6;
        }
        else
        {
            Config.dbg("Unknown blend: " + var1);
            return 0;
        }
    }

    private boolean parseBoolean(String var1, boolean var2)
    {
        if (var1 == null)
        {
            return var2;
        }
        else if (var1.toLowerCase().equals("true"))
        {
            return true;
        }
        else if (var1.toLowerCase().equals("false"))
        {
            return false;
        }
        else
        {
            Config.dbg("Unknown boolean: " + var1);
            return var2;
        }
    }

    private float parseFloat(String var1, float var2)
    {
        if (var1 == null)
        {
            return var2;
        }
        else
        {
            float var3 = Config.parseFloat(var1, Float.MIN_VALUE);

            if (var3 == Float.MIN_VALUE)
            {
                Config.dbg("Invalid value: " + var1);
                return var2;
            }
            else
            {
                return var3;
            }
        }
    }

    private float[] parseAxis(String var1, float[] var2)
    {
        if (var1 == null)
        {
            return var2;
        }
        else
        {
            String[] var3 = Config.tokenize(var1, " ");

            if (var3.length != 3)
            {
                Config.dbg("Invalid axis: " + var1);
                return var2;
            }
            else
            {
                float[] var4 = new float[3];

                for (int var5 = 0; var5 < var3.length; ++var5)
                {
                    var4[var5] = Config.parseFloat(var3[var5], Float.MIN_VALUE);

                    if (var4[var5] == Float.MIN_VALUE)
                    {
                        Config.dbg("Invalid axis: " + var1);
                        return var2;
                    }

                    if (var4[var5] < -1.0F || var4[var5] > 1.0F)
                    {
                        Config.dbg("Invalid axis values: " + var1);
                        return var2;
                    }
                }

                float var9 = var4[0];
                float var6 = var4[1];
                float var7 = var4[2];

                if (var9 * var9 + var6 * var6 + var7 * var7 < 1.0E-5F)
                {
                    Config.dbg("Invalid axis values: " + var1);
                    return var2;
                }
                else
                {
                    float[] var8 = new float[] {var7, var6, -var9};
                    return var8;
                }
            }
        }
    }

    public boolean isValid(String var1)
    {
        if (this.source == null)
        {
            Config.dbg("No source texture: " + var1);
            return false;
        }
        else if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0)
        {
            int var2 = this.normalizeTime(this.endFadeIn - this.startFadeIn);

            if (this.startFadeOut < 0)
            {
                this.startFadeOut = this.normalizeTime(this.endFadeOut - var2);
            }

            int var3 = this.normalizeTime(this.startFadeOut - this.endFadeIn);
            int var4 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            int var5 = this.normalizeTime(this.startFadeIn - this.endFadeOut);
            int var6 = var2 + var3 + var4 + var5;

            if (var6 != 24000)
            {
                Config.dbg("Invalid fadeIn/fadeOut times, sum is more than 24h");
                return false;
            }
            else if (this.speed < 0.0F)
            {
                Config.dbg("Invalid speed: " + this.speed);
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            Config.dbg("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
        }
    }

    private int normalizeTime(int var1)
    {
        while (var1 >= 24000)
        {
            var1 -= 24000;
        }

        while (var1 < 0)
        {
            var1 += 24000;
        }

        return var1;
    }

    public void render(int var1, RenderEngine var2, float var3, float var4)
    {
        float var5 = var4 * this.getFadeBrightness(var1);
        var5 = Config.limit(var5, 0.0F, 1.0F);

        if (var5 >= 1.0E-4F)
        {
            var2.bindTexture(this.textureId);
            this.setupBlend(var5);
            GL11.glPushMatrix();

            if (this.rotate)
            {
                GL11.glRotatef(var3 * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
            }

            Tessellator var6 = Tessellator.instance;
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(var6, 4);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSide(var6, 1);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSide(var6, 0);
            GL11.glPopMatrix();
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(var6, 5);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(var6, 2);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSide(var6, 3);
            GL11.glPopMatrix();
        }
    }

    private float getFadeBrightness(int var1)
    {
        int var2;
        int var3;

        if (this.timeBetween(var1, this.startFadeIn, this.endFadeIn))
        {
            var2 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
            var3 = this.normalizeTime(var1 - this.startFadeIn);
            return (float)var3 / (float)var2;
        }
        else if (this.timeBetween(var1, this.endFadeIn, this.startFadeOut))
        {
            return 1.0F;
        }
        else if (this.timeBetween(var1, this.startFadeOut, this.endFadeOut))
        {
            var2 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
            var3 = this.normalizeTime(var1 - this.startFadeOut);
            return 1.0F - (float)var3 / (float)var2;
        }
        else
        {
            return 0.0F;
        }
    }

    private void renderSide(Tessellator var1, int var2)
    {
        double var3 = (double)(var2 % 3) / 3.0D;
        double var5 = (double)(var2 / 3) / 2.0D;
        var1.startDrawingQuads();
        var1.addVertexWithUV(-100.0D, -100.0D, -100.0D, var3, var5);
        var1.addVertexWithUV(-100.0D, -100.0D, 100.0D, var3, var5 + 0.5D);
        var1.addVertexWithUV(100.0D, -100.0D, 100.0D, var3 + 0.3333333333333333D, var5 + 0.5D);
        var1.addVertexWithUV(100.0D, -100.0D, -100.0D, var3 + 0.3333333333333333D, var5);
        var1.draw();
    }

    void setupBlend(float var1)
    {
        switch (this.blend)
        {
            case 0:
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, var1);
                break;

            case 1:
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ZERO);
                GL11.glColor4f(var1, var1, var1, 1.0F);
                break;

            case 2:
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColor4f(var1, var1, var1, var1);
                break;

            case 3:
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                GL11.glColor4f(var1, var1, var1, 1.0F);
                break;

            case 4:
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
                GL11.glColor4f(var1, var1, var1, 1.0F);
                break;

            case 5:
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_COLOR);
                GL11.glColor4f(var1, var1, var1, 1.0F);
                break;

            case 6:
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, var1);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public boolean isActive(int var1)
    {
        return !this.timeBetween(var1, this.endFadeOut, this.startFadeIn);
    }

    private boolean timeBetween(int var1, int var2, int var3)
    {
        return var2 <= var3 ? var1 >= var2 && var1 <= var3 : var1 >= var2 || var1 <= var3;
    }
}
