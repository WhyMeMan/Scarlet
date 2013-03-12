package com.whymeman.scarlet.console.commands;

import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.modbase.Mod;



public class CommandGlide implements ICommand
{
	private String command = "glide";
	
	public void onCmd(String[] cmd) 
	{
		Mod glide = ModManager.getModByName("Glide");
		glide.toggle();
		Console.acm("Glide turned " + (glide.getActive() ? "on" : "off"));
		
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
