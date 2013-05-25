package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

public class CustomColorizer
{
    private static int[] grassColors = null;
    private static int[] waterColors = null;
    private static int[] foliageColors = null;
    private static int[] foliagePineColors = null;
    private static int[] foliageBirchColors = null;
    private static int[] swampFoliageColors = null;
    private static int[] swampGrassColors = null;
    private static int[][] blockPalettes = (int[][])null;
    private static int[][] paletteColors = (int[][])null;
    private static int[] skyColors = null;
    private static int[] fogColors = null;
    private static int[] underwaterColors = null;
    private static float[][][] lightMapsColorsRgb = (float[][][])null;
    private static int[] lightMapsHeight = null;
    private static float[][] sunRgbs = new float[16][3];
    private static float[][] torchRgbs = new float[16][3];
    private static int[] redstoneColors = null;
    private static int[] stemColors = null;
    private static int[] myceliumParticleColors = null;
    private static boolean useDefaultColorMultiplier = true;
    private static int particleWaterColor = -1;
    private static int particlePortalColor = -1;
    private static int lilyPadColor = -1;
    private static Vec3 fogColorNether = null;
    private static Vec3 fogColorEnd = null;
    private static Vec3 skyColorEnd = null;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static Random random = new Random();

    public static void update(RenderEngine var0)
    {
        grassColors = null;
        waterColors = null;
        foliageColors = null;
        foliageBirchColors = null;
        foliagePineColors = null;
        swampGrassColors = null;
        swampFoliageColors = null;
        skyColors = null;
        fogColors = null;
        underwaterColors = null;
        redstoneColors = null;
        stemColors = null;
        myceliumParticleColors = null;
        lightMapsColorsRgb = (float[][][])null;
        lightMapsHeight = null;
        lilyPadColor = -1;
        particleWaterColor = -1;
        particlePortalColor = -1;
        fogColorNether = null;
        fogColorEnd = null;
        skyColorEnd = null;
        blockPalettes = (int[][])null;
        paletteColors = (int[][])null;
        useDefaultColorMultiplier = true;
        grassColors = getCustomColors("/misc/grasscolor.png", var0, 65536);
        foliageColors = getCustomColors("/misc/foliagecolor.png", var0, 65536);
        waterColors = getCustomColors("/misc/watercolorX.png", var0, 65536);

        if (Config.isCustomColors())
        {
            foliagePineColors = getCustomColors("/misc/pinecolor.png", var0, 65536);
            foliageBirchColors = getCustomColors("/misc/birchcolor.png", var0, 65536);
            swampGrassColors = getCustomColors("/misc/swampgrasscolor.png", var0, 65536);
            swampFoliageColors = getCustomColors("/misc/swampfoliagecolor.png", var0, 65536);
            skyColors = getCustomColors("/misc/skycolor0.png", var0, 65536);
            fogColors = getCustomColors("/misc/fogcolor0.png", var0, 65536);
            underwaterColors = getCustomColors("/misc/underwatercolor.png", var0, 65536);
            redstoneColors = getCustomColors("/misc/redstonecolor.png", var0, 16);
            stemColors = getCustomColors("/misc/stemcolor.png", var0, 8);
            myceliumParticleColors = getCustomColors("/misc/myceliumparticlecolor.png", var0, -1);
            int[][] var1 = new int[3][];
            lightMapsColorsRgb = new float[3][][];
            lightMapsHeight = new int[3];

            for (int var2 = 0; var2 < var1.length; ++var2)
            {
                String var3 = "/environment/lightmap" + (var2 - 1) + ".png";
                var1[var2] = getCustomColors(var3, var0, -1);

                if (var1[var2] != null)
                {
                    lightMapsColorsRgb[var2] = toRgb(var1[var2]);
                }

                lightMapsHeight[var2] = getTextureHeight(var0, var3, 32);
            }

            readColorProperties("/color.properties", var0);
            updateUseDefaultColorMultiplier();
        }
    }

    private static int getTextureHeight(RenderEngine var0, String var1, int var2)
    {
        try
        {
            BufferedImage var3 = var0.readTextureImage(var1);
            return var3 == null ? var2 : var3.getHeight();
        }
        catch (IOException var4)
        {
            return var2;
        }
    }

