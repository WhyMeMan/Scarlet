package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMobs
{
    private static Map textureVariantsMap = new HashMap();
    private static RenderGlobal renderGlobal = null;
    private static boolean initialized = false;
    private static Random random = new Random();
    private static boolean working = false;

    public static void entityLoaded(Entity var0)
    {
        if (var0 instanceof EntityLiving)
        {
            if (!(var0 instanceof EntityPlayer))
            {
                EntityLiving var1 = (EntityLiving)var0;
                WorldServer var2 = Config.getWorldServer();

                if (var2 != null)
                {
                    Entity var3 = var2.getEntityByID(var0.entityId);

                    if (var3 instanceof EntityLiving)
                    {
                        EntityLiving var4 = (EntityLiving)var3;
                        int var5 = var4.persistentId;
                        var1.persistentId = var5;
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

    public static String getTexture(String var0)
    {
        if (working)
        {
            return var0;
        }
        else
        {
            String var3;

            try
            {
                working = true;

                if (!initialized)
                {
                    initialize();
                }

                if (renderGlobal == null)
                {
                    String var7 = var0;
                    return var7;
                }

                Entity var1 = renderGlobal.renderedEntity;
                String var8;

                if (var1 == null)
                {
                    var8 = var0;
                    return var8;
                }

                if (!(var1 instanceof EntityLiving))
                {
                    var8 = var0;
                    return var8;
                }

                EntityLiving var2 = (EntityLiving)var1;

                if (!var0.startsWith("/mob/"))
                {
                    var3 = var0;
                    return var3;
                }

                var3 = getTexture(var0, var2.persistentId);
            }
            finally
            {
                working = false;
            }

            return var3;
        }
    }

    private static String getTexture(String var0, int var1)
    {
        if (var1 <= 0)
        {
            return var0;
        }
        else
        {
            String[] var2 = (String[])((String[])textureVariantsMap.get(var0));

            if (var2 == null)
            {
                var2 = getTextureVariants(var0);
                textureVariantsMap.put(var0, var2);
            }

            if (var2 != null && var2.length > 0)
            {
                int var3 = var1 % var2.length;
                String var4 = var2[var3];
                return var4;
            }
            else
            {
                return var0;
            }
        }
    }

    private static String[] getTextureVariants(String var0)
    {
        RenderEngine var1 = Config.getRenderEngine();
        var1.getTexture(var0);
        String[] var2 = new String[0];
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
        RenderEngine var3 = Config.getRenderEngine();
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
        renderGlobal = Config.getRenderGlobal();

        if (renderGlobal != null)
        {
            initialized = true;
            ArrayList var0 = new ArrayList();
            var0.add("bat");
            var0.add("cat_black");
            var0.add("cat_red");
            var0.add("cat_siamese");
            var0.add("cavespider");
            var0.add("chicken");
            var0.add("cow");
            var0.add("creeper");
            var0.add("enderman");
            var0.add("enderman_eyes");
            var0.add("fire");
            var0.add("ghast");
            var0.add("ghast_fire");
            var0.add("lava");
            var0.add("ozelot");
            var0.add("pig");
            var0.add("pigman");
            var0.add("pigzombie");
            var0.add("redcow");
            var0.add("saddle");
            var0.add("sheep");
            var0.add("sheep_fur");
            var0.add("silverfish");
            var0.add("skeleton");
            var0.add("skeleton_wither");
            var0.add("slime");
            var0.add("snowman");
            var0.add("spider");
            var0.add("spider_eyes");
            var0.add("squid");
            var0.add("villager");
            var0.add("villager_golem");
            var0.add("wither");
            var0.add("wither_invul");
            var0.add("wolf");
            var0.add("wolf_angry");
            var0.add("wolf_collar");
            var0.add("wolf_tame");
            var0.add("zombie");
            var0.add("zombie_villager");

            for (int var1 = 0; var1 < var0.size(); ++var1)
            {
                String var2 = (String)var0.get(var1);
                String var3 = "/mob/" + var2 + ".png";
                getTexture(var3, 100);
            }
        }
    }
}
