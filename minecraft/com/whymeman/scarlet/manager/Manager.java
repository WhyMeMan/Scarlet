package com.whymeman.scarlet.manager;

public class Manager 
{
	String name;
	public Manager() {
		this.name = "null";
	}
	public Manager(String s)
	{
		this.name = s;
	}
	public String getName()
	{
		return name;
	}
	public void update() { }
}
