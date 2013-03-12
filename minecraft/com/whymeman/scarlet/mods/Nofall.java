package com.whymeman.scarlet.mods;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class Nofall extends ModHack 
{
	public Nofall()
	{
		super("Nofall",Keyboard.KEY_N);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
}
