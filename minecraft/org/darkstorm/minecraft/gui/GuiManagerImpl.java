/*
 * Copyright (c) 2013, DarkStorm (darkstorm@evilminecraft.net)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.darkstorm.minecraft.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import java.awt.*;

import net.minecraft.client.Minecraft;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.basic.*;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.listener.*;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.manager.ModManager;
import com.whymeman.scarlet.modbase.Mod;
import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;

/**
 * Minecraft GUI API
 * 
 * @author DarkStorm (darkstorm@evilminecraft.net)
 */
public class GuiManagerImpl implements GuiManager {
	private class ModuleFrame extends BasicFrame {
		private ModuleFrame() {
		}

		private ModuleFrame(String title) {
			super(title);
		}
	}

	private final List<Frame> frames;

	private Theme theme;
	private boolean setup = false;

	public GuiManagerImpl() {
		frames = new CopyOnWriteArrayList<Frame>();
	}

	@Override
	public void setup() {
		if(setup)
			return;
		setup = true;

		createModFrames();

		/* Sample module frame setup
		*
		final Map<ModuleCategory, ModuleFrame> categoryFrames = new HashMap<ModuleCategory, ModuleFrame>();
		for(Module module : ClientNameHere.getClientInstance().getModuleManager().getModules()) {
			if(!module.isToggleable())
				continue;
			ModuleFrame frame = categoryFrames.get(module.getCategory());
			if(frame == null) {
				String name = module.getCategory().name().toLowerCase();
				name = Character.toUpperCase(name.charAt(0))
						+ name.substring(1);
				frame = new ModuleFrame(name);
				frame.setTheme(theme);
				frame.setLayoutManager(new GridLayoutManager(2, 0));
				frame.setVisible(true);
				frame.setClosable(false);
				frame.setMinimized(true);
				addFrame(frame);
				categoryFrames.put(module.getCategory(), frame);
			}
			frame.add(new BasicLabel(module.getName()));
			final Module updateModule = module;
			Button button = new BasicButton(module.isEnabled() ? "Disable"
					: "Enable") {
				@Override
				public void update() {
					setText(updateModule.isEnabled() ? "Disable" : "Enable");
				}
			};
			button.addButtonListener(new ButtonListener() {
				@Override
				public void onButtonPress(Button button) {
					updateModule.toggle();
					button.setText(updateModule.isEnabled() ? "Disable"
							: "Enable");
				}
			});
			frame.add(button, HorizontalGridConstraint.RIGHT);
		}
		*/

		// Optional equal sizing and auto-positioning
		//resizeComponents();
		Minecraft minecraft = Minecraft.getMinecraft();
		Dimension maxSize = recalculateSizes();
		int offsetX = 5, offsetY = 5;
		int scale = minecraft.gameSettings.guiScale;
		if(scale == 0)
			scale = 1000;
		int scaleFactor = 0;
		while(scaleFactor < scale
				&& minecraft.displayWidth / (scaleFactor + 1) >= 320
				&& minecraft.displayHeight / (scaleFactor + 1) >= 240)
			scaleFactor++;
		for(Frame frame : frames) {
			frame.setX(offsetX);
			frame.setY(offsetY);
			offsetX += maxSize.width + 5;
			if(offsetX + maxSize.width + 5 > minecraft.displayWidth
					/ scaleFactor) {
				offsetX = 5;
				offsetY += maxSize.height + 5;
			}
		}
	}
	private void createModFrames()
	{
		ArrayList<Mod> mods = ModManager.getMods();
		ArrayList<String> tabs = new ArrayList<String>();
		ArrayList<Frame> frames = new ArrayList<Frame>();
		for (Mod m : mods)
		{
			if (m instanceof ModHack)
			{
				ModHack h = (ModHack)m;
				String type = h.getHackType();
				if (!tabs.contains(type) && !type.equals(HackType.HACK_NONE))
				{
					tabs.add(type);
					Frame frame = new BasicFrame("Frame");
					frame = new ModuleFrame(type);
					frame.setTheme(theme);
					frame.setLayoutManager(new GridLayoutManager(2, 0));
					frame.setVisible(true);
					frame.setClosable(false);
					frame.setPinnable(false);
					frame.setMinimized(true);
					frame.setWidth(188);
					frames.add(frame);
				}
				for (Frame f : frames)
					if (f.getTitle().equals(type))
						addModToFrame(h,f);
			}
		}
		for (Frame f : frames)
		{
			addFrame(f);
		}
	}
	private void addModToFrame(ModHack h, Frame frame)
	{
		BasicLabel label = new BasicLabel(h.getName());
		frame.add(label);
		final ModHack updateHack = h;
		Button button = new BasicButton(h.getActive() ? "Disable"
				: "Enable") {
			@Override
			public void update() {
				setText(updateHack.getActive() ? "Disable" : "Enable");
			}
		};
		button.addButtonListener(new ButtonListener() {
			@Override
			public void onButtonPress(Button button) {
				updateHack.toggle();
				button.setText(updateHack.getActive() ? "Disable"
						: "Enable");
			}
		});
		frame.add(button, GridLayoutManager.HorizontalGridConstraint.RIGHT);
	}
	private void createColorFrame()
	{
		
	}
	private void createKeybindFrame()
	{
		Frame frame = new BasicFrame("Frame");
		String name = "Keybinds";
		frame = new ModuleFrame(name);
		frame.setTheme(theme);
		frame.setLayoutManager(new GridLayoutManager(2, 0));
		frame.setVisible(true);
		frame.setClosable(false);
		frame.setMinimized(true);
		frame.setPinnable(true);

		
		addFrame(frame);
		
	}
	private void createHackFrame()
	{
		Frame frame = new BasicFrame("Frame");
		String name = "Loltest";
		frame = new ModuleFrame(name);
		frame.setTheme(theme);
		frame.setLayoutManager(new GridLayoutManager(2, 0));
		frame.setVisible(true);
		frame.setClosable(false);
		frame.setMinimized(true);
		/*ArrayList<IHack> hacks = Nero.getHacks();
		for (IHack h : hacks)
		{
			BasicLabel label = new BasicLabel(h.getName());
			frame.add(label);
			final IHack updateHack = h;
			Button button = new BasicButton(h.getActive() ? "Disable"
					: "Enable") {
				@Override
				public void update() {
					setText(updateHack.getActive() ? "Disable" : "Enable");
				}
			};
			button.addButtonListener(new ButtonListener() {
				@Override
				public void onButtonPress(Button button) {
					updateHack.toggle();
					button.setText(updateHack.getActive() ? "Disable"
							: "Enable");
				}
			});
			frame.add(button, GridLayoutManager.HorizontalGridConstraint.RIGHT);
		}*/
		BasicLabel label = new BasicLabel("Test");
		frame.add(label);
		BasicButton b = new BasicButton("Enable");
		frame.add(b, GridLayoutManager.HorizontalGridConstraint.RIGHT);
		
		addFrame(frame);
	}
	private void createTestFrame() {
		
		
		Frame testFrame = new BasicFrame("Frame");
		testFrame.setTheme(theme);
		testFrame.add(new BasicLabel("TEST LOL"));
		testFrame.add(new BasicLabel("TEST 23423"));
		testFrame.add(new BasicLabel("TE123123123ST LOL"));
		testFrame.add(new BasicLabel("31243 LO3242L432"));
		BasicButton testButton = new BasicButton("Duplicate this frame!");
		testButton.addButtonListener(new ButtonListener() {

			@Override
			public void onButtonPress(Button button) {
				createTestFrame();
			}
		});
		testFrame.add(new BasicCheckButton("This is a checkbox"));
		testFrame.add(testButton);
		ComboBox comboBox = new BasicComboBox("Simple theme", "Other theme",
				"Other theme 2");
		comboBox.addComboBoxListener(new ComboBoxListener() {

			@Override
			public void onComboBoxSelectionChanged(ComboBox comboBox) {
				Theme theme;
				switch(comboBox.getSelectedIndex()) {
				case 0:
					theme = new SimpleTheme();
					break;
				case 1:
					// Some other theme
					// break;
				case 2:
					// Another theme
					// break;
				default:
					return;
				}
				setTheme(theme);
			}
		});
		testFrame.add(comboBox);
		testFrame.setX(50);
		testFrame.setY(50);
		Dimension defaultDimension = theme.getUIForComponent(testFrame)
				.getDefaultSize(testFrame);
		testFrame.setWidth(defaultDimension.width);
		testFrame.setHeight(defaultDimension.height);
		testFrame.layoutChildren();
		testFrame.setVisible(true);
		testFrame.setClosable(false);
		testFrame.setMinimized(true);
		addFrame(testFrame);
	}

