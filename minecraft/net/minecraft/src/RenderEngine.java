package net.minecraft.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

public class RenderEngine
{
    private HashMap textureMap = new HashMap();

    /** Texture contents map (key: texture name, value: int[] contents) */
    private HashMap textureContentsMap = new HashMap();

    /** A mapping from GL texture names (integers) to BufferedImage instances */
    private IntHashMap textureNameToImageMap = new IntHashMap();

    /** An IntBuffer storing 1 int used as scratch space in RenderEngine */
    private IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);

    /** Stores the image data for the texture. */
    private ByteBuffer imageData = GLAllocation.createDirectByteBuffer(16777216);
    public List textureList = new ArrayList();

    /** A mapping from image URLs to ThreadDownloadImageData instances */
    private Map urlToImageDataMap = new HashMap();

    /** Reference to the GameSettings object */
    private GameSettings options;

    /** Flag set when a texture should not be repeated */
    public boolean clampTexture = false;

    /** Flag set when a texture should use blurry resizing */
    public boolean blurTexture = false;

    /** Texture pack */
    public TexturePackList texturePack;

    /** Missing texture image */
    private BufferedImage missingTextureImage = new BufferedImage(64, 64, 2);
    public static boolean useMipmaps = true;
    public int terrainTextureId = -1;
    public int guiItemsTextureId = -1;
    private boolean hdTexturesInstalled = false;
    private Map textureDimensionsMap = new HashMap();
    private Map textureDataMap = new HashMap();
    private int tickCounter = 0;
    private ByteBuffer[] mipImageDatas;
    private boolean dynamicTexturesUpdated = false;
    private Map textureFxMap = new IdentityHashMap();
    private Map mipDataBufsMap = new HashMap();
    private boolean singleTileTexture = false;
    private Map customAnimationMap = new HashMap();
    private CustomAnimation[] textureAnimations = null;
    public static Logger log = Logger.getAnonymousLogger();

    public RenderEngine(TexturePackList par1TexturePackList, GameSettings par2GameSettings)
    {
        if (Config.isMultiTexture())
        {
            int var3 = Config.getAntialiasingLevel();
            Config.dbg("FSAA Samples: " + var3);

            try
            {
                Display.destroy();
                Display.create((new PixelFormat()).withDepthBits(24).withSamples(var3));
            }
            catch (LWJGLException var9)
            {
                Config.dbg("Error setting FSAA: " + var3 + "x");
                var9.printStackTrace();

                try
                {
                    Display.create((new PixelFormat()).withDepthBits(24));
                }
                catch (LWJGLException var8)
                {
                    var8.printStackTrace();

                    try
                    {
                        Display.create();
                    }
                    catch (LWJGLException var7)
                    {
                        var7.printStackTrace();
                    }
                }
            }
        }

        this.texturePack = par1TexturePackList;
        this.options = par2GameSettings;
        Graphics var10 = this.missingTextureImage.getGraphics();
        var10.setColor(Color.WHITE);
        var10.fillRect(0, 0, 64, 64);
        var10.setColor(Color.BLACK);
        var10.drawString("missingtex", 1, 10);
        var10.dispose();
        this.allocateImageData(256, 256);
    }

    public int[] getTextureContents(String par1Str)
    {
        ITexturePack var2 = this.texturePack.getSelectedTexturePack();
        int[] var3 = (int[])((int[])this.textureContentsMap.get(par1Str));

        if (var3 != null)
        {
            return var3;
        }
        else
        {
            int[] var5;

            try
            {
                Object var4 = null;

                if (par1Str.startsWith("##"))
                {
                    var5 = this.getImageContentsAndAllocate(this.unwrapImageByColumns(this.readTextureImage(var2.getResourceAsStream(par1Str.substring(2)))));
                }
                else if (par1Str.startsWith("%clamp%"))
                {
                    this.clampTexture = true;
                    var5 = this.getImageContentsAndAllocate(this.readTextureImage(var2.getResourceAsStream(par1Str.substring(7))));
                    this.clampTexture = false;
                }
                else if (par1Str.startsWith("%blur%"))
                {
                    this.blurTexture = true;
                    this.clampTexture = true;
                    var5 = this.getImageContentsAndAllocate(this.readTextureImage(var2.getResourceAsStream(par1Str.substring(6))));
                    this.clampTexture = false;
                    this.blurTexture = false;
                }
                else
                {
                    InputStream var6 = var2.getResourceAsStream(par1Str);

                    if (var6 == null)
                    {
                        var5 = this.getImageContentsAndAllocate(this.missingTextureImage);
                    }
                    else
                    {
                        var5 = this.getImageContentsAndAllocate(this.readTextureImage(var6));
                    }
                }

                this.textureContentsMap.put(par1Str, var5);
                return var5;
            }
            catch (IOException var7)
            {
                var7.printStackTrace();
                var5 = this.getImageContentsAndAllocate(this.missingTextureImage);
                this.textureContentsMap.put(par1Str, var5);
                return var5;
            }
        }
    }

    private int[] getImageContentsAndAllocate(BufferedImage par1BufferedImage)
    {
        int var2 = par1BufferedImage.getWidth();
        int var3 = par1BufferedImage.getHeight();
        int[] var4 = new int[var2 * var3];
        par1BufferedImage.getRGB(0, 0, var2, var3, var4, 0, var2);
        return var4;
    }

    private int[] getImageContents(BufferedImage par1BufferedImage, int[] par2ArrayOfInteger)
    {
        int var3 = par1BufferedImage.getWidth();
        int var4 = par1BufferedImage.getHeight();
        par1BufferedImage.getRGB(0, 0, var3, var4, par2ArrayOfInteger, 0, var3);
        return par2ArrayOfInteger;
    }

    public int getTexture(String par1Str)
    {
        Integer var2 = (Integer)this.textureMap.get(par1Str);

        if (var2 != null)
        {
            return var2.intValue();
        }
        else
        {
            ITexturePack var3 = this.texturePack.getSelectedTexturePack();

            try
            {
                if (Reflector.ForgeHooksClient.exists())
                {
                    Reflector.callVoid(Reflector.ForgeHooksClient_onTextureLoadPre, new Object[] {par1Str});
                }

                this.singleIntBuffer.clear();
                GLAllocation.generateTextureNames(this.singleIntBuffer);

                if (Tessellator.renderingWorldRenderer)
                {
                    System.out.printf("Warning: Texture %s not preloaded, will cause render glitches!\n", new Object[] {par1Str});
                }

                int var4 = this.singleIntBuffer.get(0);
                Config.dbg("setupTexture: \"" + par1Str + "\", id: " + var4);

                if (par1Str.startsWith("##"))
                {
                    this.setupTexture(this.unwrapImageByColumns(this.readTextureImage(var3.getResourceAsStream(par1Str.substring(2)))), var4);
                }
                else if (par1Str.startsWith("%clamp%"))
                {
                    this.clampTexture = true;

                    if (par1Str.equals("%clamp%/misc/shadow.png"))
                    {
                        useMipmaps = false;
                    }

                    this.setupTexture(this.readTextureImage(var3.getResourceAsStream(par1Str.substring(7))), var4);
                    useMipmaps = true;
                    this.clampTexture = false;
                }
                else if (par1Str.startsWith("%blur%"))
                {
                    this.blurTexture = true;
                    this.setupTexture(this.readTextureImage(var3.getResourceAsStream(par1Str.substring(6))), var4);
                    this.blurTexture = false;
                }
                else if (par1Str.startsWith("%blurclamp%"))
                {
                    this.blurTexture = true;
                    this.clampTexture = true;
                    this.setupTexture(this.readTextureImage(var3.getResourceAsStream(par1Str.substring(11))), var4);
                    this.blurTexture = false;
                    this.clampTexture = false;
                }
                else
                {
                    InputStream var8 = var3.getResourceAsStream(par1Str);

                    if (var8 == null)
                    {
                        this.setupTexture(this.missingTextureImage, var4);
                    }
                    else
                    {
                        if (par1Str.equals("/terrain.png"))
                        {
                            this.terrainTextureId = var4;
                        }

                        if (par1Str.equals("/gui/items.png"))
                        {
                            this.guiItemsTextureId = var4;
                        }

                        TextureUtils.textureCreated(par1Str, var4);
                        BufferedImage var6 = this.readTextureImage(var8);
                        var6 = TextureUtils.fixTextureDimensions(par1Str, var6);
                        this.setupTexture(var6, var4);
                    }
                }

                this.textureMap.put(par1Str, Integer.valueOf(var4));

                if (Reflector.ForgeHooksClient.exists())
                {
                    Reflector.callVoid(Reflector.ForgeHooksClient_onTextureLoad, new Object[] {par1Str, var3});
                }

                return var4;
            }
            catch (Exception var7)
            {
                var7.printStackTrace();
                GLAllocation.generateTextureNames(this.singleIntBuffer);
                int var5 = this.singleIntBuffer.get(0);
                this.setupTexture(this.missingTextureImage, var5);
                this.textureMap.put(par1Str, Integer.valueOf(var5));
                return var5;
            }
        }
    }

    /**
     * Takes an image with multiple 16-pixel-wide columns and creates a new 16-pixel-wide image where the columns are
     * stacked vertically
     */
    private BufferedImage unwrapImageByColumns(BufferedImage par1BufferedImage)
    {
        int var2 = par1BufferedImage.getWidth() / 16;
        BufferedImage var3 = new BufferedImage(16, par1BufferedImage.getHeight() * var2, 2);
        Graphics var4 = var3.getGraphics();

        for (int var5 = 0; var5 < var2; ++var5)
        {
            var4.drawImage(par1BufferedImage, -var5 * 16, var5 * par1BufferedImage.getHeight(), (ImageObserver)null);
        }

        var4.dispose();
        return var3;
    }

    /**
     * Copy the supplied image onto a newly-allocated OpenGL texture, returning the allocated texture name
     */
    public int allocateAndSetupTexture(BufferedImage par1BufferedImage)
    {
        this.singleIntBuffer.clear();
        GLAllocation.generateTextureNames(this.singleIntBuffer);
        int var2 = this.singleIntBuffer.get(0);
        this.setupTexture(par1BufferedImage, var2);
        this.textureNameToImageMap.addKey(var2, par1BufferedImage);
        return var2;
    }

    /**
     * Copy the supplied image onto the specified OpenGL texture
     */
    public void setupTexture(BufferedImage par1BufferedImage, int par2)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par2);
        boolean var3 = useMipmaps && Config.isUseMipmaps();
        int var4;
        int var5;

        if (var3 && par2 != this.guiItemsTextureId)
        {
            var4 = Config.getMipmapType();
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var4);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            if (GLContext.getCapabilities().OpenGL12)
            {
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
                var5 = Config.getMipmapLevel();

                if (var5 >= 4)
                {
                    int var6 = Math.min(par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
                    var5 = this.getMaxMipmapLevel(var6);

                    if (!this.singleTileTexture)
                    {
                        var5 -= 4;
                    }

                    if (var5 < 0)
                    {
                        var5 = 0;
                    }
                }

                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, var5);
            }

            if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
            {
                FloatBuffer var21 = BufferUtils.createFloatBuffer(16);
                var21.rewind();
                GL11.glGetFloat(34047, var21);
                float var20 = var21.get(0);
                float var7 = (float)Config.getAnisotropicFilterLevel();
                var7 = Math.min(var7, var20);
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 34046, var7);
            }
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }

        if (this.blurTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        if (this.clampTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        var4 = par1BufferedImage.getWidth();
        var5 = par1BufferedImage.getHeight();
        this.setTextureDimension(par2, new Dimension(var4, var5));

        if (Reflector.FMLRender.exists())
        {
            Reflector.callVoid(Reflector.FMLRender_setTextureDimensions, new Object[] {Integer.valueOf(par2), Integer.valueOf(var4), Integer.valueOf(var5), this.textureList});
        }

        int[] var19 = new int[var4 * var5];
        byte[] var22 = new byte[var4 * var5 * 4];
        par1BufferedImage.getRGB(0, 0, var4, var5, var19, 0, var4);
        int[] var8 = null;
        int var9;
        int var10;

        if (var3)
        {
            if (TextureUtils.isAtlasId(par2))
            {
                var8 = new int[256];

                for (var9 = 0; var9 < 16; ++var9)
                {
                    for (var10 = 0; var10 < 16; ++var10)
                    {
                        var8[var9 * 16 + var10] = this.getAverageOpaqueColor(var19, var10, var9, var4, var5);
                    }
                }
            }

            if (this.singleTileTexture)
            {
                var8 = new int[] {this.getAverageOpaqueColor(var19)};
            }
        }

        int var11;
        int var12;
        int var13;
        int var14;
        int var15;
        int var17;

        for (var9 = 0; var9 < var19.length; ++var9)
        {
            var10 = var19[var9] >> 24 & 255;
            var11 = var19[var9] >> 16 & 255;
            var12 = var19[var9] >> 8 & 255;
            var13 = var19[var9] & 255;
            int var16;

            if (this.options != null && this.options.anaglyph)
            {
                var14 = (var11 * 30 + var12 * 59 + var13 * 11) / 100;
                var15 = (var11 * 30 + var12 * 70) / 100;
                var16 = (var11 * 30 + var13 * 70) / 100;
                var11 = var14;
                var12 = var15;
                var13 = var16;
            }

            if (var10 == 0)
            {
                var11 = 0;
                var12 = 0;
                var13 = 0;

                if (TextureUtils.isAtlasId(par2) || this.singleTileTexture)
                {
                    var11 = 255;
                    var12 = 255;
                    var13 = 255;

                    if (var3)
                    {
                        boolean var24 = false;

                        if (this.singleTileTexture)
                        {
                            var14 = var8[0];
                        }
                        else
                        {
                            var15 = var9 % var4;
                            var16 = var9 / var4;
                            var17 = var15 / (var4 / 16);
                            int var18 = var16 / (var5 / 16);
                            var14 = var8[var18 * 16 + var17];
                        }

                        if (var14 != 0)
                        {
                            var11 = var14 >> 16 & 255;
                            var12 = var14 >> 8 & 255;
                            var13 = var14 & 255;
                        }
                    }
                }
            }

            var22[var9 * 4 + 0] = (byte)var11;
            var22[var9 * 4 + 1] = (byte)var12;
            var22[var9 * 4 + 2] = (byte)var13;
            var22[var9 * 4 + 3] = (byte)var10;
        }

        this.checkImageDataSize(var4, var5);
        this.imageData.clear();
        this.imageData.put(var22);
        this.imageData.position(0).limit(var22.length);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, var4, var5, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);

        if (var3)
        {
            this.generateMipMaps(this.imageData, var4, var5);
        }

        if (Config.isMultiTexture() && TextureUtils.isAtlasId(par2))
        {
            int[] var23 = Tessellator.getTileTextures(par2);

            if (var23 == null)
            {
                var23 = new int[256];
            }

            var10 = var4 / 16;
            var11 = var5 / 16;

            for (var12 = 0; var12 < 16; ++var12)
            {
                for (var13 = 0; var13 < 16; ++var13)
                {
                    var14 = var13 * var10;
                    var15 = var12 * var11;
                    BufferedImage var25 = par1BufferedImage.getSubimage(var14, var15, var10, var11);
                    var17 = var12 * 16 + var13;

                    if (var23[var17] <= 0)
                    {
                        this.singleIntBuffer.clear();
                        GLAllocation.generateTextureNames(this.singleIntBuffer);
                        var23[var17] = this.singleIntBuffer.get(0);
                    }

                    this.clampTexture = this.isTileClamped(par2, var17);
                    this.singleTileTexture = true;
                    this.setupTexture(var25, var23[var17]);
                    this.singleTileTexture = false;
                }
            }

            this.clampTexture = false;
            Tessellator.setTileTextures(par2, var23);
        }
    }

    public void createTextureFromBytes(int[] par1ArrayOfInteger, int par2, int par3, int par4)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par4);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        if (this.blurTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }

        if (this.clampTexture)
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        }
        else
        {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        }

        byte[] var5 = new byte[par2 * par3 * 4];

        for (int var6 = 0; var6 < par1ArrayOfInteger.length; ++var6)
        {
            int var7 = par1ArrayOfInteger[var6] >> 24 & 255;
            int var8 = par1ArrayOfInteger[var6] >> 16 & 255;
            int var9 = par1ArrayOfInteger[var6] >> 8 & 255;
            int var10 = par1ArrayOfInteger[var6] & 255;

            if (this.options != null && this.options.anaglyph)
            {
                int var11 = (var8 * 30 + var9 * 59 + var10 * 11) / 100;
                int var12 = (var8 * 30 + var9 * 70) / 100;
                int var13 = (var8 * 30 + var10 * 70) / 100;
                var8 = var11;
                var9 = var12;
                var10 = var13;
            }

            var5[var6 * 4 + 0] = (byte)var8;
            var5[var6 * 4 + 1] = (byte)var9;
            var5[var6 * 4 + 2] = (byte)var10;
            var5[var6 * 4 + 3] = (byte)var7;
        }

        this.imageData.clear();
        this.imageData.put(var5);
        this.imageData.position(0).limit(var5.length);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, par2, par3, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
    }

    /**
     * Deletes a single GL texture
     */
    public void deleteTexture(int par1)
    {
        this.textureNameToImageMap.removeObject(par1);
        this.singleIntBuffer.clear();
        this.singleIntBuffer.put(par1);
        this.singleIntBuffer.flip();
        GL11.glDeleteTextures(this.singleIntBuffer);
    }

    /**
     * Takes a URL of a downloadable image and the name of the local image to be used as a fallback.  If the image has
     * been downloaded, returns the GL texture of the downloaded image, otherwise returns the GL texture of the fallback
     * image.
     */
    public int getTextureForDownloadableImage(String par1Str, String par2Str)
    {
        if (Config.isRandomMobs())
        {
            int var3 = RandomMobs.getTexture(par1Str, par2Str);

            if (var3 >= 0)
            {
                return var3;
            }
        }

        ThreadDownloadImageData var4 = (ThreadDownloadImageData)this.urlToImageDataMap.get(par1Str);

        if (var4 != null && var4.image != null && !var4.textureSetupComplete)
        {
            if (var4.textureName < 0)
            {
                var4.textureName = this.allocateAndSetupTexture(var4.image);
            }
            else
            {
                this.setupTexture(var4.image, var4.textureName);
            }

            var4.textureSetupComplete = true;
        }

        return var4 != null && var4.textureName >= 0 ? var4.textureName : (par2Str == null ? -1 : this.getTexture(par2Str));
    }

    /**
     * Checks if urlToImageDataMap has image data for the given key
     */
    public boolean hasImageData(String par1Str)
    {
        return this.urlToImageDataMap.containsKey(par1Str);
    }

    /**
     * Return a ThreadDownloadImageData instance for the given URL.  If it does not already exist, it is created and
     * uses the passed ImageBuffer.  If it does, its reference count is incremented.
     */
    public ThreadDownloadImageData obtainImageData(String par1Str, IImageBuffer par2IImageBuffer)
    {
        if (par1Str != null && par1Str.length() > 0 && Character.isDigit(par1Str.charAt(0)))
        {
            return null;
        }
        else
        {
            ThreadDownloadImageData var3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(par1Str);

            if (var3 == null)
            {
                this.urlToImageDataMap.put(par1Str, new ThreadDownloadImageData(par1Str, par2IImageBuffer));
            }
            else
            {
                ++var3.referenceCount;
            }

            return var3;
        }
    }

    /**
     * Decrements the reference count for a given URL, deleting the image data if the reference count hits 0
     */
    public void releaseImageData(String par1Str)
    {
        ThreadDownloadImageData var2 = (ThreadDownloadImageData)this.urlToImageDataMap.get(par1Str);

        if (var2 != null)
        {
            --var2.referenceCount;

            if (var2.referenceCount == 0)
            {
                if (var2.textureName >= 0)
                {
                    this.deleteTexture(var2.textureName);
                }

                this.urlToImageDataMap.remove(par1Str);
            }
        }
    }

    public void registerTextureFX(TextureFX par1TextureFX)
    {
        if (Reflector.FMLRender.exists())
        {
            Reflector.callVoid(Reflector.FMLRender_preRegisterEffect, new Object[] {par1TextureFX});
        }

        int var2 = this.getTextureId(par1TextureFX);

        for (int var3 = 0; var3 < this.textureList.size(); ++var3)
        {
            TextureFX var4 = (TextureFX)this.textureList.get(var3);
            int var5 = this.getTextureId(var4);

            if (var5 == var2 && var4.iconIndex == par1TextureFX.iconIndex)
            {
                this.textureList.remove(var3);
                --var3;
                Config.log("TextureFX removed: " + var4 + ", texId: " + var5 + ", index: " + var4.iconIndex);
            }
        }

        if (par1TextureFX instanceof TextureHDFX)
        {
            TextureHDFX var7 = (TextureHDFX)par1TextureFX;
            var7.setTexturePackBase(this.texturePack.getSelectedTexturePack());
            Dimension var6 = this.getTextureDimensions(var2);

            if (var6 != null)
            {
                var7.setTileWidth(var6.width / 16);
            }
        }

        this.textureList.add(par1TextureFX);
        par1TextureFX.onTick();
        Config.log("TextureFX registered: " + par1TextureFX + ", texId: " + var2 + ", index: " + par1TextureFX.iconIndex);
        this.dynamicTexturesUpdated = false;
    }

    public void updateDynamicTextures()
    {
        boolean var1 = useMipmaps && Config.isUseMipmaps();
        this.checkHdTextures();
        ++this.tickCounter;

        if (this.terrainTextureId < 0)
        {
            this.terrainTextureId = this.getTexture("/terrain.png");
        }

        if (this.guiItemsTextureId < 0)
        {
            this.guiItemsTextureId = this.getTexture("/gui/items.png");
        }

        StringBuffer var2 = new StringBuffer();
        int var3 = -1;

        for (int var4 = 0; var4 < this.textureList.size(); ++var4)
        {
            TextureFX var5 = (TextureFX)this.textureList.get(var4);
            var5.anaglyphEnabled = this.options.anaglyph;

            if (!var5.getClass().getName().equals("ModTextureStatic") || !this.dynamicTexturesUpdated)
            {
                int var6 = this.getTextureId(var5);
                Dimension var7 = this.getTextureDimensions(var6);

                if (var7 == null)
                {
                    throw new IllegalArgumentException("Unknown dimensions for texture id: " + var6);
                }

                int var8 = var7.width / 16;
                int var9 = var7.height / 16;
                this.checkImageDataSize(var7.width, var7.height);
                this.imageData.limit(0);
                var2.setLength(0);
                boolean var10 = this.updateCustomTexture(var5, var6, this.imageData, var7.width / 16, var2);

                if (!var10 || this.imageData.limit() > 0)
                {
                    boolean var11;

                    if (this.imageData.limit() <= 0)
                    {
                        var11 = this.updateDefaultTexture(var5, var6, this.imageData, var7.width / 16, var2);

                        if (var11 && this.imageData.limit() <= 0)
                        {
                            continue;
                        }
                    }

                    if (this.imageData.limit() <= 0)
                    {
                        var5.onTick();

                        if (Reflector.FMLRender.exists() && !Reflector.callBoolean(Reflector.FMLRender_onUpdateTextureEffect, new Object[] {var5}) || var5.imageData == null)
                        {
                            continue;
                        }
                        int var26 = var8 * var9 * 4;

                        if (var5.imageData.length == var26)
                        {
                            this.imageData.clear();
                            this.imageData.put(var5.imageData);
                            this.imageData.position(0).limit(var5.imageData.length);
                        }
                        else
                        {
                            this.copyScaled(var5.imageData, this.imageData, var8);
                        }
                    }

                    if (var6 != var3)
                    {
                        var5.bindImage(this);
                        var3 = var6;
                    }

                    var11 = this.scalesWithFastColor(var5);
                    int var12;
                    int var13;

                    for (var12 = 0; var12 < var5.tileSize; ++var12)
                    {
                        for (var13 = 0; var13 < var5.tileSize; ++var13)
                        {
                            int var14 = var5.iconIndex % 16 * var8 + var12 * var8;
                            int var15 = var5.iconIndex / 16 * var9 + var13 * var9;
                            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, var14, var15, var8, var9, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);

                            if (var1 && var6 != this.guiItemsTextureId)
                            {
                                String var16 = var2.toString();

                                if (var12 == 0 && var13 == 0)
                                {
                                    this.generateMipMapsSub(var14, var15, var8, var9, this.imageData, var5.tileSize, var11, 0, 0, var16);
                                }
                            }
                        }
                    }

                    if (Config.isMultiTexture() && (var6 == this.terrainTextureId || var6 == this.guiItemsTextureId))
                    {
                        for (var12 = 0; var12 < var5.tileSize; ++var12)
                        {
                            for (var13 = 0; var13 < var5.tileSize; ++var13)
                            {
                                byte var28 = 0;
                                byte var27 = 0;
                                int var29 = var13 * 16 + var12;
                                int[] var17 = Tessellator.getTileTextures(var6);
                                int var18 = var17[var5.iconIndex + var29];
                                GL11.glBindTexture(GL11.GL_TEXTURE_2D, var18);
                                var3 = var18;
                                GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, var28, var27, var8, var9, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);

                                if (var1)
                                {
                                    String var19 = var2.toString();

                                    if (var12 == 0 && var13 == 0)
                                    {
                                        this.generateMipMapsSub(var28, var27, var8, var9, this.imageData, var5.tileSize, var11, var6, var5.iconIndex, var19);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (this.textureAnimations != null)
        {
            boolean var20 = this.options.ofAnimatedTextures;

            for (int var21 = 0; var21 < this.textureAnimations.length; ++var21)
            {
                CustomAnimation var22 = this.textureAnimations[var21];
                int var23 = this.getTexture(var22.destTexture);

                if (var23 >= 0)
                {
                    Dimension var24 = this.getTextureDimensions(var23);

                    if (var24 != null)
                    {
                        this.checkImageDataSize(var24.width, var24.height);
                        this.imageData.limit(0);
                        var2.setLength(0);
                        boolean var25 = var22.updateCustomTexture(this.imageData, var20, this.dynamicTexturesUpdated, var2);

                        if ((!var25 || this.imageData.limit() > 0) && this.imageData.limit() > 0)
                        {
                            this.bindTexture(var23);
                            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, var22.destX, var22.destY, var22.frameWidth, var22.frameHeight, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
                        }
                    }
                }
            }
        }

        this.dynamicTexturesUpdated = true;
    }

    /**
     * Updates a single dynamic texture
     */
    public int updateDynamicTexture(TextureFX par1TextureFX, int par2)
    {
        if (par1TextureFX instanceof TextureCompassFX)
        {
            par1TextureFX = TextureHDCompassFX.instance;
        }

        this.imageData.clear();
        this.imageData.put(((TextureFX)par1TextureFX).imageData);
        this.imageData.position(0).limit(((TextureFX)par1TextureFX).imageData.length);

        if (((TextureFX)par1TextureFX).iconIndex != par2)
        {
            ((TextureFX)par1TextureFX).bindImage(this);
            par2 = ((TextureFX)par1TextureFX).iconIndex;
        }

        boolean var3 = true;
        int var9;

        if (((TextureFX)par1TextureFX).tileImage == 0)
        {
            var9 = Config.getIconWidthTerrain();
        }
        else
        {
            var9 = Config.getIconWidthItems();
        }

        int var4 = var9;

        for (int var5 = 0; var5 < ((TextureFX)par1TextureFX).tileSize; ++var5)
        {
            for (int var6 = 0; var6 < ((TextureFX)par1TextureFX).tileSize; ++var6)
            {
                int var7 = ((TextureFX)par1TextureFX).iconIndex % 16 * var9 + var5 * var9;
                int var8 = ((TextureFX)par1TextureFX).iconIndex / 16 * var4 + var6 * var4;
                GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, var7, var8, var9, var4, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
            }
        }

        return par2;
    }

    /**
     * Call setupTexture on all currently-loaded textures again to account for changes in rendering options
     */
    public void refreshTextures()
    {
        this.textureDataMap.clear();
        this.textureFxMap.clear();
        this.dynamicTexturesUpdated = false;
        Config.setTextureUpdateTime(System.currentTimeMillis());
        WrUpdates.finishCurrentUpdate();
        this.mipDataBufsMap.clear();
        this.customAnimationMap.clear();
        ITexturePack var1 = this.texturePack.getSelectedTexturePack();
        Iterator var2 = this.textureNameToImageMap.getKeySet().iterator();
        BufferedImage var3;

        while (var2.hasNext())
        {
            int var4 = ((Integer)var2.next()).intValue();
            var3 = (BufferedImage)this.textureNameToImageMap.lookup(var4);
            this.setupTexture(var3, var4);
        }

        ThreadDownloadImageData var9;

        for (var2 = this.urlToImageDataMap.values().iterator(); var2.hasNext(); var9.textureSetupComplete = false)
        {
            var9 = (ThreadDownloadImageData)var2.next();
        }

        var2 = this.textureMap.keySet().iterator();
        String var5;

        while (var2.hasNext())
        {
            var5 = (String)var2.next();

            try
            {
                if (var5.startsWith("##"))
                {
                    var3 = this.unwrapImageByColumns(this.readTextureImage(var1.getResourceAsStream(var5.substring(2))));
                }
                else if (var5.startsWith("%clamp%"))
                {
                    this.clampTexture = true;
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5.substring(7)));
                }
                else if (var5.startsWith("%blur%"))
                {
                    this.blurTexture = true;
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5.substring(6)));
                }
                else if (var5.startsWith("%blurclamp%"))
                {
                    this.blurTexture = true;
                    this.clampTexture = true;
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5.substring(11)));
                }
                else
                {
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5));
                }

                int var6 = ((Integer)this.textureMap.get(var5)).intValue();
                this.setupTexture(var3, var6);
                this.blurTexture = false;
                this.clampTexture = false;
            }
            catch (Exception var8)
            {
                if (!"input == null!".equals(var8.getMessage()))
                {
                    var8.printStackTrace();
                }
            }
        }

        var2 = this.textureContentsMap.keySet().iterator();

        while (var2.hasNext())
        {
            var5 = (String)var2.next();

            try
            {
                if (var5.startsWith("##"))
                {
                    var3 = this.unwrapImageByColumns(this.readTextureImage(var1.getResourceAsStream(var5.substring(2))));
                }
                else if (var5.startsWith("%clamp%"))
                {
                    this.clampTexture = true;
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5.substring(7)));
                }
                else if (var5.startsWith("%blur%"))
                {
                    this.blurTexture = true;
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5.substring(6)));
                }
                else
                {
                    var3 = this.readTextureImage(var1.getResourceAsStream(var5));
                }

                this.getImageContents(var3, (int[])((int[])this.textureContentsMap.get(var5)));
                this.blurTexture = false;
                this.clampTexture = false;
            }
            catch (Exception var7)
            {
                if (!"input == null!".equals(var7.getMessage()))
                {
                    var7.printStackTrace();
                }
            }
        }

        this.registerCustomTexturesFX();
        CustomColorizer.update(this);
        ConnectedTextures.update(this);
        NaturalTextures.update(this);
        RandomMobs.resetTextures();

        if (Reflector.FMLRender.exists())
        {
            Reflector.callVoid(Reflector.FMLRender_onTexturePackChange, new Object[] {this, var1, this.textureList});
        }

        this.updateDynamicTextures();
    }

    /**
     * Returns a BufferedImage read off the provided input stream.  Args: inputStream
     */
    private BufferedImage readTextureImage(InputStream par1InputStream) throws IOException
    {
        BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }

    public void bindTexture(int par1)
    {
        if (par1 >= 0)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1);
        }
    }

    private void setTextureDimension(int var1, Dimension var2)
    {
        this.textureDimensionsMap.put(new Integer(var1), var2);

        if (var1 == this.terrainTextureId)
        {
            Config.setIconWidthTerrain(var2.width / 16);
        }

        if (var1 == this.guiItemsTextureId)
        {
            Config.setIconWidthItems(var2.width / 16);
        }

        this.updateDinamicTextures(var1, var2);
    }

    public Dimension getTextureDimensions(int var1)
    {
        Dimension var2 = (Dimension)this.textureDimensionsMap.get(new Integer(var1));
        return var2;
    }

    private void updateDinamicTextures(int var1, Dimension var2)
    {
        for (int var3 = 0; var3 < this.textureList.size(); ++var3)
        {
            TextureFX var4 = (TextureFX)this.textureList.get(var3);
            int var5 = this.getTextureId(var4);

            if (var5 == var1 && var4 instanceof TextureHDFX)
            {
                TextureHDFX var6 = (TextureHDFX)var4;
                var6.setTexturePackBase(this.texturePack.getSelectedTexturePack());
                var6.setTileWidth(var2.width / 16);
                var6.onTick();
            }
        }
    }

    public boolean updateCustomTexture(TextureFX var1, int var2, ByteBuffer var3, int var4, StringBuffer var5)
    {
        if (var2 == this.terrainTextureId)
        {
            if (var1.iconIndex == Block.waterStill.blockIndexInTexture)
            {
                if (Config.isGeneratedWater())
                {
                    return false;
                }

                return this.updateCustomTexture(var1, "/custom_water_still.png", var3, var4, Config.isAnimatedWater(), 1, var5);
            }

            if (var1.iconIndex == Block.waterStill.blockIndexInTexture + 1)
            {
                if (Config.isGeneratedWater())
                {
                    return false;
                }

                return this.updateCustomTexture(var1, "/custom_water_flowing.png", var3, var4, Config.isAnimatedWater(), 1, var5);
            }

            if (var1.iconIndex == Block.lavaStill.blockIndexInTexture)
            {
                if (Config.isGeneratedLava())
                {
                    return false;
                }

                return this.updateCustomTexture(var1, "/custom_lava_still.png", var3, var4, Config.isAnimatedLava(), 1, var5);
            }

            if (var1.iconIndex == Block.lavaStill.blockIndexInTexture + 1)
            {
                if (Config.isGeneratedLava())
                {
                    return false;
                }

                return this.updateCustomTexture(var1, "/custom_lava_flowing.png", var3, var4, Config.isAnimatedLava(), 1, var5);
            }

            if (var1.iconIndex == Block.portal.blockIndexInTexture)
            {
                return this.updateCustomTexture(var1, "/custom_portal.png", var3, var4, Config.isAnimatedPortal(), 1, var5);
            }

            if (var1.iconIndex == Block.fire.blockIndexInTexture)
            {
                return this.updateCustomTexture(var1, "/custom_fire_n_s.png", var3, var4, Config.isAnimatedFire(), 1, var5);
            }

            if (var1.iconIndex == Block.fire.blockIndexInTexture + 16)
            {
                return this.updateCustomTexture(var1, "/custom_fire_e_w.png", var3, var4, Config.isAnimatedFire(), 1, var5);
            }

            if (Config.isAnimatedTerrain())
            {
                return this.updateCustomTexture(var1, "/custom_terrain_" + var1.iconIndex + ".png", var3, var4, Config.isAnimatedTerrain(), 1, var5);
            }
        }

        return var2 == this.guiItemsTextureId && Config.isAnimatedItems() ? this.updateCustomTexture(var1, "/custom_item_" + var1.iconIndex + ".png", var3, var4, Config.isAnimatedTerrain(), 1, var5) : false;
    }

    private boolean updateDefaultTexture(TextureFX var1, int var2, ByteBuffer var3, int var4, StringBuffer var5)
    {
        return var2 != this.terrainTextureId ? false : (this.texturePack.getSelectedTexturePack() instanceof TexturePackDefault ? false : (var1.iconIndex == Block.waterStill.blockIndexInTexture ? (Config.isGeneratedWater() ? false : this.updateDefaultTexture(var1, var3, var4, false, 1, var5)) : (var1.iconIndex == Block.waterStill.blockIndexInTexture + 1 ? (Config.isGeneratedWater() ? false : this.updateDefaultTexture(var1, var3, var4, Config.isAnimatedWater(), 1, var5)) : (var1.iconIndex == Block.lavaStill.blockIndexInTexture ? (Config.isGeneratedLava() ? false : this.updateDefaultTexture(var1, var3, var4, false, 1, var5)) : (var1.iconIndex == Block.lavaStill.blockIndexInTexture + 1 ? (Config.isGeneratedLava() ? false : this.updateDefaultTexture(var1, var3, var4, Config.isAnimatedLava(), 3, var5)) : false)))));
    }

    private boolean updateDefaultTexture(TextureFX var1, ByteBuffer var2, int var3, boolean var4, int var5, StringBuffer var6)
    {
        int var7 = var1.iconIndex;

        if (!var4 && this.dynamicTexturesUpdated)
        {
            return true;
        }
        else
        {
            byte[] var8 = this.getTerrainIconData(var7, var3, var6);

            if (var8 == null)
            {
                return false;
            }
            else
            {
                var2.clear();
                int var9 = var8.length;

                if (var4)
                {
                    int var10 = var3 - this.tickCounter / var5 % var3;
                    int var11 = var10 * var3 * 4;
                    var2.put(var8, var11, var9 - var11);
                    var2.put(var8, 0, var11);
                    var6.append(":");
                    var6.append(var10);
                }
                else
                {
                    var2.put(var8, 0, var9);
                }

                var2.position(0).limit(var9);
                return true;
            }
        }
    }

    private boolean updateCustomTexture(TextureFX var1, String var2, ByteBuffer var3, int var4, boolean var5, int var6, StringBuffer var7)
    {
        CustomAnimation var9 = this.getCustomAnimation(var2, var4, var4, var6);
        return var9 == null ? false : var9.updateCustomTexture(var3, var5, this.dynamicTexturesUpdated, var7);
    }

    private CustomAnimation getCustomAnimation(String var1, int var2, int var3, int var4)
    {
        CustomAnimation var5 = (CustomAnimation)this.customAnimationMap.get(var1);

        if (var5 == null)
        {
            if (this.customAnimationMap.containsKey(var1))
            {
                return null;
            }

            byte[] var6 = this.getCustomTextureData(var1, var2);

            if (var6 == null)
            {
                this.customAnimationMap.put(var1, (Object)null);
                return null;
            }

            Properties var7 = new Properties();
            String var8 = this.makePropertiesName(var1);

            if (var8 != null)
            {
                try
                {
                    InputStream var9 = this.texturePack.getSelectedTexturePack().getResourceAsStream(var8);

                    if (var9 == null)
                    {
                        var9 = this.texturePack.getSelectedTexturePack().getResourceAsStream("/anim" + var8);
                    }

                    if (var9 != null)
                    {
                        var7.load(var9);
                    }
                }
                catch (IOException var10)
                {
                    var10.printStackTrace();
                }
            }

            var5 = new CustomAnimation(var1, var6, var2, var3, var7, var4);
            this.customAnimationMap.put(var1, var5);
        }

        return var5;
    }

    private String makePropertiesName(String var1)
    {
        if (!var1.endsWith(".png"))
        {
            return null;
        }
        else
        {
            int var2 = var1.lastIndexOf(".png");

            if (var2 < 0)
            {
                return null;
            }
            else
            {
                String var3 = var1.substring(0, var2) + ".properties";
                return var3;
            }
        }
    }

    private byte[] getTerrainIconData(int var1, int var2, StringBuffer var3)
    {
        String var4 = "Tile-" + var1;
        byte[] var5 = this.getCustomTextureData(var4, var2);

        if (var5 != null)
        {
            var3.append(var4);
            return var5;
        }
        else
        {
            byte[] var6 = this.getCustomTextureData("/terrain.png", var2 * 16);

            if (var6 == null)
            {
                return null;
            }
            else
            {
                var5 = new byte[var2 * var2 * 4];
                int var7 = var1 % 16;
                int var8 = var1 / 16;
                int var9 = var7 * var2;
                int var10 = var8 * var2;
                int var10000 = var9 + var2;
                var10000 = var10 + var2;

                for (int var13 = 0; var13 < var2; ++var13)
                {
                    int var14 = var10 + var13;

                    for (int var15 = 0; var15 < var2; ++var15)
                    {
                        int var16 = var9 + var15;
                        int var17 = 4 * (var16 + var14 * var2 * 16);
                        int var18 = 4 * (var15 + var13 * var2);
                        var5[var18 + 0] = var6[var17 + 0];
                        var5[var18 + 1] = var6[var17 + 1];
                        var5[var18 + 2] = var6[var17 + 2];
                        var5[var18 + 3] = var6[var17 + 3];
                    }
                }

                this.setCustomTextureData(var4, var5);
                var3.append(var4);
                return var5;
            }
        }
    }

    public byte[] getCustomTextureData(String var1, int var2)
    {
        byte[] var3 = (byte[])((byte[])this.textureDataMap.get(var1));

        if (var3 == null)
        {
            if (this.textureDataMap.containsKey(var1))
            {
                return null;
            }

            var3 = this.loadImage(var1, var2);

            if (var3 == null)
            {
                var3 = this.loadImage("/anim" + var1, var2);
            }

            this.textureDataMap.put(var1, var3);
        }

        return var3;
    }

    private void setCustomTextureData(String var1, byte[] var2)
    {
        this.textureDataMap.put(var1, var2);
    }

    private byte[] loadImage(String var1, int var2)
    {
        try
        {
            ITexturePack var3 = this.texturePack.getSelectedTexturePack();

            if (var3 == null)
            {
                return null;
            }
            else
            {
                InputStream var4 = var3.getResourceAsStream(var1);

                if (var4 == null)
                {
                    return null;
                }
                else
                {
                    BufferedImage var5 = this.readTextureImage(var4);

                    if (var5 == null)
                    {
                        return null;
                    }
                    else
                    {
                        if (var2 > 0 && var5.getWidth() != var2)
                        {
                            double var6 = (double)(var5.getHeight() / var5.getWidth());
                            int var8 = (int)((double)var2 * var6);
                            var5 = scaleBufferedImage(var5, var2, var8);
                        }

                        int var19 = var5.getWidth();
                        int var7 = var5.getHeight();
                        int[] var20 = new int[var19 * var7];
                        byte[] var9 = new byte[var19 * var7 * 4];
                        var5.getRGB(0, 0, var19, var7, var20, 0, var19);

                        for (int var10 = 0; var10 < var20.length; ++var10)
                        {
                            int var11 = var20[var10] >> 24 & 255;
                            int var12 = var20[var10] >> 16 & 255;
                            int var13 = var20[var10] >> 8 & 255;
                            int var14 = var20[var10] & 255;

                            if (this.options != null && this.options.anaglyph)
                            {
                                int var15 = (var12 * 30 + var13 * 59 + var14 * 11) / 100;
                                int var16 = (var12 * 30 + var13 * 70) / 100;
                                int var17 = (var12 * 30 + var14 * 70) / 100;
                                var12 = var15;
                                var13 = var16;
                                var14 = var17;
                            }

                            var9[var10 * 4 + 0] = (byte)var12;
                            var9[var10 * 4 + 1] = (byte)var13;
                            var9[var10 * 4 + 2] = (byte)var14;
                            var9[var10 * 4 + 3] = (byte)var11;
                        }

                        return var9;
                    }
                }
            }
        }
        catch (Exception var18)
        {
            var18.printStackTrace();
            return null;
        }
    }

    public static BufferedImage scaleBufferedImage(BufferedImage var0, int var1, int var2)
    {
        BufferedImage var3 = new BufferedImage(var1, var2, 2);
        Graphics2D var4 = var3.createGraphics();
        var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        var4.drawImage(var0, 0, 0, var1, var2, (ImageObserver)null);
        return var3;
    }

    private void checkImageDataSize(int var1, int var2)
    {
        if (this.imageData != null)
        {
            int var3 = var1 * var2 * 4;

            if (this.imageData.capacity() >= var3)
            {
                return;
            }
        }

        this.allocateImageData(var1, var2);
    }

    private void allocateImageData(int var1, int var2)
    {
        var1 = this.powerOfTwo(var1);
        var2 = this.powerOfTwo(var2);
        int var3 = var1 * var2 * 4;
        this.imageData = GLAllocation.createDirectByteBuffer(var3);
        ArrayList var4 = new ArrayList();
        int var5 = var1 / 2;

        for (int var6 = var2 / 2; var5 > 0 && var6 > 0; var6 /= 2)
        {
            int var7 = var5 * var6 * 4;
            ByteBuffer var8 = GLAllocation.createDirectByteBuffer(var7);
            var4.add(var8);
            var5 /= 2;
        }

        this.mipImageDatas = (ByteBuffer[])((ByteBuffer[])var4.toArray(new ByteBuffer[var4.size()]));
    }

    private int powerOfTwo(int var1)
    {
        int var2;

        for (var2 = 1; var2 < var1; var2 *= 2)
        {
            ;
        }

        return var2;
    }

    public void checkHdTextures()
    {
        if (!this.hdTexturesInstalled)
        {
            Minecraft var1 = Config.getMinecraft();

            if (var1 != null)
            {
                this.hdTexturesInstalled = true;
                this.registerTextureFX(new TextureHDLavaFX());
                this.registerTextureFX(new TextureHDWaterFX());
                this.registerTextureFX(new TextureHDPortalFX());
                this.registerTextureFX(new TextureHDWaterFlowFX());
                this.registerTextureFX(new TextureHDLavaFlowFX());
                this.registerTextureFX(new TextureHDFlamesFX(0));
                this.registerTextureFX(new TextureHDFlamesFX(1));
                this.registerTextureFX(new TextureHDCompassFX(var1));
                this.registerTextureFX(new TextureHDWatchFX(var1));
                this.registerCustomTexturesFX();
                CustomColorizer.update(this);
                ConnectedTextures.update(this);
                NaturalTextures.update(this);
            }
        }
    }

    private void registerCustomTexturesFX()
    {
        TextureFX[] var1 = this.getRegisteredTexturesFX(TextureHDCustomFX.class);
        int var2;

        for (var2 = 0; var2 < var1.length; ++var2)
        {
            TextureFX var3 = var1[var2];
            this.unregisterTextureFX(var3);
        }

        if (Config.isAnimatedTerrain())
        {
            for (var2 = 0; var2 < 256; ++var2)
            {
                this.registerCustomTextureFX("/custom_terrain_" + var2 + ".png", var2, 0);
            }
        }

        if (Config.isAnimatedItems())
        {
            for (var2 = 0; var2 < 256; ++var2)
            {
                this.registerCustomTextureFX("/custom_item_" + var2 + ".png", var2, 1);
            }
        }

        this.textureAnimations = this.getTextureAnimations();
    }

    private CustomAnimation[] getTextureAnimations()
    {
        ITexturePack var1 = this.texturePack.getSelectedTexturePack();

        if (!(var1 instanceof TexturePackImplementation))
        {
            return null;
        }
        else
        {
            TexturePackImplementation var2 = (TexturePackImplementation)var1;
            File var3 = var2.texturePackFile;

            if (var3 == null)
            {
                return null;
            }
            else if (!var3.exists())
            {
                return null;
            }
            else
            {
                Properties[] var4 = null;

                if (var3.isFile())
                {
                    var4 = this.getAnimationPropertiesZip(var3);
                }
                else
                {
                    var4 = this.getAnimationPropertiesDir(var3);
                }

                if (var4 == null)
                {
                    return null;
                }
                else
                {
                    ArrayList var5 = new ArrayList();

                    for (int var6 = 0; var6 < var4.length; ++var6)
                    {
                        Properties var7 = var4[var6];
                        CustomAnimation var8 = this.makeTextureAnimation(var7);

                        if (var8 != null)
                        {
                            var5.add(var8);
                        }
                    }

                    CustomAnimation[] var9 = (CustomAnimation[])((CustomAnimation[])var5.toArray(new CustomAnimation[var5.size()]));
                    return var9;
                }
            }
        }
    }

    private CustomAnimation makeTextureAnimation(Properties var1)
    {
        String var2 = var1.getProperty("from");
        String var3 = var1.getProperty("to");
        int var4 = Config.parseInt(var1.getProperty("x"), -1);
        int var5 = Config.parseInt(var1.getProperty("y"), -1);
        int var6 = Config.parseInt(var1.getProperty("w"), -1);
        int var7 = Config.parseInt(var1.getProperty("h"), -1);

        if (var2 != null && var3 != null)
        {
            if (var4 >= 0 && var5 >= 0 && var6 >= 0 && var7 >= 0)
            {
                byte[] var8 = this.getCustomTextureData(var2, var6);

                if (var8 == null)
                {
                    return null;
                }
                else
                {
                    CustomAnimation var9 = new CustomAnimation(var2, var8, var6, var7, var1, 1);
                    var9.destTexture = var3;
                    var9.destX = var4;
                    var9.destY = var5;
                    return var9;
                }
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    private Properties[] getAnimationPropertiesDir(File var1)
    {
        File var2 = new File(var1, "anim");

        if (!var2.exists())
        {
            return null;
        }
        else if (!var2.isDirectory())
        {
            return null;
        }
        else
        {
            File[] var3 = var2.listFiles();

            if (var3 == null)
            {
                return null;
            }
            else
            {
                try
                {
                    ArrayList var4 = new ArrayList();

                    for (int var5 = 0; var5 < var3.length; ++var5)
                    {
                        File var6 = var3[var5];
                        String var7 = var6.getName();

                        if (!var7.startsWith("custom_") && var7.endsWith(".properties") && var6.isFile() && var6.canRead())
                        {
                            FileInputStream var8 = new FileInputStream(var6);
                            Properties var9 = new Properties();
                            var9.load(var8);
                            var8.close();
                            var4.add(var9);
                        }
                    }

                    Properties[] var11 = (Properties[])((Properties[])var4.toArray(new Properties[var4.size()]));
                    return var11;
                }
                catch (IOException var10)
                {
                    var10.printStackTrace();
                    return null;
                }
            }
        }
    }

    private Properties[] getAnimationPropertiesZip(File var1)
    {
        try
        {
            ZipFile var2 = new ZipFile(var1);
            Enumeration var3 = var2.entries();
            ArrayList var4 = new ArrayList();

            while (var3.hasMoreElements())
            {
                ZipEntry var5 = (ZipEntry)var3.nextElement();
                String var6 = var5.getName();

                if (var6.startsWith("anim/") && !var6.startsWith("anim/custom_") && var6.endsWith(".properties"))
                {
                    InputStream var7 = var2.getInputStream(var5);
                    Properties var8 = new Properties();
                    var8.load(var7);
                    var7.close();
                    var4.add(var8);
                }
            }

            Properties[] var10 = (Properties[])((Properties[])var4.toArray(new Properties[var4.size()]));
            return var10;
        }
        catch (IOException var9)
        {
            var9.printStackTrace();
            return null;
        }
    }

    private void unregisterTextureFX(TextureFX var1)
    {
        for (int var2 = 0; var2 < this.textureList.size(); ++var2)
        {
            TextureFX var3 = (TextureFX)this.textureList.get(var2);

            if (var3 == var1)
            {
                this.textureList.remove(var2);
                --var2;
            }
        }
    }

    private TextureFX[] getRegisteredTexturesFX(Class var1)
    {
        ArrayList var2 = new ArrayList();

        for (int var3 = 0; var3 < this.textureList.size(); ++var3)
        {
            TextureFX var4 = (TextureFX)this.textureList.get(var3);

            if (var1.isAssignableFrom(var4.getClass()))
            {
                var2.add(var4);
            }
        }

        TextureFX[] var5 = (TextureFX[])((TextureFX[])var2.toArray(new TextureFX[var2.size()]));
        return var5;
    }

    private void registerCustomTextureFX(String var1, int var2, int var3)
    {
        Object var4 = null;
        byte[] var5;

        if (var3 == 0)
        {
            var5 = this.getCustomTextureData(var1, Config.getIconWidthTerrain());
        }
        else
        {
            var5 = this.getCustomTextureData(var1, Config.getIconWidthItems());
        }

        if (var5 != null)
        {
            this.registerTextureFX(new TextureHDCustomFX(var2, var3));
        }
    }

    private int getMaxMipmapLevel(int var1)
    {
        int var2;

        for (var2 = 0; var1 > 0; ++var2)
        {
            var1 /= 2;
        }

        return var2 - 1;
    }

    private void copyScaled(byte[] var1, ByteBuffer var2, int var3)
    {
        int var4 = (int)Math.sqrt((double)(var1.length / 4));
        int var5 = var3 / var4;
        byte[] var6 = new byte[4];
        int var7 = var3 * var3;
        var2.clear();

        if (var5 > 1)
        {
            for (int var8 = 0; var8 < var4; ++var8)
            {
                int var9 = var8 * var4;
                int var10 = var8 * var5;
                int var11 = var10 * var3;

                for (int var12 = 0; var12 < var4; ++var12)
                {
                    int var13 = (var12 + var9) * 4;
                    var6[0] = var1[var13];
                    var6[1] = var1[var13 + 1];
                    var6[2] = var1[var13 + 2];
                    var6[3] = var1[var13 + 3];
                    int var14 = var12 * var5;
                    int var15 = var14 + var11;

                    for (int var16 = 0; var16 < var5; ++var16)
                    {
                        int var17 = var15 + var16 * var3;
                        var2.position(var17 * 4);

                        for (int var18 = 0; var18 < var5; ++var18)
                        {
                            var2.put(var6);
                        }
                    }
                }
            }
        }

        var2.position(0).limit(var3 * var3 * 4);
    }

    private boolean scalesWithFastColor(TextureFX var1)
    {
        return !var1.getClass().getName().equals("ModTextureStatic");
    }

    private boolean isTileClamped(int var1, int var2)
    {
        return var1 != this.terrainTextureId || !Config.between(var2, 0, 2) && !Config.between(var2, 4, 10) && !Config.between(var2, 16, 21) && !Config.between(var2, 32, 37) && !Config.between(var2, 40, 40) && !Config.between(var2, 48, 53) && !Config.between(var2, 64, 67) && !Config.between(var2, 69, 75) && !Config.between(var2, 86, 87) && !Config.between(var2, 102, 107) && !Config.between(var2, 109, 110) && !Config.between(var2, 113, 114) && !Config.between(var2, 116, 121) && !Config.between(var2, 129, 133) && !Config.between(var2, 144, 147) && !Config.between(var2, 160, 165) && !Config.between(var2, 176, 181) && !Config.between(var2, 192, 195) && !Config.between(var2, 205, 207) && !Config.between(var2, 208, 210) && !Config.between(var2, 222, 223) && !Config.between(var2, 225, 225) && !Config.between(var2, 237, 239) && !Config.between(var2, 240, 249) && !Config.between(var2, 254, 255);
    }

    private void generateMipMapsSub(int var1, int var2, int var3, int var4, ByteBuffer var5, int var6, boolean var7, int var8, int var9, String var10)
    {
        ByteBuffer var11 = var5;
        byte[][] var12 = (byte[][])null;

        if (var10.length() > 0)
        {
            var12 = (byte[][])((byte[][])this.mipDataBufsMap.get(var10));

            if (var12 == null)
            {
                var12 = new byte[17][];
                this.mipDataBufsMap.put(var10, var12);
            }
        }

        for (int var13 = 1; var13 <= 16; ++var13)
        {
            int var14 = var3 >> var13 - 1;
            int var15 = var3 >> var13;
            int var16 = var4 >> var13;
            int var17 = var1 >> var13;
            int var18 = var2 >> var13;

            if (var15 <= 0 || var16 <= 0)
            {
                break;
            }

            ByteBuffer var19 = this.mipImageDatas[var13 - 1];
            var19.limit(var15 * var16 * 4);
            byte[] var20 = null;

            if (var12 != null)
            {
                var20 = var12[var13];
            }

            if (var20 != null && var20.length != var15 * var16 * 4)
            {
                var20 = null;
            }

            int var21;
            int var23;
            int var22;
            int var25;
            int var24;

            if (var20 == null)
            {
                if (var12 != null)
                {
                    var20 = new byte[var15 * var16 * 4];
                }

                for (var21 = 0; var21 < var15; ++var21)
                {
                    for (var22 = 0; var22 < var16; ++var22)
                    {
                        var23 = var11.getInt((var21 * 2 + 0 + (var22 * 2 + 0) * var14) * 4);
                        var24 = var11.getInt((var21 * 2 + 1 + (var22 * 2 + 0) * var14) * 4);
                        var25 = var11.getInt((var21 * 2 + 1 + (var22 * 2 + 1) * var14) * 4);
                        int var26 = var11.getInt((var21 * 2 + 0 + (var22 * 2 + 1) * var14) * 4);
                        int var27;

                        if (var7)
                        {
                            var27 = this.averageColor(this.averageColor(var23, var24), this.averageColor(var25, var26));
                        }
                        else
                        {
                            var27 = this.alphaBlend(var23, var24, var25, var26);
                        }

                        var19.putInt((var21 + var22 * var15) * 4, var27);
                    }
                }

                if (var12 != null)
                {
                    var19.rewind();
                    var19.get(var20);
                    var12[var13] = var20;
                }
            }

            if (var20 != null)
            {
                var19.rewind();
                var19.put(var20);
            }

            var19.rewind();

            for (var21 = 0; var21 < var6; ++var21)
            {
                for (var22 = 0; var22 < var6; ++var22)
                {
                    var23 = var21 * var15;
                    var24 = var22 * var16;

                    if (Config.isMultiTexture() && var8 == this.terrainTextureId)
                    {
                        var25 = var22 * 16 + var21;
                        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Tessellator.getTileTextures(this.terrainTextureId)[var9 + var25]);
                        var23 = 0;
                        var24 = 0;
                    }

                    GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, var13, var17 + var23, var18 + var24, var15, var16, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, var19);
                }
            }

            var11 = var19;
        }
    }

    private int alphaBlend(int var1, int var2, int var3, int var4)
    {
        int var5 = this.alphaBlend(var1, var2);
        int var6 = this.alphaBlend(var3, var4);
        int var7 = this.alphaBlend(var5, var6);
        return var7;
    }

    private int alphaBlend(int var1, int var2)
    {
        int var3 = (var1 & -16777216) >> 24 & 255;
        int var4 = (var2 & -16777216) >> 24 & 255;
        int var5 = (var3 + var4) / 2;

        if (var3 == 0 && var4 == 0)
        {
            var3 = 1;
            var4 = 1;
        }
        else
        {
            if (var3 == 0)
            {
                var1 = var2;
                var5 /= 2;
            }

            if (var4 == 0)
            {
                var2 = var1;
                var5 /= 2;
            }
        }

        int var6 = (var1 >> 16 & 255) * var3;
        int var7 = (var1 >> 8 & 255) * var3;
        int var8 = (var1 & 255) * var3;
        int var9 = (var2 >> 16 & 255) * var4;
        int var10 = (var2 >> 8 & 255) * var4;
        int var11 = (var2 & 255) * var4;
        int var12 = (var6 + var9) / (var3 + var4);
        int var13 = (var7 + var10) / (var3 + var4);
        int var14 = (var8 + var11) / (var3 + var4);
        return var5 << 24 | var12 << 16 | var13 << 8 | var14;
    }

    private int averageColor(int var1, int var2)
    {
        int var3 = (var1 & -16777216) >> 24 & 255;
        int var4 = (var2 & -16777216) >> 24 & 255;
        return (var3 + var4 >> 1 << 24) + ((var1 & 16711422) + (var2 & 16711422) >> 1);
    }

    private int getAverageOpaqueColor(int[] var1)
    {
        long var2 = 0L;
        long var4 = 0L;
        long var6 = 0L;
        long var8 = 0L;
        int var11;
        int var12;
        int var13;

        for (int var10 = 0; var10 < var1.length; ++var10)
        {
            var11 = var1[var10];
            var12 = var11 >> 24 & 255;

            if (var12 != 0)
            {
                var13 = var11 >> 16 & 255;
                int var14 = var11 >> 8 & 255;
                int var15 = var11 & 255;
                var2 += (long)var13;
                var4 += (long)var14;
                var6 += (long)var15;
                ++var8;
            }
        }

        if (var8 <= 0L)
        {
            return -1;
        }
        else
        {
            short var16 = 255;
            var11 = (int)(var2 / var8);
            var12 = (int)(var4 / var8);
            var13 = (int)(var6 / var8);
            return var16 << 24 | var11 << 16 | var12 << 8 | var13;
        }
    }

    private void fixAlpha(BufferedImage var1)
    {
        long var2 = 0L;
        long var4 = 0L;
        long var6 = 0L;
        long var8 = 0L;
        int var10 = var1.getWidth();
        int var11 = var1.getHeight();
        int var12;
        int var13;
        int var14;
        int var15;
        int var17;
        int var16;
        int var18;

        for (var12 = 0; var12 < var11; ++var12)
        {
            for (var13 = 0; var13 < var10; ++var13)
            {
                var14 = var1.getRGB(var13, var12);
                var15 = var14 >> 24 & 255;

                if (var15 != 0)
                {
                    var16 = var14 >> 16 & 255;
                    var17 = var14 >> 8 & 255;
                    var18 = var14 & 255;
                    var2 += (long)var16;
                    var4 += (long)var17;
                    var6 += (long)var18;
                    ++var8;
                }
            }
        }

        if (var8 > 0L)
        {
            var12 = (int)(var2 / var8);
            var13 = (int)(var4 / var8);
            var14 = (int)(var6 / var8);

            for (var15 = 0; var15 < var11; ++var15)
            {
                for (var16 = 0; var16 < var10; ++var16)
                {
                    var17 = var1.getRGB(var16, var15);
                    var18 = var17 >> 24 & 255;

                    if (var18 == 0)
                    {
                        var17 = var18 << 24 | var12 << 16 | var13 << 8 | var14 << 0;
                        var1.setRGB(var16, var15, var17);
                    }
                }
            }
        }
    }

    private int getAverageOpaqueColor(int[] var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var4 / 16;
        int var7 = var5 / 16;
        int var8 = var3 * var7 * var4 + var2 * var6;
        long var9 = 0L;
        long var11 = 0L;
        long var13 = 0L;
        long var15 = 0L;
        int var19;
        int var18;
        int var20;

        for (int var17 = 0; var17 < var7; ++var17)
        {
            for (var18 = 0; var18 < var6; ++var18)
            {
                var19 = var8 + var17 * var4 + var18;
                var20 = var1[var19] >> 24 & 255;

                if (var20 != 0)
                {
                    int var21 = var1[var19] >> 16 & 255;
                    int var22 = var1[var19] >> 8 & 255;
                    int var23 = var1[var19] & 255;
                    var9 += (long)var21;
                    var11 += (long)var22;
                    var13 += (long)var23;
                    ++var15;
                }
            }
        }

        if (var15 <= 0L)
        {
            return 0;
        }
        else
        {
            short var24 = 255;
            var18 = (int)(var9 / var15);
            var19 = (int)(var11 / var15);
            var20 = (int)(var13 / var15);
            return var24 << 24 | var18 << 16 | var19 << 8 | var20;
        }
    }

    private void generateMipMaps(ByteBuffer var1, int var2, int var3)
    {
        ByteBuffer var4 = var1;

        for (int var5 = 1; var5 <= 16; ++var5)
        {
            int var6 = var2 >> var5 - 1;
            int var7 = var2 >> var5;
            int var8 = var3 >> var5;

            if (var7 <= 0 || var8 <= 0)
            {
                break;
            }

            ByteBuffer var9 = this.mipImageDatas[var5 - 1];
            var9.limit(var7 * var8 * 4);

            for (int var10 = 0; var10 < var7; ++var10)
            {
                for (int var11 = 0; var11 < var8; ++var11)
                {
                    int var12 = var4.getInt((var10 * 2 + 0 + (var11 * 2 + 0) * var6) * 4);
                    int var13 = var4.getInt((var10 * 2 + 1 + (var11 * 2 + 0) * var6) * 4);
                    int var14 = var4.getInt((var10 * 2 + 1 + (var11 * 2 + 1) * var6) * 4);
                    int var15 = var4.getInt((var10 * 2 + 0 + (var11 * 2 + 1) * var6) * 4);
                    int var16 = this.alphaBlend(var12, var13, var14, var15);
                    var9.putInt((var10 + var11 * var7) * 4, var16);
                }
            }

            var9.rewind();
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, var5, GL11.GL_RGBA, var7, var8, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, var9);
            var4 = var9;
        }
    }

    private int getBoundTexture()
    {
        int var1 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        return var1;
    }

    private int getTextureId(TextureFX var1)
    {
        Integer var2 = (Integer)this.textureFxMap.get(var1);

        if (var2 != null)
        {
            return var2.intValue();
        }
        else
        {
            int var3 = this.getBoundTexture();
            var1.bindImage(this);
            int var4 = this.getBoundTexture();
            this.bindTexture(var3);
            this.textureFxMap.put(var1, new Integer(var4));
            return var4;
        }
    }

    protected BufferedImage readTextureImage(String var1) throws IOException
    {
        InputStream var2 = this.texturePack.getSelectedTexturePack().getResourceAsStream(var1);

        if (var2 == null)
        {
            return null;
        }
        else
        {
            BufferedImage var3 = ImageIO.read(var2);
            var2.close();
            return var3;
        }
    }

    public TexturePackList getTexturePack()
    {
        return this.texturePack;
    }
}
