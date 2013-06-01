package com.whymeman.scarlet.mods;

import java.util.ArrayList;

import net.minecraft.src.Block;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class Wallhack extends ModHack
{
	public ArrayList<Integer> enabledOres;
	public int opacity;
	
	public Wallhack() 
	{
		super("Wallhack",Keyboard.KEY_X);
		this.setHackType(HackType.HACK_WORLD);
		
		enabledOres = new ArrayList<Integer>();
		opacity = 130;
		
		enabledOres.add(Block.oreDiamond.blockID);
		enabledOres.add(Block.oreEmerald.blockID);
		enabledOres.add(Block.oreGold.blockID);
		enabledOres.add(11);
	}
	
	public void onEnable()
	{
		renderArea();
	}
	
	public void onDisable()
	{
		renderArea();
	}
	
	public void renderArea()
	{
		int x = (int)getPlayer().posX;

        int z = (int)getPlayer().posZ;
        getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(x-200, 0, z-200, x+200, 255, z+200);
        getMinecraft().renderGlobal.loadRenderers();
	}
	
	public int getOpacity()
	{
		return this.opacity;
	}

	public void setOpacity(int newOpacity)
	{
		this.opacity = newOpacity;
	}

}
