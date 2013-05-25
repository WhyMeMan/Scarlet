package net.minecraft.src;

import java.util.ArrayList;
import java.util.Properties;

public class ConnectedProperties
{
    public String name = null;
    public String basePath = null;
    public int[] matchBlocks = null;
    public String[] matchTiles = null;
    public int method = 0;
    public String[] tiles = null;
    public int connect = 0;
    public int faces = 63;
    public int[] metadatas = null;
    public BiomeGenBase[] biomes = null;
    public int minHeight = 0;
    public int maxHeight = 1024;
    public int renderPass = 0;
    public boolean innerSeams = false;
    public int width = 0;
    public int height = 0;
    public int[] weights = null;
    public int symmetry = 1;
    public int[] sumWeights = null;
    public int sumAllWeights = 0;
    public Icon[] matchTileIcons = null;
    public Icon[] tileIcons = null;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_CTM = 1;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int METHOD_TOP = 3;
    public static final int METHOD_RANDOM = 4;
    public static final int METHOD_REPEAT = 5;
    public static final int METHOD_VERTICAL = 6;
    public static final int METHOD_FIXED = 7;
    public static final int CONNECT_NONE = 0;
    public static final int CONNECT_BLOCK = 1;
    public static final int CONNECT_TILE = 2;
    public static final int CONNECT_MATERIAL = 3;
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

    public ConnectedProperties(Properties var1, String var2)
    {
        this.name = parseName(var2);
        this.basePath = parseBasePath(var2);
        this.matchBlocks = parseInts(var1.getProperty("matchBlocks"));
        this.matchTiles = this.parseMatchTiles(var1.getProperty("matchTiles"));
        this.method = parseMethod(var1.getProperty("method"));
        this.tiles = this.parseTileNames(var1.getProperty("tiles"));
        this.connect = parseConnect(var1.getProperty("connect"));
        this.faces = parseFaces(var1.getProperty("faces"));
        this.metadatas = parseInts(var1.getProperty("metadata"));
        this.biomes = parseBiomes(var1.getProperty("biomes"));
        this.minHeight = parseInt(var1.getProperty("minHeight"), -1);
        this.maxHeight = parseInt(var1.getProperty("maxHeight"), 1024);
        this.renderPass = parseInt(var1.getProperty("renderPass"));
        this.innerSeams = parseBoolean(var1.getProperty("innerSeams"));
        this.width = parseInt(var1.getProperty("width"));
        this.height = parseInt(var1.getProperty("height"));
        this.weights = parseInts(var1.getProperty("weights"));
        this.symmetry = parseSymmetry(var1.getProperty("symmetry"));
    }

