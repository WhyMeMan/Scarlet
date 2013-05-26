package com.whymeman.scarlet.modbase;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.WorldClient;

import com.whymeman.scarlet.modbase.mod.Properties;
import com.whymeman.scarlet.modbase.mod.PropertyType;

public class Mod 
{
	private String name;
	private int key;
	private Properties properties;
	private Minecraft mc = Minecraft.getMinecraft();
	private boolean isActive;
	private static boolean[] keyStates = new boolean[256];
	
	public Mod(String name, int key)
	{
		this.name = name;
		this.key = key;
		Properties p = new Properties();
		p.setBypassed(true);
		p.setDisabled(false);
		p.setType(PropertyType.TYPE_HACK);
		this.setProperties(p);
		isActive = false;
	}
	public void update(int delta) { }
	public void toggle()
	{
		this.setActive(!isActive);
	}
	public void onEnable() { }
	public void onDisable() { }
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
		if (this.isActive)
			onEnable();
		else
			onDisable();
	}
	public boolean getActive()
	{
		return this.isActive;
	}
	public String getName()
	{
		return this.name;
	}
	public int getKey()
	{
		return this.key;
	}
	public Properties getProperties()
	{
		return this.properties;
	}
	public void setProperties(Properties p)
	{
		this.properties = p;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setKeybind(int key)
	{
		this.key = key;
	}
	public Minecraft getMinecraft()
	{
		return this.mc;
	}
	public WorldClient getWorld()
	{
		return getMinecraft().theWorld;
	}
	protected EntityClientPlayerMP getPlayer()
	{
		return this.mc.thePlayer;
	}
	public boolean isKeyDown()
	{
		 return mc.currentScreen != null ? false : (Keyboard.isKeyDown(this.key) != keyStates[this.key] ? (keyStates[this.key] = !keyStates[this.key]) : false);
	}
}
