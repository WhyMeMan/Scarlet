package net.minecraft.src;

import java.io.InputStream;
import java.util.ArrayList;

public class NaturalTextures
{
    private static RenderEngine renderEngine = null;
    private static NaturalProperties[][] propertiesByTex = new NaturalProperties[0][];

    public static void update(RenderEngine var0)
    {
        propertiesByTex = new NaturalProperties[0][];
        renderEngine = var0;

        if (Config.isNaturalTextures())
        {
            String var1 = "/natural.properties";
            InputStream var2 = var0.texturePack.getSelectedTexturePack().getResourceAsStream(var1);

            if (var2 == null)
            {
                Config.dbg("NaturalTextures: configuration \"" + var1 + "\" not found");
                propertiesByTex = makeDefaultProperties();
            }
            else
            {
                try
                {
                    ArrayList var3 = new ArrayList(1024);
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
                                String[] var11 = Config.tokenize(var9, ":");

                                if (var11.length != 2)
                                {
                                    Config.dbg("Natural Textures: Invalid \"" + var1 + "\" line: " + var7);
                                }
                                else
                                {
                                    String var12 = var11[0];
                                    String var13 = var11[1];
                                    int var14 = Config.parseInt(var13, -1);

                                    if (var14 >= 0 && var14 <= 255)
                                    {
                                        NaturalProperties var15 = new NaturalProperties(var10);

                                        if (var15.isValid())
                                        {
                                            int var16 = var0.getTexture(var12);

                                            if (var16 >= 0)
                                            {
                                                while (var3.size() <= var16)
                                                {
                                                    var3.add((Object)null);
                                                }

                                                NaturalProperties[] var17 = (NaturalProperties[])((NaturalProperties[])var3.get(var16));

                                                if (var17 == null)
                                                {
                                                    var17 = new NaturalProperties[256];
                                                    var3.set(var16, var17);
                                                }

                                                var17[var14] = var15;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        Config.dbg("Natural Textures: Invalid \"" + var1 + "\" line: " + var7);
                                    }
                                }
                            }
                        }
                    }

                    propertiesByTex = (NaturalProperties[][])((NaturalProperties[][])var3.toArray(new NaturalProperties[var3.size()][]));
                }
                catch (Exception var18)
                {
                    var18.printStackTrace();
                }
            }
        }
    }

    public static NaturalProperties getNaturalProperties(int var0, int var1)
    {
        if (var0 < 0)
        {
            return null;
        }
        else
        {
            if (var0 == 0)
            {
                var0 = renderEngine.terrainTextureId;
            }

            if (var0 > propertiesByTex.length)
            {
                return null;
            }
            else
            {
                NaturalProperties[] var2 = propertiesByTex[var0];

                if (var2 == null)
                {
                    return null;
                }
                else if (var1 >= 0 && var1 < var2.length)
                {
                    NaturalProperties var3 = var2[var1];
                    return var3;
                }
                else
                {
                    return null;
                }
            }
        }
    }

    private static NaturalProperties[][] makeDefaultProperties()
    {
        if (!(renderEngine.texturePack.getSelectedTexturePack() instanceof TexturePackDefault))
        {
            Config.dbg("NaturalTextures: Texture pack is not default, ignoring default configuration.");
            return new NaturalProperties[0][];
        }
        else
        {
            Config.dbg("Natural Textures: Using default configuration.");
            NaturalProperties[] var0 = new NaturalProperties[256];
            var0[0] = new NaturalProperties("4F");
            var0[1] = new NaturalProperties("2F");
            var0[2] = new NaturalProperties("4F");
            var0[3] = new NaturalProperties("F");
            var0[38] = new NaturalProperties("F");
            var0[6] = new NaturalProperties("F");
            var0[17] = new NaturalProperties("2F");
            var0[18] = new NaturalProperties("4F");
            var0[19] = new NaturalProperties("2");
            var0[20] = new NaturalProperties("2F");
            var0[21] = new NaturalProperties("4F");
            var0[32] = new NaturalProperties("2F");
            var0[33] = new NaturalProperties("2F");
            var0[34] = new NaturalProperties("2F");
            var0[50] = new NaturalProperties("2F");
            var0[51] = new NaturalProperties("2F");
            var0[160] = new NaturalProperties("2F");
            var0[37] = new NaturalProperties("4F");
            var0[52] = new NaturalProperties("2F");
            var0[53] = new NaturalProperties("2F");
            var0[196] = new NaturalProperties("2");
            var0[197] = new NaturalProperties("2");
            var0[66] = new NaturalProperties("4F");
            var0[68] = new NaturalProperties("F");
            var0[70] = new NaturalProperties("2F");
            var0[72] = new NaturalProperties("4F");
            var0[77] = new NaturalProperties("F");
            var0[78] = new NaturalProperties("4F");
            var0[86] = new NaturalProperties("2F");
            var0[87] = new NaturalProperties("2F");
            var0[103] = new NaturalProperties("4F");
            var0[104] = new NaturalProperties("4F");
            var0[105] = new NaturalProperties("4");
            var0[116] = new NaturalProperties("2F");
            var0[117] = new NaturalProperties("F");
            var0[132] = new NaturalProperties("2F");
            var0[133] = new NaturalProperties("2F");
            var0[153] = new NaturalProperties("2F");
            var0[175] = new NaturalProperties("4");
            var0[176] = new NaturalProperties("4");
            var0[208] = new NaturalProperties("4F");
            var0[211] = new NaturalProperties("4F");
            var0[212] = new NaturalProperties("4F");
            int var1 = renderEngine.terrainTextureId;
            NaturalProperties[][] var2 = new NaturalProperties[var1 + 1][];
            var2[var1] = var0;
            return var2;
        }
    }
}