	private Dimension recalculateSizes() {
		int maxWidth = 0, maxHeight = 0;
		for(Frame frame : frames) {
			Dimension defaultDimension = frame.getTheme()
					.getUIForComponent(frame).getDefaultSize(frame);
			maxWidth = Math.max(maxWidth, defaultDimension.width);
			frame.setHeight(defaultDimension.height);
			if(frame.isMinimized()) {
				for(Rectangle area : frame.getTheme().getUIForComponent(frame)
						.getInteractableRegions(frame))
					maxHeight = Math.max(maxHeight, area.height);
			} else
				maxHeight = Math.max(maxHeight, defaultDimension.height);
		}
		for(Frame frame : frames) {
			frame.setWidth(maxWidth);
			frame.layoutChildren();
		}
		return new Dimension(maxWidth, maxHeight);
	}

	private void resizeComponents() {
		Button enable = new BasicButton("Enable");
		Button disable = new BasicButton("Disable");
		Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(
				enable);
		Dimension disableSize = theme.getUIForComponent(disable)
				.getDefaultSize(disable);
		int buttonWidth = Math.max(enableSize.width, disableSize.width);
		int buttonHeight = Math.max(enableSize.height, disableSize.height);
		for(Frame frame : frames) {
			if(frame instanceof ModuleFrame) {
				for(Component component : frame.getChildren()) {
					if(component instanceof Button) {
						component.setWidth(buttonWidth);
						component.setHeight(buttonHeight);
					}
				}
			}
		}
	}

	@Override
	public void addFrame(Frame frame) {
		frame.setTheme(theme);
		frames.add(frame);
	}

	@Override
	public void removeFrame(Frame frame) {
		frames.remove(frame);
	}

	@Override
	public Frame[] getFrames() {
		return frames.toArray(new Frame[frames.size()]);
	}

	@Override
	public void bringForward(Frame frame) {
		if(frames.remove(frame))
			frames.add(0, frame);
	}

	@Override
	public Theme getTheme() {
		return theme;
	}

	@Override
	public void setTheme(Theme theme) {
		this.theme = theme;
		for(Frame frame : frames)
			frame.setTheme(theme);
		resizeComponents();
		recalculateSizes();
	}

	@Override
	public void render() {
		for(Frame frame : frames)
			frame.render();
	}
}