    private String[] parseMatchTiles(String var1)
    {
        if (var1 == null)
        {
            return null;
        }
        else
        {
            String[] var2 = Config.tokenize(var1, " ");

            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                String var4 = var2[var3];

                if (var4.endsWith(".png"))
                {
                    var4 = var4.substring(0, var4.length() - 4);
                }

                if (var4.startsWith("/ctm/"))
                {
                    var4 = var4.substring(1);
                }

                var2[var3] = var4;
            }

            return var2;
        }
    }

    private static String parseName(String var0)
    {
        String var1 = var0;
        int var2 = var0.lastIndexOf(47);

        if (var2 >= 0)
        {
            var1 = var0.substring(var2 + 1);
        }

        int var3 = var1.lastIndexOf(46);

        if (var3 >= 0)
        {
            var1 = var1.substring(0, var3);
        }

        return var1;
    }

    private static String parseBasePath(String var0)
    {
        int var1 = var0.lastIndexOf(47);
        return var1 < 0 ? "" : var0.substring(0, var1);
    }

    private static BiomeGenBase[] parseBiomes(String var0)
    {
        if (var0 == null)
        {
            return null;
        }
        else
        {
            String[] var1 = Config.tokenize(var0, " ");
            ArrayList var2 = new ArrayList();

            for (int var3 = 0; var3 < var1.length; ++var3)
            {
                String var4 = var1[var3];
                BiomeGenBase var5 = findBiome(var4);

                if (var5 == null)
                {
                    Config.dbg("Biome not found: " + var4);
                }
                else
                {
                    var2.add(var5);
                }
            }

            BiomeGenBase[] var6 = (BiomeGenBase[])((BiomeGenBase[])var2.toArray(new BiomeGenBase[var2.size()]));
            return var6;
        }
    }

    private static BiomeGenBase findBiome(String var0)
    {
        var0 = var0.toLowerCase();

        for (int var1 = 0; var1 < BiomeGenBase.biomeList.length; ++var1)
        {
            BiomeGenBase var2 = BiomeGenBase.biomeList[var1];

            if (var2 != null)
            {
                String var3 = var2.biomeName.replace(" ", "").toLowerCase();

                if (var3.equals(var0))
                {
                    return var2;
                }
            }
        }

        return null;
    }

    private String[] parseTileNames(String var1)
    {
        if (var1 == null)
        {
            return null;
        }
        else
        {
            ArrayList var2 = new ArrayList();
            String[] var3 = Config.tokenize(var1, " ,");
            label63:

            for (int var4 = 0; var4 < var3.length; ++var4)
            {
                String var5 = var3[var4];

                if (var5.contains("-"))
                {
                    String[] var6 = Config.tokenize(var5, "-");

                    if (var6.length == 2)
                    {
                        int var7 = Config.parseInt(var6[0], -1);
                        int var8 = Config.parseInt(var6[1], -1);

                        if (var7 >= 0 && var8 >= 0)
                        {
                            if (var7 <= var8)
                            {
                                int var9 = var7;

                                while (true)
                                {
                                    if (var9 > var8)
                                    {
                                        continue label63;
                                    }

                                    var2.add(String.valueOf(var9));
                                    ++var9;
                                }
                            }

                            Config.dbg("Invalid interval: " + var5 + ", when parsing: " + var1);
                            continue;
                        }
                    }
                }

                var2.add(var5);
            }

            String[] var10 = (String[])((String[])var2.toArray(new String[var2.size()]));

            for (int var11 = 0; var11 < var10.length; ++var11)
            {
                String var12 = var10[var11];

                if (!var12.startsWith("/"))
                {
                    var12 = this.basePath + "/" + var12;
                }

                if (var12.endsWith(".png"))
                {
                    var12 = var12.substring(0, var12.length() - 4);
                }

                String var13 = "/textures/blocks/";

                if (var12.startsWith(var13))
                {
                    var12 = var12.substring(var13.length());
                }

                if (var12.startsWith("/"))
                {
                    var12 = var12.substring(1);
                }

                var10[var11] = var12;
            }

            return var10;
        }
    }

    private static int parseInt(String var0)
    {
        if (var0 == null)
        {
            return -1;
        }
        else
        {
            int var1 = Config.parseInt(var0, -1);

            if (var1 < 0)
            {
                Config.dbg("Invalid number: " + var0);
            }

            return var1;
        }
    }

    private static int parseInt(String var0, int var1)
    {
        if (var0 == null)
        {
            return var1;
        }
        else
        {
            int var2 = Config.parseInt(var0, -1);

            if (var2 < 0)
            {
                Config.dbg("Invalid number: " + var0);
                return var1;
            }
            else
            {
                return var2;
            }
        }
    }

    private static boolean parseBoolean(String var0)
    {
        return var0 == null ? false : var0.toLowerCase().equals("true");
    }

    private static int parseSymmetry(String var0)
    {
        if (var0 == null)
        {
            return 1;
        }
        else if (var0.equals("opposite"))
        {
            return 2;
        }
        else if (var0.equals("all"))
        {
            return 6;
        }
        else
        {
            Config.dbg("Unknown symmetry: " + var0);
            return 1;
        }
    }

    private static int parseFaces(String var0)
    {
        if (var0 == null)
        {
            return 63;
        }
        else
        {
            String[] var1 = Config.tokenize(var0, " ,");
            int var2 = 0;

            for (int var3 = 0; var3 < var1.length; ++var3)
            {
                String var4 = var1[var3];
                int var5 = parseFace(var4);
                var2 |= var5;
            }

            return var2;
        }
    }

    private static int parseFace(String var0)
    {
        if (var0.equals("bottom"))
        {
            return 1;
        }
        else if (var0.equals("top"))
        {
            return 2;
        }
        else if (var0.equals("north"))
        {
            return 4;
        }
        else if (var0.equals("south"))
        {
            return 8;
        }
        else if (var0.equals("east"))
        {
            return 32;
        }
        else if (var0.equals("west"))
        {
            return 16;
        }
        else if (var0.equals("sides"))
        {
            return 60;
        }
        else if (var0.equals("all"))
        {
            return 63;
        }
        else
        {
            Config.dbg("Unknown face: " + var0);
            return 128;
        }
    }

    private static int parseConnect(String var0)
    {
        if (var0 == null)
        {
            return 0;
        }
        else if (var0.equals("block"))
        {
            return 1;
        }
        else if (var0.equals("tile"))
        {
            return 2;
        }
        else if (var0.equals("material"))
        {
            return 3;
        }
        else
        {
            Config.dbg("Unknown connect: " + var0);
            return 128;
        }
    }

    private static int[] parseInts(String var0)
    {
        if (var0 == null)
        {
            return null;
        }
        else
        {
            ArrayList var1 = new ArrayList();
            String[] var2 = Config.tokenize(var0, " ,");

            for (int var3 = 0; var3 < var2.length; ++var3)
            {
                String var4 = var2[var3];

                if (var4.contains("-"))
                {
                    String[] var5 = Config.tokenize(var4, "-");

                    if (var5.length != 2)
                    {
                        Config.dbg("Invalid interval: " + var4 + ", when parsing: " + var0);
                    }
                    else
                    {
                        int var6 = Config.parseInt(var5[0], -1);
                        int var7 = Config.parseInt(var5[1], -1);

                        if (var6 >= 0 && var7 >= 0 && var6 <= var7)
                        {
                            for (int var8 = var6; var8 <= var7; ++var8)
                            {
                                var1.add(Integer.valueOf(var8));
                            }
                        }
                        else
                        {
                            Config.dbg("Invalid interval: " + var4 + ", when parsing: " + var0);
                        }
                    }
                }
                else
                {
                    int var11 = Config.parseInt(var4, -1);

                    if (var11 < 0)
                    {
                        Config.dbg("Invalid number: " + var4 + ", when parsing: " + var0);
                    }
                    else
                    {
                        var1.add(Integer.valueOf(var11));
                    }
                }
            }

            int[] var9 = new int[var1.size()];

            for (int var10 = 0; var10 < var9.length; ++var10)
            {
                var9[var10] = ((Integer)var1.get(var10)).intValue();
            }

            return var9;
        }
    }

    private static int parseMethod(String var0)
    {
        if (var0 == null)
        {
            return 1;
        }
        else if (var0.equals("ctm"))
        {
            return 1;
        }
        else if (var0.equals("horizontal"))
        {
            return 2;
        }
        else if (var0.equals("vertical"))
        {
            return 6;
        }
        else if (var0.equals("top"))
        {
            return 3;
        }
        else if (var0.equals("random"))
        {
            return 4;
        }
        else if (var0.equals("repeat"))
        {
            return 5;
        }
        else if (var0.equals("fixed"))
        {
            return 7;
        }
        else
        {
            Config.dbg("Unknown method: " + var0);
            return 0;
        }
    }

    public boolean isValid(String var1)
    {
        if (this.name != null && this.name.length() > 0)
        {
            if (this.basePath == null)
            {
                Config.dbg("No base path found: " + var1);
                return false;
            }
            else
            {
                if (this.matchBlocks == null)
                {
                    this.matchBlocks = this.detectMatchBlocks();
                }

                if (this.matchTiles == null && this.matchBlocks == null)
                {
                    this.matchTiles = this.detectMatchTiles();
                }

                if (this.matchBlocks == null && this.matchTiles == null)
                {
                    Config.dbg("No matchBlocks or matchTiles specified: " + var1);
                    return false;
                }
                else if (this.method == 0)
                {
                    Config.dbg("No method: " + var1);
                    return false;
                }
                else if (this.tiles != null && this.tiles.length > 0)
                {
                    if (this.connect == 0)
                    {
                        this.connect = this.detectConnect();
                    }

                    if (this.connect == 128)
                    {
                        Config.dbg("Invalid connect in: " + var1);
                        return false;
                    }
                    else if (this.renderPass > 0)
                    {
                        Config.dbg("Render pass not supported: " + this.renderPass);
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

                            case 7:
                                return this.isValidFixed(var1);

                            default:
                                Config.dbg("Unknown method: " + var1);
                                return false;
                        }
                    }
                }
                else
                {
                    Config.dbg("No tiles specified: " + var1);
                    return false;
                }
            }
        }
        else
        {
            Config.dbg("No name found: " + var1);
            return false;
        }
    }

    private int detectConnect()
    {
        return this.matchBlocks != null ? 1 : (this.matchTiles != null ? 2 : 128);
    }

    private int[] detectMatchBlocks()
    {
        if (!this.name.startsWith("block"))
        {
            return null;
        }
        else
        {
            int var1 = "block".length();
            int var2;

            for (var2 = var1; var2 < this.name.length(); ++var2)
            {
                char var3 = this.name.charAt(var2);

                if (var3 < 48 || var3 > 57)
                {
                    break;
                }
            }

            if (var2 == var1)
            {
                return null;
            }
            else
            {
                String var5 = this.name.substring(var1, var2);
                int var4 = Config.parseInt(var5, -1);
                return var4 < 0 ? null : new int[] {var4};
            }
        }
    }

    private String[] detectMatchTiles()
    {
        Icon var1 = getIcon(this.name);
        return var1 == null ? null : new String[] {this.name};
    }

    private static Icon getIcon(String var0)
    {
        RenderEngine var1 = Config.getRenderEngine();
        return var1 == null ? null : var1.textureMapBlocks.getIconSafe(var0);
    }

    private boolean isValidCtm(String var1)
    {
        if (this.tiles == null)
        {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }

        if (this.tiles.length < 47)
        {
            Config.dbg("Invalid tiles, must be at least 47: " + var1);
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
            this.tiles = this.parseTileNames("12-15");
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
                this.weights = null;
            }

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

    private boolean isValidFixed(String var1)
    {
        if (this.tiles == null)
        {
            Config.dbg("Tiles not defined: " + var1);
            return false;
        }
        else if (this.tiles.length != 1)
        {
            Config.dbg("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidTop(String var1)
    {
        if (this.tiles == null)
        {
            this.tiles = this.parseTileNames("66");
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

    public void updateIcons(TextureMap var1)
    {
        if (this.matchTiles != null)
        {
            this.matchTileIcons = registerIcons(this.matchTiles, var1);
        }

        if (this.tiles != null)
        {
            this.tileIcons = registerIcons(this.tiles, var1);
        }
    }

    private static Icon[] registerIcons(String[] var0, TextureMap var1)
    {
        if (var0 == null)
        {
            return null;
        }
        else
        {
            ITexturePack var2 = Config.getRenderEngine().getTexturePack().getSelectedTexturePack();
            ArrayList var3 = new ArrayList();

            for (int var4 = 0; var4 < var0.length; ++var4)
            {
                String var5 = var0[var4];
                String var6 = var5;

                if (!var5.contains("/"))
                {
                    var6 = "textures/blocks/" + var5;
                }

                String var7 = "/" + var6 + ".png";
                boolean var8 = var2.func_98138_b(var7, true);

                if (!var8)
                {
                    Config.dbg("File not found: " + var7);
                }

                Icon var9 = var1.registerIcon(var5);
                var3.add(var9);
            }

            Icon[] var10 = (Icon[])((Icon[])var3.toArray(new Icon[var3.size()]));
            return var10;
        }
    }

    public String toString()
    {
        return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString((Object[])this.matchTiles);
    }
}
