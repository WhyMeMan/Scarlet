package com.whymeman.scarlet.modbase.mod;

import com.whymeman.scarlet.modbase.Mod;

public class ModRender extends Mod
{
	private int renderType;
	public ModRender(String name, int key)
	{
		super(name,key);
		this.renderType = RenderType.RENDER_NONE;
	}
	public void render() { }
	public void setRenderType(int renderType)
	{
		this.renderType = renderType;
	}
	public int getRenderType()
	{
		return this.renderType;
	}
}
