package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemRendererHD extends ItemRenderer
{
    private Minecraft minecraft = null;

    public ItemRendererHD(Minecraft var1)
    {
        super(var1);
        this.minecraft = var1;
    }

    /**
     * Renders the item stack for being in an entity's hand Args: itemStack
     */
    public void renderItem(EntityLiving var1, ItemStack var2, int var3)
    {
        boolean var4 = Reflector.MinecraftForgeClient.exists();

        if (var4)
        {
            Object var5 = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
            Object var6 = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[] {var2, var5});

            if (var6 != null)
            {
                super.renderItem(var1, var2, var3);
                return;
            }
        }

        boolean var21 = var2.getItem() instanceof ItemBlock;

        if (var21 && RenderBlocks.renderItemIn3d(Block.blocksList[var2.itemID].getRenderType()))
        {
            super.renderItem(var1, var2, var3);
        }
        else
        {
            int var22 = Config.getIconWidthTerrain();

            if (var22 < 16)
            {
                super.renderItem(var1, var2, var3);
            }
            else
            {
                GL11.glPushMatrix();
                int var7 = var1.getItemIcon(var2, var3);
                float var8 = 256.0F;
                String var9;

                if (var21)
                {
                    var9 = "/terrain.png";

                    if (var4)
                    {
                        var9 = Reflector.callString(var2.getItem(), Reflector.ForgeItem_getTextureFile, new Object[0]);
                    }

                    if (var9.equals("/terrain.png") && Config.isMultiTexture())
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Tessellator.getTileTextures(this.minecraft.renderEngine.getTexture(var9))[var7]);
                        var7 = 0;
                        var8 = 16.0F;
                    }
                    else
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.minecraft.renderEngine.getTexture(var9));
                    }

                    var22 = Config.getIconWidthTerrain();
                }
                else
                {
                    var9 = "/gui/items.png";

                    if (var4)
                    {
                        var9 = Reflector.callString(var2.getItem(), Reflector.ForgeItem_getTextureFile, new Object[0]);
                    }

                    if (var9.equals("/gui/items.png") && Config.isMultiTexture())
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Tessellator.getTileTextures(this.minecraft.renderEngine.getTexture(var9))[var7]);
                        var7 = 0;
                        var8 = 16.0F;
                    }
                    else
                    {
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.minecraft.renderEngine.getTexture(var9));
                    }

                    var22 = Config.getIconWidthItems();
                }

                Tessellator var23 = Tessellator.instance;
                float var11 = ((float)(var7 % 16 * 16) + 0.01F) / var8;
                float var12 = ((float)(var7 % 16 * 16) + 15.99F) / var8;
                float var13 = ((float)(var7 / 16 * 16) + 0.01F) / var8;
                float var14 = ((float)(var7 / 16 * 16) + 15.99F) / var8;
                float var15 = 0.0F;
                float var16 = 0.3F;
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glTranslatef(-var15, -var16, 0.0F);
                float var17 = 1.5F;
                GL11.glScalef(var17, var17, var17);
                GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
                this.renderItem3D(var23, var12, var13, var11, var14, var22);

                if (var2 != null && var2.hasEffect() && var3 == 0)
                {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    this.minecraft.renderEngine.bindTexture(this.minecraft.renderEngine.getTexture("%blur%/misc/glint.png"));
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float var18 = 0.76F;
                    GL11.glColor4f(0.5F * var18, 0.25F * var18, 0.8F * var18, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float var19 = 0.125F;
                    GL11.glScalef(var19, var19, var19);
                    float var20 = (float)(System.currentTimeMillis() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(var20, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    this.renderItem3D(var23, 0.0F, 0.0F, 1.0F, 1.0F, var22);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(var19, var19, var19);
                    var20 = (float)(System.currentTimeMillis() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-var20, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    this.renderItem3D(var23, 0.0F, 0.0F, 1.0F, 1.0F, var22);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }

                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glPopMatrix();
            }
        }
    }

    private void renderItem3D(Tessellator var1, float var2, float var3, float var4, float var5, int var6)
    {
        float var7 = 1.0F;
        float var8 = 0.0625F;
        var1.startDrawingQuads();
        var1.setNormal(0.0F, 0.0F, 1.0F);
        var1.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)var2, (double)var5);
        var1.addVertexWithUV((double)var7, 0.0D, 0.0D, (double)var4, (double)var5);
        var1.addVertexWithUV((double)var7, 1.0D, 0.0D, (double)var4, (double)var3);
        var1.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)var2, (double)var3);
        var1.draw();
        var1.startDrawingQuads();
        var1.setNormal(0.0F, 0.0F, -1.0F);
        var1.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - var8), (double)var2, (double)var3);
        var1.addVertexWithUV((double)var7, 1.0D, (double)(0.0F - var8), (double)var4, (double)var3);
        var1.addVertexWithUV((double)var7, 0.0D, (double)(0.0F - var8), (double)var4, (double)var5);
        var1.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - var8), (double)var2, (double)var5);
        var1.draw();
        float var9 = 1.0F / (float)(32 * var6);
        float var10 = 1.0F / (float)var6;
        var1.startDrawingQuads();
        var1.setNormal(-1.0F, 0.0F, 0.0F);
        int var11;
        float var12;
        float var13;
        float var14;

        for (var11 = 0; var11 < var6; ++var11)
        {
            var12 = (float)var11 / ((float)var6 * 1.0F);
            var13 = var2 + (var4 - var2) * var12 - var9;
            var14 = var7 * var12;
            var1.addVertexWithUV((double)var14, 0.0D, (double)(0.0F - var8), (double)var13, (double)var5);
            var1.addVertexWithUV((double)var14, 0.0D, 0.0D, (double)var13, (double)var5);
            var1.addVertexWithUV((double)var14, 1.0D, 0.0D, (double)var13, (double)var3);
            var1.addVertexWithUV((double)var14, 1.0D, (double)(0.0F - var8), (double)var13, (double)var3);
        }

        var1.draw();
        var1.startDrawingQuads();
        var1.setNormal(1.0F, 0.0F, 0.0F);

        for (var11 = 0; var11 < var6; ++var11)
        {
            var12 = (float)var11 / ((float)var6 * 1.0F);
            var13 = var2 + (var4 - var2) * var12 - var9;
            var14 = var7 * var12 + var10;
            var1.addVertexWithUV((double)var14, 1.0D, (double)(0.0F - var8), (double)var13, (double)var3);
            var1.addVertexWithUV((double)var14, 1.0D, 0.0D, (double)var13, (double)var3);
            var1.addVertexWithUV((double)var14, 0.0D, 0.0D, (double)var13, (double)var5);
            var1.addVertexWithUV((double)var14, 0.0D, (double)(0.0F - var8), (double)var13, (double)var5);
        }

        var1.draw();
        var1.startDrawingQuads();
        var1.setNormal(0.0F, 1.0F, 0.0F);

        for (var11 = 0; var11 < var6; ++var11)
        {
            var12 = (float)var11 / ((float)var6 * 1.0F);
            var13 = var5 + (var3 - var5) * var12 - var9;
            var14 = var7 * var12 + var10;
            var1.addVertexWithUV(0.0D, (double)var14, 0.0D, (double)var2, (double)var13);
            var1.addVertexWithUV((double)var7, (double)var14, 0.0D, (double)var4, (double)var13);
            var1.addVertexWithUV((double)var7, (double)var14, (double)(0.0F - var8), (double)var4, (double)var13);
            var1.addVertexWithUV(0.0D, (double)var14, (double)(0.0F - var8), (double)var2, (double)var13);
        }

        var1.draw();
        var1.startDrawingQuads();
        var1.setNormal(0.0F, -1.0F, 0.0F);

        for (var11 = 0; var11 < var6; ++var11)
        {
            var12 = (float)var11 / ((float)var6 * 1.0F);
            var13 = var5 + (var3 - var5) * var12 - var9;
            var14 = var7 * var12;
            var1.addVertexWithUV((double)var7, (double)var14, 0.0D, (double)var4, (double)var13);
            var1.addVertexWithUV(0.0D, (double)var14, 0.0D, (double)var2, (double)var13);
            var1.addVertexWithUV(0.0D, (double)var14, (double)(0.0F - var8), (double)var2, (double)var13);
            var1.addVertexWithUV((double)var7, (double)var14, (double)(0.0F - var8), (double)var4, (double)var13);
        }

        var1.draw();
    }
}
