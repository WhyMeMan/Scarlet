package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Set;

public class TextureUtils
{
    private static Set atlasNames = makeAtlasNames();
    private static Set atlasIds = new HashSet();

    private static Set makeAtlasNames()
    {
        HashSet var0 = new HashSet();
        var0.add("/terrain.png");
        var0.add("/gui/items.png");
        var0.add("/ctm.png");
        var0.add("/eloraam/world/world1.png");
        var0.add("/gfx/buildcraft/blocks/blocks.png");
        return var0;
    }

    public static void textureCreated(String var0, int var1)
    {
        if (atlasNames.contains(var0))
        {
            atlasIds.add(Integer.valueOf(var1));
        }
    }

    public static void addAtlasName(String var0)
    {
        if (var0 != null)
        {
            atlasNames.add(var0);
            Config.dbg("TextureAtlas: " + var0);
        }
    }

    public static boolean isAtlasId(int var0)
    {
        return atlasIds.contains(Integer.valueOf(var0));
    }

    public static boolean isAtlasName(String var0)
    {
        return atlasNames.contains(var0);
    }

    public static BufferedImage fixTextureDimensions(String var0, BufferedImage var1)
    {
        if (var0.startsWith("/mob/zombie") || var0.startsWith("/mob/pigzombie"))
        {
            int var2 = var1.getWidth();
            int var3 = var1.getHeight();

            if (var2 == var3 * 2)
            {
                BufferedImage var4 = new BufferedImage(var2, var3 * 2, 2);
                Graphics2D var5 = var4.createGraphics();
                var5.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                var5.drawImage(var1, 0, 0, var2, var3, (ImageObserver)null);
                return var4;
            }
        }

        return var1;
    }
}
