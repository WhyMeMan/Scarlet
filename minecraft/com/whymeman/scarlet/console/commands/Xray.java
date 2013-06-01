package com.whymeman.scarlet.console.commands;

import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.mods.Wallhack;

public class Xray implements ICommand
{
	private String command = "xray";
	
	public void onCmd(String[] cmd) 
	{
		try {
			Wallhack wall = (Wallhack)ModManager.getModByName("Wallhack");
			if (cmd[2].equalsIgnoreCase("add"))
			{
				int blockID = Integer.parseInt(cmd[3]);
				
				if (!wall.enabledOres.contains(blockID))
				{
					wall.enabledOres.add(blockID);
					
					if(wall.getActive())
					wall.renderArea();
				}
					
			}
			else if (cmd[2].equalsIgnoreCase("del"))
			{
				int blockID = Integer.parseInt(cmd[3]);
				
				for (int i = wall.enabledOres.size()-1; i >= 0; i--)
				{
					if (wall.enabledOres.get(i) == blockID)
					{
						wall.enabledOres.remove(i);
						
						if(wall.getActive())
						wall.renderArea();
						
						break;
					}
				}
			}
			else if (cmd[2].equalsIgnoreCase("opacity"))
			{
				int op = Integer.parseInt(cmd[3]);
				if(op > 255)
					Console.acm("Invalid Opacity!");
				else
				{
					wall.setOpacity(op);
					
					if(wall.getActive())
					wall.renderArea();
				}

			}
		} catch(Exception e) { Console.acm("Invalid Syntax!"); }
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: xray {add|del|opacity} {id|opacity}");
		Console.acm("Definition: Xray settings");
	}
}