    private static float[][] toRgb(int[] var0)
    {
        float[][] var1 = new float[var0.length][3];

        for (int var2 = 0; var2 < var0.length; ++var2)
        {
            int var3 = var0[var2];
            float var4 = (float)(var3 >> 16 & 255) / 255.0F;
            float var5 = (float)(var3 >> 8 & 255) / 255.0F;
            float var6 = (float)(var3 & 255) / 255.0F;
            float[] var7 = var1[var2];
            var7[0] = var4;
            var7[1] = var5;
            var7[2] = var6;
        }

        return var1;
    }

    private static void readColorProperties(String var0, RenderEngine var1)
    {
        try
        {
            InputStream var2 = var1.getTexturePack().getSelectedTexturePack().getResourceAsStream(var0);

            if (var2 == null)
            {
                return;
            }

            Config.log("Loading " + var0);
            Properties var3 = new Properties();
            var3.load(var2);
            lilyPadColor = readColor(var3, "lilypad");
            particleWaterColor = readColor(var3, new String[] {"particle.water", "drop.water"});
            particlePortalColor = readColor(var3, "particle.portal");
            fogColorNether = readColorVec3(var3, "fog.nether");
            fogColorEnd = readColorVec3(var3, "fog.end");
            skyColorEnd = readColorVec3(var3, "sky.end");
            readCustomPalettes(var3, var1);
        }
        catch (FileNotFoundException var4)
        {
            return;
        }
        catch (IOException var5)
        {
            var5.printStackTrace();
        }
    }

    private static void readCustomPalettes(Properties var0, RenderEngine var1)
    {
        blockPalettes = new int[256][1];

        for (int var2 = 0; var2 < 256; ++var2)
        {
            blockPalettes[var2][0] = -1;
        }

        String var17 = "palette.block.";
        HashMap var3 = new HashMap();
        Set var4 = var0.keySet();
        Iterator var5 = var4.iterator();
        String var7;

        while (var5.hasNext())
        {
            String var6 = (String)var5.next();
            var7 = var0.getProperty(var6);

            if (var6.startsWith(var17))
            {
                var3.put(var6, var7);
            }
        }

        String[] var18 = (String[])((String[])var3.keySet().toArray(new String[var3.size()]));
        paletteColors = new int[var18.length][];

        for (int var19 = 0; var19 < var18.length; ++var19)
        {
            var7 = var18[var19];
            String var8 = var0.getProperty(var7);
            Config.log("Block palette: " + var7 + " = " + var8);
            String var9 = var7.substring(var17.length());
            int[] var10 = getCustomColors(var9, var1, 65536);
            paletteColors[var19] = var10;
            String[] var11 = Config.tokenize(var8, " ,;");

            for (int var12 = 0; var12 < var11.length; ++var12)
            {
                String var13 = var11[var12];
                int var14 = -1;

                if (var13.contains(":"))
                {
                    String[] var15 = Config.tokenize(var13, ":");
                    var13 = var15[0];
                    String var16 = var15[1];
                    var14 = Config.parseInt(var16, -1);

                    if (var14 < 0 || var14 > 15)
                    {
                        Config.log("Invalid block metadata: " + var13 + " in palette: " + var7);
                        continue;
                    }
                }

                int var20 = Config.parseInt(var13, -1);

                if (var20 >= 0 && var20 <= 255)
                {
                    if (var20 != Block.grass.blockID && var20 != Block.tallGrass.blockID && var20 != Block.leaves.blockID && var20 != Block.vine.blockID)
                    {
                        if (var14 == -1)
                        {
                            blockPalettes[var20][0] = var19;
                        }
                        else
                        {
                            if (blockPalettes[var20].length < 16)
                            {
                                blockPalettes[var20] = new int[16];
                                Arrays.fill(blockPalettes[var20], -1);
                            }

                            blockPalettes[var20][var14] = var19;
                        }
                    }
                }
                else
                {
                    Config.log("Invalid block index: " + var20 + " in palette: " + var7);
                }
            }
        }
    }

    private static int readColor(Properties var0, String[] var1)
    {
        for (int var2 = 0; var2 < var1.length; ++var2)
        {
            String var3 = var1[var2];
            int var4 = readColor(var0, var3);

            if (var4 >= 0)
            {
                return var4;
            }
        }

        return -1;
    }

