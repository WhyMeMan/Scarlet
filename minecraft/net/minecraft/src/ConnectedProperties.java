package net.minecraft.src;

import java.util.ArrayList;
import java.util.Properties;

public class ConnectedProperties
{
    public int method = 0;
    public String source = null;
    public int[] tiles = null;
    public int connect = 0;
    public int faces = 63;
    public int[] metadatas = null;
    public int[] weights = null;
    public int symmetry = 1;
    public int width = 0;
    public int height = 0;
    public int[] sumWeights = null;
    public int sumAllWeights = 0;
    public int textureId = -1;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_CTM = 1;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int METHOD_TOP = 3;
    public static final int METHOD_RANDOM = 4;
    public static final int METHOD_REPEAT = 5;
    public static final int METHOD_VERTICAL = 6;
    public static final int CONNECT_NONE = 0;
    public static final int CONNECT_BLOCK = 1;
    public static final int CONNECT_TILE = 2;
    public static final int CONNECT_UNKNOWN = 128;
    public static final int FACE_BOTTOM = 1;
    public static final int FACE_TOP = 2;
    public static final int FACE_EAST = 4;
    public static final int FACE_WEST = 8;
    public static final int FACE_NORTH = 16;
    public static final int FACE_SOUTH = 32;
    public static final int FACE_SIDES = 60;
    public static final int FACE_ALL = 63;
    public static final int FACE_UNKNOWN = 128;
    public static final int SYMMETRY_NONE = 1;
    public static final int SYMMETRY_OPPOSITE = 2;
    public static final int SYMMETRY_ALL = 6;
    public static final int SYMMETRY_UNKNOWN = 128;

    public ConnectedProperties(Properties var1)
    {
        this.method = this.parseMethod(var1.getProperty("method"));
        this.source = var1.getProperty("source");
        this.tiles = this.parseInts(var1.getProperty("tiles"));
        this.connect = this.parseConnect(var1.getProperty("connect"));
        this.faces = this.parseFaces(var1.getProperty("faces"));
        this.metadatas = this.parseInts(var1.getProperty("metadata"));
        this.weights = this.parseInts(var1.getProperty("weights"));
        this.symmetry = this.parseSymmetry(var1.getProperty("symmetry"));
        this.width = this.parseInt(var1.getProperty("width"));
        this.height = this.parseInt(var1.getProperty("height"));
    }

    private int parseInt(String var1)
    {
        if (var1 == null)
        {
            return -1;
        }
        else
        {
            int var2 = Config.parseInt(var1, -1);

            if (var2 < 0)
            {
                Config.dbg("Invalid number: " + var1);
            }

            return var2;
        }
    }

    private int parseSymmetry(String var1)
    {
        if (var1 == null)
        {
            return 1;
        }
        else if (var1.equals("opposite"))
        {
            return 2;
        }
        else if (var1.equals("all"))
        {
            return 6;
        }
        else
        {
            Config.dbg("Unknown symmetry: " + var1);
            return 1;
        }
    }

    private int parseFaces(String var1)
    {
        if (var1 == null)
        {
            return 63;
        }
        else
        {
            String[] var2 = Config.tokenize(var1, " ,");
            int var3 = 0;

            for (int var4 = 0; var4 < var2.length; ++var4)
            {
                String var5 = var2[var4];
                int var6 = this.parseFace(var5);
                var3 |= var6;
            }

            return var3;
        }
    }

    private int parseFace(String var1)
    {
        if (var1.equals("bottom"))
        {
            return 1;
        }
        else if (var1.equals("top"))
        {
            return 2;
        }
        else if (var1.equals("north"))
        {
            return 4;
        }
        else if (var1.equals("south"))
        {
            return 8;
        }
        else if (var1.equals("east"))
        {
            return 32;
        }
        else if (var1.equals("west"))
        {
            return 16;
        }
        else if (var1.equals("sides"))
        {
            return 60;
        }
        else if (var1.equals("all"))
        {
            return 63;
        }
        else
        {
            Config.dbg("Unknown face: " + var1);
            return 128;
        }
    }

    private int parseConnect(String var1)
    {
        if (var1 == null)
        {
            return 0;
        }
        else if (var1.equals("block"))
        {
            return 1;
        }
        else if (var1.equals("tile"))
        {
            return 2;
        }
        else
        {
            Config.dbg("Unknown connect: " + var1);
            return 128;
        }
    }

    private int[] parseInts(String var1)
    {
        if (var1 == null)
        {
            return null;
        }
        else
        {
            ArrayList var2 = new ArrayList();
            String[] var3 = Config.tokenize(var1, " ,");

            for (int var4 = 0; var4 < var3.length; ++var4)
            {
                String var5 = var3[var4];

                if (var5.contains("-"))
                {
                    String[] var6 = Config.tokenize(var5, "-");

                    if (var6.length != 2)
                    {
                        Config.dbg("Invalid interval: " + var5 + ", when parsing: " + var1);
                    }
                    else
                    {
                        int var7 = Config.parseInt(var6[0], -1);
                        int var8 = Config.parseInt(var6[1], -1);

                        if (var7 >= 0 && var8 >= 0 && var7 <= var8)
                        {
                            for (int var9 = var7; var9 <= var8; ++var9)
                            {
                                var2.add(Integer.valueOf(var9));
                            }
                        }
                        else
                        {
                            Config.dbg("Invalid interval: " + var5 + ", when parsing: " + var1);
                        }
                    }
                }
                else
                {
                    int var12 = Config.parseInt(var5, -1);

                    if (var12 < 0)
                    {
                        Config.dbg("Invalid number: " + var5 + ", when parsing: " + var1);
                    }
                    else
                    {
                        var2.add(Integer.valueOf(var12));
                    }
                }
            }

            int[] var10 = new int[var2.size()];

            for (int var11 = 0; var11 < var10.length; ++var11)
            {
                var10[var11] = ((Integer)var2.get(var11)).intValue();
            }

            return var10;
        }
    }

