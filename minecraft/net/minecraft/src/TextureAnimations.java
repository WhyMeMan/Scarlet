package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

public class TextureAnimations
{
    private static TextureAnimation[] textureAnimations = null;
    private static RenderEngine renderEngine = null;

    public static void reset()
    {
        textureAnimations = null;
    }

    public static void update(RenderEngine var0)
    {
        renderEngine = var0;
        ITexturePack var1 = var0.texturePack.getSelectedTexturePack();
        textureAnimations = getTextureAnimations(var1);
        updateAnimations();
    }

    public static void updateCustomAnimations()
    {
        if (textureAnimations != null)
        {
            if (Config.isAnimatedTextures())
            {
                updateAnimations();
            }
        }
    }

    public static void updateAnimations()
    {
        if (textureAnimations != null)
        {
            for (int var0 = 0; var0 < textureAnimations.length; ++var0)
            {
                TextureAnimation var1 = textureAnimations[var0];
                var1.updateTexture();
            }
        }
    }

    public static TextureAnimation[] getTextureAnimations(ITexturePack var0)
    {
        if (!(var0 instanceof TexturePackImplementation))
        {
            return null;
        }
        else
        {
            TexturePackImplementation var1 = (TexturePackImplementation)var0;
            File var2 = var1.texturePackFile;

            if (var2 == null)
            {
                return null;
            }
            else if (!var2.exists())
            {
                return null;
            }
            else
            {
                String[] var3 = null;

                if (var2.isFile())
                {
                    var3 = getAnimationPropertiesZip(var2);
                }
                else
                {
                    var3 = getAnimationPropertiesDir(var2);
                }

                if (var3 == null)
                {
                    return null;
                }
                else
                {
                    ArrayList var4 = new ArrayList();

                    for (int var5 = 0; var5 < var3.length; ++var5)
                    {
                        String var6 = var3[var5];
                        Config.dbg("Texture animation: " + var6);

                        try
                        {
                            InputStream var7 = var1.getResourceAsStream(var6);
                            Properties var8 = new Properties();
                            var8.load(var7);
                            TextureAnimation var9 = makeTextureAnimation(var8);

                            if (var9 != null)
                            {
                                var4.add(var9);
                            }
                        }
                        catch (FileNotFoundException var10)
                        {
                            Config.dbg("File not found: " + var10.getMessage());
                        }
                        catch (IOException var11)
                        {
                            var11.printStackTrace();
                        }
                    }

                    TextureAnimation[] var12 = (TextureAnimation[])((TextureAnimation[])var4.toArray(new TextureAnimation[var4.size()]));
                    return var12;
                }
            }
        }
    }

    public static TextureAnimation makeTextureAnimation(Properties var0)
    {
        String var1 = var0.getProperty("from");
        String var2 = var0.getProperty("to");
        int var3 = Config.parseInt(var0.getProperty("x"), -1);
        int var4 = Config.parseInt(var0.getProperty("y"), -1);
        int var5 = Config.parseInt(var0.getProperty("w"), -1);
        int var6 = Config.parseInt(var0.getProperty("h"), -1);

        if (var1 != null && var2 != null)
        {
            if (var3 >= 0 && var4 >= 0 && var5 >= 0 && var6 >= 0)
            {
                byte[] var7 = getCustomTextureData(var1, var5);

                if (var7 == null)
                {
                    Config.dbg("TextureAnimation: Source texture not found: " + var2);
                    return null;
                }
                else if (!renderEngine.getTexturePack().getSelectedTexturePack().func_98138_b(var2, true))
                {
                    Config.dbg("TextureAnimation: Target texture not found: " + var2);
                    return null;
                }
                else
                {
                    int var8 = renderEngine.getTexture(var2);

                    if (var8 < 0)
                    {
                        Config.dbg("TextureAnimation: Target texture not found: " + var2);
                        return null;
                    }
                    else
                    {
                        TextureAnimation var9 = new TextureAnimation(var1, var7, var2, var8, var3, var4, var5, var6, var0, 1);
                        return var9;
                    }
                }
            }
            else
            {
                Config.dbg("TextureAnimation: Invalid coordinates");
                return null;
            }
        }
        else
        {
            Config.dbg("TextureAnimation: Source or target texture not specified");
            return null;
        }
    }

