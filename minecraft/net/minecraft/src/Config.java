package net.minecraft.src;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Config$1;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class Config
{
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.5.2";
    public static final String OF_EDITION = "HD_U";
    public static final String OF_RELEASE = "D3";
    public static final String VERSION = "OptiFine_1.5.2_HD_U_D3";
    private static String newRelease = null;
    private static GameSettings gameSettings = null;
    private static Minecraft minecraft = null;
    private static Thread minecraftThread = null;
    private static DisplayMode desktopDisplayMode = null;
    private static int antialiasingLevel = 0;
    private static int availableProcessors = 0;
    public static boolean zoomMode = false;
    private static int texturePackClouds = 0;
    private static PrintStream systemOut = new PrintStream(new FileOutputStream(FileDescriptor.out));
    public static final Boolean DEF_FOG_FANCY = Boolean.valueOf(true);
    public static final Float DEF_FOG_START = Float.valueOf(0.2F);
    public static final Boolean DEF_OPTIMIZE_RENDER_DISTANCE = Boolean.valueOf(false);
    public static final Boolean DEF_OCCLUSION_ENABLED = Boolean.valueOf(false);
    public static final Integer DEF_MIPMAP_LEVEL = Integer.valueOf(0);
    public static final Integer DEF_MIPMAP_TYPE = Integer.valueOf(9984);
    public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
    public static final Boolean DEF_LOAD_CHUNKS_FAR = Boolean.valueOf(false);
    public static final Integer DEF_PRELOADED_CHUNKS = Integer.valueOf(0);
    public static final Integer DEF_CHUNKS_LIMIT = Integer.valueOf(25);
    public static final Integer DEF_UPDATES_PER_FRAME = Integer.valueOf(3);
    public static final Boolean DEF_DYNAMIC_UPDATES = Boolean.valueOf(false);

    public static String getVersion()
    {
        return "OptiFine_1.5.2_HD_U_D3";
    }

    private static void checkOpenGlCaps()
    {
        log("");
        log(getVersion());
        log("" + new Date());
        log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        log("LWJGL: " + Sys.getVersion());
        log("OpenGL: " + GL11.glGetString(GL11.GL_RENDERER) + " version " + GL11.glGetString(GL11.GL_VERSION) + ", " + GL11.glGetString(GL11.GL_VENDOR));
        int var0 = getOpenGlVersion();
        String var1 = "" + var0 / 10 + "." + var0 % 10;
        log("OpenGL Version: " + var1);

        if (!GLContext.getCapabilities().OpenGL12)
        {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }

        if (!GLContext.getCapabilities().GL_NV_fog_distance)
        {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }

        if (!GLContext.getCapabilities().GL_ARB_occlusion_query)
        {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }

        int var2 = Minecraft.getGLMaximumTextureSize();
        dbg("Maximum texture size: " + var2 + "x" + var2);
    }

    public static boolean isFancyFogAvailable()
    {
        return GLContext.getCapabilities().GL_NV_fog_distance;
    }

    public static boolean isOcclusionAvailable()
    {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }

    private static int getOpenGlVersion()
    {
        return !GLContext.getCapabilities().OpenGL11 ? 10 : (!GLContext.getCapabilities().OpenGL12 ? 11 : (!GLContext.getCapabilities().OpenGL13 ? 12 : (!GLContext.getCapabilities().OpenGL14 ? 13 : (!GLContext.getCapabilities().OpenGL15 ? 14 : (!GLContext.getCapabilities().OpenGL20 ? 15 : (!GLContext.getCapabilities().OpenGL21 ? 20 : (!GLContext.getCapabilities().OpenGL30 ? 21 : (!GLContext.getCapabilities().OpenGL31 ? 30 : (!GLContext.getCapabilities().OpenGL32 ? 31 : (!GLContext.getCapabilities().OpenGL33 ? 32 : (!GLContext.getCapabilities().OpenGL40 ? 33 : 40)))))))))));
    }

    public static void setGameSettings(GameSettings var0)
    {
        if (gameSettings == null)
        {
            if (!Display.isCreated())
            {
                return;
            }

            checkOpenGlCaps();
            startVersionCheckThread();
        }

        gameSettings = var0;
        minecraft = gameSettings.mc;
        minecraftThread = Thread.currentThread();

        if (gameSettings != null)
        {
            antialiasingLevel = gameSettings.ofAaLevel;
        }

        updateThreadPriorities();
    }

    public static void updateThreadPriorities()
    {
        try
        {
            ThreadGroup var0 = Thread.currentThread().getThreadGroup();

            if (var0 == null)
            {
                return;
            }

            int var1 = (var0.activeCount() + 10) * 2;
            Thread[] var2 = new Thread[var1];
            var0.enumerate(var2, false);
            byte var3 = 5;
            byte var4 = 5;

            if (isSmoothWorld())
            {
                var4 = 3;
            }

            minecraftThread.setPriority(var3);

            for (int var5 = 0; var5 < var2.length; ++var5)
            {
                Thread var6 = var2[var5];

                if (var6 != null && var6 instanceof ThreadMinecraftServer)
                {
                    var6.setPriority(var4);
                }
            }
        }
        catch (Throwable var7)
        {
            dbg(var7.getMessage());
        }
    }

    public static boolean isMinecraftThread()
    {
        return Thread.currentThread() == minecraftThread;
    }

    private static void startVersionCheckThread()
    {
        VersionCheckThread var0 = new VersionCheckThread();
        var0.start();
    }

    public static boolean isUseMipmaps()
    {
        int var0 = getMipmapLevel();
        return var0 > 0;
    }

    public static int getMipmapLevel()
    {
        return gameSettings == null ? DEF_MIPMAP_LEVEL.intValue() : gameSettings.ofMipmapLevel;
    }

    public static int getMipmapType()
    {
        if (gameSettings == null)
        {
            return DEF_MIPMAP_TYPE.intValue();
        }
        else
        {
            switch (gameSettings.ofMipmapType)
            {
                case 0:
                    return 9984;

                case 1:
                    return 9986;

                case 2:
                    if (isMultiTexture())
                    {
                        return 9985;
                    }

                    return 9986;

                case 3:
                    if (isMultiTexture())
                    {
                        return 9987;
                    }

                    return 9986;

                default:
                    return 9984;
            }
        }
    }

    public static boolean isUseAlphaFunc()
    {
        float var0 = getAlphaFuncLevel();
        return var0 > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F;
    }

    public static float getAlphaFuncLevel()
    {
        return DEF_ALPHA_FUNC_LEVEL.floatValue();
    }

    public static boolean isFogFancy()
    {
        return !isFancyFogAvailable() ? false : (gameSettings == null ? false : gameSettings.ofFogType == 2);
    }

    public static boolean isFogFast()
    {
        return gameSettings == null ? false : gameSettings.ofFogType == 1;
    }

    public static boolean isFogOff()
    {
        return gameSettings == null ? false : gameSettings.ofFogType == 3;
    }

    public static float getFogStart()
    {
        return gameSettings == null ? DEF_FOG_START.floatValue() : gameSettings.ofFogStart;
    }

    public static boolean isOcclusionEnabled()
    {
        return gameSettings == null ? DEF_OCCLUSION_ENABLED.booleanValue() : gameSettings.advancedOpengl;
    }

    public static boolean isOcclusionFancy()
    {
        return !isOcclusionEnabled() ? false : (gameSettings == null ? false : gameSettings.ofOcclusionFancy);
    }

    public static boolean isLoadChunksFar()
    {
        return gameSettings == null ? DEF_LOAD_CHUNKS_FAR.booleanValue() : gameSettings.ofLoadFar;
    }

    public static int getPreloadedChunks()
    {
        return gameSettings == null ? DEF_PRELOADED_CHUNKS.intValue() : gameSettings.ofPreloadedChunks;
    }

    public static void dbg(String var0)
    {
        systemOut.print("[OptiFine] ");
        systemOut.println(var0);
    }

    public static void log(String var0)
    {
        dbg(var0);
    }

    public static int getUpdatesPerFrame()
    {
        return gameSettings != null ? gameSettings.ofChunkUpdates : 1;
    }

    public static boolean isDynamicUpdates()
    {
        return gameSettings != null ? gameSettings.ofChunkUpdatesDynamic : true;
    }

    public static boolean isRainFancy()
    {
        return gameSettings.ofRain == 0 ? gameSettings.fancyGraphics : gameSettings.ofRain == 2;
    }

    public static boolean isWaterFancy()
    {
        return gameSettings.ofWater == 0 ? gameSettings.fancyGraphics : gameSettings.ofWater == 2;
    }

    public static boolean isRainOff()
    {
        return gameSettings.ofRain == 3;
    }

    public static boolean isCloudsFancy()
    {
        return gameSettings.ofClouds != 0 ? gameSettings.ofClouds == 2 : (texturePackClouds != 0 ? texturePackClouds == 2 : gameSettings.fancyGraphics);
    }

    public static boolean isCloudsOff()
    {
        return gameSettings.ofClouds == 3;
    }

    public static void updateTexturePackClouds()
    {
        texturePackClouds = 0;
        RenderEngine var0 = getRenderEngine();

        if (var0 != null)
        {
            ITexturePack var1 = var0.getTexturePack().getSelectedTexturePack();

            if (var1 != null)
            {
                try
                {
                    InputStream var2 = var1.getResourceAsStream("/color.properties");

                    if (var2 == null)
                    {
                        return;
                    }

                    Properties var3 = new Properties();
                    var3.load(var2);
                    var2.close();
                    String var4 = var3.getProperty("clouds");

                    if (var4 == null)
                    {
                        return;
                    }

                    dbg("Texture pack clouds: " + var4);
                    var4 = var4.toLowerCase();

                    if (var4.equals("fast"))
                    {
                        texturePackClouds = 1;
                    }

                    if (var4.equals("fancy"))
                    {
                        texturePackClouds = 2;
                    }
                }
                catch (Exception var5)
                {
                    ;
                }
            }
        }
    }

    public static boolean isTreesFancy()
    {
        return gameSettings.ofTrees == 0 ? gameSettings.fancyGraphics : gameSettings.ofTrees == 2;
    }

    public static boolean isGrassFancy()
    {
        return gameSettings.ofGrass == 0 ? gameSettings.fancyGraphics : gameSettings.ofGrass == 2;
    }

    public static boolean isDroppedItemsFancy()
    {
        return gameSettings.ofDroppedItems == 0 ? gameSettings.fancyGraphics : gameSettings.ofDroppedItems == 2;
    }

    public static int limit(int var0, int var1, int var2)
    {
        return var0 < var1 ? var1 : (var0 > var2 ? var2 : var0);
    }

    public static float limit(float var0, float var1, float var2)
    {
        return var0 < var1 ? var1 : (var0 > var2 ? var2 : var0);
    }

    public static float limitTo1(float var0)
    {
        return var0 < 0.0F ? 0.0F : (var0 > 1.0F ? 1.0F : var0);
    }

    public static boolean isAnimatedWater()
    {
        return gameSettings != null ? gameSettings.ofAnimatedWater != 2 : true;
    }

    public static boolean isGeneratedWater()
    {
        return gameSettings != null ? gameSettings.ofAnimatedWater == 1 : true;
    }

    public static boolean isAnimatedPortal()
    {
        return gameSettings != null ? gameSettings.ofAnimatedPortal : true;
    }

    public static boolean isAnimatedLava()
    {
        return gameSettings != null ? gameSettings.ofAnimatedLava != 2 : true;
    }

    public static boolean isGeneratedLava()
    {
        return gameSettings != null ? gameSettings.ofAnimatedLava == 1 : true;
    }

    public static boolean isAnimatedFire()
    {
        return gameSettings != null ? gameSettings.ofAnimatedFire : true;
    }

    public static boolean isAnimatedRedstone()
    {
        return gameSettings != null ? gameSettings.ofAnimatedRedstone : true;
    }

    public static boolean isAnimatedExplosion()
    {
        return gameSettings != null ? gameSettings.ofAnimatedExplosion : true;
    }

    public static boolean isAnimatedFlame()
    {
        return gameSettings != null ? gameSettings.ofAnimatedFlame : true;
    }

    public static boolean isAnimatedSmoke()
    {
        return gameSettings != null ? gameSettings.ofAnimatedSmoke : true;
    }

    public static boolean isVoidParticles()
    {
        return gameSettings != null ? gameSettings.ofVoidParticles : true;
    }

    public static boolean isWaterParticles()
    {
        return gameSettings != null ? gameSettings.ofWaterParticles : true;
    }

    public static boolean isRainSplash()
    {
        return gameSettings != null ? gameSettings.ofRainSplash : true;
    }

    public static boolean isPortalParticles()
    {
        return gameSettings != null ? gameSettings.ofPortalParticles : true;
    }

    public static boolean isPotionParticles()
    {
        return gameSettings != null ? gameSettings.ofPotionParticles : true;
    }

    public static boolean isDepthFog()
    {
        return gameSettings != null ? gameSettings.ofDepthFog : true;
    }

    public static float getAmbientOcclusionLevel()
    {
        return gameSettings != null ? gameSettings.ofAoLevel : 0.0F;
    }

    private static Method getMethod(Class var0, String var1, Object[] var2)
    {
        Method[] var3 = var0.getMethods();

        for (int var4 = 0; var4 < var3.length; ++var4)
        {
            Method var5 = var3[var4];

            if (var5.getName().equals(var1) && var5.getParameterTypes().length == var2.length)
            {
                return var5;
            }
        }

        dbg("No method found for: " + var0.getName() + "." + var1 + "(" + arrayToString(var2) + ")");
        return null;
    }

    public static String arrayToString(Object[] var0)
    {
        if (var0 == null)
        {
            return "";
        }
        else
        {
            StringBuffer var1 = new StringBuffer(var0.length * 5);

            for (int var2 = 0; var2 < var0.length; ++var2)
            {
                Object var3 = var0[var2];

                if (var2 > 0)
                {
                    var1.append(", ");
                }

                var1.append(String.valueOf(var3));
            }

            return var1.toString();
        }
    }

    public static String arrayToString(int[] var0)
    {
        if (var0 == null)
        {
            return "";
        }
        else
        {
            StringBuffer var1 = new StringBuffer(var0.length * 5);

            for (int var2 = 0; var2 < var0.length; ++var2)
            {
                int var3 = var0[var2];

                if (var2 > 0)
                {
                    var1.append(", ");
                }

                var1.append(String.valueOf(var3));
            }

            return var1.toString();
        }
    }

    public static Minecraft getMinecraft()
    {
        return minecraft;
    }

    public static RenderEngine getRenderEngine()
    {
        return minecraft.renderEngine;
    }

    public static RenderGlobal getRenderGlobal()
    {
        return minecraft == null ? null : minecraft.renderGlobal;
    }

    public static int getMaxDynamicTileWidth()
    {
        return 64;
    }

    public static Icon getSideGrassTexture(IBlockAccess var0, int var1, int var2, int var3, int var4, Icon var5)
    {
        if (!isBetterGrass())
        {
            return var5;
        }
        else
        {
            Icon var6 = TextureUtils.iconGrassTop;
            byte var7 = 2;

            if (var5 == TextureUtils.iconMycelSide)
            {
                var6 = TextureUtils.iconMycelTop;
                var7 = 110;
            }

            if (isBetterGrassFancy())
            {
                --var2;

                switch (var4)
                {
                    case 2:
                        --var3;
                        break;

                    case 3:
                        ++var3;
                        break;

                    case 4:
                        --var1;
                        break;

                    case 5:
                        ++var1;
                }

                int var8 = var0.getBlockId(var1, var2, var3);

                if (var8 != var7)
                {
                    return var5;
                }
            }

            return var6;
        }
    }

    public static Icon getSideSnowGrassTexture(IBlockAccess var0, int var1, int var2, int var3, int var4)
    {
        if (!isBetterGrass())
        {
            return TextureUtils.iconSnowSide;
        }
        else
        {
            if (isBetterGrassFancy())
            {
                switch (var4)
                {
                    case 2:
                        --var3;
                        break;

                    case 3:
                        ++var3;
                        break;

                    case 4:
                        --var1;
                        break;

                    case 5:
                        ++var1;
                }

                int var5 = var0.getBlockId(var1, var2, var3);

                if (var5 != 78 && var5 != 80)
                {
                    return TextureUtils.iconSnowSide;
                }
            }

            return TextureUtils.iconSnow;
        }
    }

    public static boolean isBetterGrass()
    {
        return gameSettings == null ? false : gameSettings.ofBetterGrass != 3;
    }

    public static boolean isBetterGrassFancy()
    {
        return gameSettings == null ? false : gameSettings.ofBetterGrass == 2;
    }

    public static boolean isWeatherEnabled()
    {
        return gameSettings == null ? true : gameSettings.ofWeather;
    }

    public static boolean isSkyEnabled()
    {
        return gameSettings == null ? true : gameSettings.ofSky;
    }

    public static boolean isSunMoonEnabled()
    {
        return gameSettings == null ? true : gameSettings.ofSunMoon;
    }

    public static boolean isStarsEnabled()
    {
        return gameSettings == null ? true : gameSettings.ofStars;
    }

    public static void sleep(long var0)
    {
        try
        {
            Thread.currentThread();
            Thread.sleep(var0);
        }
        catch (InterruptedException var3)
        {
            var3.printStackTrace();
        }
    }

    public static boolean isTimeDayOnly()
    {
        return gameSettings == null ? false : gameSettings.ofTime == 1;
    }

    public static boolean isTimeDefault()
    {
        return gameSettings == null ? false : gameSettings.ofTime == 0 || gameSettings.ofTime == 2;
    }

    public static boolean isTimeNightOnly()
    {
        return gameSettings == null ? false : gameSettings.ofTime == 3;
    }

    public static boolean isClearWater()
    {
        return gameSettings == null ? false : gameSettings.ofClearWater;
    }

    public static int getAnisotropicFilterLevel()
    {
        return gameSettings == null ? 1 : gameSettings.ofAfLevel;
    }

    public static int getAntialiasingLevel()
    {
        return antialiasingLevel;
    }

    public static boolean between(int var0, int var1, int var2)
    {
        return var0 >= var1 && var0 <= var2;
    }

    public static boolean isMultiTexture()
    {
        return getAnisotropicFilterLevel() > 1 ? true : getAntialiasingLevel() > 0;
    }

    public static boolean isDrippingWaterLava()
    {
        return gameSettings == null ? false : gameSettings.ofDrippingWaterLava;
    }

    public static boolean isBetterSnow()
    {
        return gameSettings == null ? false : gameSettings.ofBetterSnow;
    }

    public static Dimension getFullscreenDimension()
    {
        if (desktopDisplayMode == null)
        {
            return null;
        }
        else if (gameSettings == null)
        {
            return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
        }
        else
        {
            String var0 = gameSettings.ofFullscreenMode;

            if (var0.equals("Default"))
            {
                return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
            }
            else
            {
                String[] var1 = tokenize(var0, " x");
                return var1.length < 2 ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(parseInt(var1[0], -1), parseInt(var1[1], -1));
            }
        }
    }

    public static int parseInt(String var0, int var1)
    {
        try
        {
            return var0 == null ? var1 : Integer.parseInt(var0);
        }
        catch (NumberFormatException var3)
        {
            return var1;
        }
    }

    public static float parseFloat(String var0, float var1)
    {
        try
        {
            return var0 == null ? var1 : Float.parseFloat(var0);
        }
        catch (NumberFormatException var3)
        {
            return var1;
        }
    }

    public static String[] tokenize(String var0, String var1)
    {
        StringTokenizer var2 = new StringTokenizer(var0, var1);
        ArrayList var3 = new ArrayList();

        while (var2.hasMoreTokens())
        {
            String var4 = var2.nextToken();
            var3.add(var4);
        }

        String[] var5 = (String[])((String[])var3.toArray(new String[var3.size()]));
        return var5;
    }

    public static DisplayMode getDesktopDisplayMode()
    {
        return desktopDisplayMode;
    }

    public static void setDesktopDisplayMode(DisplayMode var0)
    {
        desktopDisplayMode = var0;
    }

    public static DisplayMode[] getFullscreenDisplayModes()
    {
        try
        {
            DisplayMode[] var0 = Display.getAvailableDisplayModes();
            ArrayList var1 = new ArrayList();

            for (int var2 = 0; var2 < var0.length; ++var2)
            {
                DisplayMode var3 = var0[var2];

                if (desktopDisplayMode == null || var3.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && var3.getFrequency() == desktopDisplayMode.getFrequency())
                {
                    var1.add(var3);
                }
            }

            DisplayMode[] var5 = (DisplayMode[])((DisplayMode[])var1.toArray(new DisplayMode[var1.size()]));
            Config$1 var6 = new Config$1();
            Arrays.sort(var5, var6);
            return var5;
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
            return new DisplayMode[] {desktopDisplayMode};
        }
    }

    public static String[] getFullscreenModes()
    {
        DisplayMode[] var0 = getFullscreenDisplayModes();
        String[] var1 = new String[var0.length];

        for (int var2 = 0; var2 < var0.length; ++var2)
        {
            DisplayMode var3 = var0[var2];
            String var4 = "" + var3.getWidth() + "x" + var3.getHeight();
            var1[var2] = var4;
        }

        return var1;
    }

    public static DisplayMode getDisplayMode(Dimension var0) throws LWJGLException
    {
        DisplayMode[] var1 = Display.getAvailableDisplayModes();

        for (int var2 = 0; var2 < var1.length; ++var2)
        {
            DisplayMode var3 = var1[var2];

            if (var3.getWidth() == var0.width && var3.getHeight() == var0.height && (desktopDisplayMode == null || var3.getBitsPerPixel() == desktopDisplayMode.getBitsPerPixel() && var3.getFrequency() == desktopDisplayMode.getFrequency()))
            {
                return var3;
            }
        }

        return desktopDisplayMode;
    }

    public static boolean isAnimatedTerrain()
    {
        return gameSettings != null ? gameSettings.ofAnimatedTerrain : true;
    }

    public static boolean isAnimatedItems()
    {
        return gameSettings != null ? gameSettings.ofAnimatedItems : true;
    }

    public static boolean isAnimatedTextures()
    {
        return gameSettings != null ? gameSettings.ofAnimatedTextures : true;
    }

    public static boolean isSwampColors()
    {
        return gameSettings != null ? gameSettings.ofSwampColors : true;
    }

    public static boolean isRandomMobs()
    {
        return gameSettings != null ? gameSettings.ofRandomMobs : true;
    }

    public static void checkGlError(String var0)
    {
        int var1 = GL11.glGetError();

        if (var1 != 0)
        {
            String var2 = GLU.gluErrorString(var1);
            dbg("OpenGlError: " + var1 + " (" + var2 + "), at: " + var0);
        }
    }

    public static boolean isSmoothBiomes()
    {
        return gameSettings != null ? gameSettings.ofSmoothBiomes : true;
    }

    public static boolean isCustomColors()
    {
        return gameSettings != null ? gameSettings.ofCustomColors : true;
    }

    public static boolean isCustomSky()
    {
        return gameSettings != null ? gameSettings.ofCustomSky : true;
    }

    public static boolean isCustomFonts()
    {
        return gameSettings != null ? gameSettings.ofCustomFonts : true;
    }

    public static boolean isShowCapes()
    {
        return gameSettings != null ? gameSettings.ofShowCapes : true;
    }

    public static boolean isConnectedTextures()
    {
        return gameSettings != null ? gameSettings.ofConnectedTextures != 3 : false;
    }

    public static boolean isNaturalTextures()
    {
        return gameSettings != null ? gameSettings.ofNaturalTextures : false;
    }

    public static boolean isConnectedTexturesFancy()
    {
        return gameSettings != null ? gameSettings.ofConnectedTextures == 2 : false;
    }

    public static String[] readLines(File var0) throws IOException
    {
        ArrayList var1 = new ArrayList();
        FileInputStream var2 = new FileInputStream(var0);
        InputStreamReader var3 = new InputStreamReader(var2, "ASCII");
        BufferedReader var4 = new BufferedReader(var3);

        while (true)
        {
            String var5 = var4.readLine();

            if (var5 == null)
            {
                String[] var6 = (String[])((String[])var1.toArray(new String[var1.size()]));
                return var6;
            }

            var1.add(var5);
        }
    }

    public static String readFile(File var0) throws IOException
    {
        FileInputStream var1 = new FileInputStream(var0);
        return readInputStream(var1, "ASCII");
    }

    public static String readInputStream(InputStream var0) throws IOException
    {
        return readInputStream(var0, "ASCII");
    }

    public static String readInputStream(InputStream var0, String var1) throws IOException
    {
        InputStreamReader var2 = new InputStreamReader(var0, var1);
        BufferedReader var3 = new BufferedReader(var2);
        StringBuffer var4 = new StringBuffer();

        while (true)
        {
            String var5 = var3.readLine();

            if (var5 == null)
            {
                return var4.toString();
            }

            var4.append(var5);
            var4.append("\n");
        }
    }

    public static GameSettings getGameSettings()
    {
        return gameSettings;
    }

    public static String getNewRelease()
    {
        return newRelease;
    }

    public static void setNewRelease(String var0)
    {
        newRelease = var0;
    }

    public static int compareRelease(String var0, String var1)
    {
        String[] var2 = splitRelease(var0);
        String[] var3 = splitRelease(var1);
        String var4 = var2[0];
        String var5 = var3[0];

        if (!var4.equals(var5))
        {
            return var4.compareTo(var5);
        }
        else
        {
            int var6 = parseInt(var2[1], -1);
            int var7 = parseInt(var3[1], -1);

            if (var6 != var7)
            {
                return var6 - var7;
            }
            else
            {
                String var8 = var2[2];
                String var9 = var3[2];
                return var8.compareTo(var9);
            }
        }
    }

    private static String[] splitRelease(String var0)
    {
        if (var0 != null && var0.length() > 0)
        {
            String var1 = var0.substring(0, 1);

            if (var0.length() <= 1)
            {
                return new String[] {var1, "", ""};
            }
            else
            {
                int var2;

                for (var2 = 1; var2 < var0.length() && Character.isDigit(var0.charAt(var2)); ++var2)
                {
                    ;
                }

                String var3 = var0.substring(1, var2);

                if (var2 >= var0.length())
                {
                    return new String[] {var1, var3, ""};
                }
                else
                {
                    String var4 = var0.substring(var2);
                    return new String[] {var1, var3, var4};
                }
            }
        }
        else
        {
            return new String[] {"", "", ""};
        }
    }

    public static int intHash(int var0)
    {
        var0 = var0 ^ 61 ^ var0 >> 16;
        var0 += var0 << 3;
        var0 ^= var0 >> 4;
        var0 *= 668265261;
        var0 ^= var0 >> 15;
        return var0;
    }

    public static int getRandom(int var0, int var1, int var2, int var3)
    {
        int var4 = intHash(var3 + 37);
        var4 = intHash(var4 + var0);
        var4 = intHash(var4 + var2);
        var4 = intHash(var4 + var1);
        return var4;
    }

    public static WorldServer getWorldServer()
    {
        if (minecraft == null)
        {
            return null;
        }
        else
        {
            WorldClient var0 = minecraft.theWorld;

            if (var0 == null)
            {
                return null;
            }
            else
            {
                WorldProvider var1 = var0.provider;

                if (var1 == null)
                {
                    return null;
                }
                else
                {
                    int var2 = var1.dimensionId;
                    IntegratedServer var3 = minecraft.getIntegratedServer();

                    if (var3 == null)
                    {
                        return null;
                    }
                    else
                    {
                        WorldServer var4 = var3.worldServerForDimension(var2);
                        return var4;
                    }
                }
            }
        }
    }

    public static int getAvailableProcessors()
    {
        if (availableProcessors < 1)
        {
            availableProcessors = Runtime.getRuntime().availableProcessors();
        }

        return availableProcessors;
    }

    public static boolean isSingleProcessor()
    {
        return getAvailableProcessors() <= 1;
    }

    public static boolean isSmoothWorld()
    {
        return getAvailableProcessors() > 1 ? false : (gameSettings == null ? true : gameSettings.ofSmoothWorld);
    }

    public static boolean isLazyChunkLoading()
    {
        return getAvailableProcessors() > 1 ? false : (gameSettings == null ? true : gameSettings.ofLazyChunkLoading);
    }

    public static int getChunkViewDistance()
    {
        if (gameSettings == null)
        {
            return 10;
        }
        else
        {
            int var0 = gameSettings.ofRenderDistanceFine / 16;
            return var0 <= 16 ? 10 : var0;
        }
    }
}
