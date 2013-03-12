package net.minecraft.src;

import java.util.List;

public class WrUpdaterSmooth implements IWrUpdater
{
    private long lastUpdateStartTimeNs = 0L;
    private long updateStartTimeNs = 0L;
    private long updateTimeNs = 10000000L;
    private WorldRendererSmooth currentUpdateRenderer = null;
    private int renderersUpdated = 0;
    private int renderersFound = 0;

    public void initialize() {}

    public void terminate() {}

    public WorldRenderer makeWorldRenderer(World var1, List var2, int var3, int var4, int var5, int var6)
    {
        return new WorldRendererSmooth(var1, var2, var3, var4, var5, var6);
    }

    public boolean updateRenderers(RenderGlobal var1, EntityLiving var2, boolean var3)
    {
        this.lastUpdateStartTimeNs = this.updateStartTimeNs;
        this.updateStartTimeNs = System.nanoTime();
        long var4 = this.updateStartTimeNs + this.updateTimeNs;
        int var6 = Config.getUpdatesPerFrame();

        if (Config.isDynamicUpdates() && !var1.isMoving(var2))
        {
            var6 *= 3;
        }

        this.renderersUpdated = 0;

        do
        {
            this.renderersFound = 0;
            this.updateRenderersImpl(var1, var2, var3);
        }
        while (this.renderersFound > 0 && System.nanoTime() - var4 < 0L);

        if (this.renderersFound > 0)
        {
            var6 = Math.min(var6, this.renderersFound);
            long var7 = 400000L;

            if (this.renderersUpdated > var6)
            {
                this.updateTimeNs -= 2L * var7;
            }

            if (this.renderersUpdated < var6)
            {
                this.updateTimeNs += var7;
            }
        }
        else
        {
            this.updateTimeNs = 0L;
            this.updateTimeNs -= 200000L;
        }

        if (this.updateTimeNs < 0L)
        {
            this.updateTimeNs = 0L;
        }

        return this.renderersUpdated > 0;
    }

    private void updateRenderersImpl(RenderGlobal var1, EntityLiving var2, boolean var3)
    {
        this.renderersFound = 0;
        boolean var4 = true;

        if (this.currentUpdateRenderer != null)
        {
            ++this.renderersFound;
            var4 = this.updateRenderer(this.currentUpdateRenderer);

            if (var4)
            {
                ++this.renderersUpdated;
            }
        }

        if (var1.worldRenderersToUpdate.size() > 0)
        {
            byte var5 = 4;
            WorldRendererSmooth var6 = null;
            float var7 = Float.MAX_VALUE;
            int var8 = -1;
            int var9;

            for (var9 = 0; var9 < var1.worldRenderersToUpdate.size(); ++var9)
            {
                WorldRendererSmooth var10 = (WorldRendererSmooth)var1.worldRenderersToUpdate.get(var9);

                if (var10 != null)
                {
                    ++this.renderersFound;

                    if (!var10.needsUpdate)
                    {
                        var1.worldRenderersToUpdate.set(var9, (Object)null);
                    }
                    else
                    {
                        float var11 = var10.distanceToEntitySquared(var2);

                        if (var11 <= 256.0F && var1.isActingNow())
                        {
                            var10.updateRenderer();
                            var10.needsUpdate = false;
                            var1.worldRenderersToUpdate.set(var9, (Object)null);
                            ++this.renderersUpdated;
                        }
                        else
                        {
                            if (!var10.isInFrustum)
                            {
                                var11 *= (float)var5;
                            }

                            if (var6 == null)
                            {
                                var6 = var10;
                                var7 = var11;
                                var8 = var9;
                            }
                            else if (var11 < var7)
                            {
                                var6 = var10;
                                var7 = var11;
                                var8 = var9;
                            }
                        }
                    }
                }
            }

            if (this.currentUpdateRenderer == null || var4)
            {
                int var15;

                if (var6 != null)
                {
                    var1.worldRenderersToUpdate.set(var8, (Object)null);

                    if (!this.updateRenderer(var6))
                    {
                        return;
                    }

                    ++this.renderersUpdated;

                    if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs)
                    {
                        return;
                    }

                    float var14 = var7 / 5.0F;

                    for (var15 = 0; var15 < var1.worldRenderersToUpdate.size(); ++var15)
                    {
                        WorldRendererSmooth var16 = (WorldRendererSmooth)var1.worldRenderersToUpdate.get(var15);

                        if (var16 != null)
                        {
                            float var12 = var16.distanceToEntitySquared(var2);

                            if (!var16.isInFrustum)
                            {
                                var12 *= (float)var5;
                            }

                            float var13 = Math.abs(var12 - var7);

                            if (var13 < var14)
                            {
                                var1.worldRenderersToUpdate.set(var15, (Object)null);

                                if (!this.updateRenderer(var16))
                                {
                                    return;
                                }

                                ++this.renderersUpdated;

                                if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs)
                                {
                                    break;
                                }
                            }
                        }
                    }
                }

                if (this.renderersFound == 0)
                {
                    var1.worldRenderersToUpdate.clear();
                }

                if (var1.worldRenderersToUpdate.size() > 100 && this.renderersFound < var1.worldRenderersToUpdate.size() * 4 / 5)
                {
                    var9 = 0;

                    for (var15 = 0; var15 < var1.worldRenderersToUpdate.size(); ++var15)
                    {
                        Object var17 = var1.worldRenderersToUpdate.get(var15);

                        if (var17 != null)
                        {
                            if (var15 != var9)
                            {
                                var1.worldRenderersToUpdate.set(var9, var17);
                            }

                            ++var9;
                        }
                    }

                    for (var15 = var1.worldRenderersToUpdate.size() - 1; var15 >= var9; --var15)
                    {
                        var1.worldRenderersToUpdate.remove(var15);
                    }
                }
            }
        }
    }

    private boolean updateRenderer(WorldRendererSmooth var1)
    {
        long var2 = this.updateStartTimeNs + this.updateTimeNs;
        var1.needsUpdate = false;
        boolean var4 = var1.updateRenderer(var2);

        if (!var4)
        {
            this.currentUpdateRenderer = var1;
            return false;
        }
        else
        {
            var1.finishUpdate();
            this.currentUpdateRenderer = null;
            return true;
        }
    }

    public void finishCurrentUpdate() {}

    public void resumeBackgroundUpdates() {}

    public void pauseBackgroundUpdates() {}

    public void preRender(RenderGlobal var1, EntityLiving var2) {}

    public void postRender() {}
}
