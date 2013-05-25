package net.minecraft.src;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NaturalTextures
{
    private static RenderEngine renderEngine = null;
    private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

    public static void update(RenderEngine var0)
    {
        propertiesByIndex = new NaturalProperties[0];
        renderEngine = var0;

        if (Config.isNaturalTextures())
        {
            String var1 = "/natural.properties";

            try
            {
                InputStream var2 = var0.texturePack.getSelectedTexturePack().getResourceAsStream(var1);

                if (var2 == null)
                {
                    Config.dbg("NaturalTextures: configuration \"" + var1 + "\" not found");
                    propertiesByIndex = makeDefaultProperties();
                    return;
                }

                ArrayList var3 = new ArrayList(256);
                String var4 = Config.readInputStream(var2);
                var2.close();
                String[] var5 = Config.tokenize(var4, "\n\r");
                Config.dbg("Natural Textures: Parsing configuration \"" + var1 + "\"");

                for (int var6 = 0; var6 < var5.length; ++var6)
                {
                    String var7 = var5[var6].trim();

                    if (!var7.startsWith("#"))
                    {
                        String[] var8 = Config.tokenize(var7, "=");

                        if (var8.length != 2)
                        {
                            Config.dbg("Natural Textures: Invalid \"" + var1 + "\" line: " + var7);
                        }
                        else
                        {
                            String var9 = var8[0].trim();
                            String var10 = var8[1].trim();
                            TextureStitched var12 = var0.textureMapBlocks.getIconSafe(var9);

                            if (var12 == null)
                            {
                                Config.dbg("Natural Textures: Texture not found: \"" + var1 + "\" line: " + var7);
                            }
                            else
                            {
                                int var13 = var12.getIndexInMap();

                                if (var13 < 0)
                                {
                                    Config.dbg("Natural Textures: Invalid \"" + var1 + "\" line: " + var7);
                                }
                                else
                                {
                                    NaturalProperties var14 = new NaturalProperties(var10);

                                    if (var14.isValid())
                                    {
                                        while (var3.size() <= var13)
                                        {
                                            var3.add((Object)null);
                                        }

                                        var3.set(var13, var14);
                                    }
                                }
                            }
                        }
                    }
                }

                propertiesByIndex = (NaturalProperties[])((NaturalProperties[])var3.toArray(new NaturalProperties[var3.size()]));
            }
            catch (FileNotFoundException var15)
            {
                Config.dbg("NaturalTextures: configuration \"" + var1 + "\" not found");
                propertiesByIndex = makeDefaultProperties();
                return;
            }
            catch (Exception var16)
            {
                var16.printStackTrace();
            }
        }
    }

    public static NaturalProperties getNaturalProperties(Icon var0)
    {
        if (!(var0 instanceof TextureStitched))
        {
            return null;
        }
        else
        {
            TextureStitched var1 = (TextureStitched)var0;
            int var2 = var1.getIndexInMap();

            if (var2 >= 0 && var2 < propertiesByIndex.length)
            {
                NaturalProperties var3 = propertiesByIndex[var2];
                return var3;
            }
            else
            {
                return null;
            }
        }
    }

    private static NaturalProperties[] makeDefaultProperties()
    {
        if (!(renderEngine.texturePack.getSelectedTexturePack() instanceof TexturePackDefault))
        {
            Config.dbg("NaturalTextures: Texture pack is not default, ignoring default configuration.");
            return new NaturalProperties[0];
        }
        else
        {
            Config.dbg("Natural Textures: Using default configuration.");
            ArrayList var0 = new ArrayList();
            setIconProperties(var0, "grass_top", "4F");
            setIconProperties(var0, "stone", "2F");
            setIconProperties(var0, "dirt", "4F");
            setIconProperties(var0, "grass_side", "F");
            setIconProperties(var0, "grass_side_overlay", "F");
            setIconProperties(var0, "stoneslab_top", "F");
            setIconProperties(var0, "bedrock", "2F");
            setIconProperties(var0, "sand", "4F");
            setIconProperties(var0, "gravel", "2");
            setIconProperties(var0, "tree_side", "2F");
            setIconProperties(var0, "tree_top", "4F");
            setIconProperties(var0, "oreGold", "2F");
            setIconProperties(var0, "oreIron", "2F");
            setIconProperties(var0, "oreCoal", "2F");
            setIconProperties(var0, "oreDiamond", "2F");
            setIconProperties(var0, "oreRedstone", "2F");
            setIconProperties(var0, "oreLapis", "2F");
            setIconProperties(var0, "obsidian", "4F");
            setIconProperties(var0, "leaves", "2F");
            setIconProperties(var0, "leaves_opaque", "2F");
            setIconProperties(var0, "leaves_jungle", "2");
            setIconProperties(var0, "leaves_jungle_opaque", "2");
            setIconProperties(var0, "snow", "4F");
            setIconProperties(var0, "snow_side", "F");
            setIconProperties(var0, "cactus_side", "2F");
            setIconProperties(var0, "clay", "4F");
            setIconProperties(var0, "mycel_side", "F");
            setIconProperties(var0, "mycel_top", "4F");
            setIconProperties(var0, "farmland_wet", "2F");
            setIconProperties(var0, "farmland_dry", "2F");
            setIconProperties(var0, "hellrock", "4F");
            setIconProperties(var0, "hellsand", "4F");
            setIconProperties(var0, "lightgem", "4");
            setIconProperties(var0, "tree_spruce", "2F");
            setIconProperties(var0, "tree_birch", "F");
            setIconProperties(var0, "leaves_spruce", "2F");
            setIconProperties(var0, "leaves_spruce_opaque", "2F");
            setIconProperties(var0, "tree_jungle", "2F");
            setIconProperties(var0, "whiteStone", "4");
            setIconProperties(var0, "sandstone_top", "4");
            setIconProperties(var0, "sandstone_bottom", "4F");
            setIconProperties(var0, "redstoneLight_lit", "4F");
            NaturalProperties[] var1 = (NaturalProperties[])((NaturalProperties[])var0.toArray(new NaturalProperties[var0.size()]));
            return var1;
        }
    }

    private static void setIconProperties(List var0, String var1, String var2)
    {
        TextureMap var3 = renderEngine.textureMapBlocks;
        Icon var4 = var3.registerIcon(var1);

        if (var4 == null)
        {
            Config.dbg("*** NaturalProperties: Icon not found: " + var1 + " ***");
        }
        else if (!(var4 instanceof TextureStitched))
        {
            Config.dbg("*** NaturalProperties: Icon is not IconStitched: " + var1 + ": " + var4.getClass().getName() + " ***");
        }
        else
        {
            TextureStitched var5 = (TextureStitched)var4;
            int var6 = var5.getIndexInMap();

            if (var6 < 0)
            {
                Config.dbg("*** NaturalProperties: Invalid index for icon: " + var1 + ": " + var6 + " ***");
            }
            else
            {
                while (var6 >= var0.size())
                {
                    var0.add((Object)null);
                }

                NaturalProperties var7 = new NaturalProperties(var2);
                var0.set(var6, var7);
            }
        }
    }

    public static void updateIcons(TextureMap var0) {}
}
