package com.whymeman.scarlet;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.whymeman.scarlet.gui.AnimatedTexture;
import com.whymeman.scarlet.manager.*;
import com.whymeman.scarlet.modbase.Mod;
import com.whymeman.scarlet.modbase.mod.*;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.ScaledResolution;

import com.whymeman.scarlet.mods.*;

public class Scarlet 
{
	private static ArrayList<Manager> managers = new ArrayList<Manager>();
	public Scarlet()
	{
		managers.add(new ModManager());
		managers.add(new ScarletGuiManager());
		managers.add(new ConsoleManager());
		managers.add(new IOManager());
		managers.add(new IRCManager());
	}
	public void update()
	{	
		for (Manager m : managers)
			m.update();
	}
	public static Manager getManagerByName(String s)
	{
		for (Manager m : managers)
		{
			if (m.getName().equals(s))
				return m;
		}
		return null;
	}
	
}
