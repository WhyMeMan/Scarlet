package com.whymeman.scarlet.mods;

import java.util.ArrayList;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet18Animation;
import net.minecraft.src.RenderGlobal;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;
import com.whymeman.scarlet.util.math.Vector3D;
import com.whymeman.scarlet.util.render.EspDrawer;

import net.minecraft.src.Block;

public class Nuker extends ModHack 
{
	private float timePassed = 0;
	private boolean mouseState = false;
	private ArrayList<Vector3D> markedBlocks = new ArrayList<Vector3D>();
	private int nukeBlock;
	
	public Nuker()
	{
		super("Nuker",Keyboard.KEY_P);
		this.setHackType(HackType.HACK_WORLD);
		nukeBlock = 2;
	}
	public void update(int delta)
	{
		if (!getActive())
			return;
		timePassed += delta;
		if (!getPlayer().capabilities.isCreativeMode)
		{
			while (timePassed >= 150)
			{
				timePassed -= 150;
				survivalNuke();
			}
		}
		else
		{
			if (Mouse.isButtonDown(1))
				mouseState = true;
			else if (mouseState)
			{
				markBlocksForDestruction();
				mouseState = false;
			}
			while (timePassed >= 150)
			{
				timePassed -= 150;
				destroyBlocksInRange();
			}
		}
	}
	private void survivalNuke()
	{
		int size = 4;
		for(int x = -size; x < size + 1; x++)
		{
			for(int z = -size; z < size + 1; z++)
			{
				for(int y = -size; y < size + 1; y++)
				{
					int i = (int) getMinecraft().thePlayer.posX + x;
					int j = (int) getMinecraft().thePlayer.posY + y;
					int k = (int) getMinecraft().thePlayer.posZ + z;
					if(getMinecraft().theWorld.getBlockId(i, j, k) != 0 && getMinecraft().theWorld.getBlockId(i, j, k) == this.nukeBlock)
					{
						getPlayer().swingItem();
						getPlayer().sendQueue.addToSendQueue(new Packet18Animation(getPlayer(), 1));
						getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(0, i, j, k, 0));
						getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(2, i, j, k, 0));
					}
				}
			}
		}
	}
	private void destroyBlocksInRange()
	{
		
		int size = 4;
		for(int x = -size; x < size + 1; x++)
		{
			for(int z = -size; z < size + 1; z++)
			{
				for(int y = -size; y < size + 1; y++)
				{
					int i = (int) getMinecraft().thePlayer.posX + x;
					int j = (int) getMinecraft().thePlayer.posY + y;
					int k = (int) getMinecraft().thePlayer.posZ + z;
					Vector3D blockV = new Vector3D(i,j,k);
					Vector3D markedBlock = null;
					boolean destroy = false;
					for (Vector3D v : this.markedBlocks)
					{
						if (v.compare(blockV))
						{
							markedBlock = v;
							destroy = true;
							break;
						}
					}
					if(destroy)
					{
						getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(0, i, j, k, 1));
						getPlayer().sendQueue.addToSendQueue(new Packet14BlockDig(2, i, j, k, 1));
					}
				}
			}
		}
	}
	private void markBlocksForDestruction()
	{
		int size = 7;
		for(int x = -size; x <= size; x++)
		{
			for(int z = -size; z <= size; z++)
			{
				for(int y = -size; y <= size; y++)
				{
					int i = (int) getMinecraft().thePlayer.posX + x;
					int j = (int) getMinecraft().thePlayer.posY + y;
					int k = (int) getMinecraft().thePlayer.posZ + z;
					if(getMinecraft().theWorld.getBlockId(i, j, k) != 0)
					{
						Vector3D toBeMarked = new Vector3D(i,j,k);
						boolean mark = true;
						for (Vector3D v : this.markedBlocks)
						{
							if (v.compare(toBeMarked))
								mark = false;
							
						}
						if (mark)
							this.markedBlocks.add(toBeMarked);
					}
				}
			}
		}
	}
	public void draw(float par5)
	{
		for (int i = this.markedBlocks.size()-1; i >= 0; i--)
		{
			Vector3D v = this.markedBlocks.get(i);
			if (getMinecraft().theWorld.getBlockId((int)v.getX(), (int)v.getY(), (int)v.getZ()) == 0)
				this.markedBlocks.remove(v);
		}
		for (Vector3D v : this.markedBlocks)
		{
			float var6 = 0.002F;
			double var8 = getPlayer().lastTickPosX + (getPlayer().posX - getPlayer().lastTickPosX) * (double)par5;
            double var10 = getPlayer().lastTickPosY + (getPlayer().posY - getPlayer().lastTickPosY) * (double)par5;
            double var12 = getPlayer().lastTickPosZ + (getPlayer().posZ - getPlayer().lastTickPosZ) * (double)par5;
			AxisAlignedBB axisalignedbb = Block.blocksList[2].getSelectedBoundingBoxFromPool(getMinecraft().theWorld, (int)v.getX(),(int)v.getY(), (int)v.getZ()).expand((double)var6, (double)var6, (double)var6).getOffsetBoundingBox(-var8, -var10, -var12);
			GL11.glLineWidth(2.0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			if (getPlayer().getDistance((int)v.getX(), (int)v.getY(), (int)v.getZ())<= 6)
				GL11.glColor4f(1f,0f,0f,0.2F);
			else
				GL11.glColor4f(0f,1f,0f,0.2F);
			EspDrawer.drawBox(axisalignedbb);
			RenderGlobal.drawOutlinedBoundingBox(axisalignedbb);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	public void onDisable()
	{
		this.markedBlocks.clear();
	}
	public void setBlockId(int blockId) {
		this.nukeBlock = blockId;
		
	}
	
}
