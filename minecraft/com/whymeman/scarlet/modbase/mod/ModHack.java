package com.whymeman.scarlet.modbase.mod;

import com.whymeman.scarlet.modbase.Mod;

public class ModHack extends Mod
{
	private String hackType;
	public ModHack(String name, int keybind)
	{
		super(name,keybind);
		this.hackType = HackType.HACK_NONE;
	}
	public void setHackType(String hackType)
	{
		this.hackType = hackType;
	}
	public String getHackType()
	{
		return this.hackType;
	}
}
