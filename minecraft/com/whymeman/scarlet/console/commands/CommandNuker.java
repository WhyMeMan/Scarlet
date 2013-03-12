package com.whymeman.scarlet.console.commands;

import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.mods.Nuker;



public class CommandNuker implements ICommand
{
	private String command = "nuker";
	
	public void onCmd(String[] cmd) 
	{
		//> nuker set 2
		try {
			if (cmd[2].equalsIgnoreCase("set"))
			{
				int blockId = Integer.parseInt(cmd[3]);
				Nuker nuker = (Nuker)ModManager.getModByName("Nuker");
				nuker.setBlockId(blockId);
			}
		} catch(Exception e) { Console.acm("Invalid Syntax!"); }
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: ");
		Console.acm("Definition: ");
	}
}
