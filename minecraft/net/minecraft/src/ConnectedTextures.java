package net.minecraft.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ConnectedTextures
{
    private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
    private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])null;
    private static boolean multipass = false;
    private static boolean defaultGlassTexture = false;
    private static final int BOTTOM = 0;
    private static final int TOP = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int NORTH = 4;
    private static final int SOUTH = 5;
    private static final String[] propSuffixes = new String[] {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final int[] ctmIndexes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0};

    public static void update(RenderEngine var0) {}

    private static ConnectedProperties[][] readConnectedProperties(String var0, int var1, RenderEngine var2, int var3)
    {
        return (ConnectedProperties[][])null;
    }

    public static Icon getConnectedTexture(IBlockAccess var0, Block var1, int var2, int var3, int var4, int var5, Icon var6)
    {
        if (var0 == null)
        {
            return var6;
        }
        else
        {
            Icon var7 = getConnectedTextureSingle(var0, var1, var2, var3, var4, var5, var6, true);

            if (!multipass)
            {
                return var7;
            }
            else if (var7 == var6)
            {
                return var7;
            }
            else
            {
                Icon var8 = var7;

                for (int var9 = 0; var9 < 3; ++var9)
                {
                    Icon var10 = getConnectedTextureSingle(var0, var1, var2, var3, var4, var5, var8, false);

                    if (var10 == var8)
                    {
                        break;
                    }

                    var8 = var10;
                }

                return var8;
            }
        }
    }

    public static Icon getConnectedTextureSingle(IBlockAccess var0, Block var1, int var2, int var3, int var4, int var5, Icon var6, boolean var7)
    {
        if (!(var6 instanceof TextureStitched))
        {
            return var6;
        }
        else
        {
            TextureStitched var8 = (TextureStitched)var6;
            int var9 = var8.getIndexInMap();
            int var10 = -1;

            if (tileProperties != null && Tessellator.instance.defaultTexture && var9 >= 0 && var9 < tileProperties.length)
            {
                ConnectedProperties[] var11 = tileProperties[var9];

                if (var11 != null)
                {
                    if (var10 < 0)
                    {
                        var10 = var0.getBlockMetadata(var2, var3, var4);
                    }

                    Icon var12 = getConnectedTexture(var11, var0, var1, var2, var3, var4, var5, var8, var10);

                    if (var12 != null)
                    {
                        return var12;
                    }
                }
            }

            if (blockProperties != null && var7)
            {
                int var14 = var1.blockID;

                if (var14 >= 0 && var14 < blockProperties.length)
                {
                    ConnectedProperties[] var15 = blockProperties[var14];

                    if (var15 != null)
                    {
                        if (var10 < 0)
                        {
                            var10 = var0.getBlockMetadata(var2, var3, var4);
                        }

                        Icon var13 = getConnectedTexture(var15, var0, var1, var2, var3, var4, var5, var8, var10);

                        if (var13 != null)
                        {
                            return var13;
                        }
                    }
                }
            }

            return var6;
        }
    }

    public static ConnectedProperties getConnectedProperties(IBlockAccess var0, Block var1, int var2, int var3, int var4, int var5, Icon var6)
    {
        if (var0 == null)
        {
            return null;
        }
        else if (!(var6 instanceof TextureStitched))
        {
            return null;
        }
        else
        {
            TextureStitched var7 = (TextureStitched)var6;
            int var8 = var7.getIndexInMap();
            int var9 = -1;

            if (tileProperties != null && Tessellator.instance.defaultTexture && var8 >= 0 && var8 < tileProperties.length)
            {
                ConnectedProperties[] var10 = tileProperties[var8];

                if (var10 != null)
                {
                    if (var9 < 0)
                    {
                        var9 = var0.getBlockMetadata(var2, var3, var4);
                    }

                    ConnectedProperties var11 = getConnectedProperties(var10, var0, var1, var2, var3, var4, var5, var7, var9);

                    if (var11 != null)
                    {
                        return var11;
                    }
                }
            }

            if (blockProperties != null)
            {
                int var14 = var1.blockID;

                if (var14 >= 0 && var14 < blockProperties.length)
                {
                    ConnectedProperties[] var13 = blockProperties[var14];

                    if (var13 != null)
                    {
                        if (var9 < 0)
                        {
                            var9 = var0.getBlockMetadata(var2, var3, var4);
                        }

                        ConnectedProperties var12 = getConnectedProperties(var13, var0, var1, var2, var3, var4, var5, var7, var9);

                        if (var12 != null)
                        {
                            return var12;
                        }
                    }
                }
            }

            return null;
        }
    }

    private static Icon getConnectedTexture(ConnectedProperties[] var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        for (int var9 = 0; var9 < var0.length; ++var9)
        {
            ConnectedProperties var10 = var0[var9];

            if (var10 != null)
            {
                Icon var11 = getConnectedTexture(var10, var1, var2, var3, var4, var5, var6, var7, var8);

                if (var11 != null)
                {
                    return var11;
                }
            }
        }

        return null;
    }

    private static ConnectedProperties getConnectedProperties(ConnectedProperties[] var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        for (int var9 = 0; var9 < var0.length; ++var9)
        {
            ConnectedProperties var10 = var0[var9];

            if (var10 != null)
            {
                Icon var11 = getConnectedTexture(var10, var1, var2, var3, var4, var5, var6, var7, var8);

                if (var11 != null)
                {
                    return var10;
                }
            }
        }

        return null;
    }

    private static Icon getConnectedTexture(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        if (var4 >= var0.minHeight && var4 <= var0.maxHeight)
        {
            if (var0.biomes != null)
            {
                BiomeGenBase var9 = var1.getBiomeGenForCoords(var3, var5);
                boolean var10 = false;

                for (int var11 = 0; var11 < var0.biomes.length; ++var11)
                {
                    BiomeGenBase var12 = var0.biomes[var11];

                    if (var9 == var12)
                    {
                        var10 = true;
                        break;
                    }
                }

                if (!var10)
                {
                    return null;
                }
            }

            boolean var14 = var2 instanceof BlockLog;
            int var15;

            if (var6 >= 0 && var0.faces != 63)
            {
                var15 = var6;

                if (var14)
                {
                    var15 = fixWoodSide(var1, var3, var4, var5, var6, var8);
                }

                if ((1 << var15 & var0.faces) == 0)
                {
                    return null;
                }
            }

            var15 = var8;

            if (var14)
            {
                var15 = var8 & 3;
            }

            if (var0.metadatas != null)
            {
                int[] var17 = var0.metadatas;
                boolean var16 = false;

                for (int var13 = 0; var13 < var17.length; ++var13)
                {
                    if (var17[var13] == var15)
                    {
                        var16 = true;
                        break;
                    }
                }

                if (!var16)
                {
                    return null;
                }
            }

            switch (var0.method)
            {
                case 1:
                    return getConnectedTextureCtm(var0, var1, var2, var3, var4, var5, var6, var7, var8);

                case 2:
                    return getConnectedTextureHorizontal(var0, var1, var2, var3, var4, var5, var6, var7, var8);

                case 3:
                    return getConnectedTextureTop(var0, var1, var2, var3, var4, var5, var6, var7, var8);

                case 4:
                    return getConnectedTextureRandom(var0, var3, var4, var5, var6);

                case 5:
                    return getConnectedTextureRepeat(var0, var3, var4, var5, var6);

                case 6:
                    return getConnectedTextureVertical(var0, var1, var2, var3, var4, var5, var6, var7, var8);

                case 7:
                    return getConnectedTextureFixed(var0);

                default:
                    return null;
            }
        }
        else
        {
            return null;
        }
    }

    private static int fixWoodSide(IBlockAccess var0, int var1, int var2, int var3, int var4, int var5)
    {
        int var6 = (var5 & 12) >> 2;

        switch (var6)
        {
            case 0:
                return var4;

            case 1:
                switch (var4)
                {
                    case 0:
                        return 4;

                    case 1:
                        return 5;

                    case 2:
                    case 3:
                    default:
                        return var4;

                    case 4:
                        return 1;

                    case 5:
                        return 0;
                }

            case 2:
                switch (var4)
                {
                    case 0:
                        return 2;

                    case 1:
                        return 3;

                    case 2:
                        return 1;

                    case 3:
                        return 0;

                    default:
                        return var4;
                }

            case 3:
                return 2;

            default:
                return var4;
        }
    }

    private static Icon getConnectedTextureRandom(ConnectedProperties var0, int var1, int var2, int var3, int var4)
    {
        if (var0.tileIcons.length == 1)
        {
            return var0.tileIcons[0];
        }
        else
        {
            int var5 = var4 / var0.symmetry * var0.symmetry;
            int var6 = Config.getRandom(var1, var2, var3, var5) & Integer.MAX_VALUE;
            int var7 = 0;

            if (var0.weights == null)
            {
                var7 = var6 % var0.tileIcons.length;
            }
            else
            {
                int var8 = var6 % var0.sumAllWeights;
                int[] var9 = var0.sumWeights;

                for (int var10 = 0; var10 < var9.length; ++var10)
                {
                    if (var8 < var9[var10])
                    {
                        var7 = var10;
                        break;
                    }
                }
            }

            return var0.tileIcons[var7];
        }
    }

    private static Icon getConnectedTextureFixed(ConnectedProperties var0)
    {
        return var0.tileIcons[0];
    }

    private static Icon getConnectedTextureRepeat(ConnectedProperties var0, int var1, int var2, int var3, int var4)
    {
        if (var0.tileIcons.length == 1)
        {
            return var0.tileIcons[0];
        }
        else
        {
            int var5 = 0;
            int var6 = 0;

            switch (var4)
            {
                case 0:
                    var5 = var1;
                    var6 = var3;
                    break;

                case 1:
                    var5 = var1;
                    var6 = var3;
                    break;

                case 2:
                    var5 = -var1 - 1;
                    var6 = -var2;
                    break;

                case 3:
                    var5 = var1;
                    var6 = -var2;
                    break;

                case 4:
                    var5 = var3;
                    var6 = -var2;
                    break;

                case 5:
                    var5 = -var3 - 1;
                    var6 = -var2;
            }

            var5 %= var0.width;
            var6 %= var0.height;

            if (var5 < 0)
            {
                var5 += var0.width;
            }

            if (var6 < 0)
            {
                var6 += var0.height;
            }

            int var7 = var6 * var0.width + var5;
            return var0.tileIcons[var7];
        }
    }

    private static Icon getConnectedTextureCtm(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        boolean[] var9 = new boolean[6];

        switch (var6)
        {
            case 0:
            case 1:
                var9[0] = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                break;

            case 2:
                var9[0] = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;

            case 3:
                var9[0] = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;

            case 4:
                var9[0] = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;

            case 5:
                var9[0] = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
        }

        byte var10 = 0;

        if (var9[0] & !var9[1] & !var9[2] & !var9[3])
        {
            var10 = 3;
        }
        else if (!var9[0] & var9[1] & !var9[2] & !var9[3])
        {
            var10 = 1;
        }
        else if (!var9[0] & !var9[1] & var9[2] & !var9[3])
        {
            var10 = 12;
        }
        else if (!var9[0] & !var9[1] & !var9[2] & var9[3])
        {
            var10 = 36;
        }
        else if (var9[0] & var9[1] & !var9[2] & !var9[3])
        {
            var10 = 2;
        }
        else if (!var9[0] & !var9[1] & var9[2] & var9[3])
        {
            var10 = 24;
        }
        else if (var9[0] & !var9[1] & var9[2] & !var9[3])
        {
            var10 = 15;
        }
        else if (var9[0] & !var9[1] & !var9[2] & var9[3])
        {
            var10 = 39;
        }
        else if (!var9[0] & var9[1] & var9[2] & !var9[3])
        {
            var10 = 13;
        }
        else if (!var9[0] & var9[1] & !var9[2] & var9[3])
        {
            var10 = 37;
        }
        else if (!var9[0] & var9[1] & var9[2] & var9[3])
        {
            var10 = 25;
        }
        else if (var9[0] & !var9[1] & var9[2] & var9[3])
        {
            var10 = 27;
        }
        else if (var9[0] & var9[1] & !var9[2] & var9[3])
        {
            var10 = 38;
        }
        else if (var9[0] & var9[1] & var9[2] & !var9[3])
        {
            var10 = 14;
        }
        else if (var9[0] & var9[1] & var9[2] & var9[3])
        {
            var10 = 26;
        }

        if (!Config.isConnectedTexturesFancy())
        {
            return var0.tileIcons[var10];
        }
        else
        {
            boolean[] var11 = new boolean[6];

            switch (var6)
            {
                case 0:
                case 1:
                    var11[0] = !isNeighbour(var0, var1, var2, var3 + 1, var4, var5 + 1, var6, var7, var8);
                    var11[1] = !isNeighbour(var0, var1, var2, var3 - 1, var4, var5 + 1, var6, var7, var8);
                    var11[2] = !isNeighbour(var0, var1, var2, var3 + 1, var4, var5 - 1, var6, var7, var8);
                    var11[3] = !isNeighbour(var0, var1, var2, var3 - 1, var4, var5 - 1, var6, var7, var8);
                    break;

                case 2:
                    var11[0] = !isNeighbour(var0, var1, var2, var3 - 1, var4 - 1, var5, var6, var7, var8);
                    var11[1] = !isNeighbour(var0, var1, var2, var3 + 1, var4 - 1, var5, var6, var7, var8);
                    var11[2] = !isNeighbour(var0, var1, var2, var3 - 1, var4 + 1, var5, var6, var7, var8);
                    var11[3] = !isNeighbour(var0, var1, var2, var3 + 1, var4 + 1, var5, var6, var7, var8);
                    break;

                case 3:
                    var11[0] = !isNeighbour(var0, var1, var2, var3 + 1, var4 - 1, var5, var6, var7, var8);
                    var11[1] = !isNeighbour(var0, var1, var2, var3 - 1, var4 - 1, var5, var6, var7, var8);
                    var11[2] = !isNeighbour(var0, var1, var2, var3 + 1, var4 + 1, var5, var6, var7, var8);
                    var11[3] = !isNeighbour(var0, var1, var2, var3 - 1, var4 + 1, var5, var6, var7, var8);
                    break;

                case 4:
                    var11[0] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 + 1, var6, var7, var8);
                    var11[1] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 - 1, var6, var7, var8);
                    var11[2] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 + 1, var6, var7, var8);
                    var11[3] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 - 1, var6, var7, var8);
                    break;

                case 5:
                    var11[0] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 - 1, var6, var7, var8);
                    var11[1] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 + 1, var6, var7, var8);
                    var11[2] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 - 1, var6, var7, var8);
                    var11[3] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 + 1, var6, var7, var8);
            }

            if (var10 == 13 && var11[0])
            {
                var10 = 4;
            }

            if (var10 == 15 && var11[1])
            {
                var10 = 5;
            }

            if (var10 == 37 && var11[2])
            {
                var10 = 16;
            }

            if (var10 == 39 && var11[3])
            {
                var10 = 17;
            }

            if (var10 == 14 && var11[0] && var11[1])
            {
                var10 = 7;
            }

            if (var10 == 25 && var11[0] && var11[2])
            {
                var10 = 6;
            }

            if (var10 == 27 && var11[3] && var11[1])
            {
                var10 = 19;
            }

            if (var10 == 38 && var11[3] && var11[2])
            {
                var10 = 18;
            }

            if (var10 == 14 && !var11[0] && var11[1])
            {
                var10 = 31;
            }

            if (var10 == 25 && var11[0] && !var11[2])
            {
                var10 = 30;
            }

            if (var10 == 27 && !var11[3] && var11[1])
            {
                var10 = 41;
            }

            if (var10 == 38 && var11[3] && !var11[2])
            {
                var10 = 40;
            }

            if (var10 == 14 && var11[0] && !var11[1])
            {
                var10 = 29;
            }

            if (var10 == 25 && !var11[0] && var11[2])
            {
                var10 = 28;
            }

            if (var10 == 27 && var11[3] && !var11[1])
            {
                var10 = 43;
            }

            if (var10 == 38 && !var11[3] && var11[2])
            {
                var10 = 42;
            }

            if (var10 == 26 && var11[0] && var11[1] && var11[2] && var11[3])
            {
                var10 = 46;
            }

            if (var10 == 26 && !var11[0] && var11[1] && var11[2] && var11[3])
            {
                var10 = 9;
            }

            if (var10 == 26 && var11[0] && !var11[1] && var11[2] && var11[3])
            {
                var10 = 21;
            }

            if (var10 == 26 && var11[0] && var11[1] && !var11[2] && var11[3])
            {
                var10 = 8;
            }

            if (var10 == 26 && var11[0] && var11[1] && var11[2] && !var11[3])
            {
                var10 = 20;
            }

            if (var10 == 26 && var11[0] && var11[1] && !var11[2] && !var11[3])
            {
                var10 = 11;
            }

            if (var10 == 26 && !var11[0] && !var11[1] && var11[2] && var11[3])
            {
                var10 = 22;
            }

            if (var10 == 26 && !var11[0] && var11[1] && !var11[2] && var11[3])
            {
                var10 = 23;
            }

            if (var10 == 26 && var11[0] && !var11[1] && var11[2] && !var11[3])
            {
                var10 = 10;
            }

            if (var10 == 26 && var11[0] && !var11[1] && !var11[2] && var11[3])
            {
                var10 = 34;
            }

            if (var10 == 26 && !var11[0] && var11[1] && var11[2] && !var11[3])
            {
                var10 = 35;
            }

            if (var10 == 26 && var11[0] && !var11[1] && !var11[2] && !var11[3])
            {
                var10 = 32;
            }

            if (var10 == 26 && !var11[0] && var11[1] && !var11[2] && !var11[3])
            {
                var10 = 33;
            }

            if (var10 == 26 && !var11[0] && !var11[1] && var11[2] && !var11[3])
            {
                var10 = 44;
            }

            if (var10 == 26 && !var11[0] && !var11[1] && !var11[2] && var11[3])
            {
                var10 = 45;
            }

            return var0.tileIcons[var10];
        }
    }

    private static boolean isNeighbour(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        int var9 = var1.getBlockId(var3, var4, var5);
        Block var10;

        if (var0.connect == 2)
        {
            var10 = Block.blocksList[var9];

            if (var10 == null)
            {
                return false;
            }
            else
            {
                Icon var11 = var10.getBlockTexture(var1, var3, var4, var5, var6);
                return var11 == var7;
            }
        }
        else if (var0.connect == 3)
        {
            var10 = Block.blocksList[var9];
            return var10 == null ? false : var10.blockMaterial == var2.blockMaterial;
        }
        else
        {
            return var9 == var2.blockID && var1.getBlockMetadata(var3, var4, var5) == var8;
        }
    }

    private static Icon getConnectedTextureHorizontal(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        if (var6 != 0 && var6 != 1)
        {
            boolean var9 = false;
            boolean var10 = false;

            switch (var6)
            {
                case 2:
                    var9 = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                    break;

                case 3:
                    var9 = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                    break;

                case 4:
                    var9 = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                    break;

                case 5:
                    var9 = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
            }

            boolean var11 = true;
            byte var12;

            if (var9)
            {
                if (var10)
                {
                    var12 = 1;
                }
                else
                {
                    var12 = 2;
                }
            }
            else if (var10)
            {
                var12 = 0;
            }
            else
            {
                var12 = 3;
            }

            return var0.tileIcons[var12];
        }
        else
        {
            return null;
        }
    }

    private static Icon getConnectedTextureVertical(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        if (var6 != 0 && var6 != 1)
        {
            boolean var9 = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
            boolean var10 = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
            boolean var11 = true;
            byte var12;

            if (var9)
            {
                if (var10)
                {
                    var12 = 1;
                }
                else
                {
                    var12 = 2;
                }
            }
            else if (var10)
            {
                var12 = 0;
            }
            else
            {
                var12 = 3;
            }

            return var0.tileIcons[var12];
        }
        else
        {
            return null;
        }
    }

    private static Icon getConnectedTextureTop(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, Icon var7, int var8)
    {
        return var6 != 0 && var6 != 1 ? (isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8) ? var0.tileIcons[0] : null) : null;
    }

    public static boolean isConnectedGlassPanes()
    {
        return Config.isConnectedTextures() && defaultGlassTexture;
    }

    private static boolean getMatchingCtmPng(RenderEngine var0)
    {
        return false;
    }

    private static ConnectedProperties makeDefaultProperties(String var0, RenderEngine var1)
    {
        return null;
    }

    public static void updateIcons(TextureMap var0)
    {
        blockProperties = (ConnectedProperties[][])null;
        tileProperties = (ConnectedProperties[][])null;
        defaultGlassTexture = false;
        RenderEngine var1 = Config.getRenderEngine();

        if (var1 != null)
        {
            ITexturePack var2 = var1.getTexturePack().getSelectedTexturePack();

            if (var2 != null)
            {
                boolean var3 = var2.func_98138_b("/textures/blocks/glass.png", false);
                defaultGlassTexture = !var3;
                String[] var4 = collectFiles(var2, "ctm/", ".properties");
                Arrays.sort(var4);
                ArrayList var5 = new ArrayList();
                ArrayList var6 = new ArrayList();

                for (int var7 = 0; var7 < var4.length; ++var7)
                {
                    String var8 = var4[var7];
                    Config.dbg("ConnectedTextures: " + var8);

                    try
                    {
                        String var9 = "/" + var8;
                        InputStream var10 = var2.getResourceAsStream(var9);

                        if (var10 == null)
                        {
                            Config.dbg("ConnectedTextures file not found: " + var8);
                        }
                        else
                        {
                            Properties var11 = new Properties();
                            var11.load(var10);
                            ConnectedProperties var12 = new ConnectedProperties(var11, var8);

                            if (var12.isValid(var9))
                            {
                                var12.updateIcons(var0);
                                addToTileList(var12, var5);
                                addToBlockList(var12, var6);
                            }
                        }
                    }
                    catch (FileNotFoundException var13)
                    {
                        Config.dbg("ConnectedTextures file not found: " + var8);
                    }
                    catch (IOException var14)
                    {
                        var14.printStackTrace();
                    }
                }

                blockProperties = propertyListToArray(var6);
                tileProperties = propertyListToArray(var5);
                multipass = detectMultipass();
                Config.dbg("Multipass connected textures: " + multipass);
            }
        }
    }

    private static boolean detectMultipass()
    {
        ArrayList var0 = new ArrayList();
        int var1;
        ConnectedProperties[] var2;

        for (var1 = 0; var1 < tileProperties.length; ++var1)
        {
            var2 = tileProperties[var1];

            if (var2 != null)
            {
                var0.addAll(Arrays.asList(var2));
            }
        }

        for (var1 = 0; var1 < blockProperties.length; ++var1)
        {
            var2 = blockProperties[var1];

            if (var2 != null)
            {
                var0.addAll(Arrays.asList(var2));
            }
        }

        ConnectedProperties[] var6 = (ConnectedProperties[])((ConnectedProperties[])var0.toArray(new ConnectedProperties[var0.size()]));
        HashSet var7 = new HashSet();
        HashSet var3 = new HashSet();

        for (int var4 = 0; var4 < var6.length; ++var4)
        {
            ConnectedProperties var5 = var6[var4];

            if (var5.matchTileIcons != null)
            {
                var7.addAll(Arrays.asList(var5.matchTileIcons));
            }

            if (var5.tileIcons != null)
            {
                var3.addAll(Arrays.asList(var5.tileIcons));
            }
        }

        var7.retainAll(var3);
        return !var7.isEmpty();
    }

    private static ConnectedProperties[][] propertyListToArray(List var0)
    {
        ConnectedProperties[][] var1 = new ConnectedProperties[var0.size()][];

        for (int var2 = 0; var2 < var0.size(); ++var2)
        {
            List var3 = (List)var0.get(var2);

            if (var3 != null)
            {
                ConnectedProperties[] var4 = (ConnectedProperties[])((ConnectedProperties[])var3.toArray(new ConnectedProperties[var3.size()]));
                var1[var2] = var4;
            }
        }

        return var1;
    }

    private static void addToTileList(ConnectedProperties var0, List var1)
    {
        if (var0.matchTileIcons != null)
        {
            for (int var2 = 0; var2 < var0.matchTileIcons.length; ++var2)
            {
                Icon var3 = var0.matchTileIcons[var2];

                if (!(var3 instanceof TextureStitched))
                {
                    Config.dbg("Icon is not TextureStitched: " + var3 + ", name: " + var3.getIconName());
                }
                else
                {
                    TextureStitched var4 = (TextureStitched)var3;
                    int var5 = var4.getIndexInMap();

                    if (var5 < 0)
                    {
                        Config.dbg("Invalid tile ID: " + var5 + ", icon: " + var4.getIconName());
                    }
                    else
                    {
                        addToList(var0, var1, var5);
                    }
                }
            }
        }
    }

    private static void addToBlockList(ConnectedProperties var0, List var1)
    {
        if (var0.matchBlocks != null)
        {
            for (int var2 = 0; var2 < var0.matchBlocks.length; ++var2)
            {
                int var3 = var0.matchBlocks[var2];

                if (var3 < 0)
                {
                    Config.dbg("Invalid block ID: " + var3);
                }
                else
                {
                    addToList(var0, var1, var3);
                }
            }
        }
    }

    private static void addToList(ConnectedProperties var0, List var1, int var2)
    {
        while (var2 >= var1.size())
        {
            var1.add((Object)null);
        }

        Object var3 = (List)var1.get(var2);

        if (var3 == null)
        {
            var3 = new ArrayList();
            var1.set(var2, var3);
        }

        ((List)var3).add(var0);
    }

    private static String[] collectFiles(ITexturePack var0, String var1, String var2)
    {
        if (!(var0 instanceof TexturePackImplementation))
        {
            return new String[0];
        }
        else
        {
            TexturePackImplementation var3 = (TexturePackImplementation)var0;

            if (var3 instanceof TexturePackDefault)
            {
                return collectFilesDefault(var3);
            }
            else
            {
                File var4 = var3.texturePackFile;
                return var4 == null ? new String[0] : (var4.isDirectory() ? collectFilesFolder(var4, "", var1, var2) : (var4.isFile() ? collectFilesZIP(var4, var1, var2) : new String[0]));
            }
        }
    }

    private static String[] collectFilesDefault(TexturePackImplementation var0)
    {
        ArrayList var1 = new ArrayList();
        String[] var2 = new String[] {"ctm/default/bookshelf.properties", "ctm/default/glass.properties", "ctm/default/glasspane.properties", "ctm/default/sandstone.properties"};

        for (int var3 = 0; var3 < var2.length; ++var3)
        {
            String var4 = var2[var3];

            if (var0.func_98140_c("/" + var4))
            {
                var1.add(var4);
            }
        }

        String[] var5 = (String[])((String[])var1.toArray(new String[var1.size()]));
        return var5;
    }

    private static String[] collectFilesFolder(File var0, String var1, String var2, String var3)
    {
        ArrayList var4 = new ArrayList();
        File[] var5 = var0.listFiles();

        if (var5 == null)
        {
            return new String[0];
        }
        else
        {
            for (int var6 = 0; var6 < var5.length; ++var6)
            {
                File var7 = var5[var6];
                String var8;

                if (var7.isFile())
                {
                    var8 = var1 + var7.getName();

                    if (var8.startsWith(var2) && var8.endsWith(var3))
                    {
                        var4.add(var8);
                    }
                }
                else if (var7.isDirectory())
                {
                    var8 = var1 + var7.getName() + "/";
                    String[] var9 = collectFilesFolder(var7, var8, var2, var3);

                    for (int var10 = 0; var10 < var9.length; ++var10)
                    {
                        String var11 = var9[var10];
                        var4.add(var11);
                    }
                }
            }

            String[] var12 = (String[])((String[])var4.toArray(new String[var4.size()]));
            return var12;
        }
    }

    private static String[] collectFilesZIP(File var0, String var1, String var2)
    {
        ArrayList var3 = new ArrayList();

        try
        {
            ZipFile var4 = new ZipFile(var0);
            Enumeration var5 = var4.entries();

            while (var5.hasMoreElements())
            {
                ZipEntry var6 = (ZipEntry)var5.nextElement();
                String var7 = var6.getName();

                if (var7.startsWith(var1) && var7.endsWith(var2))
                {
                    var3.add(var7);
                }
            }

            var4.close();
            String[] var9 = (String[])((String[])var3.toArray(new String[var3.size()]));
            return var9;
        }
        catch (IOException var8)
        {
            var8.printStackTrace();
            return new String[0];
        }
    }

    public static int getPaneTextureIndex(boolean var0, boolean var1, boolean var2, boolean var3)
    {
        return var1 && var0 ? (var2 ? (var3 ? 34 : 50) : (var3 ? 18 : 2)) : (var1 && !var0 ? (var2 ? (var3 ? 35 : 51) : (var3 ? 19 : 3)) : (!var1 && var0 ? (var2 ? (var3 ? 33 : 49) : (var3 ? 17 : 1)) : (var2 ? (var3 ? 32 : 48) : (var3 ? 16 : 0))));
    }

    public static int getReversePaneTextureIndex(int var0)
    {
        int var1 = var0 % 16;
        return var1 == 1 ? var0 + 2 : (var1 == 3 ? var0 - 2 : var0);
    }

    public static Icon getCtmTexture(ConnectedProperties var0, int var1, Icon var2)
    {
        if (var0.method != 1)
        {
            return var2;
        }
        else if (var1 >= 0 && var1 < ctmIndexes.length)
        {
            int var3 = ctmIndexes[var1];
            Icon[] var4 = var0.tileIcons;
            return var3 >= 0 && var3 < var4.length ? var4[var3] : var2;
        }
        else
        {
            return var2;
        }
    }
}
