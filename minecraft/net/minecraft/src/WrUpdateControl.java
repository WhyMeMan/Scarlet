package net.minecraft.src;

public class WrUpdateControl implements IWrUpdateControl
{
    private boolean hasForge;
    private int renderPass;

    public WrUpdateControl()
    {
        this.hasForge = Reflector.ForgeHooksClient.exists();
        this.renderPass = 0;
    }

    public void resume()
    {
        if (this.hasForge)
        {
            Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[] {Integer.valueOf(this.renderPass)});
        }
    }

    public void pause()
    {
        AxisAlignedBB.getAABBPool().cleanPool();
        WorldClient var1 = Config.getMinecraft().theWorld;

        if (var1 != null)
        {
            var1.getWorldVec3Pool().clear();
        }

        if (this.hasForge)
        {
            Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[] {Integer.valueOf(this.renderPass)});
        }
    }

    public void setRenderPass(int var1)
    {
        this.renderPass = var1;
    }
}
