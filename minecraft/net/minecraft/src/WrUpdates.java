package net.minecraft.src;

import java.util.List;

public class WrUpdates
{
    private static IWrUpdater wrUpdater = null;

    public static void setWrUpdater(IWrUpdater var0)
    {
        if (wrUpdater != null)
        {
            wrUpdater.terminate();
        }

        wrUpdater = var0;

        if (wrUpdater != null)
        {
            try
            {
                wrUpdater.initialize();
            }
            catch (Exception var2)
            {
                wrUpdater = null;
                var2.printStackTrace();
            }
        }
    }

    public static boolean hasWrUpdater()
    {
        return wrUpdater != null;
    }

    public static IWrUpdater getWrUpdater()
    {
        return wrUpdater;
    }

    public static WorldRenderer makeWorldRenderer(World var0, List var1, int var2, int var3, int var4, int var5)
    {
        return wrUpdater == null ? new WorldRenderer(var0, var1, var2, var3, var4, var5) : wrUpdater.makeWorldRenderer(var0, var1, var2, var3, var4, var5);
    }

    public static boolean updateRenderers(RenderGlobal var0, EntityLiving var1, boolean var2)
    {
        try
        {
            return wrUpdater.updateRenderers(var0, var1, var2);
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
            setWrUpdater((IWrUpdater)null);
            return false;
        }
    }

    public static void resumeBackgroundUpdates()
    {
        if (wrUpdater != null)
        {
            wrUpdater.resumeBackgroundUpdates();
        }
    }

    public static void pauseBackgroundUpdates()
    {
        if (wrUpdater != null)
        {
            wrUpdater.pauseBackgroundUpdates();
        }
    }

    public static void finishCurrentUpdate()
    {
        if (wrUpdater != null)
        {
            wrUpdater.finishCurrentUpdate();
        }
    }

    public static void preRender(RenderGlobal var0, EntityLiving var1)
    {
        if (wrUpdater != null)
        {
            wrUpdater.preRender(var0, var1);
        }
    }

    public static void postRender()
    {
        if (wrUpdater != null)
        {
            wrUpdater.postRender();
        }
    }
}
