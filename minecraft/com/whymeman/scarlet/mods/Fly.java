package com.whymeman.scarlet.mods;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class Fly extends ModHack {

	public Fly() {
		super("Fly", Keyboard.KEY_R);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		getPlayer().capabilities.isFlying = true;
	}
	public void onDisable()
	{
		getPlayer().capabilities.isFlying = false;
	}
}
