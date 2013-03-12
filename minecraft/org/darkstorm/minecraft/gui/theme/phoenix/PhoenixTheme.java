package org.darkstorm.minecraft.gui.theme.phoenix;

import java.awt.Font;

import net.minecraft.src.FontRenderer;

import org.darkstorm.minecraft.gui.font.UnicodeFontRenderer;
import org.darkstorm.minecraft.gui.theme.AbstractTheme;

/**
 * 
 * @author WhyMeMan
 * @see Developed for Darkstorm's GUI API, GUI meant to replicate the Phoenix client.
 *
 */

public class PhoenixTheme extends AbstractTheme {
	private final FontRenderer fontRenderer;

	public PhoenixTheme() {
		fontRenderer = new UnicodeFontRenderer(new Font("Trebuchet MS",
				Font.PLAIN, 15));

		installUI(new PhoenixFrameUI(this));
		installUI(new PhoenixPanelUI(this));
		installUI(new PhoenixLabelUI(this));
		installUI(new PhoenixButtonUI(this));
		installUI(new PhoenixCheckButtonUI(this));
		installUI(new PhoenixComboBoxUI(this));
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}
