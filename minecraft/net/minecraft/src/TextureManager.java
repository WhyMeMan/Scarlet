package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;

public class TextureManager
{
    private static TextureManager instance;
    private int nextTextureID = 0;
    private final HashMap texturesMap = new HashMap();
    private final HashMap mapNameToId = new HashMap();

    public static void init()
    {
        instance = new TextureManager();
    }

    public static TextureManager instance()
    {
        return instance;
    }

    public int getNextTextureId()
    {
        return this.nextTextureID++;
    }

    public void registerTexture(String par1Str, Texture par2Texture)
    {
        this.mapNameToId.put(par1Str, Integer.valueOf(par2Texture.getTextureId()));

        if (!this.texturesMap.containsKey(Integer.valueOf(par2Texture.getTextureId())))
        {
            this.texturesMap.put(Integer.valueOf(par2Texture.getTextureId()), par2Texture);
        }
    }

    public void registerTexture(Texture par1Texture)
    {
        if (this.texturesMap.containsValue(par1Texture))
        {
            Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.registerTexture called, but this texture has already been registered. ignoring.");
        }
        else
        {
            this.texturesMap.put(Integer.valueOf(par1Texture.getTextureId()), par1Texture);
        }
    }

    public Stitcher createStitcher(String par1Str)
    {
        int var2 = Minecraft.getGLMaximumTextureSize();
        return new Stitcher(par1Str, var2, var2, true);
    }

    public List createTexture(String par1Str)
    {
        return this.createNewTexture(this.getBasename(par1Str), par1Str, (TextureStitched)null);
    }

    public List createNewTexture(String var1, String var2, TextureStitched var3)
    {
        ArrayList var4 = new ArrayList();
        ITexturePack var5 = Minecraft.getMinecraft().texturePackList.getSelectedTexturePack();

        try
        {
            BufferedImage var6 = null;
            FileNotFoundException var7 = null;

            try
            {
                var6 = ImageIO.read(var5.getResourceAsStream("/" + var2));
            }
            catch (FileNotFoundException var19)
            {
                var7 = var19;
            }

            if (var3 != null && var3.loadTexture(this, var5, var1, var2, var6, var4))
            {
                return var4;
            }

            if (var7 != null)
            {
                throw var7;
            }

            int var8 = var6.getHeight();
            int var9 = var6.getWidth();
            String var10 = var1;
            int var11 = var6.getWidth();
            int var12 = var6.getHeight();
            boolean var13 = var12 > var11 && var12 / var11 * var11 == var12;

            if (!var13 && !this.hasAnimationTxt(var2, var5))
            {
                if (var9 == var8)
                {
                    var4.add(this.makeTexture(var1, 2, var9, var8, 10496, 6408, 9728, 9728, false, var6));
                }
                else
                {
                    Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture: Skipping " + var2 + " because of broken aspect ratio and not animation");
                }
            }
            else
            {
                int var14 = var9;
                int var15 = var9;
                int var16 = var8 / var9;

                for (int var17 = 0; var17 < var16; ++var17)
                {
                    Texture var18 = this.makeTexture(var10, 2, var14, var15, 10496, 6408, 9728, 9728, false, var6.getSubimage(0, var15 * var17, var14, var15));
                    var4.add(var18);
                }
            }

            return var4;
        }
        catch (FileNotFoundException var20)
        {
            Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture called for file " + var2 + ", but that file does not exist. Ignoring.");
        }
        catch (IOException var21)
        {
            Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture encountered an IOException when trying to read file " + var2 + ". Ignoring.");
        }

        return var4;
    }

    /**
     * Strips directory and file extension from the specified path, returning only the filename
     */
    private String getBasename(String par1Str)
    {
        if (!par1Str.startsWith("ctm/") && !par1Str.startsWith("mods/"))
        {
            File var2 = new File(par1Str);
            return var2.getName().substring(0, var2.getName().lastIndexOf(46));
        }
        else
        {
            return par1Str.substring(0, par1Str.lastIndexOf(46));
        }
    }

    /**
     * Returns true if specified texture pack contains animation data for the specified texture file
     */
    private boolean hasAnimationTxt(String par1Str, ITexturePack par2ITexturePack)
    {
        String var3 = "/" + par1Str.substring(0, par1Str.lastIndexOf(46)) + ".txt";
        boolean var4 = par2ITexturePack.func_98138_b("/" + par1Str, false);
        return Minecraft.getMinecraft().texturePackList.getSelectedTexturePack().func_98138_b(var3, !var4);
    }

    public Texture makeTexture(String par1Str, int par2, int par3, int par4, int par5, int par6, int par7, int par8, boolean par9, BufferedImage par10BufferedImage)
    {
        Texture var11 = new Texture(par1Str, par2, par3, par4, par5, par6, par7, par8, par10BufferedImage);
        this.registerTexture(var11);
        return var11;
    }

    public Texture createEmptyTexture(String par1Str, int par2, int par3, int par4, int par5)
    {
        return this.makeTexture(par1Str, par2, par3, par4, 10496, par5, 9728, 9728, false, (BufferedImage)null);
    }
}
