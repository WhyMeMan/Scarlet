package com.whymeman.scarlet.mods;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.Mod;
import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class Step extends ModHack
{
	private int timePassed = 0;
	public Step()
	{
		super("Step",Keyboard.KEY_NONE);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		timePassed += delta;
		if (timePassed >= 20)
		{
			timePassed = 0;
			if (getPlayer().isCollidedHorizontally)
				getPlayer().setPosition(getPlayer().posX, getPlayer().posY + 1, getPlayer().posZ);
		}
		
	}
}
