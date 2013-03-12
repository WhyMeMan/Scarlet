package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.server.MinecraftServer;

public class WorldServerOF extends WorldServer
{
    private NextTickHashSet nextTickHashSet = null;
    private TreeSet pendingTickList = null;

    public WorldServerOF(MinecraftServer var1, ISaveHandler var2, String var3, int var4, WorldSettings var5, Profiler var6)
    {
        super(var1, var2, var3, var4, var5, var6);
        this.fixSetNextTicks();
    }

    private void fixSetNextTicks()
    {
        try
        {
            Field[] var1 = WorldServer.class.getDeclaredFields();

            if (var1.length > 5)
            {
                Field var2 = var1[3];
                var2.setAccessible(true);

                if (var2.getType() == Set.class)
                {
                    Set var3 = (Set)var2.get(this);
                    NextTickHashSet var4 = new NextTickHashSet(var3);
                    var2.set(this, var4);
                    Field var5 = var1[4];
                    var5.setAccessible(true);
                    this.pendingTickList = (TreeSet)var5.get(this);
                    this.nextTickHashSet = var4;
                }
            }
        }
        catch (Exception var6)
        {
            Config.dbg("Error setting WorldServer.nextTickSet: " + var6.getMessage());
        }
    }

    public List getPendingBlockUpdates(Chunk var1, boolean var2)
    {
        if (this.nextTickHashSet != null && this.pendingTickList != null)
        {
            ArrayList var3 = null;
            ChunkCoordIntPair var4 = var1.getChunkCoordIntPair();
            int var5 = var4.chunkXPos << 4;
            int var6 = var5 + 16;
            int var7 = var4.chunkZPos << 4;
            int var8 = var7 + 16;
            Iterator var9 = this.nextTickHashSet.getNextTickEntries(var4.chunkXPos, var4.chunkZPos);

            while (var9.hasNext())
            {
                NextTickListEntry var10 = (NextTickListEntry)var9.next();

                if (var10.xCoord >= var5 && var10.xCoord < var6 && var10.zCoord >= var7 && var10.zCoord < var8)
                {
                    if (var2)
                    {
                        this.pendingTickList.remove(var10);
                        var9.remove();
                    }

                    if (var3 == null)
                    {
                        var3 = new ArrayList();
                    }

                    var3.add(var10);
                }
                else
                {
                    Config.dbg("Not matching: " + var5 + "," + var7);
                }
            }

            return var3;
        }
        else
        {
            return super.getPendingBlockUpdates(var1, var2);
        }
    }
}
