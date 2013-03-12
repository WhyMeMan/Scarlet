package com.whymeman.scarlet.mods;

import net.minecraft.src.Chunk;
import net.minecraft.src.MathHelper;

import org.lwjgl.input.Keyboard;

import com.whymeman.scarlet.modbase.Mod;
import com.whymeman.scarlet.modbase.mod.HackType;
import com.whymeman.scarlet.modbase.mod.ModHack;
import com.whymeman.scarlet.modbase.mod.Properties;

public class Fullbright extends ModHack
{
	private float oldGamma = 0;
	private boolean fading = false;
	
	public Fullbright() {
		super("Fullbright", Keyboard.KEY_F);
		this.setHackType(HackType.HACK_WORLD);
	}
	public void update(int delta)
	{
		if (!this.getActive() && !fading)
		{
			if (this.getMinecraft().gameSettings.gammaSetting != 0f)
			{
				this.oldGamma = 0f;
				this.fading = true;
			}
		}
		else if (!this.getActive() && fading)
		{
			if (this.oldGamma != getMinecraft().gameSettings.gammaSetting)
			{
				getMinecraft().gameSettings.gammaSetting -= 0.01*delta;
				if (getMinecraft().gameSettings.gammaSetting < oldGamma)
				{
					getMinecraft().gameSettings.gammaSetting = oldGamma;
					fading = false;
				}
			}
		}
		else if (getActive())
		{
			int pX = MathHelper.floor_double(this.getMinecraft().thePlayer.posX);
			int pY = MathHelper.floor_double(this.getMinecraft().thePlayer.posY);
            int pZ = MathHelper.floor_double(this.getMinecraft().thePlayer.posZ);
			Chunk chunk = getMinecraft().theWorld.getChunkFromBlockCoords(pX, pZ);
			float rl = 16;
			try {
			if (chunk != null)
				rl = 1 +chunk.getBlockLightValue(pX & 15, pY, pZ & 15, 0);
			} catch(Exception e) { }
			float maxBrightness = 16f/rl;
			maxBrightness *=2;
			if (maxBrightness > 8)
				maxBrightness = 8;
			if (maxBrightness != getMinecraft().gameSettings.gammaSetting)
			{
				getMinecraft().gameSettings.gammaSetting += 0.01*delta;
				if (getMinecraft().gameSettings.gammaSetting > maxBrightness)
					getMinecraft().gameSettings.gammaSetting = maxBrightness;
			}
		}
	}
	public void onEnable()
	{
		if (!fading)
			this.oldGamma = getMinecraft().gameSettings.gammaSetting;
	}
	public void onDisable()
	{
		fading = true;
	}
}
