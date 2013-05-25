package net.minecraft.src;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflector
{
    public static ReflectorClass ModLoader = new ReflectorClass("ModLoader");
    public static ReflectorMethod ModLoader_renderWorldBlock = new ReflectorMethod(ModLoader, "renderWorldBlock");
    public static ReflectorMethod ModLoader_renderInvBlock = new ReflectorMethod(ModLoader, "renderInvBlock");
    public static ReflectorMethod ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(ModLoader, "renderBlockIsItemFull3D");
    public static ReflectorClass FMLRenderAccessLibrary = new ReflectorClass("FMLRenderAccessLibrary");
    public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderWorldBlock");
    public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderInventoryBlock");
    public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(FMLRenderAccessLibrary, "renderItemAsFull3DBlock");
    public static ReflectorClass LightCache = new ReflectorClass("LightCache");
    public static ReflectorField LightCache_cache = new ReflectorField(LightCache, "cache");
    public static ReflectorMethod LightCache_clear = new ReflectorMethod(LightCache, "clear");
    public static ReflectorClass BlockCoord = new ReflectorClass("BlockCoord");
    public static ReflectorMethod BlockCoord_resetPool = new ReflectorMethod(BlockCoord, "resetPool");
    public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
    public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
    public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
    public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
    public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
    public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
    public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
    public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
    public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
    public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
    public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
    public static ReflectorMethod ForgeHooks_isLivingOnLadder = new ReflectorMethod(ForgeHooks, "isLivingOnLadder");
    public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
    public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
    public static ReflectorMethod ForgeHooksClient_onTextureLoadPre = new ReflectorMethod(ForgeHooksClient, "onTextureLoadPre");
    public static ReflectorMethod ForgeHooksClient_onTextureLoad = new ReflectorMethod(ForgeHooksClient, "onTextureLoad");
    public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
    public static ReflectorClass FMLCommonHandler = new ReflectorClass("cpw.mods.fml.common.FMLCommonHandler");
    public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
    public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
    public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
    public static ReflectorClass FMLClientHandler = new ReflectorClass("cpw.mods.fml.client.FMLClientHandler");
    public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
    public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
    public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
    public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
    public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
    public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
    public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
    public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
    public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
    public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
    public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] {World.class});
    public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.event.EventBus");
    public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
    public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
    public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] {ChunkCoordIntPair.class, EntityPlayerMP.class});
    public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
    public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
    public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
    public static ReflectorMethod ForgeBlock_canRenderInPass = new ReflectorMethod(ForgeBlock, "canRenderInPass");
    public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
    public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
    public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
    public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
    public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
    public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
    public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");

    public static void callVoid(ReflectorMethod var0, Object ... var1)
    {
        try
        {
            Method var2 = var0.getTargetMethod();

            if (var2 == null)
            {
                return;
            }

            var2.invoke((Object)null, var1);
        }
        catch (Throwable var3)
        {
            handleException(var3, (Object)null, var0, var1);
        }
    }

    public static boolean callBoolean(ReflectorMethod var0, Object ... var1)
    {
        try
        {
            Method var2 = var0.getTargetMethod();

            if (var2 == null)
            {
                return false;
            }
            else
            {
                Boolean var3 = (Boolean)var2.invoke((Object)null, var1);
                return var3.booleanValue();
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, var0, var1);
            return false;
        }
    }

    public static int callInt(ReflectorMethod var0, Object ... var1)
    {
        try
        {
            Method var2 = var0.getTargetMethod();

            if (var2 == null)
            {
                return 0;
            }
            else
            {
                Integer var3 = (Integer)var2.invoke((Object)null, var1);
                return var3.intValue();
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, var0, var1);
            return 0;
        }
    }

    public static float callFloat(ReflectorMethod var0, Object ... var1)
    {
        try
        {
            Method var2 = var0.getTargetMethod();

            if (var2 == null)
            {
                return 0.0F;
            }
            else
            {
                Float var3 = (Float)var2.invoke((Object)null, var1);
                return var3.floatValue();
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, var0, var1);
            return 0.0F;
        }
    }

    public static String callString(ReflectorMethod var0, Object ... var1)
    {
        try
        {
            Method var2 = var0.getTargetMethod();

            if (var2 == null)
            {
                return null;
            }
            else
            {
                String var3 = (String)var2.invoke((Object)null, var1);
                return var3;
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, var0, var1);
            return null;
        }
    }

    public static Object call(ReflectorMethod var0, Object ... var1)
    {
        try
        {
            Method var2 = var0.getTargetMethod();

            if (var2 == null)
            {
                return null;
            }
            else
            {
                Object var3 = var2.invoke((Object)null, var1);
                return var3;
            }
        }
        catch (Throwable var4)
        {
            handleException(var4, (Object)null, var0, var1);
            return null;
        }
    }

    public static void callVoid(Object var0, ReflectorMethod var1, Object ... var2)
    {
        try
        {
            if (var0 == null)
            {
                return;
            }

            Method var3 = var1.getTargetMethod();

            if (var3 == null)
            {
                return;
            }

            var3.invoke(var0, var2);
        }
        catch (Throwable var4)
        {
            handleException(var4, var0, var1, var2);
        }
    }

    public static boolean callBoolean(Object var0, ReflectorMethod var1, Object ... var2)
    {
        try
        {
            Method var3 = var1.getTargetMethod();

            if (var3 == null)
            {
                return false;
            }
            else
            {
                Boolean var4 = (Boolean)var3.invoke(var0, var2);
                return var4.booleanValue();
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, var0, var1, var2);
            return false;
        }
    }

    public static int callInt(Object var0, ReflectorMethod var1, Object ... var2)
    {
        try
        {
            Method var3 = var1.getTargetMethod();

            if (var3 == null)
            {
                return 0;
            }
            else
            {
                Integer var4 = (Integer)var3.invoke(var0, var2);
                return var4.intValue();
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, var0, var1, var2);
            return 0;
        }
    }

    public static float callFloat(Object var0, ReflectorMethod var1, Object ... var2)
    {
        try
        {
            Method var3 = var1.getTargetMethod();

            if (var3 == null)
            {
                return 0.0F;
            }
            else
            {
                Float var4 = (Float)var3.invoke(var0, var2);
                return var4.floatValue();
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, var0, var1, var2);
            return 0.0F;
        }
    }

    public static String callString(Object var0, ReflectorMethod var1, Object ... var2)
    {
        try
        {
            Method var3 = var1.getTargetMethod();

            if (var3 == null)
            {
                return null;
            }
            else
            {
                String var4 = (String)var3.invoke(var0, var2);
                return var4;
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, var0, var1, var2);
            return null;
        }
    }

    public static Object call(Object var0, ReflectorMethod var1, Object ... var2)
    {
        try
        {
            Method var3 = var1.getTargetMethod();

            if (var3 == null)
            {
                return null;
            }
            else
            {
                Object var4 = var3.invoke(var0, var2);
                return var4;
            }
        }
        catch (Throwable var5)
        {
            handleException(var5, var0, var1, var2);
            return null;
        }
    }

    public static Object getFieldValue(ReflectorField var0)
    {
        return getFieldValue((Object)null, var0);
    }

    public static Object getFieldValue(Object var0, ReflectorField var1)
    {
        try
        {
            Field var2 = var1.getTargetField();

            if (var2 == null)
            {
                return null;
            }
            else
            {
                Object var3 = var2.get(var0);
                return var3;
            }
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
            return null;
        }
    }

    public static void setFieldValue(ReflectorField var0, Object var1)
    {
        setFieldValue((Object)null, var0, var1);
    }

    public static void setFieldValue(Object var0, ReflectorField var1, Object var2)
    {
        try
        {
            Field var3 = var1.getTargetField();

            if (var3 == null)
            {
                return;
            }

            var3.set(var0, var2);
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    public static void postForgeBusEvent(ReflectorConstructor var0, Object ... var1)
    {
        try
        {
            Object var2 = getFieldValue(MinecraftForge_EVENT_BUS);

            if (var2 == null)
            {
                return;
            }

            Constructor var3 = var0.getTargetConstructor();

            if (var3 == null)
            {
                return;
            }

            Object var4 = var3.newInstance(var1);
            callVoid(var2, EventBus_post, new Object[] {var4});
        }
        catch (Throwable var5)
        {
            var5.printStackTrace();
        }
    }

    public static boolean matchesTypes(Class[] var0, Class[] var1)
    {
        if (var0.length != var1.length)
        {
            return false;
        }
        else
        {
            for (int var2 = 0; var2 < var1.length; ++var2)
            {
                Class var3 = var0[var2];
                Class var4 = var1[var2];

                if (var3 != var4)
                {
                    return false;
                }
            }

            return true;
        }
    }

    private static void dbgCall(boolean var0, String var1, ReflectorMethod var2, Object[] var3, Object var4)
    {
        String var5 = var2.getTargetMethod().getDeclaringClass().getName();
        String var6 = var2.getTargetMethod().getName();
        String var7 = "";

        if (var0)
        {
            var7 = " static";
        }

        Config.dbg(var1 + var7 + " " + var5 + "." + var6 + "(" + Config.arrayToString(var3) + ") => " + var4);
    }

    private static void dbgCallVoid(boolean var0, String var1, ReflectorMethod var2, Object[] var3)
    {
        String var4 = var2.getTargetMethod().getDeclaringClass().getName();
        String var5 = var2.getTargetMethod().getName();
        String var6 = "";

        if (var0)
        {
            var6 = " static";
        }

        Config.dbg(var1 + var6 + " " + var4 + "." + var5 + "(" + Config.arrayToString(var3) + ")");
    }

    private static void dbgFieldValue(boolean var0, String var1, ReflectorField var2, Object var3)
    {
        String var4 = var2.getTargetField().getDeclaringClass().getName();
        String var5 = var2.getTargetField().getName();
        String var6 = "";

        if (var0)
        {
            var6 = " static";
        }

        Config.dbg(var1 + var6 + " " + var4 + "." + var5 + " => " + var3);
    }

    private static void handleException(Throwable var0, Object var1, ReflectorMethod var2, Object[] var3)
    {
        if (var0 instanceof InvocationTargetException)
        {
            var0.printStackTrace();
        }
        else
        {
            if (var0 instanceof IllegalArgumentException)
            {
                Config.dbg("*** IllegalArgumentException ***");
                Config.dbg("Method: " + var2.getTargetMethod());
                Config.dbg("Object: " + var1);
                Config.dbg("Parameter classes: " + Config.arrayToString(getClasses(var3)));
                Config.dbg("Parameters: " + Config.arrayToString(var3));
            }

            Config.dbg("*** Exception outside of method ***");
            Config.dbg("Method deactivated: " + var2.getTargetMethod());
            var2.deactivate();
            var0.printStackTrace();
        }
    }

    private static Object[] getClasses(Object[] var0)
    {
        if (var0 == null)
        {
            return new Class[0];
        }
        else
        {
            Class[] var1 = new Class[var0.length];

            for (int var2 = 0; var2 < var1.length; ++var2)
            {
                Object var3 = var0[var2];

                if (var3 != null)
                {
                    var1[var2] = var3.getClass();
                }
            }

            return var1;
        }
    }
}
