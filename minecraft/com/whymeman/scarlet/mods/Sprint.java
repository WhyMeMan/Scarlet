package com.whymeman.scarlet.mods;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class Sprint extends ModHack {

	public Sprint() {
		super("Sprint", Keyboard.KEY_V);
		getProperties().setBypassed(true);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		
		boolean canSprint = getPlayer().movementInput.moveForward > 0.0F && !getPlayer().isSneaking();
    	
    	if (canSprint && !getPlayer().isSprinting())
    	{
    		getPlayer().setSprinting(true);
    	}
	}
}