    private static int readColor(Properties var0, String var1)
    {
        String var2 = var0.getProperty(var1);

        if (var2 == null)
        {
            return -1;
        }
        else
        {
            try
            {
                int var3 = Integer.parseInt(var2, 16) & 16777215;
                Config.log("Custom color: " + var1 + " = " + var2);
                return var3;
            }
            catch (NumberFormatException var4)
            {
                Config.log("Invalid custom color: " + var1 + " = " + var2);
                return -1;
            }
        }
    }

    private static Vec3 readColorVec3(Properties var0, String var1)
    {
        int var2 = readColor(var0, var1);

        if (var2 < 0)
        {
            return null;
        }
        else
        {
            int var3 = var2 >> 16 & 255;
            int var4 = var2 >> 8 & 255;
            int var5 = var2 & 255;
            float var6 = (float)var3 / 255.0F;
            float var7 = (float)var4 / 255.0F;
            float var8 = (float)var5 / 255.0F;
            return Vec3.createVectorHelper((double)var6, (double)var7, (double)var8);
        }
    }

    private static int[] getCustomColors(String var0, RenderEngine var1, int var2)
    {
        try
        {
            InputStream var3 = var1.getTexturePack().getSelectedTexturePack().getResourceAsStream(var0);

            if (var3 == null)
            {
                return null;
            }
            else
            {
                int[] var4 = var1.getTextureContents(var0);

                if (var4 == null)
                {
                    return null;
                }
                else if (var2 > 0 && var4.length != var2)
                {
                    Config.log("Invalid custom colors length: " + var4.length + ", path: " + var0);
                    return null;
                }
                else
                {
                    Config.log("Loading custom colors: " + var0);
                    return var4;
                }
            }
        }
        catch (FileNotFoundException var5)
        {
            return null;
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
            return null;
        }
    }

    public static void updateUseDefaultColorMultiplier()
    {
        useDefaultColorMultiplier = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes();
    }

    public static int getColorMultiplier(Block var0, IBlockAccess var1, int var2, int var3, int var4)
    {
        if (useDefaultColorMultiplier)
        {
            return var0.colorMultiplier(var1, var2, var3, var4);
        }
        else
        {
            int[] var5 = null;
            int[] var6 = null;
            int var10;

            if (blockPalettes != null)
            {
                int var7 = var0.blockID;

                if (var7 >= 0 && var7 < 256)
                {
                    int[] var8 = blockPalettes[var7];
                    boolean var9 = true;
                    int var13;

                    if (var8.length > 1)
                    {
                        var10 = var1.getBlockMetadata(var2, var3, var4);
                        var13 = var8[var10];
                    }
                    else
                    {
                        var13 = var8[0];
                    }

                    if (var13 >= 0)
                    {
                        var5 = paletteColors[var13];
                    }
                }

                if (var5 != null)
                {
                    if (Config.isSmoothBiomes())
                    {
                        return getSmoothColorMultiplier(var0, var1, var2, var3, var4, var5, var5, 0, 0);
                    }

                    return getCustomColor(var5, var1, var2, var3, var4);
                }
            }

            boolean var11 = Config.isSwampColors();
            boolean var12 = false;
            byte var14 = 0;
            var10 = 0;

            if (var0 != Block.grass && var0 != Block.tallGrass)
            {
                if (var0 == Block.leaves)
                {
                    var14 = 2;
                    var12 = Config.isSmoothBiomes();
                    var10 = var1.getBlockMetadata(var2, var3, var4);

                    if ((var10 & 3) == 1)
                    {
                        var5 = foliagePineColors;
                    }
                    else if ((var10 & 3) == 2)
                    {
                        var5 = foliageBirchColors;
                    }
                    else
                    {
                        var5 = foliageColors;

                        if (var11)
                        {
                            var6 = swampFoliageColors;
                        }
                        else
                        {
                            var6 = var5;
                        }
                    }
                }
                else if (var0 == Block.vine)
                {
                    var14 = 2;
                    var12 = Config.isSmoothBiomes();
                    var5 = foliageColors;

                    if (var11)
                    {
                        var6 = swampFoliageColors;
                    }
                    else
                    {
                        var6 = var5;
                    }
                }
            }
            else
            {
                var14 = 1;
                var12 = Config.isSmoothBiomes();
                var5 = grassColors;

                if (var11)
                {
                    var6 = swampGrassColors;
                }
                else
                {
                    var6 = var5;
                }
            }

            if (var12)
            {
                return getSmoothColorMultiplier(var0, var1, var2, var3, var4, var5, var6, var14, var10);
            }
            else
            {
                if (var6 != var5 && var1.getBiomeGenForCoords(var2, var4) == BiomeGenBase.swampland)
                {
                    var5 = var6;
                }

                return var5 != null ? getCustomColor(var5, var1, var2, var3, var4) : var0.colorMultiplier(var1, var2, var3, var4);
            }
        }
    }

