package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.lwjgl.opengl.GL11;

public class CustomSky
{
    private static CustomSkyLayer[][] worldSkyLayers = (CustomSkyLayer[][])null;

    public static void reset()
    {
        worldSkyLayers = (CustomSkyLayer[][])null;
    }

    public static void update(RenderEngine var0)
    {
        reset();

        if (Config.isCustomSky())
        {
            if (var0 != null)
            {
                worldSkyLayers = readCustomSkies(var0);
            }
        }
    }

    private static CustomSkyLayer[][] readCustomSkies(RenderEngine var0)
    {
        CustomSkyLayer[][] var1 = new CustomSkyLayer[10][0];
        String var2 = "/environment/sky";
        int var3 = -1;
        int var4 = 0;

        while (var4 < var1.length)
        {
            String var5 = var2 + var4 + "/sky";
            ArrayList var6 = new ArrayList();
            int var7 = 1;

            while (true)
            {
                if (var7 < 1000)
                {
                    label63:
                    {
                        String var8 = var5 + var7 + ".properties";

                        try
                        {
                            InputStream var9 = var0.texturePack.getSelectedTexturePack().getResourceAsStream(var8);

                            if (var9 == null)
                            {
                                break label63;
                            }

                            Properties var10 = new Properties();
                            var10.load(var9);
                            Config.dbg("CustomSky properties: " + var8);
                            String var11 = var5 + var7 + ".png";
                            CustomSkyLayer var12 = new CustomSkyLayer(var10, var11);

                            if (var12.isValid(var8))
                            {
                                var12.textureId = var0.getTexture(var12.source);
                                var6.add(var12);
                                var9.close();
                            }
                        }
                        catch (FileNotFoundException var13)
                        {
                            break label63;
                        }
                        catch (IOException var14)
                        {
                            var14.printStackTrace();
                        }

                        ++var7;
                        continue;
                    }
                }

                if (var6.size() > 0)
                {
                    CustomSkyLayer[] var17 = (CustomSkyLayer[])((CustomSkyLayer[])var6.toArray(new CustomSkyLayer[var6.size()]));
                    var1[var4] = var17;
                    var3 = var4;
                }

                ++var4;
                break;
            }
        }

        if (var3 < 0)
        {
            return (CustomSkyLayer[][])null;
        }
        else
        {
            var4 = var3 + 1;
            CustomSkyLayer[][] var15 = new CustomSkyLayer[var4][0];

            for (int var16 = 0; var16 < var15.length; ++var16)
            {
                var15[var16] = var1[var16];
            }

            return var15;
        }
    }

    public static void renderSky(World var0, RenderEngine var1, float var2, float var3)
    {
        if (worldSkyLayers != null)
        {
            if (Config.getGameSettings().ofRenderDistanceFine >= 128)
            {
                int var4 = var0.provider.dimensionId;

                if (var4 >= 0 && var4 < worldSkyLayers.length)
                {
                    CustomSkyLayer[] var5 = worldSkyLayers[var4];

                    if (var5 != null)
                    {
                        long var6 = var0.getWorldTime();
                        int var8 = (int)(var6 % 24000L);

                        for (int var9 = 0; var9 < var5.length; ++var9)
                        {
                            CustomSkyLayer var10 = var5[var9];

                            if (var10.isActive(var8))
                            {
                                var10.render(var8, var1, var2, var3);
                            }
                        }

                        clearBlend(var3);
                    }
                }
            }
        }
    }

    public static boolean hasSkyLayers(World var0)
    {
        if (worldSkyLayers == null)
        {
            return false;
        }
        else if (Config.getGameSettings().ofRenderDistanceFine < 128)
        {
            return false;
        }
        else
        {
            int var1 = var0.provider.dimensionId;

            if (var1 >= 0 && var1 < worldSkyLayers.length)
            {
                CustomSkyLayer[] var2 = worldSkyLayers[var1];
                return var2 == null ? false : var2.length > 0;
            }
            else
            {
                return false;
            }
        }
    }

    private static void clearBlend(float var0)
    {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var0);
    }
}