    private int parseMethod(String var1)
    {
        if (var1 == null)
        {
            return 1;
        }
        else if (var1.equals("ctm"))
        {
            return 1;
        }
        else if (var1.equals("horizontal"))
        {
            return 2;
        }
        else if (var1.equals("vertical"))
        {
            return 6;
        }
        else if (var1.equals("top"))
        {
            return 3;
        }
        else if (var1.equals("random"))
        {
            return 4;
        }
        else if (var1.equals("repeat"))
        {
            return 5;
        }
        else
        {
            Config.dbg("Unknown method: " + var1);
            return 0;
        }
    }

    public boolean isValid(String var1)
    {
        if (this.source == null)
        {
            Config.dbg("No source texture: " + var1);
            return false;
        }
        else if (this.method == 0)
        {
            Config.dbg("No method: " + var1);
            return false;
        }
        else if ((this.connect & 128) != 0)
        {
            Config.dbg("Invalid connect in: " + var1);
            return false;
        }
        else if ((this.faces & 128) != 0)
        {
            Config.dbg("Invalid faces in: " + var1);
            return false;
        }
        else if ((this.symmetry & 128) != 0)
        {
            Config.dbg("Invalid symmetry in: " + var1);
            return false;
        }
        else
        {
            if (this.tiles != null)
            {
                for (int var2 = 0; var2 < this.tiles.length; ++var2)
                {
                    int var3 = this.tiles[var2];

                    if (var3 < 0 || var3 > 255)
                    {
                        Config.dbg("Invalid tile: " + var3 + ", in " + var1);
                        return false;
                    }
                }
            }

            switch (this.method)
            {
                case 1:
                    return this.isValidCtm(var1);

                case 2:
                    return this.isValidHorizontal(var1);

                case 3:
                    return this.isValidTop(var1);

                case 4:
                    return this.isValidRandom(var1);

                case 5:
                    return this.isValidRepeat(var1);

                case 6:
                    return this.isValidVertical(var1);

                default:
                    Config.dbg("Unknown method: " + var1);
                    return false;
            }
        }
    }

    private boolean isValidCtm(String var1)
    {
        if (this.tiles == null)
        {
            this.tiles = this.parseInts("0-11 16-27 32-43 48-59");
        }

        if (this.tiles.length != 48)
        {
            Config.dbg("Invalid tiles, must be exactly 48: " + var1);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidHorizontal(String var1)
    {
        if (this.tiles == null)
        {
            this.tiles = this.parseInts("12-15");
        }

        if (this.tiles.length != 4)
        {
            Config.dbg("Invalid tiles, must be exactly 4: " + var1);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidVertical(String var1)
    {
        if (this.tiles == null)
        {
            Config.dbg("No tiles defined for vertical: " + var1);
            return false;
        }
        else if (this.tiles.length != 4)
        {
            Config.dbg("Invalid tiles, must be exactly 4: " + var1);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidRandom(String var1)
    {
        if (this.tiles != null && this.tiles.length > 0)
        {
            if (this.weights != null && this.weights.length != this.tiles.length)
            {
                Config.dbg("Number of weights must equal the number of tiles: " + var1);
                return false;
            }
            else
            {
                if (this.weights != null)
                {
                    this.sumWeights = new int[this.weights.length];
                    int var2 = 0;

                    for (int var3 = 0; var3 < this.weights.length; ++var3)
                    {
                        var2 += this.weights[var3];
                        this.sumWeights[var3] = var2;
                    }

                    this.sumAllWeights = var2;
                }

                return true;
            }
        }
        else
        {
            Config.dbg("Tiles not defined: " + var1);
            return false;
        }
    }

    private boolean isValidRepeat(String var1)
    {
        if (this.tiles == null)
        {
            Config.dbg("Tiles not defined: " + var1);
            return false;
        }
        else if (this.width > 0 && this.width <= 16)
        {
            if (this.height > 0 && this.height <= 16)
            {
                if (this.tiles.length != this.width * this.height)
                {
                    Config.dbg("Number of tiles does not equal width x height: " + var1);
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                Config.dbg("Invalid height: " + var1);
                return false;
            }
        }
        else
        {
            Config.dbg("Invalid width: " + var1);
            return false;
        }
    }

    private boolean isValidTop(String var1)
    {
        if (this.tiles == null)
        {
            this.tiles = this.parseInts("66");
        }

        if (this.tiles.length != 1)
        {
            Config.dbg("Invalid tiles, must be exactly 1: " + var1);
            return false;
        }
        else
        {
            return true;
        }
    }
}
