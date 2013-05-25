package com.whymeman.scarlet.mods;

import net.minecraft.src.EntityClientPlayerMP;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class WaterWalk extends ModHack {

	public WaterWalk() {
		super("Waterwalk", Keyboard.KEY_J);
		this.getProperties().setBypassed(true);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		if (getPlayer().isWet())
		{
			getPlayer().setSprinting(false);
			getPlayer().jump();
			getPlayer().motionY/=2.0;
		}
			
		
		
	}
	public boolean isMoving()
	{
		return Math.sqrt((getPlayer().motionX *  getPlayer().motionX) + (getPlayer().motionZ * getPlayer().motionZ)) >= 0.04;
	}
}
