package net.minecraft.src;

import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

public class WrUpdaterThreaded implements IWrUpdater
{
    private WrUpdateThread updateThread = null;
    private float timePerUpdateMs = 10.0F;
    private long updateStartTimeNs = 0L;
    private boolean firstUpdate = true;
    private int updateTargetNum = 0;

    public void terminate()
    {
        if (this.updateThread != null)
        {
            this.updateThread.terminate();
            this.updateThread.unpauseToEndOfUpdate();
        }
    }

    public void initialize() {}

    private void delayedInit()
    {
        if (this.updateThread == null)
        {
            this.createUpdateThread(Display.getDrawable());
        }
    }

    public WorldRenderer makeWorldRenderer(World var1, List var2, int var3, int var4, int var5, int var6)
    {
        return new WorldRendererThreaded(var1, var2, var3, var4, var5, var6);
    }

    public WrUpdateThread createUpdateThread(Drawable var1)
    {
        if (this.updateThread != null)
        {
            throw new IllegalStateException("UpdateThread is already existing");
        }
        else
        {
            try
            {
                Pbuffer var2 = new Pbuffer(1, 1, new PixelFormat(), var1);
                this.updateThread = new WrUpdateThread(var2);
                this.updateThread.setPriority(1);
                this.updateThread.start();
                this.updateThread.pause();
                return this.updateThread;
            }
            catch (Exception var3)
            {
                throw new RuntimeException(var3);
            }
        }
    }

    public boolean isUpdateThread()
    {
        return Thread.currentThread() == this.updateThread;
    }

    public static boolean isBackgroundChunkLoading()
    {
        return true;
    }

    public void preRender(RenderGlobal var1, EntityLiving var2)
    {
        this.updateTargetNum = 0;

        if (this.updateThread != null)
        {
            if (this.updateStartTimeNs == 0L)
            {
                this.updateStartTimeNs = System.nanoTime();
            }

            if (this.updateThread.hasWorkToDo())
            {
                this.updateTargetNum = Config.getUpdatesPerFrame();

                if (Config.isDynamicUpdates() && !var1.isMoving(var2))
                {
                    this.updateTargetNum *= 3;
                }

                this.updateTargetNum = Math.min(this.updateTargetNum, this.updateThread.getPendingUpdatesCount());

                if (this.updateTargetNum > 0)
                {
                    this.updateThread.unpause();
                }
            }
        }
    }

    public void postRender()
    {
        if (this.updateThread != null)
        {
            float var1 = 0.0F;

            if (this.updateTargetNum > 0)
            {
                long var2 = System.nanoTime() - this.updateStartTimeNs;
                float var4 = this.timePerUpdateMs * (1.0F + (float)(this.updateTargetNum - 1) / 2.0F);

                if (var4 > 0.0F)
                {
                    int var5 = (int)var4;
                    Config.sleep((long)var5);
                }

                this.updateThread.pause();
            }

            float var6 = 0.2F;

            if (this.updateTargetNum > 0)
            {
                int var3 = this.updateThread.resetUpdateCount();

                if (var3 < this.updateTargetNum)
                {
                    this.timePerUpdateMs += var6;
                }

                if (var3 > this.updateTargetNum)
                {
                    this.timePerUpdateMs -= var6;
                }

                if (var3 == this.updateTargetNum)
                {
                    this.timePerUpdateMs -= var6;
                }
            }
            else
            {
                this.timePerUpdateMs -= var6 / 5.0F;
            }

            if (this.timePerUpdateMs < 0.0F)
            {
                this.timePerUpdateMs = 0.0F;
            }

            this.updateStartTimeNs = System.nanoTime();
        }
    }