    private static int getSmoothColorMultiplier(Block var0, IBlockAccess var1, int var2, int var3, int var4, int[] var5, int[] var6, int var7, int var8)
    {
        int var9 = 0;
        int var10 = 0;
        int var11 = 0;
        int var12;
        int var13;

        for (var12 = var2 - 1; var12 <= var2 + 1; ++var12)
        {
            for (var13 = var4 - 1; var13 <= var4 + 1; ++var13)
            {
                int[] var14 = var5;

                if (var6 != var5 && var1.getBiomeGenForCoords(var12, var13) == BiomeGenBase.swampland)
                {
                    var14 = var6;
                }

                boolean var15 = false;
                int var17;

                if (var14 == null)
                {
                    switch (var7)
                    {
                        case 1:
                            var17 = var1.getBiomeGenForCoords(var12, var13).getBiomeGrassColor();
                            break;

                        case 2:
                            if ((var8 & 3) == 1)
                            {
                                var17 = ColorizerFoliage.getFoliageColorPine();
                            }
                            else if ((var8 & 3) == 2)
                            {
                                var17 = ColorizerFoliage.getFoliageColorBirch();
                            }
                            else
                            {
                                var17 = var1.getBiomeGenForCoords(var12, var13).getBiomeFoliageColor();
                            }

                            break;

                        default:
                            var17 = var0.colorMultiplier(var1, var12, var3, var13);
                    }
                }
                else
                {
                    var17 = getCustomColor(var14, var1, var12, var3, var13);
                }

                var9 += var17 >> 16 & 255;
                var10 += var17 >> 8 & 255;
                var11 += var17 & 255;
            }
        }

        var12 = var9 / 9;
        var13 = var10 / 9;
        int var16 = var11 / 9;
        return var12 << 16 | var13 << 8 | var16;
    }

    public static int getFluidColor(Block var0, IBlockAccess var1, int var2, int var3, int var4)
    {
        return var0.blockMaterial != Material.water ? var0.colorMultiplier(var1, var2, var3, var4) : (waterColors != null ? (Config.isSmoothBiomes() ? getSmoothColor(waterColors, var1, (double)var2, (double)var3, (double)var4, 3, 1) : getCustomColor(waterColors, var1, var2, var3, var4)) : (!Config.isSwampColors() ? 16777215 : var0.colorMultiplier(var1, var2, var3, var4)));
    }

    private static int getCustomColor(int[] var0, IBlockAccess var1, int var2, int var3, int var4)
    {
        BiomeGenBase var5 = var1.getBiomeGenForCoords(var2, var4);
        double var6 = (double)MathHelper.clamp_float(var5.getFloatTemperature(), 0.0F, 1.0F);
        double var8 = (double)MathHelper.clamp_float(var5.getFloatRainfall(), 0.0F, 1.0F);
        var8 *= var6;
        int var10 = (int)((1.0D - var6) * 255.0D);
        int var11 = (int)((1.0D - var8) * 255.0D);
        return var0[var11 << 8 | var10] & 16777215;
    }

    public static void updatePortalFX(EntityFX var0)
    {
        if (particlePortalColor >= 0)
        {
            int var1 = particlePortalColor;
            int var2 = var1 >> 16 & 255;
            int var3 = var1 >> 8 & 255;
            int var4 = var1 & 255;
            float var5 = (float)var2 / 255.0F;
            float var6 = (float)var3 / 255.0F;
            float var7 = (float)var4 / 255.0F;
            var0.particleRed = var5;
            var0.particleGreen = var6;
            var0.particleBlue = var7;
        }
    }

