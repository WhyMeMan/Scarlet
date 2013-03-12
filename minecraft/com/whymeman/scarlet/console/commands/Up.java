package com.whymeman.scarlet.console.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;

import com.whymeman.scarlet.console.Console;


public class Up implements ICommand
{
	private String command = "up";
	
	public void onCmd(String[] cmd) 
	{
		//> command args args args
		if (cmd.length < 3)
		{
			Console.acm("Error: Invalid syntax. Read help for this command.");
			return;
		}
		int b;
		try {
		b = Integer.parseInt(cmd[2]);
		} catch (Exception e) { Console.acm("Error: Invalid Syntax"); return; }
		if (b == 0)
			return;
		if (b > 8)
		{
			Console.acm("You can only teleport 8 blocks");
			return;
		}
		EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
		thePlayer.setPosition(thePlayer.posX, thePlayer.posY+b, thePlayer.posZ);
		
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		Console.acm("Usage: up [num of blocks]");
		Console.acm("Definition: Teleports you up a certain number of blocks. Max: 8");
	}
}
