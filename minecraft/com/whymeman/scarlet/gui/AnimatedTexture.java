package com.whymeman.scarlet.gui;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;

public class AnimatedTexture 
{
	private int x,y;
	private String path;
	private Rectangle map;
	private boolean isLooping;
	private int fps;
	private int width,height;
	private float timePassed;
	private int currentFrame;
	private int totalFrames;
	public AnimatedTexture(int x, int y, int width, int height, int totalFrames, String path, Rectangle map) 
	{
		this.x = x;
		this.y = y;
		this.path = path;
		this.map = map;
		this.width = width;
		this.height = height;
		this.totalFrames = totalFrames;
		isLooping = true;
		fps = 5;
		timePassed = 0;
		currentFrame = 0;
	}
	public void draw(int delta)
	{
		map.x = (map.width*this.currentFrame)%this.width;
		this.drawTexture(map);
		if (isLooping)
		{
			timePassed += delta;
			while(timePassed >= (1000f/(fps)))
			{
				currentFrame++;
				if (currentFrame > this.totalFrames-1)
					currentFrame = 0;
				this.timePassed -= (1000f/(fps));
			}
		}
	}
	private void drawTexture(Rectangle bounds)
    {
		GL11.glPushMatrix();
		GL11.glScalef(.5f, .5f, 1f);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glTranslatef(x-bounds.x, y-bounds.y, 1f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		float rate = (float)(((float)this.currentFrame)/this.totalFrames);
		RenderEngine renderEngine = Minecraft.getMinecraft().renderEngine;
		int t = renderEngine.getTexture(this.path);
		renderEngine.bindTexture(t);
	   	 Tessellator tessellator = Tessellator.instance;
	   	 tessellator.startDrawingQuads();
	   	 tessellator.addVertexWithUV(bounds.x , bounds.y + bounds.height, 0, rate, 2/this.totalFrames);
	   	 tessellator.addVertexWithUV(bounds.x + bounds.width, bounds.y + bounds.height, 0, 1/this.totalFrames+rate, 2/this.totalFrames);
	   	 tessellator.addVertexWithUV(bounds.x + bounds.width, bounds.y , 0, 1/this.totalFrames+rate, 1/this.totalFrames);
	   	 tessellator.addVertexWithUV(bounds.x , bounds.y , 0, +rate, 1/this.totalFrames);
	   	 tessellator.draw();
	   	GL11.glScalef(1f, 1f, 1f);
	   	 GL11.glPopMatrix();
    }
	public void setLooping(boolean looping)
	{
		this.isLooping = looping;
	}
	public void setFps(int fps)
	{
		this.fps = fps;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public void setCurrentFrame(int f)
	{
		this.currentFrame = f;
	}
	public boolean getLooping() { return this.isLooping; }
	public int getFps() { return this.fps; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getCurrentFrame() { return this.currentFrame; }
}
