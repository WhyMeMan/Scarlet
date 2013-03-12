package net.minecraft.src;

import java.util.HashSet;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class WorldRendererSmooth extends WorldRenderer
{
    private WrUpdateState updateState = new WrUpdateState();
    public int activeSet = 0;
    public int[] activeListIndex = new int[] {0, 0};
    public int[][][] glWorkLists = new int[2][2][16];
    public boolean[] tempSkipRenderPass = new boolean[2];

    public WorldRendererSmooth(World var1, List var2, int var3, int var4, int var5, int var6)
    {
        super(var1, var2, var3, var4, var5, var6);
        int var7 = 393216 + 64 * (this.glRenderList / 3);

        for (int var8 = 0; var8 < 2; ++var8)
        {
            int var9 = var7 + var8 * 2 * 16;

            for (int var10 = 0; var10 < 2; ++var10)
            {
                int var11 = var9 + var10 * 16;

                for (int var12 = 0; var12 < 16; ++var12)
                {
                    this.glWorkLists[var8][var10][var12] = var11 + var12;
                }
            }
        }
    }

    /**
     * Sets a new position for the renderer and setting it up so it can be reloaded with the new data for that position
     */
    public void setPosition(int var1, int var2, int var3)
    {
        if (this.isUpdating)
        {
            this.updateRenderer();
        }

        super.setPosition(var1, var2, var3);
    }

    /**
     * Will update this chunk renderer
     */
    public void updateRenderer()
    {
        if (this.worldObj != null)
        {
            this.updateRenderer(0L);
            this.finishUpdate();
        }
    }

    public boolean updateRenderer(long var1)
    {
        if (this.worldObj == null)
        {
            return true;
        }
        else
        {
            this.needsUpdate = false;

            if (!this.isUpdating)
            {
                if (this.needsBoxUpdate)
                {
                    float var3 = 0.0F;
                    GL11.glNewList(this.glRenderList + 2, GL11.GL_COMPILE);
                    RenderItem.renderAABB(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)((float)this.posXClip - var3), (double)((float)this.posYClip - var3), (double)((float)this.posZClip - var3), (double)((float)(this.posXClip + 16) + var3), (double)((float)(this.posYClip + 16) + var3), (double)((float)(this.posZClip + 16) + var3)));
                    GL11.glEndList();
                    this.needsBoxUpdate = false;
                }

                if (Reflector.LightCache.exists())
                {
                    Object var25 = Reflector.getFieldValue(Reflector.LightCache_cache);
                    Reflector.callVoid(var25, Reflector.LightCache_clear, new Object[0]);
                    Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
                }

                Chunk.isLit = false;
            }

            int var26 = this.posX;
            int var4 = this.posY;
            int var5 = this.posZ;
            int var6 = this.posX + 16;
            int var7 = this.posY + 16;
            int var8 = this.posZ + 16;
            ChunkCache var9 = null;
            RenderBlocks var10 = null;
            HashSet var11 = null;

            if (!this.isUpdating)
            {
                for (int var12 = 0; var12 < 2; ++var12)
                {
                    this.tempSkipRenderPass[var12] = true;
                }

                byte var27 = 1;
                var9 = new ChunkCache(this.worldObj, var26 - var27, var4 - var27, var5 - var27, var6 + var27, var7 + var27, var8 + var27);
                var10 = new RenderBlocks(var9);
                var11 = new HashSet();
                var11.addAll(this.tileEntityRenderers);
                this.tileEntityRenderers.clear();
            }

            if (this.isUpdating || !var9.extendedLevelsInChunkCache())
            {
                this.bytesDrawn = 0;
                Tessellator var28 = Tessellator.instance;
                boolean var13 = Reflector.ForgeHooksClient.exists();

                for (int var14 = 0; var14 < 2; ++var14)
                {
                    boolean var15 = false;
                    boolean var16 = false;
                    boolean var17 = false;

                    for (int var18 = var4; var18 < var7; ++var18)
                    {
                        if (this.isUpdating)
                        {
                            this.isUpdating = false;
                            var9 = this.updateState.chunkcache;
                            var10 = this.updateState.renderblocks;
                            var11 = this.updateState.setOldEntityRenders;
                            var14 = this.updateState.renderPass;
                            var18 = this.updateState.y;
                            var15 = this.updateState.flag;
                            var16 = this.updateState.hasRenderedBlocks;
                            var17 = this.updateState.hasGlList;

                            if (var17)
                            {
                                GL11.glNewList(this.glWorkLists[this.activeSet][var14][this.activeListIndex[var14]], GL11.GL_COMPILE);

                                if (var13)
                                {
                                    Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[] {Integer.valueOf(var14)});
                                }

                                var28.setRenderingChunk(true);
                                var28.startDrawingQuads();
                                var28.setTranslation((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                            }
                        }
                        else if (var17 && var1 != 0L && System.nanoTime() - var1 > 0L && this.activeListIndex[var14] < 15)
                        {
                            if (var13)
                            {
                                Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[] {Integer.valueOf(var14)});
                            }

                            var28.draw();
                            GL11.glEndList();
                            var28.setRenderingChunk(false);
                            var28.setTranslation(0.0D, 0.0D, 0.0D);
                            ++this.activeListIndex[var14];
                            this.updateState.chunkcache = var9;
                            this.updateState.renderblocks = var10;
                            this.updateState.setOldEntityRenders = var11;
                            this.updateState.renderPass = var14;
                            this.updateState.y = var18;
                            this.updateState.flag = var15;
                            this.updateState.hasRenderedBlocks = var16;
                            this.updateState.hasGlList = var17;
                            this.isUpdating = true;
                            return false;
                        }

                        for (int var19 = var5; var19 < var8; ++var19)
                        {
                            for (int var20 = var26; var20 < var6; ++var20)
                            {
                                int var21 = var9.getBlockId(var20, var18, var19);

                                if (var21 > 0)
                                {
                                    if (!var17)
                                    {
                                        var17 = true;
                                        GL11.glNewList(this.glWorkLists[this.activeSet][var14][this.activeListIndex[var14]], GL11.GL_COMPILE);

                                        if (var13)
                                        {
                                            Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[] {Integer.valueOf(var14)});
                                        }

                                        var28.setRenderingChunk(true);
                                        var28.startDrawingQuads();
                                        var28.setTranslation((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                                    }

                                    Block var22 = Block.blocksList[var21];

                                    if (var14 == 0 && var22.hasTileEntity())
                                    {
                                        TileEntity var23 = var9.getBlockTileEntity(var20, var18, var19);

                                        if (TileEntityRenderer.instance.hasSpecialRenderer(var23))
                                        {
                                            this.tileEntityRenderers.add(var23);
                                        }
                                    }

                                    int var30 = var22.getRenderBlockPass();
                                    boolean var24 = true;

                                    if (var30 != var14)
                                    {
                                        var15 = true;
                                        var24 = false;
                                    }

                                    if (var13)
                                    {
                                        var24 = Reflector.callBoolean(var22, Reflector.ForgeBlock_canRenderInPass, new Object[] {Integer.valueOf(var14)});
                                    }

                                    if (var24)
                                    {
                                        if (var13)
                                        {
                                            Reflector.callVoid(Reflector.ForgeHooksClient_beforeBlockRender, new Object[] {var22, var10});
                                        }

                                        var16 |= var10.renderBlockByRenderType(var22, var20, var18, var19);

                                        if (var13)
                                        {
                                            Reflector.callVoid(Reflector.ForgeHooksClient_afterBlockRender, new Object[] {var22, var10});
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (var17)
                    {
                        if (var13)
                        {
                            Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[] {Integer.valueOf(var14)});
                        }

                        this.bytesDrawn += var28.draw();
                        GL11.glEndList();
                        var28.setRenderingChunk(false);
                        var28.setTranslation(0.0D, 0.0D, 0.0D);
                    }
                    else
                    {
                        var16 = false;
                    }

                    if (var16)
                    {
                        this.tempSkipRenderPass[var14] = false;
                    }

                    if (!var15)
                    {
                        break;
                    }
                }
            }

            HashSet var29 = new HashSet();
            var29.addAll(this.tileEntityRenderers);
            var29.removeAll(var11);
            this.tileEntities.addAll(var29);
            var11.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(var11);
            this.isChunkLit = Chunk.isLit;
            this.isInitialized = true;
            ++chunksUpdated;
            this.isVisible = true;
            this.isVisibleFromPosition = false;
            this.skipRenderPass[0] = this.tempSkipRenderPass[0];
            this.skipRenderPass[1] = this.tempSkipRenderPass[1];
            this.isUpdating = false;
            return true;
        }
    }

    public void finishUpdate()
    {
        int var1;
        int var2;
        int var3;

        for (var1 = 0; var1 < 2; ++var1)
        {
            if (!this.skipRenderPass[var1])
            {
                GL11.glNewList(this.glRenderList + var1, GL11.GL_COMPILE);

                for (var2 = 0; var2 <= this.activeListIndex[var1]; ++var2)
                {
                    var3 = this.glWorkLists[this.activeSet][var1][var2];
                    GL11.glCallList(var3);
                }

                GL11.glEndList();
            }
        }

        if (this.activeSet == 0)
        {
            this.activeSet = 1;
        }
        else
        {
            this.activeSet = 0;
        }

        for (var1 = 0; var1 < 2; ++var1)
        {
            if (!this.skipRenderPass[var1])
            {
                for (var2 = 0; var2 <= this.activeListIndex[var1]; ++var2)
                {
                    var3 = this.glWorkLists[this.activeSet][var1][var2];
                    GL11.glNewList(var3, GL11.GL_COMPILE);
                    GL11.glEndList();
                }
            }
        }

        for (var1 = 0; var1 < 2; ++var1)
        {
            this.activeListIndex[var1] = 0;
        }
    }
}
