package net.minecraft.src;

import net.minecraft.src.WrUpdateThread$1;
import net.minecraft.src.WrUpdateThread$ThreadUpdateControl;

class WrUpdateThread$ThreadUpdateListener implements IWrUpdateListener
{
    private WrUpdateThread$ThreadUpdateControl tuc;

    final WrUpdateThread this$0;

    private WrUpdateThread$ThreadUpdateListener(WrUpdateThread var1)
    {
        this.this$0 = var1;
        this.tuc = new WrUpdateThread$ThreadUpdateControl(this.this$0, (WrUpdateThread$1)null);
    }

    public void updating(IWrUpdateControl var1)
    {
        this.tuc.setUpdateControl(var1);
        WrUpdateThread.access$300(this.this$0, this.tuc);
    }

    WrUpdateThread$ThreadUpdateListener(WrUpdateThread var1, WrUpdateThread$1 var2)
    {
        this(var1);
    }
}
