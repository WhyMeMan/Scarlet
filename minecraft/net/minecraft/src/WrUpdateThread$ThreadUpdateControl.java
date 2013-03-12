package net.minecraft.src;

import net.minecraft.src.WrUpdateThread$1;

class WrUpdateThread$ThreadUpdateControl implements IWrUpdateControl
{
    private IWrUpdateControl updateControl;
    private boolean paused;

    final WrUpdateThread this$0;

    private WrUpdateThread$ThreadUpdateControl(WrUpdateThread var1)
    {
        this.this$0 = var1;
        this.updateControl = null;
        this.paused = false;
    }

    public void pause()
    {
        if (!this.paused)
        {
            this.paused = true;
            this.updateControl.pause();
            Tessellator.instance = WrUpdateThread.access$000(this.this$0);
        }
    }

    public void resume()
    {
        if (this.paused)
        {
            this.paused = false;
            Tessellator.instance = WrUpdateThread.access$100(this.this$0);
            this.updateControl.resume();
        }
    }

    public void setUpdateControl(IWrUpdateControl var1)
    {
        this.updateControl = var1;
    }

    WrUpdateThread$ThreadUpdateControl(WrUpdateThread var1, WrUpdateThread$1 var2)
    {
        this(var1);
    }
}
