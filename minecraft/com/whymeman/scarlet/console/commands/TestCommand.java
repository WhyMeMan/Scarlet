package com.whymeman.scarlet.console.commands;

import com.whymeman.scarlet.console.Console;



public class TestCommand implements ICommand
{
	private String command = "Blah";
	
	public void onCmd(String[] cmd) 
	{

		
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
