package com.whymeman.scarlet.mods;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

public class MagicCarpet extends ModHack
{
	public MagicCarpet()
	{
		super("Magic Carpet",Keyboard.KEY_M);
		this.setHackType(HackType.HACK_MOVEMENT);
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		int range = 2;
		if (getPlayer().inventory.getCurrentItem() == null || getPlayer().inventory.getCurrentItem().itemID != 20)
			return;
		for (int x = -range; x < range; x++)
		{
			for (int z = -range; z < range; z++)
			{
				int xLoc = (int)getPlayer().posX + x;
				int yLoc = (int)getPlayer().posY-2;
				int zLoc = (int)getPlayer().posZ + z;
				int blockID = getMinecraft().theWorld.getBlockId(xLoc, yLoc, zLoc);
				Block block = Block.blocksList[blockID];
				if (getPlayer().isSneaking())
				{
					getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(0, xLoc, yLoc, zLoc, 1));
					getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(2, xLoc, yLoc, zLoc, 1));
					
					getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(0, xLoc, yLoc+1, zLoc, 1));
					getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(2, xLoc, yLoc+1, zLoc, 1));
					
					getPlayer().sendQueue.addToSendQueue(
							new Packet15Place(
									xLoc,
									yLoc-2,
									zLoc,
									1,
									getPlayer().inventory.getCurrentItem(),
									0,
									0,
									0));
				}
				else if(blockID != 20)
				{
					getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(0, xLoc, yLoc, zLoc, 1));
					getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(2, xLoc, yLoc, zLoc, 1));
					getPlayer().sendQueue.addToSendQueue(
							new Packet15Place(
									xLoc,
									yLoc-1,
									zLoc,
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
