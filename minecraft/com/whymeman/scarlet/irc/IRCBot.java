package com.whymeman.scarlet.irc;

import net.minecraft.client.Minecraft;

import org.jibble.pircbot.PircBot;

public class IRCBot extends PircBot
{
	private Minecraft mc;
	public IRCBot(String name)
	{
		mc = Minecraft.getMinecraft();
		this.setName(name);
		connect();
	}
	public void connect()
	{
		try{
			setVerbose(false);
			connect("ownagedev.com");
			joinChannel("#Xenon");
		} catch (Exception e) { e.printStackTrace(); }
		
	}
	public void update()
	{
		/*if (!mc.session.username.equalsIgnoreCase(this.getName()))
		{
			this.setName(mc.session.username);
			this.changeNick(mc.session.username);
		}*/
	}
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		
	}
	public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)
	{
		
	}
	public void onPrivateMessage(String sender, String login, String hostname, String message)
	{
		
	}
	public static void acm(String msg)
    {
    	if(Minecraft.getMinecraft().thePlayer != null) 
		{
    		Minecraft.getMinecraft().thePlayer.addChatMessage("[§cScarlet§f]: " + msg);
		}
    }
	public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target)
	{
		sendNotice(sourceNick,"\001VERSION Scarlet\001");
	}
}
