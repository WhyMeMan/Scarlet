package net.minecraft.src;

import java.util.HashSet;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class WorldRendererThreaded extends WorldRenderer
{
    private int glRenderListStable;
    private int glRenderListBoundingBox;

    public WorldRendererThreaded(World var1, List var2, int var3, int var4, int var5, int var6)
    {
        super(var1, var2, var3, var4, var5, var6);
        this.glRenderListStable = this.glRenderList + 393216;
        this.glRenderListBoundingBox = this.glRenderList + 2;
    }

    /**
     * Will update this chunk renderer
     */
    public void updateRenderer()
    {
        if (this.worldObj != null)
        {
            this.updateRenderer((IWrUpdateListener)null);
            this.finishUpdate();
        }
    }

    public void updateRenderer(IWrUpdateListener var1)
    {
        if (this.worldObj != null)
        {
            this.needsUpdate = false;
            int var2 = this.posX;
            int var3 = this.posY;
            int var4 = this.posZ;
            int var5 = this.posX + 16;
            int var6 = this.posY + 16;
            int var7 = this.posZ + 16;
            boolean[] var8 = new boolean[2];

            for (int var9 = 0; var9 < var8.length; ++var9)
            {
                var8[var9] = true;
            }

            if (Reflector.LightCache.exists())
            {
                Object var28 = Reflector.getFieldValue(Reflector.LightCache_cache);
                Reflector.callVoid(var28, Reflector.LightCache_clear, new Object[0]);
                Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }

            Chunk.isLit = false;
            HashSet var27 = new HashSet();
            var27.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            byte var10 = 1;
            ChunkCache var11 = new ChunkCache(this.worldObj, var2 - var10, var3 - var10, var4 - var10, var5 + var10, var6 + var10, var7 + var10, var10);

            if (!var11.extendedLevelsInChunkCache())
            {
                ++chunksUpdated;
                RenderBlocks var12 = new RenderBlocks(var11);
                this.bytesDrawn = 0;
                Tessellator var13 = Tessellator.instance;
                boolean var14 = Reflector.ForgeHooksClient.exists();
                WrUpdateControl var15 = new WrUpdateControl();

                for (int var16 = 0; var16 < 2; ++var16)
                {
                    var15.setRenderPass(var16);
                    boolean var17 = false;
                    boolean var18 = false;
                    boolean var19 = false;

                    for (int var20 = var3; var20 < var6; ++var20)
                    {
                        if (var18 && var1 != null)
                        {
                            var1.updating(var15);
                        }

                        for (int var21 = var4; var21 < var7; ++var21)
                        {
                            for (int var22 = var2; var22 < var5; ++var22)
                            {
                                int var23 = var11.getBlockId(var22, var20, var21);

                                if (var23 > 0)
                                {
                                    if (!var19)
                                    {
                                        var19 = true;
                                        GL11.glNewList(this.glRenderList + var16, GL11.GL_COMPILE);
                                        var13.setRenderingChunk(true);
                                        var13.startDrawingQuads();
                                        var13.setTranslation((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                                    }

                                    Block var24 = Block.blocksList[var23];

                                    if (var16 == 0 && var24.hasTileEntity())
                                    {
                                        TileEntity var25 = var11.getBlockTileEntity(var22, var20, var21);

                                        if (TileEntityRenderer.instance.hasSpecialRenderer(var25))
                                        {
                                            this.tileEntityRenderers.add(var25);
                                        }
                                    }

                                    int var31 = var24.getRenderBlockPass();
                                    boolean var26 = true;

                                    if (var31 != var16)
                                    {
                                        var17 = true;
                                        var26 = false;
                                    }

                                    if (var14)
                                    {
                                        var26 = Reflector.callBoolean(var24, Reflector.ForgeBlock_canRenderInPass, new Object[] {Integer.valueOf(var16)});
                                    }

                                    if (var26)
                                    {
                                        var18 |= var12.renderBlockByRenderType(var24, var22, var20, var21);
                                    }
                                }
                            }
                        }
                    }

                    if (var19)
                    {
                        if (var1 != null)
                        {
                            var1.updating(var15);
                        }

                        this.bytesDrawn += var13.draw();
                        GL11.glEndList();
                        var13.setRenderingChunk(false);
                        var13.setTranslation(0.0D, 0.0D, 0.0D);
                    }
                    else
                    {
                        var18 = false;
                    }

                    if (var18)
                    {
                        var8[var16] = false;
                    }

                    if (!var17)
                    {
                        break;
                    }
                }
            }

            for (int var29 = 0; var29 < 2; ++var29)
            {
                this.skipRenderPass[var29] = var8[var29];
            }

            HashSet var30 = new HashSet();
            var30.addAll(this.tileEntityRenderers);
            var30.removeAll(var27);
            this.tileEntities.addAll(var30);
            var27.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(var27);
            this.isChunkLit = Chunk.isLit;
            this.isInitialized = true;
            this.isVisible = true;
            this.isVisibleFromPosition = false;
        }
    }

    public void finishUpdate()
    {
        int var1 = this.glRenderList;
        this.glRenderList = this.glRenderListStable;
        this.glRenderListStable = var1;

        for (int var2 = 0; var2 < 2; ++var2)
        {
            if (!this.skipRenderPass[var2])
            {
                GL11.glNewList(this.glRenderList + var2, GL11.GL_COMPILE);
                GL11.glEndList();
            }
        }

        if (this.needsBoxUpdate && !this.skipAllRenderPasses())
        {
            float var3 = 0.0F;
            GL11.glNewList(this.glRenderListBoundingBox, GL11.GL_COMPILE);
            RenderItem.renderAABB(AxisAlignedBB.getAABBPool().getAABB((double)((float)this.posXClip - var3), (double)((float)this.posYClip - var3), (double)((float)this.posZClip - var3), (double)((float)(this.posXClip + 16) + var3), (double)((float)(this.posYClip + 16) + var3), (double)((float)(this.posZClip + 16) + var3)));
            GL11.glEndList();
            this.needsBoxUpdate = false;
        }
    }

    /**
     * Takes in the pass the call list is being requested for. Args: renderPass
     */
    public int getGLCallListForPass(int var1)
    {
        return !this.isInFrustum ? -1 : (!this.skipRenderPass[var1] ? this.glRenderListStable + var1 : -1);
    }

    /**
     * Renders the occlusion query GL List
     */
    public void callOcclusionQueryList()
    {
        GL11.glCallList(this.glRenderListBoundingBox);
    }
}
