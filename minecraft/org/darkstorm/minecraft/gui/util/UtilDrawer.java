package org.darkstorm.minecraft.gui.util;

import static org.lwjgl.opengl.GL11.glScalef;

import java.awt.Color;
import java.awt.geom.Point2D;
/*import info.wulf.vulture.utils.CustomFont;
import info.wulf.vulture.utils.VultureWrapper;*/
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;

import org.lwjgl.opengl.GL11;

public class UtilDrawer extends Gui{
	private void drawEQTriangle(Point2D loc, int h, Color color)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 1f);
		int x = (int)loc.getX() - h;
		int x2 = (int)loc.getX() + h;
		int y = (int)loc.getY() + h;
		for (int i = 0; i <= h; i++)
		{
			this.drawRect(x+i, y-i, x2-i+1, y-1-i, color.getRGB());
		}
		GL11.glScalef(1f, 1f, 1f);
		GL11.glPopMatrix();
	}
	public void drawBaseRect(int i, int j, int k, int l, int i1, int j1,
            boolean flag) {
        if (flag) {
            drawRect(i + 1, j, k, l, i1);
            drawHorizontalLine(i + 1, k - 1, j, j1);
            drawVerticalLine(i + 1, j, l, j1);
            drawVerticalLine(k - 1, j, l, j1);
            drawHorizontalLine(i + 1, k - 1, l, j1);
        } else {
            drawRect(i + 2, j + 2, k - 1, l - 1, i1);
            drawHorizontalLine(i + 2, k - 2, j + 1, j1);
            drawVerticalLine(i + 1, j + 1, l - 1, j1);
            drawVerticalLine(k - 1, j + 1, l - 1, j1);
            drawHorizontalLine(i + 2, k - 2, l - 1, j1);
        }
    }

    public void drawBorderedRect(int i, int j, int k, int l, int i1, int j1,
            boolean flag) {
        glScalef(0.5F, 0.5F, 0.5F);
        drawBaseRect(i * 2, j * 2, k * 2, l * 2, i1, j1, flag);
        glScalef(2.0F, 2.0F, 2.0F);
    }

    public void drawBaseGradientRect(int i, int j, int k, int l, int i1,
            int j1, int k1) {
        drawGradientRect(i + 1, j, k, l, i1, j1);
        drawHorizontalLine(i + 1, k - 1, j, k1);
        drawVerticalLine(i + 1, j, l, k1);
        drawVerticalLine(k - 1, j, l, k1);
        drawHorizontalLine(i + 1, k - 1, l, k1);
    }

    public void drawGradientBorderedRect(int i, int j, int k, int l, int i1,
            int j1, int k1) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawBaseGradientRect(i * 2, j * 2, k * 2, l * 2, i1, j1, k1);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }
}