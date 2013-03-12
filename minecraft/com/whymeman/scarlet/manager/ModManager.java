package com.whymeman.scarlet.manager;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.ScaledResolution;

import com.whymeman.scarlet.gui.AnimatedTexture;
import com.whymeman.scarlet.modbase.Mod;
import com.whymeman.scarlet.modbase.mod.ModHack;
import com.whymeman.scarlet.mods.*;

public class ModManager extends Manager
{
	private static ArrayList<Mod> mods = new ArrayList<Mod>();
	private long lastMS = 0;
	public ModManager()
	{
		addMod(new Fullbright());
		addMod(new Fly());
		addMod(new WaterWalk());
		addMod(new Glide());
		addMod(new Sprint());
		addMod(new KillAura());
		addMod(new ReverseNuker());
		//addMod(new MagicCarpet());
		addMod(new Nuker());
		addMod(new Nofall());
		//addMod(new Tracers());
	}
	public void update()
	{

		if (lastMS == 0)
			lastMS = System.currentTimeMillis();
		int delta = (int) (System.currentTimeMillis() - lastMS);
		int yIndex = 2;
		FontRenderer f = Minecraft.getMinecraft().fontRenderer;
		f.drawStringWithShadow("Scarlet 1.4.7", 2, 2, 0xFFFFFFFF);
		for (Mod m : mods)
		{
			if (m instanceof ModHack)
			{
				if (m.isKeyDown())
					m.toggle();
				if (m.getActive())
				{
					f.drawStringWithShadow(m.getName(), ScaledResolution.getScaledWidth() - f.getStringWidth(m.getName())- 2, yIndex, 0xFFFFFFFF);
					yIndex += 10;
				}
				m.update(delta);
			}
		}
		lastMS = System.currentTimeMillis();
	}
	public static Mod getModByName(String name)
	{
		for (Mod m : mods)
		{
			if (m.getName().equalsIgnoreCase(name))
				return m;
		}
	
		return null;
	}
	public void addMod(Mod m)
	{
		mods.add(m);
	}
	public static ArrayList<Mod> getMods()
	{
		return mods;
	}

}
