package com.whymeman.scarlet.console.commands;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.modbase.Mod;


public class Keybinds implements ICommand
{
	private String command = "keybinds";
	
	public void onCmd(String[] cmd) 
	{
		ArrayList<Mod> mods = ModManager.getMods();
		
		String output = "Keybinds:";
		int i = 0;
		
		for (Mod m : mods)
		{
			if (m.getKey() != Keyboard.KEY_NONE)
			{
				if (i == mods.size()-1)
				{
					output = output + " §2" + m.getName() + " - §f" + Keyboard.getKeyName(m.getKey());
					break;
				}
				output = output + " §2" + m.getName() + " - §f" + Keyboard.getKeyName(m.getKey()) + ", ";
			}
			i++;
		}
		Console.acm(output);
		
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: keybinds");
		Console.acm("Definition: Displays a list of hacks and the keybinds corresponding to them.");
	}
}
