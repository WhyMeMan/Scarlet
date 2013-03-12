package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemRenderer
{
    /** A reference to the Minecraft object. */
    private Minecraft mc;
    private ItemStack itemToRender = null;

    /**
     * How far the current item has been equipped (0 disequipped and 1 fully up)
     */
    private float equippedProgress = 0.0F;
    private float prevEquippedProgress = 0.0F;

    /** Instance of RenderBlocks. */
    private RenderBlocks renderBlocksInstance = new RenderBlocks();
    public final MapItemRenderer mapItemRenderer;

    /** The index of the currently held item (0-8, or -1 if not yet updated) */
    private int equippedItemSlot = -1;

    public ItemRenderer(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
        this.mapItemRenderer = new MapItemRenderer(par1Minecraft.fontRenderer, par1Minecraft.gameSettings, par1Minecraft.renderEngine);
    }

    /**
     * Renders the item stack for being in an entity's hand Args: itemStack
     */
    public void renderItem(EntityLiving par1EntityLiving, ItemStack par2ItemStack, int par3)
    {
        GL11.glPushMatrix();
        boolean var4 = Reflector.MinecraftForgeClient.exists();
        String var5 = null;

        if (var4)
        {
            var5 = Reflector.callString(par2ItemStack.getItem(), Reflector.ForgeItem_getTextureFile, new Object[0]);
            Object var6 = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
            Object var7 = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[] {par2ItemStack, var6});

            if (var7 != null)
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(var5));
                Reflector.callVoid(Reflector.ForgeHooksClient_renderEquippedItem, new Object[] {var7, this.renderBlocksInstance, par1EntityLiving, par2ItemStack});
                GL11.glPopMatrix();
                return;
            }
        }

        boolean var23 = par2ItemStack.getItem() instanceof ItemBlock;
        Block var24 = null;

        if (var23)
        {
            var24 = Block.blocksList[par2ItemStack.itemID];
        }

        if (var24 != null && RenderBlocks.renderItemIn3d(var24.getRenderType()))
        {
            if (var5 == null)
            {
                var5 = "/terrain.png";
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(var5));
            this.renderBlocksInstance.renderBlockAsItem(var24, par2ItemStack.getItemDamage(), 1.0F);
        }
        else
        {
            int var8 = Config.getIconWidthTerrain();
            int var9 = par1EntityLiving.getItemIcon(par2ItemStack, par3);
            float var10 = 256.0F;

            if (var23)
            {
                if (var5 == null)
                {
                    var5 = "/terrain.png";
                }

                if (Config.isMultiTexture() && var5.equals("/terrain.png"))
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, Tessellator.getTileTextures(this.mc.renderEngine.getTexture(var5))[var9]);
                    var9 = 0;
                    var10 = 16.0F;
                }
                else
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(var5));
                }

                var8 = Config.getIconWidthTerrain();
            }
            else
            {
                if (var5 == null)
                {
                    var5 = "/gui/items.png";
                }

                if (Config.isMultiTexture() && var5.equals("/gui/items.png"))
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, Tessellator.getTileTextures(this.mc.renderEngine.getTexture(var5))[var9]);
                    var9 = 0;
                    var10 = 16.0F;
                }
                else
                {
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(var5));
                }

                var8 = Config.getIconWidthItems();
            }

            Tessellator var11 = Tessellator.instance;
            float var13 = ((float)(var9 % 16 * 16) + 0.0F) / var10;
            float var14 = ((float)(var9 % 16 * 16) + 15.99F) / var10;
            float var15 = ((float)(var9 / 16 * 16) + 0.0F) / var10;
            float var16 = ((float)(var9 / 16 * 16) + 15.99F) / var10;
            float var17 = 0.0F;
            float var18 = 0.3F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(-var17, -var18, 0.0F);
            float var19 = 1.5F;
            GL11.glScalef(var19, var19, var19);
            GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            renderItemIn2D(var11, var14, var15, var13, var16, 0.0625F, var8);

            if (par2ItemStack != null && par2ItemStack.hasEffect() && par3 == 0)
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("%blur%/misc/glint.png"));
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                float var20 = 0.76F;
                GL11.glColor4f(0.5F * var20, 0.25F * var20, 0.8F * var20, 1.0F);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float var21 = 0.125F;
                GL11.glScalef(var21, var21, var21);
                float var22 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                GL11.glTranslatef(var22, 0.0F, 0.0F);
                GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                renderItemIn2D(var11, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F, var8);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(var21, var21, var21);
                var22 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                GL11.glTranslatef(-var22, 0.0F, 0.0F);
                GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                renderItemIn2D(var11, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F, var8);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }

        GL11.glPopMatrix();
    }

    /**
     * Renders an item held in hand as a 2D texture with thickness
     */
    public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, float par5)
    {
        renderItemIn2D(par0Tessellator, par1, par2, par3, par4, par5, 16);
    }

    public static void renderItemIn2D(Tessellator var0, float var1, float var2, float var3, float var4, float var5, int var6)
    {
        float var7 = 1.0F;
        var0.startDrawingQuads();
        var0.setNormal(0.0F, 0.0F, 1.0F);
        var0.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)var1, (double)var4);
        var0.addVertexWithUV((double)var7, 0.0D, 0.0D, (double)var3, (double)var4);
        var0.addVertexWithUV((double)var7, 1.0D, 0.0D, (double)var3, (double)var2);
        var0.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)var1, (double)var2);
        var0.draw();
        var0.startDrawingQuads();
        var0.setNormal(0.0F, 0.0F, -1.0F);
        var0.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - var5), (double)var1, (double)var2);
        var0.addVertexWithUV((double)var7, 1.0D, (double)(0.0F - var5), (double)var3, (double)var2);
        var0.addVertexWithUV((double)var7, 0.0D, (double)(0.0F - var5), (double)var3, (double)var4);
        var0.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - var5), (double)var1, (double)var4);
        var0.draw();
        float var8 = 1.0F / (float)(32 * var6);
        float var9 = 1.0F / (float)var6;
        var0.startDrawingQuads();
        var0.setNormal(-1.0F, 0.0F, 0.0F);
        int var10;
        float var11;
        float var12;
        float var13;

        for (var10 = 0; var10 < var6; ++var10)
        {
            var11 = (float)var10 / (float)var6;
            var12 = var1 + (var3 - var1) * var11 - var8;
            var13 = var7 * var11;
            var0.addVertexWithUV((double)var13, 0.0D, (double)(0.0F - var5), (double)var12, (double)var4);
            var0.addVertexWithUV((double)var13, 0.0D, 0.0D, (double)var12, (double)var4);
            var0.addVertexWithUV((double)var13, 1.0D, 0.0D, (double)var12, (double)var2);
            var0.addVertexWithUV((double)var13, 1.0D, (double)(0.0F - var5), (double)var12, (double)var2);
        }

        var0.draw();
        var0.startDrawingQuads();
        var0.setNormal(1.0F, 0.0F, 0.0F);

        for (var10 = 0; var10 < var6; ++var10)
        {
            var11 = (float)var10 / (float)var6;
            var12 = var1 + (var3 - var1) * var11 - var8;
            var13 = var7 * var11 + var9;
            var0.addVertexWithUV((double)var13, 1.0D, (double)(0.0F - var5), (double)var12, (double)var2);
            var0.addVertexWithUV((double)var13, 1.0D, 0.0D, (double)var12, (double)var2);
            var0.addVertexWithUV((double)var13, 0.0D, 0.0D, (double)var12, (double)var4);
            var0.addVertexWithUV((double)var13, 0.0D, (double)(0.0F - var5), (double)var12, (double)var4);
        }

        var0.draw();
        var0.startDrawingQuads();
        var0.setNormal(0.0F, 1.0F, 0.0F);

        for (var10 = 0; var10 < var6; ++var10)
        {
            var11 = (float)var10 / (float)var6;
            var12 = var4 + (var2 - var4) * var11 - var8;
            var13 = var7 * var11 + var9;
            var0.addVertexWithUV(0.0D, (double)var13, 0.0D, (double)var1, (double)var12);
            var0.addVertexWithUV((double)var7, (double)var13, 0.0D, (double)var3, (double)var12);
            var0.addVertexWithUV((double)var7, (double)var13, (double)(0.0F - var5), (double)var3, (double)var12);
            var0.addVertexWithUV(0.0D, (double)var13, (double)(0.0F - var5), (double)var1, (double)var12);
        }

        var0.draw();
        var0.startDrawingQuads();
        var0.setNormal(0.0F, -1.0F, 0.0F);

        for (var10 = 0; var10 < var6; ++var10)
        {
            var11 = (float)var10 / (float)var6;
            var12 = var4 + (var2 - var4) * var11 - var8;
            var13 = var7 * var11;
            var0.addVertexWithUV((double)var7, (double)var13, 0.0D, (double)var3, (double)var12);
            var0.addVertexWithUV(0.0D, (double)var13, 0.0D, (double)var1, (double)var12);
            var0.addVertexWithUV(0.0D, (double)var13, (double)(0.0F - var5), (double)var1, (double)var12);
            var0.addVertexWithUV((double)var7, (double)var13, (double)(0.0F - var5), (double)var3, (double)var12);
        }

        var0.draw();
    }

    /**
     * Renders the active item in the player's hand when in first person mode. Args: partialTickTime
     */
    public void renderItemInFirstPerson(float par1)
    {
        float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * par1;
        EntityClientPlayerMP var3 = this.mc.thePlayer;
        float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float var5;
        float var6;

        if (var3 instanceof EntityPlayerSP)
        {
            var5 = var3.prevRenderArmPitch + (var3.renderArmPitch - var3.prevRenderArmPitch) * par1;
            var6 = var3.prevRenderArmYaw + (var3.renderArmYaw - var3.prevRenderArmYaw) * par1;
            GL11.glRotatef((var3.rotationPitch - var5) * 0.1F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef((var3.rotationYaw - var6) * 0.1F, 0.0F, 1.0F, 0.0F);
        }

        ItemStack var7 = this.itemToRender;
        var5 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
        var5 = 1.0F;
        int var8 = this.mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        int var9 = var8 % 65536;
        int var10 = var8 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var9 / 1.0F, (float)var10 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var11;
        float var12;
        float var13;

        if (var7 != null)
        {
            var8 = Item.itemsList[var7.itemID].getColorFromItemStack(var7, 0);
            var13 = (float)(var8 >> 16 & 255) / 255.0F;
            var12 = (float)(var8 >> 8 & 255) / 255.0F;
            var11 = (float)(var8 & 255) / 255.0F;
            GL11.glColor4f(var5 * var13, var5 * var12, var5 * var11, 1.0F);
        }
        else
        {
            GL11.glColor4f(var5, var5, var5, 1.0F);
        }

        float var14;
        float var15;
        Render var17;
        float var16;
        RenderPlayer var18;

        if (var7 != null && var7.getItem() instanceof ItemMap)
        {
            GL11.glPushMatrix();
            var6 = 0.8F;
            var13 = var3.getSwingProgress(par1);
            var12 = MathHelper.sin(var13 * (float)Math.PI);
            var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
            GL11.glTranslatef(-var11 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI * 2.0F) * 0.2F, -var12 * 0.2F);
            var13 = 1.0F - var4 / 45.0F + 0.1F;

            if (var13 < 0.0F)
            {
                var13 = 0.0F;
            }

            if (var13 > 1.0F)
            {
                var13 = 1.0F;
            }

            var13 = -MathHelper.cos(var13 * (float)Math.PI) * 0.5F + 0.5F;
            GL11.glTranslatef(0.0F, 0.0F * var6 - (1.0F - var2) * 1.2F - var13 * 0.5F + 0.04F, -0.9F * var6);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var13 * -85.0F, 0.0F, 0.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getTexture()));

            for (var10 = 0; var10 < 2; ++var10)
            {
                int var29 = var10 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float)var29);
                GL11.glRotatef((float)(-45 * var29), 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef((float)(-65 * var29), 0.0F, 1.0F, 0.0F);
                var17 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
                var18 = (RenderPlayer)var17;
                var16 = 1.0F;
                GL11.glScalef(var16, var16, var16);
                var18.func_82441_a(this.mc.thePlayer);
                GL11.glPopMatrix();
            }

            var12 = var3.getSwingProgress(par1);
            var11 = MathHelper.sin(var12 * var12 * (float)Math.PI);
            var14 = MathHelper.sin(MathHelper.sqrt_float(var12) * (float)Math.PI);
            GL11.glRotatef(-var11 * 20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-var14 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var14 * 80.0F, 1.0F, 0.0F, 0.0F);
            var15 = 0.38F;
            GL11.glScalef(var15, var15, var15);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
            var16 = 0.015625F;
            GL11.glScalef(var16, var16, var16);
            this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/misc/mapbg.png"));
            Tessellator var30 = Tessellator.instance;
            GL11.glNormal3f(0.0F, 0.0F, -1.0F);
            var30.startDrawingQuads();
            byte var25 = 7;
            var30.addVertexWithUV((double)(0 - var25), (double)(128 + var25), 0.0D, 0.0D, 1.0D);
            var30.addVertexWithUV((double)(128 + var25), (double)(128 + var25), 0.0D, 1.0D, 1.0D);
            var30.addVertexWithUV((double)(128 + var25), (double)(0 - var25), 0.0D, 1.0D, 0.0D);
            var30.addVertexWithUV((double)(0 - var25), (double)(0 - var25), 0.0D, 0.0D, 0.0D);
            var30.draw();
            MapData var26 = ((ItemMap)var7.getItem()).getMapData(var7, this.mc.theWorld);
            Object var27 = null;
            Object var28 = null;

            if (Reflector.MinecraftForgeClient_getItemRenderer.exists())
            {
                var28 = Reflector.getFieldValue(Reflector.ItemRenderType_FIRST_PERSON_MAP);
                var27 = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[] {var7, var28});
            }

            if (var27 != null)
            {
                Reflector.callVoid(var27, Reflector.IItemRenderer_renderItem, new Object[] {var28, var7, this.mc.thePlayer, this.mc.renderEngine, var26});
            }
            else if (var26 != null)
            {
                this.mapItemRenderer.renderMap(this.mc.thePlayer, this.mc.renderEngine, var26);
            }

            GL11.glPopMatrix();
        }
        else if (var7 != null)
        {
            GL11.glPushMatrix();
            var6 = 0.8F;

            if (var3.getItemInUseCount() > 0)
            {
                EnumAction var19 = var7.getItemUseAction();

                if (var19 == EnumAction.eat || var19 == EnumAction.drink)
                {
                    var12 = (float)var3.getItemInUseCount() - par1 + 1.0F;
                    var11 = 1.0F - var12 / (float)var7.getMaxItemUseDuration();
                    var14 = 1.0F - var11;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var15 = 1.0F - var14;
                    GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos(var12 / 4.0F * (float)Math.PI) * 0.1F) * (float)((double)var11 > 0.2D ? 1 : 0), 0.0F);
                    GL11.glTranslatef(var15 * 0.6F, -var15 * 0.5F, 0.0F);
                    GL11.glRotatef(var15 * 90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(var15 * 10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(var15 * 30.0F, 0.0F, 0.0F, 1.0F);
                }
            }
            else
            {
                var13 = var3.getSwingProgress(par1);
                var12 = MathHelper.sin(var13 * (float)Math.PI);
                var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
                GL11.glTranslatef(-var11 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI * 2.0F) * 0.2F, -var12 * 0.2F);
            }

            GL11.glTranslatef(0.7F * var6, -0.65F * var6 - (1.0F - var2) * 0.6F, -0.9F * var6);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            var13 = var3.getSwingProgress(par1);
            var12 = MathHelper.sin(var13 * var13 * (float)Math.PI);
            var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
            GL11.glRotatef(-var12 * 20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-var11 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var11 * 80.0F, 1.0F, 0.0F, 0.0F);
            var14 = 0.4F;
            GL11.glScalef(var14, var14, var14);
            float var20;
            float var24;

            if (var3.getItemInUseCount() > 0)
            {
                EnumAction var21 = var7.getItemUseAction();

                if (var21 == EnumAction.block)
                {
                    GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
                    GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
                }
                else if (var21 == EnumAction.bow)
                {
                    GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
                    var16 = (float)var7.getMaxItemUseDuration() - ((float)var3.getItemInUseCount() - par1 + 1.0F);
                    var24 = var16 / 20.0F;
                    var24 = (var24 * var24 + var24 * 2.0F) / 3.0F;

                    if (var24 > 1.0F)
                    {
                        var24 = 1.0F;
                    }

                    if (var24 > 0.1F)
                    {
                        GL11.glTranslatef(0.0F, MathHelper.sin((var16 - 0.1F) * 1.3F) * 0.01F * (var24 - 0.1F), 0.0F);
                    }

                    GL11.glTranslatef(0.0F, 0.0F, var24 * 0.1F);
                    GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.5F, 0.0F);
                    var20 = 1.0F + var24 * 0.2F;
                    GL11.glScalef(1.0F, 1.0F, var20);
                    GL11.glTranslatef(0.0F, -0.5F, 0.0F);
                    GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
                }
            }

            if (var7.getItem().shouldRotateAroundWhenRendering())
            {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var7.getItem().requiresMultipleRenderPasses())
            {
                this.renderItem(var3, var7, 0);
                int var31 = 2;

                if (Reflector.ForgeItem_getRenderPasses.exists())
                {
                    var31 = Reflector.callInt(var7.getItem(), Reflector.ForgeItem_getRenderPasses, new Object[] {Integer.valueOf(var7.getItemDamage())});
                }

                for (int var22 = 1; var22 < var31; ++var22)
                {
                    int var23 = Item.itemsList[var7.itemID].getColorFromItemStack(var7, var22);
                    var16 = (float)(var23 >> 16 & 255) / 255.0F;
                    var24 = (float)(var23 >> 8 & 255) / 255.0F;
                    var20 = (float)(var23 & 255) / 255.0F;
                    GL11.glColor4f(var5 * var16, var5 * var24, var5 * var20, 1.0F);
                    this.renderItem(var3, var7, var22);
                }
            }
            else
            {
                this.renderItem(var3, var7, 0);
            }

            GL11.glPopMatrix();
        }
        else if (!var3.getHasActivePotion())
        {
            GL11.glPushMatrix();
            var6 = 0.8F;
            var13 = var3.getSwingProgress(par1);
            var12 = MathHelper.sin(var13 * (float)Math.PI);
            var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
            GL11.glTranslatef(-var11 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI * 2.0F) * 0.4F, -var12 * 0.4F);
            GL11.glTranslatef(0.8F * var6, -0.75F * var6 - (1.0F - var2) * 0.6F, -0.9F * var6);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            var13 = var3.getSwingProgress(par1);
            var12 = MathHelper.sin(var13 * var13 * (float)Math.PI);
            var11 = MathHelper.sin(MathHelper.sqrt_float(var13) * (float)Math.PI);
            GL11.glRotatef(var11 * 70.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-var12 * 20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getTexture()));
            GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
            GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(5.6F, 0.0F, 0.0F);
            var17 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            var18 = (RenderPlayer)var17;
            var16 = 1.0F;
            GL11.glScalef(var16, var16, var16);
            var18.func_82441_a(this.mc.thePlayer);
            GL11.glPopMatrix();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
    }

    /**
     * Renders all the overlays that are in first person mode. Args: partialTickTime
     */
    public void renderOverlays(float par1)
    {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        int var2;

        if (this.mc.thePlayer.isBurning())
        {
            var2 = this.mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, var2);
            this.renderFireInFirstPerson(par1);
        }

        if (this.mc.thePlayer.isEntityInsideOpaqueBlock())
        {
            var2 = MathHelper.floor_double(this.mc.thePlayer.posX);
            int var3 = MathHelper.floor_double(this.mc.thePlayer.posY);
            int var4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            int var5 = this.mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, var5);
            int var6 = this.mc.theWorld.getBlockId(var2, var3, var4);

            if (this.mc.theWorld.isBlockNormalCube(var2, var3, var4))
            {
                this.renderInsideOfBlock(par1, Block.blocksList[var6].getBlockTextureFromSide(2));
            }
            else
            {
                for (int var7 = 0; var7 < 8; ++var7)
                {
                    float var8 = ((float)((var7 >> 0) % 2) - 0.5F) * this.mc.thePlayer.width * 0.9F;
                    float var9 = ((float)((var7 >> 1) % 2) - 0.5F) * this.mc.thePlayer.height * 0.2F;
                    float var10 = ((float)((var7 >> 2) % 2) - 0.5F) * this.mc.thePlayer.width * 0.9F;
                    int var11 = MathHelper.floor_float((float)var2 + var8);
                    int var12 = MathHelper.floor_float((float)var3 + var9);
                    int var13 = MathHelper.floor_float((float)var4 + var10);

                    if (this.mc.theWorld.isBlockNormalCube(var11, var12, var13))
                    {
                        var6 = this.mc.theWorld.getBlockId(var11, var12, var13);
                    }
                }
            }

            if (Block.blocksList[var6] != null)
            {
                this.renderInsideOfBlock(par1, Block.blocksList[var6].getBlockTextureFromSide(2));
            }
        }

        if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
        {
            var2 = this.mc.renderEngine.getTexture("/misc/water.png");
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, var2);
            this.renderWarpedTextureOverlay(par1);
        }

        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

    /**
     * Renders the texture of the block the player is inside as an overlay. Args: partialTickTime, blockTextureIndex
     */
    private void renderInsideOfBlock(float par1, int par2)
    {
        Tessellator var3 = Tessellator.instance;
        this.mc.thePlayer.getBrightness(par1);
        float var4 = 0.1F;
        GL11.glColor4f(var4, var4, var4, 0.5F);
        GL11.glPushMatrix();
        float var5 = -1.0F;
        float var6 = 1.0F;
        float var7 = -1.0F;
        float var8 = 1.0F;
        float var9 = -0.5F;
        float var10 = 0.0078125F;
        float var11 = (float)(par2 % 16) / 256.0F - var10;
        float var12 = ((float)(par2 % 16) + 15.99F) / 256.0F + var10;
        float var13 = (float)(par2 / 16) / 256.0F - var10;
        float var14 = ((float)(par2 / 16) + 15.99F) / 256.0F + var10;
        var3.startDrawingQuads();
        var3.addVertexWithUV((double)var5, (double)var7, (double)var9, (double)var12, (double)var14);
        var3.addVertexWithUV((double)var6, (double)var7, (double)var9, (double)var11, (double)var14);
        var3.addVertexWithUV((double)var6, (double)var8, (double)var9, (double)var11, (double)var13);
        var3.addVertexWithUV((double)var5, (double)var8, (double)var9, (double)var12, (double)var13);
        var3.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders a texture that warps around based on the direction the player is looking. Texture needs to be bound
     * before being called. Used for the water overlay. Args: parialTickTime
     */
    private void renderWarpedTextureOverlay(float par1)
    {
        Tessellator var2 = Tessellator.instance;
        float var3 = this.mc.thePlayer.getBrightness(par1);
        GL11.glColor4f(var3, var3, var3, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glPushMatrix();
        float var4 = 4.0F;
        float var5 = -1.0F;
        float var6 = 1.0F;
        float var7 = -1.0F;
        float var8 = 1.0F;
        float var9 = -0.5F;
        float var10 = -this.mc.thePlayer.rotationYaw / 64.0F;
        float var11 = this.mc.thePlayer.rotationPitch / 64.0F;
        var2.startDrawingQuads();
        var2.addVertexWithUV((double)var5, (double)var7, (double)var9, (double)(var4 + var10), (double)(var4 + var11));
        var2.addVertexWithUV((double)var6, (double)var7, (double)var9, (double)(0.0F + var10), (double)(var4 + var11));
        var2.addVertexWithUV((double)var6, (double)var8, (double)var9, (double)(0.0F + var10), (double)(0.0F + var11));
        var2.addVertexWithUV((double)var5, (double)var8, (double)var9, (double)(var4 + var10), (double)(0.0F + var11));
        var2.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * Renders the fire on the screen for first person mode. Arg: partialTickTime
     */
    private void renderFireInFirstPerson(float par1)
    {
        Tessellator var2 = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        float var3 = 1.0F;

        for (int var4 = 0; var4 < 2; ++var4)
        {
            GL11.glPushMatrix();
            int var5 = Block.fire.blockIndexInTexture + var4 * 16;
            int var6 = (var5 & 15) << 4;
            int var7 = var5 & 240;
            float var8 = (float)var6 / 256.0F;
            float var9 = ((float)var6 + 15.99F) / 256.0F;
            float var10 = (float)var7 / 256.0F;
            float var11 = ((float)var7 + 15.99F) / 256.0F;
            float var12 = (0.0F - var3) / 2.0F;
            float var13 = var12 + var3;
            float var14 = 0.0F - var3 / 2.0F;
            float var15 = var14 + var3;
            float var16 = -0.5F;
            GL11.glTranslatef((float)(-(var4 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GL11.glRotatef((float)(var4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            var2.startDrawingQuads();
            var2.addVertexWithUV((double)var12, (double)var14, (double)var16, (double)var9, (double)var11);
            var2.addVertexWithUV((double)var13, (double)var14, (double)var16, (double)var8, (double)var11);
            var2.addVertexWithUV((double)var13, (double)var15, (double)var16, (double)var8, (double)var10);
            var2.addVertexWithUV((double)var12, (double)var15, (double)var16, (double)var9, (double)var10);
            var2.draw();
            GL11.glPopMatrix();
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void updateEquippedItem()
    {
        this.prevEquippedProgress = this.equippedProgress;
        EntityClientPlayerMP var1 = this.mc.thePlayer;
        ItemStack var2 = var1.inventory.getCurrentItem();
        boolean var3 = this.equippedItemSlot == var1.inventory.currentItem && var2 == this.itemToRender;

        if (this.itemToRender == null && var2 == null)
        {
            var3 = true;
        }

        if (var2 != null && this.itemToRender != null && var2 != this.itemToRender && var2.itemID == this.itemToRender.itemID && var2.getItemDamage() == this.itemToRender.getItemDamage())
        {
            this.itemToRender = var2;
            var3 = true;
        }

        float var4 = 0.4F;
        float var5 = var3 ? 1.0F : 0.0F;
        float var6 = var5 - this.equippedProgress;

        if (var6 < -var4)
        {
            var6 = -var4;
        }

        if (var6 > var4)
        {
            var6 = var4;
        }

        this.equippedProgress += var6;

        if (this.equippedProgress < 0.1F)
        {
            this.itemToRender = var2;
            this.equippedItemSlot = var1.inventory.currentItem;
        }
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress()
    {
        this.equippedProgress = 0.0F;
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress2()
    {
        this.equippedProgress = 0.0F;
    }
}
