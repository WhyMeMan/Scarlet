package com.whymeman.scarlet.console.commands;

import java.util.ArrayList;

import com.whymeman.scarlet.console.Console;

public class Help implements ICommand
{
	private String command = "help";
	
	public void onCmd(String[] cmd) 
	{
		ArrayList<ICommand> cmds = Console.getCommands();
		if (cmd.length == 2)
		{
			Console.acm("Type \"help <command>\" for more info on each command");
			
			
			String commandsList = "Commands -";
			int i = 0;
			for (ICommand command : cmds)
			{
				if (i == cmds.size()-1)
				{
					commandsList = commandsList + " " + command.getCommand();
					break;
				}
				commandsList = commandsList + " " + command.getCommand() + ", ";
				i++;
			}
			Console.acm(commandsList);
			return;
		}
		else
		{
			for (ICommand command : cmds)
			{
				if (command.getCommand().equalsIgnoreCase(cmd[2]))
				{
					command.showHelp(cmd);
					return;
				}
			}
			Console.acm("No help documentation found for \"" + cmd[2] + "\"");
		}
		
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		
	}
}
