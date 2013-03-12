package com.whymeman.scarlet.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;

public class GL11Helper {
	
	public static void drawBlockESP(double x, double y, double z, float r, float g, float b) {
		EntityPlayerSP ep = Minecraft.getMinecraft().thePlayer;
		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)Minecraft.getMinecraft().timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;
		GL11.glPushMatrix();
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848 /*GL_LINE_SMOOTH*/);
		GL11.glColor4f(r, g, b, 0.25F);
		drawBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1);
		GL11.glColor4f(r, g, b, 0.15F);
		drawOutlinedBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.6F);
		GL11.glDisable(2848 /*GL_LINE_SMOOTH*/);
		GL11.glDepthMask(true);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glPopMatrix();
	}
	
	public static void drawBox(double x, double y, double z, double x2, double y2, double z2) {
		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_QUADS);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	public static void drawOutlinedBox(double x, double y, double z, double x2, double y2, double z2, float l1) {
		glLineWidth(l1);

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_LINES);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}
	
	public static void enableDefaults() {
		Minecraft.getMinecraft().entityRenderer.disableLightmap(0.0D);
		GL11.glEnable(3042);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		GL11.glEnable(2848);
		GL11.glDisable(3553);
		GL11.glHint(3154, 4354);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(32925);
		GL11.glEnable(32926);
		GL11.glShadeModel(7425);
		GL11.glLineWidth(1.8F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL13.GL_MULTISAMPLE);
        GL11.glEnable(GL13.GL_SAMPLE_ALPHA_TO_COVERAGE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDepthMask(false);
	}

	public static void disableDefaults() {
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		GL11.glDisable(32925);
		GL11.glDisable(32926);
		GL11.glDepthMask(true);
		GL11.glDisable(GL13.GL_SAMPLE_ALPHA_TO_COVERAGE);
		GL11.glDisable(GL13.GL_MULTISAMPLE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		Minecraft.getMinecraft().entityRenderer.enableLightmap(0.0D);
	}
	
	public static void lines(AxisAlignedBB ax) {
		GL11.glLineWidth(1.8F);
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public static void drawCrossedOutlinedBoundingBox(AxisAlignedBB var0) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(3);
		tessellator.addVertex(var0.minX, var0.minY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.minY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.minY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.minY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.minY, var0.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(var0.minX, var0.maxY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.maxY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.maxY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.maxY, var0.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(var0.minX, var0.minY, var0.minZ);
		tessellator.addVertex(var0.minX, var0.maxY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.minY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.maxY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.minY, var0.maxZ);
		tessellator.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.minY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.maxY, var0.maxZ);
		tessellator.addVertex(var0.minX, var0.minY, var0.minZ);
		tessellator.addVertex(var0.minX, var0.maxY, var0.maxZ);
		tessellator.addVertex(var0.maxX, var0.minY, var0.minZ);
		tessellator.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		tessellator.draw();
	}
}
