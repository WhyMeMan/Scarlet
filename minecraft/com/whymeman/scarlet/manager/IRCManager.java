package com.whymeman.scarlet.manager;

import java.util.Random;

import net.minecraft.client.Minecraft;

import org.jibble.pircbot.PircBot;

import com.whymeman.scarlet.irc.IRCBot;

public class IRCManager extends Manager
{
	private IRCBot irc;
	private Minecraft mc;
	public IRCManager()
	{
		mc = Minecraft.getMinecraft();
		//irc = new IRCBot("ACCIDENTLY");

	}
	public void update()
	{
		//irc.update();
	}
}
