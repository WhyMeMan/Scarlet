package com.whymeman.scarlet.manager;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ScaledResolution;

import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.GuiManagerImpl;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.darkstorm.minecraft.gui.util.GuiControls;
import org.lwjgl.input.Keyboard;

public class ScarletGuiManager extends Manager
{
	private GuiManager manager;
	private int key = Keyboard.KEY_GRAVE;
	public ScarletGuiManager() 
	{
		manager = new GuiManagerImpl();
    	manager.setTheme(new SimpleTheme());
    	manager.setup();
	}
	public void update()
	{
		if (Keyboard.isKeyDown(key) && Minecraft.getMinecraft().currentScreen == null)
			Minecraft.getMinecraft().displayGuiScreen(new GuiControls(manager));
		for (Frame f : manager.getFrames())
    	{
    		f.update();
    		if (f.getX() + f.getWidth() > ScaledResolution.getScaledWidth())
    			f.setX(ScaledResolution.getScaledWidth() - f.getWidth());
    		if (f.isPinned() && Minecraft.getMinecraft().currentScreen == null)
    			f.render();
    	}
	}
}
