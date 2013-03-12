package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMobs
{
    private static Map textureVariantsMap = new HashMap();
    private static boolean initialized = false;
    private static Random random = new Random();

    public static void entityLoaded(Entity var0)
    {
        if (var0.skinUrl == null)
        {
            if (var0 instanceof EntityLiving)
            {
                if (!(var0 instanceof EntityPlayer))
                {
                    EntityLiving var1 = (EntityLiving)var0;
                    WorldServer var2 = Config.getWorldServer();

                    if (var2 == null)
                    {
                        var1.skinUrl = "123" + var1.entityId;
                    }
                    else
                    {
                        Entity var3 = var2.getEntityByID(var0.entityId);

                        if (var3 instanceof EntityLiving)
                        {
                            EntityLiving var4 = (EntityLiving)var3;
                            int var5 = var4.persistentId;
                            var1.persistentId = var5;
                            var1.skinUrl = "" + var5;
                            var4.skinUrl = var1.skinUrl;
                        }
                    }
                }
            }
        }
    }

    public static void worldChanged(World var0, World var1)
    {
        if (var1 != null)
        {
            List var2 = var1.getLoadedEntityList();

            for (int var3 = 0; var3 < var2.size(); ++var3)
            {
                Entity var4 = (Entity)var2.get(var3);
                entityLoaded(var4);
            }
        }
    }

    public static int getTexture(String var0, String var1)
    {
        if (!initialized)
        {
            initialize();
        }

        if (var1 == null)
        {
            return -1;
        }
        else if (var0 == null)
        {
            return -1;
        }
        else if (var0.length() <= 1)
        {
            return -1;
        }
        else
        {
            char var2 = var0.charAt(0);

            if (var2 >= 48 && var2 <= 57)
            {
                int var3 = Math.abs(var0.hashCode());
                String[] var4 = (String[])((String[])textureVariantsMap.get(var1));

                if (var4 == null)
                {
                    var4 = getTextureVariants(var1);
                    textureVariantsMap.put(var1, var4);
                }

                if (var4 != null && var4.length > 0)
                {
                    int var5 = var3 % var4.length;
                    String var6 = var4[var5];
                    return Config.getMinecraft().renderEngine.getTexture(var6);
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                return -1;
            }
        }
    }

    private static String[] getTextureVariants(String var0)
    {
        RenderEngine var1 = Config.getMinecraft().renderEngine;
        var1.getTexture(var0);
        String[] var2 = new String[] {var0};
        int var3 = var0.lastIndexOf(46);

        if (var3 < 0)
        {
            return var2;
        }
        else
        {
            String var4 = var0.substring(0, var3);
            String var5 = var0.substring(var3);
            int var6 = getCountTextureVariants(var0, var4, var5);

            if (var6 <= 1)
            {
                return var2;
            }
            else
            {
                var2 = new String[var6];
                var2[0] = var0;

                for (int var7 = 1; var7 < var2.length; ++var7)
                {
                    int var8 = var7 + 1;
                    String var9 = var4 + var8 + var5;
                    var2[var7] = var9;
                    var1.getTexture(var9);
                }

                Config.dbg("RandomMobs: " + var0 + ", variants: " + var2.length);
                return var2;
            }
        }
    }

    private static int getCountTextureVariants(String var0, String var1, String var2)
    {
        RenderEngine var3 = Config.getMinecraft().renderEngine;
        short var4 = 1000;

        for (int var5 = 2; var5 < var4; ++var5)
        {
            String var6 = var1 + var5 + var2;

            try
            {
                InputStream var7 = var3.texturePack.getSelectedTexturePack().getResourceAsStream(var6);

                if (var7 == null)
                {
                    return var5 - 1;
                }

                var7.close();
            }
            catch (IOException var8)
            {
                return var5 - 1;
            }
        }

        return var4;
    }

    public static void resetTextures()
    {
        textureVariantsMap.clear();

        if (Config.isRandomMobs())
        {
            initialize();
        }
    }

    private static void initialize()
    {
        initialized = true;
        getTexture("100", "/mob/bat.png");
        getTexture("100", "/mob/cavespider.png");
        getTexture("100", "/mob/chicken.png");
        getTexture("100", "/mob/cow.png");
        getTexture("100", "/mob/creeper.png");
        getTexture("100", "/mob/enderman.png");
        getTexture("100", "/mob/ghast.png");
        getTexture("100", "/mob/ghast_fire.png");
        getTexture("100", "/mob/lava.png");
        getTexture("100", "/mob/ozelot.png");
        getTexture("100", "/mob/pig.png");
        getTexture("100", "/mob/pigzombie.png");
        getTexture("100", "/mob/sheep.png");
        getTexture("100", "/mob/sheep_fur.png");
        getTexture("100", "/mob/skeleton.png");
        getTexture("100", "/mob/skeleton_wither.png");
        getTexture("100", "/mob/slime.png");
        getTexture("100", "/mob/spider.png");
        getTexture("100", "/mob/squid.png");
        getTexture("100", "/mob/wolf.png");
        getTexture("100", "/mob/zombie.png");
    }
}
