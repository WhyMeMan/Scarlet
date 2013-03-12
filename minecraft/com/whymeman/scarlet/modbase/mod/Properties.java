package com.whymeman.scarlet.modbase.mod;

public class Properties 
{
	private int type;
	private boolean isDisabled;
	private boolean isBypassed;
	
	public Properties()
	{
		this.type = PropertyType.TYPE_HACK;
		this.isDisabled = false;
		this.isBypassed = false;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public void setDisabled(boolean isDisabled)
	{
		this.isDisabled = isDisabled;
	}
	public void setBypassed(boolean bypassed)
	{
		this.isBypassed = bypassed;
	}
}
