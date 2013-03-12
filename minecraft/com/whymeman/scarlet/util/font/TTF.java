package com.whymeman.scarlet.util.font;

import java.nio.IntBuffer;

import net.minecraft.src.FontRenderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class TTF
{
	
	private static int colorCode[];
	private static float red, blue, green, alpha;
	
	public TTF()
	{
        colorCode = new int[32];
	}
	public static int getWidth(UnicodeFont font, String s)
	{
		return font.getWidth(s);
	}
	public static void disableDefaults()
	{
		GL11.glEnable(3042);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
	}
	
	public static void enableDefaults()
	{
		GL11.glDisable(3042);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glScalef(1.0f, 1.0f, 1.0f);
	}
	
	public static void drawShadowString(UnicodeFont font, String text, float x, float y, Color color)
	{
		renderString(font, x+1, y+1, text, null);
		renderString(font, x, y, text, color);
	}
	public static void drawShadowStringC2(UnicodeFont font, String text, float x, float y, Color color, Color color2)
	{
		renderString(font, x, y+1, text, color2);
		renderString(font, x, y, text, color);
	}
	public static void drawString(UnicodeFont font, String text, float x, float y, Color color)
	{
		renderString(font, x, y, text, color);
	}
	
	public static void renderString(UnicodeFont font, float x, float y, String s, Color colorOverride)
	{
		GL11.glPushMatrix();
		disableDefaults();
		int col = 0xFFFFFF;
		int length=0;
		if(s.contains("\247"))
		{
			if(!s.startsWith("\247"))s="\247F"+s;
			String[] as = s.split("\247");
			for(int i = 0; i < as.length; i++)
			{
				try
				{
					String first = as[i].substring(0, 1);
					if(first.equalsIgnoreCase("0") ||
					first.equalsIgnoreCase("1") ||
					first.equalsIgnoreCase("2") ||
					first.equalsIgnoreCase("2") || 
					first.equalsIgnoreCase("3") ||
					first.equalsIgnoreCase("4") ||
					first.equalsIgnoreCase("5") ||
					first.equalsIgnoreCase("6") || 
					first.equalsIgnoreCase("7") ||
					first.equalsIgnoreCase("8") ||
					first.equalsIgnoreCase("9"))
					{
						col = FontRenderer.colorCode[Integer.parseInt(first)];
					}else
					{
						if(first.equalsIgnoreCase("a"))col=FontRenderer.colorCode[10];
						if(first.equalsIgnoreCase("b"))col=FontRenderer.colorCode[11];
						if(first.equalsIgnoreCase("c"))col=FontRenderer.colorCode[12];
						if(first.equalsIgnoreCase("d"))col=FontRenderer.colorCode[13];
						if(first.equalsIgnoreCase("e"))col=FontRenderer.colorCode[14];
						if(first.equalsIgnoreCase("f") || first.equalsIgnoreCase("r"))col=FontRenderer.colorCode[15];
					}
					//System.out.println("lolwat");
					//font.drawString((x) + 2 + length, y - 3, as[i].substring(1), org.newdawn.slick.Color.black);
					if(colorOverride != null)
					{
						font.drawString(x + length, y, as[i].substring(1), new org.newdawn.slick.Color(col));
					}else
					{
						font.drawString(x + length, y, as[i].substring(1), new org.newdawn.slick.Color(org.newdawn.slick.Color.black));
					}
					length += 1 + font.getWidth(as[i].substring(1));
				}catch(Exception e){}
			}
		}else
		{
			if(colorOverride!=null)
			{
				font.drawString(x, y, s, colorOverride);
				
			}
			else if (col != 0xFFFFFF)
			{
				font.drawString(x, y, s, new org.newdawn.slick.Color(col));
			}
			else
			{
				font.drawString(x, y, s, new org.newdawn.slick.Color(org.newdawn.slick.Color.black));
			}
		}
		enableDefaults();
		GL11.glPopMatrix();
	}
	
	public static int getScreenWidth()
	{
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		return(Math.round(viewport.get(2)));
	}
	
	public static int getScreenHeight()
	{
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		return(Math.round(viewport.get(3)));
	}
}