    public static void updateMyceliumFX(EntityFX var0)
    {
        if (myceliumParticleColors != null)
        {
            int var1 = myceliumParticleColors[random.nextInt(myceliumParticleColors.length)];
            int var2 = var1 >> 16 & 255;
            int var3 = var1 >> 8 & 255;
            int var4 = var1 & 255;
            float var5 = (float)var2 / 255.0F;
            float var6 = (float)var3 / 255.0F;
            float var7 = (float)var4 / 255.0F;
            var0.particleRed = var5;
            var0.particleGreen = var6;
            var0.particleBlue = var7;
        }
    }

    public static void updateReddustFX(EntityFX var0, IBlockAccess var1, double var2, double var4, double var6)
    {
        if (redstoneColors != null)
        {
            int var8 = var1.getBlockMetadata((int)var2, (int)var4, (int)var6);
            int var9 = getRedstoneColor(var8);

            if (var9 != -1)
            {
                int var10 = var9 >> 16 & 255;
                int var11 = var9 >> 8 & 255;
                int var12 = var9 & 255;
                float var13 = (float)var10 / 255.0F;
                float var14 = (float)var11 / 255.0F;
                float var15 = (float)var12 / 255.0F;
                var0.particleRed = var13;
                var0.particleGreen = var14;
                var0.particleBlue = var15;
            }
        }
    }

    public static int getRedstoneColor(int var0)
    {
        return redstoneColors == null ? -1 : (var0 >= 0 && var0 <= 15 ? redstoneColors[var0] & 16777215 : -1);
    }

    public static void updateWaterFX(EntityFX var0, IBlockAccess var1)
    {
        if (waterColors != null)
        {
            int var2 = (int)var0.posX;
            int var3 = (int)var0.posY;
            int var4 = (int)var0.posZ;
            int var5 = getFluidColor(Block.waterStill, var1, var2, var3, var4);
            int var6 = var5 >> 16 & 255;
            int var7 = var5 >> 8 & 255;
            int var8 = var5 & 255;
            float var9 = (float)var6 / 255.0F;
            float var10 = (float)var7 / 255.0F;
            float var11 = (float)var8 / 255.0F;

            if (particleWaterColor >= 0)
            {
                int var12 = particleWaterColor >> 16 & 255;
                int var13 = particleWaterColor >> 8 & 255;
                int var14 = particleWaterColor & 255;
                var9 *= (float)var12 / 255.0F;
                var10 *= (float)var13 / 255.0F;
                var11 *= (float)var14 / 255.0F;
            }

            var0.particleRed = var9;
            var0.particleGreen = var10;
            var0.particleBlue = var11;
        }
    }

    public static int getLilypadColor()
    {
        return lilyPadColor < 0 ? Block.waterlily.getBlockColor() : lilyPadColor;
    }

    public static Vec3 getFogColorNether(Vec3 var0)
    {
        return fogColorNether == null ? var0 : fogColorNether;
    }

    public static Vec3 getFogColorEnd(Vec3 var0)
    {
        return fogColorEnd == null ? var0 : fogColorEnd;
    }

    public static Vec3 getSkyColorEnd(Vec3 var0)
    {
        return skyColorEnd == null ? var0 : skyColorEnd;
    }

    public static Vec3 getSkyColor(Vec3 var0, IBlockAccess var1, double var2, double var4, double var6)
    {
        if (skyColors == null)
        {
            return var0;
        }
        else
        {
            int var8 = getSmoothColor(skyColors, var1, var2, var4, var6, 10, 1);
            int var9 = var8 >> 16 & 255;
            int var10 = var8 >> 8 & 255;
            int var11 = var8 & 255;
            float var12 = (float)var9 / 255.0F;
            float var13 = (float)var10 / 255.0F;
            float var14 = (float)var11 / 255.0F;
            float var15 = (float)var0.xCoord / 0.5F;
            float var16 = (float)var0.yCoord / 0.66275F;
            float var17 = (float)var0.zCoord;
            var12 *= var15;
            var13 *= var16;
            var14 *= var17;
            return Vec3.createVectorHelper((double)var12, (double)var13, (double)var14);
        }
    }

