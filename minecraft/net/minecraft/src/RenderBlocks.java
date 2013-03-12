package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderBlocks
{
    /** The IBlockAccess used by this instance of RenderBlocks */
    public IBlockAccess blockAccess;

    /**
     * If set to >=0, all block faces will be rendered using this texture index
     */
    public int overrideBlockTexture = -1;

    /**
     * Set to true if the texture should be flipped horizontally during render*Face
     */
    public boolean flipTexture = false;

    /**
     * If true, renders all faces on all blocks rather than using the logic in Block.shouldSideBeRendered.  Unused.
     */
    public boolean renderAllFaces = false;

    /** Fancy grass side matching biome */
    public static boolean fancyGrass = true;
    public static boolean cfgGrassFix = true;
    public boolean useInventoryTint = true;

    /** The minimum X value for rendering (default 0.0). */
    public double renderMinX;

    /** The maximum X value for rendering (default 1.0). */
    public double renderMaxX;

    /** The minimum Y value for rendering (default 0.0). */
    public double renderMinY;

    /** The maximum Y value for rendering (default 1.0). */
    public double renderMaxY;

    /** The minimum Z value for rendering (default 0.0). */
    public double renderMinZ;

    /** The maximum Z value for rendering (default 1.0). */
    public double renderMaxZ;

    /**
     * Set by overrideBlockBounds, to keep this class from changing the visual bounding box.
     */
    public boolean lockBlockBounds = false;
    public int uvRotateEast = 0;
    public int uvRotateWest = 0;
    public int uvRotateSouth = 0;
    public int uvRotateNorth = 0;
    public int uvRotateTop = 0;
    public int uvRotateBottom = 0;

    /** Whether ambient occlusion is enabled or not */
    public boolean enableAO;

    /** Light value of the block itself */
    public float lightValueOwn;

    /** Light value one block less in x axis */
    public float aoLightValueXNeg;

    /** Light value one block more in y axis */
    public float aoLightValueYNeg;

    /** Light value one block more in z axis */
    public float aoLightValueZNeg;

    /** Light value one block more in x axis */
    public float aoLightValueXPos;

    /** Light value one block more in y axis */
    public float aoLightValueYPos;

    /** Light value one block more in z axis */
    public float aoLightValueZPos;

    /**
     * Used as a scratch variable for ambient occlusion on the north/bottom/east corner.
     */
    public float aoLightValueScratchXYZNNN;

    /**
     * Used as a scratch variable for ambient occlusion between the bottom face and the north face.
     */
    public float aoLightValueScratchXYNN;

    /**
     * Used as a scratch variable for ambient occlusion on the north/bottom/west corner.
     */
    public float aoLightValueScratchXYZNNP;

    /**
     * Used as a scratch variable for ambient occlusion between the bottom face and the east face.
     */
    public float aoLightValueScratchYZNN;

    /**
     * Used as a scratch variable for ambient occlusion between the bottom face and the west face.
     */
    public float aoLightValueScratchYZNP;

    /**
     * Used as a scratch variable for ambient occlusion on the south/bottom/east corner.
     */
    public float aoLightValueScratchXYZPNN;

    /**
     * Used as a scratch variable for ambient occlusion between the bottom face and the south face.
     */
    public float aoLightValueScratchXYPN;

    /**
     * Used as a scratch variable for ambient occlusion on the south/bottom/west corner.
     */
    public float aoLightValueScratchXYZPNP;

    /**
     * Used as a scratch variable for ambient occlusion on the north/top/east corner.
     */
    public float aoLightValueScratchXYZNPN;

    /**
     * Used as a scratch variable for ambient occlusion between the top face and the north face.
     */
    public float aoLightValueScratchXYNP;

    /**
     * Used as a scratch variable for ambient occlusion on the north/top/west corner.
     */
    public float aoLightValueScratchXYZNPP;

    /**
     * Used as a scratch variable for ambient occlusion between the top face and the east face.
     */
    public float aoLightValueScratchYZPN;

    /**
     * Used as a scratch variable for ambient occlusion on the south/top/east corner.
     */
    public float aoLightValueScratchXYZPPN;

    /**
     * Used as a scratch variable for ambient occlusion between the top face and the south face.
     */
    public float aoLightValueScratchXYPP;

    /**
     * Used as a scratch variable for ambient occlusion between the top face and the west face.
     */
    public float aoLightValueScratchYZPP;

    /**
     * Used as a scratch variable for ambient occlusion on the south/top/west corner.
     */
    public float aoLightValueScratchXYZPPP;

    /**
     * Used as a scratch variable for ambient occlusion between the north face and the east face.
     */
    public float aoLightValueScratchXZNN;

    /**
     * Used as a scratch variable for ambient occlusion between the south face and the east face.
     */
    public float aoLightValueScratchXZPN;

    /**
     * Used as a scratch variable for ambient occlusion between the north face and the west face.
     */
    public float aoLightValueScratchXZNP;

    /**
     * Used as a scratch variable for ambient occlusion between the south face and the west face.
     */
    public float aoLightValueScratchXZPP;

    /** Ambient occlusion brightness XYZNNN */
    public int aoBrightnessXYZNNN;

    /** Ambient occlusion brightness XYNN */
    public int aoBrightnessXYNN;

    /** Ambient occlusion brightness XYZNNP */
    public int aoBrightnessXYZNNP;

    /** Ambient occlusion brightness YZNN */
    public int aoBrightnessYZNN;

    /** Ambient occlusion brightness YZNP */
    public int aoBrightnessYZNP;

    /** Ambient occlusion brightness XYZPNN */
    public int aoBrightnessXYZPNN;

    /** Ambient occlusion brightness XYPN */
    public int aoBrightnessXYPN;

    /** Ambient occlusion brightness XYZPNP */
    public int aoBrightnessXYZPNP;

    /** Ambient occlusion brightness XYZNPN */
    public int aoBrightnessXYZNPN;

    /** Ambient occlusion brightness XYNP */
    public int aoBrightnessXYNP;

    /** Ambient occlusion brightness XYZNPP */
    public int aoBrightnessXYZNPP;

    /** Ambient occlusion brightness YZPN */
    public int aoBrightnessYZPN;

    /** Ambient occlusion brightness XYZPPN */
    public int aoBrightnessXYZPPN;

    /** Ambient occlusion brightness XYPP */
    public int aoBrightnessXYPP;

    /** Ambient occlusion brightness YZPP */
    public int aoBrightnessYZPP;

    /** Ambient occlusion brightness XYZPPP */
    public int aoBrightnessXYZPPP;

    /** Ambient occlusion brightness XZNN */
    public int aoBrightnessXZNN;

    /** Ambient occlusion brightness XZPN */
    public int aoBrightnessXZPN;

    /** Ambient occlusion brightness XZNP */
    public int aoBrightnessXZNP;

    /** Ambient occlusion brightness XZPP */
    public int aoBrightnessXZPP;

    /** Ambient occlusion type (0=simple, 1=complex) */
    public int aoType = 1;

    /** Brightness top left */
    public int brightnessTopLeft;

    /** Brightness bottom left */
    public int brightnessBottomLeft;

    /** Brightness bottom right */
    public int brightnessBottomRight;

    /** Brightness top right */
    public int brightnessTopRight;

    /** Red color value for the top left corner */
    public float colorRedTopLeft;

    /** Red color value for the bottom left corner */
    public float colorRedBottomLeft;

    /** Red color value for the bottom right corner */
    public float colorRedBottomRight;

    /** Red color value for the top right corner */
    public float colorRedTopRight;

    /** Green color value for the top left corner */
    public float colorGreenTopLeft;

    /** Green color value for the bottom left corner */
    public float colorGreenBottomLeft;

    /** Green color value for the bottom right corner */
    public float colorGreenBottomRight;

    /** Green color value for the top right corner */
    public float colorGreenTopRight;

    /** Blue color value for the top left corner */
    public float colorBlueTopLeft;

    /** Blue color value for the bottom left corner */
    public float colorBlueBottomLeft;

    /** Blue color value for the bottom right corner */
    public float colorBlueBottomRight;

    /** Blue color value for the top right corner */
    public float colorBlueTopRight;

    /**
     * Grass flag for ambient occlusion on Center X, Positive Y, and Negative Z
     */
    public boolean aoGrassXYZCPN;

    /**
     * Grass flag for ambient occlusion on Positive X, Positive Y, and Center Z
     */
    public boolean aoGrassXYZPPC;

    /**
     * Grass flag for ambient occlusion on Negative X, Positive Y, and Center Z
     */
    public boolean aoGrassXYZNPC;

    /**
     * Grass flag for ambient occlusion on Center X, Positive Y, and Positive Z
     */
    public boolean aoGrassXYZCPP;

    /**
     * Grass flag for ambient occlusion on Negative X, Center Y, and Negative Z
     */
    public boolean aoGrassXYZNCN;

    /**
     * Grass flag for ambient occlusion on Positive X, Center Y, and Positive Z
     */
    public boolean aoGrassXYZPCP;

    /**
     * Grass flag for ambient occlusion on Negative X, Center Y, and Positive Z
     */
    public boolean aoGrassXYZNCP;

    /**
     * Grass flag for ambient occlusion on Positive X, Center Y, and Negative Z
     */
    public boolean aoGrassXYZPCN;

    /**
     * Grass flag for ambient occlusion on Center X, Negative Y, and Negative Z
     */
    public boolean aoGrassXYZCNN;

    /**
     * Grass flag for ambient occlusion on Positive X, Negative Y, and Center Z
     */
    public boolean aoGrassXYZPNC;

    /**
     * Grass flag for ambient occlusion on Negative X, Negative Y, and center Z
     */
    public boolean aoGrassXYZNNC;

    /**
     * Grass flag for ambient occlusion on Center X, Negative Y, and Positive Z
     */
    public boolean aoGrassXYZCNP;
    public boolean aoLightValuesCalculated;
    public float aoLightValueOpaque = 0.2F;
    public static float[][] redstoneColors = new float[16][];

    public RenderBlocks(IBlockAccess par1IBlockAccess)
    {
        this.blockAccess = par1IBlockAccess;
        this.aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
    }

    public RenderBlocks() {}

    /**
     * Sets overrideBlockTexture
     */
    public void setOverrideBlockTexture(int par1)
    {
        this.overrideBlockTexture = par1;
    }

    /**
     * Clear override block texture
     */
    public void clearOverrideBlockTexture()
    {
        this.overrideBlockTexture = -1;
    }

    /**
     * Sets the bounding box for the block to draw in, e.g. 0.25-0.75 on all axes for a half-size, centered block.
     */
    public void setRenderBounds(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        if (!this.lockBlockBounds)
        {
            this.renderMinX = par1;
            this.renderMaxX = par7;
            this.renderMinY = par3;
            this.renderMaxY = par9;
            this.renderMinZ = par5;
            this.renderMaxZ = par11;
        }
    }

    /**
     * Like setRenderBounds, but automatically pulling the bounds from the given Block.
     */
    public void setRenderBoundsFromBlock(Block par1Block)
    {
        if (!this.lockBlockBounds)
        {
            this.renderMinX = par1Block.getBlockBoundsMinX();
            this.renderMaxX = par1Block.getBlockBoundsMaxX();
            this.renderMinY = par1Block.getBlockBoundsMinY();
            this.renderMaxY = par1Block.getBlockBoundsMaxY();
            this.renderMinZ = par1Block.getBlockBoundsMinZ();
            this.renderMaxZ = par1Block.getBlockBoundsMaxZ();
        }
    }

    /**
     * Like setRenderBounds, but locks the values so that RenderBlocks won't change them.  If you use this, you must
     * call unlockBlockBounds after you finish rendering!
     */
    public void overrideBlockBounds(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        this.renderMinX = par1;
        this.renderMaxX = par7;
        this.renderMinY = par3;
        this.renderMaxY = par9;
        this.renderMinZ = par5;
        this.renderMaxZ = par11;
        this.lockBlockBounds = true;
    }

    /**
     * Unlocks the visual bounding box so that RenderBlocks can change it again.
     */
    public void unlockBlockBounds()
    {
        this.lockBlockBounds = false;
    }

    /**
     * Renders a block using the given texture instead of the block's own default texture
     */
    public void renderBlockUsingTexture(Block par1Block, int par2, int par3, int par4, int par5)
    {
        this.overrideBlockTexture = par5;
        this.renderBlockByRenderType(par1Block, par2, par3, par4);
        this.overrideBlockTexture = -1;
    }

    /**
     * Render all faces of a block
     */
    public void renderBlockAllFaces(Block par1Block, int par2, int par3, int par4)
    {
        this.renderAllFaces = true;
        this.renderBlockByRenderType(par1Block, par2, par3, par4);
        this.renderAllFaces = false;
    }

    /**
     * Render BlockEndPortalFrame
     */
    public boolean renderBlockEndPortalFrame(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 3;

        if (var6 == 0)
        {
            this.uvRotateTop = 3;
        }
        else if (var6 == 3)
        {
            this.uvRotateTop = 1;
        }
        else if (var6 == 1)
        {
            this.uvRotateTop = 2;
        }

        if (!BlockEndPortalFrame.isEnderEyeInserted(var5))
        {
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.uvRotateTop = 0;
            return true;
        }
        else
        {
            this.renderAllFaces = true;
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.overrideBlockTexture = 174;
            this.setRenderBounds(0.25D, 0.8125D, 0.25D, 0.75D, 1.0D, 0.75D);
            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.renderAllFaces = false;
            this.clearOverrideBlockTexture();
            this.uvRotateTop = 0;
            return true;
        }
    }

    /**
     * render a bed at the given coordinates
     */
    public boolean renderBlockBed(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = BlockBed.getDirection(var6);
        boolean var8 = BlockBed.isBlockHeadOfBed(var6);

        if (Reflector.ForgeBlock_getBedDirection.exists())
        {
            var7 = Reflector.callInt(par1Block, Reflector.ForgeBlock_getBedDirection, new Object[] {this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4)});
        }

        if (Reflector.ForgeBlock_isBedFoot.exists())
        {
            var8 = Reflector.callBoolean(par1Block, Reflector.ForgeBlock_isBedFoot, new Object[] {this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4)});
        }

        float var9 = 0.5F;
        float var10 = 1.0F;
        float var11 = 0.8F;
        float var12 = 0.6F;
        int var13 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        var5.setBrightness(var13);
        var5.setColorOpaque_F(var9, var9, var9);
        int var14 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 0);

        if (this.overrideBlockTexture >= 0)
        {
            var14 = this.overrideBlockTexture;
        }

        int var15 = (var14 & 15) << 4;
        int var16 = var14 & 240;
        double var17 = (double)((float)var15 / 256.0F);
        double var19 = ((double)(var15 + 16) - 0.01D) / 256.0D;
        double var21 = (double)((float)var16 / 256.0F);
        double var23 = ((double)(var16 + 16) - 0.01D) / 256.0D;
        double var25 = (double)par2 + this.renderMinX;
        double var27 = (double)par2 + this.renderMaxX;
        double var29 = (double)par3 + this.renderMinY + 0.1875D;
        double var31 = (double)par4 + this.renderMinZ;
        double var33 = (double)par4 + this.renderMaxZ;
        var5.addVertexWithUV(var25, var29, var33, var17, var23);
        var5.addVertexWithUV(var25, var29, var31, var17, var21);
        var5.addVertexWithUV(var27, var29, var31, var19, var21);
        var5.addVertexWithUV(var27, var29, var33, var19, var23);
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
        var5.setColorOpaque_F(var10, var10, var10);
        var14 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 1);

        if (this.overrideBlockTexture >= 0)
        {
            var14 = this.overrideBlockTexture;
        }

        var15 = (var14 & 15) << 4;
        var16 = var14 & 240;
        var17 = (double)((float)var15 / 256.0F);
        var19 = ((double)(var15 + 16) - 0.01D) / 256.0D;
        var21 = (double)((float)var16 / 256.0F);
        var23 = ((double)(var16 + 16) - 0.01D) / 256.0D;
        var25 = var17;
        var27 = var19;
        var29 = var21;
        var31 = var21;
        var33 = var17;
        double var35 = var19;
        double var37 = var23;
        double var39 = var23;

        if (var7 == 0)
        {
            var27 = var17;
            var29 = var23;
            var33 = var19;
            var39 = var21;
        }
        else if (var7 == 2)
        {
            var25 = var19;
            var31 = var23;
            var35 = var17;
            var37 = var21;
        }
        else if (var7 == 3)
        {
            var25 = var19;
            var31 = var23;
            var35 = var17;
            var37 = var21;
            var27 = var17;
            var29 = var23;
            var33 = var19;
            var39 = var21;
        }

        double var41 = (double)par2 + this.renderMinX;
        double var43 = (double)par2 + this.renderMaxX;
        double var45 = (double)par3 + this.renderMaxY;
        double var47 = (double)par4 + this.renderMinZ;
        double var49 = (double)par4 + this.renderMaxZ;
        var5.addVertexWithUV(var43, var45, var49, var33, var37);
        var5.addVertexWithUV(var43, var45, var47, var25, var29);
        var5.addVertexWithUV(var41, var45, var47, var27, var31);
        var5.addVertexWithUV(var41, var45, var49, var35, var39);
        int var51 = Direction.headInvisibleFace[var7];

        if (var8)
        {
            var51 = Direction.headInvisibleFace[Direction.footInvisibleFaceRemap[var7]];
        }

        byte var52 = 4;

        switch (var7)
        {
            case 0:
                var52 = 5;
                break;

            case 1:
                var52 = 3;

            case 2:
            default:
                break;

            case 3:
                var52 = 2;
        }

        if (var51 != 2 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2)))
        {
            var5.setBrightness(this.renderMinZ > 0.0D ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = var52 == 2;
            this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 2));
        }

        if (var51 != 3 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3)))
        {
            var5.setBrightness(this.renderMaxZ < 1.0D ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = var52 == 3;
            this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 3));
        }

        if (var51 != 4 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4)))
        {
            var5.setBrightness(this.renderMinZ > 0.0D ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = var52 == 4;
            this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 4));
        }

        if (var51 != 5 && (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5)))
        {
            var5.setBrightness(this.renderMaxZ < 1.0D ? var13 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = var52 == 5;
            this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 5));
        }

        this.flipTexture = false;
        return true;
    }

    /**
     * Render BlockBrewingStand
     */
    public boolean renderBlockBrewingStand(BlockBrewingStand par1BlockBrewingStand, int par2, int par3, int par4)
    {
        this.setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.overrideBlockTexture = 156;
        this.setRenderBounds(0.5625D, 0.0D, 0.3125D, 0.9375D, 0.125D, 0.6875D);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.setRenderBounds(0.125D, 0.0D, 0.0625D, 0.5D, 0.125D, 0.4375D);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.setRenderBounds(0.125D, 0.0D, 0.5625D, 0.5D, 0.125D, 0.9375D);
        this.renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
        this.clearOverrideBlockTexture();
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockBrewingStand.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1BlockBrewingStand.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        int var34 = par1BlockBrewingStand.getBlockTextureFromSideAndMetadata(0, 0);

        if (this.overrideBlockTexture >= 0)
        {
            var34 = this.overrideBlockTexture;
        }

        int var35 = (var34 & 15) << 4;
        int var36 = var34 & 240;
        double var14 = (double)((float)var36 / 256.0F);
        double var16 = (double)(((float)var36 + 15.99F) / 256.0F);
        int var18 = this.blockAccess.getBlockMetadata(par2, par3, par4);

        for (int var19 = 0; var19 < 3; ++var19)
        {
            double var20 = (double)var19 * Math.PI * 2.0D / 3.0D + (Math.PI / 2D);
            double var22 = (double)(((float)var35 + 8.0F) / 256.0F);
            double var24 = (double)(((float)var35 + 15.99F) / 256.0F);

            if ((var18 & 1 << var19) != 0)
            {
                var22 = (double)(((float)var35 + 7.99F) / 256.0F);
                var24 = (double)(((float)var35 + 0.0F) / 256.0F);
            }

            double var26 = (double)par2 + 0.5D;
            double var28 = (double)par2 + 0.5D + Math.sin(var20) * 8.0D / 16.0D;
            double var30 = (double)par4 + 0.5D;
            double var32 = (double)par4 + 0.5D + Math.cos(var20) * 8.0D / 16.0D;
            var5.addVertexWithUV(var26, (double)(par3 + 1), var30, var22, var14);
            var5.addVertexWithUV(var26, (double)(par3 + 0), var30, var22, var16);
            var5.addVertexWithUV(var28, (double)(par3 + 0), var32, var24, var16);
            var5.addVertexWithUV(var28, (double)(par3 + 1), var32, var24, var14);
            var5.addVertexWithUV(var28, (double)(par3 + 1), var32, var24, var14);
            var5.addVertexWithUV(var28, (double)(par3 + 0), var32, var24, var16);
            var5.addVertexWithUV(var26, (double)(par3 + 0), var30, var22, var16);
            var5.addVertexWithUV(var26, (double)(par3 + 1), var30, var22, var14);
        }

        par1BlockBrewingStand.setBlockBoundsForItemRender();
        return true;
    }

    /**
     * Render block cauldron
     */
    public boolean renderBlockCauldron(BlockCauldron par1BlockCauldron, int par2, int par3, int par4)
    {
        this.renderStandardBlock(par1BlockCauldron, par2, par3, par4);
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockCauldron.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1BlockCauldron.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;
        float var11;

        if (EntityRenderer.anaglyphEnable)
        {
            float var12 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            var11 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var12;
            var9 = var11;
            var10 = var13;
        }

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        short var20 = 154;
        var11 = 0.125F;
        this.renderSouthFace(par1BlockCauldron, (double)((float)par2 - 1.0F + var11), (double)par3, (double)par4, var20);
        this.renderNorthFace(par1BlockCauldron, (double)((float)par2 + 1.0F - var11), (double)par3, (double)par4, var20);
        this.renderWestFace(par1BlockCauldron, (double)par2, (double)par3, (double)((float)par4 - 1.0F + var11), var20);
        this.renderEastFace(par1BlockCauldron, (double)par2, (double)par3, (double)((float)par4 + 1.0F - var11), var20);
        short var21 = 139;
        this.renderTopFace(par1BlockCauldron, (double)par2, (double)((float)par3 - 1.0F + 0.25F), (double)par4, var21);
        this.renderBottomFace(par1BlockCauldron, (double)par2, (double)((float)par3 + 1.0F - 0.75F), (double)par4, var21);
        int var14 = this.blockAccess.getBlockMetadata(par2, par3, par4);

        if (var14 > 0)
        {
            short var15 = 205;

            if (var14 > 3)
            {
                var14 = 3;
            }

            int var16 = CustomColorizer.getFluidColor(Block.waterStill, this.blockAccess, par2, par3, par4);
            float var17 = (float)(var16 >> 16 & 255) / 255.0F;
            float var18 = (float)(var16 >> 8 & 255) / 255.0F;
            float var19 = (float)(var16 & 255) / 255.0F;
            var5.setColorOpaque_F(var17, var18, var19);
            this.renderTopFace(par1BlockCauldron, (double)par2, (double)((float)par3 - 1.0F + (6.0F + (float)var14 * 3.0F) / 16.0F), (double)par4, var15);
        }

        return true;
    }

    /**
     * Renders flower pot
     */
    public boolean renderBlockFlowerpot(BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4)
    {
        this.renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1BlockFlowerPot.colorMultiplier(this.blockAccess, par2, par3, par4);
        int var8 = par1BlockFlowerPot.getBlockTextureFromSide(0);
        float var9 = (float)(var7 >> 16 & 255) / 255.0F;
        float var10 = (float)(var7 >> 8 & 255) / 255.0F;
        float var11 = (float)(var7 & 255) / 255.0F;
        float var12;
        float var13;

        if (EntityRenderer.anaglyphEnable)
        {
            var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
            float var14 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
            var13 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
            var9 = var12;
            var10 = var14;
            var11 = var13;
        }

        var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
        var12 = 0.1865F;
        this.renderSouthFace(par1BlockFlowerPot, (double)((float)par2 - 0.5F + var12), (double)par3, (double)par4, var8);
        this.renderNorthFace(par1BlockFlowerPot, (double)((float)par2 + 0.5F - var12), (double)par3, (double)par4, var8);
        this.renderWestFace(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 - 0.5F + var12), var8);
        this.renderEastFace(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 + 0.5F - var12), var8);
        this.renderTopFace(par1BlockFlowerPot, (double)par2, (double)((float)par3 - 0.5F + var12 + 0.1875F), (double)par4, Block.dirt.blockIndexInTexture);
        int var19 = this.blockAccess.getBlockMetadata(par2, par3, par4);

        if (var19 != 0)
        {
            var13 = 0.0F;
            float var15 = 4.0F;
            float var16 = 0.0F;
            BlockFlower var17 = null;

            switch (var19)
            {
                case 1:
                    var17 = Block.plantRed;
                    break;

                case 2:
                    var17 = Block.plantYellow;

                case 3:
                case 4:
                case 5:
                case 6:
                default:
                    break;

                case 7:
                    var17 = Block.mushroomRed;
                    break;

                case 8:
                    var17 = Block.mushroomBrown;
            }

            var5.addTranslation(var13 / 16.0F, var15 / 16.0F, var16 / 16.0F);

            if (var17 != null)
            {
                this.renderBlockByRenderType(var17, par2, par3, par4);
            }
            else if (var19 == 9)
            {
                this.renderAllFaces = true;
                float var18 = 0.125F;
                this.setRenderBounds((double)(0.5F - var18), 0.0D, (double)(0.5F - var18), (double)(0.5F + var18), 0.25D, (double)(0.5F + var18));
                this.renderStandardBlock(Block.cactus, par2, par3, par4);
                this.setRenderBounds((double)(0.5F - var18), 0.25D, (double)(0.5F - var18), (double)(0.5F + var18), 0.5D, (double)(0.5F + var18));
                this.renderStandardBlock(Block.cactus, par2, par3, par4);
                this.setRenderBounds((double)(0.5F - var18), 0.5D, (double)(0.5F - var18), (double)(0.5F + var18), 0.75D, (double)(0.5F + var18));
                this.renderStandardBlock(Block.cactus, par2, par3, par4);
                this.renderAllFaces = false;
                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (var19 == 3)
            {
                this.drawCrossedSquares(Block.sapling, 0, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 5)
            {
                this.drawCrossedSquares(Block.sapling, 2, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 4)
            {
                this.drawCrossedSquares(Block.sapling, 1, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 6)
            {
                this.drawCrossedSquares(Block.sapling, 3, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 11)
            {
                var7 = Block.tallGrass.colorMultiplier(this.blockAccess, par2, par3, par4);
                var9 = (float)(var7 >> 16 & 255) / 255.0F;
                var10 = (float)(var7 >> 8 & 255) / 255.0F;
                var11 = (float)(var7 & 255) / 255.0F;
                var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
                this.drawCrossedSquares(Block.tallGrass, 2, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 10)
            {
                this.drawCrossedSquares(Block.deadBush, 2, (double)par2, (double)par3, (double)par4, 0.75F);
            }

            var5.addTranslation(-var13 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
        }

        return true;
    }

    /**
     * Renders anvil
     */
    public boolean renderBlockAnvil(BlockAnvil par1BlockAnvil, int par2, int par3, int par4)
    {
        return this.renderBlockAnvilMetadata(par1BlockAnvil, par2, par3, par4, this.blockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * Renders anvil block with metadata
     */
    public boolean renderBlockAnvilMetadata(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5)
    {
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(par1BlockAnvil.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var7 = 1.0F;
        int var8 = par1BlockAnvil.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var9 = (float)(var8 >> 16 & 255) / 255.0F;
        float var10 = (float)(var8 >> 8 & 255) / 255.0F;
        float var11 = (float)(var8 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
            float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
            float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }

        var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        return this.renderBlockAnvilOrient(par1BlockAnvil, par2, par3, par4, par5, false);
    }

    /**
     * Renders anvil block with orientation
     */
    public boolean renderBlockAnvilOrient(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5, boolean par6)
    {
        int var7 = par6 ? 0 : par5 & 3;
        boolean var8 = false;
        float var9 = 0.0F;

        switch (var7)
        {
            case 0:
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                break;

            case 1:
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                var8 = true;
                break;

            case 2:
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                break;

            case 3:
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                var8 = true;
        }

        var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 0, var9, 0.75F, 0.25F, 0.75F, var8, par6, par5);
        var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 1, var9, 0.5F, 0.0625F, 0.625F, var8, par6, par5);
        var9 = this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 2, var9, 0.25F, 0.3125F, 0.5F, var8, par6, par5);
        this.renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 3, var9, 0.625F, 0.375F, 1.0F, var8, par6, par5);
        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return true;
    }

    /**
     * Renders anvil block with rotation
     */
    public float renderBlockAnvilRotate(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5, float par6, float par7, float par8, float par9, boolean par10, boolean par11, int par12)
    {
        if (par10)
        {
            float var13 = par7;
            par7 = par9;
            par9 = var13;
        }

        par7 /= 2.0F;
        par9 /= 2.0F;
        par1BlockAnvil.field_82521_b = par5;
        this.setRenderBounds((double)(0.5F - par7), (double)par6, (double)(0.5F - par9), (double)(0.5F + par7), (double)(par6 + par8), (double)(0.5F + par9));

        if (par11)
        {
            Tessellator var14 = Tessellator.instance;
            var14.startDrawingQuads();
            var14.setNormal(0.0F, -1.0F, 0.0F);
            this.renderBottomFace(par1BlockAnvil, 0.0D, 0.0D, 0.0D, par1BlockAnvil.getBlockTextureFromSideAndMetadata(0, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0F, 1.0F, 0.0F);
            this.renderTopFace(par1BlockAnvil, 0.0D, 0.0D, 0.0D, par1BlockAnvil.getBlockTextureFromSideAndMetadata(1, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0F, 0.0F, -1.0F);
            this.renderEastFace(par1BlockAnvil, 0.0D, 0.0D, 0.0D, par1BlockAnvil.getBlockTextureFromSideAndMetadata(2, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0F, 0.0F, 1.0F);
            this.renderWestFace(par1BlockAnvil, 0.0D, 0.0D, 0.0D, par1BlockAnvil.getBlockTextureFromSideAndMetadata(3, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(-1.0F, 0.0F, 0.0F);
            this.renderNorthFace(par1BlockAnvil, 0.0D, 0.0D, 0.0D, par1BlockAnvil.getBlockTextureFromSideAndMetadata(4, par12));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(1.0F, 0.0F, 0.0F);
            this.renderSouthFace(par1BlockAnvil, 0.0D, 0.0D, 0.0D, par1BlockAnvil.getBlockTextureFromSideAndMetadata(5, par12));
            var14.draw();
        }
        else
        {
            this.renderStandardBlock(par1BlockAnvil, par2, par3, par4);
        }

        return par6 + par8;
    }

    /**
     * Renders a torch block at the given coordinates
     */
    public boolean renderBlockTorch(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        double var7 = 0.4000000059604645D;
        double var9 = 0.5D - var7;
        double var11 = 0.20000000298023224D;

        if (var5 == 1)
        {
            this.renderTorchAtAngle(par1Block, (double)par2 - var9, (double)par3 + var11, (double)par4, -var7, 0.0D);
        }
        else if (var5 == 2)
        {
            this.renderTorchAtAngle(par1Block, (double)par2 + var9, (double)par3 + var11, (double)par4, var7, 0.0D);
        }
        else if (var5 == 3)
        {
            this.renderTorchAtAngle(par1Block, (double)par2, (double)par3 + var11, (double)par4 - var9, 0.0D, -var7);
        }
        else if (var5 == 4)
        {
            this.renderTorchAtAngle(par1Block, (double)par2, (double)par3 + var11, (double)par4 + var9, 0.0D, var7);
        }
        else
        {
            this.renderTorchAtAngle(par1Block, (double)par2, (double)par3, (double)par4, 0.0D, 0.0D);

            if (par1Block != Block.torchWood && Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
            {
                this.renderSnow(par2, par3, par4, Block.snow.maxY);
            }
        }

        return true;
    }

    /**
     * render a redstone repeater at the given coordinates
     */
    public boolean renderBlockRepeater(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 3;
        int var7 = (var5 & 12) >> 2;
        this.renderStandardBlock(par1Block, par2, par3, par4);
        Tessellator var8 = Tessellator.instance;
        var8.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        double var9 = -0.1875D;
        boolean var11 = ((BlockRedstoneRepeater)par1Block).func_82523_e(this.blockAccess, par2, par3, par4, var5);
        double var12 = 0.0D;
        double var14 = 0.0D;
        double var16 = 0.0D;
        double var18 = 0.0D;

        switch (var6)
        {
            case 0:
                var18 = -0.3125D;
                var14 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;

            case 1:
                var16 = 0.3125D;
                var12 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;

            case 2:
                var18 = 0.3125D;
                var14 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
                break;

            case 3:
                var16 = -0.3125D;
                var12 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
        }

        if (!var11)
        {
            this.renderTorchAtAngle(par1Block, (double)par2 + var12, (double)par3 + var9, (double)par4 + var14, 0.0D, 0.0D);
        }
        else
        {
            this.setOverrideBlockTexture(17);
            byte var20 = 16;
            byte var21 = 16;
            float var22 = 2.0F;
            float var23 = 14.0F;
            float var24 = 7.0F;
            float var25 = 9.0F;

            switch (var6)
            {
                case 1:
                case 3:
                    var22 = 7.0F;
                    var23 = 9.0F;
                    var24 = 2.0F;
                    var25 = 14.0F;

                case 0:
                case 2:
                default:
                    this.setRenderBounds((double)(var22 / 16.0F + (float)var12), 0.125D, (double)(var24 / 16.0F + (float)var14), (double)(var23 / 16.0F + (float)var12), 0.25D, (double)(var25 / 16.0F + (float)var14));
                    var8.addVertexWithUV((double)((float)par2 + var22 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var24 / 16.0F) + var14, (double)(((float)var20 + var22) / 256.0F), (double)(((float)var21 + var24) / 256.0F));
                    var8.addVertexWithUV((double)((float)par2 + var22 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var25 / 16.0F) + var14, (double)(((float)var20 + var22) / 256.0F), (double)(((float)var21 + var25) / 256.0F));
                    var8.addVertexWithUV((double)((float)par2 + var23 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var25 / 16.0F) + var14, (double)(((float)var20 + var23) / 256.0F), (double)(((float)var21 + var25) / 256.0F));
                    var8.addVertexWithUV((double)((float)par2 + var23 / 16.0F) + var12, (double)((float)par3 + 0.25F), (double)((float)par4 + var24 / 16.0F) + var14, (double)(((float)var20 + var23) / 256.0F), (double)(((float)var21 + var24) / 256.0F));
                    this.renderStandardBlock(par1Block, par2, par3, par4);
                    this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
                    this.clearOverrideBlockTexture();
            }
        }

        var8.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        this.renderTorchAtAngle(par1Block, (double)par2 + var16, (double)par3 + var9, (double)par4 + var18, 0.0D, 0.0D);
        int var51 = par1Block.getBlockTextureFromSide(1);
        int var53 = (var51 & 15) << 4;
        int var52 = var51 & 240;
        double var55 = (double)((float)var53 / 256.0F);
        double var54 = (double)(((float)var53 + 15.99F) / 256.0F);
        double var27 = (double)((float)var52 / 256.0F);
        double var29 = (double)(((float)var52 + 15.99F) / 256.0F);
        double var31 = 0.125D;
        double var33 = (double)(par2 + 1);
        double var35 = (double)(par2 + 1);
        double var37 = (double)(par2 + 0);
        double var39 = (double)(par2 + 0);
        double var41 = (double)(par4 + 0);
        double var43 = (double)(par4 + 1);
        double var45 = (double)(par4 + 1);
        double var47 = (double)(par4 + 0);
        double var49 = (double)par3 + var31;

        if (var6 == 2)
        {
            var33 = var35 = (double)(par2 + 0);
            var37 = var39 = (double)(par2 + 1);
            var41 = var47 = (double)(par4 + 1);
            var43 = var45 = (double)(par4 + 0);
        }
        else if (var6 == 3)
        {
            var33 = var39 = (double)(par2 + 0);
            var35 = var37 = (double)(par2 + 1);
            var41 = var43 = (double)(par4 + 0);
            var45 = var47 = (double)(par4 + 1);
        }
        else if (var6 == 1)
        {
            var33 = var39 = (double)(par2 + 1);
            var35 = var37 = (double)(par2 + 0);
            var41 = var43 = (double)(par4 + 1);
            var45 = var47 = (double)(par4 + 0);
        }

        var8.addVertexWithUV(var39, var49, var47, var55, var27);
        var8.addVertexWithUV(var37, var49, var45, var55, var29);
        var8.addVertexWithUV(var35, var49, var43, var54, var29);
        var8.addVertexWithUV(var33, var49, var41, var54, var27);
        return true;
    }

    /**
     * Render all faces of the piston base
     */
    public void renderPistonBaseAllFaces(Block par1Block, int par2, int par3, int par4)
    {
        this.renderAllFaces = true;
        this.renderPistonBase(par1Block, par2, par3, par4, true);
        this.renderAllFaces = false;
    }

    /**
     * renders a block as a piston base
     */
    public boolean renderPistonBase(Block par1Block, int par2, int par3, int par4, boolean par5)
    {
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        boolean var7 = par5 || (var6 & 8) != 0;
        int var8 = BlockPistonBase.getOrientation(var6);

        if (var7)
        {
            switch (var8)
            {
                case 0:
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                    this.setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
                    break;

                case 1:
                    this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
                    break;

                case 2:
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    this.setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
                    break;

                case 3:
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
                    break;

                case 4:
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    this.setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                    break;

                case 5:
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                    this.setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
            }

            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
            this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        }
        else
        {
            switch (var8)
            {
                case 0:
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;

                case 1:
                default:
                    break;

                case 2:
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    break;

                case 3:
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    break;

                case 4:
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    break;

                case 5:
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
            }

            this.renderStandardBlock(par1Block, par2, par3, par4);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
        }

        return true;
    }

    /**
     * Render piston rod up/down
     */
    public void renderPistonRodUD(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14)
    {
        int var16 = 108;

        if (this.overrideBlockTexture >= 0)
        {
            var16 = this.overrideBlockTexture;
        }

        int var17 = (var16 & 15) << 4;
        int var18 = var16 & 240;
        Tessellator var19 = Tessellator.instance;
        double var20 = (double)((float)(var17 + 0) / 256.0F);
        double var22 = (double)((float)(var18 + 0) / 256.0F);
        double var24 = ((double)var17 + par14 - 0.01D) / 256.0D;
        double var26 = ((double)((float)var18 + 4.0F) - 0.01D) / 256.0D;
        var19.setColorOpaque_F(par13, par13, par13);
        var19.addVertexWithUV(par1, par7, par9, var24, var22);
        var19.addVertexWithUV(par1, par5, par9, var20, var22);
        var19.addVertexWithUV(par3, par5, par11, var20, var26);
        var19.addVertexWithUV(par3, par7, par11, var24, var26);
    }

    /**
     * Render piston rod south/north
     */
    public void renderPistonRodSN(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14)
    {
        int var16 = 108;

        if (this.overrideBlockTexture >= 0)
        {
            var16 = this.overrideBlockTexture;
        }

        int var17 = (var16 & 15) << 4;
        int var18 = var16 & 240;
        Tessellator var19 = Tessellator.instance;
        double var20 = (double)((float)(var17 + 0) / 256.0F);
        double var22 = (double)((float)(var18 + 0) / 256.0F);
        double var24 = ((double)var17 + par14 - 0.01D) / 256.0D;
        double var26 = ((double)((float)var18 + 4.0F) - 0.01D) / 256.0D;
        var19.setColorOpaque_F(par13, par13, par13);
        var19.addVertexWithUV(par1, par5, par11, var24, var22);
        var19.addVertexWithUV(par1, par5, par9, var20, var22);
        var19.addVertexWithUV(par3, par7, par9, var20, var26);
        var19.addVertexWithUV(par3, par7, par11, var24, var26);
    }

    /**
     * Render piston rod east/west
     */
    public void renderPistonRodEW(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14)
    {
        int var16 = 108;

        if (this.overrideBlockTexture >= 0)
        {
            var16 = this.overrideBlockTexture;
        }

        int var17 = (var16 & 15) << 4;
        int var18 = var16 & 240;
        Tessellator var19 = Tessellator.instance;
        double var20 = (double)((float)(var17 + 0) / 256.0F);
        double var22 = (double)((float)(var18 + 0) / 256.0F);
        double var24 = ((double)var17 + par14 - 0.01D) / 256.0D;
        double var26 = ((double)((float)var18 + 4.0F) - 0.01D) / 256.0D;
        var19.setColorOpaque_F(par13, par13, par13);
        var19.addVertexWithUV(par3, par5, par9, var24, var22);
        var19.addVertexWithUV(par1, par5, par9, var20, var22);
        var19.addVertexWithUV(par1, par7, par11, var20, var26);
        var19.addVertexWithUV(par3, par7, par11, var24, var26);
    }

    /**
     * Render all faces of the piston extension
     */
    public void renderPistonExtensionAllFaces(Block par1Block, int par2, int par3, int par4, boolean par5)
    {
        this.renderAllFaces = true;
        this.renderPistonExtension(par1Block, par2, par3, par4, par5);
        this.renderAllFaces = false;
    }

    /**
     * renders the pushing part of a piston
     */
    public boolean renderPistonExtension(Block par1Block, int par2, int par3, int par4, boolean par5)
    {
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = BlockPistonExtension.getDirectionMeta(var6);
        float var8 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4);
        float var9 = par5 ? 1.0F : 0.5F;
        double var10 = par5 ? 16.0D : 8.0D;

        switch (var7)
        {
            case 0:
                this.uvRotateEast = 3;
                this.uvRotateWest = 3;
                this.uvRotateSouth = 3;
                this.uvRotateNorth = 3;
                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var9), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var8 * 0.8F, var10);
                this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var9), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var8 * 0.8F, var10);
                this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var9), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var8 * 0.6F, var10);
                this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.25F), (double)((float)par3 + 0.25F + var9), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var8 * 0.6F, var10);
                break;

            case 1:
                this.setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 - 0.25F + 1.0F - var9), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var8 * 0.8F, var10);
                this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 - 0.25F + 1.0F - var9), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var8 * 0.8F, var10);
                this.renderPistonRodUD((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 - 0.25F + 1.0F - var9), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var8 * 0.6F, var10);
                this.renderPistonRodUD((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 - 0.25F + 1.0F - var9), (double)((float)par3 - 0.25F + 1.0F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var8 * 0.6F, var10);
                break;

            case 2:
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var9), var8 * 0.6F, var10);
                this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var9), var8 * 0.6F, var10);
                this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var9), var8 * 0.5F, var10);
                this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.25F), (double)((float)par4 + 0.25F + var9), var8, var10);
                break;

            case 3:
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                this.setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 - 0.25F + 1.0F - var9), (double)((float)par4 - 0.25F + 1.0F), var8 * 0.6F, var10);
                this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 - 0.25F + 1.0F - var9), (double)((float)par4 - 0.25F + 1.0F), var8 * 0.6F, var10);
                this.renderPistonRodSN((double)((float)par2 + 0.375F), (double)((float)par2 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 - 0.25F + 1.0F - var9), (double)((float)par4 - 0.25F + 1.0F), var8 * 0.5F, var10);
                this.renderPistonRodSN((double)((float)par2 + 0.625F), (double)((float)par2 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 - 0.25F + 1.0F - var9), (double)((float)par4 - 0.25F + 1.0F), var8, var10);
                break;

            case 4:
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                this.setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var9), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var8 * 0.5F, var10);
                this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var9), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var8, var10);
                this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var9), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var8 * 0.6F, var10);
                this.renderPistonRodEW((double)((float)par2 + 0.25F), (double)((float)par2 + 0.25F + var9), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var8 * 0.6F, var10);
                break;

            case 5:
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                this.setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                this.renderStandardBlock(par1Block, par2, par3, par4);
                this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var9), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.375F), var8 * 0.5F, var10);
                this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var9), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.625F), var8, var10);
                this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var9), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.375F), (double)((float)par3 + 0.625F), (double)((float)par4 + 0.375F), (double)((float)par4 + 0.375F), var8 * 0.6F, var10);
                this.renderPistonRodEW((double)((float)par2 - 0.25F + 1.0F - var9), (double)((float)par2 - 0.25F + 1.0F), (double)((float)par3 + 0.625F), (double)((float)par3 + 0.375F), (double)((float)par4 + 0.625F), (double)((float)par4 + 0.625F), var8 * 0.6F, var10);
        }

        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        return true;
    }

    /**
     * Renders a lever block at the given coordinates
     */
    public boolean renderBlockLever(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 7;
        boolean var7 = (var5 & 8) > 0;
        Tessellator var8 = Tessellator.instance;
        boolean var9 = this.overrideBlockTexture >= 0;

        if (!var9)
        {
            this.overrideBlockTexture = Block.cobblestone.blockIndexInTexture;
        }

        float var10 = 0.25F;
        float var11 = 0.1875F;
        float var12 = 0.1875F;

        if (var6 == 5)
        {
            this.setRenderBounds((double)(0.5F - var11), 0.0D, (double)(0.5F - var10), (double)(0.5F + var11), (double)var12, (double)(0.5F + var10));
        }
        else if (var6 == 6)
        {
            this.setRenderBounds((double)(0.5F - var10), 0.0D, (double)(0.5F - var11), (double)(0.5F + var10), (double)var12, (double)(0.5F + var11));
        }
        else if (var6 == 4)
        {
            this.setRenderBounds((double)(0.5F - var11), (double)(0.5F - var10), (double)(1.0F - var12), (double)(0.5F + var11), (double)(0.5F + var10), 1.0D);
        }
        else if (var6 == 3)
        {
            this.setRenderBounds((double)(0.5F - var11), (double)(0.5F - var10), 0.0D, (double)(0.5F + var11), (double)(0.5F + var10), (double)var12);
        }
        else if (var6 == 2)
        {
            this.setRenderBounds((double)(1.0F - var12), (double)(0.5F - var10), (double)(0.5F - var11), 1.0D, (double)(0.5F + var10), (double)(0.5F + var11));
        }
        else if (var6 == 1)
        {
            this.setRenderBounds(0.0D, (double)(0.5F - var10), (double)(0.5F - var11), (double)var12, (double)(0.5F + var10), (double)(0.5F + var11));
        }
        else if (var6 == 0)
        {
            this.setRenderBounds((double)(0.5F - var10), (double)(1.0F - var12), (double)(0.5F - var11), (double)(0.5F + var10), 1.0D, (double)(0.5F + var11));
        }
        else if (var6 == 7)
        {
            this.setRenderBounds((double)(0.5F - var11), (double)(1.0F - var12), (double)(0.5F - var10), (double)(0.5F + var11), 1.0D, (double)(0.5F + var10));
        }

        this.renderStandardBlock(par1Block, par2, par3, par4);

        if (!var9)
        {
            this.overrideBlockTexture = -1;
        }

        var8.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var13 = 1.0F;

        if (Block.lightValue[par1Block.blockID] > 0)
        {
            var13 = 1.0F;
        }

        var8.setColorOpaque_F(var13, var13, var13);
        int var14 = par1Block.getBlockTextureFromSide(0);

        if (this.overrideBlockTexture >= 0)
        {
            var14 = this.overrideBlockTexture;
        }

        int var15 = (var14 & 15) << 4;
        int var16 = var14 & 240;
        float var17 = (float)var15 / 256.0F;
        float var18 = ((float)var15 + 15.99F) / 256.0F;
        float var19 = (float)var16 / 256.0F;
        float var20 = ((float)var16 + 15.99F) / 256.0F;
        Vec3[] var21 = new Vec3[8];
        float var22 = 0.0625F;
        float var23 = 0.0625F;
        float var24 = 0.625F;
        var21[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var22), 0.0D, (double)(-var23));
        var21[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var22, 0.0D, (double)(-var23));
        var21[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var22, 0.0D, (double)var23);
        var21[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var22), 0.0D, (double)var23);
        var21[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var22), (double)var24, (double)(-var23));
        var21[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var22, (double)var24, (double)(-var23));
        var21[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var22, (double)var24, (double)var23);
        var21[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var22), (double)var24, (double)var23);

        for (int var25 = 0; var25 < 8; ++var25)
        {
            if (var7)
            {
                var21[var25].zCoord -= 0.0625D;
                var21[var25].rotateAroundX(((float)Math.PI * 2F / 9F));
            }
            else
            {
                var21[var25].zCoord += 0.0625D;
                var21[var25].rotateAroundX(-((float)Math.PI * 2F / 9F));
            }

            if (var6 == 0 || var6 == 7)
            {
                var21[var25].rotateAroundZ((float)Math.PI);
            }

            if (var6 == 6 || var6 == 0)
            {
                var21[var25].rotateAroundY(((float)Math.PI / 2F));
            }

            if (var6 > 0 && var6 < 5)
            {
                var21[var25].yCoord -= 0.375D;
                var21[var25].rotateAroundX(((float)Math.PI / 2F));

                if (var6 == 4)
                {
                    var21[var25].rotateAroundY(0.0F);
                }

                if (var6 == 3)
                {
                    var21[var25].rotateAroundY((float)Math.PI);
                }

                if (var6 == 2)
                {
                    var21[var25].rotateAroundY(((float)Math.PI / 2F));
                }

                if (var6 == 1)
                {
                    var21[var25].rotateAroundY(-((float)Math.PI / 2F));
                }

                var21[var25].xCoord += (double)par2 + 0.5D;
                var21[var25].yCoord += (double)((float)par3 + 0.5F);
                var21[var25].zCoord += (double)par4 + 0.5D;
            }
            else if (var6 != 0 && var6 != 7)
            {
                var21[var25].xCoord += (double)par2 + 0.5D;
                var21[var25].yCoord += (double)((float)par3 + 0.125F);
                var21[var25].zCoord += (double)par4 + 0.5D;
            }
            else
            {
                var21[var25].xCoord += (double)par2 + 0.5D;
                var21[var25].yCoord += (double)((float)par3 + 0.875F);
                var21[var25].zCoord += (double)par4 + 0.5D;
            }
        }

        Vec3 var30 = null;
        Vec3 var26 = null;
        Vec3 var27 = null;
        Vec3 var28 = null;

        for (int var29 = 0; var29 < 6; ++var29)
        {
            if (var29 == 0)
            {
                var17 = (float)(var15 + 7) / 256.0F;
                var18 = ((float)(var15 + 9) - 0.01F) / 256.0F;
                var19 = (float)(var16 + 6) / 256.0F;
                var20 = ((float)(var16 + 8) - 0.01F) / 256.0F;
            }
            else if (var29 == 2)
            {
                var17 = (float)(var15 + 7) / 256.0F;
                var18 = ((float)(var15 + 9) - 0.01F) / 256.0F;
                var19 = (float)(var16 + 6) / 256.0F;
                var20 = ((float)(var16 + 16) - 0.01F) / 256.0F;
            }

            if (var29 == 0)
            {
                var30 = var21[0];
                var26 = var21[1];
                var27 = var21[2];
                var28 = var21[3];
            }
            else if (var29 == 1)
            {
                var30 = var21[7];
                var26 = var21[6];
                var27 = var21[5];
                var28 = var21[4];
            }
            else if (var29 == 2)
            {
                var30 = var21[1];
                var26 = var21[0];
                var27 = var21[4];
                var28 = var21[5];
            }
            else if (var29 == 3)
            {
                var30 = var21[2];
                var26 = var21[1];
                var27 = var21[5];
                var28 = var21[6];
            }
            else if (var29 == 4)
            {
                var30 = var21[3];
                var26 = var21[2];
                var27 = var21[6];
                var28 = var21[7];
            }
            else if (var29 == 5)
            {
                var30 = var21[0];
                var26 = var21[3];
                var27 = var21[7];
                var28 = var21[4];
            }

            var8.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, (double)var17, (double)var20);
            var8.addVertexWithUV(var26.xCoord, var26.yCoord, var26.zCoord, (double)var18, (double)var20);
            var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, (double)var18, (double)var19);
            var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, (double)var17, (double)var19);
        }

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        return true;
    }

    /**
     * Renders a trip wire source block at the given coordinates
     */
    public boolean renderBlockTripWireSource(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = var6 & 3;
        boolean var8 = (var6 & 4) == 4;
        boolean var9 = (var6 & 8) == 8;
        boolean var10 = !this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
        boolean var11 = this.overrideBlockTexture >= 0;

        if (!var11)
        {
            this.overrideBlockTexture = Block.planks.blockIndexInTexture;
        }

        float var12 = 0.25F;
        float var13 = 0.125F;
        float var14 = 0.125F;
        float var15 = 0.3F - var12;
        float var16 = 0.3F + var12;

        if (var7 == 2)
        {
            this.setRenderBounds((double)(0.5F - var13), (double)var15, (double)(1.0F - var14), (double)(0.5F + var13), (double)var16, 1.0D);
        }
        else if (var7 == 0)
        {
            this.setRenderBounds((double)(0.5F - var13), (double)var15, 0.0D, (double)(0.5F + var13), (double)var16, (double)var14);
        }
        else if (var7 == 1)
        {
            this.setRenderBounds((double)(1.0F - var14), (double)var15, (double)(0.5F - var13), 1.0D, (double)var16, (double)(0.5F + var13));
        }
        else if (var7 == 3)
        {
            this.setRenderBounds(0.0D, (double)var15, (double)(0.5F - var13), (double)var14, (double)var16, (double)(0.5F + var13));
        }

        this.renderStandardBlock(par1Block, par2, par3, par4);

        if (!var11)
        {
            this.overrideBlockTexture = -1;
        }

        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var17 = 1.0F;

        if (Block.lightValue[par1Block.blockID] > 0)
        {
            var17 = 1.0F;
        }

        var5.setColorOpaque_F(var17, var17, var17);
        int var18 = par1Block.getBlockTextureFromSide(0);

        if (this.overrideBlockTexture >= 0)
        {
            var18 = this.overrideBlockTexture;
        }

        int var19 = (var18 & 15) << 4;
        int var20 = var18 & 240;
        float var21 = (float)var19 / 256.0F;
        float var22 = ((float)var19 + 15.99F) / 256.0F;
        float var23 = (float)var20 / 256.0F;
        float var24 = ((float)var20 + 15.99F) / 256.0F;
        Vec3[] var25 = new Vec3[8];
        float var26 = 0.046875F;
        float var27 = 0.046875F;
        float var28 = 0.3125F;
        var25[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var26), 0.0D, (double)(-var27));
        var25[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var26, 0.0D, (double)(-var27));
        var25[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var26, 0.0D, (double)var27);
        var25[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var26), 0.0D, (double)var27);
        var25[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var26), (double)var28, (double)(-var27));
        var25[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var26, (double)var28, (double)(-var27));
        var25[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var26, (double)var28, (double)var27);
        var25[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var26), (double)var28, (double)var27);

        for (int var29 = 0; var29 < 8; ++var29)
        {
            var25[var29].zCoord += 0.0625D;

            if (var9)
            {
                var25[var29].rotateAroundX(0.5235988F);
                var25[var29].yCoord -= 0.4375D;
            }
            else if (var8)
            {
                var25[var29].rotateAroundX(0.08726647F);
                var25[var29].yCoord -= 0.4375D;
            }
            else
            {
                var25[var29].rotateAroundX(-((float)Math.PI * 2F / 9F));
                var25[var29].yCoord -= 0.375D;
            }

            var25[var29].rotateAroundX(((float)Math.PI / 2F));

            if (var7 == 2)
            {
                var25[var29].rotateAroundY(0.0F);
            }

            if (var7 == 0)
            {
                var25[var29].rotateAroundY((float)Math.PI);
            }

            if (var7 == 1)
            {
                var25[var29].rotateAroundY(((float)Math.PI / 2F));
            }

            if (var7 == 3)
            {
                var25[var29].rotateAroundY(-((float)Math.PI / 2F));
            }

            var25[var29].xCoord += (double)par2 + 0.5D;
            var25[var29].yCoord += (double)((float)par3 + 0.3125F);
            var25[var29].zCoord += (double)par4 + 0.5D;
        }

        Vec3 var61 = null;
        Vec3 var30 = null;
        Vec3 var31 = null;
        Vec3 var32 = null;
        byte var33 = 7;
        byte var34 = 9;
        byte var35 = 9;
        byte var36 = 16;

        for (int var37 = 0; var37 < 6; ++var37)
        {
            if (var37 == 0)
            {
                var61 = var25[0];
                var30 = var25[1];
                var31 = var25[2];
                var32 = var25[3];
                var21 = (float)(var19 + var33) / 256.0F;
                var22 = (float)(var19 + var34) / 256.0F;
                var23 = (float)(var20 + var35) / 256.0F;
                var24 = (float)(var20 + var35 + 2) / 256.0F;
            }
            else if (var37 == 1)
            {
                var61 = var25[7];
                var30 = var25[6];
                var31 = var25[5];
                var32 = var25[4];
            }
            else if (var37 == 2)
            {
                var61 = var25[1];
                var30 = var25[0];
                var31 = var25[4];
                var32 = var25[5];
                var21 = (float)(var19 + var33) / 256.0F;
                var22 = (float)(var19 + var34) / 256.0F;
                var23 = (float)(var20 + var35) / 256.0F;
                var24 = (float)(var20 + var36) / 256.0F;
            }
            else if (var37 == 3)
            {
                var61 = var25[2];
                var30 = var25[1];
                var31 = var25[5];
                var32 = var25[6];
            }
            else if (var37 == 4)
            {
                var61 = var25[3];
                var30 = var25[2];
                var31 = var25[6];
                var32 = var25[7];
            }
            else if (var37 == 5)
            {
                var61 = var25[0];
                var30 = var25[3];
                var31 = var25[7];
                var32 = var25[4];
            }

            var5.addVertexWithUV(var61.xCoord, var61.yCoord, var61.zCoord, (double)var21, (double)var24);
            var5.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, (double)var22, (double)var24);
            var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, (double)var22, (double)var23);
            var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, (double)var21, (double)var23);
        }

        float var62 = 0.09375F;
        float var38 = 0.09375F;
        float var39 = 0.03125F;
        var25[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var62), 0.0D, (double)(-var38));
        var25[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var62, 0.0D, (double)(-var38));
        var25[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var62, 0.0D, (double)var38);
        var25[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var62), 0.0D, (double)var38);
        var25[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var62), (double)var39, (double)(-var38));
        var25[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var62, (double)var39, (double)(-var38));
        var25[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)var62, (double)var39, (double)var38);
        var25[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool((double)(-var62), (double)var39, (double)var38);

        for (int var40 = 0; var40 < 8; ++var40)
        {
            var25[var40].zCoord += 0.21875D;

            if (var9)
            {
                var25[var40].yCoord -= 0.09375D;
                var25[var40].zCoord -= 0.1625D;
                var25[var40].rotateAroundX(0.0F);
            }
            else if (var8)
            {
                var25[var40].yCoord += 0.015625D;
                var25[var40].zCoord -= 0.171875D;
                var25[var40].rotateAroundX(0.17453294F);
            }
            else
            {
                var25[var40].rotateAroundX(0.87266463F);
            }

            if (var7 == 2)
            {
                var25[var40].rotateAroundY(0.0F);
            }

            if (var7 == 0)
            {
                var25[var40].rotateAroundY((float)Math.PI);
            }

            if (var7 == 1)
            {
                var25[var40].rotateAroundY(((float)Math.PI / 2F));
            }

            if (var7 == 3)
            {
                var25[var40].rotateAroundY(-((float)Math.PI / 2F));
            }

            var25[var40].xCoord += (double)par2 + 0.5D;
            var25[var40].yCoord += (double)((float)par3 + 0.3125F);
            var25[var40].zCoord += (double)par4 + 0.5D;
        }

        byte var63 = 5;
        byte var41 = 11;
        byte var42 = 3;
        byte var43 = 9;

        for (int var44 = 0; var44 < 6; ++var44)
        {
            if (var44 == 0)
            {
                var61 = var25[0];
                var30 = var25[1];
                var31 = var25[2];
                var32 = var25[3];
                var21 = (float)(var19 + var63) / 256.0F;
                var22 = (float)(var19 + var41) / 256.0F;
                var23 = (float)(var20 + var42) / 256.0F;
                var24 = (float)(var20 + var43) / 256.0F;
            }
            else if (var44 == 1)
            {
                var61 = var25[7];
                var30 = var25[6];
                var31 = var25[5];
                var32 = var25[4];
            }
            else if (var44 == 2)
            {
                var61 = var25[1];
                var30 = var25[0];
                var31 = var25[4];
                var32 = var25[5];
                var21 = (float)(var19 + var63) / 256.0F;
                var22 = (float)(var19 + var41) / 256.0F;
                var23 = (float)(var20 + var42) / 256.0F;
                var24 = (float)(var20 + var42 + 2) / 256.0F;
            }
            else if (var44 == 3)
            {
                var61 = var25[2];
                var30 = var25[1];
                var31 = var25[5];
                var32 = var25[6];
            }
            else if (var44 == 4)
            {
                var61 = var25[3];
                var30 = var25[2];
                var31 = var25[6];
                var32 = var25[7];
            }
            else if (var44 == 5)
            {
                var61 = var25[0];
                var30 = var25[3];
                var31 = var25[7];
                var32 = var25[4];
            }

            var5.addVertexWithUV(var61.xCoord, var61.yCoord, var61.zCoord, (double)var21, (double)var24);
            var5.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, (double)var22, (double)var24);
            var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, (double)var22, (double)var23);
            var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, (double)var21, (double)var23);
        }

        if (var8)
        {
            double var64 = var25[0].yCoord;
            float var46 = 0.03125F;
            float var47 = 0.5F - var46 / 2.0F;
            float var48 = var47 + var46;
            int var49 = (Block.tripWire.blockIndexInTexture & 15) << 4;
            int var50 = Block.tripWire.blockIndexInTexture & 240;
            double var51 = (double)((float)var49 / 256.0F);
            double var53 = (double)((float)(var49 + 16) / 256.0F);
            double var55 = (double)((float)(var50 + (var8 ? 2 : 0)) / 256.0F);
            double var57 = (double)((float)(var50 + (var8 ? 4 : 2)) / 256.0F);
            double var59 = (double)(var10 ? 3.5F : 1.5F) / 16.0D;
            var17 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4) * 0.75F;
            var5.setColorOpaque_F(var17, var17, var17);

            if (var7 == 2)
            {
                var5.addVertexWithUV((double)((float)par2 + var47), (double)par3 + var59, (double)par4 + 0.25D, var51, var55);
                var5.addVertexWithUV((double)((float)par2 + var48), (double)par3 + var59, (double)par4 + 0.25D, var51, var57);
                var5.addVertexWithUV((double)((float)par2 + var48), (double)par3 + var59, (double)par4, var53, var57);
                var5.addVertexWithUV((double)((float)par2 + var47), (double)par3 + var59, (double)par4, var53, var55);
                var5.addVertexWithUV((double)((float)par2 + var47), var64, (double)par4 + 0.5D, var51, var55);
                var5.addVertexWithUV((double)((float)par2 + var48), var64, (double)par4 + 0.5D, var51, var57);
                var5.addVertexWithUV((double)((float)par2 + var48), (double)par3 + var59, (double)par4 + 0.25D, var53, var57);
                var5.addVertexWithUV((double)((float)par2 + var47), (double)par3 + var59, (double)par4 + 0.25D, var53, var55);
            }
            else if (var7 == 0)
            {
                var5.addVertexWithUV((double)((float)par2 + var47), (double)par3 + var59, (double)par4 + 0.75D, var51, var55);
                var5.addVertexWithUV((double)((float)par2 + var48), (double)par3 + var59, (double)par4 + 0.75D, var51, var57);
                var5.addVertexWithUV((double)((float)par2 + var48), var64, (double)par4 + 0.5D, var53, var57);
                var5.addVertexWithUV((double)((float)par2 + var47), var64, (double)par4 + 0.5D, var53, var55);
                var5.addVertexWithUV((double)((float)par2 + var47), (double)par3 + var59, (double)(par4 + 1), var51, var55);
                var5.addVertexWithUV((double)((float)par2 + var48), (double)par3 + var59, (double)(par4 + 1), var51, var57);
                var5.addVertexWithUV((double)((float)par2 + var48), (double)par3 + var59, (double)par4 + 0.75D, var53, var57);
                var5.addVertexWithUV((double)((float)par2 + var47), (double)par3 + var59, (double)par4 + 0.75D, var53, var55);
            }
            else if (var7 == 1)
            {
                var5.addVertexWithUV((double)par2, (double)par3 + var59, (double)((float)par4 + var48), var51, var57);
                var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var59, (double)((float)par4 + var48), var53, var57);
                var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var59, (double)((float)par4 + var47), var53, var55);
                var5.addVertexWithUV((double)par2, (double)par3 + var59, (double)((float)par4 + var47), var51, var55);
                var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var59, (double)((float)par4 + var48), var51, var57);
                var5.addVertexWithUV((double)par2 + 0.5D, var64, (double)((float)par4 + var48), var53, var57);
                var5.addVertexWithUV((double)par2 + 0.5D, var64, (double)((float)par4 + var47), var53, var55);
                var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var59, (double)((float)par4 + var47), var51, var55);
            }
            else
            {
                var5.addVertexWithUV((double)par2 + 0.5D, var64, (double)((float)par4 + var48), var51, var57);
                var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var59, (double)((float)par4 + var48), var53, var57);
                var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var59, (double)((float)par4 + var47), var53, var55);
                var5.addVertexWithUV((double)par2 + 0.5D, var64, (double)((float)par4 + var47), var51, var55);
                var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var59, (double)((float)par4 + var48), var51, var57);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var59, (double)((float)par4 + var48), var53, var57);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var59, (double)((float)par4 + var47), var53, var55);
                var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var59, (double)((float)par4 + var47), var51, var55);
            }
        }

        return true;
    }

    /**
     * Renders a trip wire block at the given coordinates
     */
    public boolean renderBlockTripWire(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.getBlockTextureFromSide(0);
        int var7 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        boolean var8 = (var7 & 4) == 4;
        boolean var9 = (var7 & 2) == 2;

        if (this.overrideBlockTexture >= 0)
        {
            var6 = this.overrideBlockTexture;
        }

        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var10 = par1Block.getBlockBrightness(this.blockAccess, par2, par3, par4) * 0.75F;
        var5.setColorOpaque_F(var10, var10, var10);
        int var11 = (var6 & 15) << 4;
        int var12 = var6 & 240;
        double var13 = (double)((float)var11 / 256.0F);
        double var15 = (double)((float)(var11 + 16) / 256.0F);
        double var17 = (double)((float)(var12 + (var8 ? 2 : 0)) / 256.0F);
        double var19 = (double)((float)(var12 + (var8 ? 4 : 2)) / 256.0F);
        double var21 = (double)(var9 ? 3.5F : 1.5F) / 16.0D;
        boolean var23 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 1);
        boolean var24 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 3);
        boolean var25 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 2);
        boolean var26 = BlockTripWire.func_72148_a(this.blockAccess, par2, par3, par4, var7, 0);
        float var27 = 0.03125F;
        float var28 = 0.5F - var27 / 2.0F;
        float var29 = var28 + var27;

        if (!var25 && !var24 && !var26 && !var23)
        {
            var25 = true;
            var26 = true;
        }

        if (var25)
        {
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.25D, var13, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.25D, var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.25D, var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.25D, var13, var17);
        }

        if (var25 || var26 && !var24 && !var23)
        {
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.5D, var13, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.5D, var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.25D, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.25D, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.25D, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.25D, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.5D, var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.5D, var13, var17);
        }

        if (var26 || var25 && !var24 && !var23)
        {
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.75D, var13, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.75D, var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.5D, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.5D, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.5D, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.5D, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.75D, var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.75D, var13, var17);
        }

        if (var26)
        {
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)(par4 + 1), var13, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)(par4 + 1), var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.75D, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.75D, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)par4 + 0.75D, var15, var17);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)par4 + 0.75D, var15, var19);
            var5.addVertexWithUV((double)((float)par2 + var29), (double)par3 + var21, (double)(par4 + 1), var13, var19);
            var5.addVertexWithUV((double)((float)par2 + var28), (double)par3 + var21, (double)(par4 + 1), var13, var17);
        }

        if (var23)
        {
            var5.addVertexWithUV((double)par2, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
        }

        if (var23 || var24 && !var25 && !var26)
        {
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2 + 0.25D, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
        }

        if (var24 || var23 && !var25 && !var26)
        {
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2 + 0.5D, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
        }

        if (var24)
        {
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
            var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var28), var13, var17);
            var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var21, (double)((float)par4 + var28), var15, var17);
            var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var21, (double)((float)par4 + var29), var15, var19);
            var5.addVertexWithUV((double)par2 + 0.75D, (double)par3 + var21, (double)((float)par4 + var29), var13, var19);
        }

        return true;
    }

    /**
     * Renders a fire block at the given coordinates
     */
    public boolean renderBlockFire(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.getBlockTextureFromSide(0);

        if (this.overrideBlockTexture >= 0)
        {
            var6 = this.overrideBlockTexture;
        }

        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        int var7 = (var6 & 15) << 4;
        int var8 = var6 & 240;
        double var9 = (double)((float)var7 / 256.0F);
        double var11 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var13 = (double)((float)var8 / 256.0F);
        double var15 = (double)(((float)var8 + 15.99F) / 256.0F);
        float var17 = 1.4F;
        double var18;
        double var20;
        double var22;
        double var24;
        double var26;
        double var28;
        double var30;

        if (!this.blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 - 1, par4))
        {
            float var36 = 0.2F;
            float var33 = 0.0625F;

            if ((par2 + par3 + par4 & 1) == 1)
            {
                var9 = (double)((float)var7 / 256.0F);
                var11 = (double)(((float)var7 + 15.99F) / 256.0F);
                var13 = (double)((float)(var8 + 16) / 256.0F);
                var15 = (double)(((float)var8 + 15.99F + 16.0F) / 256.0F);
            }

            if ((par2 / 2 + par3 / 2 + par4 / 2 & 1) == 1)
            {
                var18 = var11;
                var11 = var9;
                var9 = var18;
            }

            if (Block.fire.canBlockCatchFire(this.blockAccess, par2 - 1, par3, par4))
            {
                var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var11, var13);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var11, var15);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
                var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var13);
                var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var13);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var11, var15);
                var5.addVertexWithUV((double)((float)par2 + var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var11, var13);
            }

            if (Block.fire.canBlockCatchFire(this.blockAccess, par2 + 1, par3, par4))
            {
                var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var13);
                var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
                var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var11, var15);
                var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var11, var13);
                var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 1), var11, var13);
                var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1), var11, var15);
                var5.addVertexWithUV((double)(par2 + 1 - 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
                var5.addVertexWithUV((double)((float)(par2 + 1) - var36), (double)((float)par3 + var17 + var33), (double)(par4 + 0), var9, var13);
            }

            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 - 1))
            {
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var11, var13);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var11, var15);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var9, var13);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var9, var13);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var9, var15);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 0), var11, var15);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)par4 + var36), var11, var13);
            }

            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3, par4 + 1))
            {
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var9, var13);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var9, var15);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var11, var15);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var11, var13);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var11, var13);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var11, var15);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 0) + var33), (double)(par4 + 1 - 0), var9, var15);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17 + var33), (double)((float)(par4 + 1) - var36), var9, var13);
            }

            if (Block.fire.canBlockCatchFire(this.blockAccess, par2, par3 + 1, par4))
            {
                var18 = (double)par2 + 0.5D + 0.5D;
                var20 = (double)par2 + 0.5D - 0.5D;
                var22 = (double)par4 + 0.5D + 0.5D;
                var24 = (double)par4 + 0.5D - 0.5D;
                var26 = (double)par2 + 0.5D - 0.5D;
                var28 = (double)par2 + 0.5D + 0.5D;
                var30 = (double)par4 + 0.5D - 0.5D;
                double var34 = (double)par4 + 0.5D + 0.5D;
                var9 = (double)((float)var7 / 256.0F);
                var11 = (double)(((float)var7 + 15.99F) / 256.0F);
                var13 = (double)((float)var8 / 256.0F);
                var15 = (double)(((float)var8 + 15.99F) / 256.0F);
                ++par3;
                var17 = -0.2F;

                if ((par2 + par3 + par4 & 1) == 0)
                {
                    var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 0), var11, var13);
                    var5.addVertexWithUV(var18, (double)(par3 + 0), (double)(par4 + 0), var11, var15);
                    var5.addVertexWithUV(var18, (double)(par3 + 0), (double)(par4 + 1), var9, var15);
                    var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 1), var9, var13);
                    var9 = (double)((float)var7 / 256.0F);
                    var11 = (double)(((float)var7 + 15.99F) / 256.0F);
                    var13 = (double)((float)(var8 + 16) / 256.0F);
                    var15 = (double)(((float)var8 + 15.99F + 16.0F) / 256.0F);
                    var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 1), var11, var13);
                    var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 1), var11, var15);
                    var5.addVertexWithUV(var20, (double)(par3 + 0), (double)(par4 + 0), var9, var15);
                    var5.addVertexWithUV(var28, (double)((float)par3 + var17), (double)(par4 + 0), var9, var13);
                }
                else
                {
                    var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var34, var11, var13);
                    var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var24, var11, var15);
                    var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var24, var9, var15);
                    var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var34, var9, var13);
                    var9 = (double)((float)var7 / 256.0F);
                    var11 = (double)(((float)var7 + 15.99F) / 256.0F);
                    var13 = (double)((float)(var8 + 16) / 256.0F);
                    var15 = (double)(((float)var8 + 15.99F + 16.0F) / 256.0F);
                    var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var30, var11, var13);
                    var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var22, var11, var15);
                    var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var22, var9, var15);
                    var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var30, var9, var13);
                }
            }
        }
        else
        {
            double var32 = (double)par2 + 0.5D + 0.2D;
            var18 = (double)par2 + 0.5D - 0.2D;
            var20 = (double)par4 + 0.5D + 0.2D;
            var22 = (double)par4 + 0.5D - 0.2D;
            var24 = (double)par2 + 0.5D - 0.3D;
            var26 = (double)par2 + 0.5D + 0.3D;
            var28 = (double)par4 + 0.5D - 0.3D;
            var30 = (double)par4 + 0.5D + 0.3D;
            var5.addVertexWithUV(var24, (double)((float)par3 + var17), (double)(par4 + 1), var11, var13);
            var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 1), var11, var15);
            var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV(var24, (double)((float)par3 + var17), (double)(par4 + 0), var9, var13);
            var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 0), var11, var13);
            var5.addVertexWithUV(var18, (double)(par3 + 0), (double)(par4 + 0), var11, var15);
            var5.addVertexWithUV(var18, (double)(par3 + 0), (double)(par4 + 1), var9, var15);
            var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 1), var9, var13);
            var9 = (double)((float)var7 / 256.0F);
            var11 = (double)(((float)var7 + 15.99F) / 256.0F);
            var13 = (double)((float)(var8 + 16) / 256.0F);
            var15 = (double)(((float)var8 + 15.99F + 16.0F) / 256.0F);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var30, var11, var13);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var22, var11, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var22, var9, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var30, var9, var13);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var28, var11, var13);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var20, var11, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var20, var9, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var28, var9, var13);
            var32 = (double)par2 + 0.5D - 0.5D;
            var18 = (double)par2 + 0.5D + 0.5D;
            var20 = (double)par4 + 0.5D - 0.5D;
            var22 = (double)par4 + 0.5D + 0.5D;
            var24 = (double)par2 + 0.5D - 0.4D;
            var26 = (double)par2 + 0.5D + 0.4D;
            var28 = (double)par4 + 0.5D - 0.4D;
            var30 = (double)par4 + 0.5D + 0.4D;
            var5.addVertexWithUV(var24, (double)((float)par3 + var17), (double)(par4 + 0), var9, var13);
            var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 0), var9, var15);
            var5.addVertexWithUV(var32, (double)(par3 + 0), (double)(par4 + 1), var11, var15);
            var5.addVertexWithUV(var24, (double)((float)par3 + var17), (double)(par4 + 1), var11, var13);
            var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 1), var9, var13);
            var5.addVertexWithUV(var18, (double)(par3 + 0), (double)(par4 + 1), var9, var15);
            var5.addVertexWithUV(var18, (double)(par3 + 0), (double)(par4 + 0), var11, var15);
            var5.addVertexWithUV(var26, (double)((float)par3 + var17), (double)(par4 + 0), var11, var13);
            var9 = (double)((float)var7 / 256.0F);
            var11 = (double)(((float)var7 + 15.99F) / 256.0F);
            var13 = (double)((float)var8 / 256.0F);
            var15 = (double)(((float)var8 + 15.99F) / 256.0F);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var30, var9, var13);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var22, var9, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var22, var11, var15);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var30, var11, var13);
            var5.addVertexWithUV((double)(par2 + 1), (double)((float)par3 + var17), var28, var9, var13);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), var20, var9, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), var20, var11, var15);
            var5.addVertexWithUV((double)(par2 + 0), (double)((float)par3 + var17), var28, var11, var13);
        }

        return true;
    }

    /**
     * Renders a redstone wire block at the given coordinates
     */
    public boolean renderBlockRedstoneWire(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = par1Block.getBlockTextureFromSideAndMetadata(1, var6);

        if (this.overrideBlockTexture >= 0)
        {
            var7 = this.overrideBlockTexture;
        }

        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var8 = 1.0F;
        float var9 = (float)var6 / 15.0F;
        float var10 = var9 * 0.6F + 0.4F;

        if (var6 == 0)
        {
            var10 = 0.3F;
        }

        float var11 = var9 * var9 * 0.7F - 0.5F;
        float var12 = var9 * var9 * 0.6F - 0.7F;

        if (var11 < 0.0F)
        {
            var11 = 0.0F;
        }

        if (var12 < 0.0F)
        {
            var12 = 0.0F;
        }

        int var13 = CustomColorizer.getRedstoneColor(var6);
        int var14;
        int var15;

        if (var13 != -1)
        {
            var14 = var13 >> 16 & 255;
            var15 = var13 >> 8 & 255;
            int var16 = var13 & 255;
            var10 = (float)var14 / 255.0F;
            var11 = (float)var15 / 255.0F;
            var12 = (float)var16 / 255.0F;
        }

        var5.setColorOpaque_F(var10, var11, var12);
        var14 = (var7 & 15) << 4;
        var15 = var7 & 240;
        double var33 = (double)((float)var14 / 256.0F);
        double var18 = (double)(((float)var14 + 15.99F) / 256.0F);
        double var20 = (double)((float)var15 / 256.0F);
        double var22 = (double)(((float)var15 + 15.99F) / 256.0F);
        boolean var24 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3, par4, 1) || !this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 - 1, par4, -1);
        boolean var25 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3, par4, 3) || !this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 - 1, par4, -1);
        boolean var26 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 - 1, 2) || !this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 - 1, -1);
        boolean var27 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 + 1, 0) || !this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 + 1, -1);

        if (!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
        {
            if (this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 + 1, par4, -1))
            {
                var24 = true;
            }

            if (this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 + 1, par4, -1))
            {
                var25 = true;
            }

            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 - 1, -1))
            {
                var26 = true;
            }

            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 + 1, -1))
            {
                var27 = true;
            }
        }

        float var28 = (float)(par2 + 0);
        float var29 = (float)(par2 + 1);
        float var30 = (float)(par4 + 0);
        float var31 = (float)(par4 + 1);
        byte var32 = 0;

        if ((var24 || var25) && !var26 && !var27)
        {
            var32 = 1;
        }

        if ((var26 || var27) && !var25 && !var24)
        {
            var32 = 2;
        }

        if (var32 != 0)
        {
            var33 = (double)((float)(var14 + 16) / 256.0F);
            var18 = (double)(((float)(var14 + 16) + 15.99F) / 256.0F);
            var20 = (double)((float)var15 / 256.0F);
            var22 = (double)(((float)var15 + 15.99F) / 256.0F);
        }

        if (var32 == 0)
        {
            if (!var24)
            {
                var28 += 0.3125F;
            }

            if (!var24)
            {
                var33 += 0.01953125D;
            }

            if (!var25)
            {
                var29 -= 0.3125F;
            }

            if (!var25)
            {
                var18 -= 0.01953125D;
            }

            if (!var26)
            {
                var30 += 0.3125F;
            }

            if (!var26)
            {
                var20 += 0.01953125D;
            }

            if (!var27)
            {
                var31 -= 0.3125F;
            }

            if (!var27)
            {
                var22 -= 0.01953125D;
            }

            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var31, var18, var22);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var30, var18, var20);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var30, var33, var20);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var31, var33, var22);
            var5.setColorOpaque_F(var8, var8, var8);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var31, var18, var22 + 0.0625D);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var30, var18, var20 + 0.0625D);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var30, var33, var20 + 0.0625D);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var31, var33, var22 + 0.0625D);
        }
        else if (var32 == 1)
        {
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var31, var18, var22);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var30, var18, var20);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var30, var33, var20);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var31, var33, var22);
            var5.setColorOpaque_F(var8, var8, var8);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var31, var18, var22 + 0.0625D);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var30, var18, var20 + 0.0625D);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var30, var33, var20 + 0.0625D);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var31, var33, var22 + 0.0625D);
        }
        else if (var32 == 2)
        {
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var31, var18, var22);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var30, var33, var22);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var30, var33, var20);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var31, var18, var20);
            var5.setColorOpaque_F(var8, var8, var8);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var31, var18, var22 + 0.0625D);
            var5.addVertexWithUV((double)var29, (double)par3 + 0.015625D, (double)var30, var33, var22 + 0.0625D);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var30, var33, var20 + 0.0625D);
            var5.addVertexWithUV((double)var28, (double)par3 + 0.015625D, (double)var31, var18, var20 + 0.0625D);
        }

        if (!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
        {
            var33 = (double)((float)(var14 + 16) / 256.0F);
            var18 = (double)(((float)(var14 + 16) + 15.99F) / 256.0F);
            var20 = (double)((float)var15 / 256.0F);
            var22 = (double)(((float)var15 + 15.99F) / 256.0F);

            if (this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4) == Block.redstoneWire.blockID)
            {
                var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), var18, var20);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 1), var33, var20);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 0), var33, var22);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), var18, var22);
                var5.setColorOpaque_F(var8, var8, var8);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), var18, var20 + 0.0625D);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 1), var33, var20 + 0.0625D);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)(par3 + 0), (double)(par4 + 0), var33, var22 + 0.0625D);
                var5.addVertexWithUV((double)par2 + 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), var18, var22 + 0.0625D);
            }

            if (this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4) == Block.redstoneWire.blockID)
            {
                var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 1), var33, var22);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), var18, var22);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), var18, var20);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 0), var33, var20);
                var5.setColorOpaque_F(var8, var8, var8);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 1), var33, var22 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1), var18, var22 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 0), var18, var20 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 1) - 0.015625D, (double)(par3 + 0), (double)(par4 + 0), var33, var20 + 0.0625D);
            }

            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1) == Block.redstoneWire.blockID)
            {
                var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
                var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + 0.015625D, var33, var22);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, var18, var22);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, var18, var20);
                var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + 0.015625D, var33, var20);
                var5.setColorOpaque_F(var8, var8, var8);
                var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + 0.015625D, var33, var22 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, var18, var22 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)par4 + 0.015625D, var18, var20 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + 0.015625D, var33, var20 + 0.0625D);
            }

            if (this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1) == Block.redstoneWire.blockID)
            {
                var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, var18, var20);
                var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, var33, var20);
                var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, var33, var22);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, var18, var22);
                var5.setColorOpaque_F(var8, var8, var8);
                var5.addVertexWithUV((double)(par2 + 1), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, var18, var20 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, var33, var20 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - 0.015625D, var33, var22 + 0.0625D);
                var5.addVertexWithUV((double)(par2 + 0), (double)((float)(par3 + 1) + 0.021875F), (double)(par4 + 1) - 0.015625D, var18, var22 + 0.0625D);
            }
        }

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, 0.01D);
        }

        return true;
    }

    /**
     * Renders a minecart track block at the given coordinates
     */
    public boolean renderBlockMinecartTrack(BlockRail par1BlockRail, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = par1BlockRail.getBlockTextureFromSideAndMetadata(0, var6);

        if (this.overrideBlockTexture >= 0)
        {
            var7 = this.overrideBlockTexture;
        }

        if (par1BlockRail.isPowered())
        {
            var6 &= 7;
        }

        var5.setBrightness(par1BlockRail.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        int var8 = (var7 & 15) << 4;
        int var9 = var7 & 240;
        double var10 = (double)((float)var8 / 256.0F);
        double var12 = (double)(((float)var8 + 15.99F) / 256.0F);
        double var14 = (double)((float)var9 / 256.0F);
        double var16 = (double)(((float)var9 + 15.99F) / 256.0F);
        double var18 = 0.0625D;
        double var20 = (double)(par2 + 1);
        double var22 = (double)(par2 + 1);
        double var24 = (double)(par2 + 0);
        double var26 = (double)(par2 + 0);
        double var28 = (double)(par4 + 0);
        double var30 = (double)(par4 + 1);
        double var32 = (double)(par4 + 1);
        double var34 = (double)(par4 + 0);
        double var36 = (double)par3 + var18;
        double var38 = (double)par3 + var18;
        double var40 = (double)par3 + var18;
        double var42 = (double)par3 + var18;

        if (var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7)
        {
            if (var6 == 8)
            {
                var20 = var22 = (double)(par2 + 0);
                var24 = var26 = (double)(par2 + 1);
                var28 = var34 = (double)(par4 + 1);
                var30 = var32 = (double)(par4 + 0);
            }
            else if (var6 == 9)
            {
                var20 = var26 = (double)(par2 + 0);
                var22 = var24 = (double)(par2 + 1);
                var28 = var30 = (double)(par4 + 0);
                var32 = var34 = (double)(par4 + 1);
            }
        }
        else
        {
            var20 = var26 = (double)(par2 + 1);
            var22 = var24 = (double)(par2 + 0);
            var28 = var30 = (double)(par4 + 1);
            var32 = var34 = (double)(par4 + 0);
        }

        if (var6 != 2 && var6 != 4)
        {
            if (var6 == 3 || var6 == 5)
            {
                ++var38;
                ++var40;
            }
        }
        else
        {
            ++var36;
            ++var42;
        }

        var5.addVertexWithUV(var20, var36, var28, var12, var14);
        var5.addVertexWithUV(var22, var38, var30, var12, var16);
        var5.addVertexWithUV(var24, var40, var32, var10, var16);
        var5.addVertexWithUV(var26, var42, var34, var10, var14);
        var5.addVertexWithUV(var26, var42, var34, var10, var14);
        var5.addVertexWithUV(var24, var40, var32, var10, var16);
        var5.addVertexWithUV(var22, var38, var30, var12, var16);
        var5.addVertexWithUV(var20, var36, var28, var12, var14);

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, 0.05D);
        }

        return true;
    }

    /**
     * Renders a ladder block at the given coordinates
     */
    public boolean renderBlockLadder(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.getBlockTextureFromSide(0);

        if (this.overrideBlockTexture >= 0)
        {
            var6 = this.overrideBlockTexture;
        }

        int var8;

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0)
        {
            int var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, par2, par3, par4, -1, var6);

            if (var7 >= 0)
            {
                var8 = var7 / 256;
                var5 = Tessellator.instance.getSubTessellator(var8);
                var6 = var7 % 256;
            }
        }

        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var23 = 1.0F;
        var5.setColorOpaque_F(var23, var23, var23);
        var8 = (var6 & 15) << 4;
        int var9 = var6 & 240;
        double var10 = (double)((float)var8 / 256.0F);
        double var12 = (double)(((float)var8 + 15.99F) / 256.0F);
        double var14 = (double)((float)var9 / 256.0F);
        double var16 = (double)(((float)var9 + 15.99F) / 256.0F);
        int var18 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        double var19 = 0.0D;
        double var21 = 0.05000000074505806D;

        if (var18 == 5)
        {
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 1) + var19, (double)(par4 + 1) + var19, var10, var14);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 0) - var19, (double)(par4 + 1) + var19, var10, var16);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 0) - var19, (double)(par4 + 0) - var19, var12, var16);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 1) + var19, (double)(par4 + 0) - var19, var12, var14);
        }

        if (var18 == 4)
        {
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 0) - var19, (double)(par4 + 1) + var19, var12, var16);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 1) + var19, (double)(par4 + 1) + var19, var12, var14);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 1) + var19, (double)(par4 + 0) - var19, var10, var14);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 0) - var19, (double)(par4 + 0) - var19, var10, var16);
        }

        if (var18 == 3)
        {
            var5.addVertexWithUV((double)(par2 + 1) + var19, (double)(par3 + 0) - var19, (double)par4 + var21, var12, var16);
            var5.addVertexWithUV((double)(par2 + 1) + var19, (double)(par3 + 1) + var19, (double)par4 + var21, var12, var14);
            var5.addVertexWithUV((double)(par2 + 0) - var19, (double)(par3 + 1) + var19, (double)par4 + var21, var10, var14);
            var5.addVertexWithUV((double)(par2 + 0) - var19, (double)(par3 + 0) - var19, (double)par4 + var21, var10, var16);
        }

        if (var18 == 2)
        {
            var5.addVertexWithUV((double)(par2 + 1) + var19, (double)(par3 + 1) + var19, (double)(par4 + 1) - var21, var10, var14);
            var5.addVertexWithUV((double)(par2 + 1) + var19, (double)(par3 + 0) - var19, (double)(par4 + 1) - var21, var10, var16);
            var5.addVertexWithUV((double)(par2 + 0) - var19, (double)(par3 + 0) - var19, (double)(par4 + 1) - var21, var12, var16);
            var5.addVertexWithUV((double)(par2 + 0) - var19, (double)(par3 + 1) + var19, (double)(par4 + 1) - var21, var12, var14);
        }

        return true;
    }

    /**
     * Render block vine
     */
    public boolean renderBlockVine(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.getBlockTextureFromSide(0);

        if (this.overrideBlockTexture >= 0)
        {
            var6 = this.overrideBlockTexture;
        }

        int var8;

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0)
        {
            int var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, par2, par3, par4, -1, var6);

            if (var7 >= 0)
            {
                var8 = var7 / 256;
                var5 = Tessellator.instance.getSubTessellator(var8);
                var6 = var7 % 256;
            }
        }

        float var24 = 1.0F;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var8 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
        float var9 = (float)(var8 >> 16 & 255) / 255.0F;
        float var10 = (float)(var8 >> 8 & 255) / 255.0F;
        float var11 = (float)(var8 & 255) / 255.0F;
        var5.setColorOpaque_F(var24 * var9, var24 * var10, var24 * var11);
        var8 = (var6 & 15) << 4;
        int var12 = var6 & 240;
        double var13 = (double)((float)var8 / 256.0F);
        double var15 = (double)(((float)var8 + 15.99F) / 256.0F);
        double var17 = (double)((float)var12 / 256.0F);
        double var19 = (double)(((float)var12 + 15.99F) / 256.0F);
        double var21 = 0.05000000074505806D;
        int var23 = this.blockAccess.getBlockMetadata(par2, par3, par4);

        if ((var23 & 2) != 0)
        {
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 1), (double)(par4 + 1), var13, var17);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 0), (double)(par4 + 1), var13, var19);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 0), (double)(par4 + 0), var15, var19);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 1), (double)(par4 + 0), var15, var17);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 1), (double)(par4 + 0), var15, var17);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 0), (double)(par4 + 0), var15, var19);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 0), (double)(par4 + 1), var13, var19);
            var5.addVertexWithUV((double)par2 + var21, (double)(par3 + 1), (double)(par4 + 1), var13, var17);
        }

        if ((var23 & 8) != 0)
        {
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 0), (double)(par4 + 1), var15, var19);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 1), (double)(par4 + 1), var15, var17);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 1), (double)(par4 + 0), var13, var17);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 0), (double)(par4 + 0), var13, var19);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 0), (double)(par4 + 0), var13, var19);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 1), (double)(par4 + 0), var13, var17);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 1), (double)(par4 + 1), var15, var17);
            var5.addVertexWithUV((double)(par2 + 1) - var21, (double)(par3 + 0), (double)(par4 + 1), var15, var19);
        }

        if ((var23 & 4) != 0)
        {
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + var21, var15, var19);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)par4 + var21, var15, var17);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)par4 + var21, var13, var17);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + var21, var13, var19);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)par4 + var21, var13, var19);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)par4 + var21, var13, var17);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)par4 + var21, var15, var17);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)par4 + var21, var15, var19);
        }

        if ((var23 & 1) != 0)
        {
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1) - var21, var13, var17);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - var21, var13, var19);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - var21, var15, var19);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)(par4 + 1) - var21, var15, var17);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1), (double)(par4 + 1) - var21, var15, var17);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 0), (double)(par4 + 1) - var21, var15, var19);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 0), (double)(par4 + 1) - var21, var13, var19);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1), (double)(par4 + 1) - var21, var13, var17);
        }

        if (this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
        {
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1) - var21, (double)(par4 + 0), var13, var17);
            var5.addVertexWithUV((double)(par2 + 1), (double)(par3 + 1) - var21, (double)(par4 + 1), var13, var19);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1) - var21, (double)(par4 + 1), var15, var19);
            var5.addVertexWithUV((double)(par2 + 0), (double)(par3 + 1) - var21, (double)(par4 + 0), var15, var17);
        }

        return true;
    }

    public boolean renderBlockPane(BlockPane par1BlockPane, int par2, int par3, int par4)
    {
        int var5 = this.blockAccess.getHeight();
        Tessellator var6 = Tessellator.instance;
        boolean var7 = par1BlockPane == Block.thinGlass && ConnectedTextures.isConnectedGlassPanes();
        Tessellator var8 = var6;
        var6.setBrightness(par1BlockPane.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var10 = 1.0F;
        int var11 = par1BlockPane.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var12 = (float)(var11 >> 16 & 255) / 255.0F;
        float var13 = (float)(var11 >> 8 & 255) / 255.0F;
        float var14 = (float)(var11 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var15 = (var12 * 30.0F + var13 * 59.0F + var14 * 11.0F) / 100.0F;
            float var16 = (var12 * 30.0F + var13 * 70.0F) / 100.0F;
            float var17 = (var12 * 30.0F + var14 * 70.0F) / 100.0F;
            var12 = var15;
            var13 = var16;
            var14 = var17;
        }

        var6.setColorOpaque_F(var10 * var12, var10 * var13, var10 * var14);
        boolean var110 = false;
        boolean var112 = false;
        int var18;
        int var111;
        int var114;
        int var113;

        if (this.overrideBlockTexture >= 0)
        {
            var113 = this.overrideBlockTexture;
            var111 = this.overrideBlockTexture;
            var7 = false;
        }
        else
        {
            var114 = this.blockAccess.getBlockMetadata(par2, par3, par4);
            var113 = par1BlockPane.getBlockTextureFromSideAndMetadata(0, var114);
            var111 = par1BlockPane.getSideTextureIndex();

            if (var7)
            {
                var18 = Config.getMinecraft().renderEngine.getTexture("/ctm.png");
                var8 = Tessellator.instance.getSubTessellator(var18);
                var113 = 0;
            }
        }

        var114 = var113;
        var18 = var113;
        int var19 = var113;
        int var20 = this.blockAccess.getBlockId(par2 + 1, par3, par4);
        int var21 = this.blockAccess.getBlockId(par2 - 1, par3, par4);
        int var22 = this.blockAccess.getBlockId(par2, par3, par4 + 1);
        int var23 = this.blockAccess.getBlockId(par2, par3, par4 - 1);
        int var25;
        int var24;

        if (var7)
        {
            var24 = Block.thinGlass.blockID;
            var25 = this.blockAccess.getBlockId(par2, par3 + 1, par4);
            int var26 = this.blockAccess.getBlockId(par2, par3 - 1, par4);
            boolean var27 = var20 == var24;
            boolean var28 = var21 == var24;
            boolean var29 = var25 == var24;
            boolean var30 = var26 == var24;
            boolean var31 = var22 == var24;
            boolean var32 = var23 == var24;
            var113 = this.getGlassPaneTexture(var27, var28, var29, var30);
            var114 = this.getReverseGlassPaneTexture(var113);
            var18 = this.getGlassPaneTexture(var31, var32, var29, var30);
            var19 = this.getReverseGlassPaneTexture(var18);
        }

        var24 = (var113 & 15) << 4;
        var25 = var113 & 240;
        double var116 = (double)((float)var24 / 256.0F);
        double var115 = (double)(((float)var24 + 7.99F) / 256.0F);
        double var117 = (double)(((float)var24 + 15.99F) / 256.0F);
        double var118 = (double)((float)var25 / 256.0F);
        double var34 = (double)(((float)var25 + 15.99F) / 256.0F);
        int var36 = (var114 & 15) << 4;
        int var37 = var114 & 240;
        double var38 = (double)((float)var36 / 256.0F);
        double var40 = (double)(((float)var36 + 7.99F) / 256.0F);
        double var42 = (double)(((float)var36 + 15.99F) / 256.0F);
        double var44 = (double)((float)var37 / 256.0F);
        double var46 = (double)(((float)var37 + 15.99F) / 256.0F);
        int var48 = (var18 & 15) << 4;
        int var49 = var18 & 240;
        double var50 = (double)((float)var48 / 256.0F);
        double var52 = (double)(((float)var48 + 7.99F) / 256.0F);
        double var54 = (double)(((float)var48 + 15.99F) / 256.0F);
        double var56 = (double)((float)var49 / 256.0F);
        double var58 = (double)(((float)var49 + 15.99F) / 256.0F);
        int var60 = (var19 & 15) << 4;
        int var61 = var19 & 240;
        double var62 = (double)((float)var60 / 256.0F);
        double var64 = (double)(((float)var60 + 7.99F) / 256.0F);
        double var66 = (double)(((float)var60 + 15.99F) / 256.0F);
        double var68 = (double)((float)var61 / 256.0F);
        double var70 = (double)(((float)var61 + 15.99F) / 256.0F);
        int var72 = (var111 & 15) << 4;
        int var73 = var111 & 240;
        double var74 = (double)((float)(var72 + 7) / 256.0F);
        double var76 = (double)(((float)var72 + 8.99F) / 256.0F);
        double var78 = (double)((float)var73 / 256.0F);
        double var80 = (double)((float)(var73 + 8) / 256.0F);
        double var82 = (double)(((float)var73 + 15.99F) / 256.0F);
        double var84 = (double)par2;
        double var86 = (double)par2 + 0.5D;
        double var88 = (double)(par2 + 1);
        double var90 = (double)par4;
        double var92 = (double)par4 + 0.5D;
        double var94 = (double)(par4 + 1);
        double var96 = (double)par2 + 0.5D - 0.0625D;
        double var98 = (double)par2 + 0.5D + 0.0625D;
        double var100 = (double)par4 + 0.5D - 0.0625D;
        double var102 = (double)par4 + 0.5D + 0.0625D;
        boolean var104 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 - 1));
        boolean var105 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 + 1));
        boolean var106 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 - 1, par3, par4));
        boolean var107 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 + 1, par3, par4));
        boolean var108 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
        boolean var109 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);

        if ((!var106 || !var107) && (var106 || var107 || var104 || var105))
        {
            if (var106 && !var107)
            {
                var8.addVertexWithUV(var84, (double)(par3 + 1), var92, var116, var118);
                var8.addVertexWithUV(var84, (double)(par3 + 0), var92, var116, var34);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var115, var34);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var115, var118);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var40, var44);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var40, var46);
                var8.addVertexWithUV(var84, (double)(par3 + 0), var92, var42, var46);
                var8.addVertexWithUV(var84, (double)(par3 + 1), var92, var42, var44);

                if (!var105 && !var104)
                {
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var102, var74, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var102, var74, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var100, var76, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var100, var76, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var100, var74, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var100, var74, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var102, var76, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var102, var76, var78);
                }

                if (var108 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))
                {
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                }

                if (var109 || par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))
                {
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var80);
                }
            }
            else if (!var106 && var107)
            {
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var115, var118);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var115, var34);
                var8.addVertexWithUV(var88, (double)(par3 + 0), var92, var117, var34);
                var8.addVertexWithUV(var88, (double)(par3 + 1), var92, var117, var118);
                var8.addVertexWithUV(var88, (double)(par3 + 1), var92, var38, var44);
                var8.addVertexWithUV(var88, (double)(par3 + 0), var92, var38, var46);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var40, var46);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var40, var44);

                if (!var105 && !var104)
                {
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var100, var74, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var100, var74, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var102, var76, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var102, var76, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var102, var74, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var102, var74, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 0), var100, var76, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1), var100, var76, var78);
                }

                if (var108 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))
                {
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var78);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var100, var74, var78);
                }

                if (var109 || par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))
                {
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var78);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var100, var74, var78);
                }
            }
        }
        else
        {
            var8.addVertexWithUV(var84, (double)(par3 + 1), var92, var116, var118);
            var8.addVertexWithUV(var84, (double)(par3 + 0), var92, var116, var34);
            var8.addVertexWithUV(var88, (double)(par3 + 0), var92, var117, var34);
            var8.addVertexWithUV(var88, (double)(par3 + 1), var92, var117, var118);
            var8.addVertexWithUV(var88, (double)(par3 + 1), var92, var38, var44);
            var8.addVertexWithUV(var88, (double)(par3 + 0), var92, var38, var46);
            var8.addVertexWithUV(var84, (double)(par3 + 0), var92, var42, var46);
            var8.addVertexWithUV(var84, (double)(par3 + 1), var92, var42, var44);

            if (var108)
            {
                var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var102, var76, var82);
                var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var102, var76, var78);
                var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var100, var74, var78);
                var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var100, var74, var82);
                var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var102, var76, var82);
                var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var102, var76, var78);
                var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var100, var74, var78);
                var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var100, var74, var82);
            }
            else
            {
                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))
                {
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var84, (double)(par3 + 1) + 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                }

                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))
                {
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var78);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)(par3 + 1) + 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var88, (double)(par3 + 1) + 0.01D, var100, var74, var78);
                }
            }

            if (var109)
            {
                var6.addVertexWithUV(var84, (double)par3 - 0.01D, var102, var76, var82);
                var6.addVertexWithUV(var88, (double)par3 - 0.01D, var102, var76, var78);
                var6.addVertexWithUV(var88, (double)par3 - 0.01D, var100, var74, var78);
                var6.addVertexWithUV(var84, (double)par3 - 0.01D, var100, var74, var82);
                var6.addVertexWithUV(var88, (double)par3 - 0.01D, var102, var76, var82);
                var6.addVertexWithUV(var84, (double)par3 - 0.01D, var102, var76, var78);
                var6.addVertexWithUV(var84, (double)par3 - 0.01D, var100, var74, var78);
                var6.addVertexWithUV(var88, (double)par3 - 0.01D, var100, var74, var82);
            }
            else
            {
                if (par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))
                {
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var102, var76, var82);
                    var6.addVertexWithUV(var84, (double)par3 - 0.01D, var100, var74, var82);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var80);
                }

                if (par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))
                {
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var78);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var102, var76, var78);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var102, var76, var80);
                    var6.addVertexWithUV(var86, (double)par3 - 0.01D, var100, var74, var80);
                    var6.addVertexWithUV(var88, (double)par3 - 0.01D, var100, var74, var78);
                }
            }
        }

        if ((!var104 || !var105) && (var106 || var107 || var104 || var105))
        {
            if (var104 && !var105)
            {
                var8.addVertexWithUV(var86, (double)(par3 + 1), var90, var50, var56);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var90, var50, var58);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var52, var58);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var52, var56);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var64, var68);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var64, var70);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var90, var66, var70);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var90, var66, var68);

                if (!var107 && !var106)
                {
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var74, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 0), var92, var74, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 0), var92, var76, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var76, var78);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var74, var78);
                    var6.addVertexWithUV(var98, (double)(par3 + 0), var92, var74, var82);
                    var6.addVertexWithUV(var96, (double)(par3 + 0), var92, var76, var82);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var76, var78);
                }

                if (var108 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))
                {
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var90, var76, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var76, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var74, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var90, var74, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var76, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var90, var76, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var90, var74, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var74, var78);
                }

                if (var109 || par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))
                {
                    var6.addVertexWithUV(var96, (double)par3, var90, var76, var78);
                    var6.addVertexWithUV(var96, (double)par3, var92, var76, var80);
                    var6.addVertexWithUV(var98, (double)par3, var92, var74, var80);
                    var6.addVertexWithUV(var98, (double)par3, var90, var74, var78);
                    var6.addVertexWithUV(var96, (double)par3, var92, var76, var78);
                    var6.addVertexWithUV(var96, (double)par3, var90, var76, var80);
                    var6.addVertexWithUV(var98, (double)par3, var90, var74, var80);
                    var6.addVertexWithUV(var98, (double)par3, var92, var74, var78);
                }
            }
            else if (!var104 && var105)
            {
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var52, var56);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var52, var58);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var94, var54, var58);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var94, var54, var56);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var94, var62, var68);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var94, var62, var70);
                var8.addVertexWithUV(var86, (double)(par3 + 0), var92, var64, var70);
                var8.addVertexWithUV(var86, (double)(par3 + 1), var92, var64, var68);

                if (!var107 && !var106)
                {
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var74, var78);
                    var6.addVertexWithUV(var98, (double)(par3 + 0), var92, var74, var82);
                    var6.addVertexWithUV(var96, (double)(par3 + 0), var92, var76, var82);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var76, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var74, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 0), var92, var74, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 0), var92, var76, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var76, var78);
                }

                if (var108 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))
                {
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var74, var80);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var94, var74, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var94, var76, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var76, var80);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var94, var74, var80);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var74, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var76, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var94, var76, var80);
                }

                if (var109 || par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))
                {
                    var6.addVertexWithUV(var96, (double)par3, var92, var74, var80);
                    var6.addVertexWithUV(var96, (double)par3, var94, var74, var82);
                    var6.addVertexWithUV(var98, (double)par3, var94, var76, var82);
                    var6.addVertexWithUV(var98, (double)par3, var92, var76, var80);
                    var6.addVertexWithUV(var96, (double)par3, var94, var74, var80);
                    var6.addVertexWithUV(var96, (double)par3, var92, var74, var82);
                    var6.addVertexWithUV(var98, (double)par3, var92, var76, var82);
                    var6.addVertexWithUV(var98, (double)par3, var94, var76, var80);
                }
            }
        }
        else
        {
            var8.addVertexWithUV(var86, (double)(par3 + 1), var94, var62, var68);
            var8.addVertexWithUV(var86, (double)(par3 + 0), var94, var62, var70);
            var8.addVertexWithUV(var86, (double)(par3 + 0), var90, var66, var70);
            var8.addVertexWithUV(var86, (double)(par3 + 1), var90, var66, var68);
            var8.addVertexWithUV(var86, (double)(par3 + 1), var90, var50, var56);
            var8.addVertexWithUV(var86, (double)(par3 + 0), var90, var50, var58);
            var8.addVertexWithUV(var86, (double)(par3 + 0), var94, var54, var58);
            var8.addVertexWithUV(var86, (double)(par3 + 1), var94, var54, var56);

            if (var108)
            {
                var6.addVertexWithUV(var98, (double)(par3 + 1), var94, var76, var82);
                var6.addVertexWithUV(var98, (double)(par3 + 1), var90, var76, var78);
                var6.addVertexWithUV(var96, (double)(par3 + 1), var90, var74, var78);
                var6.addVertexWithUV(var96, (double)(par3 + 1), var94, var74, var82);
                var6.addVertexWithUV(var98, (double)(par3 + 1), var90, var76, var82);
                var6.addVertexWithUV(var98, (double)(par3 + 1), var94, var76, var78);
                var6.addVertexWithUV(var96, (double)(par3 + 1), var94, var74, var78);
                var6.addVertexWithUV(var96, (double)(par3 + 1), var90, var74, var82);
            }
            else
            {
                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))
                {
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var90, var76, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var76, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var74, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var90, var74, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var76, var78);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var90, var76, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var90, var74, var80);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var74, var78);
                }

                if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))
                {
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var74, var80);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var94, var74, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var94, var76, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var76, var80);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var94, var74, var80);
                    var6.addVertexWithUV(var96, (double)(par3 + 1), var92, var74, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var92, var76, var82);
                    var6.addVertexWithUV(var98, (double)(par3 + 1), var94, var76, var80);
                }
            }

            if (var109)
            {
                var6.addVertexWithUV(var98, (double)par3, var94, var76, var82);
                var6.addVertexWithUV(var98, (double)par3, var90, var76, var78);
                var6.addVertexWithUV(var96, (double)par3, var90, var74, var78);
                var6.addVertexWithUV(var96, (double)par3, var94, var74, var82);
                var6.addVertexWithUV(var98, (double)par3, var90, var76, var82);
                var6.addVertexWithUV(var98, (double)par3, var94, var76, var78);
                var6.addVertexWithUV(var96, (double)par3, var94, var74, var78);
                var6.addVertexWithUV(var96, (double)par3, var90, var74, var82);
            }
            else
            {
                if (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))
                {
                    var6.addVertexWithUV(var96, (double)par3, var90, var76, var78);
                    var6.addVertexWithUV(var96, (double)par3, var92, var76, var80);
                    var6.addVertexWithUV(var98, (double)par3, var92, var74, var80);
                    var6.addVertexWithUV(var98, (double)par3, var90, var74, var78);
                    var6.addVertexWithUV(var96, (double)par3, var92, var76, var78);
                    var6.addVertexWithUV(var96, (double)par3, var90, var76, var80);
                    var6.addVertexWithUV(var98, (double)par3, var90, var74, var80);
                    var6.addVertexWithUV(var98, (double)par3, var92, var74, var78);
                }

                if (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))
                {
                    var6.addVertexWithUV(var96, (double)par3, var92, var74, var80);
                    var6.addVertexWithUV(var96, (double)par3, var94, var74, var82);
                    var6.addVertexWithUV(var98, (double)par3, var94, var76, var82);
                    var6.addVertexWithUV(var98, (double)par3, var92, var76, var80);
                    var6.addVertexWithUV(var96, (double)par3, var94, var74, var80);
                    var6.addVertexWithUV(var96, (double)par3, var92, var74, var82);
                    var6.addVertexWithUV(var98, (double)par3, var92, var76, var82);
                    var6.addVertexWithUV(var98, (double)par3, var94, var76, var80);
                }
            }
        }

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        return true;
    }

    /**
     * Renders any block requiring croseed squares such as reeds, flowers, and mushrooms
     */
    public boolean renderCrossedSquares(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;

        if (par1Block == Block.tallGrass)
        {
            long var17 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
            var17 = var17 * var17 * 42317861L + var17 * 11L;
            var19 += ((double)((float)(var17 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            var20 += ((double)((float)(var17 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            var15 += ((double)((float)(var17 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
        }

        this.drawCrossedSquares(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        return true;
    }

    /**
     * Render block stem
     */
    public boolean renderBlockStem(Block par1Block, int par2, int par3, int par4)
    {
        BlockStem var5 = (BlockStem)par1Block;
        Tessellator var6 = Tessellator.instance;
        var6.setBrightness(var5.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var7 = 1.0F;
        int var8 = CustomColorizer.getStemColorMultiplier(var5, this.blockAccess, par2, par3, par4);
        float var9 = (float)(var8 >> 16 & 255) / 255.0F;
        float var10 = (float)(var8 >> 8 & 255) / 255.0F;
        float var11 = (float)(var8 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
            float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
            float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }

        var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
        var5.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        int var15 = var5.getState(this.blockAccess, par2, par3, par4);

        if (var15 < 0)
        {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), this.renderMaxY, (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
        }
        else
        {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), 0.5D, (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
            this.renderBlockStemBig(var5, this.blockAccess.getBlockMetadata(par2, par3, par4), var15, this.renderMaxY, (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
        }

        return true;
    }

    /**
     * Render block crops
     */
    public boolean renderBlockCrops(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        this.renderBlockCropsImpl(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)((float)par3 - 0.0625F), (double)par4);
        return true;
    }

    /**
     * Renders a torch at the given coordinates, with the base slanting at the given delta
     */
    public void renderTorchAtAngle(Block par1Block, double par2, double par4, double par6, double par8, double par10)
    {
        Tessellator var12 = Tessellator.instance;
        int var13 = par1Block.getBlockTextureFromSide(0);

        if (this.overrideBlockTexture >= 0)
        {
            var13 = this.overrideBlockTexture;
        }

        int var14 = (var13 & 15) << 4;
        int var15 = var13 & 240;
        float var16 = (float)var14 / 256.0F;
        float var17 = ((float)var14 + 15.99F) / 256.0F;
        float var18 = (float)var15 / 256.0F;
        float var19 = ((float)var15 + 15.99F) / 256.0F;
        double var20 = (double)var16 + 0.02734375D;
        double var22 = (double)var18 + 0.0234375D;
        double var24 = (double)var16 + 0.03515625D;
        double var26 = (double)var18 + 0.03125D;
        double var28 = (double)var16 + 0.02734375D;
        double var30 = (double)var18 + 0.05078125D;
        double var32 = (double)var16 + 0.03515625D;
        double var34 = (double)var18 + 0.05859375D;
        par2 += 0.5D;
        par6 += 0.5D;
        double var36 = par2 - 0.5D;
        double var38 = par2 + 0.5D;
        double var40 = par6 - 0.5D;
        double var42 = par6 + 0.5D;
        double var44 = 0.0625D;
        double var46 = 0.625D;
        var12.addVertexWithUV(par2 + par8 * (1.0D - var46) - var44, par4 + var46, par6 + par10 * (1.0D - var46) - var44, var20, var22);
        var12.addVertexWithUV(par2 + par8 * (1.0D - var46) - var44, par4 + var46, par6 + par10 * (1.0D - var46) + var44, var20, var26);
        var12.addVertexWithUV(par2 + par8 * (1.0D - var46) + var44, par4 + var46, par6 + par10 * (1.0D - var46) + var44, var24, var26);
        var12.addVertexWithUV(par2 + par8 * (1.0D - var46) + var44, par4 + var46, par6 + par10 * (1.0D - var46) - var44, var24, var22);
        var12.addVertexWithUV(par2 + var44 + par8, par4, par6 - var44 + par10, var32, var30);
        var12.addVertexWithUV(par2 + var44 + par8, par4, par6 + var44 + par10, var32, var34);
        var12.addVertexWithUV(par2 - var44 + par8, par4, par6 + var44 + par10, var28, var34);
        var12.addVertexWithUV(par2 - var44 + par8, par4, par6 - var44 + par10, var28, var30);
        var12.addVertexWithUV(par2 - var44, par4 + 1.0D, var40, (double)var16, (double)var18);
        var12.addVertexWithUV(par2 - var44 + par8, par4 + 0.0D, var40 + par10, (double)var16, (double)var19);
        var12.addVertexWithUV(par2 - var44 + par8, par4 + 0.0D, var42 + par10, (double)var17, (double)var19);
        var12.addVertexWithUV(par2 - var44, par4 + 1.0D, var42, (double)var17, (double)var18);
        var12.addVertexWithUV(par2 + var44, par4 + 1.0D, var42, (double)var16, (double)var18);
        var12.addVertexWithUV(par2 + par8 + var44, par4 + 0.0D, var42 + par10, (double)var16, (double)var19);
        var12.addVertexWithUV(par2 + par8 + var44, par4 + 0.0D, var40 + par10, (double)var17, (double)var19);
        var12.addVertexWithUV(par2 + var44, par4 + 1.0D, var40, (double)var17, (double)var18);
        var12.addVertexWithUV(var36, par4 + 1.0D, par6 + var44, (double)var16, (double)var18);
        var12.addVertexWithUV(var36 + par8, par4 + 0.0D, par6 + var44 + par10, (double)var16, (double)var19);
        var12.addVertexWithUV(var38 + par8, par4 + 0.0D, par6 + var44 + par10, (double)var17, (double)var19);
        var12.addVertexWithUV(var38, par4 + 1.0D, par6 + var44, (double)var17, (double)var18);
        var12.addVertexWithUV(var38, par4 + 1.0D, par6 - var44, (double)var16, (double)var18);
        var12.addVertexWithUV(var38 + par8, par4 + 0.0D, par6 - var44 + par10, (double)var16, (double)var19);
        var12.addVertexWithUV(var36 + par8, par4 + 0.0D, par6 - var44 + par10, (double)var17, (double)var19);
        var12.addVertexWithUV(var36, par4 + 1.0D, par6 - var44, (double)var17, (double)var18);
    }

    /**
     * Utility function to draw crossed swuares
     */
    public void drawCrossedSquares(Block par1Block, int par2, double par3, double par5, double par7, float par9)
    {
        Tessellator var10 = Tessellator.instance;
        int var11 = par1Block.getBlockTextureFromSideAndMetadata(0, par2);

        if (this.overrideBlockTexture >= 0)
        {
            var11 = this.overrideBlockTexture;
        }

        int var12;
        int var13;

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0)
        {
            var12 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par3, (int)par5, (int)par7, -1, var11);

            if (var12 >= 0)
            {
                var13 = var12 / 256;
                var10 = Tessellator.instance.getSubTessellator(var13);
                var11 = var12 % 256;
            }
        }

        var12 = (var11 & 15) << 4;
        var13 = var11 & 240;
        double var14 = (double)((float)var12 / 256.0F);
        double var16 = (double)(((float)var12 + 15.99F) / 256.0F);
        double var18 = (double)((float)var13 / 256.0F);
        double var20 = (double)(((float)var13 + 15.99F) / 256.0F);
        double var22 = 0.45D * (double)par9;
        double var24 = par3 + 0.5D - var22;
        double var26 = par3 + 0.5D + var22;
        double var28 = par7 + 0.5D - var22;
        double var30 = par7 + 0.5D + var22;
        var10.addVertexWithUV(var24, par5 + (double)par9, var28, var14, var18);
        var10.addVertexWithUV(var24, par5 + 0.0D, var28, var14, var20);
        var10.addVertexWithUV(var26, par5 + 0.0D, var30, var16, var20);
        var10.addVertexWithUV(var26, par5 + (double)par9, var30, var16, var18);
        var10.addVertexWithUV(var26, par5 + (double)par9, var30, var14, var18);
        var10.addVertexWithUV(var26, par5 + 0.0D, var30, var14, var20);
        var10.addVertexWithUV(var24, par5 + 0.0D, var28, var16, var20);
        var10.addVertexWithUV(var24, par5 + (double)par9, var28, var16, var18);
        var10.addVertexWithUV(var24, par5 + (double)par9, var30, var14, var18);
        var10.addVertexWithUV(var24, par5 + 0.0D, var30, var14, var20);
        var10.addVertexWithUV(var26, par5 + 0.0D, var28, var16, var20);
        var10.addVertexWithUV(var26, par5 + (double)par9, var28, var16, var18);
        var10.addVertexWithUV(var26, par5 + (double)par9, var28, var14, var18);
        var10.addVertexWithUV(var26, par5 + 0.0D, var28, var14, var20);
        var10.addVertexWithUV(var24, par5 + 0.0D, var30, var16, var20);
        var10.addVertexWithUV(var24, par5 + (double)par9, var30, var16, var18);
    }

    /**
     * Render block stem small
     */
    public void renderBlockStemSmall(Block par1Block, int par2, double par3, double par5, double par7, double par9)
    {
        Tessellator var11 = Tessellator.instance;
        int var12 = par1Block.getBlockTextureFromSideAndMetadata(0, par2);

        if (this.overrideBlockTexture >= 0)
        {
            var12 = this.overrideBlockTexture;
        }

        int var13 = (var12 & 15) << 4;
        int var14 = var12 & 240;
        double var15 = (double)((float)var13 / 256.0F);
        double var17 = (double)(((float)var13 + 15.99F) / 256.0F);
        double var19 = (double)((float)var14 / 256.0F);
        double var21 = ((double)var14 + 15.989999771118164D * par3) / 256.0D;
        double var23 = par5 + 0.5D - 0.44999998807907104D;
        double var25 = par5 + 0.5D + 0.44999998807907104D;
        double var27 = par9 + 0.5D - 0.44999998807907104D;
        double var29 = par9 + 0.5D + 0.44999998807907104D;
        var11.addVertexWithUV(var23, par7 + par3, var27, var15, var19);
        var11.addVertexWithUV(var23, par7 + 0.0D, var27, var15, var21);
        var11.addVertexWithUV(var25, par7 + 0.0D, var29, var17, var21);
        var11.addVertexWithUV(var25, par7 + par3, var29, var17, var19);
        var11.addVertexWithUV(var25, par7 + par3, var29, var15, var19);
        var11.addVertexWithUV(var25, par7 + 0.0D, var29, var15, var21);
        var11.addVertexWithUV(var23, par7 + 0.0D, var27, var17, var21);
        var11.addVertexWithUV(var23, par7 + par3, var27, var17, var19);
        var11.addVertexWithUV(var23, par7 + par3, var29, var15, var19);
        var11.addVertexWithUV(var23, par7 + 0.0D, var29, var15, var21);
        var11.addVertexWithUV(var25, par7 + 0.0D, var27, var17, var21);
        var11.addVertexWithUV(var25, par7 + par3, var27, var17, var19);
        var11.addVertexWithUV(var25, par7 + par3, var27, var15, var19);
        var11.addVertexWithUV(var25, par7 + 0.0D, var27, var15, var21);
        var11.addVertexWithUV(var23, par7 + 0.0D, var29, var17, var21);
        var11.addVertexWithUV(var23, par7 + par3, var29, var17, var19);
    }

    /**
     * Render BlockLilyPad
     */
    public boolean renderBlockLilyPad(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = par1Block.blockIndexInTexture;

        if (this.overrideBlockTexture >= 0)
        {
            var6 = this.overrideBlockTexture;
        }

        int var7;
        int var8;

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0)
        {
            var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, par2, par3, par4, -1, var6);

            if (var7 >= 0)
            {
                var8 = var7 / 256;
                var5 = Tessellator.instance.getSubTessellator(var8);
                var6 = var7 % 256;
            }
        }

        var7 = (var6 & 15) << 4;
        var8 = var6 & 240;
        float var9 = 0.015625F;
        double var10 = (double)((float)var7 / 256.0F);
        double var12 = (double)(((float)var7 + 15.99F) / 256.0F);
        double var14 = (double)((float)var8 / 256.0F);
        double var16 = (double)(((float)var8 + 15.99F) / 256.0F);
        long var18 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
        var18 = var18 * var18 * 42317861L + var18 * 11L;
        int var20 = (int)(var18 >> 16 & 3L);
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        float var21 = (float)par2 + 0.5F;
        float var22 = (float)par4 + 0.5F;
        float var23 = (float)(var20 & 1) * 0.5F * (float)(1 - var20 / 2 % 2 * 2);
        float var24 = (float)(var20 + 1 & 1) * 0.5F * (float)(1 - (var20 + 1) / 2 % 2 * 2);
        int var25 = CustomColorizer.getLilypadColor();
        var5.setColorOpaque_I(var25);
        var5.addVertexWithUV((double)(var21 + var23 - var24), (double)((float)par3 + var9), (double)(var22 + var23 + var24), var10, var14);
        var5.addVertexWithUV((double)(var21 + var23 + var24), (double)((float)par3 + var9), (double)(var22 - var23 + var24), var12, var14);
        var5.addVertexWithUV((double)(var21 - var23 + var24), (double)((float)par3 + var9), (double)(var22 - var23 - var24), var12, var16);
        var5.addVertexWithUV((double)(var21 - var23 - var24), (double)((float)par3 + var9), (double)(var22 + var23 - var24), var10, var16);
        var5.setColorOpaque_I((var25 & 16711422) >> 1);
        var5.addVertexWithUV((double)(var21 - var23 - var24), (double)((float)par3 + var9), (double)(var22 + var23 - var24), var10, var16);
        var5.addVertexWithUV((double)(var21 - var23 + var24), (double)((float)par3 + var9), (double)(var22 - var23 - var24), var12, var16);
        var5.addVertexWithUV((double)(var21 + var23 + var24), (double)((float)par3 + var9), (double)(var22 - var23 + var24), var12, var14);
        var5.addVertexWithUV((double)(var21 + var23 - var24), (double)((float)par3 + var9), (double)(var22 + var23 + var24), var10, var14);
        return true;
    }

    /**
     * Render block stem big
     */
    public void renderBlockStemBig(Block par1Block, int par2, int par3, double par4, double par6, double par8, double par10)
    {
        Tessellator var12 = Tessellator.instance;
        int var13 = par1Block.getBlockTextureFromSideAndMetadata(0, par2) + 16;

        if (this.overrideBlockTexture >= 0)
        {
            var13 = this.overrideBlockTexture;
        }

        int var14 = (var13 & 15) << 4;
        int var15 = var13 & 240;
        double var16 = (double)((float)var14 / 256.0F);
        double var18 = (double)(((float)var14 + 15.99F) / 256.0F);
        double var20 = (double)((float)var15 / 256.0F);
        double var22 = ((double)var15 + 15.989999771118164D * par4) / 256.0D;
        double var24 = par6 + 0.5D - 0.5D;
        double var26 = par6 + 0.5D + 0.5D;
        double var28 = par10 + 0.5D - 0.5D;
        double var30 = par10 + 0.5D + 0.5D;
        double var32 = par6 + 0.5D;
        double var34 = par10 + 0.5D;

        if ((par3 + 1) / 2 % 2 == 1)
        {
            double var36 = var18;
            var18 = var16;
            var16 = var36;
        }

        if (par3 < 2)
        {
            var12.addVertexWithUV(var24, par8 + par4, var34, var16, var20);
            var12.addVertexWithUV(var24, par8 + 0.0D, var34, var16, var22);
            var12.addVertexWithUV(var26, par8 + 0.0D, var34, var18, var22);
            var12.addVertexWithUV(var26, par8 + par4, var34, var18, var20);
            var12.addVertexWithUV(var26, par8 + par4, var34, var18, var20);
            var12.addVertexWithUV(var26, par8 + 0.0D, var34, var18, var22);
            var12.addVertexWithUV(var24, par8 + 0.0D, var34, var16, var22);
            var12.addVertexWithUV(var24, par8 + par4, var34, var16, var20);
        }
        else
        {
            var12.addVertexWithUV(var32, par8 + par4, var30, var16, var20);
            var12.addVertexWithUV(var32, par8 + 0.0D, var30, var16, var22);
            var12.addVertexWithUV(var32, par8 + 0.0D, var28, var18, var22);
            var12.addVertexWithUV(var32, par8 + par4, var28, var18, var20);
            var12.addVertexWithUV(var32, par8 + par4, var28, var18, var20);
            var12.addVertexWithUV(var32, par8 + 0.0D, var28, var18, var22);
            var12.addVertexWithUV(var32, par8 + 0.0D, var30, var16, var22);
            var12.addVertexWithUV(var32, par8 + par4, var30, var16, var20);
        }
    }

    /**
     * Render block crops implementation
     */
    public void renderBlockCropsImpl(Block par1Block, int par2, double par3, double par5, double par7)
    {
        Tessellator var9 = Tessellator.instance;
        int var10 = par1Block.getBlockTextureFromSideAndMetadata(0, par2);

        if (this.overrideBlockTexture >= 0)
        {
            var10 = this.overrideBlockTexture;
        }

        int var11 = (var10 & 15) << 4;
        int var12 = var10 & 240;
        double var13 = (double)((float)var11 / 256.0F);
        double var15 = (double)(((float)var11 + 15.99F) / 256.0F);
        double var17 = (double)((float)var12 / 256.0F);
        double var19 = (double)(((float)var12 + 15.99F) / 256.0F);
        double var21 = par3 + 0.5D - 0.25D;
        double var23 = par3 + 0.5D + 0.25D;
        double var25 = par7 + 0.5D - 0.5D;
        double var27 = par7 + 0.5D + 0.5D;
        var9.addVertexWithUV(var21, par5 + 1.0D, var25, var13, var17);
        var9.addVertexWithUV(var21, par5 + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var21, par5 + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var21, par5 + 1.0D, var27, var15, var17);
        var9.addVertexWithUV(var21, par5 + 1.0D, var27, var13, var17);
        var9.addVertexWithUV(var21, par5 + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var21, par5 + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var21, par5 + 1.0D, var25, var15, var17);
        var9.addVertexWithUV(var23, par5 + 1.0D, var27, var13, var17);
        var9.addVertexWithUV(var23, par5 + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var23, par5 + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var23, par5 + 1.0D, var25, var15, var17);
        var9.addVertexWithUV(var23, par5 + 1.0D, var25, var13, var17);
        var9.addVertexWithUV(var23, par5 + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var23, par5 + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var23, par5 + 1.0D, var27, var15, var17);
        var21 = par3 + 0.5D - 0.5D;
        var23 = par3 + 0.5D + 0.5D;
        var25 = par7 + 0.5D - 0.25D;
        var27 = par7 + 0.5D + 0.25D;
        var9.addVertexWithUV(var21, par5 + 1.0D, var25, var13, var17);
        var9.addVertexWithUV(var21, par5 + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var23, par5 + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var23, par5 + 1.0D, var25, var15, var17);
        var9.addVertexWithUV(var23, par5 + 1.0D, var25, var13, var17);
        var9.addVertexWithUV(var23, par5 + 0.0D, var25, var13, var19);
        var9.addVertexWithUV(var21, par5 + 0.0D, var25, var15, var19);
        var9.addVertexWithUV(var21, par5 + 1.0D, var25, var15, var17);
        var9.addVertexWithUV(var23, par5 + 1.0D, var27, var13, var17);
        var9.addVertexWithUV(var23, par5 + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var21, par5 + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var21, par5 + 1.0D, var27, var15, var17);
        var9.addVertexWithUV(var21, par5 + 1.0D, var27, var13, var17);
        var9.addVertexWithUV(var21, par5 + 0.0D, var27, var13, var19);
        var9.addVertexWithUV(var23, par5 + 0.0D, var27, var15, var19);
        var9.addVertexWithUV(var23, par5 + 1.0D, var27, var15, var17);
    }

    /**
     * Renders a block based on the BlockFluids class at the given coordinates
     */
    public boolean renderBlockFluids(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        int var6 = CustomColorizer.getFluidColor(par1Block, this.blockAccess, par2, par3, par4);
        float var7 = (float)(var6 >> 16 & 255) / 255.0F;
        float var8 = (float)(var6 >> 8 & 255) / 255.0F;
        float var9 = (float)(var6 & 255) / 255.0F;
        boolean var10 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
        boolean var11 = par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);
        boolean[] var12 = new boolean[] {par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2), par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3), par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4), par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5)};

        if (!var10 && !var11 && !var12[0] && !var12[1] && !var12[2] && !var12[3])
        {
            return false;
        }
        else
        {
            boolean var13 = false;
            float var14 = 0.5F;
            float var15 = 1.0F;
            float var16 = 0.8F;
            float var17 = 0.6F;
            double var18 = 0.0D;
            double var20 = 1.0D;
            Material var22 = par1Block.blockMaterial;
            int var23 = this.blockAccess.getBlockMetadata(par2, par3, par4);
            double var24 = (double)this.getFluidHeight(par2, par3, par4, var22);
            double var26 = (double)this.getFluidHeight(par2, par3, par4 + 1, var22);
            double var28 = (double)this.getFluidHeight(par2 + 1, par3, par4 + 1, var22);
            double var30 = (double)this.getFluidHeight(par2 + 1, par3, par4, var22);
            double var32 = 0.0010000000474974513D;
            int var34;
            int var35;
            float var36;
            int var37;
            double var42;
            double var40;
            double var44;

            if (this.renderAllFaces || var10)
            {
                var13 = true;
                var34 = par1Block.getBlockTextureFromSideAndMetadata(1, var23);
                var36 = (float)BlockFluid.getFlowDirection(this.blockAccess, par2, par3, par4, var22);

                if (var36 > -999.0F)
                {
                    var34 = par1Block.getBlockTextureFromSideAndMetadata(2, var23);
                }

                var24 -= var32;
                var26 -= var32;
                var28 -= var32;
                var30 -= var32;
                var37 = (var34 & 15) << 4;
                var35 = var34 & 240;
                double var38 = ((double)var37 + 8.0D) / 256.0D;
                var40 = ((double)var35 + 8.0D) / 256.0D;

                if (var36 < -999.0F)
                {
                    var36 = 0.0F;
                }
                else
                {
                    var38 = (double)((float)(var37 + 16) / 256.0F);
                    var40 = (double)((float)(var35 + 16) / 256.0F);
                }

                var42 = (double)(MathHelper.sin(var36) * 8.0F) / 256.0D;
                var44 = (double)(MathHelper.cos(var36) * 8.0F) / 256.0D;
                var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
                float var46 = 1.0F;
                var5.setColorOpaque_F(var15 * var46 * var7, var15 * var46 * var8, var15 * var46 * var9);
                double var47 = 3.90625E-5D;
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var24, (double)(par4 + 0), var38 - var44 - var42 + var47, var40 - var44 + var42 + var47);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var26, (double)(par4 + 1), var38 - var44 + var42 + var47, var40 + var44 + var42 - var47);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var28, (double)(par4 + 1), var38 + var44 + var42 - var47, var40 + var44 - var42 - var47);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var30, (double)(par4 + 0), var38 + var44 - var42 - var47, var40 - var44 - var42 + var47);
            }

            if (this.renderAllFaces || var11)
            {
                var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
                var36 = 1.0F;
                var5.setColorOpaque_F(var14 * var36 * var7, var14 * var36 * var8, var14 * var36 * var9);
                this.renderBottomFace(par1Block, (double)par2, (double)par3 + var32, (double)par4, par1Block.getBlockTextureFromSide(0));
                var13 = true;
            }

            for (var34 = 0; var34 < 4; ++var34)
            {
                int var64 = par2;
                var35 = par4;

                if (var34 == 0)
                {
                    var35 = par4 - 1;
                }

                if (var34 == 1)
                {
                    ++var35;
                }

                if (var34 == 2)
                {
                    var64 = par2 - 1;
                }

                if (var34 == 3)
                {
                    ++var64;
                }

                var37 = par1Block.getBlockTextureFromSideAndMetadata(var34 + 2, var23);
                int var63 = (var37 & 15) << 4;
                int var39 = var37 & 240;

                if (this.renderAllFaces || var12[var34])
                {
                    double var65;
                    double var50;
                    double var48;

                    if (var34 == 0)
                    {
                        var42 = var24;
                        var40 = var30;
                        var65 = (double)par2;
                        var50 = (double)(par2 + 1);
                        var44 = (double)par4 + var32;
                        var48 = (double)par4 + var32;
                    }
                    else if (var34 == 1)
                    {
                        var42 = var28;
                        var40 = var26;
                        var65 = (double)(par2 + 1);
                        var50 = (double)par2;
                        var44 = (double)(par4 + 1) - var32;
                        var48 = (double)(par4 + 1) - var32;
                    }
                    else if (var34 == 2)
                    {
                        var42 = var26;
                        var40 = var24;
                        var65 = (double)par2 + var32;
                        var50 = (double)par2 + var32;
                        var44 = (double)(par4 + 1);
                        var48 = (double)par4;
                    }
                    else
                    {
                        var42 = var30;
                        var40 = var28;
                        var65 = (double)(par2 + 1) - var32;
                        var50 = (double)(par2 + 1) - var32;
                        var44 = (double)par4;
                        var48 = (double)(par4 + 1);
                    }

                    var13 = true;
                    double var52 = (double)((float)(var63 + 0) / 256.0F);
                    double var54 = ((double)(var63 + 16) - 0.01D) / 256.0D;
                    double var56 = ((double)var39 + (1.0D - var42) * 16.0D) / 256.0D;
                    double var58 = ((double)var39 + (1.0D - var40) * 16.0D) / 256.0D;
                    double var60 = ((double)(var39 + 16) - 0.01D) / 256.0D;
                    var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, var64, par3, var35));
                    float var62 = 1.0F;

                    if (var34 < 2)
                    {
                        var62 *= var16;
                    }
                    else
                    {
                        var62 *= var17;
                    }

                    var5.setColorOpaque_F(var15 * var62 * var7, var15 * var62 * var8, var15 * var62 * var9);
                    var5.addVertexWithUV(var65, (double)par3 + var42, var44, var52, var56);
                    var5.addVertexWithUV(var50, (double)par3 + var40, var48, var54, var58);
                    var5.addVertexWithUV(var50, (double)(par3 + 0), var48, var54, var60);
                    var5.addVertexWithUV(var65, (double)(par3 + 0), var44, var52, var60);
                }
            }

            this.renderMinY = var18;
            this.renderMaxY = var20;
            return var13;
        }
    }

    /**
     * Get fluid height
     */
    public float getFluidHeight(int par1, int par2, int par3, Material par4Material)
    {
        int var5 = 0;
        float var6 = 0.0F;

        for (int var7 = 0; var7 < 4; ++var7)
        {
            int var8 = par1 - (var7 & 1);
            int var9 = par3 - (var7 >> 1 & 1);

            if (this.blockAccess.getBlockMaterial(var8, par2 + 1, var9) == par4Material)
            {
                return 1.0F;
            }

            Material var10 = this.blockAccess.getBlockMaterial(var8, par2, var9);

            if (var10 == par4Material)
            {
                int var11 = this.blockAccess.getBlockMetadata(var8, par2, var9);

                if (var11 >= 8 || var11 == 0)
                {
                    var6 += BlockFluid.getFluidHeightPercent(var11) * 10.0F;
                    var5 += 10;
                }

                var6 += BlockFluid.getFluidHeightPercent(var11);
                ++var5;
            }
            else if (!var10.isSolid())
            {
                ++var6;
                ++var5;
            }
        }

        return 1.0F - var6 / (float)var5;
    }

    /**
     * Renders a falling sand block
     */
    public void renderBlockSandFalling(Block par1Block, World par2World, int par3, int par4, int par5, int par6)
    {
        float var7 = 0.5F;
        float var8 = 1.0F;
        float var9 = 0.8F;
        float var10 = 0.6F;
        Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.setBrightness(par1Block.getMixedBrightnessForBlock(par2World, par3, par4, par5));
        float var12 = 1.0F;
        float var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var7 * var13, var7 * var13, var7 * var13);
        this.renderBottomFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSideAndMetadata(0, par6));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
        this.renderTopFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSideAndMetadata(1, par6));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.renderEastFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSideAndMetadata(2, par6));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.renderWestFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSideAndMetadata(3, par6));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        this.renderNorthFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSideAndMetadata(4, par6));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        this.renderSouthFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSideAndMetadata(5, par6));
        var11.draw();
    }

    /**
     * Renders a standard cube block at the given coordinates
     */
    public boolean renderStandardBlock(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
        float var6 = (float)(var5 >> 16 & 255) / 255.0F;
        float var7 = (float)(var5 >> 8 & 255) / 255.0F;
        float var8 = (float)(var5 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
            float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
            float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }

        return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? this.renderStandardBlockWithAmbientOcclusion(par1Block, par2, par3, par4, var6, var7, var8) : this.renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4, var6, var7, var8);
    }

    /**
     * Renders a log block at the given coordinates
     */
    public boolean renderBlockLog(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var6 = var5 & 12;

        if (var6 == 4)
        {
            this.uvRotateEast = 1;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 1;
        }
        else if (var6 == 8)
        {
            this.uvRotateSouth = 1;
            this.uvRotateNorth = 1;
        }

        boolean var7 = this.renderStandardBlock(par1Block, par2, par3, par4);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var7;
    }

    public boolean renderStandardBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
    {
        this.enableAO = true;
        boolean var8 = Tessellator.instance.defaultTexture;
        boolean var9 = Config.isBetterGrass() && var8;

        if (par1Block.getClass() == BlockGlass.class)
        {
            this.aoType = 0;
        }
        else
        {
            this.aoType = 1;
        }

        boolean var10 = false;
        boolean var11 = true;
        boolean var12 = true;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        this.aoLightValuesCalculated = false;
        int var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        Tessellator var18 = Tessellator.instance;
        var18.setBrightness(983055);

        if (par1Block.blockIndexInTexture == 3)
        {
            var16 = false;
            var15 = false;
            var14 = false;
            var13 = false;
            var11 = false;
        }

        if (this.overrideBlockTexture >= 0)
        {
            var16 = false;
            var15 = false;
            var14 = false;
            var13 = false;
            var11 = false;
        }

        int var19;
        float var21;
        float var20;
        float var23;
        float var22;

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
        {
            if (!this.aoLightValuesCalculated)
            {
                this.calculateAoLightValues(par1Block, par2, par3, par4);
            }

            var19 = var17;

            if (this.renderMinY <= 0.0D)
            {
                var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
            }

            if (this.aoType > 0)
            {
                if (this.renderMinY <= 0.0D)
                {
                    --par3;
                }

                this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
                this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
                this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
                this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
                this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
                this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
                this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
                this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);

                if (!this.aoGrassXYZCNN && !this.aoGrassXYZNNC)
                {
                    this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                    this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
                }
                else
                {
                    this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
                    this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
                }

                if (!this.aoGrassXYZCNP && !this.aoGrassXYZNNC)
                {
                    this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                    this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
                }
                else
                {
                    this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
                    this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
                }

                if (!this.aoGrassXYZCNN && !this.aoGrassXYZPNC)
                {
                    this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                    this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
                }
                else
                {
                    this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
                    this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
                }

                if (!this.aoGrassXYZCNP && !this.aoGrassXYZPNC)
                {
                    this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                    this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
                }
                else
                {
                    this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
                    this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
                }

                if (this.renderMinY <= 0.0D)
                {
                    ++par3;
                }

                var20 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + this.aoLightValueYNeg) / 4.0F;
                var23 = (this.aoLightValueScratchYZNP + this.aoLightValueYNeg + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
                var22 = (this.aoLightValueYNeg + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
                var21 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + this.aoLightValueYNeg + this.aoLightValueScratchYZNN) / 4.0F;
                this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var19);
                this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var19);
                this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var19);
                this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var19);
            }
            else
            {
                var23 = this.aoLightValueYNeg;
                var22 = this.aoLightValueYNeg;
                var21 = this.aoLightValueYNeg;
                var20 = this.aoLightValueYNeg;
                this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var19;
            }

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var11 ? par5 : 1.0F) * 0.5F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var11 ? par6 : 1.0F) * 0.5F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var11 ? par7 : 1.0F) * 0.5F;
            this.colorRedTopLeft *= var20;
            this.colorGreenTopLeft *= var20;
            this.colorBlueTopLeft *= var20;
            this.colorRedBottomLeft *= var21;
            this.colorGreenBottomLeft *= var21;
            this.colorBlueBottomLeft *= var21;
            this.colorRedBottomRight *= var22;
            this.colorGreenBottomRight *= var22;
            this.colorBlueBottomRight *= var22;
            this.colorRedTopRight *= var23;
            this.colorGreenTopRight *= var23;
            this.colorBlueTopRight *= var23;
            this.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 0));
            var10 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
        {
            if (!this.aoLightValuesCalculated)
            {
                this.calculateAoLightValues(par1Block, par2, par3, par4);
            }

            var19 = var17;

            if (this.renderMaxY >= 1.0D)
            {
                var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
            }

            if (this.aoType > 0)
            {
                if (this.renderMaxY >= 1.0D)
                {
                    ++par3;
                }

                this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
                this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
                this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
                this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
                this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
                this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
                this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
                this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);

                if (!this.aoGrassXYZCPN && !this.aoGrassXYZNPC)
                {
                    this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                    this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
                }
                else
                {
                    this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
                    this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
                }

                if (!this.aoGrassXYZCPN && !this.aoGrassXYZPPC)
                {
                    this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                    this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
                }
                else
                {
                    this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
                    this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
                }

                if (!this.aoGrassXYZCPP && !this.aoGrassXYZNPC)
                {
                    this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                    this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
                }
                else
                {
                    this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
                    this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
                }

                if (!this.aoGrassXYZCPP && !this.aoGrassXYZPPC)
                {
                    this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                    this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
                }
                else
                {
                    this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
                    this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
                }

                if (this.renderMaxY >= 1.0D)
                {
                    --par3;
                }

                var23 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + this.aoLightValueYPos) / 4.0F;
                var20 = (this.aoLightValueScratchYZPP + this.aoLightValueYPos + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
                var21 = (this.aoLightValueYPos + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
                var22 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + this.aoLightValueYPos + this.aoLightValueScratchYZPN) / 4.0F;
                this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var19);
                this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var19);
                this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var19);
                this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var19);
            }
            else
            {
                var23 = this.aoLightValueYPos;
                var22 = this.aoLightValueYPos;
                var21 = this.aoLightValueYPos;
                var20 = this.aoLightValueYPos;
                this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var19;
            }

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = var12 ? par5 : 1.0F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = var12 ? par6 : 1.0F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = var12 ? par7 : 1.0F;
            this.colorRedTopLeft *= var20;
            this.colorGreenTopLeft *= var20;
            this.colorBlueTopLeft *= var20;
            this.colorRedBottomLeft *= var21;
            this.colorGreenBottomLeft *= var21;
            this.colorBlueBottomLeft *= var21;
            this.colorRedBottomRight *= var22;
            this.colorGreenBottomRight *= var22;
            this.colorBlueBottomRight *= var22;
            this.colorRedTopRight *= var23;
            this.colorGreenTopRight *= var23;
            this.colorBlueTopRight *= var23;
            this.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 1));
            var10 = true;
        }

        int var24;

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
        {
            if (!this.aoLightValuesCalculated)
            {
                this.calculateAoLightValues(par1Block, par2, par3, par4);
            }

            var19 = var17;

            if (this.renderMinZ <= 0.0D)
            {
                var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
            }

            if (this.aoType > 0)
            {
                if (this.renderMinZ <= 0.0D)
                {
                    --par4;
                }

                this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
                this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
                this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
                this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
                this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
                this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
                this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
                this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);

                if (!this.aoGrassXYZNCN && !this.aoGrassXYZCNN)
                {
                    this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                    this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
                }
                else
                {
                    this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
                    this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
                }

                if (!this.aoGrassXYZNCN && !this.aoGrassXYZCPN)
                {
                    this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                    this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
                }
                else
                {
                    this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
                    this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
                }

                if (!this.aoGrassXYZPCN && !this.aoGrassXYZCNN)
                {
                    this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                    this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
                }
                else
                {
                    this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
                    this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
                }

                if (!this.aoGrassXYZPCN && !this.aoGrassXYZCPN)
                {
                    this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                    this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
                }
                else
                {
                    this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
                    this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
                }

                if (this.renderMinZ <= 0.0D)
                {
                    ++par4;
                }

                var20 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + this.aoLightValueZNeg + this.aoLightValueScratchYZPN) / 4.0F;
                var21 = (this.aoLightValueZNeg + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
                var22 = (this.aoLightValueScratchYZNN + this.aoLightValueZNeg + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
                var23 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + this.aoLightValueZNeg) / 4.0F;
                this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var19);
                this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var19);
                this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var19);
                this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var19);
            }
            else
            {
                var23 = this.aoLightValueZNeg;
                var22 = this.aoLightValueZNeg;
                var21 = this.aoLightValueZNeg;
                var20 = this.aoLightValueZNeg;
                this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var19;
            }

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var13 ? par5 : 1.0F) * 0.8F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var13 ? par6 : 1.0F) * 0.8F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var13 ? par7 : 1.0F) * 0.8F;
            this.colorRedTopLeft *= var20;
            this.colorGreenTopLeft *= var20;
            this.colorBlueTopLeft *= var20;
            this.colorRedBottomLeft *= var21;
            this.colorGreenBottomLeft *= var21;
            this.colorBlueBottomLeft *= var21;
            this.colorRedBottomRight *= var22;
            this.colorGreenBottomRight *= var22;
            this.colorBlueBottomRight *= var22;
            this.colorRedTopRight *= var23;
            this.colorGreenTopRight *= var23;
            this.colorBlueTopRight *= var23;
            var24 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 2);

            if (var9)
            {
                var24 = this.fixAoSideGrassTexture(var24, par2, par3, par4, 2, par5, par6, par7);
            }

            this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var24);

            if (var8 && fancyGrass && var24 == 3 && this.overrideBlockTexture < 0)
            {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var10 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
        {
            if (!this.aoLightValuesCalculated)
            {
                this.calculateAoLightValues(par1Block, par2, par3, par4);
            }

            var19 = var17;

            if (this.renderMaxZ >= 1.0D)
            {
                var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
            }

            if (this.aoType > 0)
            {
                if (this.renderMaxZ >= 1.0D)
                {
                    ++par4;
                }

                this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
                this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
                this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
                this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
                this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
                this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
                this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
                this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);

                if (!this.aoGrassXYZNCP && !this.aoGrassXYZCNP)
                {
                    this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                    this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
                }
                else
                {
                    this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
                    this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
                }

                if (!this.aoGrassXYZNCP && !this.aoGrassXYZCPP)
                {
                    this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                    this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
                }
                else
                {
                    this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
                    this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
                }

                if (!this.aoGrassXYZPCP && !this.aoGrassXYZCNP)
                {
                    this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                    this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
                }
                else
                {
                    this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
                    this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
                }

                if (!this.aoGrassXYZPCP && !this.aoGrassXYZCPP)
                {
                    this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                    this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
                }
                else
                {
                    this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
                    this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
                }

                if (this.renderMaxZ >= 1.0D)
                {
                    --par4;
                }

                var20 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + this.aoLightValueZPos + this.aoLightValueScratchYZPP) / 4.0F;
                var23 = (this.aoLightValueZPos + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
                var22 = (this.aoLightValueScratchYZNP + this.aoLightValueZPos + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
                var21 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + this.aoLightValueZPos) / 4.0F;
                this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var19);
                this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var19);
                this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var19);
                this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var19);
            }
            else
            {
                var23 = this.aoLightValueZPos;
                var22 = this.aoLightValueZPos;
                var21 = this.aoLightValueZPos;
                var20 = this.aoLightValueZPos;
                this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var19;
            }

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var14 ? par5 : 1.0F) * 0.8F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var14 ? par6 : 1.0F) * 0.8F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var14 ? par7 : 1.0F) * 0.8F;
            this.colorRedTopLeft *= var20;
            this.colorGreenTopLeft *= var20;
            this.colorBlueTopLeft *= var20;
            this.colorRedBottomLeft *= var21;
            this.colorGreenBottomLeft *= var21;
            this.colorBlueBottomLeft *= var21;
            this.colorRedBottomRight *= var22;
            this.colorGreenBottomRight *= var22;
            this.colorBlueBottomRight *= var22;
            this.colorRedTopRight *= var23;
            this.colorGreenTopRight *= var23;
            this.colorBlueTopRight *= var23;
            var24 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 3);

            if (var9)
            {
                var24 = this.fixAoSideGrassTexture(var24, par2, par3, par4, 3, par5, par6, par7);
            }

            this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var24);

            if (var8 && fancyGrass && var24 == 3 && this.overrideBlockTexture < 0)
            {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var10 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
        {
            if (!this.aoLightValuesCalculated)
            {
                this.calculateAoLightValues(par1Block, par2, par3, par4);
            }

            var19 = var17;

            if (this.renderMinX <= 0.0D)
            {
                var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
            }

            if (this.aoType > 0)
            {
                if (this.renderMinX <= 0.0D)
                {
                    --par2;
                }

                this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
                this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
                this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
                this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
                this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
                this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
                this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
                this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);

                if (!this.aoGrassXYZNCN && !this.aoGrassXYZNNC)
                {
                    this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                    this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
                }
                else
                {
                    this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
                    this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
                }

                if (!this.aoGrassXYZNCP && !this.aoGrassXYZNNC)
                {
                    this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                    this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
                }
                else
                {
                    this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
                    this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
                }

                if (!this.aoGrassXYZNCN && !this.aoGrassXYZNPC)
                {
                    this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                    this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
                }
                else
                {
                    this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
                    this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
                }

                if (!this.aoGrassXYZNCP && !this.aoGrassXYZNPC)
                {
                    this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                    this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
                }
                else
                {
                    this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
                    this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
                }

                if (this.renderMinX <= 0.0D)
                {
                    ++par2;
                }

                var23 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + this.aoLightValueXNeg + this.aoLightValueScratchXZNP) / 4.0F;
                var20 = (this.aoLightValueXNeg + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
                var21 = (this.aoLightValueScratchXZNN + this.aoLightValueXNeg + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
                var22 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + this.aoLightValueXNeg) / 4.0F;
                this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var19);
                this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var19);
                this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var19);
                this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var19);
            }
            else
            {
                var23 = this.aoLightValueXNeg;
                var22 = this.aoLightValueXNeg;
                var21 = this.aoLightValueXNeg;
                var20 = this.aoLightValueXNeg;
                this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var19;
            }

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var15 ? par5 : 1.0F) * 0.6F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var15 ? par6 : 1.0F) * 0.6F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var15 ? par7 : 1.0F) * 0.6F;
            this.colorRedTopLeft *= var20;
            this.colorGreenTopLeft *= var20;
            this.colorBlueTopLeft *= var20;
            this.colorRedBottomLeft *= var21;
            this.colorGreenBottomLeft *= var21;
            this.colorBlueBottomLeft *= var21;
            this.colorRedBottomRight *= var22;
            this.colorGreenBottomRight *= var22;
            this.colorBlueBottomRight *= var22;
            this.colorRedTopRight *= var23;
            this.colorGreenTopRight *= var23;
            this.colorBlueTopRight *= var23;
            var24 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 4);

            if (var9)
            {
                var24 = this.fixAoSideGrassTexture(var24, par2, par3, par4, 4, par5, par6, par7);
            }

            this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var24);

            if (var8 && fancyGrass && var24 == 3 && this.overrideBlockTexture < 0)
            {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var10 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
        {
            if (!this.aoLightValuesCalculated)
            {
                this.calculateAoLightValues(par1Block, par2, par3, par4);
            }

            var19 = var17;

            if (this.renderMaxX >= 1.0D)
            {
                var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
            }

            if (this.aoType > 0)
            {
                if (this.renderMaxX >= 1.0D)
                {
                    ++par2;
                }

                this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
                this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
                this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
                this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
                this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
                this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
                this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
                this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);

                if (!this.aoGrassXYZPNC && !this.aoGrassXYZPCN)
                {
                    this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                    this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
                }
                else
                {
                    this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
                    this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
                }

                if (!this.aoGrassXYZPNC && !this.aoGrassXYZPCP)
                {
                    this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                    this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
                }
                else
                {
                    this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
                    this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
                }

                if (!this.aoGrassXYZPPC && !this.aoGrassXYZPCN)
                {
                    this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                    this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
                }
                else
                {
                    this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
                    this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
                }

                if (!this.aoGrassXYZPPC && !this.aoGrassXYZPCP)
                {
                    this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                    this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
                }
                else
                {
                    this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
                    this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
                }

                if (this.renderMaxX >= 1.0D)
                {
                    --par2;
                }

                var20 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + this.aoLightValueXPos + this.aoLightValueScratchXZPP) / 4.0F;
                var23 = (this.aoLightValueXPos + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
                var22 = (this.aoLightValueScratchXZPN + this.aoLightValueXPos + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
                var21 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + this.aoLightValueXPos) / 4.0F;
                this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var19);
                this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var19);
                this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var19);
                this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var19);
            }
            else
            {
                var23 = this.aoLightValueXPos;
                var22 = this.aoLightValueXPos;
                var21 = this.aoLightValueXPos;
                var20 = this.aoLightValueXPos;
                this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var19;
            }

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var16 ? par5 : 1.0F) * 0.6F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var16 ? par6 : 1.0F) * 0.6F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var16 ? par7 : 1.0F) * 0.6F;
            this.colorRedTopLeft *= var20;
            this.colorGreenTopLeft *= var20;
            this.colorBlueTopLeft *= var20;
            this.colorRedBottomLeft *= var21;
            this.colorGreenBottomLeft *= var21;
            this.colorBlueBottomLeft *= var21;
            this.colorRedBottomRight *= var22;
            this.colorGreenBottomRight *= var22;
            this.colorBlueBottomRight *= var22;
            this.colorRedTopRight *= var23;
            this.colorGreenTopRight *= var23;
            this.colorBlueTopRight *= var23;
            var24 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 5);

            if (var9)
            {
                var24 = this.fixAoSideGrassTexture(var24, par2, par3, par4, 5, par5, par6, par7);
            }

            this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var24);

            if (var8 && fancyGrass && var24 == 3 && this.overrideBlockTexture < 0)
            {
                this.colorRedTopLeft *= par5;
                this.colorRedBottomLeft *= par5;
                this.colorRedBottomRight *= par5;
                this.colorRedTopRight *= par5;
                this.colorGreenTopLeft *= par6;
                this.colorGreenBottomLeft *= par6;
                this.colorGreenBottomRight *= par6;
                this.colorGreenTopRight *= par6;
                this.colorBlueTopLeft *= par7;
                this.colorBlueBottomLeft *= par7;
                this.colorBlueBottomRight *= par7;
                this.colorBlueTopRight *= par7;
                this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var10 = true;
        }

        this.enableAO = false;
        return var10;
    }

    /**
     * Get ambient occlusion brightness
     */
    public int getAoBrightness(int par1, int par2, int par3, int par4)
    {
        if (par1 == 0)
        {
            par1 = par4;
        }

        if (par2 == 0)
        {
            par2 = par4;
        }

        if (par3 == 0)
        {
            par3 = par4;
        }

        return par1 + par2 + par3 + par4 >> 2 & 16711935;
    }

    /**
     * Renders a standard cube block at the given coordinates, with a given color ratio.  Args: block, x, y, z, r, g, b
     */
    public boolean renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
    {
        this.enableAO = false;
        boolean var8 = Tessellator.instance.defaultTexture;
        boolean var9 = Config.isBetterGrass() && var8;
        Tessellator var10 = Tessellator.instance;
        boolean var11 = false;
        float var12 = 0.5F;
        float var13 = 1.0F;
        float var14 = 0.8F;
        float var15 = 0.6F;
        float var16 = var13 * par5;
        float var17 = var13 * par6;
        float var18 = var13 * par7;
        float var19 = var12;
        float var20 = var14;
        float var21 = var15;
        float var22 = var12;
        float var23 = var14;
        float var24 = var15;
        float var25 = var12;
        float var26 = var14;
        float var27 = var15;

        if (par1Block != Block.grass)
        {
            var19 = var12 * par5;
            var20 = var14 * par5;
            var21 = var15 * par5;
            var22 = var12 * par6;
            var23 = var14 * par6;
            var24 = var15 * par6;
            var25 = var12 * par7;
            var26 = var14 * par7;
            var27 = var15 * par7;
        }

        int var28 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
        {
            var10.setBrightness(this.renderMinY <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4) : var28);
            var10.setColorOpaque_F(var19, var22, var25);
            this.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 0));
            var11 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
        {
            var10.setBrightness(this.renderMaxY >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4) : var28);
            var10.setColorOpaque_F(var16, var17, var18);
            this.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 1));
            var11 = true;
        }

        int var29;

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
        {
            var10.setBrightness(this.renderMinZ <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1) : var28);
            var10.setColorOpaque_F(var20, var23, var26);
            var29 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 2);

            if (var9)
            {
                if (var29 == 3 || var29 == 77)
                {
                    var29 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 2, var29);

                    if (var29 == 0)
                    {
                        var10.setColorOpaque_F(var20 * par5, var23 * par6, var26 * par7);
                    }
                }

                if (var29 == 68)
                {
                    var29 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 2);
                }
            }

            this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var29);

            if (var8 && fancyGrass && var29 == 3 && this.overrideBlockTexture < 0)
            {
                var10.setColorOpaque_F(var20 * par5, var23 * par6, var26 * par7);
                this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var11 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
        {
            var10.setBrightness(this.renderMaxZ >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1) : var28);
            var10.setColorOpaque_F(var20, var23, var26);
            var29 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 3);

            if (var9)
            {
                if (var29 == 3 || var29 == 77)
                {
                    var29 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 3, var29);

                    if (var29 == 0)
                    {
                        var10.setColorOpaque_F(var20 * par5, var23 * par6, var26 * par7);
                    }
                }

                if (var29 == 68)
                {
                    var29 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 3);
                }
            }

            this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var29);

            if (var8 && fancyGrass && var29 == 3 && this.overrideBlockTexture < 0)
            {
                var10.setColorOpaque_F(var20 * par5, var23 * par6, var26 * par7);
                this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var11 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
        {
            var10.setBrightness(this.renderMinX <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4) : var28);
            var10.setColorOpaque_F(var21, var24, var27);
            var29 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 4);

            if (var9)
            {
                if (var29 == 3 || var29 == 77)
                {
                    var29 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 4, var29);

                    if (var29 == 0)
                    {
                        var10.setColorOpaque_F(var20 * par5, var23 * par6, var26 * par7);
                    }
                }

                if (var29 == 68)
                {
                    var29 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 4);
                }
            }

            this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var29);

            if (var8 && fancyGrass && var29 == 3 && this.overrideBlockTexture < 0)
            {
                var10.setColorOpaque_F(var21 * par5, var24 * par6, var27 * par7);
                this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var11 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
        {
            var10.setBrightness(this.renderMaxX >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4) : var28);
            var10.setColorOpaque_F(var21, var24, var27);
            var29 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 5);

            if (var9)
            {
                if (var29 == 3 || var29 == 77)
                {
                    var29 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 5, var29);

                    if (var29 == 0)
                    {
                        var10.setColorOpaque_F(var20 * par5, var23 * par6, var26 * par7);
                    }
                }

                if (var29 == 68)
                {
                    var29 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 5);
                }
            }

            this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var29);

            if (var8 && fancyGrass && var29 == 3 && this.overrideBlockTexture < 0)
            {
                var10.setColorOpaque_F(var21 * par5, var24 * par6, var27 * par7);
                this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, 38);
            }

            var11 = true;
        }

        return var11;
    }

    /**
     * Renders a Cocoa block at the given coordinates
     */
    public boolean renderBlockCocoa(BlockCocoa par1BlockCocoa, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockCocoa.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        int var7 = par1BlockCocoa.getBlockTextureFromSide(1);
        int var8 = BlockDirectional.getDirection(var6);
        int var9 = BlockCocoa.func_72219_c(var6);
        var7 = var7 + 2 - var9;
        int var10 = 4 + var9 * 2;
        int var11 = 5 + var9 * 2;
        int var12 = (var7 & 15) << 4;
        int var13 = var7 & 240;
        double var14 = 15.0D - (double)var10;
        double var16 = 15.0D;
        double var18 = 4.0D;
        double var20 = 4.0D + (double)var11;
        double var22 = ((double)var12 + var14) / 256.0D;
        double var24 = ((double)var12 + var16 - 0.01D) / 256.0D;
        double var26 = ((double)var13 + var18) / 256.0D;
        double var28 = ((double)var13 + var20 - 0.01D) / 256.0D;
        double var30 = 0.0D;
        double var32 = 0.0D;

        switch (var8)
        {
            case 0:
                var30 = 8.0D - (double)(var10 / 2);
                var32 = 15.0D - (double)var10;
                break;

            case 1:
                var30 = 1.0D;
                var32 = 8.0D - (double)(var10 / 2);
                break;

            case 2:
                var30 = 8.0D - (double)(var10 / 2);
                var32 = 1.0D;
                break;

            case 3:
                var30 = 15.0D - (double)var10;
                var32 = 8.0D - (double)(var10 / 2);
        }

        double var34 = (double)par2 + var30 / 16.0D;
        double var36 = (double)par2 + (var30 + (double)var10) / 16.0D;
        double var38 = (double)par3 + (12.0D - (double)var11) / 16.0D;
        double var40 = (double)par3 + 0.75D;
        double var42 = (double)par4 + var32 / 16.0D;
        double var44 = (double)par4 + (var32 + (double)var10) / 16.0D;
        var5.addVertexWithUV(var34, var38, var42, var22, var28);
        var5.addVertexWithUV(var34, var38, var44, var24, var28);
        var5.addVertexWithUV(var34, var40, var44, var24, var26);
        var5.addVertexWithUV(var34, var40, var42, var22, var26);
        var5.addVertexWithUV(var36, var38, var44, var22, var28);
        var5.addVertexWithUV(var36, var38, var42, var24, var28);
        var5.addVertexWithUV(var36, var40, var42, var24, var26);
        var5.addVertexWithUV(var36, var40, var44, var22, var26);
        var5.addVertexWithUV(var36, var38, var42, var22, var28);
        var5.addVertexWithUV(var34, var38, var42, var24, var28);
        var5.addVertexWithUV(var34, var40, var42, var24, var26);
        var5.addVertexWithUV(var36, var40, var42, var22, var26);
        var5.addVertexWithUV(var34, var38, var44, var22, var28);
        var5.addVertexWithUV(var36, var38, var44, var24, var28);
        var5.addVertexWithUV(var36, var40, var44, var24, var26);
        var5.addVertexWithUV(var34, var40, var44, var22, var26);
        int var46 = var10;

        if (var9 >= 2)
        {
            var46 = var10 - 1;
        }

        var22 = (double)((float)(var12 + 0) / 256.0F);
        var24 = ((double)(var12 + var46) - 0.01D) / 256.0D;
        var26 = (double)((float)(var13 + 0) / 256.0F);
        var28 = ((double)(var13 + var46) - 0.01D) / 256.0D;
        var5.addVertexWithUV(var34, var40, var44, var22, var28);
        var5.addVertexWithUV(var36, var40, var44, var24, var28);
        var5.addVertexWithUV(var36, var40, var42, var24, var26);
        var5.addVertexWithUV(var34, var40, var42, var22, var26);
        var5.addVertexWithUV(var34, var38, var42, var22, var26);
        var5.addVertexWithUV(var36, var38, var42, var24, var26);
        var5.addVertexWithUV(var36, var38, var44, var24, var28);
        var5.addVertexWithUV(var34, var38, var44, var22, var28);
        var22 = (double)((float)(var12 + 12) / 256.0F);
        var24 = ((double)(var12 + 16) - 0.01D) / 256.0D;
        var26 = (double)((float)(var13 + 0) / 256.0F);
        var28 = ((double)(var13 + 4) - 0.01D) / 256.0D;
        var30 = 8.0D;
        var32 = 0.0D;
        double var47;

        switch (var8)
        {
            case 0:
                var30 = 8.0D;
                var32 = 12.0D;
                var47 = var22;
                var22 = var24;
                var24 = var47;
                break;

            case 1:
                var30 = 0.0D;
                var32 = 8.0D;
                break;

            case 2:
                var30 = 8.0D;
                var32 = 0.0D;
                break;

            case 3:
                var30 = 12.0D;
                var32 = 8.0D;
                var47 = var22;
                var22 = var24;
                var24 = var47;
        }

        var34 = (double)par2 + var30 / 16.0D;
        var36 = (double)par2 + (var30 + 4.0D) / 16.0D;
        var38 = (double)par3 + 0.75D;
        var40 = (double)par3 + 1.0D;
        var42 = (double)par4 + var32 / 16.0D;
        var44 = (double)par4 + (var32 + 4.0D) / 16.0D;

        if (var8 != 2 && var8 != 0)
        {
            if (var8 == 1 || var8 == 3)
            {
                var5.addVertexWithUV(var36, var38, var42, var22, var28);
                var5.addVertexWithUV(var34, var38, var42, var24, var28);
                var5.addVertexWithUV(var34, var40, var42, var24, var26);
                var5.addVertexWithUV(var36, var40, var42, var22, var26);
                var5.addVertexWithUV(var34, var38, var42, var24, var28);
                var5.addVertexWithUV(var36, var38, var42, var22, var28);
                var5.addVertexWithUV(var36, var40, var42, var22, var26);
                var5.addVertexWithUV(var34, var40, var42, var24, var26);
            }
        }
        else
        {
            var5.addVertexWithUV(var34, var38, var42, var24, var28);
            var5.addVertexWithUV(var34, var38, var44, var22, var28);
            var5.addVertexWithUV(var34, var40, var44, var22, var26);
            var5.addVertexWithUV(var34, var40, var42, var24, var26);
            var5.addVertexWithUV(var34, var38, var44, var22, var28);
            var5.addVertexWithUV(var34, var38, var42, var24, var28);
            var5.addVertexWithUV(var34, var40, var42, var24, var26);
            var5.addVertexWithUV(var34, var40, var44, var22, var26);
        }

        return true;
    }

    /**
     * Renders beacon block
     */
    public boolean renderBlockBeacon(BlockBeacon par1BlockBeacon, int par2, int par3, int par4)
    {
        float var5 = 0.1875F;
        this.setOverrideBlockTexture(Block.obsidian.blockIndexInTexture);
        this.setRenderBounds(0.125D, 0.0062500000931322575D, 0.125D, 0.875D, (double)var5, 0.875D);
        this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
        this.setOverrideBlockTexture(Block.glass.blockIndexInTexture);
        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
        this.setOverrideBlockTexture(41);
        this.setRenderBounds(0.1875D, (double)var5, 0.1875D, 0.8125D, 0.875D, 0.8125D);
        this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
        this.clearOverrideBlockTexture();
        return true;
    }

    /**
     * Renders a cactus block at the given coordinates
     */
    public boolean renderBlockCactus(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4);
        float var6 = (float)(var5 >> 16 & 255) / 255.0F;
        float var7 = (float)(var5 >> 8 & 255) / 255.0F;
        float var8 = (float)(var5 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
            float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
            float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }

        return this.renderBlockCactusImpl(par1Block, par2, par3, par4, var6, var7, var8);
    }

    /**
     * Render block cactus implementation
     */
    public boolean renderBlockCactusImpl(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
    {
        Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        float var10 = 0.5F;
        float var11 = 1.0F;
        float var12 = 0.8F;
        float var13 = 0.6F;
        float var14 = var10 * par5;
        float var15 = var11 * par5;
        float var16 = var12 * par5;
        float var17 = var13 * par5;
        float var18 = var10 * par6;
        float var19 = var11 * par6;
        float var20 = var12 * par6;
        float var21 = var13 * par6;
        float var22 = var10 * par7;
        float var23 = var11 * par7;
        float var24 = var12 * par7;
        float var25 = var13 * par7;
        float var26 = 0.0625F;
        int var27 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
        {
            var8.setBrightness(this.renderMinY > 0.0D ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
            var8.setColorOpaque_F(var14, var18, var22);
            this.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 0));
            var9 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
        {
            var8.setBrightness(this.renderMaxY < 1.0D ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
            var8.setColorOpaque_F(var15, var19, var23);
            this.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 1));
            var9 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
        {
            var8.setBrightness(this.renderMinZ > 0.0D ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
            var8.setColorOpaque_F(var16, var20, var24);
            var8.addTranslation(0.0F, 0.0F, var26);
            this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 2));
            var8.addTranslation(0.0F, 0.0F, -var26);
            var9 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
        {
            var8.setBrightness(this.renderMaxZ < 1.0D ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
            var8.setColorOpaque_F(var16, var20, var24);
            var8.addTranslation(0.0F, 0.0F, -var26);
            this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 3));
            var8.addTranslation(0.0F, 0.0F, var26);
            var9 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
        {
            var8.setBrightness(this.renderMinX > 0.0D ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
            var8.setColorOpaque_F(var17, var21, var25);
            var8.addTranslation(var26, 0.0F, 0.0F);
            this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 4));
            var8.addTranslation(-var26, 0.0F, 0.0F);
            var9 = true;
        }

        if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
        {
            var8.setBrightness(this.renderMaxX < 1.0D ? var27 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
            var8.setColorOpaque_F(var17, var21, var25);
            var8.addTranslation(-var26, 0.0F, 0.0F);
            this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 5));
            var8.addTranslation(var26, 0.0F, 0.0F);
            var9 = true;
        }

        return var9;
    }

    public boolean renderBlockFence(BlockFence par1BlockFence, int par2, int par3, int par4)
    {
        boolean var5 = false;
        float var6 = 0.375F;
        float var7 = 0.625F;
        this.setRenderBounds((double)var6, 0.0D, (double)var6, (double)var7, 1.0D, (double)var7);
        this.renderStandardBlock(par1BlockFence, par2, par3, par4);
        var5 = true;
        boolean var8 = false;
        boolean var9 = false;

        if (par1BlockFence.canConnectFenceTo(this.blockAccess, par2 - 1, par3, par4) || par1BlockFence.canConnectFenceTo(this.blockAccess, par2 + 1, par3, par4))
        {
            var8 = true;
        }

        if (par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 - 1) || par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 + 1))
        {
            var9 = true;
        }

        boolean var10 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2 - 1, par3, par4);
        boolean var11 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2 + 1, par3, par4);
        boolean var12 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 - 1);
        boolean var13 = par1BlockFence.canConnectFenceTo(this.blockAccess, par2, par3, par4 + 1);

        if (!var8 && !var9)
        {
            var8 = true;
        }

        var6 = 0.4375F;
        var7 = 0.5625F;
        float var14 = 0.75F;
        float var15 = 0.9375F;
        float var16 = var10 ? 0.0F : var6;
        float var17 = var11 ? 1.0F : var7;
        float var18 = var12 ? 0.0F : var6;
        float var19 = var13 ? 1.0F : var7;

        if (var8)
        {
            this.setRenderBounds((double)var16, (double)var14, (double)var6, (double)var17, (double)var15, (double)var7);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }

        if (var9)
        {
            this.setRenderBounds((double)var6, (double)var14, (double)var18, (double)var7, (double)var15, (double)var19);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }

        var14 = 0.375F;
        var15 = 0.5625F;

        if (var8)
        {
            this.setRenderBounds((double)var16, (double)var14, (double)var6, (double)var17, (double)var15, (double)var7);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }

        if (var9)
        {
            this.setRenderBounds((double)var6, (double)var14, (double)var18, (double)var7, (double)var15, (double)var19);
            this.renderStandardBlock(par1BlockFence, par2, par3, par4);
            var5 = true;
        }

        par1BlockFence.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        return var5;
    }

    /**
     * Renders wall block
     */
    public boolean renderBlockWall(BlockWall par1BlockWall, int par2, int par3, int par4)
    {
        boolean var5 = par1BlockWall.canConnectWallTo(this.blockAccess, par2 - 1, par3, par4);
        boolean var6 = par1BlockWall.canConnectWallTo(this.blockAccess, par2 + 1, par3, par4);
        boolean var7 = par1BlockWall.canConnectWallTo(this.blockAccess, par2, par3, par4 - 1);
        boolean var8 = par1BlockWall.canConnectWallTo(this.blockAccess, par2, par3, par4 + 1);
        boolean var9 = var7 && var8 && !var5 && !var6;
        boolean var10 = !var7 && !var8 && var5 && var6;
        boolean var11 = this.blockAccess.isAirBlock(par2, par3 + 1, par4);

        if ((var9 || var10) && var11)
        {
            if (var9)
            {
                this.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
            else
            {
                this.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
        }
        else
        {
            this.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
            this.renderStandardBlock(par1BlockWall, par2, par3, par4);

            if (var5)
            {
                this.setRenderBounds(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }

            if (var6)
            {
                this.setRenderBounds(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }

            if (var7)
            {
                this.setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }

            if (var8)
            {
                this.setRenderBounds(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D);
                this.renderStandardBlock(par1BlockWall, par2, par3, par4);
            }
        }

        par1BlockWall.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        return true;
    }

    public boolean renderBlockDragonEgg(BlockDragonEgg par1BlockDragonEgg, int par2, int par3, int par4)
    {
        boolean var5 = false;
        int var6 = 0;

        for (int var7 = 0; var7 < 8; ++var7)
        {
            byte var8 = 0;
            byte var9 = 1;

            if (var7 == 0)
            {
                var8 = 2;
            }

            if (var7 == 1)
            {
                var8 = 3;
            }

            if (var7 == 2)
            {
                var8 = 4;
            }

            if (var7 == 3)
            {
                var8 = 5;
                var9 = 2;
            }

            if (var7 == 4)
            {
                var8 = 6;
                var9 = 3;
            }

            if (var7 == 5)
            {
                var8 = 7;
                var9 = 5;
            }

            if (var7 == 6)
            {
                var8 = 6;
                var9 = 2;
            }

            if (var7 == 7)
            {
                var8 = 3;
            }

            float var10 = (float)var8 / 16.0F;
            float var11 = 1.0F - (float)var6 / 16.0F;
            float var12 = 1.0F - (float)(var6 + var9) / 16.0F;
            var6 += var9;
            this.setRenderBounds((double)(0.5F - var10), (double)var12, (double)(0.5F - var10), (double)(0.5F + var10), (double)var11, (double)(0.5F + var10));
            this.renderStandardBlock(par1BlockDragonEgg, par2, par3, par4);
        }

        var5 = true;
        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        return var5;
    }

    /**
     * Render block fence gate
     */
    public boolean renderBlockFenceGate(BlockFenceGate par1BlockFenceGate, int par2, int par3, int par4)
    {
        boolean var5 = true;
        int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
        boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
        int var8 = BlockDirectional.getDirection(var6);
        float var9 = 0.375F;
        float var10 = 0.5625F;
        float var11 = 0.75F;
        float var12 = 0.9375F;
        float var13 = 0.3125F;
        float var14 = 1.0F;

        if ((var8 == 2 || var8 == 0) && this.blockAccess.getBlockId(par2 - 1, par3, par4) == Block.cobblestoneWall.blockID && this.blockAccess.getBlockId(par2 + 1, par3, par4) == Block.cobblestoneWall.blockID || (var8 == 3 || var8 == 1) && this.blockAccess.getBlockId(par2, par3, par4 - 1) == Block.cobblestoneWall.blockID && this.blockAccess.getBlockId(par2, par3, par4 + 1) == Block.cobblestoneWall.blockID)
        {
            var9 -= 0.1875F;
            var10 -= 0.1875F;
            var11 -= 0.1875F;
            var12 -= 0.1875F;
            var13 -= 0.1875F;
            var14 -= 0.1875F;
        }

        this.renderAllFaces = true;
        float var15;
        float var17;
        float var16;
        float var18;

        if (var8 != 3 && var8 != 1)
        {
            var15 = 0.0F;
            var17 = 0.125F;
            var16 = 0.4375F;
            var18 = 0.5625F;
            this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.875F;
            var17 = 1.0F;
            this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
        }
        else
        {
            this.uvRotateTop = 1;
            var15 = 0.4375F;
            var17 = 0.5625F;
            var16 = 0.0F;
            var18 = 0.125F;
            this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var16 = 0.875F;
            var18 = 1.0F;
            this.setRenderBounds((double)var15, (double)var13, (double)var16, (double)var17, (double)var14, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.uvRotateTop = 0;
        }

        if (var7)
        {
            if (var8 == 2 || var8 == 0)
            {
                this.uvRotateTop = 1;
            }

            if (var8 == 3)
            {
                this.setRenderBounds(0.8125D, (double)var9, 0.0D, 0.9375D, (double)var12, 0.125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.8125D, (double)var9, 0.875D, 0.9375D, (double)var12, 1.0D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625D, (double)var9, 0.0D, 0.8125D, (double)var10, 0.125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625D, (double)var9, 0.875D, 0.8125D, (double)var10, 1.0D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625D, (double)var11, 0.0D, 0.8125D, (double)var12, 0.125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.5625D, (double)var11, 0.875D, 0.8125D, (double)var12, 1.0D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
            else if (var8 == 1)
            {
                this.setRenderBounds(0.0625D, (double)var9, 0.0D, 0.1875D, (double)var12, 0.125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0625D, (double)var9, 0.875D, 0.1875D, (double)var12, 1.0D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875D, (double)var9, 0.0D, 0.4375D, (double)var10, 0.125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875D, (double)var9, 0.875D, 0.4375D, (double)var10, 1.0D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875D, (double)var11, 0.0D, 0.4375D, (double)var12, 0.125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.1875D, (double)var11, 0.875D, 0.4375D, (double)var12, 1.0D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
            else if (var8 == 0)
            {
                this.setRenderBounds(0.0D, (double)var9, 0.8125D, 0.125D, (double)var12, 0.9375D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875D, (double)var9, 0.8125D, 1.0D, (double)var12, 0.9375D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0D, (double)var9, 0.5625D, 0.125D, (double)var10, 0.8125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875D, (double)var9, 0.5625D, 1.0D, (double)var10, 0.8125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0D, (double)var11, 0.5625D, 0.125D, (double)var12, 0.8125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875D, (double)var11, 0.5625D, 1.0D, (double)var12, 0.8125D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
            else if (var8 == 2)
            {
                this.setRenderBounds(0.0D, (double)var9, 0.0625D, 0.125D, (double)var12, 0.1875D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875D, (double)var9, 0.0625D, 1.0D, (double)var12, 0.1875D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0D, (double)var9, 0.1875D, 0.125D, (double)var10, 0.4375D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875D, (double)var9, 0.1875D, 1.0D, (double)var10, 0.4375D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.0D, (double)var11, 0.1875D, 0.125D, (double)var12, 0.4375D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
                this.setRenderBounds(0.875D, (double)var11, 0.1875D, 1.0D, (double)var12, 0.4375D);
                this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            }
        }
        else if (var8 != 3 && var8 != 1)
        {
            var15 = 0.375F;
            var17 = 0.5F;
            var16 = 0.4375F;
            var18 = 0.5625F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.5F;
            var17 = 0.625F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.625F;
            var17 = 0.875F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var15 = 0.125F;
            var17 = 0.375F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
        }
        else
        {
            this.uvRotateTop = 1;
            var15 = 0.4375F;
            var17 = 0.5625F;
            var16 = 0.375F;
            var18 = 0.5F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var16 = 0.5F;
            var18 = 0.625F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var16 = 0.625F;
            var18 = 0.875F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            var16 = 0.125F;
            var18 = 0.375F;
            this.setRenderBounds((double)var15, (double)var9, (double)var16, (double)var17, (double)var10, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
            this.setRenderBounds((double)var15, (double)var11, (double)var16, (double)var17, (double)var12, (double)var18);
            this.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
        }

        if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        this.renderAllFaces = false;
        this.uvRotateTop = 0;
        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        return var5;
    }

    /**
     * Renders a stair block at the given coordinates
     */
    public boolean renderBlockStairs(BlockStairs par1BlockStairs, int par2, int par3, int par4)
    {
        par1BlockStairs.func_82541_d(this.blockAccess, par2, par3, par4);
        this.setRenderBoundsFromBlock(par1BlockStairs);
        this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
        boolean var5 = par1BlockStairs.func_82542_g(this.blockAccess, par2, par3, par4);
        this.setRenderBoundsFromBlock(par1BlockStairs);
        this.renderStandardBlock(par1BlockStairs, par2, par3, par4);

        if (var5 && par1BlockStairs.func_82544_h(this.blockAccess, par2, par3, par4))
        {
            this.setRenderBoundsFromBlock(par1BlockStairs);
            this.renderStandardBlock(par1BlockStairs, par2, par3, par4);
        }

        return true;
    }

    /**
     * Renders a door block at the given coordinates
     */
    public boolean renderBlockDoor(Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        boolean var6 = false;
        float var7 = 0.5F;
        float var8 = 1.0F;
        float var9 = 0.8F;
        float var10 = 0.6F;
        int var11 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
        var5.setBrightness(this.renderMinY > 0.0D ? var11 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
        var5.setColorOpaque_F(var7, var7, var7);
        this.renderBottomFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 0));
        var6 = true;
        var5.setBrightness(this.renderMaxY < 1.0D ? var11 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
        var5.setColorOpaque_F(var8, var8, var8);
        this.renderTopFace(par1Block, (double)par2, (double)par3, (double)par4, par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 1));
        var6 = true;
        var5.setBrightness(this.renderMinZ > 0.0D ? var11 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
        var5.setColorOpaque_F(var9, var9, var9);
        int var12 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 2);

        if (var12 < 0)
        {
            this.flipTexture = true;
            var12 = -var12;
        }

        this.renderEastFace(par1Block, (double)par2, (double)par3, (double)par4, var12);
        var6 = true;
        this.flipTexture = false;
        var5.setBrightness(this.renderMaxZ < 1.0D ? var11 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
        var5.setColorOpaque_F(var9, var9, var9);
        var12 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 3);

        if (var12 < 0)
        {
            this.flipTexture = true;
            var12 = -var12;
        }

        this.renderWestFace(par1Block, (double)par2, (double)par3, (double)par4, var12);
        var6 = true;
        this.flipTexture = false;
        var5.setBrightness(this.renderMinX > 0.0D ? var11 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
        var5.setColorOpaque_F(var10, var10, var10);
        var12 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 4);

        if (var12 < 0)
        {
            this.flipTexture = true;
            var12 = -var12;
        }

        this.renderNorthFace(par1Block, (double)par2, (double)par3, (double)par4, var12);
        var6 = true;
        this.flipTexture = false;
        var5.setBrightness(this.renderMaxX < 1.0D ? var11 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
        var5.setColorOpaque_F(var10, var10, var10);
        var12 = par1Block.getBlockTexture(this.blockAccess, par2, par3, par4, 5);

        if (var12 < 0)
        {
            this.flipTexture = true;
            var12 = -var12;
        }

        this.renderSouthFace(par1Block, (double)par2, (double)par3, (double)par4, var12);
        var6 = true;
        this.flipTexture = false;
        return var6;
    }

    /**
     * Renders the given texture to the bottom face of the block. Args: block, x, y, z, texture
     */
    public void renderBottomFace(Block par1Block, double par2, double par4, double par6, int par8)
    {
        Tessellator var9 = Tessellator.instance;

        if (this.overrideBlockTexture >= 0)
        {
            par8 = this.overrideBlockTexture;
        }

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0 && this.uvRotateBottom == 0)
        {
            int var10 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 0, par8);

            if (var10 >= 0)
            {
                int var11 = var10 / 256;
                var9 = Tessellator.instance.getSubTessellator(var11);
                par8 = var10 % 256;
            }
        }

        boolean var48 = false;

        if (Config.isNaturalTextures() && this.overrideBlockTexture < 0 && this.uvRotateBottom == 0)
        {
            NaturalProperties var47 = NaturalTextures.getNaturalProperties(var9.textureID, par8);

            if (var47 != null)
            {
                int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 0);

                if (var47.rotation > 1)
                {
                    this.uvRotateBottom = var12 & 3;
                }

                if (var47.rotation == 2)
                {
                    this.uvRotateBottom = this.uvRotateBottom / 2 * 3;
                }

                if (var47.flip)
                {
                    this.flipTexture = (var12 & 4) != 0;
                }

                var48 = true;
            }
        }

        double var49 = this.renderMinX;
        double var13 = this.renderMaxX;
        double var15 = this.renderMinZ;
        double var17 = this.renderMaxZ;

        if (var49 < 0.0D || var13 > 1.0D)
        {
            var49 = 0.0D;
            var13 = 1.0D;
        }

        if (var15 < 0.0D || var17 > 1.0D)
        {
            var15 = 0.0D;
            var17 = 1.0D;
        }

        int var19 = (par8 & 15) << 4;
        int var20 = par8 & 240;
        double var21 = ((double)var19 + var49 * 16.0D) / 256.0D;
        double var23 = ((double)var19 + var13 * 16.0D - 0.01D) / 256.0D;
        double var25 = ((double)var20 + var15 * 16.0D) / 256.0D;
        double var27 = ((double)var20 + var17 * 16.0D - 0.01D) / 256.0D;
        double var29;

        if (this.flipTexture)
        {
            var29 = var21;
            var21 = var23;
            var23 = var29;
        }

        var29 = var23;
        double var31 = var21;
        double var33 = var25;
        double var35 = var27;
        double var37;

        if (this.uvRotateBottom == 2)
        {
            var21 = ((double)var19 + var15 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var13 * 16.0D) / 256.0D;
            var23 = ((double)var19 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var49 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var33 = var25;
            var35 = var27;
            var29 = var21;
            var31 = var23;
            var25 = var27;
            var27 = var33;
        }
        else if (this.uvRotateBottom == 1)
        {
            var21 = ((double)(var19 + 16) - var17 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var49 * 16.0D) / 256.0D;
            var23 = ((double)(var19 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var13 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var21 = var23;
            var23 = var31;
            var33 = var27;
            var35 = var25;
        }
        else if (this.uvRotateBottom == 3)
        {
            var21 = ((double)(var19 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var13 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var17 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var33 = var25;
            var35 = var27;
        }

        if (var48)
        {
            this.uvRotateBottom = 0;
            this.flipTexture = false;
        }

        var37 = par2 + this.renderMinX;
        double var39 = par2 + this.renderMaxX;
        double var41 = par4 + this.renderMinY;
        double var43 = par6 + this.renderMinZ;
        double var45 = par6 + this.renderMaxZ;

        if (this.enableAO)
        {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var37, var41, var45, var31, var35);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var37, var41, var43, var21, var25);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var39, var41, var43, var29, var33);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var39, var41, var45, var23, var27);
        }
        else
        {
            var9.addVertexWithUV(var37, var41, var45, var31, var35);
            var9.addVertexWithUV(var37, var41, var43, var21, var25);
            var9.addVertexWithUV(var39, var41, var43, var29, var33);
            var9.addVertexWithUV(var39, var41, var45, var23, var27);
        }
    }

    /**
     * Renders the given texture to the top face of the block. Args: block, x, y, z, texture
     */
    public void renderTopFace(Block par1Block, double par2, double par4, double par6, int par8)
    {
        Tessellator var9 = Tessellator.instance;

        if (this.overrideBlockTexture >= 0)
        {
            par8 = this.overrideBlockTexture;
        }

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0 && this.uvRotateTop == 0)
        {
            int var10 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 1, par8);

            if (var10 >= 0)
            {
                int var11 = var10 / 256;
                var9 = Tessellator.instance.getSubTessellator(var11);
                par8 = var10 % 256;
            }
        }

        boolean var48 = false;

        if (Config.isNaturalTextures() && this.overrideBlockTexture < 0 && this.uvRotateTop == 0)
        {
            NaturalProperties var47 = NaturalTextures.getNaturalProperties(var9.textureID, par8);

            if (var47 != null)
            {
                int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 1);

                if (var47.rotation > 1)
                {
                    this.uvRotateTop = var12 & 3;
                }

                if (var47.rotation == 2)
                {
                    this.uvRotateTop = this.uvRotateTop / 2 * 3;
                }

                if (var47.flip)
                {
                    this.flipTexture = (var12 & 4) != 0;
                }

                var48 = true;
            }
        }

        double var49 = this.renderMinX;
        double var13 = this.renderMaxX;
        double var15 = this.renderMinZ;
        double var17 = this.renderMaxZ;

        if (var49 < 0.0D || var13 > 1.0D)
        {
            var49 = 0.0D;
            var13 = 1.0D;
        }

        if (var15 < 0.0D || var17 > 1.0D)
        {
            var15 = 0.0D;
            var17 = 1.0D;
        }

        int var19 = (par8 & 15) << 4;
        int var20 = par8 & 240;
        double var21 = ((double)var19 + var49 * 16.0D) / 256.0D;
        double var23 = ((double)var19 + var13 * 16.0D - 0.01D) / 256.0D;
        double var25 = ((double)var20 + var15 * 16.0D) / 256.0D;
        double var27 = ((double)var20 + var17 * 16.0D - 0.01D) / 256.0D;
        double var29;

        if (this.flipTexture)
        {
            var29 = var21;
            var21 = var23;
            var23 = var29;
        }

        var29 = var23;
        double var31 = var21;
        double var33 = var25;
        double var35 = var27;
        double var37;

        if (this.uvRotateTop == 1)
        {
            var21 = ((double)var19 + var15 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var13 * 16.0D) / 256.0D;
            var23 = ((double)var19 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var49 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var33 = var25;
            var35 = var27;
            var29 = var21;
            var31 = var23;
            var25 = var27;
            var27 = var33;
        }
        else if (this.uvRotateTop == 2)
        {
            var21 = ((double)(var19 + 16) - var17 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var49 * 16.0D) / 256.0D;
            var23 = ((double)(var19 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var13 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var21 = var23;
            var23 = var31;
            var33 = var27;
            var35 = var25;
        }
        else if (this.uvRotateTop == 3)
        {
            var21 = ((double)(var19 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var13 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var17 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var33 = var25;
            var35 = var27;
        }

        if (var48)
        {
            this.uvRotateTop = 0;
            this.flipTexture = false;
        }

        var37 = par2 + this.renderMinX;
        double var39 = par2 + this.renderMaxX;
        double var41 = par4 + this.renderMaxY;
        double var43 = par6 + this.renderMinZ;
        double var45 = par6 + this.renderMaxZ;

        if (this.enableAO)
        {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var39, var41, var45, var23, var27);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var39, var41, var43, var29, var33);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var37, var41, var43, var21, var25);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var37, var41, var45, var31, var35);
        }
        else
        {
            var9.addVertexWithUV(var39, var41, var45, var23, var27);
            var9.addVertexWithUV(var39, var41, var43, var29, var33);
            var9.addVertexWithUV(var37, var41, var43, var21, var25);
            var9.addVertexWithUV(var37, var41, var45, var31, var35);
        }
    }

    /**
     * Renders the given texture to the east (z-negative) face of the block.  Args: block, x, y, z, texture
     */
    public void renderEastFace(Block par1Block, double par2, double par4, double par6, int par8)
    {
        Tessellator var9 = Tessellator.instance;

        if (this.overrideBlockTexture >= 0)
        {
            par8 = this.overrideBlockTexture;
        }

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0 && this.uvRotateEast == 0)
        {
            int var10 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 2, par8);

            if (var10 >= 0)
            {
                int var11 = var10 / 256;
                var9 = Tessellator.instance.getSubTessellator(var11);
                par8 = var10 % 256;
            }
        }

        boolean var48 = false;

        if (Config.isNaturalTextures() && this.overrideBlockTexture < 0 && this.uvRotateEast == 0)
        {
            NaturalProperties var47 = NaturalTextures.getNaturalProperties(var9.textureID, par8);

            if (var47 != null)
            {
                int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 2);

                if (var47.rotation > 1)
                {
                    this.uvRotateEast = var12 & 3;
                }

                if (var47.rotation == 2)
                {
                    this.uvRotateEast = this.uvRotateEast / 2 * 3;
                }

                if (var47.flip)
                {
                    this.flipTexture = (var12 & 4) != 0;
                }

                var48 = true;
            }
        }

        double var49 = this.renderMinX;
        double var13 = this.renderMaxX;
        double var15 = this.renderMinY;
        double var17 = this.renderMaxY;

        if (var49 < 0.0D || var13 > 1.0D)
        {
            var49 = 0.0D;
            var13 = 1.0D;
        }

        if (var15 < 0.0D || var17 > 1.0D)
        {
            var15 = 0.0D;
            var17 = 1.0D;
        }

        int var19 = (par8 & 15) << 4;
        int var20 = par8 & 240;
        double var21 = ((double)var19 + var49 * 16.0D) / 256.0D;
        double var23 = ((double)var19 + var13 * 16.0D - 0.01D) / 256.0D;
        double var25 = ((double)(var20 + 16) - var17 * 16.0D) / 256.0D;
        double var27 = ((double)(var20 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
        double var29;

        if (this.flipTexture)
        {
            var29 = var21;
            var21 = var23;
            var23 = var29;
        }

        var29 = var23;
        double var31 = var21;
        double var33 = var25;
        double var35 = var27;
        double var37;

        if (this.uvRotateEast == 2)
        {
            var21 = ((double)var19 + var15 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)var19 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var13 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var33 = var25;
            var35 = var27;
            var29 = var21;
            var31 = var23;
            var25 = var27;
            var27 = var33;
        }
        else if (this.uvRotateEast == 1)
        {
            var21 = ((double)(var19 + 16) - var17 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var13 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var49 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var21 = var23;
            var23 = var31;
            var33 = var27;
            var35 = var25;
        }
        else if (this.uvRotateEast == 3)
        {
            var21 = ((double)(var19 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var13 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var15 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var33 = var25;
            var35 = var27;
        }

        if (var48)
        {
            this.uvRotateEast = 0;
            this.flipTexture = false;
        }

        var37 = par2 + this.renderMinX;
        double var39 = par2 + this.renderMaxX;
        double var41 = par4 + this.renderMinY;
        double var43 = par4 + this.renderMaxY;
        double var45 = par6 + this.renderMinZ;

        if (this.enableAO)
        {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var37, var43, var45, var29, var33);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var39, var43, var45, var21, var25);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var39, var41, var45, var31, var35);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var37, var41, var45, var23, var27);
        }
        else
        {
            var9.addVertexWithUV(var37, var43, var45, var29, var33);
            var9.addVertexWithUV(var39, var43, var45, var21, var25);
            var9.addVertexWithUV(var39, var41, var45, var31, var35);
            var9.addVertexWithUV(var37, var41, var45, var23, var27);
        }
    }

    /**
     * Renders the given texture to the west (z-positive) face of the block.  Args: block, x, y, z, texture
     */
    public void renderWestFace(Block par1Block, double par2, double par4, double par6, int par8)
    {
        Tessellator var9 = Tessellator.instance;

        if (this.overrideBlockTexture >= 0)
        {
            par8 = this.overrideBlockTexture;
        }

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0 && this.uvRotateWest == 0)
        {
            int var10 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 3, par8);

            if (var10 >= 0)
            {
                int var11 = var10 / 256;
                var9 = Tessellator.instance.getSubTessellator(var11);
                par8 = var10 % 256;
            }
        }

        boolean var48 = false;

        if (Config.isNaturalTextures() && this.overrideBlockTexture < 0 && this.uvRotateWest == 0)
        {
            NaturalProperties var47 = NaturalTextures.getNaturalProperties(var9.textureID, par8);

            if (var47 != null)
            {
                int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 3);

                if (var47.rotation > 1)
                {
                    this.uvRotateWest = var12 & 3;
                }

                if (var47.rotation == 2)
                {
                    this.uvRotateWest = this.uvRotateWest / 2 * 3;
                }

                if (var47.flip)
                {
                    this.flipTexture = (var12 & 4) != 0;
                }

                var48 = true;
            }
        }

        double var49 = this.renderMinX;
        double var13 = this.renderMaxX;
        double var15 = this.renderMinY;
        double var17 = this.renderMaxY;

        if (var49 < 0.0D || var13 > 1.0D)
        {
            var49 = 0.0D;
            var13 = 1.0D;
        }

        if (var15 < 0.0D || var17 > 1.0D)
        {
            var15 = 0.0D;
            var17 = 1.0D;
        }

        int var19 = (par8 & 15) << 4;
        int var20 = par8 & 240;
        double var21 = ((double)var19 + var49 * 16.0D) / 256.0D;
        double var23 = ((double)var19 + var13 * 16.0D - 0.01D) / 256.0D;
        double var25 = ((double)(var20 + 16) - var17 * 16.0D) / 256.0D;
        double var27 = ((double)(var20 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
        double var29;

        if (this.flipTexture)
        {
            var29 = var21;
            var21 = var23;
            var23 = var29;
        }

        var29 = var23;
        double var31 = var21;
        double var33 = var25;
        double var35 = var27;
        double var37;

        if (this.uvRotateWest == 1)
        {
            var21 = ((double)var19 + var15 * 16.0D) / 256.0D;
            var27 = ((double)(var20 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)var19 + var17 * 16.0D - 0.01D) / 256.0D;
            var25 = ((double)(var20 + 16) - var13 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var33 = var25;
            var35 = var27;
            var29 = var21;
            var31 = var23;
            var25 = var27;
            var27 = var33;
        }
        else if (this.uvRotateWest == 2)
        {
            var21 = ((double)(var19 + 16) - var17 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var49 * 16.0D) / 256.0D;
            var23 = ((double)(var19 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var13 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var21 = var23;
            var23 = var31;
            var33 = var27;
            var35 = var25;
        }
        else if (this.uvRotateWest == 3)
        {
            var21 = ((double)(var19 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var13 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var15 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var33 = var25;
            var35 = var27;
        }

        if (var48)
        {
            this.uvRotateWest = 0;
            this.flipTexture = false;
        }

        var37 = par2 + this.renderMinX;
        double var39 = par2 + this.renderMaxX;
        double var41 = par4 + this.renderMinY;
        double var43 = par4 + this.renderMaxY;
        double var45 = par6 + this.renderMaxZ;

        if (this.enableAO)
        {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var37, var43, var45, var21, var25);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var37, var41, var45, var31, var35);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var39, var41, var45, var23, var27);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var39, var43, var45, var29, var33);
        }
        else
        {
            var9.addVertexWithUV(var37, var43, var45, var21, var25);
            var9.addVertexWithUV(var37, var41, var45, var31, var35);
            var9.addVertexWithUV(var39, var41, var45, var23, var27);
            var9.addVertexWithUV(var39, var43, var45, var29, var33);
        }
    }

    /**
     * Renders the given texture to the north (x-negative) face of the block.  Args: block, x, y, z, texture
     */
    public void renderNorthFace(Block par1Block, double par2, double par4, double par6, int par8)
    {
        Tessellator var9 = Tessellator.instance;

        if (this.overrideBlockTexture >= 0)
        {
            par8 = this.overrideBlockTexture;
        }

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0 && this.uvRotateNorth == 0)
        {
            int var10 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 4, par8);

            if (var10 >= 0)
            {
                int var11 = var10 / 256;
                var9 = Tessellator.instance.getSubTessellator(var11);
                par8 = var10 % 256;
            }
        }

        boolean var48 = false;

        if (Config.isNaturalTextures() && this.overrideBlockTexture < 0 && this.uvRotateNorth == 0)
        {
            NaturalProperties var47 = NaturalTextures.getNaturalProperties(var9.textureID, par8);

            if (var47 != null)
            {
                int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 4);

                if (var47.rotation > 1)
                {
                    this.uvRotateNorth = var12 & 3;
                }

                if (var47.rotation == 2)
                {
                    this.uvRotateNorth = this.uvRotateNorth / 2 * 3;
                }

                if (var47.flip)
                {
                    this.flipTexture = (var12 & 4) != 0;
                }

                var48 = true;
            }
        }

        double var49 = this.renderMinZ;
        double var13 = this.renderMaxZ;
        double var15 = this.renderMinY;
        double var17 = this.renderMaxY;

        if (var49 < 0.0D || var13 > 1.0D)
        {
            var49 = 0.0D;
            var13 = 1.0D;
        }

        if (var15 < 0.0D || var17 > 1.0D)
        {
            var15 = 0.0D;
            var17 = 1.0D;
        }

        int var19 = (par8 & 15) << 4;
        int var20 = par8 & 240;
        double var21 = ((double)var19 + var49 * 16.0D) / 256.0D;
        double var23 = ((double)var19 + var13 * 16.0D - 0.01D) / 256.0D;
        double var25 = ((double)(var20 + 16) - var17 * 16.0D) / 256.0D;
        double var27 = ((double)(var20 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
        double var29;

        if (this.flipTexture)
        {
            var29 = var21;
            var21 = var23;
            var23 = var29;
        }

        var29 = var23;
        double var31 = var21;
        double var33 = var25;
        double var35 = var27;
        double var37;

        if (this.uvRotateNorth == 1)
        {
            var21 = ((double)var19 + var15 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var13 * 16.0D) / 256.0D;
            var23 = ((double)var19 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var49 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var33 = var25;
            var35 = var27;
            var29 = var21;
            var31 = var23;
            var25 = var27;
            var27 = var33;
        }
        else if (this.uvRotateNorth == 2)
        {
            var21 = ((double)(var19 + 16) - var17 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var49 * 16.0D) / 256.0D;
            var23 = ((double)(var19 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var13 * 16.0D - 0.01D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var21 = var23;
            var23 = var31;
            var33 = var27;
            var35 = var25;
        }
        else if (this.uvRotateNorth == 3)
        {
            var21 = ((double)(var19 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var13 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var15 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var33 = var25;
            var35 = var27;
        }

        if (var48)
        {
            this.uvRotateNorth = 0;
            this.flipTexture = false;
        }

        var37 = par2 + this.renderMinX;
        double var39 = par4 + this.renderMinY;
        double var41 = par4 + this.renderMaxY;
        double var43 = par6 + this.renderMinZ;
        double var45 = par6 + this.renderMaxZ;

        if (this.enableAO)
        {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var37, var41, var45, var29, var33);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var37, var41, var43, var21, var25);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var37, var39, var43, var31, var35);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var37, var39, var45, var23, var27);
        }
        else
        {
            var9.addVertexWithUV(var37, var41, var45, var29, var33);
            var9.addVertexWithUV(var37, var41, var43, var21, var25);
            var9.addVertexWithUV(var37, var39, var43, var31, var35);
            var9.addVertexWithUV(var37, var39, var45, var23, var27);
        }
    }

    /**
     * Renders the given texture to the south (x-positive) face of the block.  Args: block, x, y, z, texture
     */
    public void renderSouthFace(Block par1Block, double par2, double par4, double par6, int par8)
    {
        Tessellator var9 = Tessellator.instance;

        if (this.overrideBlockTexture >= 0)
        {
            par8 = this.overrideBlockTexture;
        }

        if (Config.isConnectedTextures() && this.overrideBlockTexture < 0 && this.uvRotateSouth == 0)
        {
            int var10 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 5, par8);

            if (var10 >= 0)
            {
                int var11 = var10 / 256;
                var9 = Tessellator.instance.getSubTessellator(var11);
                par8 = var10 % 256;
            }
        }

        boolean var48 = false;

        if (Config.isNaturalTextures() && this.overrideBlockTexture < 0 && this.uvRotateSouth == 0)
        {
            NaturalProperties var47 = NaturalTextures.getNaturalProperties(var9.textureID, par8);

            if (var47 != null)
            {
                int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 5);

                if (var47.rotation > 1)
                {
                    this.uvRotateSouth = var12 & 3;
                }

                if (var47.rotation == 2)
                {
                    this.uvRotateSouth = this.uvRotateSouth / 2 * 3;
                }

                if (var47.flip)
                {
                    this.flipTexture = (var12 & 4) != 0;
                }

                var48 = true;
            }
        }

        double var49 = this.renderMinZ;
        double var13 = this.renderMaxZ;
        double var15 = this.renderMinY;
        double var17 = this.renderMaxY;

        if (var49 < 0.0D || var13 > 1.0D)
        {
            var49 = 0.0D;
            var13 = 1.0D;
        }

        if (var15 < 0.0D || var17 > 1.0D)
        {
            var15 = 0.0D;
            var17 = 1.0D;
        }

        int var19 = (par8 & 15) << 4;
        int var20 = par8 & 240;
        double var21 = ((double)var19 + var49 * 16.0D) / 256.0D;
        double var23 = ((double)var19 + var13 * 16.0D - 0.01D) / 256.0D;
        double var25 = ((double)(var20 + 16) - var17 * 16.0D) / 256.0D;
        double var27 = ((double)(var20 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
        double var29;

        if (this.flipTexture)
        {
            var29 = var21;
            var21 = var23;
            var23 = var29;
        }

        var29 = var23;
        double var31 = var21;
        double var33 = var25;
        double var35 = var27;
        double var37;

        if (this.uvRotateSouth == 2)
        {
            var21 = ((double)var19 + var15 * 16.0D) / 256.0D;
            var25 = ((double)(var20 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)var19 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)(var20 + 16) - var13 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var33 = var25;
            var35 = var27;
            var29 = var21;
            var31 = var23;
            var25 = var27;
            var27 = var33;
        }
        else if (this.uvRotateSouth == 1)
        {
            var21 = ((double)(var19 + 16) - var17 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var13 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var15 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var49 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var21 = var23;
            var23 = var31;
            var33 = var27;
            var35 = var25;
        }
        else if (this.uvRotateSouth == 3)
        {
            var21 = ((double)(var19 + 16) - var49 * 16.0D - 0.01D) / 256.0D;
            var23 = ((double)(var19 + 16) - var13 * 16.0D) / 256.0D;
            var25 = ((double)var20 + var17 * 16.0D - 0.01D) / 256.0D;
            var27 = ((double)var20 + var15 * 16.0D) / 256.0D;

            if (this.flipTexture)
            {
                var37 = var21;
                var21 = var23;
                var23 = var37;
            }

            var29 = var23;
            var31 = var21;
            var33 = var25;
            var35 = var27;
        }

        if (var48)
        {
            this.uvRotateSouth = 0;
            this.flipTexture = false;
        }

        var37 = par2 + this.renderMaxX;
        double var39 = par4 + this.renderMinY;
        double var41 = par4 + this.renderMaxY;
        double var43 = par6 + this.renderMinZ;
        double var45 = par6 + this.renderMaxZ;

        if (this.enableAO)
        {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var37, var39, var45, var31, var35);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var37, var39, var43, var23, var27);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var37, var41, var43, var29, var33);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var37, var41, var45, var21, var25);
        }
        else
        {
            var9.addVertexWithUV(var37, var39, var45, var31, var35);
            var9.addVertexWithUV(var37, var39, var43, var23, var27);
            var9.addVertexWithUV(var37, var41, var43, var29, var33);
            var9.addVertexWithUV(var37, var41, var45, var21, var25);
        }
    }

    /**
     * Is called to render the image of a block on an inventory, as a held item, or as a an item on the ground
     */
    public void renderBlockAsItem(Block par1Block, int par2, float par3)
    {
        Tessellator var4 = Tessellator.instance;
        boolean var5 = par1Block.blockID == Block.grass.blockID;
        int var6;
        float var7;
        float var8;
        float var9;

        if (this.useInventoryTint)
        {
            var6 = par1Block.getRenderColor(par2);

            if (var5)
            {
                var6 = 16777215;
            }

            var7 = (float)(var6 >> 16 & 255) / 255.0F;
            var8 = (float)(var6 >> 8 & 255) / 255.0F;
            var9 = (float)(var6 & 255) / 255.0F;
            GL11.glColor4f(var7 * par3, var8 * par3, var9 * par3, 1.0F);
        }

        var6 = par1Block.getRenderType();
        this.setRenderBoundsFromBlock(par1Block);
        int var10;

        if (var6 != 0 && var6 != 31 && var6 != 16 && var6 != 26)
        {
            if (var6 == 1)
            {
                var4.startDrawingQuads();
                var4.setNormal(0.0F, -1.0F, 0.0F);
                this.drawCrossedSquares(par1Block, par2, -0.5D, -0.5D, -0.5D, 1.0F);
                var4.draw();
            }
            else if (var6 == 19)
            {
                var4.startDrawingQuads();
                var4.setNormal(0.0F, -1.0F, 0.0F);
                par1Block.setBlockBoundsForItemRender();
                this.renderBlockStemSmall(par1Block, par2, this.renderMaxY, -0.5D, -0.5D, -0.5D);
                var4.draw();
            }
            else if (var6 == 23)
            {
                var4.startDrawingQuads();
                var4.setNormal(0.0F, -1.0F, 0.0F);
                par1Block.setBlockBoundsForItemRender();
                var4.draw();
            }
            else if (var6 == 13)
            {
                par1Block.setBlockBoundsForItemRender();
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                var7 = 0.0625F;
                var4.startDrawingQuads();
                var4.setNormal(0.0F, -1.0F, 0.0F);
                this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(0));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0F, 1.0F, 0.0F);
                this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(1));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0F, 0.0F, -1.0F);
                var4.addTranslation(0.0F, 0.0F, var7);
                this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(2));
                var4.addTranslation(0.0F, 0.0F, -var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0F, 0.0F, 1.0F);
                var4.addTranslation(0.0F, 0.0F, -var7);
                this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(3));
                var4.addTranslation(0.0F, 0.0F, var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(-1.0F, 0.0F, 0.0F);
                var4.addTranslation(var7, 0.0F, 0.0F);
                this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(4));
                var4.addTranslation(-var7, 0.0F, 0.0F);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(1.0F, 0.0F, 0.0F);
                var4.addTranslation(-var7, 0.0F, 0.0F);
                this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(5));
                var4.addTranslation(var7, 0.0F, 0.0F);
                var4.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            else if (var6 == 22)
            {
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                ChestItemRenderHelper.instance.renderChest(par1Block, par2, par3);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            }
            else if (var6 == 6)
            {
                var4.startDrawingQuads();
                var4.setNormal(0.0F, -1.0F, 0.0F);
                this.renderBlockCropsImpl(par1Block, par2, -0.5D, -0.5D, -0.5D);
                var4.draw();
            }
            else if (var6 == 2)
            {
                var4.startDrawingQuads();
                var4.setNormal(0.0F, -1.0F, 0.0F);
                this.renderTorchAtAngle(par1Block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D);
                var4.draw();
            }
            else if (var6 == 10)
            {
                for (var10 = 0; var10 < 2; ++var10)
                {
                    if (var10 == 0)
                    {
                        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
                    }

                    if (var10 == 1)
                    {
                        this.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, -1.0F, 0.0F);
                    this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 1.0F, 0.0F);
                    this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, -1.0F);
                    this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, 1.0F);
                    this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0F, 0.0F, 0.0F);
                    this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0F, 0.0F, 0.0F);
                    this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(5));
                    var4.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            }
            else if (var6 == 27)
            {
                var10 = 0;
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                var4.startDrawingQuads();

                for (int var17 = 0; var17 < 8; ++var17)
                {
                    byte var12 = 0;
                    byte var13 = 1;

                    if (var17 == 0)
                    {
                        var12 = 2;
                    }

                    if (var17 == 1)
                    {
                        var12 = 3;
                    }

                    if (var17 == 2)
                    {
                        var12 = 4;
                    }

                    if (var17 == 3)
                    {
                        var12 = 5;
                        var13 = 2;
                    }

                    if (var17 == 4)
                    {
                        var12 = 6;
                        var13 = 3;
                    }

                    if (var17 == 5)
                    {
                        var12 = 7;
                        var13 = 5;
                    }

                    if (var17 == 6)
                    {
                        var12 = 6;
                        var13 = 2;
                    }

                    if (var17 == 7)
                    {
                        var12 = 3;
                    }

                    float var14 = (float)var12 / 16.0F;
                    float var15 = 1.0F - (float)var10 / 16.0F;
                    float var16 = 1.0F - (float)(var10 + var13) / 16.0F;
                    var10 += var13;
                    this.setRenderBounds((double)(0.5F - var14), (double)var16, (double)(0.5F - var14), (double)(0.5F + var14), (double)var15, (double)(0.5F + var14));
                    var4.setNormal(0.0F, -1.0F, 0.0F);
                    this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(0));
                    var4.setNormal(0.0F, 1.0F, 0.0F);
                    this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(1));
                    var4.setNormal(0.0F, 0.0F, -1.0F);
                    this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(2));
                    var4.setNormal(0.0F, 0.0F, 1.0F);
                    this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(3));
                    var4.setNormal(-1.0F, 0.0F, 0.0F);
                    this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(4));
                    var4.setNormal(1.0F, 0.0F, 0.0F);
                    this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(5));
                }

                var4.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (var6 == 11)
            {
                for (var10 = 0; var10 < 4; ++var10)
                {
                    var8 = 0.125F;

                    if (var10 == 0)
                    {
                        this.setRenderBounds((double)(0.5F - var8), 0.0D, 0.0D, (double)(0.5F + var8), 1.0D, (double)(var8 * 2.0F));
                    }

                    if (var10 == 1)
                    {
                        this.setRenderBounds((double)(0.5F - var8), 0.0D, (double)(1.0F - var8 * 2.0F), (double)(0.5F + var8), 1.0D, 1.0D);
                    }

                    var8 = 0.0625F;

                    if (var10 == 2)
                    {
                        this.setRenderBounds((double)(0.5F - var8), (double)(1.0F - var8 * 3.0F), (double)(-var8 * 2.0F), (double)(0.5F + var8), (double)(1.0F - var8), (double)(1.0F + var8 * 2.0F));
                    }

                    if (var10 == 3)
                    {
                        this.setRenderBounds((double)(0.5F - var8), (double)(0.5F - var8 * 3.0F), (double)(-var8 * 2.0F), (double)(0.5F + var8), (double)(0.5F - var8), (double)(1.0F + var8 * 2.0F));
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, -1.0F, 0.0F);
                    this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 1.0F, 0.0F);
                    this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, -1.0F);
                    this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, 1.0F);
                    this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0F, 0.0F, 0.0F);
                    this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0F, 0.0F, 0.0F);
                    this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(5));
                    var4.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (var6 == 21)
            {
                for (var10 = 0; var10 < 3; ++var10)
                {
                    var8 = 0.0625F;

                    if (var10 == 0)
                    {
                        this.setRenderBounds((double)(0.5F - var8), 0.30000001192092896D, 0.0D, (double)(0.5F + var8), 1.0D, (double)(var8 * 2.0F));
                    }

                    if (var10 == 1)
                    {
                        this.setRenderBounds((double)(0.5F - var8), 0.30000001192092896D, (double)(1.0F - var8 * 2.0F), (double)(0.5F + var8), 1.0D, 1.0D);
                    }

                    var8 = 0.0625F;

                    if (var10 == 2)
                    {
                        this.setRenderBounds((double)(0.5F - var8), 0.5D, 0.0D, (double)(0.5F + var8), (double)(1.0F - var8), 1.0D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, -1.0F, 0.0F);
                    this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 1.0F, 0.0F);
                    this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, -1.0F);
                    this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, 1.0F);
                    this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0F, 0.0F, 0.0F);
                    this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0F, 0.0F, 0.0F);
                    this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSide(5));
                    var4.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            }
            else if (var6 == 32)
            {
                for (var10 = 0; var10 < 2; ++var10)
                {
                    if (var10 == 0)
                    {
                        this.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                    }

                    if (var10 == 1)
                    {
                        this.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, -1.0F, 0.0F);
                    this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 1.0F, 0.0F);
                    this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, -1.0F);
                    this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, 1.0F);
                    this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0F, 0.0F, 0.0F);
                    this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0F, 0.0F, 0.0F);
                    this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, par2));
                    var4.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (var6 == 35)
            {
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                this.renderBlockAnvilOrient((BlockAnvil)par1Block, 0, 0, 0, par2, true);
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            else if (var6 == 34)
            {
                for (var10 = 0; var10 < 3; ++var10)
                {
                    if (var10 == 0)
                    {
                        this.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
                        this.setOverrideBlockTexture(Block.obsidian.blockIndexInTexture);
                    }
                    else if (var10 == 1)
                    {
                        this.setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
                        this.setOverrideBlockTexture(41);
                    }
                    else if (var10 == 2)
                    {
                        this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                        this.setOverrideBlockTexture(Block.glass.blockIndexInTexture);
                    }

                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, -1.0F, 0.0F);
                    this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 1.0F, 0.0F);
                    this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, -1.0F);
                    this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0F, 0.0F, 1.0F);
                    this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0F, 0.0F, 0.0F);
                    this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, par2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0F, 0.0F, 0.0F);
                    this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, par2));
                    var4.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }

                this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                this.clearOverrideBlockTexture();
            }
            else if (Reflector.ModLoader.exists())
            {
                Reflector.callVoid(Reflector.ModLoader_renderInvBlock, new Object[] {this, par1Block, Integer.valueOf(par2), Integer.valueOf(var6)});
            }
        }
        else
        {
            if (var6 == 16)
            {
                par2 = 1;
            }

            par1Block.setBlockBoundsForItemRender();
            this.setRenderBoundsFromBlock(par1Block);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, par2));
            var4.draw();

            if (var5 && this.useInventoryTint)
            {
                var10 = par1Block.getRenderColor(par2);
                var8 = (float)(var10 >> 16 & 255) / 255.0F;
                var9 = (float)(var10 >> 8 & 255) / 255.0F;
                float var11 = (float)(var10 & 255) / 255.0F;
                GL11.glColor4f(var8 * par3, var9 * par3, var11 * par3, 1.0F);
            }

            var4.startDrawingQuads();
            var4.setNormal(0.0F, 1.0F, 0.0F);
            this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, par2));
            var4.draw();

            if (var5 && this.useInventoryTint)
            {
                GL11.glColor4f(par3, par3, par3, 1.0F);
            }

            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, -1.0F);
            this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, 1.0F);
            this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0F, 0.0F, 0.0F);
            this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0F, 0.0F, 0.0F);
            this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, par2));
            var4.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }

    /**
     * Checks to see if the item's render type indicates that it should be rendered as a regular block or not.
     */
    public static boolean renderItemIn3d(int par0)
    {
        switch (par0)
        {
            case 0:
                return true;

            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 12:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 33:
            default:
                if (Reflector.ModLoader.exists())
                {
                    return Reflector.callBoolean(Reflector.ModLoader_renderBlockIsItemFull3D, new Object[] {Integer.valueOf(par0)});
                }

                return false;

            case 10:
                return true;

            case 11:
                return true;

            case 13:
                return true;

            case 16:
                return true;

            case 21:
                return true;

            case 22:
                return true;

            case 26:
                return true;

            case 27:
                return true;

            case 31:
                return true;

            case 32:
                return true;

            case 34:
                return true;

            case 35:
                return true;
        }
    }

    /**
     * Renders the block at the given coordinates using the block's rendering type
     */
    public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4)
    {
        int var5 = par1Block.getRenderType();
        par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);

        if (Config.isBetterSnow() && par1Block == Block.signPost && this.hasSnowNeighbours(par2, par3, par4))
        {
            this.renderSnow(par2, par3, par4, Block.snow.maxY);
        }

        this.setRenderBoundsFromBlock(par1Block);

        switch (var5)
        {
            case 0:
                return this.renderStandardBlock(par1Block, par2, par3, par4);

            case 1:
                return this.renderCrossedSquares(par1Block, par2, par3, par4);

            case 2:
                return this.renderBlockTorch(par1Block, par2, par3, par4);

            case 3:
                return this.renderBlockFire(par1Block, par2, par3, par4);

            case 4:
                return this.renderBlockFluids(par1Block, par2, par3, par4);

            case 5:
                return this.renderBlockRedstoneWire(par1Block, par2, par3, par4);

            case 6:
                return this.renderBlockCrops(par1Block, par2, par3, par4);

            case 7:
                return this.renderBlockDoor(par1Block, par2, par3, par4);

            case 8:
                return this.renderBlockLadder(par1Block, par2, par3, par4);

            case 9:
                return this.renderBlockMinecartTrack((BlockRail)par1Block, par2, par3, par4);

            case 10:
                return this.renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);

            case 11:
                return this.renderBlockFence((BlockFence)par1Block, par2, par3, par4);

            case 12:
                return this.renderBlockLever(par1Block, par2, par3, par4);

            case 13:
                return this.renderBlockCactus(par1Block, par2, par3, par4);

            case 14:
                return this.renderBlockBed(par1Block, par2, par3, par4);

            case 15:
                return this.renderBlockRepeater(par1Block, par2, par3, par4);

            case 16:
                return this.renderPistonBase(par1Block, par2, par3, par4, false);

            case 17:
                return this.renderPistonExtension(par1Block, par2, par3, par4, true);

            case 18:
                return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);

            case 19:
                return this.renderBlockStem(par1Block, par2, par3, par4);

            case 20:
                return this.renderBlockVine(par1Block, par2, par3, par4);

            case 21:
                return this.renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);

            case 22:
            default:
                if (Reflector.ModLoader.exists())
                {
                    return Reflector.callBoolean(Reflector.ModLoader_renderWorldBlock, new Object[] {this, this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4), par1Block, Integer.valueOf(var5)});
                }

                return false;

            case 23:
                return this.renderBlockLilyPad(par1Block, par2, par3, par4);

            case 24:
                return this.renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);

            case 25:
                return this.renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);

            case 26:
                return this.renderBlockEndPortalFrame(par1Block, par2, par3, par4);

            case 27:
                return this.renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);

            case 28:
                return this.renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);

            case 29:
                return this.renderBlockTripWireSource(par1Block, par2, par3, par4);

            case 30:
                return this.renderBlockTripWire(par1Block, par2, par3, par4);

            case 31:
                return this.renderBlockLog(par1Block, par2, par3, par4);

            case 32:
                return this.renderBlockWall((BlockWall)par1Block, par2, par3, par4);

            case 33:
                return this.renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);

            case 34:
                return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);

            case 35:
                return this.renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
        }
    }

    private void calculateAoLightValues(Block var1, int var2, int var3, int var4)
    {
        this.aoLightValueXNeg = this.getAmbientOcclusionLightValue(this.blockAccess, var2 - 1, var3, var4);
        this.aoLightValueYNeg = this.getAmbientOcclusionLightValue(this.blockAccess, var2, var3 - 1, var4);
        this.aoLightValueZNeg = this.getAmbientOcclusionLightValue(this.blockAccess, var2, var3, var4 - 1);
        this.aoLightValueXPos = this.getAmbientOcclusionLightValue(this.blockAccess, var2 + 1, var3, var4);
        this.aoLightValueYPos = this.getAmbientOcclusionLightValue(this.blockAccess, var2, var3 + 1, var4);
        this.aoLightValueZPos = this.getAmbientOcclusionLightValue(this.blockAccess, var2, var3, var4 + 1);
        this.aoGrassXYZPPC = Block.canBlockGrass[this.blockAccess.getBlockId(var2 + 1, var3 + 1, var4)];
        this.aoGrassXYZPNC = Block.canBlockGrass[this.blockAccess.getBlockId(var2 + 1, var3 - 1, var4)];
        this.aoGrassXYZPCP = Block.canBlockGrass[this.blockAccess.getBlockId(var2 + 1, var3, var4 + 1)];
        this.aoGrassXYZPCN = Block.canBlockGrass[this.blockAccess.getBlockId(var2 + 1, var3, var4 - 1)];
        this.aoGrassXYZNPC = Block.canBlockGrass[this.blockAccess.getBlockId(var2 - 1, var3 + 1, var4)];
        this.aoGrassXYZNNC = Block.canBlockGrass[this.blockAccess.getBlockId(var2 - 1, var3 - 1, var4)];
        this.aoGrassXYZNCN = Block.canBlockGrass[this.blockAccess.getBlockId(var2 - 1, var3, var4 - 1)];
        this.aoGrassXYZNCP = Block.canBlockGrass[this.blockAccess.getBlockId(var2 - 1, var3, var4 + 1)];
        this.aoGrassXYZCPP = Block.canBlockGrass[this.blockAccess.getBlockId(var2, var3 + 1, var4 + 1)];
        this.aoGrassXYZCPN = Block.canBlockGrass[this.blockAccess.getBlockId(var2, var3 + 1, var4 - 1)];
        this.aoGrassXYZCNP = Block.canBlockGrass[this.blockAccess.getBlockId(var2, var3 - 1, var4 + 1)];
        this.aoGrassXYZCNN = Block.canBlockGrass[this.blockAccess.getBlockId(var2, var3 - 1, var4 - 1)];
        this.aoLightValuesCalculated = true;
    }

    private float getAmbientOcclusionLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
        Block var5 = Block.blocksList[var1.getBlockId(var2, var3, var4)];
        return var5 == null ? 1.0F : (var5.getClass() == BlockGlass.class ? 1.0F : (var5.blockMaterial.blocksMovement() && var5.renderAsNormalBlock() ? this.aoLightValueOpaque : 1.0F));
    }

    private int fixAoSideGrassTexture(int var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        if (var1 == 3 || var1 == 77)
        {
            var1 = Config.getSideGrassTexture(this.blockAccess, var2, var3, var4, var5, var1);

            if (var1 == 0)
            {
                this.colorRedTopLeft *= var6;
                this.colorRedBottomLeft *= var6;
                this.colorRedBottomRight *= var6;
                this.colorRedTopRight *= var6;
                this.colorGreenTopLeft *= var7;
                this.colorGreenBottomLeft *= var7;
                this.colorGreenBottomRight *= var7;
                this.colorGreenTopRight *= var7;
                this.colorBlueTopLeft *= var8;
                this.colorBlueBottomLeft *= var8;
                this.colorBlueBottomRight *= var8;
                this.colorBlueTopRight *= var8;
            }
        }

        if (var1 == 68)
        {
            var1 = Config.getSideSnowGrassTexture(this.blockAccess, var2, var3, var4, var5);
        }

        return var1;
    }

    private boolean hasSnowNeighbours(int var1, int var2, int var3)
    {
        int var4 = Block.snow.blockID;
        return this.blockAccess.getBlockId(var1 - 1, var2, var3) != var4 && this.blockAccess.getBlockId(var1 + 1, var2, var3) != var4 && this.blockAccess.getBlockId(var1, var2, var3 - 1) != var4 && this.blockAccess.getBlockId(var1, var2, var3 + 1) != var4 ? false : this.blockAccess.isBlockOpaqueCube(var1, var2 - 1, var3);
    }

    private void renderSnow(int var1, int var2, int var3, double var4)
    {
        this.setRenderBoundsFromBlock(Block.snow);
        this.renderMaxY = var4;
        this.renderStandardBlock(Block.snow, var1, var2, var3);
    }

    private int getReverseGlassPaneTexture(int var1)
    {
        int var2 = var1 % 16;
        return var2 == 1 ? var1 + 2 : (var2 == 3 ? var1 - 2 : var1);
    }

    private int getGlassPaneTexture(boolean var1, boolean var2, boolean var3, boolean var4)
    {
        return var2 && var1 ? (var3 ? (var4 ? 34 : 50) : (var4 ? 18 : 2)) : (var2 && !var1 ? (var3 ? (var4 ? 35 : 51) : (var4 ? 19 : 3)) : (!var2 && var1 ? (var3 ? (var4 ? 33 : 49) : (var4 ? 17 : 1)) : (var3 ? (var4 ? 32 : 48) : (var4 ? 16 : 0))));
    }

    static
    {
        for (int var0 = 0; var0 < redstoneColors.length; ++var0)
        {
            float var1 = (float)var0 / 15.0F;
            float var2 = var1 * 0.6F + 0.4F;

            if (var0 == 0)
            {
                var1 = 0.0F;
            }

            float var3 = var1 * var1 * 0.7F - 0.5F;
            float var4 = var1 * var1 * 0.6F - 0.7F;

            if (var3 < 0.0F)
            {
                var3 = 0.0F;
            }

            if (var4 < 0.0F)
            {
                var4 = 0.0F;
            }

            redstoneColors[var0] = new float[] {var2, var3, var4};
        }
    }
}
