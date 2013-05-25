package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import org.lwjgl.input.Mouse;

import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.darkstorm.minecraft.gui.util.UtilDrawer;

public class SimpleButtonUI extends AbstractComponentUI<Button> {
	private final SimpleTheme theme;
	private UtilDrawer graphicsRenderer;
	SimpleButtonUI(SimpleTheme theme) {
		super(Button.class);
		this.theme = theme;
		this.graphicsRenderer = new UtilDrawer();
		foreground = Color.WHITE;
		background = new Color(128, 128, 128, 128 + 128 / 2);
	}

	@Override
	protected void renderComponent(Button button) {
		translateComponent(button, false);
		Rectangle area = button.getArea();
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);

		glDisable(GL_TEXTURE_2D);
		RenderUtil.setColor(button.getBackgroundColor());
//		glBegin(GL_QUADS);
//		{
//			glVertex2d(0, 0);
//			glVertex2d(area.width, 0);
//			glVertex2d(area.width, area.height);
//			glVertex2d(0, area.height);
//		}
//		glEnd();
		this.graphicsRenderer.drawGradientBorderedRect(0, 0, area.width, area.height, 0xFF7c7971, 0xff5d5c5b, 0x22000000);
		
		Point mouse = RenderUtil.calculateMouseLocation();
		Component parent = button.getParent();
		while(parent != null) {
			mouse.x -= parent.getX();
			mouse.y -= parent.getY();
			parent = parent.getParent();
		}
		if(area.contains(mouse)) {
			glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
//			glBegin(GL_QUADS);
//			{
//				glVertex2d(0, 0);
//				glVertex2d(area.width, 0);
//				glVertex2d(area.width, area.height);
//				glVertex2d(0, area.height);
//			}
//			glEnd();
			if (Mouse.isButtonDown(0))
				this.graphicsRenderer.drawGradientBorderedRect(-1, 0, area.width+1, area.height, 0xff5d5c5b, 0xFF7c7971, 0x22000000);
			else
				this.graphicsRenderer.drawGradientBorderedRect(-1, 0, area.width+1, area.height, 0xFF7c7971, 0xff5d5c5b, 0x22000000);
			
		}
		glEnable(GL_TEXTURE_2D);

		String text = button.getText();
		theme.getFontRenderer().drawString(
				text,
				area.width / 2 - theme.getFontRenderer().getStringWidth(text)
						/ 2,
				area.height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2,
				RenderUtil.toRGBA(button.getForegroundColor()));

		glEnable(GL_CULL_FACE);
		glDisable(GL_BLEND);
		translateComponent(button, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(Button component) {
		return new Dimension(theme.getFontRenderer().getStringWidth(
				component.getText()) + 4,
				theme.getFontRenderer().FONT_HEIGHT + 4);
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(Button component) {
		return new Rectangle[] { new Rectangle(0, 0, component.getWidth(),
				component.getHeight()) };
	}

	@Override
	protected void handleComponentInteraction(Button component, Point location,
			int button) {
		if(location.x <= component.getWidth()
				&& location.y <= component.getHeight() && button == 0)
			component.press();
	}
}