    public static String[] getAnimationPropertiesDir(File var0)
    {
        File var1 = new File(var0, "anim");

        if (!var1.exists())
        {
            return null;
        }
        else if (!var1.isDirectory())
        {
            return null;
        }
        else
        {
            File[] var2 = var1.listFiles();

            if (var2 == null)
            {
                return null;
            }
            else
            {
                ArrayList var3 = new ArrayList();

                for (int var4 = 0; var4 < var2.length; ++var4)
                {
                    File var5 = var2[var4];
                    String var6 = var5.getName();

                    if (!var6.startsWith("custom_") && var6.endsWith(".properties") && var5.isFile() && var5.canRead())
                    {
                        Config.dbg("TextureAnimation: anim/" + var5.getName());
                        var3.add("/anim/" + var6);
                    }
                }

                String[] var7 = (String[])((String[])var3.toArray(new String[var3.size()]));
                return var7;
            }
        }
    }

    public static String[] getAnimationPropertiesZip(File var0)
    {
        try
        {
            ZipFile var1 = new ZipFile(var0);
            Enumeration var2 = var1.entries();
            ArrayList var3 = new ArrayList();

            while (var2.hasMoreElements())
            {
                ZipEntry var4 = (ZipEntry)var2.nextElement();
                String var5 = var4.getName();

                if (var5.startsWith("anim/") && !var5.startsWith("anim/custom_") && var5.endsWith(".properties"))
                {
                    var3.add("/" + var5);
                }
            }

            String[] var7 = (String[])((String[])var3.toArray(new String[var3.size()]));
            return var7;
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
            return null;
        }
    }

    public static byte[] getCustomTextureData(String var0, int var1)
    {
        byte[] var2 = loadImage(var0, var1);

        if (var2 == null)
        {
            var2 = loadImage("/anim" + var0, var1);
        }

        return var2;
    }

    private static byte[] loadImage(String var0, int var1)
    {
        ITexturePack var2 = renderEngine.getTexturePack().getSelectedTexturePack();
        GameSettings var3 = Config.getGameSettings();

        try
        {
            if (var2 == null)
            {
                return null;
            }
            else
            {
                InputStream var5 = var2.getResourceAsStream(var0);

                if (var5 == null)
                {
                    return null;
                }
                else
                {
                    BufferedImage var6 = readTextureImage(var5);

                    if (var6 == null)
                    {
                        return null;
                    }
                    else
                    {
                        if (var1 > 0 && var6.getWidth() != var1)
                        {
                            double var7 = (double)(var6.getHeight() / var6.getWidth());
                            int var9 = (int)((double)var1 * var7);
                            var6 = scaleBufferedImage(var6, var1, var9);
                        }

                        int var21 = var6.getWidth();
                        int var8 = var6.getHeight();
                        int[] var22 = new int[var21 * var8];
                        byte[] var10 = new byte[var21 * var8 * 4];
                        var6.getRGB(0, 0, var21, var8, var22, 0, var21);

                        for (int var11 = 0; var11 < var22.length; ++var11)
                        {
                            int var12 = var22[var11] >> 24 & 255;
                            int var13 = var22[var11] >> 16 & 255;
                            int var14 = var22[var11] >> 8 & 255;
                            int var15 = var22[var11] & 255;

                            if (var3 != null && var3.anaglyph)
                            {
                                int var16 = (var13 * 30 + var14 * 59 + var15 * 11) / 100;
                                int var17 = (var13 * 30 + var14 * 70) / 100;
                                int var18 = (var13 * 30 + var15 * 70) / 100;
                                var13 = var16;
                                var14 = var17;
                                var15 = var18;
                            }

                            var10[var11 * 4 + 0] = (byte)var13;
                            var10[var11 * 4 + 1] = (byte)var14;
                            var10[var11 * 4 + 2] = (byte)var15;
                            var10[var11 * 4 + 3] = (byte)var12;
                        }

                        return var10;
                    }
                }
            }
        }
        catch (FileNotFoundException var19)
        {
            return null;
        }
        catch (Exception var20)
        {
            var20.printStackTrace();
            return null;
        }
    }

    private static BufferedImage readTextureImage(InputStream var0) throws IOException
    {
        BufferedImage var1 = ImageIO.read(var0);
        var0.close();
        return var1;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage var0, int var1, int var2)
    {
        BufferedImage var3 = new BufferedImage(var1, var2, 2);
        Graphics2D var4 = var3.createGraphics();
        var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        var4.drawImage(var0, 0, 0, var1, var2, (ImageObserver)null);
        return var3;
    }
}
