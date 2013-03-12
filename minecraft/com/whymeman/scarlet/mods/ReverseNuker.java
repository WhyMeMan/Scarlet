package com.whymeman.scarlet.mods;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet15Place;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class ReverseNuker extends ModHack 
{
	private boolean mouseDown = false;
	public ReverseNuker() {
		super("Reverse Nuker", Keyboard.KEY_B);
		this.setHackType(HackType.HACK_WORLD);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		if (Mouse.isButtonDown(1))
    	{
    		if (mouseDown)
    			return;
    		mouseDown = true;
    	}
    	else if (!Mouse.isButtonDown(1))
    	{
    		mouseDown = false;
    		return;
    	}
    	
    	ItemStack handID = getPlayer().inventory.getCurrentItem();
    	if (handID == null)
    		return;
    	int r = 6;
    	int pX = (int)getPlayer().posX;
    	int pY = (int)getPlayer().posY-1;
    	int pZ = (int)getPlayer().posZ;
    	for (int x = -r; x <=r; x++)
		{
    		for (int y = -r; y <=r; y++)
    		{
    			for (int z = -r; z <=r; z++)
    			{
    				int bID = getMinecraft().theWorld.getBlockId(pX + x, pY + y, pZ + z);
    				if (bID != 0 && bID != handID.itemID)
    				{
    					int bx = pX + x;
    					int by = pY + y;
    					int bz = pZ + z;
    					if (getMinecraft().theWorld.getBlockId(bx, by+1, bz) == 0)
    			    	{
    						getPlayer().sendQueue.addToSendQueue(
    								new Packet15Place(
    										bx,
    										by,
    										bz,
    										1,
    										getPlayer().inventory.getCurrentItem(),
    										0,
    										0,
    										0));
    			    	}
    					
    					
    				}
    			}
    		}
		}
	}
}
