package com.whymeman.scarlet.console.commands;

import java.util.ArrayList;

import com.whymeman.scarlet.Scarlet;
import com.whymeman.scarlet.console.Console;
import com.whymeman.scarlet.manager.IOManager;


public class Friend implements ICommand
{
	private String command = "friend";
	
	public void onCmd(String[] cmd) 
	{
		IOManager m = (IOManager)Scarlet.getManagerByName("IOManager");
		//Default String: > friend {add|del|list|removeall} {nick1} {nick2(optional)}
		if (cmd.length >= 3)
		{
			String attr = cmd[2];
			if (attr.equalsIgnoreCase("add"))
			{
				if (cmd.length == 4)
				{
					m.addFriend(cmd[3],"");
					Console.acm("Friend (" + cmd[3] + ") added to your friends list.");
				}
				else if (cmd.length >= 5)
				{
					m.addFriend(cmd[3],cmd[4]);
					Console.acm("Friend (" + cmd[3] + ") added to your friends list under the nickname: " + cmd[4]);
				}
				else
					Console.acm("Syntax Error: \"help friend\" for more info on this command");
				return;
			}
			else if (attr.equalsIgnoreCase("setnick"))
			{
				Console.acm("Not yet implemented :C");
			}
		}
		Console.acm("Syntax Error: \"help friend\" for more info on this command");
	}
	public String getCommand()
	{
		return command;
	}
	public void showHelp(String[] cmd)
	{
		//Default String: > help friend {add|del|list|removeall} {nick1} {nick2(optional)}
		if (cmd.length == 3)
		{
			Console.acm("Usage: friend {add|del|list|setnick|removeall} {username} {nickname(optional)}");
			Console.acm("Definition: Manage your friends list");
			return;
		}
		else if (cmd.length > 3)
		{
			if (cmd[3].equalsIgnoreCase("add"))
			{
				Console.acm("Usage: friend add {username} {nickname(optional)}");
				Console.acm("Definition: Add a username to your friends list with an optional nickname");
				return;
			}
			else if (cmd[3].equalsIgnoreCase("del"))
			{
				Console.acm("Usage: friend del {username}");
				Console.acm("Definition: Remove a username from your friends list");
				return;
			}
			else if (cmd[3].equalsIgnoreCase("list"))
			{
				Console.acm("Usage: friend list");
				Console.acm("Definition: List all of your friends along with their nickname");
				return;
			}
			else if (cmd[3].equalsIgnoreCase("setnick"))
			{
				Console.acm("Usage: friend setnick {username} {nickname}");
				Console.acm("Definition: Change a friends nickname. Entering the same username will remove the nickname");
				return;
			}
			else
			{
				Console.acm("Unknown attribute \"" + cmd[2] + "\", use \"help friend\" for more info");
				
			}
		}
	}
}
