package com.whymeman.scarlet.console.commands;

import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.mods.Wallhack;



public class XrayDel implements ICommand
{
	private String command = "xraydel";
	
	public void onCmd(String[] cmd) 
	{
		//> command args args args
		try {
			int blockID = Integer.parseInt(cmd[2]);
			
			Wallhack wall = (Wallhack)ModManager.getModByName("Wallhack");
			for (int i = wall.enabledOres.size()-1; i >= 0; i--)
			{
				if (wall.enabledOres.get(i) == blockID)
				{
					wall.enabledOres.remove(i);
					break;
				}
			}
			
		} catch(Exception e) { Console.acm("Invalid Syntax"); }
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: xrayDel {Ore ID}");
		Console.acm("Definition: Del an ore to the xray list");
	}
}
