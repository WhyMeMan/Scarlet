package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class GameSettings
{
    private static final String[] RENDER_DISTANCES = new String[] {"options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"};
    private static final String[] DIFFICULTIES = new String[] {"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};

    /** GUI scale values */
    private static final String[] GUISCALES = new String[] {"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
    private static final String[] CHAT_VISIBILITIES = new String[] {"options.chat.visibility.full", "options.chat.visibility.system", "options.chat.visibility.hidden"};
    private static final String[] PARTICLES = new String[] {"options.particles.all", "options.particles.decreased", "options.particles.minimal"};

    /** Limit framerate labels */
    private static final String[] LIMIT_FRAMERATES = new String[] {"performance.max", "performance.balanced", "performance.powersaver"};
    public float musicVolume = 1.0F;
    public float soundVolume = 1.0F;
    public float mouseSensitivity = 0.5F;
    public boolean invertMouse = false;
    public int renderDistance = 0;
    public boolean viewBobbing = true;
    public boolean anaglyph = false;

    /** Advanced OpenGL */
    public boolean advancedOpengl = false;
    public int limitFramerate = 1;
    public boolean fancyGraphics = true;

    /** Smooth Lighting */
    public boolean ambientOcclusion = true;

    /** Clouds flag */
    public boolean clouds = true;
    public int ofRenderDistanceFine = 128;
    public int ofLimitFramerateFine = 0;
    public int ofFogType = 1;
    public float ofFogStart = 0.8F;
    public int ofMipmapLevel = 0;
    public boolean ofMipmapLinear = false;
    public boolean ofLoadFar = false;
    public int ofPreloadedChunks = 0;
    public boolean ofOcclusionFancy = false;
    public boolean ofSmoothFps = false;
    public boolean ofSmoothWorld = Config.isSingleProcessor();
    public boolean ofLazyChunkLoading = Config.isSingleProcessor();
    public float ofAoLevel = 1.0F;
    public int ofAaLevel = 0;
    public int ofAfLevel = 1;
    public int ofClouds = 0;
    public float ofCloudsHeight = 0.0F;
    public int ofTrees = 0;
    public int ofGrass = 0;
    public int ofRain = 0;
    public int ofWater = 0;
    public int ofDroppedItems = 0;
    public int ofBetterGrass = 3;
    public int ofAutoSaveTicks = 4000;
    public boolean ofLagometer = false;
    public boolean ofProfiler = false;
    public boolean ofWeather = true;
    public boolean ofSky = true;
    public boolean ofStars = true;
    public boolean ofSunMoon = true;
    public int ofChunkUpdates = 1;
    public int ofChunkLoading = 0;
    public boolean ofChunkUpdatesDynamic = false;
    public int ofTime = 0;
    public boolean ofClearWater = false;
    public boolean ofDepthFog = true;
    public boolean ofBetterSnow = false;
    public String ofFullscreenMode = "Default";
    public boolean ofSwampColors = true;
    public boolean ofRandomMobs = true;
    public boolean ofSmoothBiomes = true;
    public boolean ofCustomFonts = true;
    public boolean ofCustomColors = true;
    public boolean ofShowCapes = true;
    public int ofConnectedTextures = 2;
    public boolean ofNaturalTextures = false;
    public int ofAnimatedWater = 0;
    public int ofAnimatedLava = 0;
    public boolean ofAnimatedFire = true;
    public boolean ofAnimatedPortal = true;
    public boolean ofAnimatedRedstone = true;
    public boolean ofAnimatedExplosion = true;
    public boolean ofAnimatedFlame = true;
    public boolean ofAnimatedSmoke = true;
    public boolean ofVoidParticles = true;
    public boolean ofWaterParticles = true;
    public boolean ofRainSplash = true;
    public boolean ofPortalParticles = true;
    public boolean ofDrippingWaterLava = true;
    public boolean ofAnimatedTerrain = true;
    public boolean ofAnimatedItems = true;
    public boolean ofAnimatedTextures = true;
    public static final int DEFAULT = 0;
    public static final int FAST = 1;
    public static final int FANCY = 2;
    public static final int OFF = 3;
    public static final int ANIM_ON = 0;
    public static final int ANIM_GENERATED = 1;
    public static final int ANIM_OFF = 2;
    public static final int CL_DEFAULT = 0;
    public static final int CL_SMOOTH = 1;
    public static final int CL_THREADED = 2;
    public static final String DEFAULT_STR = "Default";
    public KeyBinding ofKeyBindZoom;

    /** The name of the selected texture pack. */
    public String skin = "Default";
    public int chatVisibility = 0;
    public boolean chatColours = true;
    public boolean chatLinks = true;
    public boolean chatLinksPrompt = true;
    public float chatOpacity = 1.0F;
    public boolean serverTextures = true;
    public boolean snooperEnabled = true;
    public boolean fullScreen = false;
    public boolean enableVsync = true;
    public boolean hideServerAddress = false;

    /**
     * Whether to show advanced information on item tooltips, toggled by F3+H
     */
    public boolean advancedItemTooltips = false;

    /** Whether to pause when the game loses focus, toggled by F3+P */
    public boolean pauseOnLostFocus = true;

    /** Whether to show your cape */
    public boolean showCape = true;
    public boolean touchscreen = false;
    public int field_92118_B = 0;
    public int field_92119_C = 0;
    public boolean field_92117_D = true;
    public KeyBinding keyBindForward = new KeyBinding("key.forward", 17);
    public KeyBinding keyBindLeft = new KeyBinding("key.left", 30);
    public KeyBinding keyBindBack = new KeyBinding("key.back", 31);
    public KeyBinding keyBindRight = new KeyBinding("key.right", 32);
    public KeyBinding keyBindJump = new KeyBinding("key.jump", 57);
    public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18);
    public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16);
    public KeyBinding keyBindChat = new KeyBinding("key.chat", 20);
    public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42);
    public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100);
    public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99);
    public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15);
    public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98);
    public KeyBinding keyBindCommand = new KeyBinding("key.command", 53);
    public KeyBinding[] keyBindings;
    protected Minecraft mc;
    private File optionsFile;
    public int difficulty;
    public boolean hideGUI;
    public int thirdPersonView;

    /** true if debug info should be displayed instead of version */
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;

    /** The lastServer string. */
    public String lastServer;

    /** No clipping for singleplayer */
    public boolean noclip;

    /** Smooth Camera Toggle */
    public boolean smoothCamera;
    public boolean debugCamEnable;

    /** No clipping movement rate */
    public float noclipRate;

    /** Change rate for debug camera */
    public float debugCamRate;
    public float fovSetting;
    public float gammaSetting;

    /** GUI scale */
    public int guiScale;

    /** Determines amount of particles. 0 = All, 1 = Decreased, 2 = Minimal */
    public int particleSetting;

    /** Game settings language */
    public String language;
    private File optionsFileOF;

    public GameSettings(Minecraft par1Minecraft, File par2File)
    {
        this.renderDistance = 1;
        this.limitFramerate = 0;
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29);
        this.keyBindings = new KeyBinding[] {this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.ofKeyBindZoom, this.keyBindCommand};
        this.difficulty = 2;
        this.hideGUI = false;
        this.thirdPersonView = 0;
        this.showDebugInfo = false;
        this.showDebugProfilerChart = false;
        this.lastServer = "";
        this.noclip = false;
        this.smoothCamera = false;
        this.debugCamEnable = false;
        this.noclipRate = 1.0F;
        this.debugCamRate = 1.0F;
        this.fovSetting = 0.0F;
        this.gammaSetting = 0.0F;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.language = "en_US";
        this.mc = par1Minecraft;
        this.optionsFile = new File(par2File, "options.txt");
        this.optionsFileOF = new File(par2File, "optionsof.txt");
        this.loadOptions();
        Config.setGameSettings(this);
    }

    public GameSettings()
    {
        this.renderDistance = 1;
        this.limitFramerate = 0;
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29);
        this.keyBindings = new KeyBinding[] {this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.ofKeyBindZoom, this.keyBindCommand};
        this.difficulty = 2;
        this.hideGUI = false;
        this.thirdPersonView = 0;
        this.showDebugInfo = false;
        this.showDebugProfilerChart = false;
        this.lastServer = "";
        this.noclip = false;
        this.smoothCamera = false;
        this.debugCamEnable = false;
        this.noclipRate = 1.0F;
        this.debugCamRate = 1.0F;
        this.fovSetting = 0.0F;
        this.gammaSetting = 0.0F;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.language = "en_US";
    }

    public String getKeyBindingDescription(int par1)
    {
        StringTranslate var2 = StringTranslate.getInstance();
        return var2.translateKey(this.keyBindings[par1].keyDescription);
    }

    /**
     * The string that appears inside the button/slider in the options menu.
     */
    public String getOptionDisplayString(int par1)
    {
        int var2 = this.keyBindings[par1].keyCode;
        return getKeyDisplayString(var2);
    }

    /**
     * Represents a key or mouse button as a string. Args: key
     */
    public static String getKeyDisplayString(int par0)
    {
        return par0 < 0 ? StatCollector.translateToLocalFormatted("key.mouseButton", new Object[] {Integer.valueOf(par0 + 101)}): Keyboard.getKeyName(par0);
    }

    /**
     * Sets a key binding.
     */
    public void setKeyBinding(int par1, int par2)
    {
        this.keyBindings[par1].keyCode = par2;
        this.saveOptions();
    }

    /**
     * If the specified option is controlled by a slider (float value), this will set the float value.
     */
    public void setOptionFloatValue(EnumOptions par1EnumOptions, float par2)
    {
        if (par1EnumOptions == EnumOptions.MUSIC)
        {
            this.musicVolume = par2;
            this.mc.sndManager.onSoundOptionsChanged();
        }

        if (par1EnumOptions == EnumOptions.SOUND)
        {
            this.soundVolume = par2;
            this.mc.sndManager.onSoundOptionsChanged();
        }

        if (par1EnumOptions == EnumOptions.SENSITIVITY)
        {
            this.mouseSensitivity = par2;
        }

        if (par1EnumOptions == EnumOptions.FOV)
        {
            this.fovSetting = par2;
        }

        if (par1EnumOptions == EnumOptions.GAMMA)
        {
            this.gammaSetting = par2;
        }

        if (par1EnumOptions == EnumOptions.CLOUD_HEIGHT)
        {
            this.ofCloudsHeight = par2;
        }

        if (par1EnumOptions == EnumOptions.AO_LEVEL)
        {
            this.ofAoLevel = par2;
            this.ambientOcclusion = this.ofAoLevel > 0.0F;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE)
        {
            int var3 = this.ofRenderDistanceFine;
            this.ofRenderDistanceFine = 32 + (int)(par2 * 480.0F);
            this.ofRenderDistanceFine = this.ofRenderDistanceFine >> 4 << 4;
            this.ofRenderDistanceFine = Config.limit(this.ofRenderDistanceFine, 32, 512);
            this.renderDistance = fineToRenderDistance(this.ofRenderDistanceFine);

            if (this.ofRenderDistanceFine != var3)
            {
                this.mc.renderGlobal.loadRenderers();
            }
        }

        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT_FINE)
        {
            this.ofLimitFramerateFine = (int)(par2 * 200.0F);
            this.enableVsync = false;

            if (this.ofLimitFramerateFine < 5)
            {
                this.enableVsync = true;
                this.ofLimitFramerateFine = 0;
            }

            if (this.ofLimitFramerateFine > 199)
            {
                this.enableVsync = false;
                this.ofLimitFramerateFine = 0;
            }

            if (this.ofLimitFramerateFine > 30)
            {
                this.ofLimitFramerateFine = this.ofLimitFramerateFine / 5 * 5;
            }

            if (this.ofLimitFramerateFine > 100)
            {
                this.ofLimitFramerateFine = this.ofLimitFramerateFine / 10 * 10;
            }

            this.limitFramerate = fineToLimitFramerate(this.ofLimitFramerateFine);
            this.updateVSync();
        }

        if (par1EnumOptions == EnumOptions.CHAT_OPACITY)
        {
            this.chatOpacity = par2;
        }
    }

    private void updateWaterOpacity()
    {
        byte var1 = 3;

        if (this.ofClearWater)
        {
            var1 = 1;
        }

        Block.waterStill.setLightOpacity(var1);
        Block.waterMoving.setLightOpacity(var1);

        if (this.mc.theWorld != null)
        {
            IChunkProvider var2 = this.mc.theWorld.chunkProvider;

            if (var2 != null)
            {
                for (int var3 = -512; var3 < 512; ++var3)
                {
                    for (int var4 = -512; var4 < 512; ++var4)
                    {
                        if (var2.chunkExists(var3, var4))
                        {
                            Chunk var5 = var2.provideChunk(var3, var4);

                            if (var5 != null && !(var5 instanceof EmptyChunk))
                            {
                                ExtendedBlockStorage[] var6 = var5.getBlockStorageArray();

                                for (int var7 = 0; var7 < var6.length; ++var7)
                                {
                                    ExtendedBlockStorage var8 = var6[var7];

                                    if (var8 != null)
                                    {
                                        NibbleArray var9 = var8.getSkylightArray();

                                        if (var9 != null)
                                        {
                                            byte[] var10 = var9.data;

                                            for (int var11 = 0; var11 < var10.length; ++var11)
                                            {
                                                var10[var11] = 0;
                                            }
                                        }
                                    }
                                }

                                var5.generateSkylightMap();
                            }
                        }
                    }
                }

                this.mc.renderGlobal.loadRenderers();
            }
        }
    }

    public void updateChunkLoading()
    {
        switch (this.ofChunkLoading)
        {
            case 1:
                WrUpdates.setWrUpdater(new WrUpdaterSmooth());
                break;

            case 2:
                WrUpdates.setWrUpdater(new WrUpdaterThreaded());
                break;

            default:
                WrUpdates.setWrUpdater((IWrUpdater)null);
        }

        if (this.mc.renderGlobal != null)
        {
            this.mc.renderGlobal.loadRenderers();
        }
    }

    public void setAllAnimations(boolean var1)
    {
        int var2 = var1 ? 0 : 2;
        this.ofAnimatedWater = var2;
        this.ofAnimatedLava = var2;
        this.ofAnimatedFire = var1;
        this.ofAnimatedPortal = var1;
        this.ofAnimatedRedstone = var1;
        this.ofAnimatedExplosion = var1;
        this.ofAnimatedFlame = var1;
        this.ofAnimatedSmoke = var1;
        this.ofVoidParticles = var1;
        this.ofWaterParticles = var1;
        this.ofRainSplash = var1;
        this.ofPortalParticles = var1;
        this.particleSetting = var1 ? 0 : 2;
        this.ofDrippingWaterLava = var1;
        this.ofAnimatedTerrain = var1;
        this.ofAnimatedItems = var1;
        this.ofAnimatedTextures = var1;
        this.mc.renderEngine.refreshTextures();
    }

    /**
     * For non-float options. Toggles the option on/off, or cycles through the list i.e. render distances.
     */
    public void setOptionValue(EnumOptions par1EnumOptions, int par2)
    {
        if (par1EnumOptions == EnumOptions.INVERT_MOUSE)
        {
            this.invertMouse = !this.invertMouse;
        }

        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE)
        {
            this.renderDistance = this.renderDistance + par2 & 3;
            this.ofRenderDistanceFine = renderDistanceToFine(this.renderDistance);
        }

        if (par1EnumOptions == EnumOptions.GUI_SCALE)
        {
            this.guiScale = this.guiScale + par2 & 3;
        }

        if (par1EnumOptions == EnumOptions.PARTICLES)
        {
            this.particleSetting = (this.particleSetting + par2) % 3;
        }

        if (par1EnumOptions == EnumOptions.VIEW_BOBBING)
        {
            this.viewBobbing = !this.viewBobbing;
        }

        if (par1EnumOptions == EnumOptions.RENDER_CLOUDS)
        {
            this.clouds = !this.clouds;
        }

        if (par1EnumOptions == EnumOptions.ADVANCED_OPENGL)
        {
            if (!Config.isOcclusionAvailable())
            {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }
            else if (!this.advancedOpengl)
            {
                this.advancedOpengl = true;
                this.ofOcclusionFancy = false;
            }
            else if (!this.ofOcclusionFancy)
            {
                this.ofOcclusionFancy = true;
            }
            else
            {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }

            this.mc.renderGlobal.setAllRenderersVisible();
        }

        if (par1EnumOptions == EnumOptions.ANAGLYPH)
        {
            this.anaglyph = !this.anaglyph;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT)
        {
            this.limitFramerate = (this.limitFramerate + par2 + 3) % 3;
            this.ofLimitFramerateFine = limitFramerateToFine(this.limitFramerate);
        }

        if (par1EnumOptions == EnumOptions.DIFFICULTY)
        {
            this.difficulty = this.difficulty + par2 & 3;
        }

        if (par1EnumOptions == EnumOptions.GRAPHICS)
        {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.AMBIENT_OCCLUSION)
        {
            this.ambientOcclusion = !this.ambientOcclusion;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.FOG_FANCY)
        {
            switch (this.ofFogType)
            {
                case 1:
                    this.ofFogType = 2;

                    if (!Config.isFancyFogAvailable())
                    {
                        this.ofFogType = 3;
                    }

                    break;

                case 2:
                    this.ofFogType = 3;
                    break;

                case 3:
                    this.ofFogType = 1;
                    break;

                default:
                    this.ofFogType = 1;
            }
        }

        if (par1EnumOptions == EnumOptions.FOG_START)
        {
            this.ofFogStart += 0.2F;

            if (this.ofFogStart > 0.81F)
            {
                this.ofFogStart = 0.2F;
            }
        }

        if (par1EnumOptions == EnumOptions.MIPMAP_LEVEL)
        {
            ++this.ofMipmapLevel;

            if (this.ofMipmapLevel > 4)
            {
                this.ofMipmapLevel = 0;
            }

            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.MIPMAP_TYPE)
        {
            this.ofMipmapLinear = !this.ofMipmapLinear;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.LOAD_FAR)
        {
            this.ofLoadFar = !this.ofLoadFar;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.PRELOADED_CHUNKS)
        {
            this.ofPreloadedChunks += 2;

            if (this.ofPreloadedChunks > 8)
            {
                this.ofPreloadedChunks = 0;
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_FPS)
        {
            this.ofSmoothFps = !this.ofSmoothFps;
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_WORLD)
        {
            this.ofSmoothWorld = !this.ofSmoothWorld;
            Config.updateThreadPriorities();
        }

        if (par1EnumOptions == EnumOptions.CLOUDS)
        {
            ++this.ofClouds;

            if (this.ofClouds > 3)
            {
                this.ofClouds = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.TREES)
        {
            ++this.ofTrees;

            if (this.ofTrees > 2)
            {
                this.ofTrees = 0;
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.GRASS)
        {
            ++this.ofGrass;

            if (this.ofGrass > 2)
            {
                this.ofGrass = 0;
            }

            RenderBlocks.fancyGrass = Config.isGrassFancy();
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.DROPPED_ITEMS)
        {
            ++this.ofDroppedItems;

            if (this.ofDroppedItems > 2)
            {
                this.ofDroppedItems = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.RAIN)
        {
            ++this.ofRain;

            if (this.ofRain > 3)
            {
                this.ofRain = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.WATER)
        {
            ++this.ofWater;

            if (this.ofWater > 2)
            {
                this.ofWater = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_WATER)
        {
            ++this.ofAnimatedWater;

            if (this.ofAnimatedWater > 2)
            {
                this.ofAnimatedWater = 0;
            }

            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_LAVA)
        {
            ++this.ofAnimatedLava;

            if (this.ofAnimatedLava > 2)
            {
                this.ofAnimatedLava = 0;
            }

            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_FIRE)
        {
            this.ofAnimatedFire = !this.ofAnimatedFire;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_PORTAL)
        {
            this.ofAnimatedPortal = !this.ofAnimatedPortal;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_REDSTONE)
        {
            this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_EXPLOSION)
        {
            this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_FLAME)
        {
            this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_SMOKE)
        {
            this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }

        if (par1EnumOptions == EnumOptions.VOID_PARTICLES)
        {
            this.ofVoidParticles = !this.ofVoidParticles;
        }

        if (par1EnumOptions == EnumOptions.WATER_PARTICLES)
        {
            this.ofWaterParticles = !this.ofWaterParticles;
        }

        if (par1EnumOptions == EnumOptions.PORTAL_PARTICLES)
        {
            this.ofPortalParticles = !this.ofPortalParticles;
        }

        if (par1EnumOptions == EnumOptions.DRIPPING_WATER_LAVA)
        {
            this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_TERRAIN)
        {
            this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_TEXTURES)
        {
            this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }

        if (par1EnumOptions == EnumOptions.ANIMATED_ITEMS)
        {
            this.ofAnimatedItems = !this.ofAnimatedItems;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.RAIN_SPLASH)
        {
            this.ofRainSplash = !this.ofRainSplash;
        }

        if (par1EnumOptions == EnumOptions.LAGOMETER)
        {
            this.ofLagometer = !this.ofLagometer;
        }

        if (par1EnumOptions == EnumOptions.AUTOSAVE_TICKS)
        {
            this.ofAutoSaveTicks *= 10;

            if (this.ofAutoSaveTicks > 40000)
            {
                this.ofAutoSaveTicks = 40;
            }
        }

        if (par1EnumOptions == EnumOptions.BETTER_GRASS)
        {
            ++this.ofBetterGrass;

            if (this.ofBetterGrass > 3)
            {
                this.ofBetterGrass = 1;
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.CONNECTED_TEXTURES)
        {
            ++this.ofConnectedTextures;

            if (this.ofConnectedTextures > 3)
            {
                this.ofConnectedTextures = 1;
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.WEATHER)
        {
            this.ofWeather = !this.ofWeather;
        }

        if (par1EnumOptions == EnumOptions.SKY)
        {
            this.ofSky = !this.ofSky;
        }

        if (par1EnumOptions == EnumOptions.STARS)
        {
            this.ofStars = !this.ofStars;
        }

        if (par1EnumOptions == EnumOptions.SUN_MOON)
        {
            this.ofSunMoon = !this.ofSunMoon;
        }

        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES)
        {
            ++this.ofChunkUpdates;

            if (this.ofChunkUpdates > 5)
            {
                this.ofChunkUpdates = 1;
            }
        }

        if (par1EnumOptions == EnumOptions.CHUNK_LOADING)
        {
            ++this.ofChunkLoading;

            if (this.ofChunkLoading > 2)
            {
                this.ofChunkLoading = 0;
            }

            this.updateChunkLoading();
        }

        if (par1EnumOptions == EnumOptions.CHUNK_UPDATES_DYNAMIC)
        {
            this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
        }

        if (par1EnumOptions == EnumOptions.TIME)
        {
            ++this.ofTime;

            if (this.ofTime > 3)
            {
                this.ofTime = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.CLEAR_WATER)
        {
            this.ofClearWater = !this.ofClearWater;
            this.updateWaterOpacity();
        }

        if (par1EnumOptions == EnumOptions.DEPTH_FOG)
        {
            this.ofDepthFog = !this.ofDepthFog;
        }

        if (par1EnumOptions == EnumOptions.AA_LEVEL)
        {
            int[] var3 = new int[] {0, 2, 4, 6, 8, 12, 16};
            boolean var4 = false;

            for (int var5 = 0; var5 < var3.length - 1; ++var5)
            {
                if (this.ofAaLevel == var3[var5])
                {
                    this.ofAaLevel = var3[var5 + 1];
                    var4 = true;
                    break;
                }
            }

            if (!var4)
            {
                this.ofAaLevel = 0;
            }
        }

        if (par1EnumOptions == EnumOptions.AF_LEVEL)
        {
            this.ofAfLevel *= 2;

            if (this.ofAfLevel > 16)
            {
                this.ofAfLevel = 1;
            }

            this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
            this.mc.renderEngine.refreshTextures();
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.PROFILER)
        {
            this.ofProfiler = !this.ofProfiler;
        }

        if (par1EnumOptions == EnumOptions.BETTER_SNOW)
        {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.SWAMP_COLORS)
        {
            this.ofSwampColors = !this.ofSwampColors;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.RANDOM_MOBS)
        {
            this.ofRandomMobs = !this.ofRandomMobs;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.SMOOTH_BIOMES)
        {
            this.ofSmoothBiomes = !this.ofSmoothBiomes;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.CUSTOM_FONTS)
        {
            this.ofCustomFonts = !this.ofCustomFonts;
            this.mc.renderEngine.refreshTextures();
        }

        if (par1EnumOptions == EnumOptions.CUSTOM_COLORS)
        {
            this.ofCustomColors = !this.ofCustomColors;
            this.mc.renderEngine.refreshTextures();
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.SHOW_CAPES)
        {
            this.ofShowCapes = !this.ofShowCapes;
            this.mc.renderGlobal.updateCapes();
        }

        if (par1EnumOptions == EnumOptions.NATURAL_TEXTURES)
        {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            this.mc.renderEngine.refreshTextures();
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.LAZY_CHUNK_LOADING)
        {
            this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
            this.mc.renderGlobal.loadRenderers();
        }

        if (par1EnumOptions == EnumOptions.FULLSCREEN_MODE)
        {
            List var6 = Arrays.asList(Config.getFullscreenModes());

            if (this.ofFullscreenMode.equals("Default"))
            {
                this.ofFullscreenMode = (String)var6.get(0);
            }
            else
            {
                int var7 = var6.indexOf(this.ofFullscreenMode);

                if (var7 < 0)
                {
                    this.ofFullscreenMode = "Default";
                }
                else
                {
                    ++var7;

                    if (var7 >= var6.size())
                    {
                        this.ofFullscreenMode = "Default";
                    }
                    else
                    {
                        this.ofFullscreenMode = (String)var6.get(var7);
                    }
                }
            }
        }

        if (par1EnumOptions == EnumOptions.HELD_ITEM_TOOLTIPS)
        {
            this.field_92117_D = !this.field_92117_D;
        }

        if (par1EnumOptions == EnumOptions.CHAT_VISIBILITY)
        {
            this.chatVisibility = (this.chatVisibility + par2) % 3;
        }

        if (par1EnumOptions == EnumOptions.CHAT_COLOR)
        {
            this.chatColours = !this.chatColours;
        }

        if (par1EnumOptions == EnumOptions.CHAT_LINKS)
        {
            this.chatLinks = !this.chatLinks;
        }

        if (par1EnumOptions == EnumOptions.CHAT_LINKS_PROMPT)
        {
            this.chatLinksPrompt = !this.chatLinksPrompt;
        }

        if (par1EnumOptions == EnumOptions.USE_SERVER_TEXTURES)
        {
            this.serverTextures = !this.serverTextures;
        }

        if (par1EnumOptions == EnumOptions.SNOOPER_ENABLED)
        {
            this.snooperEnabled = !this.snooperEnabled;
        }

        if (par1EnumOptions == EnumOptions.SHOW_CAPE)
        {
            this.showCape = !this.showCape;
        }

        if (par1EnumOptions == EnumOptions.TOUCHSCREEN)
        {
            this.touchscreen = !this.touchscreen;
        }

        if (par1EnumOptions == EnumOptions.USE_FULLSCREEN)
        {
            this.fullScreen = !this.fullScreen;

            if (this.mc.isFullScreen() != this.fullScreen)
            {
                this.mc.toggleFullscreen();
            }
        }

        if (par1EnumOptions == EnumOptions.ENABLE_VSYNC)
        {
            this.enableVsync = !this.enableVsync;
            Display.setVSyncEnabled(this.enableVsync);
        }

        this.saveOptions();
    }

    public float getOptionFloatValue(EnumOptions par1EnumOptions)
    {
        return par1EnumOptions == EnumOptions.FOV ? this.fovSetting : (par1EnumOptions == EnumOptions.GAMMA ? this.gammaSetting : (par1EnumOptions == EnumOptions.MUSIC ? this.musicVolume : (par1EnumOptions == EnumOptions.SOUND ? this.soundVolume : (par1EnumOptions == EnumOptions.SENSITIVITY ? this.mouseSensitivity : (par1EnumOptions == EnumOptions.CLOUD_HEIGHT ? this.ofCloudsHeight : (par1EnumOptions == EnumOptions.AO_LEVEL ? this.ofAoLevel : (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE ? (float)(this.ofRenderDistanceFine - 32) / 480.0F : (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT_FINE ? (this.ofLimitFramerateFine > 0 && this.ofLimitFramerateFine < 200 ? (float)this.ofLimitFramerateFine / 200.0F : (this.enableVsync ? 0.0F : 1.0F)) : (par1EnumOptions == EnumOptions.CHAT_OPACITY ? this.chatOpacity : 0.0F)))))))));
    }

    public boolean getOptionOrdinalValue(EnumOptions par1EnumOptions)
    {
        switch (EnumOptionsHelper.enumOptionsMappingHelperArray[par1EnumOptions.ordinal()])
        {
            case 1:
                return this.invertMouse;

            case 2:
                return this.viewBobbing;

            case 3:
                return this.anaglyph;

            case 4:
                return this.advancedOpengl;

            case 5:
                return this.ambientOcclusion;

            case 6:
                return this.clouds;

            case 7:
                return this.chatColours;

            case 8:
                return this.chatLinks;

            case 9:
                return this.chatLinksPrompt;

            case 10:
                return this.serverTextures;

            case 11:
                return this.snooperEnabled;

            case 12:
                return this.fullScreen;

            case 13:
                return this.enableVsync;

            case 14:
                return this.showCape;

            case 15:
                return this.touchscreen;

            default:
                return false;
        }
    }

    /**
     * Returns the translation of the given index in the given String array. If the index is smaller than 0 or greater
     * than/equal to the length of the String array, it is changed to 0.
     */
    private static String getTranslation(String[] par0ArrayOfStr, int par1)
    {
        if (par1 < 0 || par1 >= par0ArrayOfStr.length)
        {
            par1 = 0;
        }

        StringTranslate var2 = StringTranslate.getInstance();
        return var2.translateKey(par0ArrayOfStr[par1]);
    }

    /**
     * Gets a key binding.
     */
    public String getKeyBinding(EnumOptions par1EnumOptions)
    {
        StringTranslate var2 = StringTranslate.getInstance();
        String var3 = var2.translateKey(par1EnumOptions.getEnumString());

        if (var3 == null)
        {
            var3 = par1EnumOptions.getEnumString();
        }

        String var4 = var3 + ": ";

        if (par1EnumOptions.getEnumFloat())
        {
            float var9 = this.getOptionFloatValue(par1EnumOptions);

            if (par1EnumOptions == EnumOptions.SENSITIVITY)
            {
                return var9 == 0.0F ? var4 + var2.translateKey("options.sensitivity.min") : (var9 == 1.0F ? var4 + var2.translateKey("options.sensitivity.max") : var4 + (int)(var9 * 200.0F) + "%");
            }
            else if (par1EnumOptions == EnumOptions.FOV)
            {
                return var9 == 0.0F ? var4 + var2.translateKey("options.fov.min") : (var9 == 1.0F ? var4 + var2.translateKey("options.fov.max") : var4 + (int)(70.0F + var9 * 40.0F));
            }
            else if (par1EnumOptions == EnumOptions.GAMMA)
            {
                return var9 == 0.0F ? var4 + var2.translateKey("options.gamma.min") : (var9 == 1.0F ? var4 + var2.translateKey("options.gamma.max") : var4 + "+" + (int)(var9 * 100.0F) + "%");
            }
            else if (par1EnumOptions == EnumOptions.RENDER_DISTANCE_FINE)
            {
                String var6 = "Tiny";
                short var7 = 32;

                if (this.ofRenderDistanceFine >= 64)
                {
                    var6 = "Short";
                    var7 = 64;
                }

                if (this.ofRenderDistanceFine >= 128)
                {
                    var6 = "Normal";
                    var7 = 128;
                }

                if (this.ofRenderDistanceFine >= 256)
                {
                    var6 = "Far";
                    var7 = 256;
                }

                if (this.ofRenderDistanceFine >= 512)
                {
                    var6 = "Extreme";
                    var7 = 512;
                }

                int var8 = this.ofRenderDistanceFine - var7;
                return var8 == 0 ? var4 + var6 : var4 + var6 + " +" + var8;
            }
            else
            {
                return par1EnumOptions == EnumOptions.FRAMERATE_LIMIT_FINE ? (this.ofLimitFramerateFine > 0 && this.ofLimitFramerateFine < 200 ? var4 + " " + this.ofLimitFramerateFine + " FPS" : (this.enableVsync ? var4 + " VSync" : var4 + " MaxFPS")) : (par1EnumOptions == EnumOptions.CHAT_OPACITY ? var4 + (int)(var9 * 90.0F + 10.0F) + "%" : (var9 == 0.0F ? var4 + var2.translateKey("options.off") : var4 + (int)(var9 * 100.0F) + "%"));
            }
        }
        else if (par1EnumOptions == EnumOptions.ADVANCED_OPENGL)
        {
            return !this.advancedOpengl ? var4 + "OFF" : (this.ofOcclusionFancy ? var4 + "Fancy" : var4 + "Fast");
        }
        else if (par1EnumOptions.getEnumBoolean())
        {
            boolean var5 = this.getOptionOrdinalValue(par1EnumOptions);
            return var5 ? var4 + var2.translateKey("options.on") : var4 + var2.translateKey("options.off");
        }
        else if (par1EnumOptions == EnumOptions.RENDER_DISTANCE)
        {
            return var4 + getTranslation(RENDER_DISTANCES, this.renderDistance);
        }
        else if (par1EnumOptions == EnumOptions.DIFFICULTY)
        {
            return var4 + getTranslation(DIFFICULTIES, this.difficulty);
        }
        else if (par1EnumOptions == EnumOptions.GUI_SCALE)
        {
            return var4 + getTranslation(GUISCALES, this.guiScale);
        }
        else if (par1EnumOptions == EnumOptions.CHAT_VISIBILITY)
        {
            return var4 + getTranslation(CHAT_VISIBILITIES, this.chatVisibility);
        }
        else if (par1EnumOptions == EnumOptions.PARTICLES)
        {
            return var4 + getTranslation(PARTICLES, this.particleSetting);
        }
        else if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT)
        {
            return var4 + getTranslation(LIMIT_FRAMERATES, this.limitFramerate);
        }
        else if (par1EnumOptions == EnumOptions.FOG_FANCY)
        {
            switch (this.ofFogType)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                case 3:
                    return var4 + "OFF";

                default:
                    return var4 + "OFF";
            }
        }
        else if (par1EnumOptions == EnumOptions.FOG_START)
        {
            return var4 + this.ofFogStart;
        }
        else if (par1EnumOptions == EnumOptions.MIPMAP_LEVEL)
        {
            return this.ofMipmapLevel == 0 ? var4 + "OFF" : (this.ofMipmapLevel == 4 ? var4 + "Max" : var4 + this.ofMipmapLevel);
        }
        else if (par1EnumOptions == EnumOptions.MIPMAP_TYPE)
        {
            return this.ofMipmapLinear ? var4 + "Linear" : var4 + "Nearest";
        }
        else if (par1EnumOptions == EnumOptions.LOAD_FAR)
        {
            return this.ofLoadFar ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.PRELOADED_CHUNKS)
        {
            return this.ofPreloadedChunks == 0 ? var4 + "OFF" : var4 + this.ofPreloadedChunks;
        }
        else if (par1EnumOptions == EnumOptions.SMOOTH_FPS)
        {
            return this.ofSmoothFps ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.SMOOTH_WORLD)
        {
            return this.ofSmoothWorld ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.CLOUDS)
        {
            switch (this.ofClouds)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                case 3:
                    return var4 + "OFF";

                default:
                    return var4 + "Default";
            }
        }
        else if (par1EnumOptions == EnumOptions.TREES)
        {
            switch (this.ofTrees)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                default:
                    return var4 + "Default";
            }
        }
        else if (par1EnumOptions == EnumOptions.GRASS)
        {
            switch (this.ofGrass)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                default:
                    return var4 + "Default";
            }
        }
        else if (par1EnumOptions == EnumOptions.DROPPED_ITEMS)
        {
            switch (this.ofDroppedItems)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                default:
                    return var4 + "Default";
            }
        }
        else if (par1EnumOptions == EnumOptions.RAIN)
        {
            switch (this.ofRain)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                case 3:
                    return var4 + "OFF";

                default:
                    return var4 + "Default";
            }
        }
        else if (par1EnumOptions == EnumOptions.WATER)
        {
            switch (this.ofWater)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                case 3:
                    return var4 + "OFF";

                default:
                    return var4 + "Default";
            }
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_WATER)
        {
            switch (this.ofAnimatedWater)
            {
                case 1:
                    return var4 + "Dynamic";

                case 2:
                    return var4 + "OFF";

                default:
                    return var4 + "ON";
            }
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_LAVA)
        {
            switch (this.ofAnimatedLava)
            {
                case 1:
                    return var4 + "Dynamic";

                case 2:
                    return var4 + "OFF";

                default:
                    return var4 + "ON";
            }
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_FIRE)
        {
            return this.ofAnimatedFire ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_PORTAL)
        {
            return this.ofAnimatedPortal ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_REDSTONE)
        {
            return this.ofAnimatedRedstone ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_EXPLOSION)
        {
            return this.ofAnimatedExplosion ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_FLAME)
        {
            return this.ofAnimatedFlame ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_SMOKE)
        {
            return this.ofAnimatedSmoke ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.VOID_PARTICLES)
        {
            return this.ofVoidParticles ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.WATER_PARTICLES)
        {
            return this.ofWaterParticles ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.PORTAL_PARTICLES)
        {
            return this.ofPortalParticles ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.DRIPPING_WATER_LAVA)
        {
            return this.ofDrippingWaterLava ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_TERRAIN)
        {
            return this.ofAnimatedTerrain ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_TEXTURES)
        {
            return this.ofAnimatedTextures ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.ANIMATED_ITEMS)
        {
            return this.ofAnimatedItems ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.RAIN_SPLASH)
        {
            return this.ofRainSplash ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.LAGOMETER)
        {
            return this.ofLagometer ? var4 + "ON" : var4 + "OFF";
        }
        else if (par1EnumOptions == EnumOptions.AUTOSAVE_TICKS)
        {
            return this.ofAutoSaveTicks <= 40 ? var4 + "Default (2s)" : (this.ofAutoSaveTicks <= 400 ? var4 + "20s" : (this.ofAutoSaveTicks <= 4000 ? var4 + "3min" : var4 + "30min"));
        }
        else if (par1EnumOptions == EnumOptions.BETTER_GRASS)
        {
            switch (this.ofBetterGrass)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                default:
                    return var4 + "OFF";
            }
        }
        else if (par1EnumOptions == EnumOptions.CONNECTED_TEXTURES)
        {
            switch (this.ofConnectedTextures)
            {
                case 1:
                    return var4 + "Fast";

                case 2:
                    return var4 + "Fancy";

                default:
                    return var4 + "OFF";
            }
        }
        else
        {
            return par1EnumOptions == EnumOptions.WEATHER ? (this.ofWeather ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.SKY ? (this.ofSky ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.STARS ? (this.ofStars ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.SUN_MOON ? (this.ofSunMoon ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.CHUNK_UPDATES ? var4 + this.ofChunkUpdates : (par1EnumOptions == EnumOptions.CHUNK_LOADING ? (this.ofChunkLoading == 1 ? var4 + "Smooth" : (this.ofChunkLoading == 2 ? var4 + "Multi-Core" : var4 + "Default")) : (par1EnumOptions == EnumOptions.CHUNK_UPDATES_DYNAMIC ? (this.ofChunkUpdatesDynamic ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.TIME ? (this.ofTime == 1 ? var4 + "Day Only" : (this.ofTime == 3 ? var4 + "Night Only" : var4 + "Default")) : (par1EnumOptions == EnumOptions.CLEAR_WATER ? (this.ofClearWater ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.DEPTH_FOG ? (this.ofDepthFog ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.AA_LEVEL ? (this.ofAaLevel == 0 ? var4 + "OFF" : var4 + this.ofAaLevel) : (par1EnumOptions == EnumOptions.AF_LEVEL ? (this.ofAfLevel == 1 ? var4 + "OFF" : var4 + this.ofAfLevel) : (par1EnumOptions == EnumOptions.PROFILER ? (this.ofProfiler ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.BETTER_SNOW ? (this.ofBetterSnow ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.SWAMP_COLORS ? (this.ofSwampColors ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.RANDOM_MOBS ? (this.ofRandomMobs ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.SMOOTH_BIOMES ? (this.ofSmoothBiomes ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.CUSTOM_FONTS ? (this.ofCustomFonts ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.CUSTOM_COLORS ? (this.ofCustomColors ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.SHOW_CAPES ? (this.ofShowCapes ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.NATURAL_TEXTURES ? (this.ofNaturalTextures ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.LAZY_CHUNK_LOADING ? (this.ofLazyChunkLoading ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.FULLSCREEN_MODE ? var4 + this.ofFullscreenMode : (par1EnumOptions == EnumOptions.HELD_ITEM_TOOLTIPS ? (this.field_92117_D ? var4 + "ON" : var4 + "OFF") : (par1EnumOptions == EnumOptions.GRAPHICS ? (this.fancyGraphics ? var4 + var2.translateKey("options.graphics.fancy") : var4 + var2.translateKey("options.graphics.fast")) : var4))))))))))))))))))))))));
        }
    }

    /**
     * Loads the options from the options file. It appears that this has replaced the previous 'loadOptions'
     */
    public void loadOptions()
    {
        try
        {
            if (!this.optionsFile.exists())
            {
                return;
            }

            BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
            String var2 = "";

            while ((var2 = var1.readLine()) != null)
            {
                try
                {
                    String[] var3 = var2.split(":");

                    if (var3[0].equals("music"))
                    {
                        this.musicVolume = this.parseFloat(var3[1]);
                    }

                    if (var3[0].equals("sound"))
                    {
                        this.soundVolume = this.parseFloat(var3[1]);
                    }

                    if (var3[0].equals("mouseSensitivity"))
                    {
                        this.mouseSensitivity = this.parseFloat(var3[1]);
                    }

                    if (var3[0].equals("fov"))
                    {
                        this.fovSetting = this.parseFloat(var3[1]);
                    }

                    if (var3[0].equals("gamma"))
                    {
                        this.gammaSetting = this.parseFloat(var3[1]);
                    }

                    if (var3[0].equals("invertYMouse"))
                    {
                        this.invertMouse = var3[1].equals("true");
                    }

                    if (var3[0].equals("viewDistance"))
                    {
                        this.renderDistance = Integer.parseInt(var3[1]);
                        this.ofRenderDistanceFine = renderDistanceToFine(this.renderDistance);
                    }

                    if (var3[0].equals("guiScale"))
                    {
                        this.guiScale = Integer.parseInt(var3[1]);
                    }

                    if (var3[0].equals("particles"))
                    {
                        this.particleSetting = Integer.parseInt(var3[1]);
                    }

                    if (var3[0].equals("bobView"))
                    {
                        this.viewBobbing = var3[1].equals("true");
                    }

                    if (var3[0].equals("anaglyph3d"))
                    {
                        this.anaglyph = var3[1].equals("true");
                    }

                    if (var3[0].equals("advancedOpengl"))
                    {
                        this.advancedOpengl = var3[1].equals("true");
                    }

                    if (var3[0].equals("fpsLimit"))
                    {
                        this.limitFramerate = Integer.parseInt(var3[1]);
                        this.ofLimitFramerateFine = limitFramerateToFine(this.limitFramerate);
                    }

                    if (var3[0].equals("difficulty"))
                    {
                        this.difficulty = Integer.parseInt(var3[1]);
                    }

                    if (var3[0].equals("fancyGraphics"))
                    {
                        this.fancyGraphics = var3[1].equals("true");
                    }

                    if (var3[0].equals("ao"))
                    {
                        this.ambientOcclusion = var3[1].equals("true");

                        if (this.ambientOcclusion)
                        {
                            this.ofAoLevel = 1.0F;
                        }
                        else
                        {
                            this.ofAoLevel = 0.0F;
                        }
                    }

                    if (var3[0].equals("clouds"))
                    {
                        this.clouds = var3[1].equals("true");
                    }

                    if (var3[0].equals("skin"))
                    {
                        this.skin = var3[1];
                    }

                    if (var3[0].equals("lastServer") && var3.length >= 2)
                    {
                        this.lastServer = var3[1];
                    }

                    if (var3[0].equals("lang") && var3.length >= 2)
                    {
                        this.language = var3[1];
                    }

                    if (var3[0].equals("chatVisibility"))
                    {
                        this.chatVisibility = Integer.parseInt(var3[1]);
                    }

                    if (var3[0].equals("chatColors"))
                    {
                        this.chatColours = var3[1].equals("true");
                    }

                    if (var3[0].equals("chatLinks"))
                    {
                        this.chatLinks = var3[1].equals("true");
                    }

                    if (var3[0].equals("chatLinksPrompt"))
                    {
                        this.chatLinksPrompt = var3[1].equals("true");
                    }

                    if (var3[0].equals("chatOpacity"))
                    {
                        this.chatOpacity = this.parseFloat(var3[1]);
                    }

                    if (var3[0].equals("serverTextures"))
                    {
                        this.serverTextures = var3[1].equals("true");
                    }

                    if (var3[0].equals("snooperEnabled"))
                    {
                        this.snooperEnabled = var3[1].equals("true");
                    }

                    if (var3[0].equals("fullscreen"))
                    {
                        this.fullScreen = var3[1].equals("true");
                    }

                    if (var3[0].equals("enableVsync"))
                    {
                        this.enableVsync = var3[1].equals("true");
                        this.updateVSync();
                    }

                    if (var3[0].equals("hideServerAddress"))
                    {
                        this.hideServerAddress = var3[1].equals("true");
                    }

                    if (var3[0].equals("advancedItemTooltips"))
                    {
                        this.advancedItemTooltips = var3[1].equals("true");
                    }

                    if (var3[0].equals("pauseOnLostFocus"))
                    {
                        this.pauseOnLostFocus = var3[1].equals("true");
                    }

                    if (var3[0].equals("showCape"))
                    {
                        this.showCape = var3[1].equals("true");
                    }

                    if (var3[0].equals("touchscreen"))
                    {
                        this.touchscreen = var3[1].equals("true");
                    }

                    if (var3[0].equals("overrideHeight"))
                    {
                        this.field_92119_C = Integer.parseInt(var3[1]);
                    }

                    if (var3[0].equals("overrideWidth"))
                    {
                        this.field_92118_B = Integer.parseInt(var3[1]);
                    }

                    if (var3[0].equals("heldItemTooltips"))
                    {
                        this.field_92117_D = var3[1].equals("true");
                    }

                    for (int var4 = 0; var4 < this.keyBindings.length; ++var4)
                    {
                        if (var3[0].equals("key_" + this.keyBindings[var4].keyDescription))
                        {
                            this.keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
                        }
                    }
                }
                catch (Exception var7)
                {
                    System.out.println("Skipping bad option: " + var2);
                    var7.printStackTrace();
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            var1.close();
        }
        catch (Exception var8)
        {
            System.out.println("Failed to load options");
            var8.printStackTrace();
        }

        try
        {
            File var9 = this.optionsFileOF;

            if (!var9.exists())
            {
                var9 = this.optionsFile;
            }

            if (!var9.exists())
            {
                return;
            }

            BufferedReader var10 = new BufferedReader(new FileReader(var9));
            String var11 = "";

            while ((var11 = var10.readLine()) != null)
            {
                try
                {
                    String[] var12 = var11.split(":");

                    if (var12[0].equals("ofRenderDistanceFine") && var12.length >= 2)
                    {
                        this.ofRenderDistanceFine = Integer.valueOf(var12[1]).intValue();
                        this.ofRenderDistanceFine = Config.limit(this.ofRenderDistanceFine, 32, 512);
                        this.renderDistance = fineToRenderDistance(this.ofRenderDistanceFine);
                    }

                    if (var12[0].equals("ofLimitFramerateFine") && var12.length >= 2)
                    {
                        this.ofLimitFramerateFine = Integer.valueOf(var12[1]).intValue();
                        this.ofLimitFramerateFine = Config.limit(this.ofLimitFramerateFine, 0, 199);
                        this.limitFramerate = fineToLimitFramerate(this.ofLimitFramerateFine);
                    }

                    if (var12[0].equals("ofFogType") && var12.length >= 2)
                    {
                        this.ofFogType = Integer.valueOf(var12[1]).intValue();
                        this.ofFogType = Config.limit(this.ofFogType, 1, 3);
                    }

                    if (var12[0].equals("ofFogStart") && var12.length >= 2)
                    {
                        this.ofFogStart = Float.valueOf(var12[1]).floatValue();

                        if (this.ofFogStart < 0.2F)
                        {
                            this.ofFogStart = 0.2F;
                        }

                        if (this.ofFogStart > 0.81F)
                        {
                            this.ofFogStart = 0.8F;
                        }
                    }

                    if (var12[0].equals("ofMipmapLevel") && var12.length >= 2)
                    {
                        this.ofMipmapLevel = Integer.valueOf(var12[1]).intValue();

                        if (this.ofMipmapLevel < 0)
                        {
                            this.ofMipmapLevel = 0;
                        }

                        if (this.ofMipmapLevel > 4)
                        {
                            this.ofMipmapLevel = 4;
                        }
                    }

                    if (var12[0].equals("ofMipmapLinear") && var12.length >= 2)
                    {
                        this.ofMipmapLinear = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofLoadFar") && var12.length >= 2)
                    {
                        this.ofLoadFar = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofPreloadedChunks") && var12.length >= 2)
                    {
                        this.ofPreloadedChunks = Integer.valueOf(var12[1]).intValue();

                        if (this.ofPreloadedChunks < 0)
                        {
                            this.ofPreloadedChunks = 0;
                        }

                        if (this.ofPreloadedChunks > 8)
                        {
                            this.ofPreloadedChunks = 8;
                        }
                    }

                    if (var12[0].equals("ofOcclusionFancy") && var12.length >= 2)
                    {
                        this.ofOcclusionFancy = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofSmoothFps") && var12.length >= 2)
                    {
                        this.ofSmoothFps = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofSmoothWorld") && var12.length >= 2)
                    {
                        this.ofSmoothWorld = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAoLevel") && var12.length >= 2)
                    {
                        this.ofAoLevel = Float.valueOf(var12[1]).floatValue();
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
                        this.ambientOcclusion = this.ofAoLevel > 0.0F;
                    }

                    if (var12[0].equals("ofClouds") && var12.length >= 2)
                    {
                        this.ofClouds = Integer.valueOf(var12[1]).intValue();
                        this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                    }

                    if (var12[0].equals("ofCloudsHeight") && var12.length >= 2)
                    {
                        this.ofCloudsHeight = Float.valueOf(var12[1]).floatValue();
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
                    }

                    if (var12[0].equals("ofTrees") && var12.length >= 2)
                    {
                        this.ofTrees = Integer.valueOf(var12[1]).intValue();
                        this.ofTrees = Config.limit(this.ofTrees, 0, 2);
                    }

                    if (var12[0].equals("ofGrass") && var12.length >= 2)
                    {
                        this.ofGrass = Integer.valueOf(var12[1]).intValue();
                        this.ofGrass = Config.limit(this.ofGrass, 0, 2);
                    }

                    if (var12[0].equals("ofDroppedItems") && var12.length >= 2)
                    {
                        this.ofDroppedItems = Integer.valueOf(var12[1]).intValue();
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
                    }

                    if (var12[0].equals("ofRain") && var12.length >= 2)
                    {
                        this.ofRain = Integer.valueOf(var12[1]).intValue();
                        this.ofRain = Config.limit(this.ofRain, 0, 3);
                    }

                    if (var12[0].equals("ofWater") && var12.length >= 2)
                    {
                        this.ofWater = Integer.valueOf(var12[1]).intValue();
                        this.ofWater = Config.limit(this.ofWater, 0, 3);
                    }

                    if (var12[0].equals("ofAnimatedWater") && var12.length >= 2)
                    {
                        this.ofAnimatedWater = Integer.valueOf(var12[1]).intValue();
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
                    }

                    if (var12[0].equals("ofAnimatedLava") && var12.length >= 2)
                    {
                        this.ofAnimatedLava = Integer.valueOf(var12[1]).intValue();
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
                    }

                    if (var12[0].equals("ofAnimatedFire") && var12.length >= 2)
                    {
                        this.ofAnimatedFire = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedPortal") && var12.length >= 2)
                    {
                        this.ofAnimatedPortal = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedRedstone") && var12.length >= 2)
                    {
                        this.ofAnimatedRedstone = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedExplosion") && var12.length >= 2)
                    {
                        this.ofAnimatedExplosion = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedFlame") && var12.length >= 2)
                    {
                        this.ofAnimatedFlame = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedSmoke") && var12.length >= 2)
                    {
                        this.ofAnimatedSmoke = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofVoidParticles") && var12.length >= 2)
                    {
                        this.ofVoidParticles = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofWaterParticles") && var12.length >= 2)
                    {
                        this.ofWaterParticles = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofPortalParticles") && var12.length >= 2)
                    {
                        this.ofPortalParticles = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofDrippingWaterLava") && var12.length >= 2)
                    {
                        this.ofDrippingWaterLava = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedTerrain") && var12.length >= 2)
                    {
                        this.ofAnimatedTerrain = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedTextures") && var12.length >= 2)
                    {
                        this.ofAnimatedTextures = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAnimatedItems") && var12.length >= 2)
                    {
                        this.ofAnimatedItems = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofRainSplash") && var12.length >= 2)
                    {
                        this.ofRainSplash = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofLagometer") && var12.length >= 2)
                    {
                        this.ofLagometer = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAutoSaveTicks") && var12.length >= 2)
                    {
                        this.ofAutoSaveTicks = Integer.valueOf(var12[1]).intValue();
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
                    }

                    if (var12[0].equals("ofBetterGrass") && var12.length >= 2)
                    {
                        this.ofBetterGrass = Integer.valueOf(var12[1]).intValue();
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
                    }

                    if (var12[0].equals("ofConnectedTextures") && var12.length >= 2)
                    {
                        this.ofConnectedTextures = Integer.valueOf(var12[1]).intValue();
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
                    }

                    if (var12[0].equals("ofWeather") && var12.length >= 2)
                    {
                        this.ofWeather = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofSky") && var12.length >= 2)
                    {
                        this.ofSky = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofStars") && var12.length >= 2)
                    {
                        this.ofStars = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofSunMoon") && var12.length >= 2)
                    {
                        this.ofSunMoon = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofChunkUpdates") && var12.length >= 2)
                    {
                        this.ofChunkUpdates = Integer.valueOf(var12[1]).intValue();
                        this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
                    }

                    if (var12[0].equals("ofChunkLoading") && var12.length >= 2)
                    {
                        this.ofChunkLoading = Integer.valueOf(var12[1]).intValue();
                        this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
                        this.updateChunkLoading();
                    }

                    if (var12[0].equals("ofChunkUpdatesDynamic") && var12.length >= 2)
                    {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofTime") && var12.length >= 2)
                    {
                        this.ofTime = Integer.valueOf(var12[1]).intValue();
                        this.ofTime = Config.limit(this.ofTime, 0, 3);
                    }

                    if (var12[0].equals("ofClearWater") && var12.length >= 2)
                    {
                        this.ofClearWater = Boolean.valueOf(var12[1]).booleanValue();
                        this.updateWaterOpacity();
                    }

                    if (var12[0].equals("ofDepthFog") && var12.length >= 2)
                    {
                        this.ofDepthFog = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofAaLevel") && var12.length >= 2)
                    {
                        this.ofAaLevel = Integer.valueOf(var12[1]).intValue();
                        this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
                    }

                    if (var12[0].equals("ofAfLevel") && var12.length >= 2)
                    {
                        this.ofAfLevel = Integer.valueOf(var12[1]).intValue();
                        this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
                    }

                    if (var12[0].equals("ofProfiler") && var12.length >= 2)
                    {
                        this.ofProfiler = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofBetterSnow") && var12.length >= 2)
                    {
                        this.ofBetterSnow = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofSwampColors") && var12.length >= 2)
                    {
                        this.ofSwampColors = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofRandomMobs") && var12.length >= 2)
                    {
                        this.ofRandomMobs = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofSmoothBiomes") && var12.length >= 2)
                    {
                        this.ofSmoothBiomes = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofCustomFonts") && var12.length >= 2)
                    {
                        this.ofCustomFonts = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofCustomColors") && var12.length >= 2)
                    {
                        this.ofCustomColors = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofShowCapes") && var12.length >= 2)
                    {
                        this.ofShowCapes = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofNaturalTextures") && var12.length >= 2)
                    {
                        this.ofNaturalTextures = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofLazyChunkLoading") && var12.length >= 2)
                    {
                        this.ofLazyChunkLoading = Boolean.valueOf(var12[1]).booleanValue();
                    }

                    if (var12[0].equals("ofFullscreenMode") && var12.length >= 2)
                    {
                        this.ofFullscreenMode = var12[1];
                    }
                }
                catch (Exception var5)
                {
                    System.out.println("Skipping bad option: " + var11);
                    var5.printStackTrace();
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            var10.close();
        }
        catch (Exception var6)
        {
            System.out.println("Failed to load options");
            var6.printStackTrace();
        }
    }

    /**
     * Parses a string into a float.
     */
    private float parseFloat(String par1Str)
    {
        return par1Str.equals("true") ? 1.0F : (par1Str.equals("false") ? 0.0F : Float.parseFloat(par1Str));
    }

    /**
     * Saves the options to the options file.
     */
    public void saveOptions()
    {
        if (Reflector.FMLClientHandler.exists())
        {
            Object var1 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);

            if (var1 != null && Reflector.callBoolean(var1, Reflector.FMLClientHandler_isLoading, new Object[0]))
            {
                return;
            }
        }

        PrintWriter var5;

        try
        {
            var5 = new PrintWriter(new FileWriter(this.optionsFile));
            var5.println("music:" + this.musicVolume);
            var5.println("sound:" + this.soundVolume);
            var5.println("invertYMouse:" + this.invertMouse);
            var5.println("mouseSensitivity:" + this.mouseSensitivity);
            var5.println("fov:" + this.fovSetting);
            var5.println("gamma:" + this.gammaSetting);
            var5.println("viewDistance:" + this.renderDistance);
            var5.println("guiScale:" + this.guiScale);
            var5.println("particles:" + this.particleSetting);
            var5.println("bobView:" + this.viewBobbing);
            var5.println("anaglyph3d:" + this.anaglyph);
            var5.println("advancedOpengl:" + this.advancedOpengl);
            var5.println("fpsLimit:" + this.limitFramerate);
            var5.println("difficulty:" + this.difficulty);
            var5.println("fancyGraphics:" + this.fancyGraphics);
            var5.println("ao:" + this.ambientOcclusion);
            var5.println("clouds:" + this.clouds);
            var5.println("skin:" + this.skin);
            var5.println("lastServer:" + this.lastServer);
            var5.println("lang:" + this.language);
            var5.println("chatVisibility:" + this.chatVisibility);
            var5.println("chatColors:" + this.chatColours);
            var5.println("chatLinks:" + this.chatLinks);
            var5.println("chatLinksPrompt:" + this.chatLinksPrompt);
            var5.println("chatOpacity:" + this.chatOpacity);
            var5.println("serverTextures:" + this.serverTextures);
            var5.println("snooperEnabled:" + this.snooperEnabled);
            var5.println("fullscreen:" + this.fullScreen);
            var5.println("enableVsync:" + this.enableVsync);
            var5.println("hideServerAddress:" + this.hideServerAddress);
            var5.println("advancedItemTooltips:" + this.advancedItemTooltips);
            var5.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            var5.println("showCape:" + this.showCape);
            var5.println("touchscreen:" + this.touchscreen);
            var5.println("overrideWidth:" + this.field_92118_B);
            var5.println("overrideHeight:" + this.field_92119_C);
            var5.println("heldItemTooltips:" + this.field_92117_D);

            for (int var2 = 0; var2 < this.keyBindings.length; ++var2)
            {
                var5.println("key_" + this.keyBindings[var2].keyDescription + ":" + this.keyBindings[var2].keyCode);
            }

            var5.close();
        }
        catch (Exception var4)
        {
            System.out.println("Failed to save options");
            var4.printStackTrace();
        }

        try
        {
            var5 = new PrintWriter(new FileWriter(this.optionsFileOF));
            var5.println("ofRenderDistanceFine:" + this.ofRenderDistanceFine);
            var5.println("ofLimitFramerateFine:" + this.ofLimitFramerateFine);
            var5.println("ofFogType:" + this.ofFogType);
            var5.println("ofFogStart:" + this.ofFogStart);
            var5.println("ofMipmapLevel:" + this.ofMipmapLevel);
            var5.println("ofMipmapLinear:" + this.ofMipmapLinear);
            var5.println("ofLoadFar:" + this.ofLoadFar);
            var5.println("ofPreloadedChunks:" + this.ofPreloadedChunks);
            var5.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
            var5.println("ofSmoothFps:" + this.ofSmoothFps);
            var5.println("ofSmoothWorld:" + this.ofSmoothWorld);
            var5.println("ofAoLevel:" + this.ofAoLevel);
            var5.println("ofClouds:" + this.ofClouds);
            var5.println("ofCloudsHeight:" + this.ofCloudsHeight);
            var5.println("ofTrees:" + this.ofTrees);
            var5.println("ofGrass:" + this.ofGrass);
            var5.println("ofDroppedItems:" + this.ofDroppedItems);
            var5.println("ofRain:" + this.ofRain);
            var5.println("ofWater:" + this.ofWater);
            var5.println("ofAnimatedWater:" + this.ofAnimatedWater);
            var5.println("ofAnimatedLava:" + this.ofAnimatedLava);
            var5.println("ofAnimatedFire:" + this.ofAnimatedFire);
            var5.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
            var5.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
            var5.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
            var5.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
            var5.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
            var5.println("ofVoidParticles:" + this.ofVoidParticles);
            var5.println("ofWaterParticles:" + this.ofWaterParticles);
            var5.println("ofPortalParticles:" + this.ofPortalParticles);
            var5.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
            var5.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
            var5.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
            var5.println("ofAnimatedItems:" + this.ofAnimatedItems);
            var5.println("ofRainSplash:" + this.ofRainSplash);
            var5.println("ofLagometer:" + this.ofLagometer);
            var5.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
            var5.println("ofBetterGrass:" + this.ofBetterGrass);
            var5.println("ofConnectedTextures:" + this.ofConnectedTextures);
            var5.println("ofWeather:" + this.ofWeather);
            var5.println("ofSky:" + this.ofSky);
            var5.println("ofStars:" + this.ofStars);
            var5.println("ofSunMoon:" + this.ofSunMoon);
            var5.println("ofChunkUpdates:" + this.ofChunkUpdates);
            var5.println("ofChunkLoading:" + this.ofChunkLoading);
            var5.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
            var5.println("ofTime:" + this.ofTime);
            var5.println("ofClearWater:" + this.ofClearWater);
            var5.println("ofDepthFog:" + this.ofDepthFog);
            var5.println("ofAaLevel:" + this.ofAaLevel);
            var5.println("ofAfLevel:" + this.ofAfLevel);
            var5.println("ofProfiler:" + this.ofProfiler);
            var5.println("ofBetterSnow:" + this.ofBetterSnow);
            var5.println("ofSwampColors:" + this.ofSwampColors);
            var5.println("ofRandomMobs:" + this.ofRandomMobs);
            var5.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
            var5.println("ofCustomFonts:" + this.ofCustomFonts);
            var5.println("ofCustomColors:" + this.ofCustomColors);
            var5.println("ofShowCapes:" + this.ofShowCapes);
            var5.println("ofNaturalTextures:" + this.ofNaturalTextures);
            var5.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
            var5.println("ofFullscreenMode:" + this.ofFullscreenMode);
            var5.close();
        }
        catch (Exception var3)
        {
            System.out.println("Failed to save options");
            var3.printStackTrace();
        }

        this.sendSettingsToServer();
    }

    /**
     * Send a client info packet with settings information to the server
     */
    public void sendSettingsToServer()
    {
        if (this.mc.thePlayer != null)
        {
            this.mc.thePlayer.sendQueue.addToSendQueue(new Packet204ClientInfo(this.language, this.renderDistance, this.chatVisibility, this.chatColours, this.difficulty, this.showCape));
        }
    }

    public void resetSettings()
    {
        this.renderDistance = 1;
        this.ofRenderDistanceFine = renderDistanceToFine(this.renderDistance);
        this.viewBobbing = true;
        this.anaglyph = false;
        this.advancedOpengl = false;
        this.limitFramerate = 0;
        this.enableVsync = false;
        this.updateVSync();
        this.ofLimitFramerateFine = 0;
        this.fancyGraphics = true;
        this.ambientOcclusion = true;
        this.clouds = true;
        this.fovSetting = 0.0F;
        this.gammaSetting = 0.0F;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.field_92117_D = true;
        this.ofFogType = 1;
        this.ofFogStart = 0.8F;
        this.ofMipmapLevel = 0;
        this.ofMipmapLinear = false;
        this.ofLoadFar = false;
        this.ofPreloadedChunks = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();

        if (this.ambientOcclusion)
        {
            this.ofAoLevel = 1.0F;
        }
        else
        {
            this.ofAoLevel = 0.0F;
        }

        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0F;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofChunkUpdates = 1;
        this.ofChunkLoading = 0;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.mc.renderGlobal.updateCapes();
        this.updateWaterOpacity();
        this.mc.renderGlobal.setAllRenderersVisible();
        this.mc.renderEngine.refreshTextures();
        this.mc.renderGlobal.loadRenderers();
        this.saveOptions();
    }

    public void updateVSync()
    {
        Display.setVSyncEnabled(this.enableVsync);
    }

    private static int fineToRenderDistance(int var0)
    {
        byte var1 = 3;

        if (var0 > 32)
        {
            var1 = 2;
        }

        if (var0 > 64)
        {
            var1 = 1;
        }

        if (var0 > 128)
        {
            var1 = 0;
        }

        return var1;
    }

    private static int renderDistanceToFine(int var0)
    {
        return 32 << 3 - var0;
    }

    private static int fineToLimitFramerate(int var0)
    {
        byte var1 = 2;

        if (var0 > 35)
        {
            var1 = 1;
        }

        if (var0 >= 200)
        {
            var1 = 0;
        }

        if (var0 <= 0)
        {
            var1 = 0;
        }

        return var1;
    }

    private static int limitFramerateToFine(int var0)
    {
        switch (var0)
        {
            case 0:
                return 0;

            case 1:
                return 120;

            case 2:
                return 35;

            default:
                return 0;
        }
    }

    /**
     * Should render clouds
     */
    public boolean shouldRenderClouds()
    {
        return this.ofRenderDistanceFine > 64 && this.clouds;
    }
}
