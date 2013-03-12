package com.whymeman.scarlet.util.mc;

public class Friend 
{
	private String name,nick;
	private boolean hasNick = true;
	public Friend(String name)
	{
		this(name,"");
		this.hasNick = false;
	}
	public Friend(String name, String nick)
	{
		this.name = name;
		this.setNick(nick);
	}
	public boolean hasNick()
	{
		return this.hasNick;
	}
	public void setNick(String nick)
	{
		this.nick = nick;
		this.hasNick = !this.nick.equals("");
	}
	public String getName() { return this.name; }
	public String getNick() { return this.nick; }
}
