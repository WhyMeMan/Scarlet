package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashSet;
import java.util.Set;

public class TextureUtils
{
    public static final String texGrassTop = "grass_top";
    public static final String texStone = "stone";
    public static final String texDirt = "dirt";
    public static final String texGrassSide = "grass_side";
    public static final String texStoneslabSide = "stoneslab_side";
    public static final String texStoneslabTop = "stoneslab_top";
    public static final String texBedrock = "bedrock";
    public static final String texSand = "sand";
    public static final String texGravel = "gravel";
    public static final String texTreeSide = "tree_side";
    public static final String texTreeTop = "tree_top";
    public static final String texOreGold = "oreGold";
    public static final String texOreIron = "oreIron";
    public static final String texOreCoal = "oreCoal";
    public static final String texObsidian = "obsidian";
    public static final String texGrassSideOverlay = "grass_side_overlay";
    public static final String texSnow = "snow";
    public static final String texSnowSide = "snow_side";
    public static final String texMycelSide = "mycel_side";
    public static final String texMycelTop = "mycel_top";
    public static final String texOreDiamond = "oreDiamond";
    public static final String texOreRedstone = "oreRedstone";
    public static final String texOreLapis = "oreLapis";
    public static final String texLeaves = "leaves";
    public static final String texLeavesOpaque = "leaves_opaque";
    public static final String texLeavesJungle = "leaves_jungle";
    public static final String texLeavesJungleOpaque = "leaves_jungle_opaque";
    public static final String texCactusSide = "cactus_side";
    public static final String texClay = "clay";
    public static final String texFarmlandWet = "farmland_wet";
    public static final String texFarmlandDry = "farmland_dry";
    public static final String texHellrock = "hellrock";
    public static final String texHellsand = "hellsand";
    public static final String texLightgem = "lightgem";
    public static final String texTreeSpruce = "tree_spruce";
    public static final String texTreeBirch = "tree_birch";
    public static final String texLeavesSpruce = "leaves_spruce";
    public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
    public static final String texTreeJungle = "tree_jungle";
    public static final String texWhiteStone = "whiteStone";
    public static final String texSandstoneTop = "sandstone_top";
    public static final String texSandstoneBottom = "sandstone_bottom";
    public static final String texRedstoneLight = "redstoneLight";
    public static final String texRedstoneLightLit = "redstoneLight_lit";
    public static final String texWater = "water";
    public static final String texWaterFlow = "water_flow";
    public static final String texLava = "lava";
    public static final String texLavaFlow = "lava_flow";
    public static final String texFire0 = "fire_0";
    public static final String texFire1 = "fire_1";
    public static final String texPortal = "portal";
    public static Icon iconGrassTop;
    public static Icon iconGrassSide;
    public static Icon iconGrassSideOverlay;
    public static Icon iconSnow;
    public static Icon iconSnowSide;
    public static Icon iconMycelSide;
    public static Icon iconMycelTop;
    public static Icon iconWater;
    public static Icon iconWaterFlow;
    public static Icon iconLava;
    public static Icon iconLavaFlow;
    public static Icon iconPortal;
    public static Icon iconFire0;
    public static Icon iconFire1;
    private static Set atlasNames = makeAtlasNames();
    private static Set atlasIds = new HashSet();

    private static Set makeAtlasNames()
    {
        HashSet var0 = new HashSet();
        var0.add("/terrain.png");
        var0.add("/gui/items.png");
        var0.add("/ctm.png");
        var0.add("/eloraam/world/world1.png");
        var0.add("/gfx/buildcraft/blocks/blocks.png");
        return var0;
    }

    public static void update(RenderEngine var0)
    {
        TextureMap var1 = var0.textureMapBlocks;
        iconGrassTop = var1.registerIcon("grass_top");
        iconGrassSide = var1.registerIcon("grass_side");
        iconGrassSideOverlay = var1.registerIcon("grass_side_overlay");
        iconSnow = var1.registerIcon("snow");
        iconSnowSide = var1.registerIcon("snow_side");
        iconMycelSide = var1.registerIcon("mycel_side");
        iconMycelTop = var1.registerIcon("mycel_top");
        iconWater = var1.registerIcon("water");
        iconWaterFlow = var1.registerIcon("water_flow");
        iconLava = var1.registerIcon("lava");
        iconLavaFlow = var1.registerIcon("lava_flow");
        iconFire0 = var1.registerIcon("fire_0");
        iconFire1 = var1.registerIcon("fire_1");
        iconPortal = var1.registerIcon("portal");
    }

    public static void textureCreated(String var0, int var1)
    {
        if (atlasNames.contains(var0))
        {
            atlasIds.add(Integer.valueOf(var1));
        }
    }

    public static void addAtlasName(String var0)
    {
        if (var0 != null)
        {
            atlasNames.add(var0);
            Config.dbg("TextureAtlas: " + var0);
        }
    }

    public static boolean isAtlasId(int var0)
    {
        return atlasIds.contains(Integer.valueOf(var0));
    }

    public static boolean isAtlasName(String var0)
    {
        return atlasNames.contains(var0);
    }

    public static BufferedImage fixTextureDimensions(String var0, BufferedImage var1)
    {
        if (var0.startsWith("/mob/zombie") || var0.startsWith("/mob/pigzombie"))
        {
            int var2 = var1.getWidth();
            int var3 = var1.getHeight();

            if (var2 == var3 * 2)
            {
                BufferedImage var4 = new BufferedImage(var2, var3 * 2, 2);
                Graphics2D var5 = var4.createGraphics();
                var5.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                var5.drawImage(var1, 0, 0, var2, var3, (ImageObserver)null);
                return var4;
            }
        }

        return var1;
    }

    public static TextureStitched getTextureStitched(Icon var0)
    {
        return var0 instanceof TextureStitched ? (TextureStitched)var0 : null;
    }

    public static int ceilPowerOfTwo(int var0)
    {
        int var1;

        for (var1 = 1; var1 < var0; var1 *= 2)
        {
            ;
        }

        return var1;
    }

    public static int getPowerOfTwo(int var0)
    {
        int var1 = 1;
        int var2;

        for (var2 = 0; var1 < var0; ++var2)
        {
            var1 *= 2;
        }

        return var2;
    }

    public static int twoToPower(int var0)
    {
        int var1 = 1;

        for (int var2 = 0; var2 < var0; ++var2)
        {
            var1 *= 2;
        }

        return var1;
    }
}
