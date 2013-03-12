package net.minecraft.src;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectedTextures
{
    private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
    private static ConnectedProperties[][] terrainProperties = (ConnectedProperties[][])null;
    private static boolean matchingCtmPng = false;
    private static final int BOTTOM = 0;
    private static final int TOP = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int NORTH = 4;
    private static final int SOUTH = 5;
    private static final String[] propSuffixes = new String[] {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static void update(RenderEngine var0)
    {
        blockProperties = (ConnectedProperties[][])null;
        terrainProperties = (ConnectedProperties[][])null;
        matchingCtmPng = false;
        blockProperties = readConnectedProperties("/ctm/block", 256, var0, 1);
        terrainProperties = readConnectedProperties("/ctm/terrain", 256, var0, 2);
        matchingCtmPng = getMatchingCtmPng(var0);
        Config.dbg("MatchingCtmPng: " + matchingCtmPng);

        if (blockProperties == null && terrainProperties == null && matchingCtmPng)
        {
            Config.dbg("Registering default ConnectedTextures");
            blockProperties = new ConnectedProperties[256][];
            blockProperties[Block.glass.blockID] = new ConnectedProperties[1];
            blockProperties[Block.glass.blockID][0] = makeDefaultProperties("ctm", var0);
            blockProperties[Block.bookShelf.blockID] = new ConnectedProperties[1];
            blockProperties[Block.bookShelf.blockID][0] = makeDefaultProperties("horizontal", var0);
            terrainProperties = new ConnectedProperties[256][];
            terrainProperties[Block.sandStone.blockIndexInTexture] = new ConnectedProperties[1];
            terrainProperties[Block.sandStone.blockIndexInTexture][0] = makeDefaultProperties("top", var0);
        }
    }

    private static ConnectedProperties[][] readConnectedProperties(String var0, int var1, RenderEngine var2, int var3)
    {
        ConnectedProperties[][] var4 = (ConnectedProperties[][])null;
        int var5 = 0;

        while (var5 < var1)
        {
            ArrayList var6 = new ArrayList();
            int var7 = 0;

            while (true)
            {
                if (var7 < propSuffixes.length)
                {
                    String var8 = propSuffixes[var7];
                    String var9 = var0 + var5 + var8 + ".properties";
                    InputStream var10 = var2.texturePack.getSelectedTexturePack().getResourceAsStream(var9);

                    if (var10 != null)
                    {
                        try
                        {
                            Properties var11 = new Properties();
                            var11.load(var10);
                            Config.dbg("Connected texture: " + var9);
                            ConnectedProperties var12 = new ConnectedProperties(var11);

                            if (var12.connect == 0)
                            {
                                var12.connect = var3;
                            }

                            if (var12.isValid(var9))
                            {
                                TextureUtils.addAtlasName(var12.source);
                                var12.textureId = var2.getTexture(var12.source);
                                var6.add(var12);
                                var10.close();
                            }
                        }
                        catch (IOException var13)
                        {
                            var13.printStackTrace();
                        }

                        ++var7;
                        continue;
                    }
                }

                if (var6.size() > 0)
                {
                    if (var4 == null)
                    {
                        var4 = new ConnectedProperties[var1][0];
                    }

                    var4[var5] = (ConnectedProperties[])((ConnectedProperties[])var6.toArray(new ConnectedProperties[var6.size()]));
                }

                ++var5;
                break;
            }
        }

        return var4;
    }

    public static int getConnectedTexture(IBlockAccess var0, Block var1, int var2, int var3, int var4, int var5, int var6)
    {
        if (var0 == null)
        {
            return -1;
        }
        else
        {
            int var7 = -1;

            if (terrainProperties != null && Tessellator.instance.defaultTexture && var6 >= 0 && var6 < terrainProperties.length)
            {
                ConnectedProperties[] var8 = terrainProperties[var6];

                if (var8 != null)
                {
                    if (var7 < 0)
                    {
                        var7 = var0.getBlockMetadata(var2, var3, var4);
                    }

                    int var9 = getConnectedTexture(var8, var0, var1, var2, var3, var4, var5, var6, var7);

                    if (var9 >= 0)
                    {
                        return var9;
                    }
                }
            }

            if (blockProperties != null)
            {
                int var11 = var1.blockID;

                if (var11 >= 0 && var11 < blockProperties.length)
                {
                    ConnectedProperties[] var12 = blockProperties[var11];

                    if (var12 != null)
                    {
                        if (var7 < 0)
                        {
                            var7 = var0.getBlockMetadata(var2, var3, var4);
                        }

                        int var10 = getConnectedTexture(var12, var0, var1, var2, var3, var4, var5, var6, var7);

                        if (var10 >= 0)
                        {
                            return var10;
                        }
                    }
                }
            }

            return -1;
        }
    }

    private static int getConnectedTexture(ConnectedProperties[] var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        for (int var9 = 0; var9 < var0.length; ++var9)
        {
            ConnectedProperties var10 = var0[var9];

            if (var10 != null)
            {
                int var11 = getConnectedTexture(var10, var1, var2, var3, var4, var5, var6, var7, var8);

                if (var11 >= 0)
                {
                    return var11;
                }
            }
        }

        return -1;
    }

    private static int getConnectedTexture(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        boolean var9 = var2 instanceof BlockLog;
        int var10;

        if (var6 >= 0 && var0.faces != 63)
        {
            var10 = var6;

            if (var9)
            {
                var10 = fixWoodSide(var1, var3, var4, var5, var6, var8);
            }

            if ((1 << var10 & var0.faces) == 0)
            {
                return -1;
            }
        }

        var10 = var8;

        if (var9)
        {
            var10 = var8 & 3;
        }

        if (var0.metadatas != null)
        {
            int[] var11 = var0.metadatas;
            boolean var12 = false;

            for (int var13 = 0; var13 < var11.length; ++var13)
            {
                if (var11[var13] == var10)
                {
                    var12 = true;
                    break;
                }
            }

            if (!var12)
            {
                return -1;
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

            default:
                return -1;
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

    private static int getConnectedTextureRandom(ConnectedProperties var0, int var1, int var2, int var3, int var4)
    {
        if (var0.tiles.length == 1)
        {
            return var0.textureId * 256 + var0.tiles[0];
        }
        else
        {
            int var5 = var4 / var0.symmetry * var0.symmetry;
            int var6 = Config.getRandom(var1, var2, var3, var5) & Integer.MAX_VALUE;
            int var7 = 0;

            if (var0.weights == null)
            {
                var7 = var6 % var0.tiles.length;
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

            return var0.textureId * 256 + var0.tiles[var7];
        }
    }

    private static int getConnectedTextureRepeat(ConnectedProperties var0, int var1, int var2, int var3, int var4)
    {
        if (var0.tiles.length == 1)
        {
            return var0.textureId * 256 + var0.tiles[0];
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
            return var0.textureId * 256 + var0.tiles[var7];
        }
    }

    private static int getConnectedTextureCtm(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        boolean[] var9 = new boolean[6];
        int var10 = var2.blockID;

        switch (var6)
        {
            case 0:
            case 1:
                var9[0] = isNeighbour(var0, var1, var3 - 1, var4, var5, var10, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var3 + 1, var4, var5, var10, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var3, var4, var5 + 1, var10, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var3, var4, var5 - 1, var10, var6, var7, var8);
                break;

            case 2:
                var9[0] = isNeighbour(var0, var1, var3 + 1, var4, var5, var10, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var3 - 1, var4, var5, var10, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var3, var4 - 1, var5, var10, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var3, var4 + 1, var5, var10, var6, var7, var8);
                break;

            case 3:
                var9[0] = isNeighbour(var0, var1, var3 - 1, var4, var5, var10, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var3 + 1, var4, var5, var10, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var3, var4 - 1, var5, var10, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var3, var4 + 1, var5, var10, var6, var7, var8);
                break;

            case 4:
                var9[0] = isNeighbour(var0, var1, var3, var4, var5 - 1, var10, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var3, var4, var5 + 1, var10, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var3, var4 - 1, var5, var10, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var3, var4 + 1, var5, var10, var6, var7, var8);
                break;

            case 5:
                var9[0] = isNeighbour(var0, var1, var3, var4, var5 + 1, var10, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var3, var4, var5 - 1, var10, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var3, var4 - 1, var5, var10, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var3, var4 + 1, var5, var10, var6, var7, var8);
        }

        byte var11 = 0;

        if (var9[0] & !var9[1] & !var9[2] & !var9[3])
        {
            var11 = 3;
        }
        else if (!var9[0] & var9[1] & !var9[2] & !var9[3])
        {
            var11 = 1;
        }
        else if (!var9[0] & !var9[1] & var9[2] & !var9[3])
        {
            var11 = 12;
        }
        else if (!var9[0] & !var9[1] & !var9[2] & var9[3])
        {
            var11 = 36;
        }
        else if (var9[0] & var9[1] & !var9[2] & !var9[3])
        {
            var11 = 2;
        }
        else if (!var9[0] & !var9[1] & var9[2] & var9[3])
        {
            var11 = 24;
        }
        else if (var9[0] & !var9[1] & var9[2] & !var9[3])
        {
            var11 = 15;
        }
        else if (var9[0] & !var9[1] & !var9[2] & var9[3])
        {
            var11 = 39;
        }
        else if (!var9[0] & var9[1] & var9[2] & !var9[3])
        {
            var11 = 13;
        }
        else if (!var9[0] & var9[1] & !var9[2] & var9[3])
        {
            var11 = 37;
        }
        else if (!var9[0] & var9[1] & var9[2] & var9[3])
        {
            var11 = 25;
        }
        else if (var9[0] & !var9[1] & var9[2] & var9[3])
        {
            var11 = 27;
        }
        else if (var9[0] & var9[1] & !var9[2] & var9[3])
        {
            var11 = 38;
        }
        else if (var9[0] & var9[1] & var9[2] & !var9[3])
        {
            var11 = 14;
        }
        else if (var9[0] & var9[1] & var9[2] & var9[3])
        {
            var11 = 26;
        }

        if (!Config.isConnectedTexturesFancy())
        {
            return var0.textureId * 256 + var0.tiles[var11];
        }
        else
        {
            boolean[] var12 = new boolean[6];

            switch (var6)
            {
                case 0:
                case 1:
                    var12[0] = !isNeighbour(var0, var1, var3 + 1, var4, var5 + 1, var10, var6, var7, var8);
                    var12[1] = !isNeighbour(var0, var1, var3 - 1, var4, var5 + 1, var10, var6, var7, var8);
                    var12[2] = !isNeighbour(var0, var1, var3 + 1, var4, var5 - 1, var10, var6, var7, var8);
                    var12[3] = !isNeighbour(var0, var1, var3 - 1, var4, var5 - 1, var10, var6, var7, var8);
                    break;

                case 2:
                    var12[0] = !isNeighbour(var0, var1, var3 - 1, var4 - 1, var5, var10, var6, var7, var8);
                    var12[1] = !isNeighbour(var0, var1, var3 + 1, var4 - 1, var5, var10, var6, var7, var8);
                    var12[2] = !isNeighbour(var0, var1, var3 - 1, var4 + 1, var5, var10, var6, var7, var8);
                    var12[3] = !isNeighbour(var0, var1, var3 + 1, var4 + 1, var5, var10, var6, var7, var8);
                    break;

                case 3:
                    var12[0] = !isNeighbour(var0, var1, var3 + 1, var4 - 1, var5, var10, var6, var7, var8);
                    var12[1] = !isNeighbour(var0, var1, var3 - 1, var4 - 1, var5, var10, var6, var7, var8);
                    var12[2] = !isNeighbour(var0, var1, var3 + 1, var4 + 1, var5, var10, var6, var7, var8);
                    var12[3] = !isNeighbour(var0, var1, var3 - 1, var4 + 1, var5, var10, var6, var7, var8);
                    break;

                case 4:
                    var12[0] = !isNeighbour(var0, var1, var3, var4 - 1, var5 + 1, var10, var6, var7, var8);
                    var12[1] = !isNeighbour(var0, var1, var3, var4 - 1, var5 - 1, var10, var6, var7, var8);
                    var12[2] = !isNeighbour(var0, var1, var3, var4 + 1, var5 + 1, var10, var6, var7, var8);
                    var12[3] = !isNeighbour(var0, var1, var3, var4 + 1, var5 - 1, var10, var6, var7, var8);
                    break;

                case 5:
                    var12[0] = !isNeighbour(var0, var1, var3, var4 - 1, var5 - 1, var10, var6, var7, var8);
                    var12[1] = !isNeighbour(var0, var1, var3, var4 - 1, var5 + 1, var10, var6, var7, var8);
                    var12[2] = !isNeighbour(var0, var1, var3, var4 + 1, var5 - 1, var10, var6, var7, var8);
                    var12[3] = !isNeighbour(var0, var1, var3, var4 + 1, var5 + 1, var10, var6, var7, var8);
            }

            if (var11 == 13 && var12[0])
            {
                var11 = 4;
            }

            if (var11 == 15 && var12[1])
            {
                var11 = 5;
            }

            if (var11 == 37 && var12[2])
            {
                var11 = 16;
            }

            if (var11 == 39 && var12[3])
            {
                var11 = 17;
            }

            if (var11 == 14 && var12[0] && var12[1])
            {
                var11 = 7;
            }

            if (var11 == 25 && var12[0] && var12[2])
            {
                var11 = 6;
            }

            if (var11 == 27 && var12[3] && var12[1])
            {
                var11 = 19;
            }

            if (var11 == 38 && var12[3] && var12[2])
            {
                var11 = 18;
            }

            if (var11 == 14 && !var12[0] && var12[1])
            {
                var11 = 31;
            }

            if (var11 == 25 && var12[0] && !var12[2])
            {
                var11 = 30;
            }

            if (var11 == 27 && !var12[3] && var12[1])
            {
                var11 = 41;
            }

            if (var11 == 38 && var12[3] && !var12[2])
            {
                var11 = 40;
            }

            if (var11 == 14 && var12[0] && !var12[1])
            {
                var11 = 29;
            }

            if (var11 == 25 && !var12[0] && var12[2])
            {
                var11 = 28;
            }

            if (var11 == 27 && var12[3] && !var12[1])
            {
                var11 = 43;
            }

            if (var11 == 38 && !var12[3] && var12[2])
            {
                var11 = 42;
            }

            if (var11 == 26 && var12[0] && var12[1] && var12[2] && var12[3])
            {
                var11 = 46;
            }

            if (var11 == 26 && !var12[0] && var12[1] && var12[2] && var12[3])
            {
                var11 = 9;
            }

            if (var11 == 26 && var12[0] && !var12[1] && var12[2] && var12[3])
            {
                var11 = 21;
            }

            if (var11 == 26 && var12[0] && var12[1] && !var12[2] && var12[3])
            {
                var11 = 8;
            }

            if (var11 == 26 && var12[0] && var12[1] && var12[2] && !var12[3])
            {
                var11 = 20;
            }

            if (var11 == 26 && var12[0] && var12[1] && !var12[2] && !var12[3])
            {
                var11 = 11;
            }

            if (var11 == 26 && !var12[0] && !var12[1] && var12[2] && var12[3])
            {
                var11 = 22;
            }

            if (var11 == 26 && !var12[0] && var12[1] && !var12[2] && var12[3])
            {
                var11 = 23;
            }

            if (var11 == 26 && var12[0] && !var12[1] && var12[2] && !var12[3])
            {
                var11 = 10;
            }

            if (var11 == 26 && var12[0] && !var12[1] && !var12[2] && var12[3])
            {
                var11 = 34;
            }

            if (var11 == 26 && !var12[0] && var12[1] && var12[2] && !var12[3])
            {
                var11 = 35;
            }

            if (var11 == 26 && var12[0] && !var12[1] && !var12[2] && !var12[3])
            {
                var11 = 32;
            }

            if (var11 == 26 && !var12[0] && var12[1] && !var12[2] && !var12[3])
            {
                var11 = 33;
            }

            if (var11 == 26 && !var12[0] && !var12[1] && var12[2] && !var12[3])
            {
                var11 = 44;
            }

            if (var11 == 26 && !var12[0] && !var12[1] && !var12[2] && var12[3])
            {
                var11 = 45;
            }

            return var0.textureId * 256 + var0.tiles[var11];
        }
    }

    private static boolean isNeighbour(ConnectedProperties var0, IBlockAccess var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        int var9 = var1.getBlockId(var2, var3, var4);

        if (var0.connect == 2)
        {
            Block var10 = Block.blocksList[var9];

            if (var10 == null)
            {
                return false;
            }
            else
            {
                int var11 = var10.getBlockTexture(var1, var2, var3, var4, var6);
                return var11 == var7;
            }
        }
        else
        {
            return var9 == var5 && var1.getBlockMetadata(var2, var3, var4) == var8;
        }
    }

    private static int getConnectedTextureHorizontal(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        if (var6 != 0 && var6 != 1)
        {
            boolean var9 = false;
            boolean var10 = false;
            int var11 = var2.blockID;

            switch (var6)
            {
                case 2:
                    var9 = isNeighbour(var0, var1, var3 + 1, var4, var5, var11, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var3 - 1, var4, var5, var11, var6, var7, var8);
                    break;

                case 3:
                    var9 = isNeighbour(var0, var1, var3 - 1, var4, var5, var11, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var3 + 1, var4, var5, var11, var6, var7, var8);
                    break;

                case 4:
                    var9 = isNeighbour(var0, var1, var3, var4, var5 - 1, var11, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var3, var4, var5 + 1, var11, var6, var7, var8);
                    break;

                case 5:
                    var9 = isNeighbour(var0, var1, var3, var4, var5 + 1, var11, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var3, var4, var5 - 1, var11, var6, var7, var8);
            }

            boolean var12 = true;
            byte var13;

            if (var9)
            {
                if (var10)
                {
                    var13 = 1;
                }
                else
                {
                    var13 = 2;
                }
            }
            else if (var10)
            {
                var13 = 0;
            }
            else
            {
                var13 = 3;
            }

            return var0.textureId * 256 + var0.tiles[var13];
        }
        else
        {
            return -1;
        }
    }

    private static int getConnectedTextureVertical(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        if (var6 != 0 && var6 != 1)
        {
            int var9 = var2.blockID;
            boolean var10 = isNeighbour(var0, var1, var3, var4 - 1, var5, var9, var6, var7, var8);
            boolean var11 = isNeighbour(var0, var1, var3, var4 + 1, var5, var9, var6, var7, var8);
            boolean var12 = true;
            byte var13;

            if (var10)
            {
                if (var11)
                {
                    var13 = 1;
                }
                else
                {
                    var13 = 2;
                }
            }
            else if (var11)
            {
                var13 = 0;
            }
            else
            {
                var13 = 3;
            }

            return var0.textureId * 256 + var0.tiles[var13];
        }
        else
        {
            return -1;
        }
    }

    private static int getConnectedTextureTop(ConnectedProperties var0, IBlockAccess var1, Block var2, int var3, int var4, int var5, int var6, int var7, int var8)
    {
        if (var6 != 0 && var6 != 1)
        {
            int var9 = var2.blockID;
            return isNeighbour(var0, var1, var3, var4 + 1, var5, var9, var6, var7, var8) ? var0.textureId * 256 + var0.tiles[0] : -1;
        }
        else
        {
            return -1;
        }
    }

    public static boolean isConnectedGlassPanes()
    {
        return Config.isConnectedTextures() && matchingCtmPng;
    }

    private static boolean getMatchingCtmPng(RenderEngine var0)
    {
        Dimension var1 = var0.getTextureDimensions(var0.getTexture("/ctm.png"));

        if (var1 == null)
        {
            return false;
        }
        else
        {
            Dimension var2 = var0.getTextureDimensions(var0.getTexture("/terrain.png"));
            return var2 == null ? false : var1.width == var2.width && var1.height == var2.height;
        }
    }

    private static ConnectedProperties makeDefaultProperties(String var0, RenderEngine var1)
    {
        Properties var2 = new Properties();
        var2.put("source", "/ctm.png");
        var2.put("method", var0);
        ConnectedProperties var3 = new ConnectedProperties(var2);
        var3.isValid("(default)");
        var3.textureId = var1.getTexture(var3.source);
        return var3;
    }
}
