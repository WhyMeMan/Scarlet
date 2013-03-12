package com.whymeman.scarlet.manager;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.console.Console;

public class ConsoleManager extends Manager {

	private Console console;
	public ConsoleManager() 
	{
		console = new Console();
	}
	public void update()
	{
		if (Keyboard.isKeyDown(console.key) && Minecraft.getMinecraft().currentScreen == null)
			console.setActive(true);
		console.update();
	}

}