    public static Vec3 getFogColor(Vec3 var0, IBlockAccess var1, double var2, double var4, double var6)
    {
        if (fogColors == null)
        {
            return var0;
        }
        else
        {
            int var8 = getSmoothColor(fogColors, var1, var2, var4, var6, 10, 1);
            int var9 = var8 >> 16 & 255;
            int var10 = var8 >> 8 & 255;
            int var11 = var8 & 255;
            float var12 = (float)var9 / 255.0F;
            float var13 = (float)var10 / 255.0F;
            float var14 = (float)var11 / 255.0F;
            float var15 = (float)var0.xCoord / 0.753F;
            float var16 = (float)var0.yCoord / 0.8471F;
            float var17 = (float)var0.zCoord;
            var12 *= var15;
            var13 *= var16;
            var14 *= var17;
            return Vec3.createVectorHelper((double)var12, (double)var13, (double)var14);
        }
    }

    public static Vec3 getUnderwaterColor(IBlockAccess var0, double var1, double var3, double var5)
    {
        if (underwaterColors == null)
        {
            return null;
        }
        else
        {
            int var7 = getSmoothColor(underwaterColors, var0, var1, var3, var5, 10, 1);
            int var8 = var7 >> 16 & 255;
            int var9 = var7 >> 8 & 255;
            int var10 = var7 & 255;
            float var11 = (float)var8 / 255.0F;
            float var12 = (float)var9 / 255.0F;
            float var13 = (float)var10 / 255.0F;
            return Vec3.createVectorHelper((double)var11, (double)var12, (double)var13);
        }
    }

    public static int getSmoothColor(int[] var0, IBlockAccess var1, double var2, double var4, double var6, int var8, int var9)
    {
        if (var0 == null)
        {
            return -1;
        }
        else
        {
            int var10 = (int)Math.floor(var2);
            int var11 = (int)Math.floor(var4);
            int var12 = (int)Math.floor(var6);
            int var13 = var8 * var9 / 2;
            int var14 = 0;
            int var15 = 0;
            int var16 = 0;
            int var17 = 0;
            int var19;
            int var18;
            int var20;

            for (var18 = var10 - var13; var18 <= var10 + var13; var18 += var9)
            {
                for (var19 = var12 - var13; var19 <= var12 + var13; var19 += var9)
                {
                    var20 = getCustomColor(var0, var1, var18, var11, var19);
                    var14 += var20 >> 16 & 255;
                    var15 += var20 >> 8 & 255;
                    var16 += var20 & 255;
                    ++var17;
                }
            }

            var18 = var14 / var17;
            var19 = var15 / var17;
            var20 = var16 / var17;
            return var18 << 16 | var19 << 8 | var20;
        }
    }

    public static int mixColors(int var0, int var1, float var2)
    {
        if (var2 <= 0.0F)
        {
            return var1;
        }
        else if (var2 >= 1.0F)
        {
            return var0;
        }
        else
        {
            float var3 = 1.0F - var2;
            int var4 = var0 >> 16 & 255;
            int var5 = var0 >> 8 & 255;
            int var6 = var0 & 255;
            int var7 = var1 >> 16 & 255;
            int var8 = var1 >> 8 & 255;
            int var9 = var1 & 255;
            int var10 = (int)((float)var4 * var2 + (float)var7 * var3);
            int var11 = (int)((float)var5 * var2 + (float)var8 * var3);
            int var12 = (int)((float)var6 * var2 + (float)var9 * var3);
            return var10 << 16 | var11 << 8 | var12;
        }
    }

    private static int averageColor(int var0, int var1)
    {
        int var2 = var0 >> 16 & 255;
        int var3 = var0 >> 8 & 255;
        int var4 = var0 & 255;
        int var5 = var1 >> 16 & 255;
        int var6 = var1 >> 8 & 255;
        int var7 = var1 & 255;
        int var8 = (var2 + var5) / 2;
        int var9 = (var3 + var6) / 2;
        int var10 = (var4 + var7) / 2;
        return var8 << 16 | var9 << 8 | var10;
    }

    public static int getStemColorMultiplier(BlockStem var0, IBlockAccess var1, int var2, int var3, int var4)
    {
        if (stemColors == null)
        {
            return var0.colorMultiplier(var1, var2, var3, var4);
        }
        else
        {
            int var5 = var1.getBlockMetadata(var2, var3, var4);

            if (var5 < 0)
            {
                var5 = 0;
            }

            if (var5 >= stemColors.length)
            {
                var5 = stemColors.length - 1;
            }

            return stemColors[var5];
        }
    }

