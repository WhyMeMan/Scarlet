package net.minecraft.src;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class NextTickHashSet extends AbstractSet
{
    private LongHashMap longHashMap = new LongHashMap();
    private int size = 0;
    private HashSet emptySet = new HashSet();

    public NextTickHashSet(Set var1)
    {
        this.addAll(var1);
    }

    public int size()
    {
        return this.size;
    }

    public boolean contains(Object var1)
    {
        if (!(var1 instanceof NextTickListEntry))
        {
            return false;
        }
        else
        {
            NextTickListEntry var2 = (NextTickListEntry)var1;

            if (var2 == null)
            {
                return false;
            }
            else
            {
                long var3 = ChunkCoordIntPair.chunkXZ2Int(var2.xCoord >> 4, var2.zCoord >> 4);
                HashSet var5 = (HashSet)this.longHashMap.getValueByKey(var3);
                return var5 == null ? false : var5.contains(var2);
            }
        }
    }

    public boolean add(Object var1)
    {
        if (!(var1 instanceof NextTickListEntry))
        {
            return false;
        }
        else
        {
            NextTickListEntry var2 = (NextTickListEntry)var1;

            if (var2 == null)
            {
                return false;
            }
            else
            {
                long var3 = ChunkCoordIntPair.chunkXZ2Int(var2.xCoord >> 4, var2.zCoord >> 4);
                HashSet var5 = (HashSet)this.longHashMap.getValueByKey(var3);

                if (var5 == null)
                {
                    var5 = new HashSet();
                    this.longHashMap.add(var3, var5);
                }

                boolean var6 = var5.add(var2);

                if (var6)
                {
                    ++this.size;
                }

                return var6;
            }
        }
    }

    public boolean remove(Object var1)
    {
        if (!(var1 instanceof NextTickListEntry))
        {
            return false;
        }
        else
        {
            NextTickListEntry var2 = (NextTickListEntry)var1;

            if (var2 == null)
            {
                return false;
            }
            else
            {
                long var3 = ChunkCoordIntPair.chunkXZ2Int(var2.xCoord >> 4, var2.zCoord >> 4);
                HashSet var5 = (HashSet)this.longHashMap.getValueByKey(var3);

                if (var5 == null)
                {
                    return false;
                }
                else
                {
                    boolean var6 = var5.remove(var2);

                    if (var6)
                    {
                        --this.size;
                    }

                    return var6;
                }
            }
        }
    }

    public Iterator getNextTickEntries(int var1, int var2)
    {
        long var3 = ChunkCoordIntPair.chunkXZ2Int(var1, var2);
        HashSet var5 = (HashSet)this.longHashMap.getValueByKey(var3);

        if (var5 == null)
        {
            var5 = this.emptySet;
        }

        return var5.iterator();
    }

    public Iterator iterator()
    {
        throw new UnsupportedOperationException("Not implemented");
    }
}
