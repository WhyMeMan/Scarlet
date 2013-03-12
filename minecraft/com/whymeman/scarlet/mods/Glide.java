package com.whymeman.scarlet.mods;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class Glide extends ModHack {

	public Glide() {
		super("Glide", Keyboard.KEY_G);
		getProperties().setBypassed(true);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		if (getPlayer().motionY < -0.15f && !getPlayer().isOnLadder())
			getPlayer().motionY = -0.15f;
	}
}	
