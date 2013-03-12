package com.whymeman.scarlet.mods;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.StringUtils;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.manager.IOManager;
import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;
import com.whymeman.scarlet.util.mc.Friend;

public class KillAura extends ModHack
{
	private float timePassed;
	private float range;
	private float delay;
	
	public KillAura() 
	{
		super("KillAura",Keyboard.KEY_K);
		this.getProperties().setBypassed(true);
		this.setHackType(HackType.HACK_COMBAT);
		timePassed = 0;
		delay = 100;
		range = 6f;
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		timePassed += delta;
		while (timePassed > delay)
		{
			timePassed -= delay;
			EntityPlayer target = getClosestEntity();
			if (target == null)
				return;
			getPlayer().swingItem();
			getMinecraft().playerController.attackEntity(getPlayer(),target);
		}
	}
	private EntityPlayer getClosestEntity()
	{
		List entities = getMinecraft().theWorld.getLoadedEntityList();
		ArrayList<EntityPlayer> targetList = new ArrayList<EntityPlayer>();
		for (Object o : entities)
		{
			if (o instanceof EntityPlayer)
			{
				ArrayList<Friend> friends = IOManager.getFriends();
				boolean isFriend = false;
		    	for (Friend f : friends)
				{
		    		if (f.getName().equalsIgnoreCase(StringUtils.stripControlCodes(((EntityPlayer)o).username)))
		    			isFriend = true;
				}
		    	if (isFriend)
		    		continue;
				if (((EntityPlayer)o).username.equals(getPlayer().username))
					continue;
				targetList.add((EntityPlayer)o);
			}
		}
		EntityPlayer target = null;
		for (EntityPlayer p : targetList)
		{
			if (target == null && getPlayer().getDistanceToEntity(p) <= getMaxRange())
				target = p;
			if (target != null && getPlayer().getDistanceToEntity(p) <= getPlayer().getDistanceToEntity(target))
				target = p;
		}
		return target;
	}
	private float getMaxRange()
	{
		return this.range;
	}
}