    public boolean updateRenderers(RenderGlobal var1, EntityLiving var2, boolean var3)
    {
        this.delayedInit();

        if (var1.worldRenderersToUpdate.size() <= 0)
        {
            return true;
        }
        else
        {
            int var4 = 0;
            byte var5 = 4;
            int var6 = 0;
            WorldRenderer var7 = null;
            float var8 = Float.MAX_VALUE;
            int var9 = -1;
            int var10;
            float var12;

            for (var10 = 0; var10 < var1.worldRenderersToUpdate.size(); ++var10)
            {
                WorldRenderer var11 = (WorldRenderer)var1.worldRenderersToUpdate.get(var10);

                if (var11 != null)
                {
                    ++var6;

                    if (!var11.isUpdating)
                    {
                        if (!var11.needsUpdate)
                        {
                            var1.worldRenderersToUpdate.set(var10, (Object)null);
                        }
                        else
                        {
                            var12 = var11.distanceToEntitySquared(var2);

                            if (var12 < 512.0F)
                            {
                                if (var12 < 256.0F && var1.isActingNow() && var11.isInFrustum || this.firstUpdate)
                                {
                                    if (this.updateThread != null)
                                    {
                                        this.updateThread.unpauseToEndOfUpdate();
                                    }

                                    var11.updateRenderer();
                                    var11.needsUpdate = false;
                                    var1.worldRenderersToUpdate.set(var10, (Object)null);
                                    ++var4;
                                    continue;
                                }

                                if (this.updateThread != null)
                                {
                                    this.updateThread.addRendererToUpdate(var11, true);
                                    var11.needsUpdate = false;
                                    var1.worldRenderersToUpdate.set(var10, (Object)null);
                                    ++var4;
                                    continue;
                                }
                            }

                            if (!var11.isInFrustum)
                            {
                                var12 *= (float)var5;
                            }

                            if (var7 == null)
                            {
                                var7 = var11;
                                var8 = var12;
                                var9 = var10;
                            }
                            else if (var12 < var8)
                            {
                                var7 = var11;
                                var8 = var12;
                                var9 = var10;
                            }
                        }
                    }
                }
            }

            var10 = Config.getUpdatesPerFrame();
            boolean var17 = false;

            if (Config.isDynamicUpdates() && !var1.isMoving(var2))
            {
                var10 *= 3;
                var17 = true;
            }

            if (this.updateThread != null)
            {
                var10 = this.updateThread.getUpdateCapacity();

                if (var10 <= 0)
                {
                    return true;
                }
            }

            int var13;

            if (var7 != null)
            {
                this.updateRenderer(var7);
                var1.worldRenderersToUpdate.set(var9, (Object)null);
                ++var4;
                var12 = var8 / 5.0F;

                for (var13 = 0; var13 < var1.worldRenderersToUpdate.size() && var4 < var10; ++var13)
                {
                    WorldRenderer var14 = (WorldRenderer)var1.worldRenderersToUpdate.get(var13);

                    if (var14 != null && !var14.isUpdating)
                    {
                        float var15 = var14.distanceToEntitySquared(var2);

                        if (!var14.isInFrustum)
                        {
                            var15 *= (float)var5;
                        }

                        float var16 = Math.abs(var15 - var8);

                        if (var16 < var12)
                        {
                            this.updateRenderer(var14);
                            var1.worldRenderersToUpdate.set(var13, (Object)null);
                            ++var4;
                        }
                    }
                }
            }

            if (var6 == 0)
            {
                var1.worldRenderersToUpdate.clear();
            }

            if (var1.worldRenderersToUpdate.size() > 100 && var6 < var1.worldRenderersToUpdate.size() * 4 / 5)
            {
                int var18 = 0;

                for (var13 = 0; var13 < var1.worldRenderersToUpdate.size(); ++var13)
                {
                    Object var19 = var1.worldRenderersToUpdate.get(var13);

                    if (var19 != null)
                    {
                        if (var13 != var18)
                        {
                            var1.worldRenderersToUpdate.set(var18, var19);
                        }

                        ++var18;
                    }
                }

                for (var13 = var1.worldRenderersToUpdate.size() - 1; var13 >= var18; --var13)
                {
                    var1.worldRenderersToUpdate.remove(var13);
                }
            }

            this.firstUpdate = false;
            return true;
        }
    }

    private void updateRenderer(WorldRenderer var1)
    {
        WrUpdateThread var2 = this.updateThread;

        if (var2 != null)
        {
            var2.addRendererToUpdate(var1, false);
            var1.needsUpdate = false;
        }
        else
        {
            var1.updateRenderer();
            var1.needsUpdate = false;
            var1.isUpdating = false;
        }
    }

    public void finishCurrentUpdate()
    {
        if (this.updateThread != null)
        {
            this.updateThread.unpauseToEndOfUpdate();
        }
    }

    public void resumeBackgroundUpdates()
    {
        if (this.updateThread != null)
        {
            this.updateThread.unpause();
        }
    }

    public void pauseBackgroundUpdates()
    {
        if (this.updateThread != null)
        {
            this.updateThread.pause();
        }
    }
}
