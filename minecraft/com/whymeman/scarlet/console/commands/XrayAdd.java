package com.whymeman.scarlet.console.commands;

import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.mods.Wallhack;



public class XrayAdd implements ICommand
{
	private String command = "xrayadd";
	
	public void onCmd(String[] cmd) 
	{
		//> command args args args
		try {
			int blockID = Integer.parseInt(cmd[2]);
			
			Wallhack wall = (Wallhack)ModManager.getModByName("Wallhack");
			if (!wall.enabledOres.contains(blockID))
				wall.enabledOres.add(blockID);
			
		} catch(Exception e) { Console.acm("Invalid Syntax"); }
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: xrayadd {Ore ID}");
		Console.acm("Definition: Add an ore to the xray list");
	}
}
