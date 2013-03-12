package net.minecraft.src;

import java.util.LinkedList;
import java.util.List;
import net.minecraft.src.WrUpdateThread$1;
import net.minecraft.src.WrUpdateThread$ThreadUpdateListener;
import org.lwjgl.opengl.Pbuffer;

public class WrUpdateThread extends Thread
{
    private Pbuffer pbuffer = null;
    private Object lock = new Object();
    private List updateList = new LinkedList();
    private List updatedList = new LinkedList();
    private int updateCount = 0;
    private Tessellator mainTessellator;
    private Tessellator threadTessellator;
    private boolean working;
    private WorldRendererThreaded currentRenderer;
    private boolean canWork;
    private boolean canWorkToEndOfUpdate;
    private boolean terminated;
    private static final int MAX_UPDATE_CAPACITY = 10;

    public WrUpdateThread(Pbuffer var1)
    {
        super("WrUpdateThread");
        this.mainTessellator = Tessellator.instance;
        this.threadTessellator = new Tessellator(2097152);
        this.working = false;
        this.currentRenderer = null;
        this.canWork = false;
        this.canWorkToEndOfUpdate = false;
        this.terminated = false;
        this.pbuffer = var1;
    }

    public void run()
    {
        try
        {
            this.pbuffer.makeCurrent();
        }
        catch (Exception var8)
        {
            var8.printStackTrace();
        }

        WrUpdateThread$ThreadUpdateListener var1 = new WrUpdateThread$ThreadUpdateListener(this, (WrUpdateThread$1)null);

        while (!Thread.interrupted() && !this.terminated)
        {
            try
            {
                WorldRendererThreaded var2 = this.getRendererToUpdate();

                if (var2 == null)
                {
                    return;
                }

                this.checkCanWork((IWrUpdateControl)null);

                try
                {
                    this.currentRenderer = var2;
                    Tessellator.instance = this.threadTessellator;
                    var2.updateRenderer(var1);
                }
                finally
                {
                    Tessellator.instance = this.mainTessellator;
                }

                this.rendererUpdated(var2);
            }
            catch (Exception var9)
            {
                var9.printStackTrace();

                if (this.currentRenderer != null)
                {
                    this.currentRenderer.isUpdating = false;
                    this.currentRenderer.needsUpdate = true;
                }

                this.currentRenderer = null;
                this.working = false;
            }
        }
    }

    public void addRendererToUpdate(WorldRenderer var1, boolean var2)
    {
        Object var3 = this.lock;

        synchronized (this.lock)
        {
            if (var1.isUpdating)
            {
                throw new IllegalArgumentException("Renderer already updating");
            }
            else
            {
                if (var2)
                {
                    this.updateList.add(0, var1);
                }
                else
                {
                    this.updateList.add(var1);
                }

                var1.isUpdating = true;
                this.lock.notifyAll();
            }
        }
    }

    private WorldRendererThreaded getRendererToUpdate()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            while (this.updateList.size() <= 0)
            {
                try
                {
                    this.lock.wait(2000L);

                    if (this.terminated)
                    {
                        Object var10000 = null;
                        return (WorldRendererThreaded)var10000;
                    }
                }
                catch (InterruptedException var4)
                {
                    ;
                }
            }

            WorldRendererThreaded var2 = (WorldRendererThreaded)this.updateList.remove(0);
            this.lock.notifyAll();
            return var2;
        }
    }

    public boolean hasWorkToDo()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            return this.updateList.size() > 0 ? true : (this.currentRenderer != null ? true : this.working);
        }
    }

    public int getUpdateCapacity()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            return this.updateList.size() > 10 ? 0 : 10 - this.updateList.size();
        }
    }

    private void rendererUpdated(WorldRenderer var1)
    {
        Object var2 = this.lock;

        synchronized (this.lock)
        {
            this.updatedList.add(var1);
            ++this.updateCount;
            this.currentRenderer = null;
            this.working = false;
            this.lock.notifyAll();
        }
    }

    private void finishUpdatedRenderers()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            for (int var2 = 0; var2 < this.updatedList.size(); ++var2)
            {
                WorldRendererThreaded var3 = (WorldRendererThreaded)this.updatedList.get(var2);
                var3.finishUpdate();
                var3.isUpdating = false;
            }

            this.updatedList.clear();
        }
    }

    public void pause()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            this.canWork = false;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();

            while (this.working)
            {
                try
                {
                    this.lock.wait();
                }
                catch (InterruptedException var4)
                {
                    ;
                }
            }

            this.finishUpdatedRenderers();
        }
    }

    public void unpause()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            if (this.working)
            {
                Config.dbg("UpdateThread still working in unpause()!!!");
            }

            this.canWork = true;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
        }
    }

    public void unpauseToEndOfUpdate()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            if (this.working)
            {
                Config.dbg("UpdateThread still working in unpause()!!!");
            }

            if (this.currentRenderer != null)
            {
                while (this.currentRenderer != null)
                {
                    this.canWork = false;
                    this.canWorkToEndOfUpdate = true;
                    this.lock.notifyAll();

                    try
                    {
                        this.lock.wait();
                    }
                    catch (InterruptedException var4)
                    {
                        ;
                    }
                }

                this.pause();
            }
        }
    }

    private void checkCanWork(IWrUpdateControl var1)
    {
        Thread.yield();
        Object var2 = this.lock;

        synchronized (this.lock)
        {
            while (!this.canWork && (!this.canWorkToEndOfUpdate || this.currentRenderer == null))
            {
                if (var1 != null)
                {
                    var1.pause();
                }

                this.working = false;
                this.lock.notifyAll();

                try
                {
                    this.lock.wait();
                }
                catch (InterruptedException var5)
                {
                    ;
                }
            }

            this.working = true;

            if (var1 != null)
            {
                var1.resume();
            }

            this.lock.notifyAll();
        }
    }

    public void clearAllUpdates()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            this.unpauseToEndOfUpdate();
            this.updateList.clear();
            this.lock.notifyAll();
        }
    }

    public int getPendingUpdatesCount()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            int var2 = this.updateList.size();

            if (this.currentRenderer != null)
            {
                ++var2;
            }

            return var2;
        }
    }

    public int resetUpdateCount()
    {
        Object var1 = this.lock;

        synchronized (this.lock)
        {
            int var2 = this.updateCount;
            this.updateCount = 0;
            return var2;
        }
    }

    public void terminate()
    {
        this.terminated = true;
    }

    static Tessellator access$000(WrUpdateThread var0)
    {
        return var0.mainTessellator;
    }

    static Tessellator access$100(WrUpdateThread var0)
    {
        return var0.threadTessellator;
    }

    static void access$300(WrUpdateThread var0, IWrUpdateControl var1)
    {
        var0.checkCanWork(var1);
    }
}
