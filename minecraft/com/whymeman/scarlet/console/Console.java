package com.whymeman.scarlet.console;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import com.whymeman.scarlet.console.commands.*;

public class Console
{
    public static String hackName = "Console";
    public static boolean isActive;
    public static int key = Keyboard.KEY_Y;
    
    private static boolean saves = false;
    
    private ConsoleGuiChat guiChat;
    private static ConsoleGuiNewChat guiNewChat;
    private boolean reset = false;
    private static ArrayList<ICommand> commands;

    public Console()
    {
    	guiNewChat = new ConsoleGuiNewChat(Minecraft.getMinecraft());
    	update();
    	
    	
    	commands = new ArrayList<ICommand>();
    	
    	//Add Commands
    	addCommand(new Help());
    	addCommand(new Keybinds());
    	addCommand(new Up());
    	addCommand(new Down());
    	addCommand(new CommandGlide());
    	addCommand(new CreativeEnchant());
    	addCommand(new CommandNuker());
    	addCommand(new Friend());
    	addCommand(new Xray());
    	//addCommand(new XrayAdd());
    	//addCommand(new XrayDel());
    }
    
    public void update() 
    {
    	if (!isActive)
    		return;
    	String input = guiChat.inputField.getText();
    	String[] words = input.split(" ");
    	try {
    		if (!words[0].equalsIgnoreCase(">") || input.length() < 2 ||words[1].equalsIgnoreCase("y"))
    		{
    			guiChat.inputField.setText("> ");
    			
    		}
    	} catch (Exception e) {  }
    }

    public void onCmd(String cmd) 
    {
    	//> command args args args
    	String split[] = cmd.split(" ");
    	if (split.length <= 1)
    	{
    		acm("No command was typed. Use \"help\" for a list of commands");
    		return;
    	}
    	else
    	{
    		for (ICommand command : commands)
    		{
    			if (command.getCommand().equalsIgnoreCase(split[1]))
    			{
    				command.onCmd(split);
    				return;
    			}
    		}
    		acm("\"" + split[1] + "\" Unknown command. Use \"help\" for a list of commands");
    	}
    }
    public static void acm(String msg)
    {
    	if(Minecraft.getMinecraft().thePlayer != null) 
		{
    		Minecraft.getMinecraft().thePlayer.addChatMessage("[§cScarlet§f]: " + msg);
		}
    }
    public void setActive(boolean active)
    {
    	this.isActive = active;
    	if (!isActive)
    		return;
    	guiChat = new ConsoleGuiChat(this);
    	Minecraft.getMinecraft().displayGuiScreen(guiChat);
    }
    public ConsoleGuiNewChat getChatGUI() 
    {
    	return guiNewChat;
    }
    private void addCommand(ICommand cmd)
    {
    	commands.add(cmd);
    }
    public static ArrayList getCommands()
    {
    	return commands;
    }
}