    public static boolean updateLightmap(World var0, EntityRenderer var1, int[] var2, boolean var3)
    {
        if (var0 == null)
        {
            return false;
        }
        else if (lightMapsColorsRgb == null)
        {
            return false;
        }
        else if (!Config.isCustomColors())
        {
            return false;
        }
        else
        {
            int var4 = var0.provider.dimensionId;

            if (var4 >= -1 && var4 <= 1)
            {
                int var5 = var4 + 1;
                float[][] var6 = lightMapsColorsRgb[var5];

                if (var6 == null)
                {
                    return false;
                }
                else
                {
                    int var7 = lightMapsHeight[var5];

                    if (var3 && var7 < 64)
                    {
                        return false;
                    }
                    else
                    {
                        int var8 = var6.length / var7;

                        if (var8 < 16)
                        {
                            Config.dbg("Invalid lightmap width: " + var8 + " for: /environment/lightmap" + var4 + ".png");
                            lightMapsColorsRgb[var5] = (float[][])null;
                            return false;
                        }
                        else
                        {
                            int var9 = 0;

                            if (var3)
                            {
                                var9 = var8 * 16 * 2;
                            }

                            float var10 = 1.1666666F * (var0.getSunBrightness(1.0F) - 0.2F);

                            if (var0.lastLightningBolt > 0)
                            {
                                var10 = 1.0F;
                            }

                            var10 = Config.limitTo1(var10);
                            float var11 = var10 * (float)(var8 - 1);
                            float var12 = Config.limitTo1(var1.torchFlickerX + 0.5F) * (float)(var8 - 1);
                            float var13 = Config.limitTo1(Config.getGameSettings().gammaSetting);
                            boolean var14 = var13 > 1.0E-4F;
                            getLightMapColumn(var6, var11, var9, var8, sunRgbs);
                            getLightMapColumn(var6, var12, var9 + 16 * var8, var8, torchRgbs);
                            float[] var15 = new float[3];

                            for (int var16 = 0; var16 < 16; ++var16)
                            {
                                for (int var17 = 0; var17 < 16; ++var17)
                                {
                                    int var18;

                                    for (var18 = 0; var18 < 3; ++var18)
                                    {
                                        float var19 = Config.limitTo1(sunRgbs[var16][var18] + torchRgbs[var17][var18]);

                                        if (var14)
                                        {
                                            float var20 = 1.0F - var19;
                                            var20 = 1.0F - var20 * var20 * var20 * var20;
                                            var19 = var13 * var20 + (1.0F - var13) * var19;
                                        }

                                        var15[var18] = var19;
                                    }

                                    var18 = (int)(var15[0] * 255.0F);
                                    int var22 = (int)(var15[1] * 255.0F);
                                    int var21 = (int)(var15[2] * 255.0F);
                                    var2[var16 * 16 + var17] = -16777216 | var18 << 16 | var22 << 8 | var21;
                                }
                            }

                            return true;
                        }
                    }
                }
            }
            else
            {
                return false;
            }
        }
    }

    private static void getLightMapColumn(float[][] var0, float var1, int var2, int var3, float[][] var4)
    {
        int var5 = (int)Math.floor((double)var1);
        int var6 = (int)Math.ceil((double)var1);

        if (var5 == var6)
        {
            for (int var14 = 0; var14 < 16; ++var14)
            {
                float[] var15 = var0[var2 + var14 * var3 + var5];
                float[] var16 = var4[var14];

                for (int var17 = 0; var17 < 3; ++var17)
                {
                    var16[var17] = var15[var17];
                }
            }
        }
        else
        {
            float var7 = 1.0F - (var1 - (float)var5);
            float var8 = 1.0F - ((float)var6 - var1);

            for (int var9 = 0; var9 < 16; ++var9)
            {
                float[] var10 = var0[var2 + var9 * var3 + var5];
                float[] var11 = var0[var2 + var9 * var3 + var6];
                float[] var12 = var4[var9];

                for (int var13 = 0; var13 < 3; ++var13)
                {
                    var12[var13] = var10[var13] * var7 + var11[var13] * var8;
                }
            }
        }
    }
}
